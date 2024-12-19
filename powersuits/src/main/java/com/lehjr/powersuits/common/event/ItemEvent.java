package com.lehjr.powersuits.common.event;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

@EventBusSubscriber(modid= MPSConstants.MOD_ID)
public class ItemEvent {


    /*
     * Fired from CommonHooks.computeModifiedAttributes
     * When an ItemStack has DataComponents.ATTRIBUTE_MODIFIERS
     *
     */
    @SubscribeEvent
    public static void onAttributeEvent(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
//        if(!stack.getComponents().isEmpty()) {
//            NuminaLogger.logDebug("stack here: " + stack);
//        }
        if (stack.getItem() instanceof ArmorItem) {

            IModularItem iModularItem = NuminaCapabilities.getModularItem(stack);
            if (iModularItem != null) {
//                NuminaLogger.logDebug("ItemAttributeModifierEvent stack here: " + stack);
                ItemAttributeModifiers modifiers = getAttributeModifiers(stack);//, event);
                EquipmentSlot slot = ItemUtils.getEquipmentSlotForItem(stack);
                EquipmentSlotGroup equipmentslotgroup = EquipmentSlotGroup.bySlot(slot);

                for (ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
                    event.addModifier(entry.attribute(), entry.modifier(), equipmentslotgroup);
                }

//                if(slot== EquipmentSlot.LEGS) {
//                    DataComponentMap components = stack.getComponents();
//                    components.stream().iterator().forEachRemaining(i-> NuminaLogger.logDebug("component: " + i));
//                    NuminaLogger.logDebug("leg components " + stack.getComponents().size());
///*
//[22:01:54] [Render thread/INFO] [co.le.nu.co.ba.NuminaLogger/]: component: numina:model_spec=>{colors:[I;-1,-15642881],mps_pantaloons.leg1:{model:"mps_pantaloons",part:"leg1"},render:{colors:[I;-1,-15642881],mps_pantaloons.leg1:{model:"mps_pantaloons",part:"leg1"},mps_pantaloons.leg2:{model:"mps_pantaloons",part:"leg2"}}}
//[22:01:54] [Render thread/INFO] [co.le.nu.co.ba.NuminaLogger/]: component: minecraft:container=>net.minecraft.world.item.component.ItemContainerContents@3732c140
//[22:01:54] [Render thread/INFO] [co.le.nu.co.ba.NuminaLogger/]: component: minecraft:max_stack_size=>64
//[22:01:54] [Render thread/INFO] [co.le.nu.co.ba.NuminaLogger/]: component: minecraft:lore=>ItemLore[lines=[], styledLines=[]]
//[22:01:54] [Render thread/INFO] [co.le.nu.co.ba.NuminaLogger/]: component: minecraft:enchantments=>ItemEnchantments{enchantments={}, showInTooltip=true}
//[22:01:54] [Render thread/INFO] [co.le.nu.co.ba.NuminaLogger/]: component: minecraft:repair_cost=>0
//[22:01:54] [Render thread/INFO] [co.le.nu.co.ba.NuminaLogger/]: component: minecraft:attribute_modifiers=>ItemAttributeModifiers[modifiers=[], showInTooltip=true]
//[22:01:54] [Render thread/INFO] [co.le.nu.co.ba.NuminaLogger/]: component: minecraft:rarity=>COMMON
// */
//
//
//
//                }
            }
        }


    }




    public static ItemAttributeModifiers getAttributeModifiers(ItemStack stack) {//, ItemAttributeModifierEvent event) {
        if (!(stack.getItem() instanceof ArmorItem)) {
            return ItemAttributeModifiers.EMPTY;
        }
        EquipmentSlot slot = ItemUtils.getEquipmentSlotForItem(stack);

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
//        ItemAttributeModifiers modifiers = stack.getAttributeModifiers();
        EquipmentSlotGroup equipmentslotgroup = EquipmentSlotGroup.bySlot(slot);
        ResourceLocation resourcelocation = MovementManager.getArmorAttributeResourceLocationBySlot(slot);

        // ItemAttributeModifiers[modifiers=[Entry[attribute=Reference{ResourceKey[minecraft:attribute / minecraft:generic.armor]=
        // net.minecraft.world.entity.ai.attributes.RangedAttribute@2b7c21cc},
        // modifier=AttributeModifier[id=minecraft:armor.leggings,
        // amount=0.0, operation=ADD_VALUE],
        // slot=LEGS],
        // Entry[attribute=Reference{ResourceKey[minecraft:attribute / minecraft:generic.armor_toughness]=
        // net.minecraft.world.entity.ai.attributes.RangedAttribute@be07fe},
        // modifier=AttributeModifier[id=minecraft:armor.leggings,
        // amount=0.0,
        // operation=ADD_VALUE], slot=LEGS]], showInTooltip=true]

//        for (ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
//            builder.add(entry.attribute(), entry.modifier(), equipmentslotgroup);
//        }

        double armorVal = 0;
        double toughnessVal = 0;
        double knockbackResistance = 0;
        double speed = 0;
        double movementResistance = 0;
        double swimBoost = 0;
        float stepHeight = 0.5001F;

        IModularItem iModularItem = NuminaCapabilities.getModularItem(stack);
        if (iModularItem != null) {
            // Armor **should** only occupy one slot
            Pair<Integer, Integer> range = iModularItem.getRangeForCategory(ModuleCategory.ARMOR);
            if (range != null) {
                for (int i = range.getFirst(); i < range.getSecond(); i++) {
                    ItemStack module = iModularItem.getStackInSlot(i);
                    IPowerModule pm = iModularItem.getModuleCapability(module);
                    if (pm != null && pm.isAllowed() && pm.isModuleOnline()) {
                        // physical armor and hybrid energy/physical armor
                        armorVal += pm.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_PHYSICAL);
                        armorVal += pm.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_ENERGY);
                        knockbackResistance += pm.applyPropertyModifiers(MPSConstants.KNOCKBACK_RESISTANCE);
                        toughnessVal += pm.applyPropertyModifiers(MPSConstants.ARMOR_TOUGHNESS);




                                        /*
                                        toughness for diamond = 2 per
                                        */
                    }
                }
            }

