package lehjr.powersuits.client.overlay;

import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.blaze3d.platform.InputConstants;
import lehjr.numina.client.gui.geometry.DrawableRect;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.IconUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.StringUtils;
import lehjr.powersuits.client.control.MPSKeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class KBDisplay extends DrawableRect {
    List<MPSKeyMapping> boundKeybinds = new ArrayList<>();
    Map<MPSKeyMapping, Boolean> keybindsToRender = new HashMap<>();

    final InputConstants.Key finalId;
    public KBDisplay(MPSKeyMapping kb, double left, double top, double right) {
        super(left, top, right, top + 16, true, Color.DARK_GREEN.withAlpha(0.2F), Color.GREEN.withAlpha(0.2F));
        this.finalId = kb.getKey();
        addKeyBind(kb);
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
        // clear the map first...
        keybindsToRender.clear();

        // ...then filter and repopulate it
        for (MPSKeyMapping  kb : boundKeybinds) {
            if(kb.showOnHud) {
                boolean installed = false;
                boolean active = false;
                // just using the icon
                Item module = BuiltInRegistries.ITEM.get(kb.registryName);
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    ItemStack stack = ItemUtils.getItemFromEntitySlot(getPlayer(), slot);
                    IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(stack);
                    if(iModularItem != null) {
                        int index = iModularItem.findInstalledModule(module);
                        if (index != -1) {
                            installed = true;
                            if (iModularItem instanceof IModeChangingItem mci) {
                                active = mci.hasActiveModule(index, module);
                            } else if (slot.isArmor() && iModularItem.isModuleOnline(index)) {
                                active = true;
                            }
                        }
                    }

                    // stop at the first active instance
                    if(active) {
                        break;
                    }
                }
                if (installed) {
                    keybindsToRender.put(kb, active);
                }
            }
        }
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
