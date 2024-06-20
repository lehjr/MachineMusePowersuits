package lehjr.powersuits.common.item.electric.tool;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class AbstractElectricTool extends DiggerItem {
    protected AbstractElectricTool(Tier pTier, TagKey<Block> pBlocks, Properties pProperties) {
        super(pTier, pBlocks, pProperties);
    }


    @Override
    public void setDamage(ItemStack stack, int damage) {
        // TODO: drain energy?
    }

    // FIXME: find replacement?
//    @Override
//    public boolean canBeDepleted() {
//        return false;
//    }

    /**
     * Checks module first and falls back to default value of 72000
     * @param stack
     * @return
     */
    @Override
    public int getUseDuration(ItemStack stack) {
        return NuminaCapabilities.getCapability(stack, NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
                .map(IModeChangingItem::getUseDuration).orElse(72000);
    }

    /** Durability bar for showing energy level ------------------------------------------------------------------ */
    @Override
    public boolean isBarVisible(ItemStack stack) {
        return NuminaCapabilities.getCapability(stack, Capabilities.EnergyStorage.ITEM).map(iEnergyStorage -> iEnergyStorage.getMaxEnergyStored() > 1).orElse(false);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        double retVal = NuminaCapabilities.getCapability(stack, Capabilities.EnergyStorage.ITEM).map(iEnergyStorage -> iEnergyStorage.getEnergyStored() * 13D / iEnergyStorage.getMaxEnergyStored()).orElse(1D);
        return (int) retVal;
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