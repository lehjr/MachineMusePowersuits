package com.github.lehjr.powersuits.item.module.special;

import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class InvisibilityModule extends AbstractPowerModule {
    private final Effect invisibility = Effects.INVISIBILITY;

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IPlayerTickModule ticker;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, EnumModuleCategory.SPECIAL, EnumModuleTarget.TORSOONLY, MPSSettings::getModuleConfig);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap instanceof IToggleableModule) {
                ((IToggleableModule) cap).updateFromNBT();
            }
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> ticker));
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, false);
            }

            @Override
            public void onPlayerTickActive(PlayerEntity player, ItemStack item) {
                double totalEnergy = ElectricItemUtils.getPlayerEnergy(player);
                EffectInstance invis = null;
                if (player.isPotionActive(invisibility)) {
                    invis = player.getActivePotionEffect(invisibility);
                }
                if (50 < totalEnergy) {
                    if (invis == null || invis.getDuration() < 210) {
                        player.addPotionEffect(new EffectInstance(invisibility, 500, -3, false, false));
                        ElectricItemUtils.drainPlayerEnergy(player, 50);
                    }
                } else {
                    onPlayerTickInactive(player, item);
                }
            }

            @Override
            public void onPlayerTickInactive(PlayerEntity player, ItemStack item) {
                EffectInstance invis = null;
                if (player.isPotionActive(invisibility)) {
                    invis = player.getActivePotionEffect(invisibility);
                }
                if (invis != null && invis.getAmplifier() == -3) {
                    if (player.world.isRemote) {
                        player.removeActivePotionEffect(invisibility);
                    } else {
                        player.removePotionEffect(invisibility);
                    }
                }
            }
        }
    }
}