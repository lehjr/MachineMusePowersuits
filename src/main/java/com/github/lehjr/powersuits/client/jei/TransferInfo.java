package com.github.lehjr.powersuits.client.jei;

import com.github.lehjr.powersuits.container.MPSCraftingContainer;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;

import java.util.List;

import static mezz.jei.api.constants.VanillaRecipeCategoryUid.CRAFTING;

public class TransferInfo implements IRecipeTransferInfo<MPSCraftingContainer> {
    @Override
    public Class<MPSCraftingContainer> getContainerClass() {
        return MPSCraftingContainer.class;
    }

    @Override
    public ResourceLocation getRecipeCategoryUid() {
        return CRAFTING;
    }

    @Override
    public boolean canHandle(MPSCraftingContainer container) {
        return true; // FIXME???
    }

    @Override
    public List<Slot> getRecipeSlots(MPSCraftingContainer container) {
        return container.slots.subList(1, 10);
    }

    @Override
    public List<Slot> getInventorySlots(MPSCraftingContainer container) {
        return container.slots.subList(10, container.slots.size() -1);
    }
}
