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

package lehjr.numina.client.gui.slot;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.clickable.IClickable;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.client.render.NuminaRenderer;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.common.math.Colour;
import lehjr.numina.common.string.StringUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper for container slots with Item modules in them.
 * Note the 2 constructors:
 *      one for use as a module in the player's inventory
 *      the other is for use as a module in an ItemHandler Capability
 */
public class ClickableModuleSlot extends UniversalSlot implements IClickable {
    protected IPressable onPressed;
    protected IReleasable onReleased;
    boolean isVisible = true;
    boolean isEnabled = true;

    final Colour checkmarkcolour = new Colour(0.0F, 0.667F, 0.0F, 1.0F);
    boolean allowed = true;
    boolean installed = false;

    public ClickableModuleSlot(IInventory inventory, int index, int xPosition, int yPosition) {
        this(inventory, index, new MusePoint2D(xPosition, yPosition));
    }

    public ClickableModuleSlot(IInventory inventory, int index, MusePoint2D position) {
        super(inventory, index, (int)position.getX(), (int)position.getY());
    }

    public ClickableModuleSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        this(itemHandler, index, new MusePoint2D(xPosition, yPosition));
    }

    public ClickableModuleSlot(IItemHandler itemHandler, int index, MusePoint2D position) {
        super(itemHandler, index, (int)position.getX(), (int)position.getY());
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!getItem().isEmpty()) {
            NuminaRenderer.drawItemAt(getUL().getX(), getUL().getY(), getItem());
            if (!allowed) {
                String string = StringUtils.wrapFormatTags("x", StringUtils.FormatCodes.DarkRed);
                StringUtils.drawShadowedString(matrixStack, string, getPosition().getX() + 3, getPosition().getY() + 1);
            } else if (installed) {
                IconUtils.getIcon().checkmark.draw(matrixStack, getUL().getX() + 1, getUL().getY() + 1, checkmarkcolour);
            }
        }
    }

    @Override
    public void doThisOnChange() {

    }

    @Override
    public void setDoThisOnChange(IDoThis iDoThis) {

    }

    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
        if (hitBox(x, y)) {
            List<ITextComponent> toolTipText = new ArrayList<>();
            toolTipText.add(getLocalizedName());
            toolTipText.addAll(StringUtils.wrapITextComponentToLength(getLocalizedDescription(), 30));
            return toolTipText;
        }
        return null;
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled() && this.isEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    @Override
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void onReleased() {
        if (this.isVisible && this.isEnabled && this.onReleased != null) {
            this.onReleased.onReleased(this);
        }
    }

    @Override
    public void onPressed() {
        if (this.isVisible && this.isEnabled && this.onPressed != null) {
            this.onPressed.onPressed(this);
        }
    }

    public ITextComponent getLocalizedName() {
        if (this.getItem().isEmpty())
            return null;
        return this.getItem().getDisplayName();
    }

    public ITextComponent getLocalizedDescription() {
        if (this.getItem().isEmpty())
            return null;
        return new TranslationTextComponent(this.getItem().getItem().getDescriptionId().concat(".desc"));
    }

    public ModuleCategory getCategory() {
        return getItem().getCapability(PowerModuleCapability.POWER_MODULE).map(m->m.getCategory()).orElse(ModuleCategory.NONE);
    }

    public boolean equals(ClickableModuleSlot other) {
        return this.getItem().sameItem(other.getItem());
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public boolean isAllowed() {
        return this.allowed;
    }

    public void setInstalled(boolean installed) {
        this.installed = installed;
    }

    public boolean isInstalled() {
        return installed;
    }

    @Override
    public void setOnPressed(IPressable onPressed) {
        this.onPressed = onPressed;
    }

    @Override
    public void setOnReleased(IReleasable onReleased) {
        this.onReleased = onReleased;
    }


}