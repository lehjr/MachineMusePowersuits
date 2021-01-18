package com.github.lehjr.numina.tileentity;

import com.github.lehjr.numina.basemod.NuminaObjects;
import com.github.lehjr.numina.capabilities.TileEnergyStorage;
import com.github.lehjr.numina.capabilities.TileEnergyWrapper;
import com.github.lehjr.numina.config.NuminaSettings;
import com.github.lehjr.numina.util.tileentity.MuseTileEntity;
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

public class ChargingBaseTileEntity extends MuseTileEntity implements ITickableTileEntity {
    public ChargingBaseTileEntity() {
        super(NuminaObjects.CHARGING_BASE_TILE.get());
    }

    /**
     * Fetch the entities within a given position
     * @return
     */
    @Nullable
    public List<LivingEntity> getEntities() {
        List<LivingEntity> list = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos), entity -> entity instanceof LivingEntity);
        return list;
    }

    // testing some stuff from github.com/McJty/YouTubeModding14
    private ItemStackHandler itemHandler = createHandler();
    private TileEnergyStorage energyStorage = createEnergy();

    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private LazyOptional<IEnergyStorage> tileEnergy = LazyOptional.of(() -> energyStorage);

    private TileEnergyWrapper energyWrapperStorage = createWrapper();
    private LazyOptional<IEnergyStorage> energyWrapper = LazyOptional.of(() -> energyWrapperStorage);

    @Override
    public void remove() {
        super.remove();
        handler.invalidate();
        tileEnergy.invalidate();
    }

    @Override
    public void tick() {
        if (world.isRemote) {
            return;
        }

        List<LivingEntity> entityList = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos), entity -> entity instanceof LivingEntity);

        for (LivingEntity entity : entityList) {
            sendOutPower(entity);
        }

        BlockState state = this.world.getBlockState(pos);
        BlockState newState = state.with(BlockStateProperties.POWERED, energyWrapper.map(IEnergyStorage::getEnergyStored).orElse(0) > 0);

        if (state != newState) {
            this.world.setBlockState(this.pos, newState, 3);
        }
    }

    private void sendOutPower(LivingEntity entity) {
        energyWrapper.ifPresent(wrapper->{
            entity.getEquipmentAndArmor().forEach(itemStack -> {
                if (wrapper.getEnergyStored() > 0) {
                    boolean doContinue = itemStack.getCapability(CapabilityEnergy.ENERGY).map(iItemEnergyHandler -> {
                                if (iItemEnergyHandler.canReceive()) {
                                    int received = iItemEnergyHandler.receiveEnergy(wrapper.getEnergyStored(), false);
                                    energyWrapperStorage.extractEnergy(received, false);
                                    wrapper.extractEnergy(received, false);
                                    markDirty();
                                    return wrapper.getEnergyStored() > 0;
                                } else {
                                    return true;
                                }
                            }
                    ).orElse(true);
                    if (!doContinue) {
                        return;
                    }
                }
            });
        });
    }

    /**
     * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
     * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
     */
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, -1, this.getUpdateTag());
    }

    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void read(BlockState stateIn, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        energyStorage.deserializeNBT(nbt.getCompound("energy"));
        super.read(stateIn, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        nbt.put("inv", itemHandler.serializeNBT());
        nbt.put("energy", energyStorage.serializeNBT());
        return super.write(nbt);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                markDirty();
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

    private TileEnergyStorage createEnergy() {
        return new TileEnergyStorage(NuminaSettings.chargingBaseMaxPower(), NuminaSettings.chargingBaseMaxPower()) {
            @Override
            public void onEnergyChanged() {
                markDirty();
            }
        };
    }

    private TileEnergyWrapper createWrapper() {
        return new TileEnergyWrapper(this.tileEnergy, this.handler);
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