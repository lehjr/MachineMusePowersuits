package numina.common.recipes;

import lehjr.numina.common.base.NuminaObjects;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class NuminaRecipeGenerator extends RecipeProvider {

    public NuminaRecipeGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(@Nonnull RecipeOutput recipeOutput) {
        // Charging base
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.CHARGING_BASE_ITEM.get())
                .pattern("WBW")
                .pattern("WCW")
                .pattern("PPP")
                .define('P', Items.OAK_PRESSURE_PLATE)
                .define('B', NuminaObjects.CAPACITOR_1.get())// Switch to capacitor
                .define('C', NuminaObjects.CONTROL_CIRCUIT_1.get())
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .unlockedBy(getHasName(Items.OAK_PRESSURE_PLATE), has(Items.OAK_PRESSURE_PLATE))
                .save(recipeOutput);

        // Armor Stand
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NuminaObjects.ARMOR_STAND_ITEM.get())
                .requires(Items.ARMOR_STAND)
                .requires(Items.COAL, 2)
                .unlockedBy(getHasName(Items.ARMOR_STAND), has(Items.ARMOR_STAND))
                .save(recipeOutput);

        // Modules ------------------------------------------------------------------------------------
        // Energy Storage

        // First tier battery derived from capacitor to avoid redstone usage
       ShapedEnergyRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.BATTERY_1.get())
                .pattern("WIW")
                .pattern("ICI")
                .pattern("IWI")
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .define('C', NuminaObjects.CAPACITOR_1.get())
                .unlockedBy(getHasName(NuminaObjects.CAPACITOR_1.get()), has(NuminaObjects.CAPACITOR_1.get()))
                .save(recipeOutput);

        // Above tier 1, player should have access to redstone
        ShapedEnergyRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.BATTERY_2.get())
                .pattern("WBW")
                .pattern("BCB")
                .pattern("BWB")
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_2.get())
                .define('B', NuminaObjects.BATTERY_1.get())
                .unlockedBy(getHasName(NuminaObjects.BATTERY_1.get()), has(NuminaObjects.BATTERY_1.get()))
                .save(recipeOutput);

        // Above tier 2, player should have access to gold
        ShapedEnergyRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.BATTERY_3.get())
                .pattern("WBW")
                .pattern("BCB")
                .pattern("BWB")
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_3.get())
                .define('B', NuminaObjects.BATTERY_2.get())
                .unlockedBy(getHasName(NuminaObjects.BATTERY_2.get()), has(NuminaObjects.BATTERY_2.get()))
                .save(recipeOutput);

        ShapedEnergyRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.BATTERY_4.get())
                .pattern("WBW")
                .pattern("BCB")
                .pattern("BWB")
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_4.get())
                .define('B', NuminaObjects.BATTERY_3.get())
                .unlockedBy(getHasName(NuminaObjects.BATTERY_3.get()), has(NuminaObjects.BATTERY_3.get()))
                .save(recipeOutput);


//        // Old recipes
//        ShapedEnergyRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.BATTERY_1.get())
//                .pattern("WIW")
//                .pattern("ICI")
//                .pattern("IWI")
//                .define('W', NuminaObjects.WIRING_COPPER.get())
//                .define('I', Tags.Items.INGOTS_IRON)
//                .define('C', NuminaObjects.CAPACITOR_1.get())
//                .unlockedBy(getHasName(NuminaObjects.CAPACITOR_1.get()), has(NuminaObjects.CAPACITOR_1.get()))
//                .save(recipeOutput);
//
//        ShapedEnergyRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.BATTERY_2.get())
//                .pattern("WIW")
//                .pattern("ICI")
//                .pattern("IWI")
//                .define('W', NuminaObjects.WIRING_COPPER.get())
//                .define('I', Tags.Items.INGOTS_IRON)
//                .define('C', NuminaObjects.CAPACITOR_2.get())
//                .unlockedBy(getHasName(NuminaObjects.CAPACITOR_2.get()), has(NuminaObjects.CAPACITOR_2.get()))
//                .save(recipeOutput);
//
//        ShapedEnergyRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.BATTERY_3.get())
//                .pattern("WGW")
//                .pattern("GCG")
//                .pattern("GWG")
//                .define('W', NuminaObjects.WIRING_GOLD.get())
//                .define('G', Tags.Items.GLASS_PANES)
//                .define('C', NuminaObjects.CAPACITOR_3.get())
//                .unlockedBy(getHasName(NuminaObjects.CAPACITOR_3.get()), has(NuminaObjects.CAPACITOR_3.get()))
//                .save(recipeOutput);
//
//       ShapedEnergyRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.BATTERY_4.get())
//                .pattern("WGW")
//                .pattern("GCG")
//                .pattern("GWG")
//                .define('W', NuminaObjects.WIRING_GOLD.get())
//                .define('G', Tags.Items.GLASS_PANES)
//                .define('C', NuminaObjects.CAPACITOR_4.get())
//                .unlockedBy(getHasName(NuminaObjects.CAPACITOR_4.get()), has(NuminaObjects.CAPACITOR_4.get()))
//                .save(recipeOutput);


