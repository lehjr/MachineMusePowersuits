package numina.common.recipes;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.crafting.CraftingBookCategory;

public class CraftingBookCategoryLookup {
    public static CraftingBookCategory determineBookCategory(RecipeCategory category) {
        return switch (category) {
            case BUILDING_BLOCKS -> CraftingBookCategory.BUILDING;
            case TOOLS, COMBAT -> CraftingBookCategory.EQUIPMENT;
            case REDSTONE -> CraftingBookCategory.REDSTONE;
            default -> CraftingBookCategory.MISC;
        };
    }
}
