package powersuits.common.recipes;

import com.lehjr.numina.common.base.NuminaObjects;
import com.lehjr.powersuits.common.registration.MPSItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.Tags;
import numina.common.recipes.ShapedEnchantmentRecipeBuilder;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class MPSRecipeGenerator extends RecipeProvider {
    public MPSRecipeGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@Nonnull RecipeOutput output) {

        // Tinker Table -------------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.TINKER_TABLE_ITEM.get())
                .pattern("IBI")
                .pattern("BCB")
                .pattern("GBG")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', Tags.Items.DYES_BLUE)
                .define('C', Items.CRAFTING_TABLE)
                .define('G', Tags.Items.INGOTS_GOLD)
                .unlockedBy(getHasName(Items.CRAFTING_TABLE), has(Items.CRAFTING_TABLE))
                .save(output);

        // Power Fist ---------------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.POWER_FIST_1.get())
                .pattern(" W ")
                .pattern("WIC")
                .pattern(" IW")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', Items.STRING)
                .define('C', NuminaObjects.CONTROL_CIRCUIT_1.get())
                .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.POWER_FIST_2.get())
                .pattern(" W ")
                .pattern("WIC")
                .pattern(" IW")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .unlockedBy(getHasName(NuminaObjects.WIRING_COPPER.get()), has(NuminaObjects.WIRING_COPPER.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.POWER_FIST_3.get())
                .pattern(" W ")
                .pattern("WIC")
                .pattern(" IW")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .unlockedBy(getHasName(NuminaObjects.WIRING_GOLD.get()), has(NuminaObjects.WIRING_GOLD.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.POWER_FIST_4.get())
                .pattern(" W ")
                .pattern("WIC")
                .pattern(" IW")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .unlockedBy(getHasName(NuminaObjects.WIRING_GOLD.get()), has(NuminaObjects.WIRING_GOLD.get()))
                .save(output);

        // Armor ==========================================================================================================
        // Prototype Modular Armor ------------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_HELMET_1.get())
                .pattern("III")
                .pattern("WCW")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', Items.STRING)
                .define('C', NuminaObjects.CONTROL_CIRCUIT_1.get())
                .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_CHESTPLATE_1.get())
                .pattern("ICI")
                .pattern("WIW")
                .pattern("III")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', Items.STRING)
                .define('C', NuminaObjects.CONTROL_CIRCUIT_1.get())
                .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_LEGGINGS_1.get())
                .pattern("III")
                .pattern("WCW")
                .pattern("I I")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', Items.STRING)
                .define('C', NuminaObjects.CONTROL_CIRCUIT_1.get())
                .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_BOOTS_1.get())
                .pattern("ICI")
                .pattern("W W")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', Items.STRING)
                .define('C', NuminaObjects.CONTROL_CIRCUIT_1.get())
                .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                .save(output);

        // Prototype Power Armor Mk2 ----------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_HELMET_2.get())
                .pattern("III")
                .pattern("WCW")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .unlockedBy(getHasName(NuminaObjects.WIRING_COPPER.get()), has(NuminaObjects.WIRING_COPPER.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_CHESTPLATE_2.get())
                .pattern("ICI")
                .pattern("WIW")
                .pattern("III")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .unlockedBy(getHasName(NuminaObjects.WIRING_COPPER.get()), has(NuminaObjects.WIRING_COPPER.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_LEGGINGS_2.get())
                .pattern("III")
                .pattern("WCW")
                .pattern("I I")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .unlockedBy(getHasName(NuminaObjects.WIRING_COPPER.get()), has(NuminaObjects.WIRING_COPPER.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_BOOTS_2.get())
                .pattern("ICI")
                .pattern("W W")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .unlockedBy(getHasName(NuminaObjects.WIRING_COPPER.get()), has(NuminaObjects.WIRING_COPPER.get()))
                .save(output);

        // Prototype Power Armor Mk3 ----------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_HELMET_3.get())
                .pattern("III")
                .pattern("WCW")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .unlockedBy(getHasName(NuminaObjects.WIRING_GOLD.get()), has(NuminaObjects.WIRING_GOLD.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_CHESTPLATE_3.get())
                .pattern("ICI")
                .pattern("WIW")
                .pattern("III")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .unlockedBy(getHasName(NuminaObjects.WIRING_GOLD.get()), has(NuminaObjects.WIRING_GOLD.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_LEGGINGS_3.get())
                .pattern("III")
                .pattern("WCW")
                .pattern("I I")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .unlockedBy(getHasName(NuminaObjects.WIRING_GOLD.get()), has(NuminaObjects.WIRING_GOLD.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_BOOTS_3.get())
                .pattern("ICI")
                .pattern("W W")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .unlockedBy(getHasName(NuminaObjects.WIRING_GOLD.get()), has(NuminaObjects.WIRING_GOLD.get()))
                .save(output);

        // Prototype Power Armor Mk4 ----------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_HELMET_4.get())
                .pattern("III")
                .pattern("WCW")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .unlockedBy(getHasName(NuminaObjects.WIRING_GOLD.get()), has(NuminaObjects.WIRING_GOLD.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_CHESTPLATE_4.get())
                .pattern("ICI")
                .pattern("WIW")
                .pattern("III")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .unlockedBy(getHasName(NuminaObjects.WIRING_GOLD.get()), has(NuminaObjects.WIRING_GOLD.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_LEGGINGS_4.get())
                .pattern("III")
                .pattern("WCW")
                .pattern("I I")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .unlockedBy(getHasName(NuminaObjects.WIRING_GOLD.get()), has(NuminaObjects.WIRING_GOLD.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_BOOTS_4.get())
                .pattern("ICI")
                .pattern("W W")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .unlockedBy(getHasName(NuminaObjects.WIRING_GOLD.get()), has(NuminaObjects.WIRING_GOLD.get()))
                .save(output);

        // Modules ========================================================================================================
        // Armor --------------------------------------------------------------------------------------------
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, MPSItems.IRON_PLATING_MODULE.get())
                .requires(NuminaObjects.PLATING_IRON.get(), 3)
                .unlockedBy(getHasName(NuminaObjects.PLATING_IRON.get()), has(NuminaObjects.PLATING_IRON.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, MPSItems.DIAMOND_PLATING_MODULE.get())
                .requires(NuminaObjects.PLATING_DIAMOND.get(), 3)
                .unlockedBy(getHasName(NuminaObjects.PLATING_DIAMOND.get()), has(NuminaObjects.PLATING_DIAMOND.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, MPSItems.NETHERITE_PLATING_MODULE.get())
                .requires(NuminaObjects.PLATING_NETHERITE.get(), 3)
                .unlockedBy(getHasName(NuminaObjects.PLATING_NETHERITE.get()), has(NuminaObjects.PLATING_NETHERITE.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.ENERGY_SHIELD_MODULE.get())
                .pattern("WEW")
                .pattern("WCW")
                .pattern("WEW")
                .define('E', NuminaObjects.FIELD_EMITTER.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .unlockedBy(getHasName(NuminaObjects.FIELD_EMITTER.get()), has(NuminaObjects.FIELD_EMITTER.get()))
                .save(output);

        // Cosmetic -----------------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.TRANSPARENT_ARMOR_MODULE.get())
                .pattern("WW")
                .pattern("LC")
                .pattern("WW")
                .define('L', NuminaObjects.LASER_EMITTER.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .unlockedBy(getHasName(NuminaObjects.FIELD_EMITTER.get()), has(NuminaObjects.FIELD_EMITTER.get()))
                .save(output);


        // Energy Generation --------------------------------------------------------------------------
        // TODO

        // Environmental ------------------------------------------------------------------------------
        ShapedEnchantmentRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get())
                .pattern("LCL")
                .pattern("CMC")
                .pattern("LCL")
                .define('L', NuminaObjects.LASER_EMITTER.get())
                .define('M', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .define('C', NuminaObjects.COMPUTER_CHIP.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_3.get()), has(NuminaObjects.CONTROL_CIRCUIT_3.get()))
                .save(output);

        ShapedEnchantmentRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.AUTO_FEEDER_MODULE.get())
                .pattern("FOF")
                .pattern("RCR")
                .pattern("SMS")
                .define('F', NuminaObjects.SOLENOID.get())
                .define('O', Tags.Items.CHESTS)
                .define('R', NuminaObjects.WIRING_COPPER.get())
                .define('S', NuminaObjects.SERVO.get())
                .define('M', NuminaObjects.RUBBER_HOSE.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_2.get()), has(NuminaObjects.CONTROL_CIRCUIT_2.get()))
                .save(output);

        ShapedEnchantmentRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.MAGNET_MODULE.get())
                .pattern("IMI")
                .pattern("WCW")
                .pattern("IMI")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('M', NuminaObjects.MAGNET.get())
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_2.get()), has(NuminaObjects.CONTROL_CIRCUIT_2.get()))
                .save(output);

        ShapedEnchantmentRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.MOB_REPULSOR_MODULE.get())
                .pattern("FWF")
                .pattern("WCW")
                .pattern("FWF")
                .define('F', NuminaObjects.FIELD_EMITTER.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_3.get()), has(NuminaObjects.CONTROL_CIRCUIT_3.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.PIGLIN_PACIFICATION_MODULE.get())
                .pattern(" S ")
                .pattern(" G ")
                .define('S', Tags.Items.STRINGS)
                .define('G', Tags.Items.NUGGETS_GOLD)
                .unlockedBy(getHasName(Items.GOLD_NUGGET), has(Items.GOLD_NUGGET))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.WATER_ELECTROLYZER_MODULE.get())
                .pattern("WLW")
                .pattern("OCO")
                .pattern("WRW")
                .define('L', NuminaObjects.CAPACITOR_1.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('R', NuminaObjects.RUBBER_HOSE.get())
                .define('O', NuminaObjects.SOLENOID.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_2.get()), has(NuminaObjects.CONTROL_CIRCUIT_2.get()))
                .save(output);

        // Mining Enchantments ------------------------------------------------------------------------
        // FIXME: needs tags for books
        ShapedEnchantmentRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.AQUA_AFFINITY_MODULE.get())
                .pattern("ENE")
                .pattern("WCW")
                .pattern("EEE")
                .define('E', NuminaObjects.COMPUTER_CHIP.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('N', Tags.Items.INGOTS_IRON, Enchantments.AQUA_AFFINITY, 1)
                .unlockedBy(getHasName(Items.ENCHANTED_BOOK), has(Items.ENCHANTED_BOOK))
                .save(output);

        ShapedEnchantmentRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.FORTUNE_MODULE.get())
                .pattern("ENE")
                .pattern("WCW")
                .pattern("EEE")
                .define('E', NuminaObjects.COMPUTER_CHIP.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('N', Tags.Items.INGOTS_IRON, Enchantments.FORTUNE, 3)
                .unlockedBy(getHasName(Items.ENCHANTED_BOOK), has(Items.ENCHANTED_BOOK))
                .save(output);

        ShapedEnchantmentRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.SILK_TOUCH_MODULE.get())
                .pattern("ENE")
                .pattern("WCW")
                .pattern("EEE")
                .define('E', NuminaObjects.COMPUTER_CHIP.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('N', Tags.Items.INGOTS_IRON, Enchantments.SILK_TOUCH, 3)
                .unlockedBy(getHasName(Items.ENCHANTED_BOOK), has(Items.ENCHANTED_BOOK))
                .save(output);

        // Mining Enhancements ------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.TUNNEL_BORE_MODULE.get())
                .pattern("SES")
                .pattern("ECE")
                .pattern("SES")
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .define('S', NuminaObjects.SERVO.get())
                .define('E', NuminaObjects.SOLENOID.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_2.get()), has(NuminaObjects.CONTROL_CIRCUIT_2.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.VEIN_MINER_MODULE.get())
                .pattern("CSC")
                .pattern("SMS")
                .pattern("CSC")
                .define('C', NuminaObjects.COMPUTER_CHIP.get())
                .define('S', NuminaObjects.SERVO.get())
                .define('M', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_3.get()), has(NuminaObjects.CONTROL_CIRCUIT_3.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.SELECTIVE_MINER_MODULE.get())
                .pattern("OCO")
                .pattern("GMG")
                .pattern("SGS")
                .define('C', NuminaObjects.COMPUTER_CHIP.get())
                .define('S', NuminaObjects.SERVO.get())
                .define('O', NuminaObjects.SOLENOID.get())
                .define('M', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .define('G', NuminaObjects.WIRING_GOLD.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_3.get()), has(NuminaObjects.CONTROL_CIRCUIT_3.get()))
                .save(output);

        // Movement -----------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.BLINK_DRIVE_MODULE.get())
                .pattern("CIC")
                .pattern("FPF")
                .pattern("WIW")
                .define('C', NuminaObjects.COMPUTER_CHIP.get())
                .define('P', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .define('F', NuminaObjects.FIELD_EMITTER.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('I', NuminaObjects.ION_THRUSTER.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_2.get()), has(NuminaObjects.CONTROL_CIRCUIT_2.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.CLIMB_ASSIST_MODULE.get())
                .pattern("W W")
                .pattern("SCS")
                .pattern("W W")
                .define('S', NuminaObjects.SOLENOID.get())
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_1.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_1.get()), has(NuminaObjects.CONTROL_CIRCUIT_1.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.DIMENSIONAL_RIFT_MODULE.get())
                .pattern("ECE")
                .pattern("COC")
                .pattern("ECE")
                .define('C', NuminaObjects.COMPUTER_CHIP.get())
                .define('E', Items.ENDER_EYE)
                .define('O', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_4.get()), has(NuminaObjects.CONTROL_CIRCUIT_4.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.FLIGHT_CONTROL_MODULE.get())
                .pattern("CWC")
                .pattern("WOW")
                .pattern("CWC")
                .define('C', NuminaObjects.COMPUTER_CHIP.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('O', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_4.get()), has(NuminaObjects.CONTROL_CIRCUIT_4.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.GLIDER_MODULE.get())
                .pattern("SWS")
                .pattern("GCG")
                .pattern("SWS")
                .define('C', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .define('G', NuminaObjects.GLIDER_WING.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('S', NuminaObjects.SERVO.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_4.get()), has(NuminaObjects.CONTROL_CIRCUIT_4.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.JET_BOOTS_MODULE.get())
                .pattern("S S")
                .pattern("CMC")
                .pattern("IWI")
                .define('S', NuminaObjects.SOLENOID.get())
                .define('M', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .define('C', NuminaObjects.COMPUTER_CHIP.get())
                .define('I', NuminaObjects.ION_THRUSTER.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_3.get()), has(NuminaObjects.CONTROL_CIRCUIT_3.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.JETPACK_MODULE.get())
                .pattern("ISI")
                .pattern("WCW")
                .pattern("ISI")
                .define('I', NuminaObjects.ION_THRUSTER.get())
                .define('S', NuminaObjects.SERVO.get())
                .define('W', NuminaObjects.COMPUTER_CHIP.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_3.get()), has(NuminaObjects.CONTROL_CIRCUIT_3.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.JUMP_ASSIST_MODULE.get())
                .pattern("SWS")
                .pattern("WCW")
                .pattern("SWS")
                .define('S', NuminaObjects.SOLENOID.get())
                .define('W', Items.WHITE_WOOL)
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_2.get()), has(NuminaObjects.CONTROL_CIRCUIT_2.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.PARACHUTE_MODULE.get())
                .pattern("SGS")
                .pattern("PCP")
                .pattern("GSG")
                .define('S', NuminaObjects.SERVO.get())
                .define('G', Tags.Items.STRINGS)
                .define('P', NuminaObjects.PARACHUTE.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_1.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_1.get()), has(NuminaObjects.CONTROL_CIRCUIT_1.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.SHOCK_ABSORBER_MODULE.get())
                .pattern("S S")
                .pattern("SCS")
                .pattern("W W")
                .define('S', NuminaObjects.SOLENOID.get())
                .define('W', Items.WHITE_WOOL)
                .define('C', NuminaObjects.CONTROL_CIRCUIT_1.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_1.get()), has(NuminaObjects.CONTROL_CIRCUIT_1.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.SPRINT_ASSIST_MODULE.get())
                .pattern("SNS")
                .pattern("NMN")
                .pattern("SCS")
                .define('S', NuminaObjects.SERVO.get())
                .define('N', NuminaObjects.SOLENOID.get())
                .define('C', NuminaObjects.COMPUTER_CHIP.get())
                .define('M', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_3.get()), has(NuminaObjects.CONTROL_CIRCUIT_3.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.SWIM_ASSIST_MODULE.get())
                .pattern("SHS")
                .pattern("WCW")
                .pattern("HSH")
                .define('S', NuminaObjects.SERVO.get())
                .define('H', NuminaObjects.RUBBER_HOSE.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_3.get()), has(NuminaObjects.CONTROL_CIRCUIT_3.get()))
                .save(output);

        // Tools ------------------------------------------------------------------------------------------------
        // Axes -----------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.STONE_AXE_MODULE.get())
                .pattern("SSW")
                .pattern("SCW")
                .pattern(" X ")
                .define('S', Tags.Items.COBBLESTONES)
                .define('C', NuminaObjects.CONTROL_CIRCUIT_1.get())
                .define('W', Tags.Items.STRINGS)
                .define('X', Items.STICK)
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_1.get()), has(NuminaObjects.CONTROL_CIRCUIT_1.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.IRON_AXE_MODULE.get())
                .pattern("IIW")
                .pattern("ICW")
                .pattern(" S ")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', NuminaObjects.SERVO.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_2.get()), has(NuminaObjects.CONTROL_CIRCUIT_2.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.DIAMOND_AXE_MODULE.get())
                .pattern("DDW")
                .pattern("DCW")
                .pattern(" S ")
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('S', NuminaObjects.SERVO.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_3.get()), has(NuminaObjects.CONTROL_CIRCUIT_3.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.NETHERITE_AXE_MODULE.get())
                .pattern("NNW")
                .pattern("NCW")
                .pattern(" S ")
                .define('N', Tags.Items.INGOTS_NETHERITE)
                .define('S', NuminaObjects.SERVO.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_4.get()), has(NuminaObjects.CONTROL_CIRCUIT_4.get()))
                .save(output);

        // Pickaxes -------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.STONE_PICKAXE_MODULE.get())
                .pattern("SSS")
                .pattern("WCW")
                .pattern(" X ")
                .define('S', Tags.Items.COBBLESTONES)
                .define('C', NuminaObjects.CONTROL_CIRCUIT_1.get())
                .define('W', Tags.Items.STRINGS)
                .define('X', Items.STICK)
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_1.get()), has(NuminaObjects.CONTROL_CIRCUIT_1.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.IRON_PICKAXE_MODULE.get())
                .pattern("III")
                .pattern("WCW")
                .pattern(" S ")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', NuminaObjects.SERVO.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_2.get()), has(NuminaObjects.CONTROL_CIRCUIT_2.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.DIAMOND_PICKAXE_MODULE.get())
                .pattern("DDD")
                .pattern("WCW")
                .pattern(" S ")
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('S', NuminaObjects.SERVO.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_3.get()), has(NuminaObjects.CONTROL_CIRCUIT_3.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.NETHERITE_PICKAXE_MODULE.get())
                .pattern("NNN")
                .pattern("WCW")
                .pattern(" S ")
                .define('N', Tags.Items.INGOTS_NETHERITE)
                .define('S', NuminaObjects.SERVO.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_4.get()), has(NuminaObjects.CONTROL_CIRCUIT_4.get()))
                .save(output);

        // Shovels --------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.STONE_SHOVEL_MODULE.get())
                .pattern(" S ")
                .pattern("WCW")
                .pattern(" X ")
                .define('S', Tags.Items.COBBLESTONES)
                .define('C', NuminaObjects.CONTROL_CIRCUIT_1.get())
                .define('W', Tags.Items.STRINGS)
                .define('X', Items.STICK)
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_1.get()), has(NuminaObjects.CONTROL_CIRCUIT_1.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.IRON_SHOVEL_MODULE.get())
                .pattern(" I ")
                .pattern("WCW")
                .pattern(" S ")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', NuminaObjects.SERVO.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_2.get()), has(NuminaObjects.CONTROL_CIRCUIT_2.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.DIAMOND_SHOVEL_MODULE.get())
                .pattern(" D ")
                .pattern("WCW")
                .pattern(" S ")
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('S', NuminaObjects.SERVO.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_3.get()), has(NuminaObjects.CONTROL_CIRCUIT_3.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.NETHERITE_SHOVEL_MODULE.get())
                .pattern(" N ")
                .pattern("WCW")
                .pattern(" S ")
                .define('N', Tags.Items.INGOTS_NETHERITE)
                .define('S', NuminaObjects.SERVO.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_4.get()), has(NuminaObjects.CONTROL_CIRCUIT_4.get()))
                .save(output);




/*
    // Farming
    public static final DeferredHolder<Item, RototillerModule> ROTOTILLER_MODULE = MPS_ITEMS.register(MPSConstants.ROTOTILLER_MODULE.getPath(), RototillerModule::new);
    // TODO?
//    public static final DeferredHolder<Item, HoeModule> ROTOTILLER_MODULE2 = MPS_ITEMS.register(MPSConstants.HOE_MODULE.getPath(), HoeModule::new);
//    public static final DeferredHolder<Item, HoeModule> ROTOTILLER_MODULE3 = MPS_ITEMS.register(MPSConstants.HOE_MODULE.getPath(), HoeModule::new);
//    public static final DeferredHolder<Item, HoeModule> ROTOTILLER_MODULE4 = MPS_ITEMS.register(MPSConstants.HOE_MODULE.getPath(), HoeModule::new);
*/





        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.LEAF_BLOWER_MODULE.get())
                .pattern("I  ")
                .pattern("WCW")
                .pattern("IS ")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('S', NuminaObjects.SOLENOID.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_2.get()), has(NuminaObjects.CONTROL_CIRCUIT_2.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.LUX_CAPACITOR_MODULE.get())
                .pattern("IRI")
                .pattern("WCW")
                .pattern("GIG")
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .define('G', Tags.Items.DUSTS_GLOWSTONE)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_2.get()), has(NuminaObjects.CONTROL_CIRCUIT_2.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.SHEARS_MODULE.get())
                .pattern("I I")
                .pattern("WCW")
                .pattern(" S ")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('S', NuminaObjects.SOLENOID.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .unlockedBy(getHasName(NuminaObjects.CONTROL_CIRCUIT_2.get()), has(NuminaObjects.CONTROL_CIRCUIT_2.get()))
                .save(output);

        // Vision ------------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.BINOCULARS_MODULE.get())
                .pattern("WWW")
                .pattern("WCW")
                .pattern("WLW")
                .define('L', NuminaObjects.LASER_EMITTER.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .unlockedBy(getHasName(NuminaObjects.LASER_EMITTER.get()), has(NuminaObjects.LASER_EMITTER.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.NIGHTVISION_MODULE.get())
                .pattern("WLW")
                .pattern("WCW")
                .pattern("WLW")
                .define('L', NuminaObjects.LASER_EMITTER.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .unlockedBy(getHasName(NuminaObjects.LASER_EMITTER.get()), has(NuminaObjects.LASER_EMITTER.get()))
                .save(output);

        // Weapons ----------------------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.BLADE_LAUNCHER_MODULE.get())
                .pattern("WMW")
                .pattern("WCW")
                .pattern("WSW")
                .define('S', NuminaObjects.SERVO.get())
                .define('M', NuminaObjects.CAPACITOR_2.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_1.get())
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .unlockedBy(getHasName(NuminaObjects.SERVO.get()), has(NuminaObjects.SERVO.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.LIGHTNING_SUMMONER_MODULE.get())
                .pattern("WHW")
                .pattern("WFW")
                .pattern("WSW")
                .define('H', NuminaObjects.CAPACITOR_4.get())
                .define('S', NuminaObjects.SOLENOID.get())
                .define('F', NuminaObjects.FIELD_EMITTER.get())
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .unlockedBy(getHasName(NuminaObjects.SOLENOID.get()), has(NuminaObjects.SOLENOID.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.MELEE_ASSIST_MODULE.get())
                .pattern(" S ")
                .pattern(" C ")
                .pattern("I I")
                .define('S', NuminaObjects.SERVO.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_1.get())
                .define('I', Tags.Items.INGOTS_GOLD)
                .unlockedBy(getHasName(NuminaObjects.FIELD_EMITTER.get()), has(NuminaObjects.FIELD_EMITTER.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.PLASMA_CANNON_MODULE.get())
                .pattern("SGC")
                .pattern("SMC")
                .pattern("SGC")
                .define('S', NuminaObjects.SOLENOID.get())
                .define('C', NuminaObjects.COMPUTER_CHIP.get())
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('M', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.RAILGUN_MODULE.get())
                .pattern("SOC")
                .pattern("CMS")
                .pattern("SOC")
                .define('S', NuminaObjects.SOLENOID.get())
                .define('C', NuminaObjects.COMPUTER_CHIP.get())
                .define('M', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .define('O', Tags.Items.INGOTS_GOLD)
                .unlockedBy(getHasName(NuminaObjects.FIELD_EMITTER.get()), has(NuminaObjects.FIELD_EMITTER.get()))
                .save(output);
/*
    public static final DeferredHolder<Item, SonicWeaponModule> SONIC_WEAPON_MODULE = MPS_ITEMS.register(MPSConstants.SONIC_WEAPON_MODULE.getPath(),
            SonicWeaponModule::new);
 */
    }
}
