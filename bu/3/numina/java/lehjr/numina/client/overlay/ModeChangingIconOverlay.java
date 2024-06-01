package lehjr.numina.client.overlay;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.inventory.modularitem.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.externalitems.IOtherModItemsAsModules;
import lehjr.numina.common.utils.TagUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ModeChangingIconOverlay {
    public static final LayeredDraw.Layer MODE_CHANGING_ICON_OVERLAY = ((gfx, partialTick) -> {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null) {
            try (Level level = player.level()) {
                for (int i = 0; i < Inventory.getSelectionSize(); i++) {
                    ItemStack itemStack = player.getInventory().getItem(i);
                    Optional<IModeChangingItem> modeChangingItemCap = NuminaCapabilities.getCapability(itemStack, NuminaCapabilities.MODE_CHANGING_MODULAR_ITEM);
                    int finalI = i;
                    modeChangingItemCap.ifPresent(handler -> handler.drawModeChangeIcon(player, finalI, mc, gfx, partialTick, gfx.guiWidth(), gfx.guiHeight()));

                    if (modeChangingItemCap.isEmpty()) {
                        Optional<IOtherModItemsAsModules> foreignModuleCap = NuminaCapabilities.getCapability(itemStack, NuminaCapabilities.EXTERNAL_MOD_ITEMS_AS_MODULES);
                        foreignModuleCap.ifPresent(fmc -> {
                            Optional<IModeChangingItem> storedMCIC = fmc.getStoredModeChangingModuleCapInStorage(TagUtils.getProvider(level));
                            storedMCIC.ifPresent(cap -> {
                                cap.drawModeChangingModularItemIcon(player, finalI, mc, gfx, partialTick, gfx.guiWidth(), gfx.guiHeight());
                            });
                        });
                    }
                }
            } catch (Exception ignored) {
            }
        }
    });

}