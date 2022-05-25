package com.lehjr.powersuits.client.gui.common.selection.modularitem;

import com.google.common.collect.Lists;
import com.lehjr.numina.client.gui.frame.IGuiFrame;
import com.lehjr.numina.client.gui.geometry.IRect;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.client.gui.geometry.RelativeRect;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class ModularItemSelectionFrame extends GuiComponent implements Widget, GuiEventListener, NarratableEntry, IGuiFrame {
    IRect rect;

    boolean visible = true;
    boolean enabled = true;
    int xOffset = 0;

    // TODO: creative install button
    private final List<ModularItemSelectionTab> tabButtons = Lists.newArrayList();
    ModularItemSelectionTab selectedTab = null;

    public ModularItemSelectionFrame(EquipmentSlot equipmentSlot) {
        setRect(new RelativeRect(0, 0, 0, 0, false));

        for (EquipmentSlot slotType : new EquipmentSlot[]{
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET,
                EquipmentSlot.MAINHAND,
                EquipmentSlot.OFFHAND
        }) {
            ModularItemSelectionTab widget = new ModularItemSelectionTab(slotType) {
                @Override
                public void onClick(double pMouseX, double pMouseY) {
                    super.onClick(pMouseX, pMouseY);
                    boolean update = this != selectedTab;
                    selectedTab = this;
                    if (update) {
                        doThisOnChange();
                    }
                }
            };
            tabButtons.add(widget);

            if (equipmentSlot != null && widget.equipmentSlot == equipmentSlot) {
                selectedTab = widget;
            }
        }
        this.setWH(new MusePoint2D(30,166));
    }

    public Optional<ModularItemSelectionTab> getSelectedTab() {
        if (selectedTab != null && selectedTab.modularItem.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance).map(IModularItem.class::cast).isPresent()) {
            return Optional.of(selectedTab);
        }
        return Optional.empty();
    }

    @Override
    public void setRect(IRect rect) {
        this.rect = rect;
        this.rect.setDoThisOnChange(doThis1 -> this.doThisOnChange());
    }

    public void updateTabs() {
        int i = (int) this.left();//(int) ((this.width() - 147) / 2 - this.xOffset - 30);
        int j = (int) this.top();//(this.height() - 166) / 2 + 3;
        int k = 27;
        int l = 0;
        for(ModularItemSelectionTab tab : this.tabButtons) {
            tab.visible = true;
            tab.setPosition(i, (int)j + k * l++);
            tab.setStateTriggered(tab == selectedTab);
        }
    }

    @Override
    public void update(double mouseX, double mouseY) {
        IGuiFrame.super.update(mouseX, mouseY);
        updateTabs();
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        IGuiFrame.super.render(matrixStack, mouseX, mouseY, partialTicks);
        for (ModularItemSelectionTab tab: tabButtons) {
            tab.render(matrixStack, mouseX, mouseY, partialTicks);
        }
//        System.out.println("frame ul: " + this.getUL());

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {


        for (ModularItemSelectionTab tab: tabButtons) {
            if(tab.mouseClicked(mouseX, mouseY, button)) {
               return true;
            }
        }


        return IGuiFrame.super.mouseClicked(mouseX, mouseY, button);
    }

    @NotNull
    @Override
    public IRect getRect() {
        return rect;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public void doThisOnChange() {
        if (this.isVisible() && this.isEnabled()) {
            updateTabs();
            if (doThis != null) {
                this.doThis.doThisOnChange(this);
            }
        }
    }

    IDoThis doThis = null;
    @Override
    public void setDoThisOnChange(IDoThis iDoThis) {
        doThis = iDoThis;
    }

    @Override
    public NarrationPriority narrationPriority() {
        return this.isVisible() ? NarratableEntry.NarrationPriority.HOVERED : NarratableEntry.NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
        List<NarratableEntry> list = Lists.newArrayList();

        Screen.NarratableSearchResult screen$narratablesearchresult = Screen.findNarratableWidget(list, (NarratableEntry)null);
        if (screen$narratablesearchresult != null) {
            screen$narratablesearchresult.entry.updateNarration(pNarrationElementOutput.nest());
        }
    }
}
