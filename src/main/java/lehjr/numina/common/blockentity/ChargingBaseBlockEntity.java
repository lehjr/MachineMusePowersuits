package lehjr.numina.common.blockentity;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.capability.energy.BlockEnergyWrapper;
import lehjr.numina.common.capability.energy.ExtendedEnergyStorage;
import lehjr.numina.common.config.NuminaCommonConfig;
import lehjr.numina.common.constants.NuminaConstants;
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
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class ChargingBaseBlockEntity extends BlockEntity {

    public static final String ITEMS_TAG = "Inventory";

    public static int SLOT_COUNT = 1;
    public static int SLOT = 0;

    private final ItemStackHandler items = createItemHandler();
    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> items);

    public ChargingBaseBlockEntity(BlockPos pos, BlockState state) {
        super(NuminaObjects.CHARGING_BASE_BLOCK_ENTITY.get(), pos, state);
    }

    private final ExtendedEnergyStorage energy = createEnergyStorage();
    private final Lazy<IEnergyStorage> energyHandler = Lazy.of(() -> new BlockEnergyWrapper(energy, items));

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
        int received = (int) ElectricItemUtils.givePlayerEnergy(entity, getEnergyHandler().getEnergyStored(), false);
        if (received > 0) {
            getEnergyHandler().extractEnergy(received, false);
            setChanged();
        }
    }

    public ItemStackHandler getItems() {
        return items;
    }

    public int getStoredPower() {
        return getEnergyHandler().getEnergyStored();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.put(ITEMS_TAG, items.serializeNBT(provider));
        tag.put(NuminaConstants.ENERGY_TAG, energy.serializeNBT(provider));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if (tag.contains(ITEMS_TAG)) {
            items.deserializeNBT(provider, tag.getCompound(ITEMS_TAG));
        }
        if (tag.contains(NuminaConstants.ENERGY_TAG)) {
            energy.deserializeNBT(provider, Objects.requireNonNull(tag.get(NuminaConstants.ENERGY_TAG)));
        }
    }

    @Nonnull
    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(SLOT_COUNT) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getCapability(Capabilities.EnergyStorage.ITEM) != null;
            }
        };
    }

    @Nonnull
    private ExtendedEnergyStorage createEnergyStorage() {
        return new ExtendedEnergyStorage() {
            @Override
            public int getMaxEnergyStored() {
                return NuminaCommonConfig.chargingBaseMaxEnergy;
            }

            @Override
            public int getMaxTransfer() {
                return NuminaCommonConfig.chargingBaseMaxTransfer;
            }
        };
    }

    public IItemHandler getItemHandler() {
        return itemHandler.get();
    }

    public IEnergyStorage getEnergyHandler() {
        return energyHandler.get();
    }
}
