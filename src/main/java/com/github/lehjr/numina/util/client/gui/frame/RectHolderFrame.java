package com.github.lehjr.numina.util.client.gui.frame;

import com.github.lehjr.numina.util.client.gui.gemoetry.IDrawable;
import com.github.lehjr.numina.util.client.gui.gemoetry.IRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
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
        this.setDoThisOnChange(iChange ->setRect());
        setRect();
    }

    public RectHolderFrame(T rect, double widthIn, double heightIn, RectPlacement placement) {
        this(rect, widthIn, heightIn, placement, null);
    }

    public RectHolderFrame(T rect, double widthIn, double heightIn, RectPlacement placement, IDoThis onChange) {
        super(widthIn, heightIn);
        this.rect = rect;
        this.setBackgroundColour(Colour.YELLOW);
        this.setBorderColour(Colour.RED);
        this.placement = placement;
        this.setDoThisOnChange(iChange -> {
            if (onChange != null) {
                onChange.doThisOnChange(this);
            }
            setRect();
        });
        setRect();
    }

    @Override
    public void initGrowth() {
        super.initGrowth();
        rect.initGrowth();
    }

    @Override
    public void update(double mouseX, double mouseY) {
        if (rect instanceof IGuiFrame) {
            ((IGuiFrame) rect).update(mouseX, mouseY);
        }
//        setRect(); // FIXME (eventually) workaround to reposition rects that don't land where they should. not ideal, but not a complex operation either
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