            movementResistance = iModularItem.getMovementResistance();

            if (slot == EquipmentSlot.LEGS) {



                for (int i = 0; i < iModularItem.getSlots(); i++) {
                    /** Note: attribute should already be removed when module is offline */
                    ItemStack module = iModularItem.getStackInSlot(i);
                    IPowerModule iPowerModule = iModularItem.getModuleCapability(module);
                    if (iPowerModule != null && iPowerModule.isModuleOnline()) {

                        movementResistance += iPowerModule.applyPropertyModifiers(NuminaConstants.MOVEMENT_RESISTANCE);
                        ItemAttributeModifiers moduleModifiers = iPowerModule.getModule().getAttributeModifiers();

                        //                        NuminaLogger.logDebug("module: " + module);

//                        swimBoost += TagUtils.getModuleDouble(module, MPSConstants.SWIM_SPEED);
                        speed += iModularItem.getModuleDouble(i, MPSConstants.MOVEMENT_SPEED);
//                        NuminaLogger.logDebug("speed here: " + speed +", modular item: " + module);

                        //                        if(speed > 0) {
//                        NuminaLogger.logDebug("speed: " + speed + ", module: " + module);
                        //                        }

                        for (ItemAttributeModifiers.Entry entry : moduleModifiers.modifiers()) {
                            if (entry.attribute().is(Attributes.STEP_HEIGHT)) {
                                stepHeight = 0.5F;
                            }
                        }
                    }
                }
            }
//            else {
//                NuminaLogger.logDebug("slot: " + slot);
//            }
        } else {
            NuminaLogger.logDebug("IModularItem Cap is broken");
        }


        if (armorVal > 0) {
            builder.add(Attributes.ARMOR, new AttributeModifier(resourcelocation, armorVal, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.bySlot(slot));
        }

        if (knockbackResistance > 0) {
            builder.add(Attributes.KNOCKBACK_RESISTANCE,
                new AttributeModifier(resourcelocation, knockbackResistance, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(slot));
        }

        if (toughnessVal > 0) {
            builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourcelocation, toughnessVal, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.bySlot(slot));
            ;
        }

        if (stepHeight > 0.5001F) {
            builder.add(Attributes.STEP_HEIGHT, new AttributeModifier(resourcelocation, stepHeight, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.bySlot(slot));
        }

        if (speed != 0 || movementResistance != 0) {
            /*
                --------------------------
                kinetic gen max speed hit: 0.025 (total walking speed: 0.075) ( total running speed: 0.08775)
                --------------------------

                -----------------------
                sprint max speed boost: 0.1625 ( total: 0.34125 )
                ----------------------

                walking max speed boost: 0.1 ( total: 0.2 )
                -----------------------

                vanilla walk speed:
                -------------------
                0.13

                vanilla sprint speed:
                ---------------------
                0.1

                resistance should be up to about 80% of walking speed or 0.08
             */

//            NuminaLogger.logDebug("setting speed attribute as: " + (speed - movementResistance * 0.16));

            /*
            // Neo: Convert Movement Speed to percent-based for more appropriate display using IAttributeExtension. Use a scale factor of 1000 since movement speed has 0.001 units.
            // "generic.movement_speed", new net.neoforged.neoforge.common.PercentageAttribute("attribute.name.generic.movement_speed", 0.7, 0.0, 1024.0, 1000).setSyncable(true)
             */

            //FIXME!!!!!!!!! this only seems to be setting when player is both sprinting and jumping

            // up to 80% walking speed restriction
            builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(resourcelocation, (speed - movementResistance * 0.16), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(slot));
        }

//        if (swimBoost > 0) {
//            builder.add(NeoForgeMod.SWIM_SPEED, new AttributeModifier(resourcelocation, swimBoost, AttributeModifier.Operation.ADD_VALUE),
//                EquipmentSlotGroup.bySlot(slot));
//        }

        // Create and return ItemAttributeModifiers instance with the list of entries
        return builder.build();

    }
}
