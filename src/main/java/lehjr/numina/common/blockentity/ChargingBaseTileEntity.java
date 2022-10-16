/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.numina.common.blockentity;

import lehjr.numina.common.capabilities.energy.BlockEnergyStorage;
import lehjr.numina.common.capabilities.energy.BlockEnergyWrapper;
import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.config.NuminaSettings;
import lehjr.numina.common.energy.ElectricItemUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ChargingBaseTileEntity extends NuminaBlockEntity implements ITickableTileEntity {
    public ChargingBaseTileEntity() {
        super(NuminaObjects.CHARGING_BASE_TILE.get());
    }

    /**
     * Fetch the entities within a given position
     * @return
     */
    @Nullable
    public List<LivingEntity> getEntities() {
        List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(this.worldPosition), entity -> entity instanceof LivingEntity);
        return list;
    }

    // testing some stuff from github.com/McJty/YouTubeModding14
    private ItemStackHandler itemHandler = createHandler();
    private BlockEnergyStorage energyStorage = createEnergy();

    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private LazyOptional<IEnergyStorage> tileEnergy = LazyOptional.of(() -> energyStorage);

    private BlockEnergyWrapper energyWrapperStorage = createWrapper();
    private LazyOptional<IEnergyStorage> energyWrapper = LazyOptional.of(() -> energyWrapperStorage);

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
        tileEnergy.invalidate();
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            return;
        }

        List<LivingEntity> entityList = level.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(getBlockPos()), entity -> entity instanceof LivingEntity);

        for (LivingEntity entity : entityList) {
            sendOutPower(entity);
        }

        BlockState state = this.level.getBlockState(getBlockPos());
        BlockState newState = state.setValue(BlockStateProperties.POWERED, energyWrapper.map(IEnergyStorage::getEnergyStored).orElse(0) > 0);

        if (state != newState) {
            this.level.setBlock(this.getBlockPos(), newState, 3);
        }
    }

    private void sendOutPower(LivingEntity entity) {
        energyWrapper.ifPresent(wrapper-> {
                    int received = ElectricItemUtils.givePlayerEnergy(entity, wrapper.getEnergyStored(), false);
                    if (received > 0) {
                        wrapper.extractEnergy(received, false);
                        setChanged();
                    }
                });
    }

    /**
     * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.sendBlockUpdated. For
     * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
     */
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.worldPosition, -1, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }

    @Override
    public void load(BlockState stateIn, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        energyStorage.deserializeNBT(nbt.getCompound("energy"));
        super.load(stateIn, nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("inv", itemHandler.serializeNBT());
        nbt.put("energy", energyStorage.serializeNBT());
        return super.save(nbt);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getCapability(CapabilityEnergy.ENERGY).isPresent();
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!stack.getCapability(CapabilityEnergy.ENERGY).isPresent()) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    private BlockEnergyStorage createEnergy() {
        return new BlockEnergyStorage(NuminaSettings.chargingBaseMaxPower(), NuminaSettings.chargingBaseMaxPower()) {
            @Override
            public void onEnergyChanged() {
                setChanged();
            }
        };
    }

    private BlockEnergyWrapper createWrapper() {
        return new BlockEnergyWrapper(this.tileEnergy, this.handler);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return energyWrapper.cast();
        }
        return super.getCapability(cap, side);
    }

    public LazyOptional<IEnergyStorage> getBatteryEnergyHandler() {
        return handler.map(iItemHandler -> iItemHandler.getStackInSlot(0).getCapability(CapabilityEnergy.ENERGY)).orElse(LazyOptional.empty());
    }

    public LazyOptional<IEnergyStorage> getTileEnergyHandler() {
        return tileEnergy;
    }
}