package com.lehjr.powersuits.common.item.module.energy.generation.solar;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.HeatUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.jetbrains.annotations.NotNull;

public class SolarGeneratorModule extends AbstractPowerModule {

    public class SolarGeneratorTickingCapability extends PlayerTickModule {
        int tier;
        public SolarGeneratorTickingCapability(ItemStack module, int tier) {
            super(module, ModuleCategory.ENERGY_GENERATION, ModuleTarget.HEADONLY);
            this.tier = tier;

            //                addBaseProperty(MPSConstants.ENERGY_GENERATION_DAY, 15000, "FE");
            //                addBaseProperty(MPSConstants.ENERGY_GENERATION_NIGHT, 1500, "FE");

            //                addBaseProperty(MPSConstants.ENERGY_GENERATION_DAY, 45000, "FE");
            //                addBaseProperty(MPSConstants.ENERGY_GENERATION_NIGHT, 1500, "FE");
            //                addBaseProperty(MPSConstants.HEAT_GENERATION_DAY, 15);
            //                addBaseProperty(MPSConstants.HEAT_GENERATION_NIGHT, 5);
        }


        @Override
        public boolean isAllowed() {
            return true; // FIXME
        }

        @Override
        public void onPlayerTickActive(Player player, Level level, @NotNull ItemStack item) {
            Level world = player.level();
            boolean isRaining, canRain = true;
            if (world.getGameTime() % 20 == 0) {
                canRain = world.getBiome(player.blockPosition()).value().hasPrecipitation();
            }
            isRaining = canRain && (world.isRaining() || world.isThundering());
            boolean sunVisible = world.isDay() && !isRaining && world.canSeeSkyFromBelowWater(player.blockPosition().above());
            boolean moonVisible = !world.isDay() && !isRaining && world.canSeeSkyFromBelowWater(player.blockPosition().above());

            if (!world.isClientSide && world.dimensionType().hasSkyLight() && (world.getGameTime() % 80) == 0) {
                float lightLevelScaled = (world.getBrightness(LightLayer.SKY, player.blockPosition().above()) - world.getSkyDarken())/15F;

                if (sunVisible) {
                    ElectricItemUtils.givePlayerEnergy(player, (int) (applyPropertyModifiers(MPSConstants.ENERGY_GENERATION_DAY) * lightLevelScaled), false);
                    HeatUtils.heatPlayer(player, applyPropertyModifiers(MPSConstants.HEAT_GENERATION_DAY) * lightLevelScaled / 2);
                } else if (moonVisible) {
                    ElectricItemUtils.givePlayerEnergy(player, (int) (applyPropertyModifiers(MPSConstants.ENERGY_GENERATION_NIGHT) * lightLevelScaled), false);
                    HeatUtils.heatPlayer(player, applyPropertyModifiers(MPSConstants.HEAT_GENERATION_NIGHT) * lightLevelScaled / 2);
                }
            }
        }
    }
}
