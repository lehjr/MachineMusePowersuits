package com.github.lehjr.powersuits.dev.crafting.jei;

import com.github.lehjr.powersuits.dev.crafting.container.NuminaCraftingContainer;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;

import java.util.List;

import static mezz.jei.api.constants.VanillaRecipeCategoryUid.CRAFTING;

public class TransferInfo implements IRecipeTransferInfo<NuminaCraftingContainer> {
    @Override
    public Class<NuminaCraftingContainer> getContainerClass() {
        return NuminaCraftingContainer.class;
    }

    @Override
    public ResourceLocation getRecipeCategoryUid() {
        return CRAFTING;
    }

    @Override
    public boolean canHandle(NuminaCraftingContainer container) {
        return true; // FIXME???
    }

    @Override
    public List<Slot> getRecipeSlots(NuminaCraftingContainer container) {
        return container.slots.subList(1, 10);
    }

    @Override
    public List<Slot> getInventorySlots(NuminaCraftingContainer container) {
        return container.slots.subList(10, container.slots.size() -1);
    }
}
