package com.github.lehjr.powersuits.item.tool;

import com.github.lehjr.numina.util.string.AdditionalInfo;
import com.github.lehjr.powersuits.capability.PowerFistCap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class AbstractElectricTool extends ToolItem {
    public AbstractElectricTool(Item.Properties properties) {
        super(0.0F,
                0.0F,
                MPAToolMaterial.EMPTY_TOOL,
                new HashSet<>(),
                properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (worldIn != null) {
            AdditionalInfo.addInformation(stack, worldIn, tooltip, flagIn);
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new PowerFistCap(stack);
    }

    /** Durability bar for showing energy level ------------------------------------------------------------------ */
    @Override
    public boolean showDurabilityBar(final ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY)
                .map( energyCap-> energyCap.getMaxEnergyStored() > 0).orElse(false);
    }

    @Override
    public double getDurabilityForDisplay(final ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY)
                .map( energyCap-> 1 - energyCap.getEnergyStored() / (double) energyCap.getMaxEnergyStored()).orElse(1D);
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {

    }

    @Override
    public boolean isDamageable() {
        return false;
    }
}