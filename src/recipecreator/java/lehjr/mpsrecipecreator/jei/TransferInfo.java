//package lehjr.mpsrecipecreator.jei;
//
//import lehjr.mpsrecipecreator.container.MPARCAbstractContainerMenu;
//import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
//import net.minecraft.inventory.container.Slot;
//import net.minecraft.resources.ResourceLocation;
//
//import java.util.List;
//
//import static mezz.jei.api.constants.VanillaRecipeCategoryUid.CRAFTING;
//
//public class TransferInfo implements IRecipeTransferInfo<MPARCAbstractContainerMenu> {
//    @Override
//    public Class<MPARCAbstractContainerMenu> getAbstractContainerMenuClass() {
//        return MPARCAbstractContainerMenu.class;
//    }
//
//    @Override
//    public ResourceLocation getRecipeCategoryUid() {
//        return CRAFTING;
//    }
//
//    @Override
//    public boolean canHandle(MPARCAbstractContainerMenu mparcAbstractContainerMenu) {
//        return true;
//    }
//
//    @Override
//    public List<Slot> getRecipeSlots(MPARCAbstractContainerMenu mparcAbstractContainerMenu) {
//        return mparcAbstractContainerMenu.slots.subList(1, 10);
//    }
//
//    @Override
//    public List<Slot> getInventorySlots(MPARCAbstractContainerMenu mparcAbstractContainerMenu) {
//        return mparcAbstractContainerMenu.slots.subList(10, mparcAbstractContainerMenu.slots.size() -1);
//    }
//}
