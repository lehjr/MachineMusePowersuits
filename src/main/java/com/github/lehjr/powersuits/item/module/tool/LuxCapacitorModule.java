package com.github.lehjr.powersuits.item.module.tool;

import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import com.github.lehjr.numina.util.capabilities.module.rightclick.RightClickModule;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.numina.util.heat.MuseHeatUtils;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.entity.LuxCapacitorEntity;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class LuxCapacitorModule extends AbstractPowerModule {
    public LuxCapacitorModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IRightClickModule rightClick;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.rightClick = new RightClickie(module, EnumModuleCategory.TOOL, EnumModuleTarget.TOOLONLY, MPSSettings::getModuleConfig);
            this.rightClick.addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 1000, "FE");
            this.rightClick.addTradeoffProperty(MPSConstants.RED, MPSConstants.RED_HUE, 1, "%");
            this.rightClick.addTradeoffProperty(MPSConstants.GREEN, MPSConstants.GREEN_HUE, 1, "%");
            this.rightClick.addTradeoffProperty(MPSConstants.BLUE, MPSConstants.BLUE_HUE, 1, "%");
            this.rightClick.addTradeoffProperty(MPSConstants.ALPHA, MPSConstants.OPACITY, 1, "%");
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> rightClick));
        }

        class RightClickie extends RightClickModule {
            public RightClickie(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public ActionResult onItemRightClick(ItemStack itemStackIn, World worldIn, PlayerEntity playerIn, Hand hand) {
                float energyConsumption = getEnergyUsage();
                if (ElectricItemUtils.getPlayerEnergy(playerIn) > energyConsumption) {
                    if (!worldIn.isRemote) {
                        MuseHeatUtils.heatPlayer(playerIn, energyConsumption / 500);

                        ElectricItemUtils.drainPlayerEnergy(playerIn, (int) energyConsumption);

                        float red = (float) applyPropertyModifiers(MPSConstants.RED_HUE);
                        float green = (float) applyPropertyModifiers(MPSConstants.GREEN_HUE);
                        float blue = (float) applyPropertyModifiers(MPSConstants.BLUE_HUE);
                        float alpha = (float) applyPropertyModifiers(MPSConstants.OPACITY);

                        LuxCapacitorEntity luxCapacitor = new LuxCapacitorEntity(worldIn, playerIn, new Colour(red, green, blue, alpha));
                        worldIn.addEntity(luxCapacitor);
                    }
                    return ActionResult.resultSuccess(itemStackIn);
                }
                return ActionResult.resultPass(itemStackIn);
            }

            @Override
            public int getEnergyUsage() {
                return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }
        }
    }
}