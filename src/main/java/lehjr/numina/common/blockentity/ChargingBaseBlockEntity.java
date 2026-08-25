package lehjr.numina.common.blockentity;

import lehjr.numina.common.capabilities.energy.BlockEnergyWrapper;
import lehjr.numina.common.capabilities.energy.ExtendedEnergyStorage;
import lehjr.numina.common.config.NuminaCommonConfig;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.registration.NuminaBlockEntities;
import lehjr.numina.common.utils.ElectricItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;
import java.util.Objects;

public class ChargingBaseBlockEntity extends BlockEntity {

    public static final String ITEMS_TAG = "Inventory";

    public static int SLOT_COUNT = 1;
    public static int SLOT = 0;
    private final ItemStackHandler itemHandler;
    private final ExtendedEnergyStorage internalEnergyHandler;
    IEnergyStorage energyHandler;

    public ChargingBaseBlockEntity(BlockPos pos, BlockState state) {
        super(NuminaBlockEntities.CHARGING_BASE_BLOCK_ENTITY.get(), pos, state);
        itemHandler = new ItemStackHandler(SLOT_COUNT) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return stack.getCapability(Capabilities.EnergyStorage.ITEM) != null;
            }
        };

        internalEnergyHandler = new ExtendedEnergyStorage() {
            @Override
            public int getMaxEnergyStored() {
                return NuminaCommonConfig.chargingBaseMaxEnergy;
            }

            @Override
            public int getMaxTransfer() {
                return NuminaCommonConfig.chargingBaseMaxTransfer;
            }
        };

        energyHandler = new BlockEnergyWrapper(internalEnergyHandler, itemHandler) {
            @Override
            public int receiveEnergy(int toReceive, boolean simulate) {
                int recieved = super.receiveEnergy(toReceive, simulate);
                if(recieved > 0 && isDirty()) {
                    setChanged();
                    setDirty(false);
                }

                return recieved;
            }

            @Override
            public int extractEnergy(int toExtract, boolean simulate) {
                int extracted = super.extractEnergy(toExtract, simulate);
                if(extracted > 0 && isDirty()) {
                    setChanged();
                    setDirty(false);
                }

                return extracted;
            }
        };
    }

    /**
     * Fetch the entities within a given position
     */
    public List<LivingEntity> getEntities() {
        assert level != null;
        return level.getEntitiesOfClass(LivingEntity.class, new AABB(this.worldPosition), entity -> entity instanceof LivingEntity);
    }

    public void tickServer() {
        for (LivingEntity entity : getEntities()) {
            distributeEnergy(entity);
        }
    }

    private void distributeEnergy(LivingEntity entity) {
        int energyStored = getStoredPower();

        int given = (int) ElectricItemUtils.givePlayerEnergy(entity, energyStored, false);
        if (given > 0) {
            energyHandler.extractEnergy(given, false);
            setChanged();
        }
    }

    public ItemStackHandler getItems() {
        return itemHandler;
    }

    public int getStoredPower() {
        return energyHandler.getEnergyStored();
    }

    public int getMaxPower() {
        return energyHandler.getMaxEnergyStored();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.put(ITEMS_TAG, itemHandler.serializeNBT(provider));
        tag.put(NuminaConstants.ENERGY_TAG, internalEnergyHandler.serializeNBT(provider));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if (tag.contains(ITEMS_TAG)) {
            itemHandler.deserializeNBT(provider, tag.getCompound(ITEMS_TAG));
        }
        if (tag.contains(NuminaConstants.ENERGY_TAG)) {
            internalEnergyHandler.deserializeNBT(provider, Objects.requireNonNull(tag.get(NuminaConstants.ENERGY_TAG)));
        }
    }

    public IItemHandler getItemHandler() {
        return itemHandler;
    }

    public IEnergyStorage getEnergyHandler() {
        return energyHandler;
    }
}
