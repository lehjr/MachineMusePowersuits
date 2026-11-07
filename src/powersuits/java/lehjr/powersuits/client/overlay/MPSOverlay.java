package lehjr.powersuits.client.overlay;

import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.blaze3d.platform.Window;
import lehjr.powersuits.client.config.MPSClientConfig;
import lehjr.powersuits.client.control.KeymappingKeyHandler;
import lehjr.powersuits.client.control.MPSKeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;

import java.util.ArrayList;
import java.util.List;

public class MPSOverlay {
    static List<KBDisplay> kbDisplayList = new ArrayList<>();

    public static final LayeredDraw.Layer MPS_KEYBIND_OVERLAY = ((gfx, deltaTracker) -> {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if(player == null)  {
            return;
        }

        Window screen = mc.getWindow();
        float guiScale = (float) screen.getGuiScale();
        float scaledWidth = screen.getWidth()/guiScale;
        float scaledHeight = screen.getHeight()/guiScale;
        MPSMeterOverlay.render(player, gfx, deltaTracker, scaledWidth, scaledHeight);

        AtomicDouble top = new AtomicDouble(MPSClientConfig.hud_keybind_y);
        if (MPSClientConfig.hud_display_keybindings_hud) {// && isModularItemEquipped(player)) {
            for(KBDisplay kbDisplay : kbDisplayList) {
                kbDisplay.refreshKBList();

                if (!kbDisplay.keybindsToRender.isEmpty()) {
                    kbDisplay.setLeft(MPSClientConfig.hud_keybind_x);
                    kbDisplay.setTop(top.get());
                    kbDisplay.setBottom(top.get() + 16);
                    kbDisplay.render(gfx, 0, 0, 0);
                    top.getAndAdd(16);
                }
            }
        }

        top.getAndAdd(4);
        MPSHUD.render(player, gfx, (float)top.get(), (float)MPSClientConfig.hud_keybind_x);
    });

    /**
     * This is now only called when the list needs to be initialized or changes.
     */
    public static void makeKBDisplayList() {
        kbDisplayList.clear();
        for (MPSKeyMapping kb : KeymappingKeyHandler.getMPSKeyMappings()) {
            if(!kb.isUnbound() && kb.showOnHud) {
                boolean added = false;
                for (KBDisplay kbd : kbDisplayList) {
                    if(kbd.finalId.equals(kb.getKey())) {
                        kbd.boundKeybinds.add(kb);
                        added = true;
                        break;
                    }
                }
                if(!added) {
                    kbDisplayList.add(new KBDisplay(kb, MPSClientConfig.hud_keybind_x, MPSClientConfig.hud_keybind_y + 16,  MPSClientConfig.hud_keybind_x + (float) 16));
                }
            }
        }
    }
}
