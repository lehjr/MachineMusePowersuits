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

package lehjr.powersuits.common.event;

import lehjr.numina.client.config.NuminaClientConfig;
import lehjr.numina.client.sound.Musique;
import lehjr.numina.client.sound.SoundDictionary;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.thruster.IThruster;
import lehjr.numina.common.capabilities.player.keystates.IPlayerKeyStates;
import lehjr.numina.common.utils.PlayerUtils;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.MathUtils;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.common.config.MPSCommonConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.*;

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

    /**
     * Unified way of controlling thrust instead of having multiple independent calls
     * @param player
     * @param level
     */
    public static void playerMovementTick(Player player, Level level) {
        IPlayerKeyStates playerKeyStates = player.getCapability(NuminaCapabilities.PLAYER_KEYSTATES).resolve().orElse(null);
        if (playerKeyStates == null || player.isPassenger()) {
            return;
        }

        double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
        double remainingEnergy = playerEnergy;
        double percentJetThrust = 0;
        double percentJetBootsThrust = 0;

        // head
        IModularItem helmetCap = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.HEAD).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM).resolve().orElse(null);
        IPowerModule flightControlCap = null;
        if (helmetCap != null) {
            //only need this if online
            flightControlCap = helmetCap.getModuleCapabilityOnlineOrNull(MPSConstants.FLIGHT_CONTROL_MODULE);
        }

        // chest
        IModularItem chestCap = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.CHEST).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM).resolve().orElse(null);
        IPowerModule parachuteCap = null;
        IPowerModule gliderCap = null;
        IPowerModule jetpackCap = null;

        if (chestCap != null) {
            // no sound to cancel for parachute
            parachuteCap = chestCap.getModuleCapabilityOnlineOrNull(MPSConstants.PARACHUTE_MODULE);

            // need these even if offline to cancel sound
            gliderCap = chestCap.getModuleCapability(MPSConstants.GLIDER_MODULE);
            jetpackCap = chestCap.getModuleCapability(MPSConstants.JETPACK_MODULE);
        }

        // legs
        IModularItem legsCap = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.LEGS).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM).resolve().orElse(null);
        IPowerModule swimboostCap = null;
        if (legsCap != null) {
            // need this even if offline to cancel sound
            swimboostCap = legsCap.getModuleCapability(MPSConstants.SWIM_BOOST_MODULE);
        }

        // feet
        IModularItem feetCap = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.FEET).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM).resolve().orElse(null);
        IPowerModule jetBootsCap = null;
        if(feetCap != null) {
            // need this even if offline to cancel sound
            jetBootsCap = feetCap.getModuleCapabilityOnlineOrNull(MPSConstants.JETBOOTS_MODULE);
        }

        List<IPowerModule> inactiveModules = new ArrayList<>(Arrays.asList(jetpackCap, jetBootsCap, gliderCap, swimboostCap));

        // try swimming
        if (player.isInWaterOrBubble() || player.isInLava()) {
            player.stopFallFlying();
            if (swimboostCap instanceof IThruster thruster && swimboostCap.isModuleOnline()) {
                inactiveModules.remove(swimboostCap);
                //                if (player.isSwimming()) { // doesn't work when strafing without "swimming"

                if(!player.isPassenger()) {
                    double moveRatio = 0;
                    if (playerKeyStates.getForwardKeyState() || playerKeyStates.getReverseKeyState()) {
                        moveRatio += 1.0;
                    }
                    if (playerKeyStates.getLeftStrafeKeyState() || playerKeyStates.getRightStrafeKeyState()) {
                        moveRatio += 1.0;
                    }
                    if (playerKeyStates.getJumpKeyState() || player.isCrouching()) {
                        moveRatio += 0.2 * 0.2;
                    }

                    if(moveRatio > 0) {
                        double swimAssistRate = thruster.getDesiredThrust() * 0.05 * moveRatio;
                        double swimEnergyConsumption = swimboostCap.getEnergyUsage();

                        if (swimEnergyConsumption <= remainingEnergy) {
                            double thrust = thrust(player, swimAssistRate, playerKeyStates, true, flightControlCap);
                            double percentApplied = thrust / swimAssistRate;

                            if(thrust > 0) {
                                thruster.playSound(player, level, percentApplied);
                                remainingEnergy -= (percentApplied * swimEnergyConsumption);
                            }
                        } else {
                            thruster.stopSound(player, level);
                        }
                    }
                }
            }
            // can't fly/glide in liquid
        } else {
            if (parachuteCap != null) {
                PlayerUtils.resetFloatKickTicks(player);
                if (player.isCrouching() && player.getDeltaMovement().y < -0.1 && (!playerKeyStates.getForwardKeyState())) {
                    double totalVelocity = Math.sqrt(
                            player.getDeltaMovement().x * player.getDeltaMovement().x + player.getDeltaMovement().z * player.getDeltaMovement().z
                                    + player.getDeltaMovement().y * player.getDeltaMovement().y);
                    if (totalVelocity > 0) {
                        Vec3 motion = player.getDeltaMovement();
                        player.setDeltaMovement(motion.x * 0.1 / totalVelocity, motion.y * 0.1 / totalVelocity, motion.z * 0.1 / totalVelocity);
                    }
                }
            } else {
                if (gliderCap != null) {
                    if (playerKeyStates.getForwardKeyState() && !player.onGround()) {
                        // FIX: Check if we are ALREADY flying.
                        // If we are, do not call startFallFlying() again, let it ride!
                        if (!player.isFallFlying()) {
                            player.tryToStartFallFlying();
                            //player.startFallFlying();
                        }
                        inactiveModules.remove(gliderCap);
                    } else {
                        // FIX: Only kill flight if the player is active in the air
                        if (player.isFallFlying()) {
                            player.stopFallFlying();
                        }
                    }
                }

                // These basically operate the same way, just different amounds of thrust
                if (jetpackCap != null || jetBootsCap != null) {
                    for (IPowerModule pm : new IPowerModule[] {jetpackCap, jetBootsCap}) {
                        if(pm instanceof IThruster thruster && pm.isModuleOnline()) {
                            double desiredThrust = thruster.getDesiredThrust();
                            if(pm.getEnergyUsage() <= remainingEnergy && desiredThrust > 0) {
                                // in this case, there will be some thrust applied
                                if (flightControlCap != null) {
                                    // calculate how much thrust can be applied and apply it
                                    double appliedThrust = thrust(player, desiredThrust, playerKeyStates, true, flightControlCap);

                                    // calculate percentage of desired thrust applied
                                    percentJetThrust = appliedThrust / desiredThrust;

                                    remainingEnergy -= percentJetThrust * pm.getEnergyUsage();

                                    thruster.playSound(player, level, percentJetThrust);
                                    inactiveModules.remove(pm);

                                    // here thrust is a bit more constrained since throtting thrust actually shows on in the sound
                                } else if (playerKeyStates.getJumpKeyState()) {
                                    // needed for sound on the client side even if not actually providing thrust.
                                    if((level.isClientSide && NuminaClientConfig.useSounds) || player.getDeltaMovement().y < 0.5) {
                                        // calculate how much thrust can be applied and apply it
                                        double appliedThrust = thrust(player, desiredThrust, playerKeyStates, false, flightControlCap);
                                        // calculate percentage of desired thrust applied
                                        percentJetBootsThrust = appliedThrust / desiredThrust;

                                        remainingEnergy -= (percentJetBootsThrust * pm.getEnergyUsage());

                                        thruster.playSound(player, level, percentJetBootsThrust);
                                        inactiveModules.remove(pm);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if(level.isClientSide && NuminaClientConfig.useSounds) {
            stopPlaying(player, level, inactiveModules);

            if( gliderCap != null && player.isFallFlying()) {
                double volume = 0;
                double pitch = 0;
                float f = (float)player.getDeltaMovement().lengthSqr();
                if ((double)f >= 1.0E-7) {
                    volume = Mth.clamp(f / 4.0F, 0.0F, 1.0F);
                } else {
                    volume = 0.0F;
                }

                if(percentJetBootsThrust > 0 || percentJetThrust > 0) {
                    volume = volume * (percentJetBootsThrust + percentJetThrust) * 0.5;
                }

                float f1 = 0.8F;
                if (volume > f1) {
                    pitch = 1.0F + (volume - f1);
                } else {
                    pitch = 1.0F;
                }

                Musique.playerSound(player, SoundEvents.ELYTRA_FLYING, SoundSource.PLAYERS, (float) volume, (float)pitch, true);
            } else {
                Musique.stopPlayerSound(player, SoundEvents.ELYTRA_FLYING);
            }
        }


        if(remainingEnergy != playerEnergy && !level.isClientSide()) {
            double energyUsed = (remainingEnergy - playerEnergy) * -1.0;
            ElectricItemUtils.drainPlayerEnergy(player, energyUsed, true);
        }
    }

    static void stopPlaying(Player player, Level level, List<IPowerModule> modules) {
        for(IPowerModule module : modules) {
            if(module instanceof IThruster thruster) {
                thruster.stopSound(player, level);
            }
        }
    }

    public static double thrust(Player player, double thrust, IPlayerKeyStates playerInput, boolean verticleAdust, IPowerModule flightControlCap) {
        double thrustUsed = 0;
        if (verticleAdust) {
            Vec3 desiredDirection = player.getLookAngle().normalize();
            double strafeX = desiredDirection.z;
            double strafeZ = -desiredDirection.x;

            // check for flight control module to set vertical flight adjustments
            double flightVerticality = 0D;
            if (flightControlCap != null) {
                flightVerticality = flightControlCap.applyPropertyModifiers(MPSConstants.FLIGHT_VERTICALITY);
            }

            // similar to what is seen on client side
            double leftImpulse = Math.signum((playerInput.getRightStrafeKeyState() ? -1D : 0) + (playerInput.getLeftStrafeKeyState() ? 1D : 0));
            double forwardImpuse = Math.signum((playerInput.getReverseKeyState() ? -1D : 0) + (playerInput.getForwardKeyState() ? 1D : 0));

            // elytra flight and swimming
            if(player.isFallFlying() || player.isSwimming()) {
                desiredDirection = new Vec3((desiredDirection.x * forwardImpuse + strafeX * leftImpulse),
                        (flightVerticality * desiredDirection.y * forwardImpuse),
                        (desiredDirection.z * forwardImpuse + strafeZ * leftImpulse));
                // This is just for normal hovering
            } else {
                desiredDirection = new Vec3((desiredDirection.x * forwardImpuse + strafeX * leftImpulse),
                        (flightVerticality * desiredDirection.y * forwardImpuse + (playerInput.getJumpKeyState() ? 1 : 0)
                                - (playerInput.getDownKeyState() ? 1 : 0)), (desiredDirection.z * forwardImpuse + strafeZ * leftImpulse));
            }
            desiredDirection = desiredDirection.normalize();

            // Brakes
            if (player.getDeltaMovement().y < 0 && desiredDirection.y >= 0) {
                if (-player.getDeltaMovement().y > thrust) {
                    player.setDeltaMovement(player.getDeltaMovement().add(0, thrust, 0));
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
                //                NuminaLogger.logDebug("player.getDeltaMovement().x");

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
                            player.getDeltaMovement().add(
                                    0, 0, -(Math.signum(player.getDeltaMovement().z) * thrust)));
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
            if (!playerInput.getForwardKeyState()) {
                player.setDeltaMovement(player.getDeltaMovement().add(0, thrust, 0));
            } else {

                //                NuminaLogger.logDebug("thrust: " + thrust +", thrust * root2: " + (thrust * root2) );

                player.setDeltaMovement(player.getDeltaMovement().add(
                        desiredDirection.x * thrust * root2 * boolToVal(playerInput.getForwardKeyState()),
                        thrust * root2,
                        desiredDirection.z * thrust * root2// * Math.signum(playerInput.forwardKey)
                ));
            }
            thrustUsed += thrust;
        }

        // Slow the player if they are going too fast

        double horizontalMovementSquared = player.getDeltaMovement().x * player.getDeltaMovement().x + player.getDeltaMovement().z * player.getDeltaMovement().z;

        // currently comes out to 0.0625 // fIxmE: actually comes out to 1.5625... and why are we even manipulating this value. Just set a real range, 0 - 2 with a default of 1.5 or so
        double horizontalLimit = MPSCommonConfig.maxFlyingSpeed * MPSCommonConfig.maxFlyingSpeed * 0.05; // speed squared * 1/20 because 20 ticks per second
        //
        //        NuminaLogger.logDebug("horizontalMovementSquared: " + horizontalMovementSquared + ", horizontalLimit: " + horizontalLimit);

        if (player.isCrouching() && horizontalLimit > 0.05) {
            horizontalLimit = 0.05;
        }

        if (horizontalMovementSquared > horizontalLimit) {
            double ratio = Math.sqrt(horizontalLimit / horizontalMovementSquared);
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
        if (event.getEntity() instanceof Player player) {
            Level level = player.level();
            IModularItem iModularItem = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.LEGS).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM).resolve().orElse(null) ;
            if (iModularItem != null) {
                IPowerModule jumper = iModularItem.getOnlineModuleOrEmpty(MPSConstants.JUMP_ASSIST_MODULE).getCapability(NuminaCapabilities.Module.POWER_MODULE).resolve().orElse(null);
                if (jumper != null) {
                    double jumpAssist = jumper.applyPropertyModifiers(MPSConstants.JUMP_BOOST) * 2;
                    double drain = jumper.applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
                    double avail = ElectricItemUtils.getPlayerEnergy(player);
                    if ((level.isClientSide()) && NuminaClientConfig.useSounds) {
                        Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_JUMP_ASSIST.get(), SoundSource.PLAYERS, (float) (jumpAssist / 8.0), (float) 1, false);
                    }

                    if (drain < avail) {
                        ElectricItemUtils.drainPlayerEnergy(player, (int) drain, false);
                        setPlayerJumpTicks(player, jumpAssist);
                        double jumpCompensationRatio = jumper.applyPropertyModifiers(MPSConstants.EXHAUSTION_COMPENSATION);
                        if (player.isSprinting()) {
                            player.getFoodData().addExhaustion((float) (-0.2F * jumpCompensationRatio));
                        } else {
                            player.getFoodData().addExhaustion((float) (-0.05F * jumpCompensationRatio));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void handleFallEvent(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player && event.getDistance() > 3.0) {
            Level level = player.level();
            IModularItem iModularItem = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.FEET).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM).resolve().orElse(null);
            if (iModularItem != null) {
                IPowerModule sa = iModularItem.getModuleCapability(MPSConstants.SHOCK_ABSORBER_MODULE);
                if (sa != null && sa.isAllowed() && sa.isModuleOnline()) {
                    double distanceAbsorb = Math.abs(event.getDistance() * sa.applyPropertyModifiers(MPSConstants.DISTANCE_REDUCTION));
                    if (level.isClientSide && NuminaClientConfig.useSounds) {
                        Musique.playerSound(player, SoundDictionary.SOUND_EVENT_GUI_INSTALL.get(), SoundSource.PLAYERS, (float) (distanceAbsorb), (float) 1, false);
                    }
                    double drain = distanceAbsorb * sa.getEnergyUsage();
                    double avail = ElectricItemUtils.getPlayerEnergy(player);
                    if (drain <= avail) {
                        ElectricItemUtils.drainPlayerEnergy(player, (int) drain, false);
                        double fallDistance = Math.max(0, (event.getDistance() - distanceAbsorb));
                        event.setDistance((float) fallDistance);
                        //                        event.getEntityLiving().sendMessage(Component.literalString("modified fall settings: [ damage : " + event.getDamageMultiplier() + " ], [ distance : " + event.getDistance() + " ]"));
                    }
                }
            }
        }
    }
}