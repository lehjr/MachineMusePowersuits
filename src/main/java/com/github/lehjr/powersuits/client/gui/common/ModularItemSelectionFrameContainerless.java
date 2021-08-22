package com.github.lehjr.powersuits.client.gui.common;

import com.github.lehjr.numina.util.client.gui.frame.GUISpacer;
import com.github.lehjr.numina.util.client.gui.frame.MultiRectHolderFrame;
import com.github.lehjr.numina.util.client.gui.frame.RectHolderFrame;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.text.ITextComponent;

import java.util.List;
import java.util.Optional;

/**
 * A containerless GUI version of the tab toggle widget holder
 *
 * TODO: replace Modular Item Selection frame with this?
 */
public class ModularItemSelectionFrameContainerless  extends MultiRectHolderFrame {
    final EquipmentSlotType[] equipmentSlotTypes = new EquipmentSlotType[]{
            EquipmentSlotType.HEAD,
            EquipmentSlotType.CHEST,
            EquipmentSlotType.LEGS,
            EquipmentSlotType.FEET,
            EquipmentSlotType.MAINHAND,
            EquipmentSlotType.OFFHAND
    };

    public final List<ModularItemTabToggleWidget> tabButtons = Lists.newArrayList();
    public ModularItemTabToggleWidget selectedTab = null;

    public ModularItemSelectionFrameContainerless() {
        super(false, true, 30, 0);
        /** 6 widgets * 27 high each = 162 + 5 spacers at 3 each = 177 gui height is 200 so 23 to split */
        // top spacer
        addRect(new GUISpacer(30, 11));
        int i=0;
        // look for modular items
        for (EquipmentSlotType slotType : equipmentSlotTypes) {
            ModularItemTabToggleWidget widget = new ModularItemTabToggleWidget(slotType);
            tabButtons.add(widget);
            addRect(new RectHolderFrame(widget, 30, 27, RectHolderFrame.RectPlacement.CENTER_RIGHT) {
                @Override
                public boolean mouseClicked(double mouseX, double mouseY, int button) {
                    widget.onPressed();
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

    void disableContainerSlots() {
    }

    public boolean mouseCLicked(int button, double mouseX, double mouseY) {
        boolean clicked = false;
        for(ModularItemTabToggleWidget recipetabtogglewidget : this.tabButtons) {
            if (recipetabtogglewidget.mouseClicked(mouseX, mouseY, button)) {
                if (this.selectedTab != recipetabtogglewidget) {
                    this.selectedTab.setStateActive(false);
                    this.selectedTab = recipetabtogglewidget;
                    this.selectedTab.setStateActive(true);
                    disableContainerSlots();
                }
                return true;
            }
        }
        return clicked;
    }

    public Optional<EquipmentSlotType> selectedType() {
        return getSelectedTab().map(tab ->tab.getSlotType());
    }

    public boolean selectedIsSlotHovered() {
        return getSelectedTab().map(tab->tab.isHovered()).orElse(false);
    }

    public Optional<ModularItemTabToggleWidget> getSelectedTab() {
        if (this.selectedTab == null) {
            this.selectedTab = this.tabButtons.get(0);
            this.selectedTab.setStateActive(true);
        }
        for (ModularItemTabToggleWidget widget : tabButtons) {
            if (widget != selectedTab) {
                widget.setStateActive(false);
            }
        }
        return Optional.ofNullable(selectedTab);
    }

    @Override
    public void update(double mouseX, double mouseY) {
        super.update(mouseX, mouseY);
        getSelectedTab();
    }
}
