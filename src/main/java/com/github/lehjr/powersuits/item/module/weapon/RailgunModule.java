package com.github.lehjr.powersuits.item.module.weapon;

import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import com.github.lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.numina.util.heat.MuseHeatUtils;
import com.github.lehjr.numina.util.math.MuseMathUtils;
import com.github.lehjr.numina.util.nbt.MuseNBTUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.entity.RailgunBoltEntity;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class RailgunModule extends AbstractPowerModule {
    public RailgunModule() {
    }

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
            this.ticker = new Ticker(module, EnumModuleCategory.WEAPON, EnumModuleTarget.TOOLONLY, MPSSettings::getModuleConfig);
            this.ticker.addBaseProperty(MPSConstants.RAILGUN_TOTAL_IMPULSE, 500, "Ns");
            this.ticker.addBaseProperty(MPSConstants.RAILGUN_ENERGY_COST, 5000, "FE");
            this.ticker.addBaseProperty(MPSConstants.RAILGUN_HEAT_EMISSION, 2, "");
            this.ticker.addTradeoffProperty(MPSConstants.VOLTAGE, MPSConstants.RAILGUN_TOTAL_IMPULSE, 2500);
            this.ticker.addTradeoffProperty(MPSConstants.VOLTAGE, MPSConstants.RAILGUN_ENERGY_COST, 25000);
            this.ticker.addTradeoffProperty(MPSConstants.VOLTAGE, MPSConstants.RAILGUN_HEAT_EMISSION, 10);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> ticker));
        }

        class Ticker extends PlayerTickModule implements IRightClickModule {

            public Ticker(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, true);
            }

            @Override
            public void onPlayerTickActive(PlayerEntity player, @Nonnull ItemStack itemStackIn) {
                double timer = MuseNBTUtils.getModularItemDoubleOrZero(itemStackIn, MPSConstants.TIMER);
                if (timer > 0) {
                    MuseNBTUtils.setModularItemDoubleOrRemove(itemStackIn, MPSConstants.TIMER, timer - 1 > 0 ? timer - 1 : 0);
                }
            }

            @Override
            public ActionResult onItemRightClick(ItemStack itemStackIn, World worldIn, PlayerEntity playerIn, Hand hand) {
                if (hand == Hand.MAIN_HAND && ElectricItemUtils.getPlayerEnergy(playerIn) > getEnergyUsage()) {
                    playerIn.setActiveHand(hand);
                    return ActionResult.resultSuccess(itemStackIn);
                }
                return ActionResult.resultPass(itemStackIn);
            }

            @Override
            // from bow, since bow launches correctly each time
            public void onPlayerStoppedUsing(ItemStack itemStack, World worldIn, LivingEntity entityLiving, int timeLeft) {
                int chargeTicks = (int) MuseMathUtils.clampDouble(itemStack.getUseDuration() - timeLeft, 10, 50);
                if (!worldIn.isRemote && entityLiving instanceof PlayerEntity) {
                    double chargePercent = chargeTicks * 0.02; // chargeticks/50
                    double energyConsumption = getEnergyUsage() * chargePercent;
                    double timer = MuseNBTUtils.getModularItemDoubleOrZero(itemStack, MPSConstants.TIMER);

                    // TODO: replace with code similar to plasma_ball ... spawn... direction... velocity...
                    if (!worldIn.isRemote && ElectricItemUtils.getPlayerEnergy(entityLiving) > energyConsumption && timer == 0) {
                        MuseNBTUtils.setModularItemDoubleOrRemove(itemStack, MPSConstants.TIMER, 10);
                        PlayerEntity playerentity = (PlayerEntity)entityLiving;

                        double velocity = applyPropertyModifiers(MPSConstants.RAILGUN_TOTAL_IMPULSE) * chargePercent;
                        double damage = velocity * 0.01; // original: impulse / 100.0
                        double knockback = damage * 0.05; // original: damage / 20.0;

                        RailgunBoltEntity bolt = new RailgunBoltEntity(worldIn, entityLiving, velocity, chargePercent, damage, knockback);

                        // Only run if enntity is added
                        if (worldIn.addEntity(bolt)) {
                            Vector3d lookVec = playerentity.getLookVec();
                            worldIn.playSound(null, entityLiving.getPosX(), entityLiving.getPosY(), entityLiving.getPosZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                            ElectricItemUtils.drainPlayerEnergy(entityLiving, (int) energyConsumption);
                            MuseHeatUtils.heatPlayer(entityLiving, applyPropertyModifiers(MPSConstants.RAILGUN_HEAT_EMISSION) * chargePercent);
                            entityLiving.addVelocity(-lookVec.x * knockback, Math.abs(-lookVec.y + 0.2f) * knockback, -lookVec.z * knockback);
                        } else {
                            System.out.println("bolt not added");
                        }
                    }
                }
            }

            @Override
            public int getEnergyUsage() {
                return (int) Math.round(applyPropertyModifiers(MPSConstants.RAILGUN_ENERGY_COST));
            }
        }
    }
}