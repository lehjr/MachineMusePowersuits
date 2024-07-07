package lehjr.powersuits.common.item.module.vision;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.TagUtils;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class NightVisionModule extends AbstractPowerModule {

    public static class Ticker extends PlayerTickModule {
        boolean added;
        boolean removed;

        public Ticker(@Nonnull ItemStack module) {
            super(module, ModuleCategory.VISION, ModuleTarget.HEADONLY);

            // setting to and loading these just allow values to be persistant when capability reloads
            added = TagUtils.getModuleBoolean(module, "added");
            removed = TagUtils.getModuleBoolean(module, "removed");
        }

        int tick = 0; // cooldown timer because firing too quickly will crash things and this doesn't need to fire that rapidly anyway

        void setAdded(boolean added) {
            this.added = added;
            TagUtils.setModuleBoolean(getModule(), "added", added);
        }

        void setRemoved(boolean removed) {
            this.removed = removed;
            TagUtils.setModuleBoolean(getModule(), "removed", removed);
        }

        @Override
        public void onPlayerTickActive(Player player, ItemStack item) {
            if (player.level().isClientSide) {
                return;
            }
            double powerDrain = applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);

            double totalEnergy = ElectricItemUtils.getPlayerEnergy(player);
            MobEffectInstance nightVisionEffect = player.hasEffect(MobEffects.NIGHT_VISION) ? player.getEffect(MobEffects.NIGHT_VISION) : null;
            if (totalEnergy > powerDrain) {
                if (nightVisionEffect == null || nightVisionEffect.getDuration() < 500) {
                    MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, false, false);
                    if (player.canBeAffected(mobEffectInstance)) { // is this check needed?
                        if (player.addEffect(mobEffectInstance)) {
                            setAdded(true);
                            setRemoved(false);
                            ElectricItemUtils.drainPlayerEnergy(player, powerDrain, false);
                        }
                    }
                }
            } else {
                onPlayerTickInactive(player, item);
            }
        }

        @Override
        public void onPlayerTickInactive(Player player, ItemStack item) {
            // there doesn't seem to be any way to immediately remove effect.
            if (added && !removed && player.hasEffect(MobEffects.NIGHT_VISION)) {
                player.removeEffect(MobEffects.NIGHT_VISION);
                setAdded(false);
                setRemoved(true);
            }
        }
    }
}