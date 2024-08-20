//package lehjr.powersuits.client.jei;
//
//import com.lehjr.powersuits.container.InstallSalvageCraftAbstractContainerMenu;
//import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
//import net.minecraft.inventory.container.Slot;
//import net.minecraft.resources.ResourceLocation;
//
//import java.util.List;
//
//import static mezz.jei.api.constants.VanillaRecipeCategoryUid.CRAFTING;
//
//public class TransferInfo implements IRecipeTransferInfo<InstallSalvageCraftAbstractContainerMenu> {
//    @Override
//    public Class<InstallSalvageCraftAbstractContainerMenu> getAbstractContainerMenuClass() {
//        return InstallSalvageCraftAbstractContainerMenu.class;
//    }
//
//    @Override
//    public ResourceLocation getRecipeCategoryUid() {
//        return CRAFTING;
//    }
//
//    @Override
//    public boolean canHandle(InstallSalvageCraftAbstractContainerMenu container) {
//        return true; // FIXME???
//    }
//
//    @Override
//    public List<Slot> getRecipeSlots(InstallSalvageCraftAbstractContainerMenu container) {
//        return container.slots.subList(1, 10);
//    }
//
//    @Override
//    public List<Slot> getInventorySlots(InstallSalvageCraftAbstractContainerMenu container) {
//        return container.slots.subList(10, container.slots.size() -1);
//    }
//}
