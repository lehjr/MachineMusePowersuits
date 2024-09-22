package com.lehjr.powersuits.common.config;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ArmorConfig {
    // Helm -----------------------------------------------------------------------------------------------------------
    private static final ModConfigSpec.Builder HELM_1__SETTINGS_BUILDER = new ModConfigSpec.Builder().push("Helm").push("Tier_1");
    private static final ModConfigSpec.IntValue HELM_1_INVENTORY_SLOTS = HELM_1__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue HELM_1__MAX_HEAT = HELM_1__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 1.0, 0, 1000.0D);

    private static final ModConfigSpec.Builder HELM_2__SETTINGS_BUILDER = HELM_1__SETTINGS_BUILDER.pop().push("Tier_2");
    private static final ModConfigSpec.IntValue HELM_2__INVENTORY_SLOTS = HELM_2__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 6, 0, 10);
    private static final ModConfigSpec.DoubleValue HELM_2__MAX_HEAT = HELM_2__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    private static final ModConfigSpec.Builder HELM_3__SETTINGS_BUILDER = HELM_2__SETTINGS_BUILDER.pop().push("Tier_3");
    private static final ModConfigSpec.IntValue HELM_3__INVENTORY_SLOTS = HELM_3__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 8, 0, 10);
    private static final ModConfigSpec.DoubleValue HELM_3__MAX_HEAT = HELM_3__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    private static final ModConfigSpec.Builder HELM_4__SETTINGS_BUILDER = HELM_3__SETTINGS_BUILDER.pop().push("Tier_4");
    private static final ModConfigSpec.IntValue HELM_4__INVENTORY_SLOTS = HELM_4__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 12, 0, 10);
    private static final ModConfigSpec.DoubleValue HELMET_4__MAX_HEAT = HELM_4__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // ChestPlate -----------------------------------------------------------------------------------------------------
    private static final ModConfigSpec.Builder CHESTPLATE_1__SETTINGS_BUILDER = HELM_4__SETTINGS_BUILDER.pop().pop().push("ChestPlate").push("Tier_1");
    private static final ModConfigSpec.IntValue CHESTPLATE_1__INVENTORY_SLOTS = CHESTPLATE_1__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 6, 0, 10);
    private static final ModConfigSpec.DoubleValue CHESTPLATE_1__MAX_HEAT = CHESTPLATE_1__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 4.0, 0, 1000.0D);

    private static final ModConfigSpec.Builder CHESTPLATE_2__SETTINGS_BUILDER = CHESTPLATE_1__SETTINGS_BUILDER.pop().push("Tier_2");
    private static final ModConfigSpec.IntValue CHESTPLATE_2__INVENTORY_SLOTS = CHESTPLATE_2__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue CHESTPLATE_2__MAX_HEAT = CHESTPLATE_2__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    private static final ModConfigSpec.Builder CHESTPLATE_3__SETTINGS_BUILDER = CHESTPLATE_2__SETTINGS_BUILDER.pop().push("Tier_3");
    private static final ModConfigSpec.IntValue CHESTPLATE_3__INVENTORY_SLOTS = CHESTPLATE_3__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue CHESTPLATE_3__MAX_HEAT = CHESTPLATE_3__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    private static final ModConfigSpec.Builder CHESTPLATE_4__SETTINGS_BUILDER = CHESTPLATE_3__SETTINGS_BUILDER.pop().push("Tier_4");
    private static final ModConfigSpec.IntValue CHESTPLATE_4__INVENTORY_SLOTS = CHESTPLATE_4__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue CHESTPLATE_4__MAX_HEAT = CHESTPLATE_4__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Leggins --------------------------------------------------------------------------------------------------------
    private static final ModConfigSpec.Builder LEGGINGS_1__SETTINGS_BUILDER = CHESTPLATE_4__SETTINGS_BUILDER.pop().pop().push("Leggings").push("Tier_1");
    private static final ModConfigSpec.IntValue LEGGINGS_1__INVENTORY_SLOTS = LEGGINGS_1__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue LEGGINGS_1__MAX_HEAT = LEGGINGS_1__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 3.0, 0, 1000.0D);

    private static final ModConfigSpec.Builder LEGGINGS_2__SETTINGS_BUILDER = LEGGINGS_1__SETTINGS_BUILDER.pop().push("Tier_2");
    private static final ModConfigSpec.IntValue LEGGINGS_2__INVENTORY_SLOTS = LEGGINGS_2__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue LEGGINGS_2__MAX_HEAT = LEGGINGS_2__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    private static final ModConfigSpec.Builder LEGGINGS_3__SETTINGS_BUILDER = LEGGINGS_2__SETTINGS_BUILDER.pop().push("Tier_3");
    private static final ModConfigSpec.IntValue LEGGINGS_3__INVENTORY_SLOTS = LEGGINGS_3__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue LEGGINGS_3__MAX_HEAT = LEGGINGS_3__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    private static final ModConfigSpec.Builder LEGGINGS_4__SETTINGS_BUILDER = LEGGINGS_3__SETTINGS_BUILDER.pop().push("Tier_4");
    private static final ModConfigSpec.IntValue LEGGINGS_4__INVENTORY_SLOTS = LEGGINGS_4__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue LEGGINGS_4__MAX_HEAT = LEGGINGS_4__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Boots ----------------------------------------------------------------------------------------------------------
    private static final ModConfigSpec.Builder BOOTS_1__SETTINGS_BUILDER = LEGGINGS_4__SETTINGS_BUILDER.pop().pop().push("Boots").push("Tier_1");
    private static final ModConfigSpec.IntValue BOOTS_1__INVENTORY_SLOTS = BOOTS_1__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue BOOTS_1__MAX_HEAT = BOOTS_1__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 1.0, 0, 1000.0D);

    private static final ModConfigSpec.Builder BOOTS_2__SETTINGS_BUILDER = BOOTS_1__SETTINGS_BUILDER.pop().push("Tier_2");
    private static final ModConfigSpec.IntValue BOOTS_2__INVENTORY_SLOTS = BOOTS_2__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue BOOTS_2__MAX_HEAT = BOOTS_2__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    private static final ModConfigSpec.Builder BOOTS_3__SETTINGS_BUILDER = BOOTS_2__SETTINGS_BUILDER.pop().push("Tier_3");
    private static final ModConfigSpec.IntValue BOOTS_3__INVENTORY_SLOTS = BOOTS_3__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue BOOTS_3__MAX_HEAT = BOOTS_3__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    private static final ModConfigSpec.Builder BOOTS_4__SETTINGS_BUILDER = BOOTS_3__SETTINGS_BUILDER.pop().push("Tier_4");
    private static final ModConfigSpec.IntValue BOOTS_4__INVENTORY_SLOTS = BOOTS_4__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue BOOTS_4__MAX_HEAT = BOOTS_4__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    public static final ModConfigSpec ARMOR_CONFIG_SPEC = BOOTS_4__SETTINGS_BUILDER.build();

    // Helm ---------------------------------------------------------------------------------------
    public static int helmInventorySlots1;
    public static double helmMaxHeat1;

    public static int helmInventorySlots2;
    public static Double helmMaxHeat2;

    public static int helmInventorySlots3;
    public static Double helmMaxHeat3;

    public static int helmInventorySlots4;
    public static Double helmMaxHeat4;

    // Chestplate ---------------------------------------------------------------------------------
    public static int chestPlateInventorySlots1;
    public static Double chestPlateMaxHeat1;

    public static int chestPlateInventorySlots2;
    public static Double chestPlateMaxHeat2;

    public static int chestPlateInventorySlots3;
    public static Double chestPlateMaxHeat3;

    public static int chestPlateInventorySlots4;
    public static Double chestPlateMaxHeat4;

    // Leggings -----------------------------------------------------------------------------------
    public static int leggingsInventorySlots1;
    public static Double leggingsMaxHeat1;

    public static int leggingsInventorySlots2;
    public static Double leggingsMaxHeat2;

    public static int leggingsInventorySlots3;
    public static Double leggingsMaxHeat3;

    public static int leggingsInventorySlots4;
    public static Double leggingsMaxHeat4;

    // Boots --------------------------------------------------------------------------------------
    public static int bootsInventorySlots1;
    public static Double bootsMaxHeat1;

    public static int bootsInventorySlots2;
    public static Double bootsMaxHeat2;

    public static int bootsInventorySlots3;
    public static Double bootsMaxHeat3;

    public static int bootsInventorySlots4;
    public static Double bootsMaxHeat4;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == ARMOR_CONFIG_SPEC) {
            // Helm
            helmInventorySlots1 = HELM_1_INVENTORY_SLOTS.get();
            helmMaxHeat1 = HELM_1__MAX_HEAT.get();

            helmInventorySlots2 = HELM_2__INVENTORY_SLOTS.get();
            helmMaxHeat2 = HELM_2__MAX_HEAT.get();

            helmInventorySlots3 = HELM_3__INVENTORY_SLOTS.get();
            helmMaxHeat3 = HELM_3__MAX_HEAT.get();

            helmInventorySlots4 = HELM_4__INVENTORY_SLOTS.get();
            helmMaxHeat4 = HELMET_4__MAX_HEAT.get();

            // Chestplate
            chestPlateInventorySlots1 = CHESTPLATE_1__INVENTORY_SLOTS.get();
            chestPlateMaxHeat1 = CHESTPLATE_1__MAX_HEAT.get();

            chestPlateInventorySlots2 = CHESTPLATE_2__INVENTORY_SLOTS.get();
            chestPlateMaxHeat2 = CHESTPLATE_2__MAX_HEAT.get();

            chestPlateInventorySlots3 = CHESTPLATE_3__INVENTORY_SLOTS.get();
            chestPlateMaxHeat3 = CHESTPLATE_3__MAX_HEAT.get();

            chestPlateInventorySlots4 = CHESTPLATE_4__INVENTORY_SLOTS.get();
            chestPlateMaxHeat4 = CHESTPLATE_4__MAX_HEAT.get();

            // Leggings
            leggingsInventorySlots1 = LEGGINGS_1__INVENTORY_SLOTS.get();
            leggingsMaxHeat1 = LEGGINGS_1__MAX_HEAT.get();

            leggingsInventorySlots2 = LEGGINGS_2__INVENTORY_SLOTS.get();
            leggingsMaxHeat2 = LEGGINGS_2__MAX_HEAT.get();

            leggingsInventorySlots3 = LEGGINGS_3__INVENTORY_SLOTS.get();
            leggingsMaxHeat3 = LEGGINGS_3__MAX_HEAT.get();

            leggingsInventorySlots4 = LEGGINGS_4__INVENTORY_SLOTS.get();
            leggingsMaxHeat4 = LEGGINGS_4__MAX_HEAT.get();


            // Boots
            bootsInventorySlots1 = BOOTS_1__INVENTORY_SLOTS.get();
            bootsMaxHeat1 = BOOTS_1__MAX_HEAT.get();

            bootsInventorySlots2 = BOOTS_2__INVENTORY_SLOTS.get();
            bootsMaxHeat2 = BOOTS_2__MAX_HEAT.get();

            bootsInventorySlots3 = BOOTS_3__INVENTORY_SLOTS.get();
            bootsMaxHeat3 = BOOTS_3__MAX_HEAT.get();

            bootsInventorySlots4 = BOOTS_4__INVENTORY_SLOTS.get();
            bootsMaxHeat4 = BOOTS_4__MAX_HEAT.get();
        }
    }
}
