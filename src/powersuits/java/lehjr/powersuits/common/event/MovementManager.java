package lehjr.powersuits.common.event;

import lehjr.numina.client.config.NuminaClientConfig;
import lehjr.numina.client.control.PlayerMovementInputWrapper;
import lehjr.numina.client.sound.Musique;
import lehjr.numina.client.sound.SoundDictionary;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

    // moved here so it is still accessible if sprint assist module isn't installed.
    public void setMovementModifier(ItemStack itemStack, double multiplier, Player player, Holder<Attribute> attribute) {
        // reduce player speed according to Kinetic Energy Generator setting
        double movementResistance = getMovementResistance(player);
        multiplier -= movementResistance;
        // player walking speed: 0.1
        // player sprint speed: 0.13
        // negative addative will make player slower
        double additive;

        NuminaLogger.logDebug("multiplier: " + multiplier);

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
        NuminaLogger.logDebug("compute before: " + modifiers.compute(player.getAttributeBaseValue(Attributes.MOVEMENT_SPEED), EquipmentSlot.LEGS));

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

        NuminaLogger.logDebug("setting speed as: " + additive);

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

    public static double thrust(Player player, double thrust, boolean flightControl) {
        PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
        double thrustUsed = 0;
        if (flightControl) {
            Vec3 desiredDirection = player.getLookAngle().normalize();
            double strafeX = desiredDirection.z;
            double strafeZ = -desiredDirection.x;

            double flightVerticality = 0D;
            IModularItem iModularItem = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.HEAD).getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
            if (iModularItem != null) {
                ItemStack flightControlModule = iModularItem.getOnlineModuleOrEmpty(MPSConstants.FLIGHT_CONTROL_MODULE);
                IPowerModule pm = flightControlModule.getCapability(NuminaCapabilities.Module.POWER_MODULE);
                if (pm != null) {
                    flightVerticality = pm.applyPropertyModifiers(MPSConstants.FLIGHT_VERTICALITY);
                }
            }

            int strafeState = ((playerInput.strafeRightKey ? -1 : 0) + (playerInput.strafeLeftKey ? 1 : 0));
            double forwardReverse = ((playerInput.reverseKey ? -1D : 0) + (playerInput.forwardKey ? 1D : 0));

            desiredDirection = new Vec3(
                (desiredDirection.x * Math.signum(forwardReverse) + strafeX * Math.signum(strafeState)),
                (flightVerticality * desiredDirection.y * Math.signum(forwardReverse) + (playerInput.jumpKey ? 1 : 0) - (playerInput.downKey ? 1 : 0)),
                (desiredDirection.z * Math.signum(forwardReverse) + strafeZ * Math.signum(strafeState)));

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
            if (!playerInput.forwardKey) {
                player.setDeltaMovement(player.getDeltaMovement().add(0, thrust, 0));
            } else {

                //                NuminaLogger.logDebug("thrust: " + thrust +", thrust * root2: " + (thrust * root2) );

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
        double horizontalLimit = MPSCommonConfig.maxFlyingSpeed * MPSCommonConfig.maxFlyingSpeed / 400;

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
