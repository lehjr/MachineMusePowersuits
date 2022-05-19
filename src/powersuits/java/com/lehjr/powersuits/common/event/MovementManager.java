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

package com.lehjr.powersuits.common.event;

import com.lehjr.numina.client.control.PlayerMovementInputWrapper;
import com.lehjr.numina.client.sound.Musique;
import com.lehjr.numina.client.sound.SoundDictionary;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.CapabilityPowerModule;
import com.lehjr.numina.common.config.NuminaSettings;
import com.lehjr.numina.common.energy.ElectricItemUtils;
import com.lehjr.numina.common.math.MathUtils;
import com.lehjr.numina.common.player.PlayerUtils;
import com.lehjr.powersuits.client.sound.MPSSoundDictionary;
import com.lehjr.powersuits.common.config.MPSSettings;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum MovementManager {
    INSTANCE;
    static final double root2 = 1/Math.sqrt(2);
    public static final Map<UUID, Double> playerJumpMultipliers = new HashMap();
    /**
     * Gravity, in meters per tick per tick.
     */
    public static final double DEFAULT_GRAVITY = -0.0784000015258789;

    public double getPlayerJumpMultiplier(Player player) {
        if (playerJumpMultipliers.containsKey(player.getUUID())) {
            return playerJumpMultipliers.get(player.getUUID());
        } else {
            return 0;
        }
    }

    public void setPlayerJumpTicks(Player player, double number) {
        playerJumpMultipliers.put(player.getUUID(), number);
    }

    public double computeFallHeightFromVelocity(double velocity) {
        double ticks = velocity / DEFAULT_GRAVITY;
        return -0.5 * DEFAULT_GRAVITY * ticks * ticks;
    }

    public static void removeModifiers(@Nonnull ItemStack itemStack) {
        itemStack.removeTagKey("AttributeModifiers");
    }

    static double boolToVal(boolean boolIn) {
        return boolIn ? 1.0D : 0.0D;
    }

    public static double thrust(Player player, double thrust, boolean flightControl) {
        PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
        double thrustUsed = 0;
        if (flightControl) {
            Vec3 desiredDirection = player.getLookAngle().normalize();
            double strafeX = desiredDirection.z;
            double strafeZ = -desiredDirection.x;

            double flightVerticality = player.getItemBySlot(EquipmentSlot.HEAD).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModularItem.class::isInstance)
                    .map(IModularItem.class::cast)
                    .map(iModularItem -> iModularItem
                            .getOnlineModuleOrEmpty(MPSRegistryNames.FLIGHT_CONTROL_MODULE)
                            .getCapability(CapabilityPowerModule.POWER_MODULE)
                            .map(pm -> pm.applyPropertyModifiers(MPSConstants.FLIGHT_VERTICALITY)).orElse(0D)).orElse(0D);
            desiredDirection = new Vec3(
                    (desiredDirection.x * boolToVal(playerInput.forwardKey) + strafeX * playerInput.strafeKey),
                    (flightVerticality * desiredDirection.y * boolToVal(playerInput.forwardKey) + boolToVal(playerInput.jumpKey) - boolToVal(playerInput.downKey)),
                    (desiredDirection.z * boolToVal(playerInput.forwardKey) + strafeZ * playerInput.strafeKey));

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
                            -(Math.signum(player.getDeltaMovement().x) * thrust), 0, 0));
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
                            player.getDeltaMovement().subtract(
                                    0, 0, Math.signum(player.getDeltaMovement().z) * thrust));
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
            Vec3 desiredDirection = player.getLookAngle().normalize();
            desiredDirection = new Vec3(desiredDirection.x, 0, desiredDirection.z);
            desiredDirection.normalize();
            if (!playerInput.forwardKey) {
                player.setDeltaMovement(player.getDeltaMovement().add(0, thrust, 0));
            } else {

//                System.out.println("thrust: " + thrust +", thrust * root2: " + (thrust * root2) );

                player.setDeltaMovement(player.getDeltaMovement().add(
                        desiredDirection.x * thrust * root2 * boolToVal(playerInput.forwardKey),
                        thrust * root2,
                        desiredDirection.z * thrust * root2// * Math.signum(playerInput.forwardKey)
                ));
            }
            thrustUsed += thrust;
        }
        // Slow the player if they are going too fast
        double horzm2 = player.getDeltaMovement().x * player.getDeltaMovement().x + player.getDeltaMovement().z * player.getDeltaMovement().z;

        // currently comes out to 0.0625
        double horizontalLimit = MPSSettings.getMaxFlyingSpeed() * MPSSettings.getMaxFlyingSpeed() / 400;

        if (player.isCrouching() && horizontalLimit > 0.05) {
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



    public static double computePlayerVelocity(Player player) {
        return MathUtils.pythag(player.getDeltaMovement().x, player.getDeltaMovement().y, player.getDeltaMovement().z);
    }

   @SubscribeEvent
    public void handleLivingJumpEvent(LivingJumpEvent event) {
        if (event.getEntityLiving() instanceof Player) {
            Player player = (Player) event.getEntityLiving();
            player.getItemBySlot(EquipmentSlot.LEGS).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModularItem.class::isInstance)
                    .map(IModularItem.class::cast)
                    .ifPresent(iModularItem -> iModularItem.getOnlineModuleOrEmpty(MPSRegistryNames.JUMP_ASSIST_MODULE).getCapability(CapabilityPowerModule.POWER_MODULE).ifPresent(jumper -> {
                        double jumpAssist = jumper.applyPropertyModifiers(MPSConstants.MULTIPLIER) * 2;
                        double drain = jumper.applyPropertyModifiers(MPSConstants.JUMP_ENERGY);
                        int avail = ElectricItemUtils.getPlayerEnergy(player);
                        if ((player.level.isClientSide()) && NuminaSettings.useSounds()) {
                            Musique.playerSound(player, MPSSoundDictionary.JUMP_ASSIST, SoundSource.PLAYERS, (float) (jumpAssist / 8.0), (float) 1, false);
                        }

                        if (drain < avail) {
                            ElectricItemUtils.drainPlayerEnergy(player, (int) drain, false);
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
        if (event.getEntityLiving() instanceof Player && event.getDistance() > 3.0) {
            Player player = (Player) event.getEntityLiving();
            player.getItemBySlot(EquipmentSlot.FEET).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModularItem.class::isInstance)
                    .map(IModularItem.class::cast)
                    .ifPresent(iModularItem -> iModularItem.getOnlineModuleOrEmpty(MPSRegistryNames.SHOCK_ABSORBER_MODULE).getCapability(CapabilityPowerModule.POWER_MODULE).ifPresent(sa -> {
                        double distanceAbsorb = event.getDistance() * sa.applyPropertyModifiers(MPSConstants.MULTIPLIER);
                        if (player.level.isClientSide && NuminaSettings.useSounds()) {
                            Musique.playerSound(player, SoundDictionary.SOUND_EVENT_GUI_INSTALL, SoundSource.PLAYERS, (float) (distanceAbsorb), (float) 1, false);
                        }
                        double drain = distanceAbsorb * sa.applyPropertyModifiers(MPSConstants.SHOCK_ENERGY);
                        int avail = ElectricItemUtils.getPlayerEnergy(player);
                        if (drain < avail) {
                            ElectricItemUtils.drainPlayerEnergy(player, (int) drain, false);
                            event.setDistance((float) (event.getDistance() - distanceAbsorb));
    //                        event.getEntityLiving().sendMessage(new TextComponentString("modified fall settings: [ damage : " + event.getDamageMultiplier() + " ], [ distance : " + event.getDistance() + " ]"));
                        }
                    }));
        }
    }
}
