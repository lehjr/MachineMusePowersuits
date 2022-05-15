package com.lehjr.numina.common.item;

import com.lehjr.numina.common.string.AdditionalInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbstractElectricTool extends Item {
    public AbstractElectricTool(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        if (level != null) {
            AdditionalInfo.appendHoverText(stack, level, tooltip, flag);
        } else {
            super.appendHoverText(stack, level, tooltip, flag);
        }
    }

    /** Durability bar for showing energy level ------------------------------------------------------------------ */
    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY).map(iEnergyStorage -> iEnergyStorage.getEnergyStored() * 13 / iEnergyStorage.getMaxEnergyStored()).orElse(0);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY).orElse(null);
        if (energy == null) {
            return super.getBarColor(stack);
        }
        return Mth.hsvToRgb(Math.max(0.0F, (float) energy.getEnergyStored() / (float) energy.getMaxEnergyStored()) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {

    }

    @Override
    public boolean canBeDepleted() {
        return false;
    }
}
