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

package com.github.lehjr.powersuits.event;

import com.github.lehjr.numina.config.NuminaSettings;
import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.client.control.PlayerMovementInputWrapper;
import com.github.lehjr.numina.util.client.sound.Musique;
import com.github.lehjr.numina.util.client.sound.SoundDictionary;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.numina.util.math.MuseMathUtils;
import com.github.lehjr.numina.util.player.PlayerUtils;
import com.github.lehjr.powersuits.client.sound.MPSSoundDictionary;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.constants.MPSRegistryNames;
import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum MovementManager {
    INSTANCE;
    static final double root2 = Math.sqrt(2);
    public static final Map<UUID, Double> playerJumpMultipliers = new HashMap();
    /**
     * Gravity, in meters per tick per tick.
     */
    public static final double DEFAULT_GRAVITY = -0.0784000015258789;

    public double getPlayerJumpMultiplier(PlayerEntity player) {
        if (playerJumpMultipliers.containsKey(player.getUUID())) {
            return playerJumpMultipliers.get(player.getUUID());
        } else {
            return 0;
        }
    }

    public void setPlayerJumpTicks(PlayerEntity player, double number) {
        playerJumpMultipliers.put(player.getUUID(), number);
    }

    public double computeFallHeightFromVelocity(double velocity) {
        double ticks = velocity / DEFAULT_GRAVITY;
        return -0.5 * DEFAULT_GRAVITY * ticks * ticks;
    }

    // moved here so it is still accessible if sprint assist module isn't installed.
    public void setMovementModifier(ItemStack itemStack, double multiplier, PlayerEntity player) {
        // reduce player speed according to Kinetic Energy Generator setting
        AtomicDouble movementResistance = new AtomicDouble(0);
        itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .ifPresent(iModularItem -> {
                iModularItem.getOnlineModuleOrEmpty(MPSRegistryNames.KINETIC_GENERATOR_MODULE_REGNAME).getCapability(PowerModuleCapability.POWER_MODULE)
                        .ifPresent(kin->{
                            movementResistance.set(kin.applyPropertyModifiers(MPSConstants.MOVEMENT_RESISTANCE));
                        });
        });

        multiplier -= movementResistance.get();
        // player walking speed: 0.10000000149011612
        // player sprintint speed: 0.13000001
        double additive = multiplier * (player.isSprinting() ? 0.13 : 0.1)/2;
        CompoundNBT itemNBT = itemStack.getOrCreateTag();
        boolean hasAttribute = false;

        if (itemNBT.contains("AttributeModifiers", Constants.NBT.TAG_LIST)) {
            ListNBT listnbt = itemNBT.getList("AttributeModifiers", Constants.NBT.TAG_COMPOUND);
            int remove = -1;

            for (int i = 0; i < listnbt.size(); ++i) {
                CompoundNBT attributeTag = listnbt.getCompound(i);
                AttributeModifier attributemodifier = AttributeModifier.load(attributeTag);
                if (attributemodifier != null && attributemodifier.getName().equals(Attributes.MOVEMENT_SPEED.getDescriptionId())) {
                    // adjust the tag
                    if (additive != 0) {
                        attributeTag.putDouble("Amount", additive);
                        hasAttribute = true;
                        break;
                    } else {
                        // discard the tag
                        remove = i;
                        break;
                    }
                }
            }
            if (hasAttribute && remove != -1) {
                listnbt.remove(remove);
            }
        }
        if (!hasAttribute && additive != 0) {
            itemStack.addAttributeModifier(Attributes.MOVEMENT_SPEED, new AttributeModifier(Attributes.MOVEMENT_SPEED.getDescriptionId(), additive, AttributeModifier.Operation.ADDITION), EquipmentSlotType.LEGS);
        }
    }

    public double thrust(PlayerEntity player, double thrust, boolean flightControl) {
        PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
        double thrustUsed = 0;
        if (flightControl) {
            Vector3d desiredDirection = player.getLookAngle().normalize();
            double strafeX = desiredDirection.z;
            double strafeZ = -desiredDirection.x;
            ItemStack helm = player.getItemBySlot(EquipmentSlotType.HEAD);
            double flightVerticality = helm.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModularItem.class::isInstance)
                    .map(IModularItem.class::cast)
                    .map(iModularItem -> iModularItem
                            .getOnlineModuleOrEmpty(MPSRegistryNames.FLIGHT_CONTROL_MODULE_REGNAME)
                            .getCapability(PowerModuleCapability.POWER_MODULE)
                            .map(pm -> pm.applyPropertyModifiers(MPSConstants.FLIGHT_VERTICALITY)).orElse(0D)).orElse(0D);

            desiredDirection = new Vector3d(
                    (desiredDirection.x * Math.signum(playerInput.moveForward) + strafeX * Math.signum(playerInput.moveStrafe)),
                    (flightVerticality * desiredDirection.y * Math.signum(playerInput.moveForward) + (playerInput.jumpKey ? 1 : 0) - (playerInput.downKey ? 1 : 0)),
                    (desiredDirection.z * Math.signum(playerInput.moveForward) + strafeZ * Math.signum(playerInput.moveStrafe)));

            desiredDirection = desiredDirection.normalize();

            // Brakes
            if (player.getDeltaMovement().y < 0 && desiredDirection.y >= 0) {
                if (-player.getDeltaMovement().y > thrust) {
                    player.setDeltaMovement(player.getDeltaMovement().add(0, thrust,0));
                    thrustUsed += thrust;
                    thrust = 0;
                } else {
                    thrust -= player.getDeltaMovement().y;
                    thrustUsed += player.getDeltaMovement().y;
                    player.setDeltaMovement(player.getDeltaMovement().x, 0, player.getDeltaMovement().z);
                }
            }
            if (player.getDeltaMovement().y < -1) {
                thrust += 1 + player.getDeltaMovement().y;
                thrustUsed -= 1 + player.getDeltaMovement().y;
                player.setDeltaMovement(player.getDeltaMovement().x, -1, player.getDeltaMovement().z);
            }
            if (Math.abs(player.getDeltaMovement().x) > 0 && desiredDirection.length() == 0) {
                if (Math.abs(player.getDeltaMovement().x) > thrust) {
                    player.setDeltaMovement(player.getDeltaMovement().add(
                            - Math.signum(player.getDeltaMovement().x) * thrust, 0, 0));
                    thrustUsed += thrust;
                    thrust = 0;
                } else {
                    thrust -= Math.abs(player.getDeltaMovement().x);
                    thrustUsed += Math.abs(player.getDeltaMovement().x);
                    player.setDeltaMovement(0, player.getDeltaMovement().y, player.getDeltaMovement().z);
                }
            }
            if (Math.abs(player.getDeltaMovement().z) > 0 && desiredDirection.length() == 0) {
                if (Math.abs(player.getDeltaMovement().z) > thrust) {
                    player.setDeltaMovement(
                            player.getDeltaMovement().add(
                                    0, 0, Math.signum(player.getDeltaMovement().z) * thrust
                            )

                    );
                    thrustUsed += thrust;
                    thrust = 0;
                } else {
                    thrustUsed += Math.abs(player.getDeltaMovement().z);
                    thrust -= Math.abs(player.getDeltaMovement().z);
                    player.setDeltaMovement(player.getDeltaMovement().x, player.getDeltaMovement().y, 0);
                }
            }

            // Thrusting, finally :V
            player.setDeltaMovement(player.getDeltaMovement().add(
                    thrust * desiredDirection.x,
                    thrust * desiredDirection.y,
                    thrust * desiredDirection.z
            ));
            thrustUsed += thrust;

        } else {
            Vector3d playerHorzFacing = player.getLookAngle();
            playerHorzFacing = new Vector3d(playerHorzFacing.x, 0, playerHorzFacing.z);
            playerHorzFacing.normalize();
            if (playerInput.moveForward == 0) {
                player.setDeltaMovement(player.getDeltaMovement().add(0, thrust, 0));
            } else {
                player.setDeltaMovement(player.getDeltaMovement().add(
                        playerHorzFacing.x * thrust / root2 * Math.signum(playerInput.moveForward),
                        thrust / root2,
                        playerHorzFacing.z * thrust / root2 * Math.signum(playerInput.moveForward)
                ));
            }
            thrustUsed += thrust;
        }

        // Slow the player if they are going too fast
        double horzm2 = player.getDeltaMovement().x * player.getDeltaMovement().x + player.getDeltaMovement().z * player.getDeltaMovement().z;

        // currently comes out to 0.0625
        double horizontalLimit = MPSSettings.getMaxFlyingSpeed() * MPSSettings.getMaxFlyingSpeed() / 400;

//        double playerVelocity = Math.abs(player.getMotion().x) + Math.abs(player.getMotion().y) + Math.abs(player.getMotion().z);

        if (playerInput.sneakKey && horizontalLimit > 0.05) {
            horizontalLimit = 0.05;
        }

        if (horzm2 > horizontalLimit) {
            double ratio = Math.sqrt(horizontalLimit / horzm2);
            player.setDeltaMovement(
                    player.getDeltaMovement().x * ratio,
                    player.getDeltaMovement().y,
                    player.getDeltaMovement().z * ratio);
        }
        PlayerUtils.resetFloatKickTicks(player);
        return thrustUsed;
    }

    public static double computePlayerVelocity(PlayerEntity player) {
        return MuseMathUtils.pythag(player.getDeltaMovement().x, player.getDeltaMovement().y, player.getDeltaMovement().z);
    }

   @SubscribeEvent
    public void handleLivingJumpEvent(LivingJumpEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            player.getItemBySlot(EquipmentSlotType.LEGS).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModularItem.class::isInstance)
                    .map(IModularItem.class::cast)
                    .ifPresent(iModularItem -> iModularItem.getOnlineModuleOrEmpty(MPSRegistryNames.JUMP_ASSIST_MODULE_REGNAME).getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(jumper -> {
                        double jumpAssist = jumper.applyPropertyModifiers(MPSConstants.MULTIPLIER) * 2;
                        double drain = jumper.applyPropertyModifiers(MPSConstants.JUMP_ENERGY);
                        int avail = ElectricItemUtils.getPlayerEnergy(player);
                        if ((player.level.isClientSide()) && NuminaSettings.useSounds()) {
                            Musique.playerSound(player, MPSSoundDictionary.JUMP_ASSIST, SoundCategory.PLAYERS, (float) (jumpAssist / 8.0), (float) 1, false);
                        }

                        if (drain < avail) {
                            ElectricItemUtils.drainPlayerEnergy(player, (int) drain);
                            setPlayerJumpTicks(player, jumpAssist);
                            double jumpCompensationRatio = jumper.applyPropertyModifiers(MPSConstants.FOOD_COMPENSATION);
                            if (player.isSprinting()) {
                                player.getFoodData().addExhaustion((float) (-0.2F * jumpCompensationRatio));
                            } else {
                                player.getFoodData().addExhaustion((float) (-0.05F * jumpCompensationRatio));
                            }
                        }
                    }));
        }
    }

    @SubscribeEvent
    public void handleFallEvent(LivingFallEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity && event.getDistance() > 3.0) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            player.getItemBySlot(EquipmentSlotType.FEET).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModularItem.class::isInstance)
                    .map(IModularItem.class::cast)
                    .ifPresent(iModularItem -> iModularItem.getOnlineModuleOrEmpty(MPSRegistryNames.SHOCK_ABSORBER_MODULE_REGNAME).getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(sa -> {
                        double distanceAbsorb = event.getDistance() * sa.applyPropertyModifiers(MPSConstants.MULTIPLIER);
                        if (player.level.isClientSide && NuminaSettings.useSounds()) {
                            Musique.playerSound(player, SoundDictionary.SOUND_EVENT_GUI_INSTALL, SoundCategory.PLAYERS, (float) (distanceAbsorb), (float) 1, false);
                        }
                        double drain = distanceAbsorb * sa.applyPropertyModifiers(MPSConstants.SHOCK_ENERGY);
                        int avail = ElectricItemUtils.getPlayerEnergy(player);
                        if (drain < avail) {
                            ElectricItemUtils.drainPlayerEnergy(player, (int) drain);
                            event.setDistance((float) (event.getDistance() - distanceAbsorb));
    //                        event.getEntityLiving().sendMessage(new TextComponentString("modified fall settings: [ damage : " + event.getDamageMultiplier() + " ], [ distance : " + event.getDistance() + " ]"));
                        }
                    }));
        }
    }
}
