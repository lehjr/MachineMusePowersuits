//package lehjr.powersuits.client.jei;
//
//import lehjr.powersuits.container.InstallSalvageCraftContainer;
//import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
//import net.minecraft.inventory.container.Slot;
//import net.minecraft.util.ResourceLocation;
//
//import java.util.List;
//
//import static mezz.jei.api.constants.VanillaRecipeCategoryUid.CRAFTING;
//
//public class TransferInfo implements IRecipeTransferInfo<InstallSalvageCraftContainer> {
//    @Override
//    public Class<InstallSalvageCraftContainer> getContainerClass() {
//        return InstallSalvageCraftContainer.class;
//    }
//
//    @Override
//    public ResourceLocation getRecipeCategoryUid() {
//        return CRAFTING;
//    }
//
//    @Override
//    public boolean canHandle(InstallSalvageCraftContainer container) {
//        return true; // FIXME???
//    }
//
//    @Override
//    public List<Slot> getRecipeSlots(InstallSalvageCraftContainer container) {
//        return container.slots.subList(1, 10);
//    }
//
//    @Override
//    public List<Slot> getInventorySlots(InstallSalvageCraftContainer container) {
//        return container.slots.subList(10, container.slots.size() -1);
//    }
//}
