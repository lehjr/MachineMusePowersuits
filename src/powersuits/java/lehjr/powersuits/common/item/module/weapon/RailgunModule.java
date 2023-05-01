/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.common.item.module.weapon;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.module.powermodule.IConfig;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.numina.common.heat.HeatUtils;
import lehjr.numina.common.math.MathUtils;
import lehjr.numina.common.tags.TagUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.entity.RailgunBoltEntity;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class RailgunModule extends AbstractPowerModule {

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final Ticker ticker;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, ModuleCategory.WEAPON, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.RAILGUN_TOTAL_IMPULSE, 500, "Ns");
                addBaseProperty(MPSConstants.RAILGUN_ENERGY_COST, 5000, "FE");
                addBaseProperty(MPSConstants.RAILGUN_HEAT_EMISSION, 2, "");
                addTradeoffProperty(MPSConstants.VOLTAGE, MPSConstants.RAILGUN_TOTAL_IMPULSE, 2500);
                addTradeoffProperty(MPSConstants.VOLTAGE, MPSConstants.RAILGUN_ENERGY_COST, 25000);
                addTradeoffProperty(MPSConstants.VOLTAGE, MPSConstants.RAILGUN_HEAT_EMISSION, 10);
            }};

            powerModuleHolder = LazyOptional.of(() -> {
                ticker.loadCapValues();
                return ticker;
            });
        }

        class Ticker extends PlayerTickModule implements IRightClickModule {
            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, true);
            }

            @Override
            public void onPlayerTickActive(Player player, @Nonnull ItemStack itemStackIn) {
                double timer = TagUtils.getModularItemDoubleOrZero(itemStackIn, MPSConstants.TIMER);
                if (timer > 0) {
                    TagUtils.setModularItemDoubleOrRemove(itemStackIn, MPSConstants.TIMER, timer - 1 > 0 ? timer - 1 : 0);
                }
            }

            @Override
            public InteractionResultHolder<ItemStack> use(@NotNull ItemStack itemStackIn, Level worldIn, Player playerIn, InteractionHand hand) {
                if (hand == InteractionHand.MAIN_HAND && ElectricItemUtils.getPlayerEnergy(playerIn) > getEnergyUsage()) {
                    playerIn.startUsingItem(hand);
                    return InteractionResultHolder.success(itemStackIn);
                }
                return InteractionResultHolder.pass(itemStackIn);
            }

            @Override
            // from bow, since bow launches correctly each time
            public void releaseUsing(ItemStack itemStack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
                int chargeTicks = (int) MathUtils.clampDouble(itemStack.getUseDuration() - timeLeft, 10, 50);
                if (!worldIn.isClientSide && entityLiving instanceof Player) {
                    double chargePercent = chargeTicks * 0.02; // chargeticks/50
                    double energyConsumption = getEnergyUsage() * chargePercent;
                    double timer = TagUtils.getModularItemDoubleOrZero(itemStack, MPSConstants.TIMER);

                    // TODO: replace with code similar to plasma_ball ... spawn... direction... velocity...
                    if (!worldIn.isClientSide && ElectricItemUtils.getPlayerEnergy(entityLiving) > energyConsumption && timer == 0) {
                        TagUtils.setModularItemDoubleOrRemove(itemStack, MPSConstants.TIMER, 10);
                        Player playerentity = (Player)entityLiving;

                        double velocity = applyPropertyModifiers(MPSConstants.RAILGUN_TOTAL_IMPULSE) * chargePercent;
                        double damage = velocity * 0.01; // original: impulse / 100.0
                        double knockback = damage * 0.05; // original: damage / 20.0;

                        RailgunBoltEntity bolt = new RailgunBoltEntity(worldIn, entityLiving, velocity, chargePercent, damage, knockback);

                        // Only run if enntity is added
                        if (worldIn.addFreshEntity(bolt)) {
                            Vec3 lookVec = playerentity.getLookAngle();
                            worldIn.playSound(null, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
                            ElectricItemUtils.drainPlayerEnergy(entityLiving, (int) energyConsumption);
                            HeatUtils.heatPlayer(entityLiving, applyPropertyModifiers(MPSConstants.RAILGUN_HEAT_EMISSION) * chargePercent);
                            entityLiving.push(-lookVec.x * knockback, Math.abs(-lookVec.y + 0.2f) * knockback, -lookVec.z * knockback);
                        }
//                        else {
//                            System.out.println("bolt not added");
//                        }
                    }
                }
            }

            @Override
            public int getEnergyUsage() {
                return (int) Math.round(applyPropertyModifiers(MPSConstants.RAILGUN_ENERGY_COST));
            }
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}