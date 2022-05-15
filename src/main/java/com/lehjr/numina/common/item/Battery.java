package com.lehjr.numina.common.item;

import com.lehjr.numina.common.string.AdditionalInfo;
import com.lehjr.numina.common.base.NuminaObjects;
import com.lehjr.numina.common.capabilities.BatteryCapabilityProvider;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class Battery extends Item {
    /**
     * these values are only used during initialization to set up the config.
     * They can be overridden in the config.
     */
    protected int maxEnergy;
    protected int maxTransfer;

    protected final int tier;

    public Battery(int maxEnergy, int maxTransfer, int tier) {
        super(new Item.Properties()
                .stacksTo(1)
                .tab(NuminaObjects.creativeTab)
                .defaultDurability(-1)
                .setNoRepair());
        this.maxEnergy = maxEnergy;
        this.maxTransfer = maxTransfer;
        this.tier = tier;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag) {
        if (level != null) {
            super.appendHoverText(itemStack, level, tooltips, flag);
            AdditionalInfo.appendHoverText(itemStack, level, tooltips, flag);
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new BatteryCapabilityProvider(stack, tier, maxEnergy, maxTransfer);
    }

    @Override
    public void fillItemCategory(@Nonnull CreativeModeTab group, @Nonnull NonNullList<ItemStack> items) {
        super.fillItemCategory(group, items);
        if (allowdedIn(group)) {
            ItemStack out = new ItemStack(this);
            BatteryCapabilityProvider provider = new BatteryCapabilityProvider(out, tier, maxEnergy, maxTransfer);
            provider.setMaxEnergy();
            items.add(out);
        }
    }

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
}
