package com.github.lehjr.powersuits.item.module.vision;

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

public class NightVisionModule extends AbstractPowerModule {
    static final int powerDrain = 50;
    private static final Effect nightvision = Effects.NIGHT_VISION;

    public NightVisionModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities (ItemStack stack, @Nullable CompoundNBT nbt){
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IPlayerTickModule ticker;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, EnumModuleCategory.VISION, EnumModuleTarget.HEADONLY, MPSSettings::getModuleConfig);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if(cap instanceof IToggleableModule) {
                ((IToggleableModule) cap).updateFromNBT();
            }
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> ticker));
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, true);
            }

            @Override
            public void onPlayerTickActive(PlayerEntity player, ItemStack item) {
                if (player.world.isRemote)
                    return;

                double totalEnergy = ElectricItemUtils.getPlayerEnergy(player);
                EffectInstance nightVisionEffect = player.isPotionActive(nightvision) ? player.getActivePotionEffect(nightvision) : null;

                if (totalEnergy > powerDrain) {
                    if (nightVisionEffect == null || nightVisionEffect.getDuration() < 250 && nightVisionEffect.getAmplifier() == -3) {
                        player.addPotionEffect(new EffectInstance(nightvision, 500, -3, false, false));
                        ElectricItemUtils.drainPlayerEnergy(player, powerDrain);
                    }
                } else
                    onPlayerTickInactive(player, item);
            }

            @Override
            public void onPlayerTickInactive(PlayerEntity player, ItemStack item) {
                EffectInstance nightVisionEffect = null;
                if (player.isPotionActive(nightvision)) {
                    nightVisionEffect = player.getActivePotionEffect(nightvision);
                    if (nightVisionEffect.getAmplifier() == -3) {
                        player.removePotionEffect(nightvision);
                    }
                }
            }
        }
    }
}