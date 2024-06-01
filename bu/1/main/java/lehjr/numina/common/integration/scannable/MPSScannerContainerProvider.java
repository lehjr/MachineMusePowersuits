//package lehjr.numina.common.integration.scannable;
//
//import lehjr.numina.common.utils.ItemUtils;
//import li.cil.scannable.common.container.AbstractHeldItemStackAbstractContainerMenuProvider;
//import li.cil.scannable.common.inventory.ItemHandlerScanner;
//import li.cil.scannable.util.LazyOptionalUtil;
//import net.minecraft.inventory.container.AbstractContainerMenu;
//import net.minecraft.util.Hand;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.common.util.LazyOptional;
//import net.minecraftforge.items.CapabilityItemHandler;
//import net.minecraftforge.items.IItemHandler;
//
//import javax.annotation.Nullable;
//
///**
// * Recreation of Scannable's ScannerAbstractContainerMenuProvider to once again deal with the ItemStack check
// */
//public final class MPSScannerAbstractContainerMenuProvider extends AbstractHeldItemStackAbstractContainerMenuProvider {
//    public MPSScannerAbstractContainerMenuProvider(Player player, Hand hand) {
//        super(player, hand);
//    }
//
//    @Nullable
//    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
//        ItemStack module = ItemUtils.getActiveModuleOrEmpty(player.getItemInHand(this.hand));
//        LazyOptional<IItemHandler> capability = module.getCapability(ForgeCapabilities.ITEM_HANDLER);
//        IItemHandler itemHandler = LazyOptionalUtil.orNull(capability);
//        return this.createAbstractContainerMenu(windowId, inventory, this.hand, itemHandler);
//    }
//
//    protected AbstractContainerMenu createAbstractContainerMenu(int windowId, Inventory inventory, Hand hand, @Nullable IItemHandler itemHandler) {
//        return itemHandler instanceof ItemHandlerScanner ? MPSAbstractContainerMenuScanner.createForServer(windowId, inventory, hand, (ItemHandlerScanner)itemHandler) : null;
//    }
//}
