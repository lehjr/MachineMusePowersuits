package com.github.lehjr.numina.util.client.gui.clickable;

import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import com.github.lehjr.numina.util.client.gui.gemoetry.IDrawable;
import com.github.lehjr.numina.util.client.gui.gemoetry.IRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.math.MuseMathUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

// TODO: ticks and tickmarks
public class RangedSlider extends Clickable {
    /** useful for differentiating sliders */
    String id="";
    /** whether the slider is horizontal or vertically oriented */
    protected boolean isHorizontal = true;
    /** used for setting up tick marks and ticks (slider ticks not game cycle ticks) */
    protected double tickVal = 0;
    /** whether or not to show tick lines */
    protected boolean showTickLines = false;

    /** The value of this slider control. Based on a value representing 0 - 100%*/
    public double sliderValue = 1.0F;
    /** minimum value when slider is at lowest setting */
    public double minValue = 0.0;
    /** max value when slider is at highest setting */
    public double maxValue = 5.0;
    /** z offset for rendering. AKA blitOffset*/
    float zLevel = 0;
    /** height of the slider. Unchanged in horizontal mode */
    protected final double thickness = 8;
    /** width of the slider. Unchanged in vertical mode */
    protected double size = 16;
    /** just a label for the slider */
    private ITextComponent label;
    /** the "knob" of the slider */
    DrawableTile knobRect;
    /** the "track" of the slider */
    DrawableTile trackRect;

    /** Is this slider control being dragged.*/
    public boolean dragging = false;

    @Nullable
    public ISlider parent = null;

    public RangedSlider(MusePoint2D position,
                        boolean isHorizontal,
                        double size,
                        ITextComponent label,
                        String id,
                        double minVal,
                        double maxVal,
                        double currentVal) {
        this(position, isHorizontal, size, label, id, minVal, maxVal, currentVal, null);
    }

    public RangedSlider(MusePoint2D position,
                        boolean isHorizontal,
                        double size,
                        ITextComponent label,
                        String id,
                        double minVal,
                        double maxVal,
                        double currentVal,
                        @Nullable ISlider iSlider) {
        this.isHorizontal = isHorizontal;
        this.size = size;
        this.label = label;
        this.id = id;
        minValue = minVal;
        maxValue = maxVal;
        parent = iSlider;
        setValue(currentVal);
        this.setPosition(position);
        calculateTickCoordinates();
        setDoThisOnChange(doThis-> createNewRects());
    }

    public String id() {
        return id;
    }

    public void setTickVal(double tickVal) {
        this.tickVal = Math.abs(tickVal);
        calculateTickCoordinates();
    }

    public void setShowTickLines(boolean showTickLines) {
        this.showTickLines = showTickLines;
    }

    List<Double> calculateTickCoordinates() {
        List<Double> vals = new ArrayList<>();
        if (tickVal !=0) {
            for (double i = minValue + tickVal; i < maxValue; i += tickVal) {
                vals.add((this.isHorizontal ?
                        trackRect.finalLeft() : trackRect.finalTop())
                        + this.size  * (MuseMathUtils.clampDouble((i - minValue) / (maxValue - minValue), 0.0, 1.0)));
            }
        }
        return vals;
    }

    void createNewRects() {
        // Mostly OK... knob position is way off
        if (isHorizontal) {

            // FIXME
            this.knobRect = new DrawableTile(
                    getPosition().getX() - 4,
                    getPosition().getY() - 1 - thickness * 0.5,
                    getPosition().getX() + 4,
                    getPosition().getY() + 1 + thickness * 0.5);

            // FIXME
            this.trackRect = new DrawableTile(
                    getPosition().getX() - size * 0.5,
                    getPosition().getY() - thickness * 0.5,
                    getPosition().getX() + size * 0.5,
                    getPosition().getY() + thickness * 0.5);

            // both Knob and track are wrong
        } else {
            // should put it right in the center
            this.knobRect = new DrawableTile(
                    getPosition().getX() - 1 - thickness * 0.5,
                    getPosition().getY() - 4,
                    getPosition().getX() + 1 + thickness * 0.5,
                    getPosition().getY() + 4);


            this.trackRect = new DrawableTile(
                    getPosition().getX() - thickness * 0.5,
                    getPosition().getY() - size * 0.5F,
                    getPosition().getX() + thickness * 0.5,
                    getPosition().getY() + size * 0.5);
        }

        this.knobRect.setBackgroundColour(Colour.LIGHT_GREY);
        this.knobRect.setBottomBorderColour(Colour.BLACK);
        this.knobRect.setTopBorderColour(Colour.WHITE);

        this.trackRect.setBackgroundColour(Colour.DARK_GREY);
        this.trackRect.setBottomBorderColour(Colour.WHITE);
        this.trackRect.setTopBorderColour(Colour.BLACK);
        super.setWH(new MusePoint2D(trackRect.finalWidth(), trackRect.finalHeight()));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        if (this.isVisible()) {
            if (label != null) {
                MuseRenderer.drawShadowedStringCentered(matrixStack, label.getString(), getPosition().getX(), getPosition().getY());
            }

            this.trackRect.render(matrixStack, mouseX, mouseY, frameTime);
            if (isHorizontal) {
                this.knobRect.setPosition(new MusePoint2D(
                        this.getPosition().getX() + this.size * (this.sliderValue - 0.5),
                        this.trackRect.centery()));
            } else {
                this.knobRect.setPosition(
                        new MusePoint2D(this.trackRect.centerx(),
                                this.getPosition().getY() +  this.size * (this.sliderValue - 0.5)));
            }

            if (showTickLines && tickVal != 0) {
                if (isHorizontal) {
                    for (double val : calculateTickCoordinates()) {
                        drawSingleLine(matrixStack, val, trackRect.finalTop(), val, trackRect.finalBottom(), Colour.WHITE);
                    }
                } else {
                    for (double val : calculateTickCoordinates()) {
                        drawSingleLine(matrixStack, trackRect.finalLeft(), val, trackRect.finalRight(), val, Colour.WHITE);
                    }
                }
            }
            this.knobRect.render(matrixStack, mouseX, mouseY, frameTime);
        }
    }

