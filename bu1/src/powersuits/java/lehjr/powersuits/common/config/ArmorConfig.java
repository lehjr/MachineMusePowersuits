package lehjr.powersuits.common.config;

import com.lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ArmorConfig {
    // Prototype Armor ------------------------------------------------------------------------------------------------
    // Helm
    private static final ModConfigSpec.Builder ARMOR_HELM_1__SETTINGS_BUILDER = new ModConfigSpec.Builder().push("Armor").push("Tier_1").push("Helm");
    private static final ModConfigSpec.IntValue ARMOR_HELM_1_INVENTORY_SLOTS = ARMOR_HELM_1__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_HELM_1__MAX_HEAT = ARMOR_HELM_1__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 1.0, 0, 1000.0D);

    // ChestPlate
    private static final ModConfigSpec.Builder ARMOR_CHESTPLATE_1__SETTINGS_BUILDER = ARMOR_HELM_1__SETTINGS_BUILDER.pop().push("ChestPlate");
    private static final ModConfigSpec.IntValue ARMOR_CHESTPLATE_1__INVENTORY_SLOTS = ARMOR_CHESTPLATE_1__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 6, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_CHESTPLATE_1__MAX_HEAT = ARMOR_CHESTPLATE_1__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 4.0, 0, 1000.0D);

    // Leggins
    private static final ModConfigSpec.Builder ARMOR_LEGGINGS_1__SETTINGS_BUILDER = ARMOR_CHESTPLATE_1__SETTINGS_BUILDER.pop().push("Leggings");
    private static final ModConfigSpec.IntValue ARMOR_LEGGINGS_1__INVENTORY_SLOTS = ARMOR_LEGGINGS_1__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_LEGGINGS_1__MAX_HEAT = ARMOR_LEGGINGS_1__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 3.0, 0, 1000.0D);

    // Boots
    private static final ModConfigSpec.Builder ARMOR_BOOTS_1__SETTINGS_BUILDER = ARMOR_LEGGINGS_1__SETTINGS_BUILDER.pop().push("Boots");
    private static final ModConfigSpec.IntValue ARMOR_BOOTS_1__INVENTORY_SLOTS = ARMOR_BOOTS_1__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_BOOTS_1__MAX_HEAT = ARMOR_BOOTS_1__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 1.0, 0, 1000.0D);

    // Prototype Armor Mk2 --------------------------------------------------------------------------------------------
    // Helm
    private static final ModConfigSpec.Builder ARMOR_HELM_2__SETTINGS_BUILDER = ARMOR_BOOTS_1__SETTINGS_BUILDER.pop().pop().push("Tier_2").push("Helm");
    private static final ModConfigSpec.IntValue ARMOR_HELM_2__INVENTORY_SLOTS = ARMOR_HELM_2__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_HELM_2__MAX_HEAT = ARMOR_HELM_2__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // ChestPlate
    private static final ModConfigSpec.Builder ARMOR_CHESTPLATE_2__SETTINGS_BUILDER = ARMOR_HELM_2__SETTINGS_BUILDER.pop().push("ChestPlate");
    private static final ModConfigSpec.IntValue ARMOR_CHESTPLATE_2__INVENTORY_SLOTS = ARMOR_CHESTPLATE_2__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_CHESTPLATE_2__MAX_HEAT = ARMOR_CHESTPLATE_2__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Leggins
    private static final ModConfigSpec.Builder TIER_2_ARMOR_LEGGINGS__SETTINGS_BUILDER = ARMOR_CHESTPLATE_2__SETTINGS_BUILDER.pop().push("Leggings");
    private static final ModConfigSpec.IntValue ARMOR_LEGGINGS_2__INVENTORY_SLOTS = TIER_2_ARMOR_LEGGINGS__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_LEGGINGS_2__MAX_HEAT = TIER_2_ARMOR_LEGGINGS__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Boots
    private static final ModConfigSpec.Builder TIER_2_ARMOR_BOOTS__SETTINGS_BUILDER = TIER_2_ARMOR_LEGGINGS__SETTINGS_BUILDER.pop().push("Boots");
    private static final ModConfigSpec.IntValue ARMOR_BOOTS_2__INVENTORY_SLOTS = TIER_2_ARMOR_BOOTS__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_BOOTS_2__MAX_HEAT = TIER_2_ARMOR_BOOTS__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Armor Mk3 ------------------------------------------------------------------------------------------------------
    // Helm
    private static final ModConfigSpec.Builder TIER_3_ARMOR_HELM__SETTINGS_BUILDER = TIER_2_ARMOR_BOOTS__SETTINGS_BUILDER.pop().pop().push("Tier_3").push("Helm");
    private static final ModConfigSpec.IntValue ARMOR_HELM_3__INVENTORY_SLOTS = TIER_3_ARMOR_HELM__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_HELM_3__MAX_HEAT = TIER_3_ARMOR_HELM__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // ChestPlate
    private static final ModConfigSpec.Builder TIER_3_ARMOR_CHESTPLATE__SETTINGS_BUILDER = TIER_3_ARMOR_HELM__SETTINGS_BUILDER.pop().push("ChestPlate");
    private static final ModConfigSpec.IntValue ARMOR_CHESTPLATE_3__INVENTORY_SLOTS = TIER_3_ARMOR_CHESTPLATE__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_CHESTPLATE_3__MAX_HEAT = TIER_3_ARMOR_CHESTPLATE__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Leggins
    private static final ModConfigSpec.Builder TIER_3_ARMOR_LEGGINGS__SETTINGS_BUILDER = TIER_3_ARMOR_CHESTPLATE__SETTINGS_BUILDER.pop().push("Armor Leggings");
    private static final ModConfigSpec.IntValue ARMOR_LEGGINGS_3__INVENTORY_SLOTS = TIER_3_ARMOR_LEGGINGS__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_LEGGINGS_3__MAX_HEAT = TIER_3_ARMOR_LEGGINGS__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Boots
    private static final ModConfigSpec.Builder TIER_3_ARMOR_BOOTS__SETTINGS_BUILDER = TIER_3_ARMOR_LEGGINGS__SETTINGS_BUILDER.pop().push("Tier 3 Armor Boots");
    private static final ModConfigSpec.IntValue ARMOR_BOOTS_3__INVENTORY_SLOTS = TIER_3_ARMOR_BOOTS__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_BOOTS_3__MAX_HEAT = TIER_3_ARMOR_BOOTS__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Armor Mk4 ------------------------------------------------------------------------------------------------------
    // Helm
    private static final ModConfigSpec.Builder TIER_4_ARMOR_HELM__SETTINGS_BUILDER = TIER_3_ARMOR_BOOTS__SETTINGS_BUILDER.pop().pop().push("Tier_4").push("Helm");
    private static final ModConfigSpec.IntValue ARMOR_HELM_4__INVENTORY_SLOTS = TIER_4_ARMOR_HELM__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_HELMET_4__MAX_HEAT = TIER_4_ARMOR_HELM__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // ChestPlate
    private static final ModConfigSpec.Builder TIER_4_ARMOR_CHESTPLATE__SETTINGS_BUILDER = TIER_4_ARMOR_HELM__SETTINGS_BUILDER.pop().push("ChestPlate");
    private static final ModConfigSpec.IntValue ARMOR_CHESTPLATE_4__INVENTORY_SLOTS = TIER_4_ARMOR_CHESTPLATE__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_CHESTPLATE_4__MAX_HEAT = TIER_4_ARMOR_CHESTPLATE__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Leggins
    private static final ModConfigSpec.Builder TIER_4_ARMOR_LEGGINGS__SETTINGS_BUILDER = TIER_4_ARMOR_CHESTPLATE__SETTINGS_BUILDER.pop().push("Leggings");
    private static final ModConfigSpec.IntValue ARMOR_LEGGINGS_4__INVENTORY_SLOTS = TIER_4_ARMOR_LEGGINGS__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_LEGGINGS_4__MAX_HEAT = TIER_4_ARMOR_LEGGINGS__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Boots
    private static final ModConfigSpec.Builder TIER_4_ARMOR_BOOTS__SETTINGS_BUILDER = TIER_4_ARMOR_LEGGINGS__SETTINGS_BUILDER.pop().push("Boots");
    private static final ModConfigSpec.IntValue ARMOR_BOOTS_4__INVENTORY_SLOTS = TIER_4_ARMOR_BOOTS__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 4, 0, 10);
    private static final ModConfigSpec.DoubleValue ARMOR_BOOTS_4__MAX_HEAT = TIER_4_ARMOR_BOOTS__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    public static final ModConfigSpec MPS_ARMOR_CONFIG_SPEC = TIER_4_ARMOR_BOOTS__SETTINGS_BUILDER.build();

    // Tier 1 -----------------------------------------------------------------
    public static int armorHelmInventorySlots1;
    public static double armorHelmMaxHeat1;

    public static int armorChestPlateInventorySlots1;
    public static Double armorChestPlateMaxHeat1;

    public static int armorLeggingsInventorySlots1;
    public static Double armorLeggingsMaxHeat1;

    public static int armorBootsInventorySlots1;
    public static Double armorBootsMaxHeat1;

    // Tier 2 -----------------------------------------------------------------
    public static int armorHelmInventorySlots2;
    public static Double armorHelmMaxHeat2;

    public static int armorChestPlateInventorySlots2;
    public static Double armorChestPlateMaxHeat2;

    public static int armorLeggingsInventorySlots2;
    public static Double armorLeggingsMaxHeat2;

    public static int armorBootsInventorySlots2;
    public static Double armorBootsMaxHeat2;

    // Tier 3 -----------------------------------------------------------------
    public static int armorHelmInventorySlots3;
    public static Double armorHelmMaxHeat3;

    public static int armorChestPlateInventorySlots3;
    public static Double armorChestPlateMaxHeat3;

    public static int armorLeggingsInventorySlots3;
    public static Double armorLeggingsMaxHeat3;

    public static int armorBootsInventorySlots3;
    public static Double armorBootsMaxHeat3;

    // Tier 4 -----------------------------------------------------------------
    public static int armorHelmInventorySlots4;
    public static Double armorHelmMaxHeat4;

    public static int armorChestPlateInventorySlots4;
    public static Double armorChestPlateMaxHeat4;

    public static int armorLeggingsInventorySlots4;
    public static Double armorLeggingsMaxHeat4;

    public static int armorBootsInventorySlots4;
    public static Double armorBootsMaxHeat4;



    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {

//        ModConfig config = event.getConfig();
//        //Make sure it is for the same modid as us
//        if (config.getModId().equals(MPSConstants.MOD_ID) && config instanceof ArmorConfig peConfig) {
//            peConfig.clearCache(configEvent);
//        }


        if (event.getConfig().getSpec() == MPS_ARMOR_CONFIG_SPEC) {
            // Tier1
            armorHelmInventorySlots1 = ARMOR_HELM_1_INVENTORY_SLOTS.get();
            armorHelmMaxHeat1 = ARMOR_HELM_1__MAX_HEAT.get();

            armorChestPlateInventorySlots1 = ARMOR_CHESTPLATE_1__INVENTORY_SLOTS.get();
            armorChestPlateMaxHeat1 = ARMOR_CHESTPLATE_1__MAX_HEAT.get();

            armorLeggingsInventorySlots1 = ARMOR_LEGGINGS_1__INVENTORY_SLOTS.get();
            armorLeggingsMaxHeat1 = ARMOR_LEGGINGS_1__MAX_HEAT.get();

            armorBootsInventorySlots1 = ARMOR_BOOTS_1__INVENTORY_SLOTS.get();
            armorBootsMaxHeat1 = ARMOR_BOOTS_1__MAX_HEAT.get();

            // Tier 2
            armorHelmInventorySlots2 = ARMOR_HELM_2__INVENTORY_SLOTS.get();
            armorHelmMaxHeat2 = ARMOR_HELM_2__MAX_HEAT.get();

            armorChestPlateInventorySlots2 = ARMOR_CHESTPLATE_2__INVENTORY_SLOTS.get();
            armorChestPlateMaxHeat2 = ARMOR_CHESTPLATE_2__MAX_HEAT.get();

            armorLeggingsInventorySlots2 = ARMOR_LEGGINGS_2__INVENTORY_SLOTS.get();
            armorLeggingsMaxHeat2 = ARMOR_LEGGINGS_2__MAX_HEAT.get();

            armorBootsInventorySlots2 = ARMOR_BOOTS_2__INVENTORY_SLOTS.get();
            armorBootsMaxHeat2 = ARMOR_BOOTS_2__MAX_HEAT.get();

            // Tier 3
            armorHelmInventorySlots3 = ARMOR_HELM_3__INVENTORY_SLOTS.get();
            armorHelmMaxHeat3 = ARMOR_HELM_3__MAX_HEAT.get();

            armorChestPlateInventorySlots3 = ARMOR_CHESTPLATE_3__INVENTORY_SLOTS.get();
            armorChestPlateMaxHeat3 = ARMOR_CHESTPLATE_3__MAX_HEAT.get();

            armorLeggingsInventorySlots3 = ARMOR_LEGGINGS_3__INVENTORY_SLOTS.get();
            armorLeggingsMaxHeat3 = ARMOR_LEGGINGS_3__MAX_HEAT.get();

            armorBootsInventorySlots3 = ARMOR_BOOTS_3__INVENTORY_SLOTS.get();
            armorBootsMaxHeat3 = ARMOR_BOOTS_3__MAX_HEAT.get();

            // Tier 4
            armorHelmInventorySlots4 = ARMOR_HELM_4__INVENTORY_SLOTS.get();
            armorHelmMaxHeat4 = ARMOR_HELMET_4__MAX_HEAT.get();

            armorChestPlateInventorySlots4 = ARMOR_CHESTPLATE_4__INVENTORY_SLOTS.get();
            armorChestPlateMaxHeat4 = ARMOR_CHESTPLATE_4__MAX_HEAT.get();

            armorLeggingsInventorySlots4 = ARMOR_LEGGINGS_4__INVENTORY_SLOTS.get();
            armorLeggingsMaxHeat4 = ARMOR_LEGGINGS_4__MAX_HEAT.get();

            armorBootsInventorySlots4 = ARMOR_BOOTS_4__INVENTORY_SLOTS.get();
            armorBootsMaxHeat4 = ARMOR_BOOTS_4__MAX_HEAT.get();
        }
    }
}