// Components ---------------------------------------------------------------------------------
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.ARTIFICIAL_MUSCLE.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .define('C', NuminaObjects.CARBON_MYOFIBER.get())
                .unlockedBy(getHasName(NuminaObjects.CARBON_MYOFIBER.get()), has(NuminaObjects.CARBON_MYOFIBER.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.CARBON_MYOFIBER.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .define('C', Tags.Items.STORAGE_BLOCKS_COAL)
                .unlockedBy(getHasName(Items.COAL_BLOCK), has(Tags.Items.STORAGE_BLOCKS_COAL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.CAPACITOR_1.get())
                .pattern("WPI")
                .pattern("WCW")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('P', Items.PAPER)
                .define('C', Items.CHARCOAL)
                .unlockedBy(getHasName(Items.CHARCOAL), has(Items.CHARCOAL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.CAPACITOR_2.get())
                .pattern("WPI")
                .pattern("WLW")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('P', Items.PAPER)
                .define('L', Tags.Items.GEMS_LAPIS)
                .unlockedBy(getHasName(Items.LAPIS_LAZULI), has(Items.LAPIS_LAZULI))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.CAPACITOR_3.get())
                .pattern("WGD")
                .pattern("WEW")
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('G', Tags.Items.GLASS_PANES)
                .define('D', Tags.Items.DUSTS_GLOWSTONE)
                .define('E', Tags.Items.ENDER_PEARLS)
                .unlockedBy(getHasName(Items.ENDER_PEARL), has(Items.ENDER_PEARL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.CAPACITOR_4.get())
                .pattern("WCW")
                .pattern("W!W")
                .pattern("WCW")
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('C', NuminaObjects.CAPACITOR_3.get())
                .define('!', NuminaObjects.COMPUTER_CHIP.get())
                .unlockedBy(getHasName(NuminaObjects.CAPACITOR_3.get()), has(NuminaObjects.CAPACITOR_3.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.COMPUTER_CHIP.get())
                .pattern("RSR")
                .pattern("CDC")
                .pattern("RLR")
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('S', Items.DAYLIGHT_DETECTOR)
                .define('C', Items.COMPARATOR)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('L', NuminaObjects.LASER_EMITTER.get())
                .unlockedBy(getHasName(Items.DAYLIGHT_DETECTOR), has(Items.DAYLIGHT_DETECTOR))
                .save(recipeOutput);

        // Basically, a mechanical computer
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.CONTROL_CIRCUIT_1.get())
                .pattern("BSB")
                .pattern("SCS")
                .pattern("BSB")
                .define('B', Items.OAK_BUTTON)
                .define('C', Items.OAK_PLANKS)
                .define('S', Items.LEVER)
                .unlockedBy(getHasName(Items.OAK_PLANKS), has(Items.OAK_PLANKS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.CONTROL_CIRCUIT_2.get())
                .pattern("BSB")
                .pattern("SCS")
                .pattern("BSB")
                .define('B', Items.STONE_BUTTON)
                .define('C', Tags.Items.STONES)
                .define('S', Items.LEVER)
                .unlockedBy(getHasName(Items.STONE), has(Items.STONE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.CONTROL_CIRCUIT_3.get())
                .pattern("WOW")
                .pattern("RGO")
                .pattern("ERW")
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('E', Tags.Items.INGOTS_COPPER)
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('G', Tags.Items.DUSTS_GLOWSTONE)
                .define('O', Tags.Items.DYES)
                .unlockedBy(getHasName(NuminaObjects.WIRING_COPPER.get()), has(NuminaObjects.WIRING_COPPER.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.CONTROL_CIRCUIT_4.get())
                .pattern("WOW")
                .pattern("RGO")
                .pattern("ERW")
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('E', Tags.Items.INGOTS_GOLD)
                .define('W', NuminaObjects.WIRING_GOLD.get())
                .define('G', Tags.Items.DUSTS_GLOWSTONE)
                .define('O', Tags.Items.DYES)
                .unlockedBy(getHasName(NuminaObjects.WIRING_GOLD.get()), has(NuminaObjects.WIRING_GOLD.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.FIELD_EMITTER.get())
                .pattern("SES")
                .pattern("ESE")
                .pattern("SES")
                .define('E', Items.ENDER_PEARL)
                .define('S', NuminaObjects.SOLENOID.get())
                .unlockedBy(getHasName(Items.ENDER_PEARL), has(Items.ENDER_PEARL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.GLIDER_WING.get())
                .pattern(" II")
                .pattern("IIS")
                .pattern("I  ")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', NuminaObjects.SOLENOID.get())
                .unlockedBy(getHasName( NuminaObjects.SOLENOID.get()), has( NuminaObjects.SOLENOID.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.LASER_EMITTER.get())
                .pattern("#G!")
                .pattern("GWG")
                .pattern("@G&")
                .define('G', Tags.Items.GLASS_BLOCKS)
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('#', Tags.Items.DUSTS_GLOWSTONE)
                .define('!', Tags.Items.DYES_GREEN)
                .define('@', Tags.Items.DYES_BLUE)
                .define('&', Tags.Items.DYES_RED)
                .unlockedBy(getHasName( NuminaObjects.SOLENOID.get()), has( NuminaObjects.SOLENOID.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.ION_THRUSTER.get())
                .pattern(" FS")
                .pattern(" IG")
                .pattern("WFS")
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('F', NuminaObjects.FIELD_EMITTER.get())
                .define('S', NuminaObjects.SOLENOID.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .define('G', Tags.Items.DUSTS_GLOWSTONE)
                .unlockedBy(getHasName( NuminaObjects.SOLENOID.get()), has( NuminaObjects.SOLENOID.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.MAGNET.get())
                .pattern("III")
                .pattern("SSS")
                .pattern("III")
                .define('S', NuminaObjects.SOLENOID.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy(getHasName( NuminaObjects.SOLENOID.get()), has( NuminaObjects.SOLENOID.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.MYOFIBER_GEL.get())
                .pattern(" M ")
                .pattern("IMI")
                .pattern("IMI")
                .define('M', Items.MAGMA_CREAM)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy(getHasName(Items.MAGMA_CREAM), has(Items.MAGMA_CREAM))
                .save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.PARACHUTE.get())
                .pattern("WWW")
                .pattern("S S")
                .pattern("I I")
                .define('S', Items.STRING)
                .define('W', Items.WHITE_WOOL)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy(getHasName(Items.WHITE_WOOL), has(Items.WHITE_WOOL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.PLATING_IRON.get())
                .pattern("II")
                .pattern("WI")
                .pattern("II")
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy(getHasName(NuminaObjects.WIRING_COPPER.get()), has(NuminaObjects.WIRING_COPPER.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.PLATING_DIAMOND.get())
                .pattern("DD")
                .pattern("WD")
                .pattern("DD")
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('D', Tags.Items.GEMS_DIAMOND)
                .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.PLATING_NETHERITE.get())
                .pattern("NN")
                .pattern("WN")
                .pattern("NN")
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('N', Tags.Items.INGOTS_NETHERITE)
                .unlockedBy(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.RUBBER_HOSE.get())
                .pattern("WWW")
                .pattern("G G")
                .pattern("WWW")
                .define('W', Items.WHITE_WOOL)
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy(getHasName(Items.WHITE_WOOL), has(Items.WHITE_WOOL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.SERVO.get())
                .pattern(" S ")
                .pattern("WCW")
                .pattern(" S ")
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('S', NuminaObjects.SOLENOID.get())
                .define('C', NuminaObjects.CONTROL_CIRCUIT_1.get())
                .unlockedBy(getHasName(NuminaObjects.SOLENOID.get()), has(NuminaObjects.SOLENOID.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.SOLAR_PANEL.get())
                .pattern("BGB")
                .pattern("GLG")
                .pattern("III")
                .define('B', Tags.Items.DYES_BLUE)
                .define('G', Tags.Items.GLASS_PANES)
                .define('L', Tags.Items.STORAGE_BLOCKS_LAPIS)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy(getHasName(Items.GLASS_PANE), has(Tags.Items.GLASS_PANES))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.SOLENOID.get())
                .pattern("WIW")
                .pattern("WIW")
                .pattern("WIW")
                .define('W', NuminaObjects.WIRING_COPPER.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy(getHasName(NuminaObjects.WIRING_COPPER.get()), has(NuminaObjects.WIRING_COPPER.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.WIRING_COPPER.get(), 8)
                .pattern("CCC")
                .define('C', Tags.Items.INGOTS_COPPER)
//                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Tags.Items.DUSTS_REDSTONE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NuminaObjects.WIRING_GOLD.get(), 8)
                .pattern("GRG")
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy(getHasName(Items.GOLD_INGOT), has(Tags.Items.INGOTS_GOLD))
                .save(recipeOutput);
    }
}
