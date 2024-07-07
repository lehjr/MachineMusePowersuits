package lehjr.powersuits.common.item.module.special;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class InvisibilityModule extends AbstractPowerModule {
    private final Holder<MobEffect> invisibility = MobEffects.INVISIBILITY;

    class Ticker extends PlayerTickModule {
        public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target) {
            super(module, category, target);
//            addBaseProperty(MPSConstants.ACTIVE_CAMOUFLAGE_ENERGY, 100, "FE");
        }

        @Override
        public void onPlayerTickActive(Player player, ItemStack item) {
            double totalEnergy = ElectricItemUtils.getPlayerEnergy(player);
            MobEffectInstance invis = null;
            if (player.hasEffect(invisibility)) {
                invis = player.getEffect(invisibility);
            }
            if (50 < totalEnergy) {
                if (invis == null || invis.getDuration() < 210) {
                    player.addEffect(new MobEffectInstance(invisibility, 500, -3, false, false));
                    ElectricItemUtils.drainPlayerEnergy(player, 50, false);
                }
            } else {
                onPlayerTickInactive(player, item);
            }
        }

        @Override
        public void onPlayerTickInactive(Player player, ItemStack item) {
            MobEffectInstance invis = null;
            if (player.hasEffect(invisibility)) {
                invis = player.getEffect(invisibility);
            }
            if (invis != null && invis.getAmplifier() == -3) {
                if (player.level().isClientSide) {
                    player.removeEffectNoUpdate(invisibility);
                } else {
                    player.removeEffect(invisibility);
                }
            }
        }
    }
}