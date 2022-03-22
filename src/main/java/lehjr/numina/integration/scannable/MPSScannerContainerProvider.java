package lehjr.numina.integration.scannable;

import lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.util.item.ItemUtils;
import li.cil.scannable.common.container.AbstractHeldItemStackContainerProvider;
import li.cil.scannable.common.inventory.ItemHandlerScanner;
import li.cil.scannable.util.LazyOptionalUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * Recreation of Scannable's ScannerContainerProvider to once again deal with the ItemStack check
 */
public final class MPSScannerContainerProvider extends AbstractHeldItemStackContainerProvider {
    public MPSScannerContainerProvider(PlayerEntity player, Hand hand) {
        super(player, hand);
    }

    @Nullable
    public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity player) {
        ItemStack module = ItemUtils.getActiveModuleOrEmpty(player.getItemInHand(this.hand));
        LazyOptional<IItemHandler> capability = module.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
        IItemHandler itemHandler = LazyOptionalUtil.orNull(capability);
        return this.createContainer(windowId, inventory, this.hand, itemHandler);
    }

    protected Container createContainer(int windowId, PlayerInventory inventory, Hand hand, @Nullable IItemHandler itemHandler) {
        return itemHandler instanceof ItemHandlerScanner ? MPSContainerScanner.createForServer(windowId, inventory, hand, (ItemHandlerScanner)itemHandler) : null;
    }
}
