package com.lehjr.powersuits.common.item.module.vision;

import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.TagUtils;
import com.lehjr.powersuits.common.config.VisionModuleConfig;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.CommonHooks;

import javax.annotation.Nonnull;

public class NightVisionModule extends AbstractPowerModule {
    static final String ADDED = "added";
    static final String REMOVED = "removed";

    public static class Ticker extends PlayerTickModule {
        boolean added;
        boolean removed;

        public Ticker(@Nonnull ItemStack module) {
            super(module, ModuleCategory.VISION, ModuleTarget.HEADONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, VisionModuleConfig.nightVisionEnergyConsumption, "FE");
            // setting to and loading these just allow values to be persistant when capability reloads
            added = TagUtils.getModuleBoolean(module, ADDED);
            removed = TagUtils.getModuleBoolean(module, REMOVED);
        }

        int tick = 0; // cooldown timer because firing too quickly will crash things and this doesn't need to fire that rapidly anyway

        @Override
        public boolean isAllowed() {
            return VisionModuleConfig.nightVisionModuleIsAllowed;
        }

        @Override
        public void onPlayerTickActive(Player player, Level level, @Nonnull ItemStack item) {
            if (level.isClientSide) {
                return;
            }
            double powerDrain = applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);

            double totalEnergy = ElectricItemUtils.getPlayerEnergy(player);
            MobEffectInstance nightVisionEffect = player.hasEffect(MobEffects.NIGHT_VISION) ? player.getEffect(MobEffects.NIGHT_VISION) : null;
            if (totalEnergy > powerDrain) {
                if (nightVisionEffect == null || nightVisionEffect.getDuration() < 500) {
                    MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, false, false);
                    if (CommonHooks.canMobEffectBeApplied(player, mobEffectInstance)) { // is this check needed?
                        if (player.addEffect(mobEffectInstance)) {
                            IModularItem modularItem = NuminaCapabilities.getModularItem(item);
                            if (modularItem != null) {
                                modularItem.setModuleBoolean(MPSConstants.NIGHT_VISION_MODULE, ADDED, true);
                                modularItem.setModuleBoolean(MPSConstants.NIGHT_VISION_MODULE, REMOVED, false);
                            }
                            ElectricItemUtils.drainPlayerEnergy(player, powerDrain, false);
                        }
                    }
                }
            } else {
                onPlayerTickInactive(player, level, item);
            }
        }

        @Override
        public void onPlayerTickInactive(Player player, Level level, @Nonnull ItemStack item) {
            // there doesn't seem to be any way to immediately remove effect.
            if (added && !removed && player.hasEffect(MobEffects.NIGHT_VISION)) {
                player.removeEffect(MobEffects.NIGHT_VISION);
                IModularItem modularItem = NuminaCapabilities.getModularItem(item);
                if (modularItem != null) {
                    modularItem.setModuleBoolean(MPSConstants.NIGHT_VISION_MODULE, ADDED, false);
                    modularItem.setModuleBoolean(MPSConstants.NIGHT_VISION_MODULE, REMOVED, true);
                }
            }
        }
    }
}