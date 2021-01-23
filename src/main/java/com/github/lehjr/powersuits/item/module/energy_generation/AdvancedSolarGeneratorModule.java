package com.github.lehjr.powersuits.item.module.energy_generation;

import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.numina.util.heat.MuseHeatUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

/**
 * Created by Eximius88 on 1/12/14.
 */
public class AdvancedSolarGeneratorModule extends AbstractPowerModule {
    public AdvancedSolarGeneratorModule() {
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
            this.ticker = new Ticker(module, EnumModuleCategory.ENERGY_GENERATION, EnumModuleTarget.HEADONLY, MPSSettings::getModuleConfig);
            this.ticker.addBaseProperty(MPSConstants.ENERGY_GENERATION_DAY, 45000, "FE");
            this.ticker.addBaseProperty(MPSConstants.ENERGY_GENERATION_NIGHT, 1500, "FE");
            this.ticker.addBaseProperty(MPSConstants.HEAT_GENERATION_DAY, 15);
            this.ticker.addBaseProperty(MPSConstants.HEAT_GENERATION_NIGHT, 5);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap instanceof IToggleableModule) {
                ((IToggleableModule) cap).updateFromNBT();
            }
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(()-> ticker));
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, true);
            }

            @Override
            public void onPlayerTickActive(PlayerEntity player, ItemStack itemStack) {
                World world = player.world;
                boolean isRaining, canRain = true;
                if (world.getGameTime() % 20 == 0) {
                    canRain = world.getBiome(player.getPosition()).getPrecipitation() != Biome.RainType.NONE;
                }
                isRaining = canRain && (world.isRaining() || world.isThundering());
                boolean sunVisible = world.isDaytime() && !isRaining && world.canBlockSeeSky(player.getPosition().up());
                boolean moonVisible = !world.isDaytime() && !isRaining && world.canBlockSeeSky(player.getPosition().up());

                if (!world.isRemote && world.getDimensionType().hasSkyLight() && (world.getGameTime() % 80) == 0) {
                    float lightLevelScaled = (world.getLightFor(LightType.SKY, player.getPosition().up()) - world.getSkylightSubtracted())/15F;

                    if (sunVisible) {
                        ElectricItemUtils.givePlayerEnergy(player, (int) (applyPropertyModifiers(MPSConstants.ENERGY_GENERATION_DAY) * lightLevelScaled));
                        MuseHeatUtils.heatPlayer(player, applyPropertyModifiers(MPSConstants.HEAT_GENERATION_DAY) * lightLevelScaled / 2);
                    } else if (moonVisible) {
                        ElectricItemUtils.givePlayerEnergy(player, (int) (applyPropertyModifiers(MPSConstants.ENERGY_GENERATION_NIGHT) * lightLevelScaled));
                        MuseHeatUtils.heatPlayer(player, applyPropertyModifiers(MPSConstants.HEAT_GENERATION_NIGHT) * lightLevelScaled / 2);
                    }
                }
            }
        }
    }
}