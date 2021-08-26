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
    public IRect setTop(double value) {
        super.setTop(value);
        setRect();
        return this;
    }

    @Override
    public IRect setLeft(double value) {
        super.setLeft(value);
        setRect();
        return this;
    }

    @Override
    public IRect setBottom(double value) {
        super.setBottom(value);
        setRect();
        return this;
    }

    @Override
    public IRect setRight(double value) {
        super.setRight(value);
        setRect();
        return this;
    }

    @Override
    public IRect setWidth(double value) {
        super.setWidth(value);
        setRect();
        return this;
    }

    @Override
    public IRect setHeight(double value) {
        super.setHeight(value);
        setRect();
        return this;
    }

    @Override
    public void move(MusePoint2D moveAmount) {
        super.move(moveAmount);
        setRect();
    }

    @Override
    public void move(double x, double y) {
        super.move(x, y);
        setRect();
    }

    @Override
    public void setPosition(MusePoint2D positionIn) {
        super.setPosition(positionIn);
        setRect();
    }

    @Override
    public IRect setMeLeftOf(IRect otherRightOfMe) {
        super.setMeLeftOf(otherRightOfMe);
        setRect();
        return this;
    }

    @Override
    public IRect setMeRightOf(IRect otherLeftOfMe) {
        super.setMeRightOf(otherLeftOfMe);
        setRect();
        return this;
    }

    @Override
    public IRect setMeAbove(IRect otherBelowMe) {
        super.setMeAbove(otherBelowMe);
        setRect();
        return this;
    }

    @Override
    public IRect setMeBelow(IRect otherAboveMe) {
        super.setMeBelow(otherAboveMe);
        setRect();
        return this;
    }

    @Override
    public void update(double mouseX, double mouseY) {
        if (rect instanceof IGuiFrame) {
            ((IGuiFrame) rect).update(mouseX, mouseY);
        }
        setRect(); // FIXME (eventually) workaround to reposition rects that don't land where they should. not ideal, but not a complex operation either
    }

    @Override
    public IRect setUL(MusePoint2D ul) {
        super.setUL(ul);
        setRect();
        return this;
    }

    @Override
    public IRect setWH(MusePoint2D wh) {
        super.setWH(wh);
        setRect();
        return this;
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

    public void setRect() {
        switch (placement) {
            case CENTER:
                rect.setPosition(super.center());
                break;

            case CENTER_LEFT:
                rect.setPosition(new MusePoint2D(super.finalLeft()  + 0.5 * rect.finalWidth(), super.centery()));
                break;

            case CENTER_RIGHT:
                rect.setPosition(new MusePoint2D(super.finalRight() - 0.5 * rect.finalWidth(), super.centery()));
                break;

            case CENTER_TOP:
                rect.setPosition(new MusePoint2D(super.centerx(), super.finalTop() + 0.5 * rect.finalHeight()));
                break;

            case CENTER_BOTTOM:
                rect.setPosition(new MusePoint2D(super.centerx(), super.finalBottom() - 0.5 * rect.finalHeight()));
                break;

            case UPPER_LEFT:
                rect.setPosition(new MusePoint2D(super.finalLeft() + 0.5 * rect.finalWidth(), super.finalTop() + 0.5 * rect.finalHeight()));
                break;

            case LOWER_LEFT:
                rect.setPosition(new MusePoint2D(super.finalLeft() + + 0.5 * rect.finalWidth(), super.finalBottom() - 0.5 * rect.finalHeight()));
                break;

            case UPPER_RIGHT:
                rect.setPosition(new MusePoint2D(super.finalRight() - 0.5 * rect.finalWidth(), super.finalTop() + 0.5 * rect.finalHeight()));
                break;
            case LOWER_RIGHT:
                rect.setPosition(new MusePoint2D(super.finalRight() - 0.5 * rect.finalWidth(), super.finalBottom() - 0.5 * rect.finalHeight()));
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
