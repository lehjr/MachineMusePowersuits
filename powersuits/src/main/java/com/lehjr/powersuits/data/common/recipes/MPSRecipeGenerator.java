package com.lehjr.powersuits.data.common.recipes;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.registration.NuminaItems;
import com.lehjr.numina.data.common.recipes.ShapedEnchantmentRecipeBuilder;
import com.lehjr.numina.data.common.recipes.ShapedModularItemUpgradeRecipeBuilder;
import com.lehjr.powersuits.common.registration.MPSItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class MPSRecipeGenerator extends RecipeProvider {
    CompletableFuture<HolderLookup.Provider> registries;

    public MPSRecipeGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
        this.registries = registries;
    }

    @Override
    protected void buildRecipes(@Nonnull RecipeOutput output) {
        HolderLookup.Provider provider = null;
        try {
            provider = registries.get();
        } catch(Exception e) {
            NuminaLogger.logException("failed to get HolderLookup.Provider during MPS recipe datagen: ", e);
        }

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
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_1.get())
            .unlockedBy(getHasName(NuminaItems.WIRING_COPPER.get()), has(NuminaItems.WIRING_COPPER.get()))
            .save(output);

        ShapedModularItemUpgradeRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.POWER_FIST_2.get())
            .pattern(" W ")
            .pattern("WXC")
            .pattern(" IW")
            .define('X', MPSItems.POWER_FIST_1.get())
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .unlockedBy(getHasName(MPSItems.POWER_FIST_1.get()), has(MPSItems.POWER_FIST_1.get()))
            .save(output);

        ShapedModularItemUpgradeRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.POWER_FIST_3.get())
            .pattern(" W ")
            .pattern("WXC")
            .pattern(" IW")
            .define('X', MPSItems.POWER_FIST_2.get())
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_3.get())
            .unlockedBy(getHasName(MPSItems.POWER_FIST_2.get()), has(MPSItems.POWER_FIST_2.get()))
            .save(output);

        ShapedModularItemUpgradeRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.POWER_FIST_4.get())
            .pattern(" W ")
            .pattern("WXC")
            .pattern(" IW")
            .define('X', MPSItems.POWER_FIST_3.get())
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_4.get())
            .unlockedBy(getHasName(MPSItems.POWER_FIST_3.get()), has(MPSItems.POWER_FIST_3.get()))
            .save(output);

        // Armor ==========================================================================================================
        // Helmet
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_HELMET_1.get())
            .pattern("III")
            .pattern("WCW")
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_1.get())
            .unlockedBy(getHasName(NuminaItems.WIRING_COPPER.get()), has(NuminaItems.WIRING_COPPER.get()))
            .save(output);

        ShapedModularItemUpgradeRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_HELMET_2.get())
            .pattern("III")
            .pattern("WCW")
            .pattern(" X ")
            .define('X', MPSItems.POWER_ARMOR_HELMET_1.get())
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .unlockedBy(getHasName(MPSItems.POWER_ARMOR_HELMET_1.get()), has(MPSItems.POWER_ARMOR_HELMET_1.get()))
            .save(output);

        ShapedModularItemUpgradeRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_HELMET_3.get())
            .pattern("III")
            .pattern("WCW")
            .pattern(" X ")
            .define('X', MPSItems.POWER_ARMOR_HELMET_2.get())
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_3.get())
            .unlockedBy(getHasName(MPSItems.POWER_ARMOR_HELMET_2.get()), has(MPSItems.POWER_ARMOR_HELMET_2.get()))
            .save(output);

        ShapedModularItemUpgradeRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_HELMET_4.get())
            .pattern("III")
            .pattern("WCW")
            .pattern(" X ")
            .define('X', MPSItems.POWER_ARMOR_HELMET_3.get())
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_4.get())
            .unlockedBy(getHasName(MPSItems.POWER_ARMOR_HELMET_3.get()), has(MPSItems.POWER_ARMOR_HELMET_3.get()))
            .save(output);

        // ChestPlate
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_CHESTPLATE_1.get())
            .pattern("ICI")
            .pattern("WIW")
            .pattern("III")
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_1.get())
            .unlockedBy(getHasName(NuminaItems.WIRING_COPPER.get()), has(NuminaItems.WIRING_COPPER.get()))
            .save(output);

        ShapedModularItemUpgradeRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_CHESTPLATE_2.get())
            .pattern("ICI")
            .pattern("WXW")
            .pattern("III")
            .define('X', MPSItems.POWER_ARMOR_CHESTPLATE_1.get())
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .unlockedBy(getHasName(MPSItems.POWER_ARMOR_CHESTPLATE_1.get()), has(MPSItems.POWER_ARMOR_CHESTPLATE_1.get()))
            .save(output);

        ShapedModularItemUpgradeRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_CHESTPLATE_3.get())
            .pattern("ICI")
            .pattern("WXW")
            .pattern("III")
            .define('X', MPSItems.POWER_ARMOR_CHESTPLATE_2.get())
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_3.get())
            .unlockedBy(getHasName(MPSItems.POWER_ARMOR_CHESTPLATE_2.get()), has(MPSItems.POWER_ARMOR_CHESTPLATE_2.get()))
            .save(output);

        ShapedModularItemUpgradeRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_CHESTPLATE_4.get())
            .pattern("ICI")
            .pattern("WXW")
            .pattern("III")
            .define('X', MPSItems.POWER_ARMOR_CHESTPLATE_3.get())
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_4.get())
            .unlockedBy(getHasName(MPSItems.POWER_ARMOR_CHESTPLATE_3.get()), has(MPSItems.POWER_ARMOR_CHESTPLATE_3.get()))
            .save(output);

        // Leggings
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_LEGGINGS_1.get())
            .pattern("III")
            .pattern("WCW")
            .pattern("I I")
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_1.get())
            .unlockedBy(getHasName(NuminaItems.WIRING_COPPER.get()), has(NuminaItems.WIRING_COPPER.get()))
            .save(output);

        ShapedModularItemUpgradeRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_LEGGINGS_2.get())
            .pattern("III")
            .pattern("WCW")
            .pattern("IXI")
            .define('X', MPSItems.POWER_ARMOR_LEGGINGS_1.get())
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .unlockedBy(getHasName(MPSItems.POWER_ARMOR_LEGGINGS_1.get()), has(MPSItems.POWER_ARMOR_LEGGINGS_1.get()))
            .save(output);

        ShapedModularItemUpgradeRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_LEGGINGS_3.get())
            .pattern("III")
            .pattern("WCW")
            .pattern("IXI")
            .define('X', MPSItems.POWER_ARMOR_LEGGINGS_2.get())
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_3.get())
            .unlockedBy(getHasName(MPSItems.POWER_ARMOR_LEGGINGS_2.get()), has(MPSItems.POWER_ARMOR_LEGGINGS_2.get()))
            .save(output);

        ShapedModularItemUpgradeRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_LEGGINGS_4.get())
            .pattern("III")
            .pattern("WCW")
            .pattern("IXI")
            .define('X', MPSItems.POWER_ARMOR_LEGGINGS_3.get())
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_4.get())
            .unlockedBy(getHasName(MPSItems.POWER_ARMOR_LEGGINGS_3.get()), has(MPSItems.POWER_ARMOR_LEGGINGS_3.get()))
            .save(output);

        // Boots
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_BOOTS_1.get())
            .pattern("ICI")
            .pattern("W W")
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_1.get())
            .unlockedBy(getHasName(NuminaItems.WIRING_COPPER.get()), has(NuminaItems.WIRING_COPPER.get()))
            .save(output);

        ShapedModularItemUpgradeRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_BOOTS_2.get())
            .pattern("ICI")
            .pattern("WXW")
            .define('X', MPSItems.POWER_ARMOR_BOOTS_1.get())
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .unlockedBy(getHasName(MPSItems.POWER_ARMOR_BOOTS_1.get()), has(MPSItems.POWER_ARMOR_BOOTS_1.get()))
            .save(output);

        ShapedModularItemUpgradeRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_BOOTS_3.get())
            .pattern("ICI")
            .pattern("WXW")
            .define('X', MPSItems.POWER_ARMOR_BOOTS_2.get())
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_3.get())
            .unlockedBy(getHasName(MPSItems.POWER_ARMOR_BOOTS_2.get()), has(MPSItems.POWER_ARMOR_BOOTS_2.get()))
            .save(output);

        ShapedModularItemUpgradeRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.POWER_ARMOR_BOOTS_4.get())
            .pattern("ICI")
            .pattern("WXW")
            .define('X', MPSItems.POWER_ARMOR_BOOTS_3.get())
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_4.get())
            .unlockedBy(getHasName(MPSItems.POWER_ARMOR_BOOTS_3.get()), has(MPSItems.POWER_ARMOR_BOOTS_3.get()))
            .save(output);

        // Modules ========================================================================================================
        // Armor --------------------------------------------------------------------------------------------
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, MPSItems.IRON_PLATING_MODULE.get())
            .requires(NuminaItems.PLATING_IRON.get(), 3)
            .unlockedBy(getHasName(NuminaItems.PLATING_IRON.get()), has(NuminaItems.PLATING_IRON.get()))
            .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, MPSItems.DIAMOND_PLATING_MODULE.get())
            .requires(NuminaItems.PLATING_DIAMOND.get(), 3)
            .unlockedBy(getHasName(NuminaItems.PLATING_DIAMOND.get()), has(NuminaItems.PLATING_DIAMOND.get()))
            .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, MPSItems.NETHERITE_PLATING_MODULE.get())
            .requires(NuminaItems.PLATING_NETHERITE.get(), 3)
            .unlockedBy(getHasName(NuminaItems.PLATING_NETHERITE.get()), has(NuminaItems.PLATING_NETHERITE.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.ENERGY_SHIELD_MODULE.get())
            .pattern("WEW")
            .pattern("WCW")
            .pattern("WEW")
            .define('E', NuminaItems.FIELD_EMITTER.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_4.get())
            .unlockedBy(getHasName(NuminaItems.FIELD_EMITTER.get()), has(NuminaItems.FIELD_EMITTER.get()))
            .save(output);

        // Cosmetic -----------------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.TRANSPARENT_ARMOR_MODULE.get())
            .pattern("WW")
            .pattern("LC")
            .pattern("WW")
            .define('L', NuminaItems.LASER_EMITTER.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_4.get())
            .unlockedBy(getHasName(NuminaItems.FIELD_EMITTER.get()), has(NuminaItems.FIELD_EMITTER.get()))
            .save(output);


        // Energy Generation --------------------------------------------------------------------------
        // TODO

        // Environmental ------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get())
            .pattern("LCL")
            .pattern("CMC")
            .pattern("LCL")
            .define('L', NuminaItems.LASER_EMITTER.get())
            .define('M', NuminaItems.CONTROL_CIRCUIT_3.get())
            .define('C', NuminaItems.COMPUTER_CHIP.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_3.get()), has(NuminaItems.CONTROL_CIRCUIT_3.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.AUTO_FEEDER_MODULE.get())
            .pattern("FOF")
            .pattern("RCR")
            .pattern("SMS")
            .define('F', NuminaItems.SOLENOID.get())
            .define('O', Tags.Items.CHESTS)
            .define('R', NuminaItems.WIRING_COPPER.get())
            .define('S', NuminaItems.SERVO.get())
            .define('M', NuminaItems.RUBBER_HOSE.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_2.get()), has(NuminaItems.CONTROL_CIRCUIT_2.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.MAGNET_MODULE.get())
            .pattern("IMI")
            .pattern("WCW")
            .pattern("IMI")
            .define('I', Tags.Items.INGOTS_IRON)
            .define('M', NuminaItems.MAGNET.get())
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_2.get()), has(NuminaItems.CONTROL_CIRCUIT_2.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.MOB_REPULSOR_MODULE.get())
            .pattern("FWF")
            .pattern("WCW")
            .pattern("FWF")
            .define('F', NuminaItems.FIELD_EMITTER.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_3.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_3.get()), has(NuminaItems.CONTROL_CIRCUIT_3.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.PIGLIN_PACIFICATION_MODULE.get())
            .pattern(" S ")
            .pattern(" G ")
            .define('S', Tags.Items.STRINGS)
            .define('G', Tags.Items.NUGGETS_GOLD)
            .unlockedBy(getHasName(Items.GOLD_NUGGET), has(Items.GOLD_NUGGET))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.WATER_ELECTROLYZER_MODULE.get())
            .pattern("WLW")
            .pattern("OCO")
            .pattern("WRW")
            .define('L', NuminaItems.CAPACITOR_1.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('R', NuminaItems.RUBBER_HOSE.get())
            .define('O', NuminaItems.SOLENOID.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_2.get()), has(NuminaItems.CONTROL_CIRCUIT_2.get()))
            .save(output);

        // Mining Enchantments ------------------------------------------------------------------------
        if(provider != null) {
            provider.lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.AQUA_AFFINITY)
                .ifPresent(enchantmentReference -> ShapedEnchantmentRecipeBuilder
                    .shaped(RecipeCategory.MISC, MPSItems.AQUA_AFFINITY_MODULE.get())
                    .pattern("ENE")
                    .pattern("WCW")
                    .pattern("EEE")
                    .define('E', NuminaItems.COMPUTER_CHIP.get())
                    .define('C', NuminaItems.CONTROL_CIRCUIT_4.get())
                    .define('W', NuminaItems.WIRING_GOLD.get())
                    .define('N', enchantmentReference, 1)
                    .unlockedBy(getHasName(Items.ENCHANTED_BOOK), has(Items.ENCHANTED_BOOK))
                    .save(output));

            provider.lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.FORTUNE)
                .ifPresent(enchantmentReference -> ShapedEnchantmentRecipeBuilder
                    .shaped(RecipeCategory.MISC, MPSItems.FORTUNE_MODULE.get())
                    .pattern("ENE")
                    .pattern("WCW")
                    .pattern("EEE")
                    .define('E', NuminaItems.COMPUTER_CHIP.get())
                    .define('C', NuminaItems.CONTROL_CIRCUIT_4.get())
                    .define('W', NuminaItems.WIRING_GOLD.get())
                    .define('N', enchantmentReference, 3)
                    .unlockedBy(getHasName(Items.ENCHANTED_BOOK), has(Items.ENCHANTED_BOOK))
                    .save(output));

            provider.lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.SILK_TOUCH)
                .ifPresent(enchantmentReference -> ShapedEnchantmentRecipeBuilder
                    .shaped(RecipeCategory.MISC, MPSItems.SILK_TOUCH_MODULE.get())
                    .pattern("ENE")
                    .pattern("WCW")
                    .pattern("EEE")
                    .define('E', NuminaItems.COMPUTER_CHIP.get())
                    .define('C', NuminaItems.CONTROL_CIRCUIT_4.get())
                    .define('W', NuminaItems.WIRING_GOLD.get())
                    .define('N', enchantmentReference, 3)
                    .unlockedBy(getHasName(Items.ENCHANTED_BOOK), has(Items.ENCHANTED_BOOK))
                    .save(output));
        }

        // Mining Enhancements ------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.TUNNEL_BORE_MODULE.get())
            .pattern("SES")
            .pattern("ECE")
            .pattern("SES")
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .define('S', NuminaItems.SERVO.get())
            .define('E', NuminaItems.SOLENOID.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_2.get()), has(NuminaItems.CONTROL_CIRCUIT_2.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.VEIN_MINER_MODULE.get())
            .pattern("CSC")
            .pattern("SMS")
            .pattern("CSC")
            .define('C', NuminaItems.COMPUTER_CHIP.get())
            .define('S', NuminaItems.SERVO.get())
            .define('M', NuminaItems.CONTROL_CIRCUIT_3.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_3.get()), has(NuminaItems.CONTROL_CIRCUIT_3.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.SELECTIVE_MINER_MODULE.get())
            .pattern("OCO")
            .pattern("GMG")
            .pattern("SGS")
            .define('C', NuminaItems.COMPUTER_CHIP.get())
            .define('S', NuminaItems.SERVO.get())
            .define('O', NuminaItems.SOLENOID.get())
            .define('M', NuminaItems.CONTROL_CIRCUIT_3.get())
            .define('G', NuminaItems.WIRING_GOLD.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_3.get()), has(NuminaItems.CONTROL_CIRCUIT_3.get()))
            .save(output);

        // Movement -----------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.BLINK_DRIVE_MODULE.get())
            .pattern("CIC")
            .pattern("FPF")
            .pattern("WIW")
            .define('C', NuminaItems.COMPUTER_CHIP.get())
            .define('P', NuminaItems.CONTROL_CIRCUIT_4.get())
            .define('F', NuminaItems.FIELD_EMITTER.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('I', NuminaItems.ION_THRUSTER.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_2.get()), has(NuminaItems.CONTROL_CIRCUIT_2.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.CLIMB_ASSIST_MODULE.get())
            .pattern("W W")
            .pattern("SCS")
            .pattern("W W")
            .define('S', NuminaItems.SOLENOID.get())
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_1.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_1.get()), has(NuminaItems.CONTROL_CIRCUIT_1.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.DIMENSIONAL_RIFT_MODULE.get())
            .pattern("ECE")
            .pattern("COC")
            .pattern("ECE")
            .define('C', NuminaItems.COMPUTER_CHIP.get())
            .define('E', Items.ENDER_EYE)
            .define('O', NuminaItems.CONTROL_CIRCUIT_4.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_4.get()), has(NuminaItems.CONTROL_CIRCUIT_4.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.FLIGHT_CONTROL_MODULE.get())
            .pattern("CWC")
            .pattern("WOW")
            .pattern("CWC")
            .define('C', NuminaItems.COMPUTER_CHIP.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('O', NuminaItems.CONTROL_CIRCUIT_4.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_4.get()), has(NuminaItems.CONTROL_CIRCUIT_4.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.GLIDER_MODULE.get())
            .pattern("SWS")
            .pattern("GCG")
            .pattern("SWS")
            .define('C', NuminaItems.CONTROL_CIRCUIT_3.get())
            .define('G', NuminaItems.GLIDER_WING.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('S', NuminaItems.SERVO.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_4.get()), has(NuminaItems.CONTROL_CIRCUIT_4.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.JET_BOOTS_MODULE.get())
            .pattern("S S")
            .pattern("CMC")
            .pattern("IWI")
            .define('S', NuminaItems.SOLENOID.get())
            .define('M', NuminaItems.CONTROL_CIRCUIT_3.get())
            .define('C', NuminaItems.COMPUTER_CHIP.get())
            .define('I', NuminaItems.ION_THRUSTER.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_3.get()), has(NuminaItems.CONTROL_CIRCUIT_3.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.JETPACK_MODULE.get())
            .pattern("ISI")
            .pattern("WCW")
            .pattern("ISI")
            .define('I', NuminaItems.ION_THRUSTER.get())
            .define('S', NuminaItems.SERVO.get())
            .define('W', NuminaItems.COMPUTER_CHIP.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_3.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_3.get()), has(NuminaItems.CONTROL_CIRCUIT_3.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.JUMP_ASSIST_MODULE.get())
            .pattern("SWS")
            .pattern("WCW")
            .pattern("SWS")
            .define('S', NuminaItems.SOLENOID.get())
            .define('W', Items.WHITE_WOOL)
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_2.get()), has(NuminaItems.CONTROL_CIRCUIT_2.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.PARACHUTE_MODULE.get())
            .pattern("SGS")
            .pattern("PCP")
            .pattern("GSG")
            .define('S', NuminaItems.SERVO.get())
            .define('G', Tags.Items.STRINGS)
            .define('P', NuminaItems.PARACHUTE.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_1.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_1.get()), has(NuminaItems.CONTROL_CIRCUIT_1.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.SHOCK_ABSORBER_MODULE.get())
            .pattern("S S")
            .pattern("SCS")
            .pattern("W W")
            .define('S', NuminaItems.SOLENOID.get())
            .define('W', Items.WHITE_WOOL)
            .define('C', NuminaItems.CONTROL_CIRCUIT_1.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_1.get()), has(NuminaItems.CONTROL_CIRCUIT_1.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.SPRINT_ASSIST_MODULE.get())
            .pattern("SNS")
            .pattern("NMN")
            .pattern("SCS")
            .define('S', NuminaItems.SERVO.get())
            .define('N', NuminaItems.SOLENOID.get())
            .define('C', NuminaItems.COMPUTER_CHIP.get())
            .define('M', NuminaItems.CONTROL_CIRCUIT_3.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_3.get()), has(NuminaItems.CONTROL_CIRCUIT_3.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.SWIM_ASSIST_MODULE.get())
            .pattern("SHS")
            .pattern("WCW")
            .pattern("HSH")
            .define('S', NuminaItems.SERVO.get())
            .define('H', NuminaItems.RUBBER_HOSE.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_3.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_3.get()), has(NuminaItems.CONTROL_CIRCUIT_3.get()))
            .save(output);

        // Tools ------------------------------------------------------------------------------------------------
        // Axes -----------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.STONE_AXE_MODULE.get())
            .pattern("IIW")
            .pattern("ICW")
            .pattern(" X ")
            .define('I', Tags.Items.COBBLESTONES)
            .define('C', NuminaItems.CONTROL_CIRCUIT_1.get())
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('X', NuminaItems.SOLENOID.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_1.get()), has(NuminaItems.CONTROL_CIRCUIT_1.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.IRON_AXE_MODULE.get())
            .pattern("IIW")
            .pattern("ICW")
            .pattern("SXS")
            .define('I', Tags.Items.INGOTS_IRON)
            .define('S', NuminaItems.SERVO.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('X', MPSItems.STONE_AXE_MODULE.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_2.get()), has(NuminaItems.CONTROL_CIRCUIT_2.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.DIAMOND_AXE_MODULE.get())
            .pattern("IIW")
            .pattern("ICW")
            .pattern("SXS")
            .define('I', Tags.Items.GEMS_DIAMOND)
            .define('S', NuminaItems.SERVO.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_3.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('X', MPSItems.IRON_AXE_MODULE.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_3.get()), has(NuminaItems.CONTROL_CIRCUIT_3.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.NETHERITE_AXE_MODULE.get())
            .pattern("IIW")
            .pattern("ICW")
            .pattern("SXS")
            .define('I', Tags.Items.INGOTS_NETHERITE)
            .define('S', NuminaItems.SERVO.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_4.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('X', MPSItems.DIAMOND_AXE_MODULE.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_4.get()), has(NuminaItems.CONTROL_CIRCUIT_4.get()))
            .save(output);

        // Pickaxes -------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.STONE_PICKAXE_MODULE.get())
            .pattern("III")
            .pattern("WCW")
            .pattern(" X ")
            .define('I', Tags.Items.COBBLESTONES)
            .define('C', NuminaItems.CONTROL_CIRCUIT_1.get())
            .define('W', Tags.Items.STRINGS)
            .define('X', NuminaItems.SOLENOID.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_1.get()), has(NuminaItems.CONTROL_CIRCUIT_1.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.IRON_PICKAXE_MODULE.get())
            .pattern("III")
            .pattern("WCW")
            .pattern("SXS")
            .define('I', Tags.Items.INGOTS_IRON)
            .define('S', NuminaItems.SERVO.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('X', MPSItems.STONE_PICKAXE_MODULE.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_2.get()), has(NuminaItems.CONTROL_CIRCUIT_2.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.DIAMOND_PICKAXE_MODULE.get())
            .pattern("III")
            .pattern("WCW")
            .pattern("SXS")
            .define('I', Tags.Items.GEMS_DIAMOND)
            .define('S', NuminaItems.SERVO.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_3.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('X', MPSItems.IRON_PICKAXE_MODULE.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_3.get()), has(NuminaItems.CONTROL_CIRCUIT_3.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.NETHERITE_PICKAXE_MODULE.get())
            .pattern("III")
            .pattern("WCW")
            .pattern("SXS")
            .define('I', Tags.Items.INGOTS_NETHERITE)
            .define('S', NuminaItems.SERVO.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_4.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('X', MPSItems.DIAMOND_PICKAXE_MODULE.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_4.get()), has(NuminaItems.CONTROL_CIRCUIT_4.get()))
            .save(output);

        // Rototillers --------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.STONE_ROTOTILLER_MODULE.get())
            .pattern("SS ")
            .pattern("WCW")
            .pattern(" X ")
            .define('S', Tags.Items.COBBLESTONES)
            .define('C', NuminaItems.CONTROL_CIRCUIT_1.get())
            .define('W', Tags.Items.STRINGS)
            .define('X', NuminaItems.SOLENOID.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_1.get()), has(NuminaItems.CONTROL_CIRCUIT_1.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.IRON_ROTOTILLER_MODULE.get())
            .pattern("II ")
            .pattern("WCW")
            .pattern("SXS")
            .define('I', Tags.Items.INGOTS_IRON)
            .define('S', NuminaItems.SERVO.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('X', MPSItems.STONE_ROTOTILLER_MODULE.get())

            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_2.get()), has(NuminaItems.CONTROL_CIRCUIT_2.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.DIAMOND_ROTOTILLER_MODULE.get())
            .pattern("II ")
            .pattern("WCW")
            .pattern("SXS")
            .define('I', Tags.Items.GEMS_DIAMOND)
            .define('S', NuminaItems.SERVO.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_3.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('X', MPSItems.IRON_ROTOTILLER_MODULE.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_3.get()), has(NuminaItems.CONTROL_CIRCUIT_3.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.NETHERITE_ROTOTILLER_MODULE.get())
            .pattern("II ")
            .pattern("WCW")
            .pattern("SXS")
            .define('I', Tags.Items.INGOTS_NETHERITE)
            .define('S', NuminaItems.SERVO.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_4.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('X', MPSItems.DIAMOND_ROTOTILLER_MODULE.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_4.get()), has(NuminaItems.CONTROL_CIRCUIT_4.get()))
            .save(output);


        // Shovels --------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.STONE_SHOVEL_MODULE.get())
            .pattern(" S ")
            .pattern("WCW")
            .pattern(" X ")
            .define('S', Tags.Items.COBBLESTONES)
            .define('C', NuminaItems.CONTROL_CIRCUIT_1.get())
            .define('W', Tags.Items.STRINGS)
            .define('X', NuminaItems.SOLENOID.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_1.get()), has(NuminaItems.CONTROL_CIRCUIT_1.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.IRON_SHOVEL_MODULE.get())
            .pattern(" I ")
            .pattern("WCW")
            .pattern("SXS")
            .define('I', Tags.Items.INGOTS_IRON)
            .define('S', NuminaItems.SERVO.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('X', MPSItems.STONE_SHOVEL_MODULE.get())

            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_2.get()), has(NuminaItems.CONTROL_CIRCUIT_2.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.DIAMOND_SHOVEL_MODULE.get())
            .pattern(" I ")
            .pattern("WCW")
            .pattern("SXS")
            .define('I', Tags.Items.GEMS_DIAMOND)
            .define('S', NuminaItems.SERVO.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_3.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('X', MPSItems.IRON_SHOVEL_MODULE.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_3.get()), has(NuminaItems.CONTROL_CIRCUIT_3.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.NETHERITE_SHOVEL_MODULE.get())
            .pattern(" I ")
            .pattern("WCW")
            .pattern("SXS")
            .define('I', Tags.Items.INGOTS_NETHERITE)
            .define('S', NuminaItems.SERVO.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_4.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .define('X', MPSItems.DIAMOND_SHOVEL_MODULE.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_4.get()), has(NuminaItems.CONTROL_CIRCUIT_4.get()))
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
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('S', NuminaItems.SOLENOID.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_2.get()), has(NuminaItems.CONTROL_CIRCUIT_2.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.LUX_CAPACITOR_MODULE.get())
            .pattern("IRI")
            .pattern("WCW")
            .pattern("GIG")
            .define('R', Tags.Items.DUSTS_REDSTONE)
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .define('G', Tags.Items.DUSTS_GLOWSTONE)
            .define('I', Tags.Items.INGOTS_IRON)
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_2.get()), has(NuminaItems.CONTROL_CIRCUIT_2.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.SHEARS_MODULE.get())
            .pattern("I I")
            .pattern("WCW")
            .pattern(" S ")
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', NuminaItems.WIRING_COPPER.get())
            .define('S', NuminaItems.SOLENOID.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .unlockedBy(getHasName(NuminaItems.CONTROL_CIRCUIT_2.get()), has(NuminaItems.CONTROL_CIRCUIT_2.get()))
            .save(output);

        // Vision ------------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.BINOCULARS_MODULE.get())
            .pattern("WWW")
            .pattern("WCW")
            .pattern("WLW")
            .define('L', NuminaItems.LASER_EMITTER.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .define('W', NuminaItems.WIRING_COPPER.get())
            .unlockedBy(getHasName(NuminaItems.LASER_EMITTER.get()), has(NuminaItems.LASER_EMITTER.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MPSItems.NIGHTVISION_MODULE.get())
            .pattern("WLW")
            .pattern("WCW")
            .pattern("WLW")
            .define('L', NuminaItems.LASER_EMITTER.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_2.get())
            .define('W', NuminaItems.WIRING_COPPER.get())
            .unlockedBy(getHasName(NuminaItems.LASER_EMITTER.get()), has(NuminaItems.LASER_EMITTER.get()))
            .save(output);

        // Weapons ----------------------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.BLADE_LAUNCHER_MODULE.get())
            .pattern("WMW")
            .pattern("WCW")
            .pattern("WSW")
            .define('S', NuminaItems.SERVO.get())
            .define('M', NuminaItems.CAPACITOR_2.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_1.get())
            .define('W', NuminaItems.WIRING_COPPER.get())
            .unlockedBy(getHasName(NuminaItems.SERVO.get()), has(NuminaItems.SERVO.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.LIGHTNING_SUMMONER_MODULE.get())
            .pattern("WHW")
            .pattern("WFW")
            .pattern("WSW")
            .define('H', NuminaItems.CAPACITOR_4.get())
            .define('S', NuminaItems.SOLENOID.get())
            .define('F', NuminaItems.FIELD_EMITTER.get())
            .define('W', NuminaItems.WIRING_GOLD.get())
            .unlockedBy(getHasName(NuminaItems.SOLENOID.get()), has(NuminaItems.SOLENOID.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.MELEE_ASSIST_MODULE.get())
            .pattern(" S ")
            .pattern(" C ")
            .pattern("I I")
            .define('S', NuminaItems.SERVO.get())
            .define('C', NuminaItems.CONTROL_CIRCUIT_1.get())
            .define('I', Tags.Items.INGOTS_GOLD)
            .unlockedBy(getHasName(NuminaItems.FIELD_EMITTER.get()), has(NuminaItems.FIELD_EMITTER.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.PLASMA_CANNON_MODULE.get())
            .pattern("SGC")
            .pattern("SMC")
            .pattern("SGC")
            .define('S', NuminaItems.SOLENOID.get())
            .define('C', NuminaItems.COMPUTER_CHIP.get())
            .define('G', Tags.Items.INGOTS_GOLD)
            .define('M', NuminaItems.CONTROL_CIRCUIT_3.get())
            .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MPSItems.RAILGUN_MODULE.get())
            .pattern("SOC")
            .pattern("CMS")
            .pattern("SOC")
            .define('S', NuminaItems.SOLENOID.get())
            .define('C', NuminaItems.COMPUTER_CHIP.get())
            .define('M', NuminaItems.CONTROL_CIRCUIT_4.get())
            .define('O', Tags.Items.INGOTS_GOLD)
            .unlockedBy(getHasName(NuminaItems.FIELD_EMITTER.get()), has(NuminaItems.FIELD_EMITTER.get()))
            .save(output);
/*
    public static final DeferredHolder<Item, SonicWeaponModule> SONIC_WEAPON_MODULE = MPS_ITEMS.register(MPSConstants.SONIC_WEAPON_MODULE.getPath(),
            SonicWeaponModule::new);
 */
    }
}
