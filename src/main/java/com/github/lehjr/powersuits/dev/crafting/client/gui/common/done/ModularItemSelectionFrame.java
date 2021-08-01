package com.github.lehjr.powersuits.dev.crafting.client.gui.common.done;

import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.client.gui.frame.GUISpacer;
import com.github.lehjr.numina.util.client.gui.frame.MultiRectHolderFrame;
import com.github.lehjr.numina.util.client.gui.frame.RectHolderFrame;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.List;

public class ModularItemSelectionFrame extends MultiRectHolderFrame {
    final EquipmentSlotType[] equipmentSlotTypes = new EquipmentSlotType[]{
            EquipmentSlotType.HEAD,
            EquipmentSlotType.CHEST,
            EquipmentSlotType.LEGS,
            EquipmentSlotType.FEET,
            EquipmentSlotType.MAINHAND,
            EquipmentSlotType.OFFHAND
    };

    public final List<MPSRecipeTabToggleWidget> tabButtons = Lists.newArrayList();
    public MPSRecipeTabToggleWidget selectedTab = null;
    public ModularItemSelectionFrame() {
        super(false, true, 30, 0);
        /** 6 widgets * 27 high each = 162 + 5 spacers at 3 each = 177 gui height is 200 so 23 to split */

        // top spacer
        addRect(new GUISpacer(30, 11));

        Minecraft minecraft = Minecraft.getInstance();
        int i=0;
        // look for modular items
        for (EquipmentSlotType slotType : equipmentSlotTypes) {
            ItemStack itemStack = minecraft.player.getItemBySlot(slotType);
            MPSRecipeTabToggleWidget widget = new MPSRecipeTabToggleWidget(itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(iItemHandler -> {
                if (iItemHandler instanceof IModularItem) {
                    return itemStack;
                }
                return ItemStack.EMPTY;
            }).orElse(ItemStack.EMPTY), slotType);
            tabButtons.add(widget);

            addRect(new RectHolderFrame(widget, 30, 27, RectHolderFrame.RectPlacement.CENTER_RIGHT) {
                @Override
                public boolean mouseClicked(double mouseX, double mouseY, int button) {
                    if (widget.mouseClicked(mouseX, mouseY, button)) {
                        selectedTab = widget;
                        widget.setStateTriggered(true);
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean mouseReleased(double mouseX, double mouseY, int button) {
                    return widget.mouseReleased(mouseX, mouseY, button);
                }

                @Override
                public List<ITextComponent> getToolTip(int x, int y) {
                    return widget.getToolTip(x, y);
                }

                @Override
                public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
                    super.render(matrixStack, mouseX, mouseY, frameTime);
//                    System.out.println("widget position: " + widget.getPosition());

                    widget.render(matrixStack, mouseX, mouseY, frameTime);
                }
            });

            // spacer under each widget
            if (i < 5) {
                addRect(new GUISpacer(30, 3));

            // bottom spacer
            } else {
                addRect(new GUISpacer(30, 12));
                doneAdding();
            }
            i++;
        }
    }

    @Override
    public void update(double mouseX, double mouseY) {
        super.update(mouseX, mouseY);
        if (this.selectedTab == null) {
            this.selectedTab = this.tabButtons.get(0);
            this.selectedTab.setStateTriggered(true);
        }
        for (MPSRecipeTabToggleWidget widget : tabButtons) {
            if (widget != selectedTab) {
                widget.setStateTriggered(false);
            }
        }
    }
}