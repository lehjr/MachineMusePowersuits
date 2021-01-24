package com.github.lehjr.powersuits.client.gui.modding.module;

import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableButton;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableItem;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableModule;
import com.github.lehjr.numina.util.client.gui.frame.ScrollableFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.sound.SoundDictionary;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.string.MuseStringUtils;
import com.github.lehjr.powersuits.client.gui.common.ItemSelectionFrame;
import com.github.lehjr.powersuits.container.MPSWorkbenchContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class InstallSalvageFrame extends ScrollableFrame {
    MPSWorkbenchContainer container;
    protected ItemSelectionFrame targetItem;
    protected ModuleSelectionFrame targetModule;
    protected ClickableButton installButton;
    protected ClickableButton salvageButton;
    protected PlayerEntity player;
    Minecraft mc;
    float zLevel;

    public InstallSalvageFrame(
            MPSWorkbenchContainer containerIn,
            PlayerEntity player,
            MusePoint2D topleft,
            MusePoint2D bottomright,
            float zLevel,
            Colour backgroundColour,
            Colour borderColour,
            ItemSelectionFrame targetItem,
            ModuleSelectionFrame targetModule) {
        super(topleft, bottomright, zLevel, backgroundColour, borderColour);
        this.container = containerIn;
        this.player = player;
        this.targetItem = targetItem;
        this.targetModule = targetModule;
        double sizex = border.right() - border.left();
        double sizey = border.bottom() - border.top();
        mc = Minecraft.getInstance();

        /** Install button -------------------------------------------------------------------------------------------- */
        this.installButton = new ClickableButton(new TranslationTextComponent("gui.powersuits.install"),
                new MusePoint2D(border.right() - sizex / 2.0, border.bottom() - sizey / 4.0),
                true);

        this.installButton.setOnPressed(press->{
            if (targetItem.getSelectedItem() == null || targetModule.getSelectedModule() == null) {
                return;
            }

            ItemStack module =  targetModule.getSelectedModule().getModule();
            Integer containerIndex = targetItem.getSelectedItem().containerIndex;

            // target container slot index
            int moduleTarget = -1;
            if (containerIndex != null) {
                moduleTarget = getModuleTargetIndexInModularItem(containerIndex, targetModule.getSelectedModule().getModule());
            }

            if (moduleTarget != -1) {
                if (player.abilities.isCreativeMode) {
                    player.playSound(SoundDictionary.SOUND_EVENT_GUI_INSTALL, 1, 0);
                    containerIn.creativeInstall(moduleTarget, new ItemStack(module.getItem()));
                } else {
                    int sourceIndex = getContainerIndexForModuleIndexInPlayerInventory(module);
                    player.playSound(SoundDictionary.SOUND_EVENT_GUI_INSTALL, 1, 0);
                    containerIn.move(sourceIndex, moduleTarget);
                }
            }
        });

        /** Salvage button -------------------------------------------------------------------------------------------- */
        this.salvageButton = new ClickableButton(new TranslationTextComponent("gui.powersuits.salvage"),
                new MusePoint2D(border.left() + sizex / 2.0, border.top() + sizey / 4.0),
                true);
        this.salvageButton.setOnPressed(pressed->{
            if (targetItem.getSelectedItem() != null && targetModule.getSelectedModule() != null && targetModule.getSelectedModule().isInstalled()) {
                Integer containerIndex = targetItem.getSelectedItem().containerIndex;

                if (containerIndex != null) {
                    List<SlotItemHandler> slots = container.getModularItemToSlotMap().get(targetItem.getSelectedItem().containerIndex);

                    Integer moduleContainerIndex = null;
                    for (SlotItemHandler slot : slots) {
                        if (ItemStack.areItemStacksEqual(slot.getStack(), targetModule.getSelectedModule().getModule())) {
                            moduleContainerIndex = slot.slotNumber;
                            break;
                        }
                    }

                    if(moduleContainerIndex != null) {
                        int targetIndex = getModuleTargetIndexInPlayerInventory(targetModule.getSelectedModule().getModule());
                        if (targetIndex > 0) {
                            player.playSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1, 0);
                            this.container.move(moduleContainerIndex, targetIndex);
                        }
                    }
                }
            }
        });
    }

    /**
     * Find a container slot index that holds the module to install
     * Only returns the first inventory index.
     *
     * @param module
     * @return
     */
    int getContainerIndexForModuleIndexInPlayerInventory(@Nonnull ItemStack module) {
        if (module.isEmpty())
            return -1;

        for (Slot slot : container.inventorySlots) {
            if (!slot.getStack().isEmpty() &&
                    slot.inventory == player.inventory &&
                    slot.getStack().isItemEqual(module)) {
                return slot.slotNumber;
            }
        }
        return -1;
    }

    /**
     *  Finds an empty container slot linked to player inventory to
     * put the module into when removed
     * @param module
     * @return
     */
    int getModuleTargetIndexInPlayerInventory(@Nonnull ItemStack module) {
        int index = -1;
        int targetIndex = player.inventory.getFirstEmptyStack();

        for (Slot slot : container.inventorySlots) {
            if (slot.inventory == player.inventory && targetIndex == slot.getSlotIndex()) {
                return container.inventorySlots.indexOf(slot);
            }
        }
        return index;
    }

    /**
     * Finds a slot that will except the module
     * @param modularItemIndex
     * @param module
     * @return
     */
    int getModuleTargetIndexInModularItem(int modularItemIndex, @Nonnull ItemStack module) {
        Slot slot = container.getSlot(modularItemIndex);
        if (slot == null)
            return -1;

        List<SlotItemHandler> slots = container.getModularItemToSlotMap().get(modularItemIndex);

        int slotIndex = -1;
        for (SlotItemHandler slotItemHandler: slots) {
            int index = slotItemHandler.getSlotIndex();

            if (slotItemHandler.getItemHandler() instanceof IModularItem) {
                if(slotItemHandler.getItemHandler().insertItem(index, module, true).isEmpty()) {
                    slotIndex = slotItemHandler.slotNumber;
                    break;
                }
            }
        }
        return slotIndex;
    }

    @Override
    public void init(double left, double top, double right, double bottom) {
        super.init(left, top, right, bottom);
    }

    @Override
    public void update(double x, double y) {
        super.update(x, y);

        double sizex = border.finalWidth();
        double sizey = border.finalHeight();

        this.installButton.setPosition(
                new MusePoint2D( border.left() + sizex / 2.0, border.bottom() - sizey / 2.0));
        this.salvageButton.setPosition(
                new MusePoint2D( border.left() + sizex / 2.0, border.bottom() - sizey / 2.0));

        if (targetItem.getSelectedItem() != null && targetModule.getSelectedModule() != null ) {
            ClickableModule selectedModule = targetModule.getSelectedModule();
            // fixme: show conflicts, but where... maybe in the summary frame
            // fixme: add cooldown timer for changes to this logic. Sometimes the grid shows for a frame or 2 when it shoudlnt
            if (selectedModule.isInstalled()) {
                this.salvageButton.enableAndShow();
                this.installButton.disableAndHide();
            } else if (player.abilities.isCreativeMode ||
                    player.inventory.hasItemStack(selectedModule.getModule())) {
                salvageButton.disableAndHide();
                installButton.enableAndShow();
            } else {
                salvageButton.disableAndHide();
                installButton.disableAndHide();
            }
        }
    }

    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
        ITextComponent ret = null;
        if (salvageButton.isVisible() && salvageButton.hitBox(x, y)) {
            ret = new TranslationTextComponent("gui.powersuits.salvage.desc");
        }
        if (installButton.isVisible() && installButton.hitBox(x, y)) {
            if (installButton.isEnabled() && player.abilities.isCreativeMode) {
                ret = new TranslationTextComponent("gui.powersuits.install.creative.desc");
            } else if (installButton.isEnabled()) {
                Collections.singletonList(new TranslationTextComponent("gui.powersuits.install.desc"));
            } else {
                // todo: tell user why disabled...
                ret = new TranslationTextComponent("gui.powersuits.install.disabled.desc");
            }
        }

        if (ret != null) {
            return MuseStringUtils.wrapITextComponentToLength(ret, 30);
        }
        return null;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        drawBackground(matrixStack,mouseX, mouseY, partialTicks);
        if (targetItem.getSelectedItem() != null && targetModule.getSelectedModule() != null) {
            drawButtons(matrixStack, mouseX, mouseY, partialTicks);
        } else {
            // TODO: message? click something or install/slavage
        }
    }

    private void drawBackground(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    private void drawButtons(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        salvageButton.render(matrixStack, mouseX, mouseY, partialTicks, zLevel);
        installButton.render(matrixStack, mouseX, mouseY, partialTicks, zLevel);
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (!border.containsPoint(x, y)) {
            return false;
        }
        ClickableItem selectedItem = targetItem.getSelectedItem();
        ClickableModule selectedModule = targetModule.getSelectedModule();
        AtomicBoolean handled = new AtomicBoolean(false);
        if (selectedItem != null && !selectedItem.getStack().isEmpty() && selectedModule != null && !selectedModule.getModule().isEmpty()) {
            selectedItem.getStack().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(cap -> {
                if (cap instanceof IModularItem) {
                    if (((IModularItem) cap).isModuleInstalled(selectedModule.getModule().getItem().getRegistryName())) {
                        if (salvageButton.mouseClicked(x, y, button)) {
                            handled.set(true);
                        }
                    } else {
                        if (installButton.mouseClicked(x, y, button)) {
                            handled.set(true);
                        }
                    }
                }
            });
        }
        return handled.get();
    }

    /**
     * Sets the guiLeft and guiTop offsets for the slot positions
     * @param ulOffset
     */
    public void setUlShift(MusePoint2D ulOffset) {

    }
}