/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.powersuits.client.gui.keybind;

import com.github.lehjr.numina.client.control.KeyBindingHelper;
import com.github.lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableButton;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableModule;
import com.github.lehjr.numina.util.client.gui.clickable.IClickable;
import com.github.lehjr.numina.util.client.gui.frame.IGuiFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.GradientAndArcCalculator;
import com.github.lehjr.numina.util.client.gui.gemoetry.IRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.MuseRect;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.client.control.KeybindKeyHandler;
import com.github.lehjr.powersuits.client.control.KeybindManager;
import com.github.lehjr.powersuits.client.gui.clickable.ClickableKeybinding;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import java.util.*;

public class KeybindConfigFrame implements IGuiFrame {
    private static KeyBindingHelper keyBindingHelper = new KeyBindingHelper();
    protected Set<ClickableModule> modules;
    protected IClickable selectedClickie;
    protected ClickableKeybinding closestKeybind;
    protected PlayerEntity player;
    protected boolean selecting;
    protected ClickableButton newKeybindButton;
    protected ClickableButton trashKeybindButton;
    protected long takenTime;
    KeybindManager keybindManager = KeybindManager.INSTANCE;

    MuseRect rect;

    public KeybindConfigFrame(MuseRect backgroundRect, PlayerEntity player) {
        modules = new HashSet();
        for (ClickableKeybinding kb : keybindManager.getKeybindings()) {
            modules.addAll(kb.getBoundModules());
        }
        this.rect = backgroundRect;
        this.player = player;
        MusePoint2D center = rect.center();
        newKeybindButton = new ClickableButton(new TranslationTextComponent("gui.powersuits.newKeybind"), center.plus(new MusePoint2D(0, -8)), true);
        trashKeybindButton = new ClickableButton(new TranslationTextComponent("gui.powersuits.trashKeybind"), center.plus(new MusePoint2D(0, 8)), true);
    }

    @Override
    public void init(double left, double top, double right, double bottom) {
        newKeybindButton.setPosition(rect.center().plus(new MusePoint2D(0, -8)));
        trashKeybindButton.setPosition(rect.center().plus(new MusePoint2D(0, 8)));
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (this.rect.containsPoint(x, y)) {
            if (button == 0) {
                if (selectedClickie == null) {
                    for (ClickableModule module : modules) {
                        if (module.hitBox(x, y)) {
                            selectedClickie = module;
                            return true;
                        }
                    }
                    for (ClickableKeybinding keybind : keybindManager.getKeybindings()) {
                        if (keybind.hitBox(x, y)) {
                            selectedClickie = keybind;
                            return true;
                        }
                    }
                }
                if (newKeybindButton.hitBox(x, y)) {
                    selecting = true;
                }
            } else if (button == 1) {
                for (ClickableKeybinding keybind : keybindManager.getKeybindings()) {
                    if (keybind.hitBox(x, y)) {
                        keybind.toggleHUDState();
                        return true;
                    }
                }
            } else if (button > 2) {
                int key = button - 100;

                if (keyBindingHelper.keyBindingHasKey(key)) {
                    takenTime = System.currentTimeMillis();
                }
                if (!keyBindingHelper.keyBindingHasKey(key)) {
                    addKeybind(key, true);
                } else if (MPSSettings.allowConfictingKeyBinds()) {
                    addKeybind(key, false);
                }
                selecting = false;
            }
            return true;
        }
        return false;
    }

    public void refreshModules() {
        NonNullList<ItemStack> installedModules = NonNullList.create();

        for (EquipmentSlotType slot: EquipmentSlotType.values()) {
            switch (slot.getSlotType()) {
                case HAND:
                    player.getItemStackFromSlot(slot).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(
                            iModeChanging -> {
                                if (iModeChanging instanceof IModeChangingItem)
                                    installedModules.addAll(((IModularItem) iModeChanging).getInstalledModulesOfType(IToggleableModule.class));
                            });
                    break;

                case ARMOR:
                    if (slot.getSlotType() == EquipmentSlotType.Group.ARMOR) {
                        player.getItemStackFromSlot(slot).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(
                                iModularItem -> {
                                    if (iModularItem instanceof IModularItem)
                                        installedModules.addAll(((IModularItem) iModularItem).getInstalledModulesOfType(IToggleableModule.class));
                                });
                    }
            }
        }

        List<MusePoint2D> points = GradientAndArcCalculator.pointsInLine(
                installedModules.size(),
                new MusePoint2D(rect.finalLeft() + 10, rect.finalTop() + 10),
                new MusePoint2D(rect.finalLeft() + 10, rect.finalBottom() - 10));
        Iterator<MusePoint2D> pointIterator = points.iterator();
        for (ItemStack module : installedModules) {
            if (!alreadyAdded(module)) {
                ClickableModule clickie = new ClickableModule(module, pointIterator.next(), -1, EnumModuleCategory.NONE);
                modules.add(clickie);
            }
        }
    }

