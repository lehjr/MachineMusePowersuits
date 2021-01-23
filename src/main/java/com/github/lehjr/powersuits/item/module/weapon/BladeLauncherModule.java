package com.github.lehjr.powersuits.item.module.weapon;

import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import com.github.lehjr.numina.util.capabilities.module.rightclick.RightClickModule;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.entity.SpinningBladeEntity;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class BladeLauncherModule extends AbstractPowerModule {
    public BladeLauncherModule() {
        super();
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IRightClickModule rightClickie;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.rightClickie = new RightClickie(module, EnumModuleCategory.WEAPON, EnumModuleTarget.TOOLONLY, MPSSettings::getModuleConfig);
            this.rightClickie.addBaseProperty(MPSConstants.BLADE_ENERGY, 5000, "FE");
            this.rightClickie.addBaseProperty(MPSConstants.BLADE_DAMAGE, 6, "pt");
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(()-> rightClickie));
        }

        class RightClickie extends RightClickModule {
            public RightClickie(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public ActionResult onItemRightClick(ItemStack itemStackIn, World worldIn, PlayerEntity playerIn, Hand hand) {
                if (hand == Hand.MAIN_HAND) {
                    if (ElectricItemUtils.getPlayerEnergy(playerIn) > applyPropertyModifiers(MPSConstants.BLADE_ENERGY)) {
                        playerIn.setActiveHand(hand);
                        return new ActionResult(ActionResultType.SUCCESS, itemStackIn);
                    }
                }
                return new ActionResult(ActionResultType.PASS, itemStackIn);
            }

            @Override
            public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
                if (!worldIn.isRemote) {
                   int energyConsumption = getEnergyUsage();

                    if (ElectricItemUtils.getPlayerEnergy(entityLiving) > energyConsumption) {
                        ElectricItemUtils.drainPlayerEnergy(entityLiving, energyConsumption);
                        SpinningBladeEntity blade = new SpinningBladeEntity(worldIn, entityLiving);
                        worldIn.addEntity(blade);
                    }
                }
            }

            @Override
            public int getEnergyUsage() {
                return (int) Math.round(applyPropertyModifiers(MPSConstants.BLADE_ENERGY));
            }
        }
    }
}