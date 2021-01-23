package com.github.lehjr.powersuits.event;

import com.github.lehjr.numina.client.control.PlayerMovementInputWrapper;
import com.github.lehjr.numina.config.NuminaSettings;
import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.client.sound.Musique;
import com.github.lehjr.numina.util.client.sound.SoundDictionary;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.numina.util.math.MuseMathUtils;
import com.github.lehjr.numina.util.player.PlayerUtils;
import com.github.lehjr.powersuits.client.sound.MPASoundDictionary;
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
        if (playerJumpMultipliers.containsKey(player.getUniqueID())) {
            return playerJumpMultipliers.get(player.getUniqueID());
        } else {
            return 0;
        }
    }

    public void setPlayerJumpTicks(PlayerEntity player, double number) {
        playerJumpMultipliers.put(player.getUniqueID(), number);
    }

    public double computeFallHeightFromVelocity(double velocity) {
        double ticks = velocity / DEFAULT_GRAVITY;
        return -0.5 * DEFAULT_GRAVITY * ticks * ticks;
    }

    // moved here so it is still accessible if sprint assist module isn't installed.
    public void setMovementModifier(ItemStack itemStack, double multiplier, PlayerEntity player) {
        // reduce player speed according to Kinetic Energy Generator setting
        AtomicDouble movementResistance = new AtomicDouble(0);
        itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iModularItem -> {
            if (iModularItem instanceof IModularItem)
                ((IModularItem) iModularItem).getOnlineModuleOrEmpty(MPSRegistryNames.KINETIC_GENERATOR_MODULE_REGNAME).getCapability(PowerModuleCapability.POWER_MODULE)
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
                AttributeModifier attributemodifier = AttributeModifier.read(attributeTag);
                if (attributemodifier != null && attributemodifier.getName().equals(Attributes.MOVEMENT_SPEED.getAttributeName())) {
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
            itemStack.addAttributeModifier(Attributes.MOVEMENT_SPEED, new AttributeModifier(Attributes.MOVEMENT_SPEED.getAttributeName(), additive, AttributeModifier.Operation.ADDITION), EquipmentSlotType.LEGS);
        }
    }

    public double thrust(PlayerEntity player, double thrust, boolean flightControl) {
        PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
        double thrustUsed = 0;
        if (flightControl) {
            Vector3d desiredDirection = player.getLookVec().normalize();
            double strafeX = desiredDirection.z;
            double strafeZ = -desiredDirection.x;
            ItemStack helm = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
            double flightVerticality = helm.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(iModularItem -> {
                if (iModularItem instanceof IModularItem) {
                    return ((IModularItem) iModularItem)
                            .getOnlineModuleOrEmpty(MPSRegistryNames.FLIGHT_CONTROL_MODULE_REGNAME)
                            .getCapability(PowerModuleCapability.POWER_MODULE)
                            .map(pm -> pm.applyPropertyModifiers(MPSConstants.FLIGHT_VERTICALITY)).orElse(0D);
                } else {
                    return 0D;
                }
            }).orElse(0D);

            desiredDirection = new Vector3d(
                    (desiredDirection.x * Math.signum(playerInput.moveForward) + strafeX * Math.signum(playerInput.moveStrafe)),
                    (flightVerticality * desiredDirection.y * Math.signum(playerInput.moveForward) + (playerInput.jumpKey ? 1 : 0) - (playerInput.downKey ? 1 : 0)),
                    (desiredDirection.z * Math.signum(playerInput.moveForward) + strafeZ * Math.signum(playerInput.moveStrafe)));

            desiredDirection = desiredDirection.normalize();

            // Brakes
            if (player.getMotion().y < 0 && desiredDirection.y >= 0) {
                if (-player.getMotion().y > thrust) {
                    player.setMotion(player.getMotion().add(0, thrust,0));
                    thrustUsed += thrust;
                    thrust = 0;
                } else {
                    thrust -= player.getMotion().y;
                    thrustUsed += player.getMotion().y;
                    player.setMotion(player.getMotion().x, 0, player.getMotion().z);
                }
            }
            if (player.getMotion().y < -1) {
                thrust += 1 + player.getMotion().y;
                thrustUsed -= 1 + player.getMotion().y;
                player.setMotion(player.getMotion().x, -1, player.getMotion().z);
            }
            if (Math.abs(player.getMotion().x) > 0 && desiredDirection.length() == 0) {
                if (Math.abs(player.getMotion().x) > thrust) {
                    player.setMotion(player.getMotion().add(
                            - Math.signum(player.getMotion().x) * thrust, 0, 0));
                    thrustUsed += thrust;
                    thrust = 0;
                } else {
                    thrust -= Math.abs(player.getMotion().x);
                    thrustUsed += Math.abs(player.getMotion().x);
                    player.setMotion(0, player.getMotion().y, player.getMotion().z);
                }
            }
            if (Math.abs(player.getMotion().z) > 0 && desiredDirection.length() == 0) {
                if (Math.abs(player.getMotion().z) > thrust) {
                    player.setMotion(
                            player.getMotion().add(
                                    0, 0, Math.signum(player.getMotion().z) * thrust
                            )

                    );
                    thrustUsed += thrust;
                    thrust = 0;
                } else {
                    thrustUsed += Math.abs(player.getMotion().z);
                    thrust -= Math.abs(player.getMotion().z);
                    player.setMotion(player.getMotion().x, player.getMotion().y, 0);
                }
            }

            // Thrusting, finally :V
            player.setMotion(player.getMotion().add(
                    thrust * desiredDirection.x,
                    thrust * desiredDirection.y,
                    thrust * desiredDirection.z
            ));
            thrustUsed += thrust;

        } else {
            Vector3d playerHorzFacing = player.getLookVec();
            playerHorzFacing = new Vector3d(playerHorzFacing.x, 0, playerHorzFacing.z);
            playerHorzFacing.normalize();
            if (playerInput.moveForward == 0) {
                player.setMotion(player.getMotion().add(0, thrust, 0));
            } else {
                player.setMotion(player.getMotion().add(
                        playerHorzFacing.x * thrust / root2 * Math.signum(playerInput.moveForward),
                        thrust / root2,
                        playerHorzFacing.z * thrust / root2 * Math.signum(playerInput.moveForward)
                ));
            }
            thrustUsed += thrust;
        }

        // Slow the player if they are going too fast
        double horzm2 = player.getMotion().x * player.getMotion().x + player.getMotion().z * player.getMotion().z;

        // currently comes out to 0.0625
        double horizontalLimit = MPSSettings.getMaxFlyingSpeed() * MPSSettings.getMaxFlyingSpeed() / 400;

//        double playerVelocity = Math.abs(player.getMotion().x) + Math.abs(player.getMotion().y) + Math.abs(player.getMotion().z);

        if (playerInput.sneakKey && horizontalLimit > 0.05) {
            horizontalLimit = 0.05;
        }

        if (horzm2 > horizontalLimit) {
            double ratio = Math.sqrt(horizontalLimit / horzm2);
            player.setMotion(
                    player.getMotion().x * ratio,
                    player.getMotion().y,
                    player.getMotion().z * ratio);
        }
        PlayerUtils.resetFloatKickTicks(player);
        return thrustUsed;
    }

    public static double computePlayerVelocity(PlayerEntity player) {
        return MuseMathUtils.pythag(player.getMotion().x, player.getMotion().y, player.getMotion().z);
    }

   @SubscribeEvent
    public void handleLivingJumpEvent(LivingJumpEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            player.getItemStackFromSlot(EquipmentSlotType.LEGS).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iModularItem -> {
                if (!(iModularItem instanceof IModularItem)) {
                    return;
                }

                ((IModularItem) iModularItem).getOnlineModuleOrEmpty(MPSRegistryNames.JUMP_ASSIST_MODULE_REGNAME).getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(jumper -> {
                    double jumpAssist = jumper.applyPropertyModifiers(MPSConstants.MULTIPLIER) * 2;
                    double drain = jumper.applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
                    int avail = ElectricItemUtils.getPlayerEnergy(player);
                    if ((player.world.isRemote()) && NuminaSettings.useSounds()) {
                        Musique.playerSound(player, MPASoundDictionary.JUMP_ASSIST, SoundCategory.PLAYERS, (float) (jumpAssist / 8.0), (float) 1, false);
                    }

                    if (drain < avail) {
                        ElectricItemUtils.drainPlayerEnergy(player, (int) drain);
                        setPlayerJumpTicks(player, jumpAssist);
                        double jumpCompensationRatio = jumper.applyPropertyModifiers(MPSConstants.FOOD_COMPENSATION);
                        if (player.isSprinting()) {
                            player.getFoodStats().addExhaustion((float) (-0.2F * jumpCompensationRatio));
                        } else {
                            player.getFoodStats().addExhaustion((float) (-0.05F * jumpCompensationRatio));
                        }
                    }
                });
            });
        }
    }

    @SubscribeEvent
    public void handleFallEvent(LivingFallEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity && event.getDistance() > 3.0) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
            boots.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iModularItem -> {
                if (!(iModularItem instanceof IModularItem)) {
                    return;
                }

                ItemStack shockAbsorbers = ((IModularItem) iModularItem).getOnlineModuleOrEmpty(MPSRegistryNames.SHOCK_ABSORBER_MODULE_REGNAME);
                shockAbsorbers.getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(sa -> {
                    double distanceAbsorb = event.getDistance() * sa.applyPropertyModifiers(MPSConstants.MULTIPLIER);
                    if (player.world.isRemote && NuminaSettings.useSounds()) {
                        Musique.playerSound(player, SoundDictionary.SOUND_EVENT_GUI_INSTALL, SoundCategory.PLAYERS, (float) (distanceAbsorb), (float) 1, false);
                    }
                    double drain = distanceAbsorb * sa.applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
                    int avail = ElectricItemUtils.getPlayerEnergy(player);
                    if (drain < avail) {
                        ElectricItemUtils.drainPlayerEnergy(player, (int) drain);
                        event.setDistance((float) (event.getDistance() - distanceAbsorb));
//                        event.getEntityLiving().sendMessage(new TextComponentString("modified fall settings: [ damage : " + event.getDamageMultiplier() + " ], [ distance : " + event.getDistance() + " ]"));
                    }
                });
            });
        }
    }
}
