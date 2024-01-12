package lehjr.powersuits.client.gui.overlay;

import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.geometry.DrawableRect;
import lehjr.numina.client.render.NuminaRenderer;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.string.StringUtils;
import lehjr.powersuits.client.control.KeymappingKeyHandler;
import lehjr.powersuits.client.control.MPSKeyMapping;
import lehjr.powersuits.common.config.MPSSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class MPSOverlay {
    // TODO: come up with a way to keep KB and overlay items from overlapping



//    public static final IGuiOverlay MPS_METER_OVERLAY = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
//
//    });
//
//    public static final IGuiOverlay MPS_KEYBIND_OVERLAY = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
//
//    });
//
//    public static final IGuiOverlay MPS_MISC_OVERLAY = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
//
//    });





    static List<KBDisplay> kbDisplayList = new ArrayList<>();
    public static final IGuiOverlay MPS_KEYBIND_OVERLAY = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = gui.getMinecraft();
        LocalPlayer player = mc.player;
        Window screen = mc.getWindow();
        float guiScale = (float) screen.getGuiScale();
        float scaledWidth = screen.getWidth()/guiScale;
        float scaledHeight = screen.getHeight()/guiScale;
        MPSMeterOverlay.render(player, poseStack, partialTick, scaledWidth, scaledHeight);


        AtomicDouble top = new AtomicDouble(MPSSettings.getHudKeybindY());
        if (MPSSettings.displayHud() && isModularItemEquipped(player)) {
            kbDisplayList.forEach(kbDisplay -> {
                if (!kbDisplay.boundKeybinds.isEmpty()) {
                    kbDisplay.setLeft(MPSSettings.getHudKeybindX());
                    kbDisplay.setTop(top.get());
                    kbDisplay.setBottom(top.get() + 16);
                    kbDisplay.render(poseStack, 0, 0, mc.getFrameTime());
                    top.getAndAdd(16);
                }
            });
        }
        top.getAndAdd(4);
        MPSHUD.render(player, poseStack, (float)top.get(), MPSSettings.getHudKeybindX());
    });

    public static void makeKBDisplayList() {
        kbDisplayList.clear();
        KeymappingKeyHandler.getMPSKeyMappings().stream().filter(kb->!kb.isUnbound()).filter(kb->kb.showOnHud).forEach(kb->{
            Optional<KBDisplay> kbDisplay = kbDisplayList.stream().filter(kbd->kbd.finalId.equals(kb.getKey())).findFirst();
            if (kbDisplay.isPresent()) {
                kbDisplay.map(kbd->kbd.boundKeybinds.add(kb));
            } else {
                kbDisplayList.add(new KBDisplay(kb, MPSSettings.getHudKeybindX(), MPSSettings.getHudKeybindY() + 16, MPSSettings.getHudKeybindX() + (float) 16));
            }
        });
    }

    static boolean isModularItemEquipped(LocalPlayer player) {
        return Arrays.stream(EquipmentSlot.values()).anyMatch(type ->ItemUtils.getItemFromEntitySlot(player, type).getCapability(ForgeCapabilities.ITEM_HANDLER).filter(IModularItem.class::isInstance).isPresent());
    }

    static class KBDisplay extends DrawableRect {
        List<MPSKeyMapping> boundKeybinds = new ArrayList<>();
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

        LocalPlayer getPlayer() {
            return Minecraft.getInstance().player;
        }

        @Override
        public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
            float stringwidth = (float) StringUtils.getFontRenderer().width(getLabel());
            setWidth(stringwidth + 8 + boundKeybinds.stream().filter(kb->kb.showOnHud).toList().size() * 18);
            super.render(matrixStack, 0, 0, partialTick);
            AtomicBoolean kbToggleVal = new AtomicBoolean(false);
            AtomicDouble x = new AtomicDouble(left() + stringwidth + 8);
            boundKeybinds.stream().filter(kb ->kb.showOnHud).forEach(kb ->{
                boolean active = false;
                // just using the icon
                ItemStack module = new ItemStack(ForgeRegistries.ITEMS.getValue(kb.registryName));
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    ItemStack stack = ItemUtils.getItemFromEntitySlot(getPlayer(), slot);
                    active = stack.getCapability(ForgeCapabilities.ITEM_HANDLER)
                            .filter(IModularItem.class::isInstance)
                            .map(IModularItem.class::cast)
                            .map(iItemHandler -> {
                                if (iItemHandler instanceof IModeChangingItem) {
                                    return ((IModeChangingItem) iItemHandler).hasActiveModule(kb.registryName);
//                                    return ((IModeChangingItem) iItemHandler).isModuleActiveAndOnline(kb.registryName);
                                }
                                return iItemHandler.isModuleOnline(kb.registryName);
                            }).orElse(false);
                    // stop at the first active instance
                    if(active) {
                        kbToggleVal.set(true);
                        break;
                    }
                }
                NuminaRenderer.drawModuleAt(matrixStack, x.get(), top(), module, active);
                x.getAndAdd(16);
            });
            matrixStack.pushPose();
            matrixStack.translate(0,0,100);
            StringUtils.drawLeftAlignedText(matrixStack, getLabel(), (float) left() + 4, (float) top() + 9, (kbToggleVal.get()) ? Color.GREEN : Color.RED);
            matrixStack.popPose();
        }
    }
}