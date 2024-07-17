package lehjr.powersuits.common.item.module.movement;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Ported by leon on 10/18/16.
 */
public class SprintAssistModule extends AbstractPowerModule {
    public static class Ticker extends PlayerTickModule {
        public Ticker(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.LEGSONLY);
            // Sprinting
            addSimpleTradeoff(MPSConstants.SPRINT_ASSIST, MPSConstants.SPRINT_ENERGY_CONSUMPTION, "RF", 0, 5000, MPSConstants.SPRINT_SPEED_MULTIPLIER, "%", 0.1, 2.49);
            // Sprinting Food Compensation
            addSimpleTradeoff(MPSConstants.COMPENSATION, MPSConstants.SPRINT_ENERGY_CONSUMPTION, "RF", 0, 2000, MPSConstants.FOOD_COMPENSATION, "%", 0, 1);
            // Walking
            addSimpleTradeoff(MPSConstants.WALKING_ASSISTANCE, MPSConstants.WALKING_ENERGY_CONSUMPTION, "RF", 0, 5000, MPSConstants.WALKING_SPEED_MULTIPLIER, "%", 0.01, 1.99);
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
                            ElectricItemUtils.drainPlayerEnergy(player, (int) (sprintCost * horzMovement) * 20, false);
                        }
                        setMovementModifier(getModule(), sprintMultiplier * 0.13 * 0.5, Attributes.MOVEMENT_SPEED.value(), Attributes.MOVEMENT_SPEED.getRegisteredName());
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
                            ElectricItemUtils.drainPlayerEnergy(player, (int) (walkCost * horzMovement), false);


                        }
                        setMovementModifier(getModule(), walkMultiplier * 0.1, Attributes.MOVEMENT_SPEED.value(), Attributes.MOVEMENT_SPEED.getRegisteredName());
                        player.getAbilities().setFlyingSpeed(player.getSpeed() * 0.2f);
                    }
                }
            }
            onPlayerTickInactive(player, itemStack);
        }

        @Override
        public void onPlayerTickInactive(Player player, @Nonnull ItemStack itemStack) {
//                itemStack.removeTagKey("AttributeModifiers");
            setMovementModifier(getModule(), 0, Attributes.MOVEMENT_SPEED.value(), Attributes.MOVEMENT_SPEED.getRegisteredName());
        }
    }

    // FIXME!!! cannot be set directly on modules (create special method in itemhandler?) can only be set server side
    // moved here so it is still accessible if sprint assist module isn't installed.
    public static void setMovementModifier(ItemStack itemStack, double multiplier, Attribute attributeModifier, String key) {
        NuminaLogger.logDebug("FIXME: set Movement modifiers");

//        CompoundTag itemNBT = itemStack.getOrCreateTag();
//        boolean hasAttribute = false;
//        if (itemNBT.contains("AttributeModifiers", Tag.TAG_LIST)) {
//            ListTag listnbt = itemNBT.getList("AttributeModifiers", Tag.TAG_COMPOUND);
//            ArrayList<Integer> remove = new ArrayList();
//
//            for (int i = 0; i < listnbt.size(); ++i) {
//                CompoundTag attributeTag = listnbt.getCompound(i);
//                AttributeModifier attributemodifier = AttributeModifier.load(attributeTag);
//                if (attributemodifier != null && attributemodifier.getName().equals(key)) {
//                    // adjust the tag
//                    if (multiplier != 0) {
//                        attributeTag.putDouble("Amount", multiplier);
//                        hasAttribute = true;
//                        break;
//                    } else {
//                        // discard the tag
//                        remove.add(i);
////                        break; // leave commented for redundant tag cleanup
//                    }
//                }
//            }
//            if (hasAttribute && !remove.isEmpty()) {
//                // remove from last to first so indices are valid
//                Collections.reverse(remove);
//                remove.forEach(index -> listnbt.remove(index));
//            }
//        }
//
//        if (!hasAttribute && multiplier != 0) {
//            // FIXME: done with data components now (see: DataComponents.ATTRIBUTE_MODIFIERS)
//
////            itemStack.addAttributeModifier(attributeModifier, new AttributeModifier(key, multiplier, AttributeModifier.Operation.ADD_VALUE), EquipmentSlot.LEGS);
//        }
    }
}