    @Override
    public float getZLevel() {
        return zLevel;
    }

    @Override
    public IDrawable setZLevel(float zLevel) {
        knobRect.setZLevel(zLevel);
        trackRect.setZLevel(zLevel);
        this.zLevel = zLevel;
        return this;
    }

    public void update(double mouseX, double mouseY) {
        double siderStart = this.sliderValue;
        if (this.isEnabled() && this.isVisible() && dragging) {
            if (isHorizontal) {
                this.sliderValue = MuseMathUtils.clampDouble((mouseX - this.getPosition().getX()) / (this.size - knobRect.finalWidth() * 0.5) + 0.5, 0.0, 1.0);
            } else {
                this.sliderValue = MuseMathUtils.clampDouble((mouseY - this.getPosition().getY()) / (this.size - knobRect.finalHeight() * 0.5) + 0.5, 0.0, 1.0);
            }
        } else {
            this.sliderValue = MuseMathUtils.clampDouble(sliderValue, 0.0, 1.0);
        }
        // updates the value
        setValue(getValue());

        if (siderStart != sliderValue && parent != null) {
            parent.onChangeSliderValue(this);
        }
    }

    public void setLabel(ITextComponent label) {
        this.label = label;
    }

    public int getValueInt() {
        return (int) Math.round(sliderValue * (maxValue - minValue) + minValue);
    }

    public double getValue() {
        return sliderValue * (maxValue - minValue) + minValue;
    }

    public void setValue(double value) {
        if (value != 0 && tickVal != 0) {
            value = value - (value % tickVal);
        }

        this.sliderValue = MuseMathUtils.clampDouble((value - minValue) / (maxValue - minValue), 0.0, 1.0);
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        // no need to use hitbox for release
        if (this.dragging) {
            update(mouseX, mouseY);
            this.dragging = false;
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isEnabled() && this.isVisible() && this.hitBox(mouseX, mouseY)) {
            update(mouseX, mouseY);
            this.dragging = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean hitBox(double x, double y) {
        return x > trackRect.left() - 2 && x < trackRect.right() + 2 && y > trackRect.top() - 2 && y < trackRect.bottom() + 2;
    }

    public interface ISlider {
        void onChangeSliderValue(RangedSlider slider);
    }

    @Override
    public void setPosition(MusePoint2D position) {
        super.setPosition(position);
        createNewRects();
    }

    @Override
    public IRect setWH(MusePoint2D wh) {
        createNewRects();
        return this;
    }

    @Override
    public IRect setWidth(double value) {
        createNewRects();
        return this;
    }

    @Override
    public IRect setHeight(double value) {
        createNewRects();
        return this;
    }

    void drawSingleLine(MatrixStack matrixStack, double xStart, double yStart, double xEnd, double yEnd, Colour colour) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuilder();
        Matrix4f matrix4f = matrixStack.last().pose();

        preDraw(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        RenderSystem.lineWidth(1);
        builder.vertex(matrix4f, (float) xStart, (float) yStart, getZLevel()).color(colour.r, colour.g, colour.b, colour.a).endVertex();
        builder.vertex(matrix4f, (float) xEnd, (float) yEnd, getZLevel()).color(colour.r, colour.g, colour.b, colour.a).endVertex();
        drawTesselator();
        postDraw();
    }
}