package com.lehjr.numina.client.overlay;

import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.module.externalitems.IOtherModItemsAsModules;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ModeChangingIconOverlay {
    public static final LayeredDraw.Layer MODE_CHANGING_ICON_OVERLAY = ((gfx, partialTick) -> {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null) {
            Level level = player.level();
            for (int i = 0; i < net.minecraft.world.entity.player.Inventory.getSelectionSize(); i++) {
                ItemStack itemStack = player.getInventory().getItem(i);
                if (!itemStack.isEmpty()) {
                    IModeChangingItem modeChangingItemCap = NuminaCapabilities.getModeChangingModularItem(itemStack);
                    int finalI = i;
                    if(modeChangingItemCap != null) {
                        modeChangingItemCap.drawModeChangeIcon(player, finalI, gfx, gfx.guiWidth(), gfx.guiHeight());
                    } else {
                        IOtherModItemsAsModules foreignModuleCap = itemStack.getCapability(NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES);
                        if(foreignModuleCap != null) {
                            IModeChangingItem storedMCIC = foreignModuleCap.getStoredModeChangingModuleCapInStorage(NuminaCapabilities.getProvider(level));
                            if(storedMCIC != null) {
                                storedMCIC.drawModeChangingModularItemIcon(player, finalI, gfx, gfx.guiWidth(), gfx.guiHeight());
                            }
                        }
                    }
                }
            }
        }
    });
}
