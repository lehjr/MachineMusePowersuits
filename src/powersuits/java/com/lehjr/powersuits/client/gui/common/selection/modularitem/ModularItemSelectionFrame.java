package com.lehjr.powersuits.client.gui.common.selection.modularitem;

import com.google.common.collect.Lists;
import com.lehjr.numina.client.gui.frame.IGuiFrame;
import com.lehjr.numina.client.gui.geometry.IRect;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.client.gui.geometry.RelativeRect;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.powersuits.client.gui.common.selection.module.ModuleSelectionFrame;
import com.lehjr.powersuits.common.menu.ModularItemInventoryMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class ModularItemSelectionFrame extends GuiComponent implements Widget, GuiEventListener, NarratableEntry, IGuiFrame {
    IRect rect;

    boolean visible = true;
    boolean enabled = true;
    int xOffset = 0;

    // TODO: creative install button
    private final List<AbstractWidget> buttons = Lists.newArrayList();
    ModularItemSelectionTab selectedTab = null;

    StateSwitchingButton2 moduleSelWindowToggle;
    ImageButton creativeInstall;

    ModuleSelectionFrame moduleSelectionFrame = null;
    ModularItemInventoryMenu menu = null;

    public ModularItemSelectionFrame(EquipmentSlot equipmentSlot, @Nullable ModuleSelectionFrame moduleSelectionFrame, ModularItemInventoryMenu menu) {
        setRect(new RelativeRect(0, 0, 0, 0, false));
//        setRect(new DrawableTile(0, 0, 0, 0, false));


        this.menu = menu;
        this.moduleSelectionFrame = moduleSelectionFrame;

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
                        doThisOnSomeEvent();
                    }
                }
            };
            widget.visible = true;
            buttons.add(widget);

            if (equipmentSlot != null && widget.equipmentSlot == equipmentSlot) {
                selectedTab = widget;
            }
        }

        ModularItemSelectionTab last = last(); // if last null here there's a bigger issue.
        // toggle module selection frame visibility
        this.moduleSelWindowToggle = new StateSwitchingButton2((int)this.left(), last.y + 2,
                28, 23, (moduleSelectionFrame != null && moduleSelectionFrame.isVisible()),
                0, 58, 30, 25, 76, 164, ModularItemSelectionTab.BACKGROUND_LOCATION) {

            @Override
            public void onClick(double pMouseX, double pMouseY) {
                super.onClick(pMouseX, pMouseY);
                this.isStateTriggered = !this.isStateTriggered;
                if (moduleSelectionFrame != null) {
                    moduleSelectionFrame.setVisible(this.isStateTriggered);
                }
                doThisOnVisibilityToggle();
            }
        };

        buttons.add(moduleSelWindowToggle);

        this.moduleSelWindowToggle.visible = (moduleSelectionFrame != null);
        this.moduleSelWindowToggle.setPosition((int)this.left() + 2, last.y + last.getHeight());
        this.creativeInstall = new ImageButton(
                (int)this.left() + 2, last.y + this.moduleSelWindowToggle.getHeight(),
                28, 23, 0, 109, 25, ModularItemSelectionTab.BACKGROUND_LOCATION,
                76, 164, (button)->{

            if (moduleSelectionFrame != null) {
                System.out.println("fix me!!! (creative install button)");
//                this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
//                ((ImageButton)button).setPosition(this.leftPos + 5, this.height / 2 - 49);
            }
        });

        creativeInstall.visible = Minecraft.getInstance().player.isCreative() && this.moduleSelectionFrame != null && menu != null;
        this.creativeInstall.setPosition((int)this.left() + 2, moduleSelWindowToggle.y + moduleSelWindowToggle.getHeight());
        buttons.add(creativeInstall);

        this.setWH(new MusePoint2D(30, 166
                + (moduleSelWindowToggle.visible ? moduleSelWindowToggle.getHeight(): 0)
                + (creativeInstall.visible ? creativeInstall.getHeight() : 0)));
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

    @Nullable
    ModularItemSelectionTab last() {
        return Lists.reverse(buttons).stream().filter(ModularItemSelectionTab.class::isInstance).map(ModularItemSelectionTab.class::cast).findFirst().orElse(null);
    }

    public void updateTabs() {
        int left = (int) this.left();//(int) ((this.width() - 147) / 2 - this.xOffset - 30);
        int top = (int) this.top();//(this.height() - 166) / 2 + 3;

        for(AbstractWidget tab : this.buttons) {
            if (tab instanceof ModularItemSelectionTab) {
                tab.visible = true;
                ((ModularItemSelectionTab) tab).setStateTriggered(tab == selectedTab);
                ((ModularItemSelectionTab) tab).setPosition(left, top);
            } else {
                tab.x = left;
                tab.y = top;
            }

            if (tab.visible) {
                top += tab.getHeight();
            }
        }
    }

    @Override
    public void update(double mouseX, double mouseY) {
        IGuiFrame.super.update(mouseX, mouseY);


        updateTabs();
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        IGuiFrame.super.render(matrixStack, mouseX, mouseY, partialTicks);
        for (AbstractWidget tab: buttons) {
            tab.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    public Optional<IModularItem> getModularItem() {
        if (selectedTab != null) {
            EquipmentSlot slot = selectedTab.getEquipmentSlot();
            return Minecraft.getInstance().player.getItemBySlot(slot).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModularItem.class::isInstance)
                    .map(IModularItem.class::cast);
        }
        return Optional.empty();
    }

    public boolean shouldShowModuleSelectionFrame() {
        return moduleSelectionFrame != null && moduleSelWindowToggle.isStateTriggered();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (AbstractWidget tab: buttons) {
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
        return visible;
    }

    @Override
    public void doThisOnSomeEvent() {
        if (this.isVisible() && this.isEnabled()) {
            doThisOnSomeEvent.doThis(this);
        }
    }

    IDoThis doThisOnSomeEvent = null;
    @Override
    public void setDoThisOnSomeEvent(IDoThis iDoThis) {
        this.doThisOnSomeEvent = iDoThis;
    }


    IDoThis doThisOnVisibilityToggle = null;
    public void setDoThisOnVisibilityToggle(IDoThis iDoThis) {
        doThisOnVisibilityToggle = iDoThis;
    }

    public void doThisOnVisibilityToggle() {
        if (doThisOnVisibilityToggle != null) {
            doThisOnVisibilityToggle.doThis(this);
        }
    }

    @Override
    public void doThisOnChange() {
        if (this.isVisible() && this.isEnabled()) {
            updateTabs();
            if (doThis != null) {
                this.doThis.doThis(this);
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
