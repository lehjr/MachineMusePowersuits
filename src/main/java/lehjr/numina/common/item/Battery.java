package lehjr.numina.common.item;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class Battery extends Item {
    public Battery(int tier) {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return NuminaCapabilities.getCapability(stack, Capabilities.EnergyStorage.ITEM).map(iEnergyStorage -> iEnergyStorage.getMaxEnergyStored() > 0).orElse(false);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        // prevent integer overflow by using double in calculation
        double retVal = NuminaCapabilities.getCapability(stack, Capabilities.EnergyStorage.ITEM).map(iEnergyStorage -> iEnergyStorage.getEnergyStored() * 13.0D / iEnergyStorage.getMaxEnergyStored()).orElse(1D);
        return (int)retVal;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy == null) {
            return super.getBarColor(stack);
        }
        return Mth.hsvToRgb(Math.max(0.0F, (float) energy.getEnergyStored() / (float) energy.getMaxEnergyStored()) / 3.0F, 1.0F, 1.0F);
    }
}
