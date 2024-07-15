package lehjr.numina.common.capability.heat;

import com.google.common.util.concurrent.AtomicDouble;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.utils.TagUtils;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public abstract class AbstractModularItemHeatWrapper implements IHeatStorage {
    private final ItemStack itemStack;
    public final int tier;
    double heat = 0;
    double capacity = 0;

    public AbstractModularItemHeatWrapper(@Nonnull ItemStack itemStack, int tier) {
        this.itemStack = itemStack;
        this.tier = tier;
        this.heat = TagUtils.getHeat(itemStack);
        // since this is constantly being reloaded, this should be fine, I think
        this.capacity = getBaseMaxHeat(itemStack, tier) + getModuleMaxHeat();
    }

    @Override
    public double receiveHeat(double maxReceive, boolean simulate) {
        if (!canReceive()) {
            return 0;
        }

        double heatReceived = Math.min(getMaxHeatStored() - getHeatStored(), Math.min(getMaxHeatTransfer(), maxReceive));
        if (!simulate && heatReceived !=0) {
            heat += heatReceived;
            TagUtils.setHeat(itemStack, heat);
        }
        return heatReceived;
    }

    @Override
    public double extractHeat(double maxExtract, boolean simulate) {
        if (!canExtract()) {
            return 0;
        }

        double heatExtracted = Math.min(heat, maxExtract);
        if (!simulate && heatExtracted != 0) {
            heat -= heatExtracted;
            TagUtils.setHeat(itemStack, heat);
        }
        return heatExtracted;
    }

    @Override
    public double getHeatStored() {
        return heat;
    }

    @Override
    public double getMaxHeatStored() {
        return capacity;
    }

    @Override
    public double getMaxHeatTransfer() {
        return getMaxHeatStored();
    }

    @Override
    public boolean canExtract() {
        return this.heat > 0 && getMaxHeatTransfer() > 0;
    }

    @Override
    public boolean canReceive() {
        return getMaxHeatTransfer() > 0;
    }

    public abstract double getBaseMaxHeat(ItemStack itemStack, int tier);

    public double getModuleMaxHeat() {
        AtomicDouble moduleMaxHeat = new AtomicDouble(0);
        NuminaCapabilities.getModularItemCapability(this.itemStack).ifPresent(iModularItem -> {
            for (int i=0; i < iModularItem.getSlots(); i++) {
                ItemStack module = iModularItem.getStackInSlot(i);
                NuminaCapabilities.getPowerModuleCapability(module).ifPresent(m-> {
                    double moduleCapMaxHeat = m.applyPropertyModifiers(NuminaConstants.MAXIMUM_HEAT);
                    if (moduleCapMaxHeat > 0) {
                        moduleMaxHeat.getAndAdd(moduleCapMaxHeat);
                    }
                });
            }
        });
        return moduleMaxHeat.get();
    }
}
