package com.lehjr.powersuits.common.item.electric.tool;

import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.AdditionalInfo;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.List;

//public class AbstractElectricTool extends DiggerItem {
//    protected AbstractElectricTool(Tier pTier, TagKey<Block> pBlocks, Properties pProperties) {
//        super(pTier, pBlocks, pProperties);
//    }
    public class AbstractElectricTool extends Item {
        protected AbstractElectricTool(Tier pTier, TagKey<Block> pBlocks, Properties pProperties) {
            super(pProperties);
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

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);
        AdditionalInfo.appendHoverText(stack, context, components, flag, Screen.hasShiftDown());
    }

    /**
     * Checks module first and falls back to default value of 72000
     * @param stack
     * @return
     */
    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(stack);
        if(mci != null) {
            return Math.min(mci.getUseDuration(entity), 7200);
        }
        return 7200;
    }

    /** Durability bar for showing energy level ------------------------------------------------------------------ */
    @Override
    public boolean isBarVisible(ItemStack stack) {
         return stack.getCapability(Capabilities.EnergyStorage.ITEM) != null;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        double retVal = 1;
        IEnergyStorage iEnergyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if(iEnergyStorage != null) {
            retVal =  iEnergyStorage.getEnergyStored() * 13D / iEnergyStorage.getMaxEnergyStored();
        }
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
