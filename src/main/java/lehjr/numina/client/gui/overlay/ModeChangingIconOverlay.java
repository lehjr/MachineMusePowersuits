package lehjr.numina.client.gui.overlay;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.externalitems.IOtherModItemsAsModules;
import lehjr.numina.common.item.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Optional;

public class ModeChangingIconOverlay {
    public static final IGuiOverlay MODE_CHANGING_ICON_OVERLAY = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = gui.getMinecraft();
        LocalPlayer player = mc.player;

        for (int i = 0; i< Inventory.getSelectionSize(); i++) {
            assert player != null;
            ItemStack itemStack = player.getInventory().getItem(i);
            Optional<IModeChangingItem> modeChangingItemCap = ItemUtils.getModeChangingModularItemCapability(itemStack);
            int finalI = i;
            modeChangingItemCap.ifPresent(handler-> handler.drawModeChangeIcon(player, finalI, gui, mc, poseStack, partialTick, screenWidth, screenHeight));

            if (modeChangingItemCap.isEmpty()) {
                Optional<IOtherModItemsAsModules> foreignModuleCap = ItemUtils.getForeignItemAsModuleCap(itemStack);
                foreignModuleCap.ifPresent(fmc->{
                    Optional<IModeChangingItem> storedMCIC = fmc.getStoredModeChangingModuleCapInStorage();
                    storedMCIC.ifPresent(cap->{
                        cap.drawModeChangingModularItemIcon(player, finalI, gui, mc, poseStack, partialTick, screenWidth, screenHeight);
                    });
                });
            }
        }
    });
}