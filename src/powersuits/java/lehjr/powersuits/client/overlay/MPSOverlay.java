package lehjr.powersuits.client.overlay;

import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import lehjr.numina.client.gui.geometry.DrawableRect;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.utils.IconUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.StringUtils;
import lehjr.powersuits.client.config.MPSClientConfig;
import lehjr.powersuits.client.control.KeymappingKeyHandler;
import lehjr.powersuits.client.control.MPSKeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MPSOverlay {
    static List<KBDisplay> kbDisplayList = new ArrayList<>();

    public static final LayeredDraw.Layer MPS_KEYBIND_OVERLAY = ((gfx, partialTick) -> {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        Window screen = mc.getWindow();
        float guiScale = (float) screen.getGuiScale();
        float scaledWidth = screen.getWidth()/guiScale;
        float scaledHeight = screen.getHeight()/guiScale;
        MPSMeterOverlay.render(player, gfx, partialTick, scaledWidth, scaledHeight);

        AtomicDouble top = new AtomicDouble(MPSClientConfig.hud_keybind_y);
        if (MPSClientConfig.hud_display_keybindings_hud && isModularItemEquipped(player)) {
            kbDisplayList.forEach(kbDisplay -> {
                kbDisplay.refreshKBList();

                if (!kbDisplay.keybindsToRender.isEmpty()) {
                    kbDisplay.setLeft(MPSClientConfig.hud_keybind_x);
                    kbDisplay.setTop(top.get());
                    kbDisplay.setBottom(top.get() + 16);
                    kbDisplay.render(gfx, 0, 0, mc.getFrameTime());
                    top.getAndAdd(16);
                }
            });
        }
        top.getAndAdd(4);
        MPSHUD.render(player, gfx, (float)top.get(), (float)MPSClientConfig.hud_keybind_x);
    });

    public static void makeKBDisplayList() {
        kbDisplayList.clear();
        KeymappingKeyHandler.getMPSKeyMappings().stream().filter(kb->!kb.isUnbound()).filter(kb->kb.showOnHud).forEach(kb->{
            Optional<KBDisplay> kbDisplay = kbDisplayList.stream().filter(kbd->kbd.finalId.equals(kb.getKey())).findFirst();
            if (kbDisplay.isPresent()) {
                kbDisplay.map(kbd->kbd.boundKeybinds.add(kb));
            } else {
                kbDisplayList.add(new KBDisplay(kb, MPSClientConfig.hud_keybind_x, MPSClientConfig.hud_keybind_y + 16,  MPSClientConfig.hud_keybind_x + (float) 16));
            }
        });
    }

    static boolean isModularItemEquipped(LocalPlayer player) {
        return Arrays.stream(EquipmentSlot.values()).anyMatch(type ->NuminaCapabilities.getOptionalModularItemOrModeChangingCapability(ItemUtils.getItemFromEntitySlot(player, type)).isPresent());
    }

    static class KBDisplay extends DrawableRect {
        List<MPSKeyMapping> boundKeybinds = new ArrayList<>();
        Map<MPSKeyMapping, Boolean> keybindsToRender = new HashMap<>();

        final InputConstants.Key finalId;
        public KBDisplay(MPSKeyMapping kb, double left, double top, double right) {
            super(left, top, right, top + 16, true, Color.DARK_GREEN.withAlpha(0.2F), Color.GREEN.withAlpha(0.2F));
            this.finalId = kb.getKey();
            boundKeybinds.add(kb);
        }

        public Component getLabel() {
            return finalId.getDisplayName();
        }

        public void addKeyBind(MPSKeyMapping kb) {
            if (!boundKeybinds.contains(kb)){
                boundKeybinds.add(kb);
            }
        }

        void refreshKBList() {
            boundKeybinds.stream().filter(kb ->kb.showOnHud).forEach(kb ->{
                AtomicBoolean installed = new AtomicBoolean(false);
                AtomicBoolean active = new AtomicBoolean( false);
                // just using the icon
                ItemStack module = new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(kb.registryName)));

                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    ItemStack stack = ItemUtils.getItemFromEntitySlot(getPlayer(), slot);
                    NuminaCapabilities.getOptionalModularItemOrModeChangingCapability(stack).ifPresent(iModularItem -> {
                        if(iModularItem.isModuleInstalled(module)) {
                            installed.set(true);
                            if (iModularItem instanceof IModeChangingItem mci && mci.hasActiveModule(kb.registryName)) {
                                active.set(true); ;
                            } else if (slot.isArmor() && iModularItem.isModuleOnline(kb.registryName)) {
                                active.set(true);
                            }
                        }
                    });

                    // stop at the first active instance
                    if(active.get()) {
                        break;
                    }
                }
                if (installed.get()) {
                    keybindsToRender.put(kb, active.get());
                }
            });
        }

        LocalPlayer getPlayer() {
            return Minecraft.getInstance().player;
        }

        @Override
        public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
            if(keybindsToRender.isEmpty()) {
                return;
            }
            float stringwidth = (float) StringUtils.getFontRenderer().width(getLabel());
            setWidth(stringwidth + 8 + keybindsToRender.keySet().stream().toList().size() * 18);
            super.render(gfx, 0, 0, partialTick);
            AtomicBoolean kbToggleVal = new AtomicBoolean(false);
            AtomicDouble x = new AtomicDouble(left() + stringwidth + 8);

            keybindsToRender.forEach((kb, active)-> {
                // just using the icon
                ItemStack module = new ItemStack(Objects.requireNonNull(BuiltInRegistries.ITEM.get(kb.registryName)));
                if(active) {
                    kbToggleVal.set(true);
                }

                IconUtils.drawModuleAt(gfx, x.get(), top(), module, active);
                x.getAndAdd(16);
            });

            gfx.pose().pushPose();
            gfx.pose().translate(0,0,100);
            StringUtils.drawLeftAlignedText(gfx, getLabel(), (float) left() + 4, (float) top() + 9, (kbToggleVal.get()) ? Color.GREEN : Color.RED);
            gfx.pose().popPose();
        }
    }
}