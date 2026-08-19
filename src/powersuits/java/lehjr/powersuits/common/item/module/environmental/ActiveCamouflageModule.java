package lehjr.powersuits.common.item.module.environmental;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.config.module.EnvironmentalModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ActiveCamouflageModule extends AbstractPowerModule {
    private static final Holder<MobEffect> invisibility = MobEffects.INVISIBILITY;

    public static class Ticker extends PlayerTickModule {
        public Ticker(ItemStack module) {
            super(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION_BASE, EnvironmentalModuleConfig.activeCamouflageModuleEnergyConsumptionBase, "FE");
        }

        @Override
        public boolean isAllowed() {
            return EnvironmentalModuleConfig.activeCamouflageModuleIsAllowed;
        }

        @Override
        public boolean onPlayerTickActive(Player player, Level level, ItemStack item, int moduleIndex) {
            double totalEnergy = ElectricItemUtils.getPlayerEnergy(player);
            MobEffectInstance invis = null;
            if (player.hasEffect(invisibility)) {
                invis = player.getEffect(invisibility);
            }
            int energyUsage = getEnergyUsage();
            if (energyUsage < totalEnergy) {
                if (invis == null || invis.getDuration() < 210) {
                    player.addEffect(new MobEffectInstance(invisibility, 500, -3, false, false));
                    ElectricItemUtils.drainPlayerEnergy(player, energyUsage, false);
                }
            } else {
                onPlayerTickInactive(player, level, item,  moduleIndex);
            }
            return false;
        }

        @Override
        public boolean onPlayerTickInactive(Player player, Level level, ItemStack item,  int moduleIndex) {
            MobEffectInstance invis = null;
            if (player.hasEffect(invisibility)) {
                invis = player.getEffect(invisibility);
            }
            if (invis != null && invis.getAmplifier() == -3) {
                if (level.isClientSide) {
                    player.removeEffectNoUpdate(invisibility);
                } else {
                    player.removeEffect(invisibility);
                }
            }
            return false;
        }

        @Override
        public int getEnergyUsage() {
            return (int)applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }
    }
}
