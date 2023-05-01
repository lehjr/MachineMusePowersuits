package lehjr.numina.client.gui.overlay;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class ModeChangingIconOverlay {

    public static final IGuiOverlay MODE_CHANGING_ICON_OVERLAY = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = gui.getMinecraft();
        LocalPlayer player = mc.player;
        int i = player.getInventory().selected;

        player.getInventory().getSelected().getCapability(ForgeCapabilities.ITEM_HANDLER)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .ifPresent(handler-> handler.drawModeChangeIcon(player, i, gui, mc, poseStack, partialTick, screenWidth, screenHeight));

    });
}
