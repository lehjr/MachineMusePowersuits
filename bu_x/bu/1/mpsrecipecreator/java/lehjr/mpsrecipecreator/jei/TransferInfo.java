package lehjr.mpsrecipecreator.jei;

import com.lehjr.mpsrecipecreator.container.MPSRCMenu;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.CraftingRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class TransferInfo implements IRecipeTransferInfo<MPSRCMenu, CraftingRecipe> {
    @Override
    public Class<? extends MPSRCMenu> getContainerClass() {
        return MPSRCMenu.class;
    }

    @Override
    public Optional<MenuType<MPSRCMenu>> getMenuType() {
        return Optional.empty();
    }

    @Override
    public RecipeType getRecipeType() {
        return RecipeTypes.CRAFTING;
    }

    @Override
    public @Nonnull List<Slot> getInventorySlots(@Nonnull MPSRCMenu container, @Nonnull CraftingRecipe recipe) {
        return container.slots.subList(10, container.slots.size() -1);
    }

    @Override
    public List<Slot> getRecipeSlots(MPSRCMenu container, CraftingRecipe recipe) {
        return container.slots.subList(1, 10);
    }

    @Override
    public boolean canHandle(MPSRCMenu container, CraftingRecipe recipe) {
        return true;
    }
}
