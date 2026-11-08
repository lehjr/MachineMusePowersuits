package lehjr.powersuits.common.event;

import lehjr.numina.client.config.NuminaClientConfig;
import lehjr.numina.client.sound.Musique;
import lehjr.numina.client.sound.SoundDictionary;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.thruster.IThruster;
import lehjr.numina.common.capabilities.player.keystates.IPlayerKeyStates;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.MathUtils;
import lehjr.numina.common.utils.PlayerUtils;
import lehjr.numina.common.utils.TagUtils;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.common.config.MPSCommonConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum MovementManager {
    INSTANCE;
    static final double root2 = 1 / Math.sqrt(2);
    public static final Map<UUID, Double> playerJumpMultipliers = new HashMap<>();
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

    public double getMovementResistance(Player player) {
        double movementResistance = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack equippedItem = ItemUtils.getItemFromEntitySlot(player, slot);
            IModularItem modularItem = NuminaCapabilities.getModularItem(equippedItem);
            if(modularItem != null) {
                movementResistance += modularItem.getMovementResistance();
            }
        }
        return movementResistance;
    }

    // Horizontal ground based movement
    // moved here so it is still accessible if sprint assist module isn't installed.
    public void setMovementModifier(ItemStack itemStack, double multiplier, Player player, Holder<Attribute> attribute) {
        // reduce player speed according to Kinetic Energy Generator setting
        double movementResistance = getMovementResistance(player);
        multiplier -= movementResistance;
        // player walking speed: 0.1
        // player sprint speed: 0.13
        // negative addative will make player slower
        double additive;

        //        NuminaLogger.logDebug("multiplier: " + multiplier);

        if(player.isSprinting()) {
            additive = multiplier * 0.13;
        }else {
            additive = multiplier * 0.1;
        }
        additive = additive * 0.5;

        if(additive > 0) {
            if(player.isSprinting()) {
                //            additive = multiplier * 0.13;
                additive = 10;
            }else {
                //            additive = multiplier * 0.1;
                additive = 5;
            }
        }

        //        removeModifiers(itemStack);

        ItemAttributeModifiers modifiers = itemStack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        //        NuminaLogger.logDebug("compute before: " + modifiers.compute(player.getAttributeBaseValue(Attributes.MOVEMENT_SPEED), EquipmentSlot.LEGS));

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

        //        NuminaLogger.logDebug("setting speed as: " + additive);

        //        builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(getArmorAttributeResourceLocationBySlot(EquipmentSlot.LEGS), additive, AttributeModifier.Operation.ADD_VALUE),
        //            EquipmentSlotGroup.bySlot(EquipmentSlot.LEGS));
        //        TagUtils.setAttributes(itemStack, builder.build());

        //        ItemStack testStack = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.LEGS);
        //
        //        ItemAttributeModifiers test = testStack.get(DataComponents.ATTRIBUTE_MODIFIERS);

        //
        //
        //
        //        //        +        // Neo: Convert Movement Speed to percent-based for more appropriate display using IAttributeExtension. Use a scale factor of 1000 since movement speed has 0.001 units.
        //        //            +        "generic.movement_speed", new PercentageAttribute("attribute.name.generic.movement_speed", 0.7, 0.0, 1024.0, 1000).setSyncable(true));
        //        //
        //        modifiers = testStack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        //
        ////                AttributeInstance attributeinstance = modifiers..modifiers().getInstance(attribute);
        ////                    this.getAttribute(Attributes.MOVEMENT_SPEED);
        ////                if (attributeinstance != null && attributeinstance.getModifier(SPEED_MODIFIER_POWDER_SNOW_ID) != null) {
        ////                    attributeinstance.removeModifier(SPEED_MODIFIER_POWDER_SNOW_ID);
        ////                }
        //
        //        NuminaLogger.logDebug("test stack compute after: " + modifiers.compute(player.getAttributeBaseValue(Attributes.MOVEMENT_SPEED), EquipmentSlot.LEGS));
        //        if (test != null) {
        //
        //            NuminaLogger.logDebug("test here: " + MobEffects.MOVEMENT_SPEED.value());
        //
        //
        //        }
        //
        //        modifiers = itemStack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        //
        //        //                AttributeInstance attributeinstance = modifiers..modifiers().getInstance(attribute);
        //        //                    this.getAttribute(Attributes.MOVEMENT_SPEED);
        //        //                if (attributeinstance != null && attributeinstance.getModifier(SPEED_MODIFIER_POWDER_SNOW_ID) != null) {
        //        //                    attributeinstance.removeModifier(SPEED_MODIFIER_POWDER_SNOW_ID);
        //        //                }
        //        test = testStack.get(DataComponents.ATTRIBUTE_MODIFIERS);
        //        NuminaLogger.logDebug("itemStack compute after: " + modifiers.compute(player.getAttributeBaseValue(Attributes.MOVEMENT_SPEED), EquipmentSlot.LEGS));
        //        if (test != null) {
        //
        //            NuminaLogger.logDebug("itemStack here: " + MobEffects.MOVEMENT_SPEED.value());
        //
        //
        //        }
        //
        //
        //
        //        NuminaLogger.logDebug("equal?: " + ItemStack.matches(itemStack, testStack));


        //        if(additive > 0) {
        //            MobEffectInstance effect = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 10);
        ////            MobEffectInstance effectTest = player.getEffect(MobEffects.MOVEMENT_SPEED);
        ////
        ////            if(effectTest == null || effectTest.getDuration() < 90) {
        //                player.addEffect(effect);
        //
        //                //            NeoForgeMod.SWIM_SPEED
        ////            } else {
        ////                NuminaLogger.logDebug("player already has effect");
        ////            }
        //        } else {
        //            NuminaLogger.logDebug("removing effect");
        //
        //            player.removeEffect(MobEffects.MOVEMENT_SPEED);
        //        }
    }

    public static ResourceLocation getArmorAttributeResourceLocationBySlot(EquipmentSlot slot) {
        return ResourceLocation.fromNamespaceAndPath(MPSConstants.MOD_ID, "armor." + slot.getName());
    }

    public static void removeModifiers(@Nonnull ItemStack itemStack) {
        TagUtils.setAttributes(itemStack, ItemAttributeModifiers.EMPTY);
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
        IPlayerKeyStates playerKeyStates = player.getCapability(NuminaCapabilities.PLAYER_KEYSTATES);
        if (playerKeyStates == null || player.isPassenger()) {
            return;
        }

        double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
        double remainingEnergy = playerEnergy;
        double percentJetPackThrust = 0;
        double percentJetBootsThrust = 0;


        // head
        IModularItem helmetCap = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.HEAD).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
        IPowerModule flightControlCap = null;
        if (helmetCap != null) {
            flightControlCap = helmetCap.getModuleCapabilityOnlineOrNull(MPSConstants.FLIGHT_CONTROL_MODULE);
        }

        // chest
        IModularItem chestCap = null;
        IPowerModule parachuteCap = null;
        IPowerModule gliderCap = null;
        IPowerModule jetpackCap = null;

        // legs
        IModularItem legsCap = null;
        IPowerModule swimboostCap = null;

        // feet
        IModularItem feetCap = null;
        IPowerModule jetBootsCap = null;

        // can't fly/glide in liquid
        if (!player.isInLiquid()) {
            chestCap = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.CHEST).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
            if (chestCap != null) {
                // active parachute prevents other movement modules from being used
                parachuteCap = chestCap.getModuleCapabilityOnlineOrNull(MPSConstants.PARACHUTE_MODULE);
                if (parachuteCap == null) {
                    gliderCap = chestCap.getModuleCapabilityOnlineOrNull(MPSConstants.GLIDER_MODULE);
                    if (gliderCap != null) {
                        if (playerKeyStates.getForwardKeyState() && !player.onGround()) {
                            if(!player.isFallFlying()) {
                                player.startFallFlying();
                            }
                        } else if (player.isFallFlying()) {
                            player.stopFallFlying();
                        }
                    }

                    if (gliderCap != null) {
                        if (playerKeyStates.getForwardKeyState() && !player.onGround()) {
                            // FIX: Check if we are ALREADY flying.
                            // If we are, do not call startFallFlying() again, let it ride!
                            if (!player.isFallFlying()) {
                                player.tryToStartFallFlying();
//                                player.startFallFlying();
                            }
                        } else {
                            // FIX: Only kill flight if the player is active in the air
                            if (player.isFallFlying()) {
                                player.stopFallFlying();
                            }
                        }
                    }

                    jetpackCap = chestCap.getModuleCapabilityOnlineOrNull(MPSConstants.JETPACK_MODULE);
                }
            }
            feetCap = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.FEET).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
            if (feetCap != null) {
                jetBootsCap = feetCap.getModuleCapabilityOnlineOrNull(MPSConstants.JETBOOTS_MODULE);
            }
            // try swimming
        } else {
            player.stopFallFlying();
            legsCap = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.LEGS).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
            if (legsCap != null) {
                swimboostCap = legsCap.getModuleCapabilityOnlineOrNull(MPSConstants.SWIM_BOOST_MODULE);
            }
        }

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
        }

        // These basically operate the same way, just different amounds of thrust
        if (jetpackCap != null || jetBootsCap != null) {
            for (IPowerModule pm : new IPowerModule[] {jetpackCap, jetBootsCap}) {
                if(pm instanceof IThruster thruster) {
                    double desiredThrust = thruster.getDesiredThrust();
                    if(pm.getEnergyUsage() <= remainingEnergy && desiredThrust > 0) {

                        // in this case, there will be some thrust applied
                        if (flightControlCap != null) {
                            // calculate how much thrust can be applied and apply it
                            double appliedThrust = thrust(player, desiredThrust, playerKeyStates, true, flightControlCap);

                            // calculate percentage of desired thrust applied
                            percentJetPackThrust = appliedThrust / desiredThrust;

                            remainingEnergy -= percentJetPackThrust * pm.getEnergyUsage();

                            thruster.playSound(player, level, percentJetPackThrust);

                        // here thrust is a bit more constrained since throtting thrust actually shows on in the sound
                        } else if (playerKeyStates.getJumpKeyState()) {
                            if(level.isClientSide && NuminaClientConfig.useSounds || player.getDeltaMovement().y < 0.5) {
                                // calculate how much thrust can be applied and apply it
                                double appliedThrust = thrust(player, desiredThrust, playerKeyStates, false, flightControlCap);
                                // calculate percentage of desired thrust applied
                                percentJetBootsThrust = appliedThrust / desiredThrust;

                                remainingEnergy -= (percentJetBootsThrust * pm.getEnergyUsage());

                                thruster.playSound(player, level, percentJetBootsThrust);
                            }
                        } else {
                            thruster.stopSound(player, level);
                        }
                    } else {
                        thruster.stopSound(player, level);
                    }
                }
            }

        } else if (swimboostCap != null && swimboostCap instanceof IThruster thruster) {
            //                if (player.isSwimming()) { // doesn't work when strafing without "swimming"

            if((player.isInLiquid() && !player.isPassenger())) {
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

        if(level.isClientSide && NuminaClientConfig.useSounds) {
// the elytra flight sound code just in case
//        public void tick() {
//            ++this.time;
//            if (!this.player.isRemoved() && (this.time <= 20 || this.player.isFallFlying())) {
//                this.x = (double)((float)this.player.getX());
//                this.y = (double)((float)this.player.getY());
//                this.z = (double)((float)this.player.getZ());
//                float f = (float)this.player.getDeltaMovement().lengthSqr();
//                if ((double)f >= 1.0E-7) {
//                    this.volume = Mth.clamp(f / 4.0F, 0.0F, 1.0F);
//                } else {
//                    this.volume = 0.0F;
//                }
//
//                if (this.time < 20) {
//                    this.volume = 0.0F;
//                } else if (this.time < 40) {
//                    this.volume *= (float)(this.time - 20) / 20.0F;
//                }
//
//                float f1 = 0.8F;
//                if (this.volume > 0.8F) {
//                    this.pitch = 1.0F + (this.volume - 0.8F);
//                } else {
//                    this.pitch = 1.0F;
//                }
//            } else {
//                this.stop();
//            }
//
//        }
            if( gliderCap != null && player.isFallFlying()) {
                double volume = 0;
                double pitch = 0;
                float f = (float)player.getDeltaMovement().lengthSqr();
                if ((double)f >= 1.0E-7) {
                    volume = Mth.clamp(f / 4.0F, 0.0F, 1.0F);
                } else {
                    volume = 0.0F;
                }

                if(percentJetBootsThrust > 0 || percentJetPackThrust > 0) {
                    volume = volume * (percentJetBootsThrust + percentJetPackThrust) * 0.5;
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

        double horzm1 = player.getDeltaMovement().x * player.getDeltaMovement().x + player.getDeltaMovement().z * player.getDeltaMovement().z;

        NuminaLogger.logDebug("player speed: " + computePlayerVelocity(player) + ", horzm2: " + horzm1);


        // Slow the player if they are going too fast
        if(!player.isFallFlying() && !player.isSwimming()) {

            double horizontalMovementSquared = player.getDeltaMovement().x * player.getDeltaMovement().x + player.getDeltaMovement().z * player.getDeltaMovement().z;

            // currently comes out to 0.0625 // fIxmE: actually comes out to 1.5625... and why are we even manipulating this value. Just set a real range, 0 - 2 with a default of 1.5 or so
            double horizontalLimit = MPSCommonConfig.maxFlyingSpeed * MPSCommonConfig.maxFlyingSpeed / 400;

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
        }

        PlayerUtils.resetFloatKickTicks(player);

        NuminaLogger.logDebug("returning thrust: " + thrustUsed);

        return thrustUsed;
    }

    public static double computePlayerVelocity(Player player) {
        return MathUtils.pythag(player.getDeltaMovement().x, player.getDeltaMovement().y, player.getDeltaMovement().z);
    }

    @SubscribeEvent
    public void handleLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player player) {
            Level level = player.level();
            IModularItem iModularItem = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.LEGS).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
            if (iModularItem != null) {
                IPowerModule jumper = iModularItem.getOnlineModuleOrEmpty(MPSConstants.JUMP_ASSIST_MODULE).getCapability(NuminaCapabilities.Module.POWER_MODULE);
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
            IModularItem iModularItem = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.FEET).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
            if (iModularItem != null) {
                ItemStack shockAbsorbers = iModularItem.getOnlineModuleOrEmpty(MPSConstants.SHOCK_ABSORBER_MODULE);
                IPowerModule sa = shockAbsorbers.getCapability(NuminaCapabilities.Module.POWER_MODULE);
                if (sa != null) {
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
