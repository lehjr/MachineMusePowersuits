package lehjr.numina.mixin.common.recipe;

import lehjr.numina.common.recipe.SmithingUpgradeRecipe;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SmithingRecipe;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmithingMenu.class)
public abstract class SmithingMenuMixin extends ItemCombinerMenu {
    @Shadow @Final public static int ADDITIONAL_SLOT;

    @Shadow
    private RecipeHolder<SmithingRecipe> selectedRecipe;

    public SmithingMenuMixin(@Nullable MenuType<?> type, int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(type, containerId, playerInventory, access);
    }

    @Inject(method = "shrinkStackInSlot", at = @At("HEAD"), cancellable = true)
    private void removeCorrectAdditionalItems(int index, CallbackInfo ci) {
        if (index == ADDITIONAL_SLOT && (this.selectedRecipe.value() instanceof SmithingUpgradeRecipe recipe)) {
            int count = recipe.getAdditionalIngredient().count();
            ItemStack itemstack = this.inputSlots.getItem(index);

            if (!itemstack.isEmpty()) {
                itemstack.shrink(count);
                this.inputSlots.setItem(index, itemstack);
            }
            ci.cancel();
        }
    }
}
