package lehjr.mpsrecipecreator.jei;

import lehjr.mpsrecipecreator.container.MPSRCContainer;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;

import java.util.List;

import static mezz.jei.api.constants.VanillaRecipeCategoryUid.CRAFTING;

public class TransferInfo implements IRecipeTransferInfo<MPSRCContainer> {
    @Override
    public Class<MPSRCContainer> getContainerClass() {
        return MPSRCContainer.class;
    }

    @Override
    public ResourceLocation getRecipeCategoryUid() {
        return CRAFTING;
    }

    @Override
    public boolean canHandle(MPSRCContainer mparcContainer) {
        return true;
    }

    @Override
    public List<Slot> getRecipeSlots(MPSRCContainer mparcContainer) {
        return mparcContainer.slots.subList(1, 10);
    }

    @Override
    public List<Slot> getInventorySlots(MPSRCContainer mparcContainer) {
        return mparcContainer.slots.subList(10, mparcContainer.slots.size() -1);
    }
}
