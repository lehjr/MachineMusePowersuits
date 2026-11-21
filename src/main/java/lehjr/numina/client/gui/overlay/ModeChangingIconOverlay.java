package lehjr.numina.client.gui.overlay;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.externalitems.IOtherModItemsAsModules;
import lehjr.numina.common.registration.NuminaCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ModeChangingIconOverlay {

    //void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight);
    public static final IGuiOverlay MODE_CHANGING_ICON_OVERLAY = ((gui, gfx, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = gui.getMinecraft();
        LocalPlayer player = mc.player;

        for (int i = 0; i< Inventory.getSelectionSize(); i++) {
            assert player != null;
            ItemStack itemStack = player.getInventory().getItem(i);
            if (!itemStack.isEmpty()) {
                IModeChangingItem modeChangingItemCap = NuminaCapabilities.getModeChangingModularItemOrStored(itemStack);
                int finalI = i;
                if(modeChangingItemCap != null) {
                    modeChangingItemCap.drawModeChangeIcon(player, finalI, gfx, gfx.guiWidth(), gfx.guiHeight());
                } else {
                    IOtherModItemsAsModules foreignModuleCap = itemStack.getCapability(NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES).resolve().orElse(null);
                    if(foreignModuleCap != null) {
                        IModeChangingItem storedMCIC = foreignModuleCap.getStoredModeChangingModuleCapInStorage();
                        if(storedMCIC != null) {
                            storedMCIC.drawModeChangingModularItemIcon(player, finalI, gfx, gfx.guiWidth(), gfx.guiHeight());
                        }
                    }
                }
            }
        }
    });
}