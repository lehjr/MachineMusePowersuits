package lehjr.powersuits.client.gui.common;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.clickable.ClickableButton;
import lehjr.numina.client.gui.clickable.IClickable;
import lehjr.numina.client.gui.frame.AbstractGuiFrame;
import lehjr.numina.client.gui.gemoetry.DrawableTile;
import lehjr.numina.client.gui.gemoetry.IRect;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.gui.gemoetry.Rect;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.math.Colour;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ModularItemSelectionFrame extends AbstractGuiFrame {
    public ModularItemTabToggleWidget selectedTab = null;
    public ClickableButton creativeInstallButton;

    IChanged changed;

    List<IRect> boxes;
    final List<EquipmentSlotType> equipmentSlotTypes = Arrays.asList(
            EquipmentSlotType.HEAD,
            EquipmentSlotType.CHEST,
            EquipmentSlotType.LEGS,
            EquipmentSlotType.FEET,
            EquipmentSlotType.MAINHAND,
            EquipmentSlotType.OFFHAND);
    public ModularItemSelectionFrame(MusePoint2D ul) {
        this(ul, EquipmentSlotType.HEAD);
    }

    public ModularItemSelectionFrame(MusePoint2D ul, EquipmentSlotType type) {
        super(new DrawableTile(ul, ul.plus(35, 200)));
        boxes = new ArrayList<>();
        // each tab is 27 tall and 35 wide
        /** 6 widgets * 27 high each = 162 + 5 spacers at 3 each = 177 gui height is 200 so 23 to split */

        // top spacer
        boxes.add(new Rect(ul.copy(), ul.plus(35, 11)));

        int i = 0;
        // look for modular items
        for (EquipmentSlotType slotType : equipmentSlotTypes) {
            ModularItemTabToggleWidget widget = new ModularItemTabToggleWidget(slotType);
            widget.setUL(ul.copy());
            widget.setBelow(boxes.get(boxes.size() - 1));

            widget.setOnPressed(pressed -> {
                if (widget != selectedTab) {
                    this.selectedTab.setStateActive(false);
                    this.selectedTab = widget;
                    this.selectedTab.setStateActive(true);
                    this.onChanged();
                    disableContainerSlots();
                }
            });

            if (slotType == type) {
                this.selectedTab = widget;
                this.selectedTab.setStateActive(true);
            }

            boxes.add(widget);
            boxes.add(new Rect(ul, ul.plus(35, 3)));
        }
        creativeInstallButton = new ClickableButton(new TranslationTextComponent("gui.powersuits.creative.install"), MusePoint2D.ZERO, false);
        creativeInstallButton.setHeight(18);
        creativeInstallButton.setWidth(30);
        creativeInstallButton.disableAndHide();
        creativeInstallButton.setEnabledBackground(Colour.LIGHT_GREY);
        creativeInstallButton.setDisabledBackground(Colour.RED);
        creativeInstallButton.setUL(getUL().copy());


//        List<ITextComponent> toolTip =  new ArrayList<ITextComponent>() {{
//            add(new TranslationTextComponent("gui.powersuits.creative.install.desc"));
//        }};
        boxes.add(creativeInstallButton);
        refreshRects();
    }

    public void refreshRects() {
        double finalHeight = 0;

        for (int index = 0; index < boxes.size(); index++) {
            IRect rect = boxes.get(index);
            if (index == 0) {
                rect.setUL(this.getUL());
            } else {
                rect.setLeft(left()).setBelow(boxes.get(index -1));
            }
            finalHeight += rect.height();;
        }
        setHeight(finalHeight);
    }

    public ClickableButton getCreativeInstallButton() {
        return creativeInstallButton;
    }

    void disableContainerSlots() {
    }

    public ItemStack getModularItemOrEmpty() {
        return getSelectedTab().map(tab -> {
            ItemStack stack = getStack(tab.getSlotType());
            return getModularItemCapability(tab.getSlotType()).isPresent() ? stack : ItemStack.EMPTY;
        }).orElse(ItemStack.EMPTY);
    }

    public Optional<IModularItem> getModularItemCapability () {
        return getModularItemOrEmpty().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast);
    }

    public boolean playerHasModularItems() {
        return equipmentSlotTypes.stream()
                .filter(type->getModularItemCapability(type)
                        .isPresent()).findFirst().isPresent();
    }

    Optional<IModularItem> getModularItemCapability (EquipmentSlotType type) {
        return getStack(type).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast);
    }

    @Nonnull
    ItemStack getStack(EquipmentSlotType type) {
        return getMinecraft().player.getItemBySlot(type);
    }

    public Optional<EquipmentSlotType> selectedType() {
        return getSelectedTab().map(tab ->tab.getSlotType());
    }

//    public boolean selectedIsSlotHovered() {
//        return getSelectedTab().map(tab->tab.isHovered()).orElse(false);
//    }

    public Optional<ModularItemTabToggleWidget> getSelectedTab() {
        if (this.selectedTab == null) {
            this.selectedTab = boxes.stream().filter(ModularItemTabToggleWidget.class::isInstance).map(ModularItemTabToggleWidget.class::cast).findFirst().orElse(null);
            if (selectedTab != null) {
                this.selectedTab.setStateActive(true);
            }
        }

        boxes.stream().filter(ModularItemTabToggleWidget.class::isInstance)
                .map(ModularItemTabToggleWidget.class::cast).forEach(widget ->
                        widget.setStateActive(widget==selectedTab));

        return Optional.ofNullable(selectedTab);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (boxes.stream().filter(IClickable.class::isInstance)
                .map(IClickable.class::cast).filter(box->box.mouseClicked(mouseX, mouseY, button)).findFirst().isPresent()) {
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (boxes.stream().filter(IClickable.class::isInstance)
                .map(IClickable.class::cast).filter(box->box.mouseReleased(mouseX, mouseY, button)).findFirst().isPresent()) {
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        boxes.stream().filter(IClickable.class::isInstance)
                .map(IClickable.class::cast)
                .forEach(box-> {
                    box.render(matrixStack, mouseX, mouseY, partialTicks);
                });
    }

    @Nullable
    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
        return boxes.stream().filter(IClickable.class::isInstance)
                .map(IClickable.class::cast).filter(box -> box.containsPoint(x, y))
                .findFirst().map(box -> box.getToolTip(x, y)).orElse(super.getToolTip(x, y));
    }

    @Override
    public void update(double mouseX, double mouseY) {
        super.update(mouseX, mouseY);
        getSelectedTab();
    }



    public void setOnChanged(IChanged changed) {
        this.changed = changed;
    }

    public void onChanged() {
        if(this.isEnabled() && this.isVisible()) {
            refreshRects();
            if (changed != null) {
                this.changed.onChanged();
            }
        }
    }

    public interface IChanged {
        void onChanged();
    }
}