    public boolean alreadyAdded(@Nonnull ItemStack module) {
        if (module.isEmpty())
            return false;

        for (ClickableModule clickie : modules) {
            if (ItemStack.areItemsEqual(clickie.getModule(),module)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double x, double y, int button) {
        if (button == 0) {
            if (selectedClickie != null && closestKeybind != null && selectedClickie instanceof ClickableModule) {
                closestKeybind.bindModule((ClickableModule) selectedClickie);
            } else if (selectedClickie != null && selectedClickie instanceof ClickableKeybinding && trashKeybindButton.hitBox((float) x, (float) y)) {
                KeyBinding binding = ((ClickableKeybinding) selectedClickie).getKeyBinding();
                keyBindingHelper.removeKey(binding);
//                KeyBinding.HASH.removeObject(binding.getKeyCode());
                keyBindingHelper.removeKey(binding);
                keybindManager.remove((ClickableKeybinding) selectedClickie);
            }
            selectedClickie = null;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        return false;
    }

    @Override
    public void update(double mousex, double mousey) {
        if (selecting) {
            return;
        }
        refreshModules();
        this.closestKeybind = null;
        double closestDistance = Double.MAX_VALUE;
        if (this.selectedClickie != null) {
            this.selectedClickie.setPosition(new MusePoint2D(mousex, mousey));
            if (this.selectedClickie instanceof ClickableModule) {
                ClickableModule selectedModule = ((ClickableModule) this.selectedClickie);
                for (ClickableKeybinding keybind : keybindManager.getKeybindings()) {
                    double distance = keybind.getPosition().minus(selectedModule.getPosition()).distance();
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        if (closestDistance < 32) {
                            this.closestKeybind = keybind;
                        }
                    }
                }
            }
        }
        for (ClickableKeybinding keybind : keybindManager.getKeybindings()) {
            if (keybind != selectedClickie) {
                keybind.unbindFarModules();
            }
            keybind.attractBoundModules(selectedClickie);
        }
        for (IClickable module : modules) {
            if (module != selectedClickie) {
                repelOtherModules(module);
            }
        }
        for (IClickable keybind : keybindManager.getKeybindings()) {
            if (keybind != selectedClickie) {
                repelOtherModules(keybind);
            }
        }
        for (IClickable module : modules) {
            clampClickiePosition(module);
        }
        for (IClickable keybind : keybindManager.getKeybindings()) {
            clampClickiePosition(keybind);
        }
    }

    private void clampClickiePosition(IClickable clickie) {
        MusePoint2D position = clickie.getPosition();
        position.setX(clampDouble(position.getX(), rect.finalLeft(), rect.finalRight()));
        position.setY(clampDouble(position.getY(), rect.finalTop(), rect.finalBottom()));
    }

    private double clampDouble(double x, double lower, double upper) {
        if (x < lower) {
            return lower;
        } else if (x > upper) {
            return upper;
        } else {
            return x;
        }
    }

    private void repelOtherModules(IClickable module) {
        MusePoint2D modulePosition = module.getPosition();
        for (ClickableModule otherModule : modules) {
            if (otherModule != selectedClickie && otherModule != module && otherModule.getPosition().distanceTo(modulePosition) < 16) {
                MusePoint2D euclideanDistance = otherModule.getPosition().minus(module.getPosition());
                MusePoint2D directionVector = euclideanDistance.normalize();
                MusePoint2D tangentTarget = directionVector.times(16).plus(module.getPosition());
                MusePoint2D midpointTangent = otherModule.getPosition().midpoint(tangentTarget);
                if (midpointTangent.distanceTo(module.getPosition()) > 2) {
                    otherModule.setPosition(midpointTangent.copy());
                }
                 MusePoint2D away = directionVector.times(0).plus(modulePosition);
                 module.setPosition(away.copy());
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // FIXME!!
        float zLevel = Minecraft.getInstance().currentScreen.getBlitOffset();

        MusePoint2D center = rect.center();

        if (selecting) {
            MuseRenderer.drawCenteredString(matrixStack, I18n.format("gui.powersuits.pressKey"), center.getX(), center.getY());
            return;
        }
        newKeybindButton.render(matrixStack, mouseX, mouseY, partialTicks, zLevel);
        trashKeybindButton.render(matrixStack, mouseX, mouseY, partialTicks, zLevel);

        MuseRenderer.drawCenteredString(matrixStack, I18n.format("gui.powersuits.keybindInstructions1"), center.getX(), center.getY() + 40);
        MuseRenderer.drawCenteredString(matrixStack, I18n.format("gui.powersuits.keybindInstructions2"), center.getX(), center.getY() + 50);
        MuseRenderer.drawCenteredString(matrixStack, I18n.format("gui.powersuits.keybindInstructions3"), center.getX(), center.getY() + 60);
        MuseRenderer.drawCenteredString(matrixStack, I18n.format("gui.powersuits.keybindInstructions4"), center.getX(), center.getY() + 70);
        if (takenTime + 1000 > System.currentTimeMillis()) {
            MusePoint2D pos = newKeybindButton.getPosition().plus(new MusePoint2D(0, -20));
            MuseRenderer.drawCenteredString(matrixStack, I18n.format("gui.powersuits.keybindTaken"), pos.getX(), pos.getY());
        }
        for (ClickableModule module : modules) {
            module.render(matrixStack, mouseX, mouseY, partialTicks, zLevel);
        }
        for (ClickableKeybinding keybind : keybindManager.getKeybindings()) {
            keybind.render(matrixStack, mouseX, mouseY, partialTicks, zLevel);
        }
        if (selectedClickie != null && closestKeybind != null) {
            MuseRenderer.drawLineBetween(selectedClickie, closestKeybind, Colour.YELLOW, zLevel);
        }
    }

    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
        for (ClickableModule module : modules) {
            if (module.hitBox(x, y)) {
                if (doAdditionalInfo()) {
                    return module.getToolTip();
                }
                return Collections.singletonList(module.getLocalizedName());
            }
        }
        return null;
    }

    public static boolean doAdditionalInfo() {
        return false; //InputMappings.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT);
    }

    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {

        int key = p_keyPressed_1_; // no idea which one to use here!!

        if (selecting) {
//                if (KeyBinding.HASH.containsItem(key)) {
            if (keyBindingHelper.keyBindingHasKey(key)) {
                takenTime = System.currentTimeMillis();
            }
//                if (!KeyBinding.HASH.containsItem(key)) {
            if (!keyBindingHelper.keyBindingHasKey(key)) {
                addKeybind(key, true);
            } else if (MPSSettings.allowConfictingKeyBinds()) {
                addKeybind(key, false);
            }
            selecting = false;
        }

        return true; // no idea what to return here!!!
    }

    private void addKeybind(int key, boolean free) {
        addKeybind(KeyBindingHelper.getInputByCode(key), free);
    }

    private void addKeybind(InputMappings.Input key, boolean free) {
        String name;
        try {
            name = key.getTranslationKey();
        } catch (Exception e) {
            name = "???";
        }
        KeyBinding keybind = new KeyBinding(name, key.getKeyCode(), KeybindKeyHandler.mpa);
        ClickableKeybinding clickie = new ClickableKeybinding(keybind, newKeybindButton.getPosition().plus(new MusePoint2D(0, -20)), free, false);
        keybindManager.getKeybindings().add(clickie);
    }

    @Override
    public IRect getBorder() {
        return rect;
    }

    boolean enabled = true;
    @Override
    public void setEnabled(boolean b) {
        enabled = b;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    boolean visible = true;
    @Override
    public void setVisible(boolean b) {
        visible = b;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public IRect setLeft(double v) {
        return rect.setLeft(v);
    }

    @Override
    public IRect setRight(double v) {
        return rect.setRight(v);
    }

    @Override
    public IRect setTop(double v) {
        return rect.setTop(v);
    }

    @Override
    public IRect setBottom(double v) {
        return rect.setBottom(v);
    }

    @Override
    public IRect setWidth(double v) {
        return rect.setWidth(v);
    }

    @Override
    public IRect setHeight(double v) {
        return rect.setHeight(v);
    }
}
