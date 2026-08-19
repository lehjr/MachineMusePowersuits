package powersuits.client.model.item;

import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.registration.MPSEntities;
import lehjr.powersuits.common.registration.MPSItems;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.CustomLoaderBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import numina.client.model.item.AbstractItemModelProvider;

import java.util.Objects;

public class MPSItemModelProvider extends AbstractItemModelProvider {
    public MPSItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MPSConstants.MOD_ID, existingFileHelper);
    }

    ItemModelBuilder LUX_CAP_MODEL_ITEM;

    @Override
    protected void registerModels() {
        registerBlockItemModels();
        registerModularItemModels();
        registerModuleModels();
        registerModuleModels();
    }

    protected void registerBlockItemModels() {
        // Block Items ============================================================
        itemEntity(MPSItems.TINKER_TABLE_ITEM.get())
            .guiLight(BlockModel.GuiLight.FRONT)
            .transforms()
            .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
            .rotation( 75, 45, 0 )
            .translation( 0, 2.5F, 0)
            .scale(0.38F).end()

            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
            .rotation( 75, 45, 0 )
            .translation( 0, 2.5F, 0)
            .scale(0.38F).end()

            .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
            .rotation( 0, 225, 0 )
            .translation( 0, 0, 0)
            .scale(0.4F).end()

            .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
            .rotation( 0, 45, 0 )
            .translation( 0, 0, 0)
            .scale(0.4F).end()

            .transform(ItemDisplayContext.GUI)
            .rotation( 30, 225, 0 )
            .translation( 0, 0, 0)
            .scale(0.63F).end()

            .transform(ItemDisplayContext.FIXED)
            .rotation(0, 0, 0)
            .translation( 0, 0, 0)
            .scale(0.5F).end()

            .transform(ItemDisplayContext.GROUND)
            .rotation(0, 0, 0)
            .translation( 0, 3, 0)
            .scale(0.25F).end();

        LUX_CAP_MODEL_ITEM = blockItemEntity(MPSItems.LUX_CAPACITOR_ITEM.get(), modLoc("block/luxcapacitor"))
            .transforms()
            .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
            .rotation(0, 0, 45)
            .translation(0, 2, 3)
            .scale(0.5F).end()

            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
            .rotation(0, 0, 45)
            .translation(0, 2, 3)
            .scale(0.5F).end()

            .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
            .rotation(-25, -90, 0)
            .translation(1.13F, 3.2F, 1.13F)
            .scale(0.68F).end()

            .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
            .rotation(0, -90, 25)
            .translation(1.13F, 3.2F, 1.13F)
            .scale(0.68F).end()

            .transform(ItemDisplayContext.GUI)
            .rotation(-45, 0, 45)
            .translation(0, 2.75F, 0)
            .scale(0.625F).end()

            .transform(ItemDisplayContext.FIXED)
            .rotation(0, 180, 0)
            .scale(1).end()

            .transform(ItemDisplayContext.GROUND)
            .rotation(-90, 0, 0)
            .translation(0, 2, 0)
            .scale(0.5F).end().end();


        // Append this inside your registerModels() method
        getBuilder(MPSEntities.PLASMA_BALL_ENTITY_TYPE.get())
            .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
            .texture("particle", ResourceLocation.fromNamespaceAndPath("numina", "block/white"))
            .customLoader((parent, existingFileHelper) ->
                new CustomLoaderBuilder<>(ResourceLocation.fromNamespaceAndPath("numina", "obj"), parent, existingFileHelper, false) {
                    @Override
                    public com.google.gson.JsonObject toJson(com.google.gson.JsonObject json) {
                        super.toJson(json);
                        json.addProperty("model", MPSConstants.MOD_ID + ":models/entity/obj/sphere.obj");
                        json.addProperty("flip_v", true);
                        return json;
                    }
                }
            );
    }




    protected void registerModularItemModels() {
        // Power Fist =============================================================
        powerFist(MPSItems.POWER_FIST_1.get(), "item/tool/handitem");
        powerFist(MPSItems.POWER_FIST_2.get(), "item/tool/handitem");
        powerFist(MPSItems.POWER_FIST_3.get(), "item/tool/handitem");
        powerFist(MPSItems.POWER_FIST_4.get(), "item/tool/handitem");
        // TODO: check for:
        //            "parent": "item/generated",
        //            "textures": {
        //            "layer0": "powersuits:item/tool/handitem"

        // Armor ==================================================================
        this.basicItem(MPSItems.POWER_ARMOR_HELMET_1.get(), "item/armor/powerarmor.head");
        this.basicItem(MPSItems.POWER_ARMOR_HELMET_2.get(), "item/armor/powerarmor.head");
        this.basicItem(MPSItems.POWER_ARMOR_HELMET_3.get(), "item/armor/powerarmor.head");
        this.basicItem(MPSItems.POWER_ARMOR_HELMET_4.get(), "item/armor/powerarmor.head");

        this.basicItem(MPSItems.POWER_ARMOR_CHESTPLATE_1.get(), "item/armor/powerarmor.torso");
        this.basicItem(MPSItems.POWER_ARMOR_CHESTPLATE_2.get(), "item/armor/powerarmor.torso");
        this.basicItem(MPSItems.POWER_ARMOR_CHESTPLATE_3.get(), "item/armor/powerarmor.torso");
        this.basicItem(MPSItems.POWER_ARMOR_CHESTPLATE_4.get(), "item/armor/powerarmor.torso");

        this.basicItem(MPSItems.POWER_ARMOR_LEGGINGS_1.get(), "item/armor/powerarmor.legs");
        this.basicItem(MPSItems.POWER_ARMOR_LEGGINGS_2.get(), "item/armor/powerarmor.legs");
        this.basicItem(MPSItems.POWER_ARMOR_LEGGINGS_3.get(), "item/armor/powerarmor.legs");
        this.basicItem(MPSItems.POWER_ARMOR_LEGGINGS_4.get(), "item/armor/powerarmor.legs");

        this.basicItem(MPSItems.POWER_ARMOR_BOOTS_1.get(), "item/armor/powerarmor.feet");
        this.basicItem(MPSItems.POWER_ARMOR_BOOTS_2.get(), "item/armor/powerarmor.feet");
        this.basicItem(MPSItems.POWER_ARMOR_BOOTS_3.get(), "item/armor/powerarmor.feet");
        this.basicItem(MPSItems.POWER_ARMOR_BOOTS_4.get(), "item/armor/powerarmor.feet");
    }

    protected void registerModuleModels() {
        // Modules ================================================================
        // Armor ----------------------------------------------
        this.basicItem(MPSItems.IRON_PLATING_MODULE.get(), "item/module/armor/plating.iron");
        this.basicItem(MPSItems.DIAMOND_PLATING_MODULE.get(), "item/module/armor/plating.diamond");
        this.basicItem(MPSItems.NETHERITE_PLATING_MODULE.get(), "item/module/armor/plating.netherite");
        this.basicItem(MPSItems.ENERGY_SHIELD_MODULE.get(), "item/module/armor/energy_shield");

        //Cosmetic --------------------------------------------
        this.basicItem(MPSItems.TRANSPARENT_ARMOR_MODULE.get(), "item/module/cosmetic/transparent_armor");

        // Energy Generation --------------------------------------------------------------------------
        // Thermal
        this.basicItem(MPSItems.THERMAL_GENERATOR_MODULE_1.get(), "item/module/energy/generation/thermalgenerator");
        this.basicItem(MPSItems.THERMAL_GENERATOR_MODULE_2.get(), "item/module/energy/generation/thermalgenerator");
        this.basicItem(MPSItems.THERMAL_GENERATOR_MODULE_3.get(), "item/module/energy/generation/thermalgenerator");
        this.basicItem(MPSItems.THERMAL_GENERATOR_MODULE_4.get(), "item/module/energy/generation/thermalgenerator");

        // Solar
        this.basicItem(MPSItems.SOLAR_GENERATOR_MODULE_1.get(), "item/module/energy/generation/generator.solar1");
        this.basicItem(MPSItems.SOLAR_GENERATOR_MODULE_2.get(), "item/module/energy/generation/generator.solar2");
        this.basicItem(MPSItems.SOLAR_GENERATOR_MODULE_3.get(), "item/module/energy/generation/generator.solar3");
        this.basicItem(MPSItems.SOLAR_GENERATOR_MODULE_4.get(), "item/module/energy/generation/generator.solar4");

        // Environmental --------------------------------------
        this.basicItem(MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get(), "item/module/environmental/invisibility");
        this.basicItem(MPSItems.AUTO_FEEDER_MODULE.get(), "item/module/environmental/auto_feeder");

        this.basicItemMC(MPSItems.COOLANT_TANK_MODULE_1.get(), "item/water_bucket");
        this.basicItemMC(MPSItems.COOLANT_TANK_MODULE_2.get(), "item/water_bucket");
        this.basicItemMC(MPSItems.COOLANT_TANK_MODULE_3.get() ,"item/water_bucket");
        this.basicItemMC(MPSItems.COOLANT_TANK_MODULE_4.get(), "item/water_bucket");

        this.basicItem(MPSItems.MAGNET_MODULE.get(), "item/module/environmental/magnet");
        this.basicItem(MPSItems.MOB_REPULSOR_MODULE.get(), "item/module/environmental/mob_repulsor");
        this.basicItemMC(MPSItems.PIGLIN_PACIFICATION_MODULE.get(), "item/gold_nugget");
        this.basicItem(MPSItems.WATER_ELECTROLYZER_MODULE.get(), "item/module/environmental/water_electrolyzer");

        // Mining Enchantment ---------------------------------
        this.basicItem(MPSItems.AQUA_AFFINITY_MODULE.get(), "item/module/mining_enchantment/aqua_affinity");
        this.basicItem(MPSItems.FORTUNE_MODULE.get(), "item/module/mining_enchantment/fortune");
        this.basicItem(MPSItems.SILK_TOUCH_MODULE.get(), "item/module/mining_enchantment/silk");

        // Mining Enhancement ---------------------------------
        this.basicItem(MPSItems.TUNNEL_BORE_MODULE.get(), "item/module/mining_enhancement/tunnel_bore");
        this.basicItemMC(MPSItems.VEIN_MINER_MODULE.get(), "item/golden_pickaxe");
        this.basicItem(MPSItems.SELECTIVE_MINER_MODULE.get(), "item/module/mining_enhancement/selective_miner");

        // Movement -------------------------------------------
        this.basicItem(MPSItems.BLINK_DRIVE_MODULE.get(), "item/module/movement/blink_drive");
        this.basicItem(MPSItems.CLIMB_ASSIST_MODULE.get(), "item/module/movement/climb_assist");
        this.basicItem(MPSItems.DIMENSIONAL_RIFT_MODULE.get(), "item/module/movement/dim_rift_gen");
        this.basicItem(MPSItems.FLIGHT_CONTROL_MODULE.get(),  "item/module/movement/flight_control");
        this.basicItem(MPSItems.GLIDER_MODULE.get(), "item/module/movement/glider");
        this.basicItem(MPSItems.JET_BOOTS_MODULE.get(), "item/module/movement/jet_boots");
        this.basicItem(MPSItems.JETPACK_MODULE.get(), "item/module/movement/jetpack");
        this.basicItem(MPSItems.JUMP_ASSIST_MODULE.get(), "item/module/movement/jump_assist");
        this.basicItem(MPSItems.PARACHUTE_MODULE.get(), "item/module/movement/parachute");
        this.basicItem(MPSItems.SHOCK_ABSORBER_MODULE.get(), "item/module/movement/shock_absorber");
        this.basicItem(MPSItems.SPRINT_ASSIST_MODULE.get(), "item/module/movement/sprint_assist");
        this.basicItem(MPSItems.SWIM_ASSIST_MODULE.get(), "item/module/movement/swim_assist");

        // Tools ----------------------------------------------
        // Axe
        this.basicItemMC(MPSItems.STONE_AXE_MODULE.get(), "item/stone_axe");
        this.basicItemMC(MPSItems.IRON_AXE_MODULE.get(), "item/iron_axe");
        this.basicItemMC(MPSItems.DIAMOND_AXE_MODULE.get(), "item/diamond_axe");
        this.basicItemMC(MPSItems.NETHERITE_AXE_MODULE.get(), "item/netherite_axe");

        // Shovel
        this.basicItemMC(MPSItems.STONE_SHOVEL_MODULE.get(), "item/stone_shovel");
        this.basicItemMC(MPSItems.IRON_SHOVEL_MODULE.get(), "item/iron_shovel");
        this.basicItemMC(MPSItems.DIAMOND_SHOVEL_MODULE.get(), "item/diamond_shovel");
        this.basicItemMC(MPSItems.NETHERITE_SHOVEL_MODULE.get(), "item/netherite_shovel");

        // Farming --------------------------------------------
        this.basicItemMC(MPSItems.STONE_ROTOTILLER_MODULE.get(), "item/stone_hoe");
        this.basicItemMC(MPSItems.IRON_ROTOTILLER_MODULE.get(), "item/iron_hoe");
        this.basicItemMC(MPSItems.DIAMOND_ROTOTILLER_MODULE.get(), "item/diamond_hoe");
        this.basicItemMC(MPSItems.NETHERITE_ROTOTILLER_MODULE.get(), "item/netherite_hoe");

        // Shears
        this.basicItem(MPSItems.SHEARS_MODULE.get(), "item/module/tool/shears");

        // PickAxe --------------------------------------------
        this.basicItemMC(MPSItems.STONE_PICKAXE_MODULE.get(), "item/stone_pickaxe");
        this.basicItemMC(MPSItems.IRON_PICKAXE_MODULE.get(), "item/iron_pickaxe");
        this.basicItemMC(MPSItems.DIAMOND_PICKAXE_MODULE.get(), "item/diamond_pickaxe");
        this.basicItemMC(MPSItems.NETHERITE_PICKAXE_MODULE.get(), "item/netherite_pickaxe");

        // Misc -----------------------------------------------
        this.basicItemMC(MPSItems.FLINT_AND_STEEL_MODULE.get(), "item/flint_and_steel");
        this.basicItem(MPSItems.LEAF_BLOWER_MODULE.get(),  "item/module/tool/leaf_blower");
        this.basicItem(MPSItems.LUX_CAPACITOR_MODULE.get(),  "block/luxlight").parent(LUX_CAP_MODEL_ITEM);

        // Vision ---------------------------------------------
        this.basicItem(MPSItems.BINOCULARS_MODULE.get(), "item/module/vision/binoculars");
        this.basicItem(MPSItems.NIGHTVISION_MODULE.get(), "item/module/vision/night_vision");

        // Weapon ---------------------------------------------
        this.basicItem(MPSItems.BLADE_LAUNCHER_MODULE.get(),  "item/module/weapon/spinningblade");
        this.basicItem(MPSItems.LIGHTNING_SUMMONER_MODULE.get(), "item/module/weapon/lightning");
        this.basicItem(MPSItems.MELEE_ASSIST_MODULE.get(), "item/module/weapon/melee_assist");
        this.basicItem(MPSItems.PLASMA_CANNON_MODULE.get(), "item/module/weapon/plasma_cannon");
        this.basicItem(MPSItems.RAILGUN_MODULE.get(), "item/module/weapon/railgun");
    }



    protected void powerFist(Item item, String texture) {
        blockItemEntity(item,  Objects.requireNonNull(mcLoc("item/generated")))
            .texture("layer0", modLoc(texture))
            .transforms()

            .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
            .rotation(165, 0, 0)
            .translation(-5.36F, -6.56F, -2.46F)
            .scale(0.67F).end()

            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
            .rotation(165, 0, 0)
            .translation(5.36F, -6.56F, -2.46F)
            .scale(0.67F).end()

            .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
            .rotation(176, 353, 0)
            .translation(-4.31F, -0.25F, -0.08F)
            .scale(0.4F).end()

            .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
            .rotation(176, 353, 0)
            .translation(1.75F, -0.25F, -0.75F)
            .scale(0.4F).end()

            .transform(ItemDisplayContext.GUI)
            .rotation(0, 0, 0)
            .translation(0, 0, 0)
            .scale(1).end()

            .transform(ItemDisplayContext.FIXED)
            .rotation(105, 180, 0)
            .translation(-5.25F, 0.75F, 3.50F)
            .scale(0.6F).end()

            .transform(ItemDisplayContext.GROUND)
            .rotation(160, 0, 0)
            .translation(2, 1, -1.75F)
            .scale(0.25F).end();
    }

    ItemModelBuilder getBuilder(EntityType entity) {
        return getBuilder(BuiltInRegistries.ENTITY_TYPE.getKey(entity).getPath());
    }
}
