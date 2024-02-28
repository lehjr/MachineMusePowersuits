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

package lehjr.powersuits.common.item.module.movement;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.module.powermodule.IConfig;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;

/**
 * Ported by leon on 10/18/16.
 */
public class SprintAssistModule extends AbstractPowerModule {

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
            this.ticker = new Ticker(module, ModuleCategory.MOVEMENT, ModuleTarget.LEGSONLY, MPSSettings::getModuleConfig) {{
                // Sprinting
                addSimpleTradeoff(MPSConstants.SPRINT_ASSIST, MPSConstants.SPRINT_ENERGY_CONSUMPTION, "RF", 0, 5000, MPSConstants.SPRINT_SPEED_MULTIPLIER, "%", 0.1, 2.49);
                // Sprinting Food Compensation
                addSimpleTradeoff(MPSConstants.COMPENSATION, MPSConstants.SPRINT_ENERGY_CONSUMPTION, "RF", 0, 2000, MPSConstants.FOOD_COMPENSATION, "%", 0, 1);
                // Walking
                addSimpleTradeoff(MPSConstants.WALKING_ASSISTANCE, MPSConstants.WALKING_ENERGY_CONSUMPTION, "RF", 0, 5000, MPSConstants.WALKING_SPEED_MULTIPLIER, "%", 0.01, 1.99);
            }};

            powerModuleHolder = LazyOptional.of(() -> {
                ticker.loadCapValues();
                return ticker;
            });
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, true);
            }

            @Override
            public void onPlayerTickActive(Player player, @Nonnull ItemStack itemStack) {
                if (player.getAbilities().flying || player.isPassenger() || player.isFallFlying()) {
                    onPlayerTickInactive(player, itemStack);
                    return;
                }

                double horzMovement = player.walkDist - player.walkDistO;
                double totalEnergy = ElectricItemUtils.getPlayerEnergy(player);

                if (horzMovement > 0) { // stop doing drain calculations when player hasn't moved
                    if (player.isSprinting()) {
                        double exhaustion = Math.round(horzMovement * 100.0F) * 0.01;
                        double sprintCost = applyPropertyModifiers(MPSConstants.SPRINT_ENERGY_CONSUMPTION);
                        if (sprintCost < totalEnergy) {
                            double sprintMultiplier = applyPropertyModifiers(MPSConstants.SPRINT_SPEED_MULTIPLIER);
                            double exhaustionComp = applyPropertyModifiers(MPSConstants.FOOD_COMPENSATION);
                            if (!player.level().isClientSide &&
                                    // every 20 ticks
                                    (player.level().getGameTime() % 20) == 0) {
                                ElectricItemUtils.drainPlayerEnergy(player, (int) (sprintCost * horzMovement) * 20);
                            }
                            setMovementModifier(getModuleStack(), sprintMultiplier * 0.13 * 0.5, Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDescriptionId());
                            player.getFoodData().addExhaustion((float) (-0.01 * exhaustion * exhaustionComp));
                            player.getAbilities().setFlyingSpeed(player.getSpeed() * 0.2f);
                        }
                    } else {
                        double walkCost = applyPropertyModifiers(MPSConstants.WALKING_ENERGY_CONSUMPTION);
                        if (walkCost < totalEnergy) {
                            double walkMultiplier = applyPropertyModifiers(MPSConstants.WALKING_SPEED_MULTIPLIER);
                            if (!player.level().isClientSide &&
                                    // every 20 ticks
                                    (player.level().getGameTime() % 20) == 0) {
                                ElectricItemUtils.drainPlayerEnergy(player, (int) (walkCost * horzMovement));




                            }
                            setMovementModifier(getModuleStack(), walkMultiplier * 0.1, Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDescriptionId());
                            player.getAbilities().setFlyingSpeed(player.getSpeed() * 0.2f);
                        }
                    }
                }
                onPlayerTickInactive(player, itemStack);
            }

            @Override
            public void onPlayerTickInactive(Player player, @Nonnull ItemStack itemStack) {
//                itemStack.removeTagKey("AttributeModifiers");
                setMovementModifier(getModuleStack(), 0, Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDescriptionId());
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

    // moved here so it is still accessible if sprint assist module isn't installed.
    public static void setMovementModifier(ItemStack itemStack, double multiplier, Attribute attributeModifier, String key) {
        CompoundTag itemNBT = itemStack.getOrCreateTag();
        boolean hasAttribute = false;
        if (itemNBT.contains("AttributeModifiers", Tag.TAG_LIST)) {
            ListTag listnbt = itemNBT.getList("AttributeModifiers", Tag.TAG_COMPOUND);
            ArrayList<Integer> remove = new ArrayList();

            for (int i = 0; i < listnbt.size(); ++i) {
                CompoundTag attributeTag = listnbt.getCompound(i);
                AttributeModifier attributemodifier = AttributeModifier.load(attributeTag);
                if (attributemodifier != null && attributemodifier.getName().equals(key)) {
                    // adjust the tag
                    if (multiplier != 0) {
                        attributeTag.putDouble("Amount", multiplier);
                        hasAttribute = true;
                        break;
                    } else {
                        // discard the tag
                        remove.add(i);
//                        break; // leave commented for redundant tag cleanup
                    }
                }
            }
            if (hasAttribute && !remove.isEmpty()) {
                // remove from last to first so indices are valid
                Collections.reverse(remove);
                remove.forEach(index -> listnbt.remove(index));
            }
        }

        if (!hasAttribute && multiplier != 0) {
            itemStack.addAttributeModifier(attributeModifier, new AttributeModifier(key, multiplier, AttributeModifier.Operation.ADDITION), EquipmentSlot.LEGS);
        }
    }
}