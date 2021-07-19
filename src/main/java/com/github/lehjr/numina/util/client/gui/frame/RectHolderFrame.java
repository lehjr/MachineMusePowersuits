package com.github.lehjr.numina.util.client.gui.frame;

import com.github.lehjr.numina.util.client.gui.gemoetry.IRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

/**
 * a wrapper for frames and rectangles to aid in GUI design.
 */
public abstract class RectHolderFrame<T extends IRect> extends GUISpacer {
    T rect;
    public RectHolderFrame(T rect, double widthIn, double heightIn) {
        super(widthIn, heightIn);
        this.rect = rect;
    }

    @Override
    public RelativeRect setTop(double value) {
        super.setTop(value);
        rect.setPosition(this.center());
        return this;
    }

    @Override
    public RelativeRect setLeft(double value) {
        super.setLeft(value);
        rect.setPosition(this.center());
        return this;
    }

    @Override
    public RelativeRect setBottom(double value) {
        super.setBottom(value);
        rect.setPosition(this.center());
        return this;
    }

    @Override
    public RelativeRect setRight(double value) {
        super.setRight(value);
        rect.setPosition(this.center());
        return this;
    }

    @Override
    public void update(double mouseX, double mouseY) {
        if (rect instanceof IGuiFrame) {
            ((IGuiFrame) rect).update(mouseX, mouseY);
        }
    }

    @Override
    public abstract boolean mouseClicked(double mouseX, double mouseY, int button);

    @Override
    public abstract void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks);

    @Override
    public abstract boolean mouseReleased(double mouseX, double mouseY, int button);

    @Override
    public abstract List<ITextComponent> getToolTip(int x, int y);
}
