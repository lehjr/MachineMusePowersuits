package com.github.lehjr.numina.util.client.gui.frame;

import com.github.lehjr.numina.util.client.gui.gemoetry.IDrawable;
import com.github.lehjr.numina.util.client.gui.gemoetry.IRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

/**
 * a wrapper for frames and rectangles to aid in GUI design.
 */
public abstract class RectHolderFrame<T extends IRect> extends GUISpacer {
    T rect;
    RectPlacement placement;
    public RectHolderFrame(T rect, double widthIn, double heightIn) {
        this(rect, widthIn, heightIn, RectPlacement.CENTER);
    }

    public RectHolderFrame(T rect, double widthIn, double heightIn, RectPlacement placement) {
        super(widthIn, heightIn);
        this.rect = rect;
        this.setBackgroundColour(Colour.YELLOW);
        this.setBorderColour(Colour.RED);
        this.placement = placement;
        setRect();
    }

    @Override
    public void initGrowth() {
        super.initGrowth();
        rect.initGrowth();
        setRect();
    }

    @Override
    public RelativeRect setTop(double value) {
        super.setTop(value);
        setRect();
        return this;
    }

    @Override
    public RelativeRect setLeft(double value) {
        super.setLeft(value);
        setRect();
        return this;
    }

    @Override
    public RelativeRect setBottom(double value) {
        super.setBottom(value);
        setRect();
        return this;
    }

    @Override
    public RelativeRect setRight(double value) {
        super.setRight(value);
        setRect();
        return this;
    }

    @Override
    public RelativeRect setWidth(double value) {
        setRect();
        return super.setWidth(value);
    }

    @Override
    public RelativeRect setHeight(double value) {
        setRect();
        return super.setHeight(value);
    }

    @Override
    public void move(MusePoint2D moveAmount) {
        setRect();
        super.move(moveAmount);
    }

    @Override
    public void move(double x, double y) {
        setRect();
        super.move(x, y);
    }

    @Override
    public void setPosition(MusePoint2D positionIn) {
        setRect();
        super.setPosition(positionIn);
    }

    @Override
    public RelativeRect setMeLeftOf(RelativeRect otherRightOfMe) {
        setRect();
        return super.setMeLeftOf(otherRightOfMe);
    }

    @Override
    public RelativeRect setMeRightOf(RelativeRect otherLeftOfMe) {
        setRect();
        return super.setMeRightOf(otherLeftOfMe);
    }

    @Override
    public RelativeRect setMeAbove(RelativeRect otherBelowMe) {
        setRect();
        return super.setMeAbove(otherBelowMe);
    }

    @Override
    public RelativeRect setMeBelow(RelativeRect otherAboveMe) {
        setRect();
        return super.setMeBelow(otherAboveMe);
    }

    @Override
    public void update(double mouseX, double mouseY) {
        if (rect instanceof IGuiFrame) {
            ((IGuiFrame) rect).update(mouseX, mouseY);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        super.render(matrixStack, mouseX, mouseY, frameTime);
        if (rect instanceof IDrawable) {
            ((IDrawable) rect).render(matrixStack, mouseX, mouseY, frameTime);
        }
    }

    @Override
    public abstract boolean mouseClicked(double mouseX, double mouseY, int button);

    @Override
    public abstract boolean mouseReleased(double mouseX, double mouseY, int button);

    @Override
    public abstract List<ITextComponent> getToolTip(int x, int y);

    void setRect() {
        switch (placement) {
            case CENTER:
                rect.setPosition(this.center());
                break;

            case CENTER_LEFT:
                rect.setPosition(new MusePoint2D(this.finalLeft(), this.centery()));
                break;

            case CENTER_RIGHT:
                rect.setPosition(new MusePoint2D(this.finalRight(), this.centery()));
                break;

            case CENTER_TOP:
                rect.setPosition(new MusePoint2D(this.centerx(), this.finalTop()));
                break;

            case CENTER_BOTTOM:
                rect.setPosition(new MusePoint2D(this.centerx(), this.finalBottom()));
                break;

            case UPPER_LEFT:
                rect.setPosition(new MusePoint2D(this.finalLeft(), this.finalTop()));
                break;

            case LOWER_LEFT:
                rect.setPosition(new MusePoint2D(this.finalLeft(), this.finalBottom()));
                break;

            case UPPER_RIGHT:
                rect.setPosition(new MusePoint2D(this.finalRight(), this.finalTop()));
                break;
            case LOWER_RIGHT:
                rect.setPosition(new MusePoint2D(this.finalRight(), this.finalBottom()));
                break;
        }
    }

    public enum RectPlacement {
        CENTER,
        CENTER_LEFT,
        CENTER_RIGHT,
        CENTER_TOP,
        CENTER_BOTTOM,
        UPPER_LEFT,
        LOWER_LEFT,
        UPPER_RIGHT,
        LOWER_RIGHT
    }
}
