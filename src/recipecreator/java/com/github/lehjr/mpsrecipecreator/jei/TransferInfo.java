package com.github.lehjr.mpsrecipecreator.jei;

import com.github.lehjr.mpsrecipecreator.container.MPARCContainer;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;

import java.util.List;

import static mezz.jei.api.constants.VanillaRecipeCategoryUid.CRAFTING;

public class TransferInfo implements IRecipeTransferInfo<MPARCContainer> {
    @Override
    public Class<MPARCContainer> getContainerClass() {
        return MPARCContainer.class;
    }

    @Override
    public ResourceLocation getRecipeCategoryUid() {
        return CRAFTING;
    }

    @Override
    public boolean canHandle(MPARCContainer mparcContainer) {
        return true;
    }

    @Override
    public List<Slot> getRecipeSlots(MPARCContainer mparcContainer) {
        return mparcContainer.slots.subList(1, 10);
    }

    @Override
    public List<Slot> getInventorySlots(MPARCContainer mparcContainer) {
        return mparcContainer.slots.subList(10, mparcContainer.slots.size() -1);
    }
}
