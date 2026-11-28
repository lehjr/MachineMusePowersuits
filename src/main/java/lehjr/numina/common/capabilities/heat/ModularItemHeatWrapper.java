package lehjr.numina.common.capabilities.heat;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.TagUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

public class ModularItemHeatWrapper extends HeatStorage {
    ItemStack stack;
    private final LazyOptional<IHeatStorage> holder = LazyOptional.of(() -> this);

    public ModularItemHeatWrapper(ItemStack stack, double capacity) {
        this(stack, capacity, capacity, capacity);
    }

    public ModularItemHeatWrapper(ItemStack stack, double capacity, double maxReceive, double maxExtract) {
        super(capacity, maxReceive, maxExtract, 0);
        this.stack = stack;
    }

    @Override
    public double receiveHeat(double maxReceive, boolean simulate) {
        if (!canReceive()) {
            return 0;
        }

        double heatReceived = Math.min(getMaxHeatStored() - getHeatStored(), Math.min(getMaxHeatTransfer(), maxReceive));
        if (!simulate && heatReceived !=0) {
            heat += heatReceived;
            onValueChanged();
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
            onValueChanged();
        }
        return heatExtracted;
    }

    @Override
    public double getMaxHeatStored() {
        return super.getMaxHeatStored() + getModuleMaxHeat();
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

    @Override
    public void loadCapValues() {
        heat = Math.min(capacity, TagUtils.getModularItemDouble(stack, TagConstants.HEAT));
    }

    @Override
    public void onValueChanged() {
        TagUtils.setModularItemDouble(stack, TagConstants.HEAT, heat);
    }

    public double getModuleMaxHeat() {
        IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(this.stack);
        double moduleCapMaxHeat = 0;
        if (iModularItem != null) {
            for (int i=0; i < iModularItem.getSlots(); i++) {
                ItemStack module = iModularItem.getStackInSlot(i);
                IPowerModule pm = module.getCapability(NuminaCapabilities.Module.POWER_MODULE).resolve().orElse(null);
                if(pm != null) {
                    moduleCapMaxHeat += pm.applyPropertyModifiers(NuminaConstants.MAXIMUM_HEAT);
                }
            }
        }
        return moduleCapMaxHeat;
    }
}
