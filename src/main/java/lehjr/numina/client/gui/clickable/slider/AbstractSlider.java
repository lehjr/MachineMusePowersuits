package lehjr.numina.client.gui.clickable.slider;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import lehjr.numina.client.gui.clickable.Clickable;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.utils.MathUtils;
import net.minecraft.client.gui.GuiGraphics;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSlider extends Clickable {
    /** keep knob completely inside the track/slot/frame/whatever */
    public boolean keepKnobWithinBounds=true;
    /** useful for differentiating sliders */
    String id="";
    /** whether the slider is horizontal or vertically oriented */
    protected boolean isHorizontal;
    /** used for setting up tick marks and ticks (slider ticks not game cycle ticks) */
    protected double tickVal = 0;
    /** show tick lines? */
    protected boolean showTickLines = false;
    /** The value of this slider control. Based on a value representing 0 - 100% */
    protected double sliderValue = 0.5;
    /** minimum value when slider is at lowest setting */
    protected double minValue = 0.0;
    /** max value when slider is at highest setting */
    protected double maxValue = 1.0;

    /** Is this slider control being dragged.*/
    public boolean dragging = false;

    protected double knobSize = 8;

    private AbstractSlider() {
        super(MusePoint2D.ZERO, MusePoint2D.ZERO);
        this.isHorizontal = false;
    }

    public AbstractSlider(double left, double top, double right, double bottom, String id, boolean isHorizontal) {
        super(left, top, right, bottom, false);
        this. isHorizontal = isHorizontal;
        this.id = id;
        calculateTickCoordinates();
//        this.knobRect = createKnobRect();
    }

    public AbstractSlider(MusePoint2D ul, MusePoint2D br, String id, boolean isHorizontal) {
        super(ul, br, false);
        this. isHorizontal = isHorizontal;
        this.id = id;
        calculateTickCoordinates();
    }

    List<Double> calculateTickCoordinates() {
        List<Double> vals = new ArrayList<>();
        if (tickVal !=0) {
            for (double i = minValue + tickVal; i < maxValue; i += tickVal) {
                vals.add((this.isHorizontal ?
                        left() : top())
                        + this.getSize()  * (MathUtils.clampDouble((i - minValue) / (maxValue - minValue), 0.0, 1.0)));
            }
        }
        return vals;
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        if (this.isVisible() && this.isEnabled()) {
            this.renderBg(gfx, mouseX, mouseY, partialTick);
            if (showTickLines && tickVal != 0) {
                for (double val : calculateTickCoordinates()) {
                    drawSingleLine(gfx, val, top(), val, bottom(), Color.WHITE);
                }
            }
            this.renderKnob(gfx, mouseX, mouseY, partialTick);
        }
    }

    public abstract void renderBg(GuiGraphics gfx, int mouseX, int mouseY, float frameTime);

    public abstract void renderKnob(GuiGraphics gfx, int mouseX, int mouseY, float frameTime);

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            this.dragging = true;
            update(mouseX, mouseY);
            return true;
        }
        return false;
    }

    public void setKnobSize(double knobSize) {
        this.knobSize = knobSize;
    }

    public double getKnobSize() {
        return knobSize;
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (dragging) {
            update(mouseX, mouseY);
            this.dragging = false;
            return true;
        }
        return false;
    }

    public void setThickness(double thicknessIn) {
        if (isHorizontal) {
            this.setHeight(thicknessIn);
        } else {
            this.setWidth(thicknessIn);
        }
    }

    public double getThickness() {
        if (isHorizontal) {
            return height();
        } else {
            return width();
        }
    }

    public void setSize(double size) {
        if (isHorizontal) {
            setWidth(size);
        } else {
            setHeight(size);
        }
    }

    public double getSize() {
        double retVal = isHorizontal? width() : height();
        if (keepKnobWithinBounds) {
            return retVal - getKnobSize();
        }
        return retVal;
    }

    public String id() {
        return this.id;
    }

    public void update(double mouseX, double mouseY) {
//        if (dragging) {
            if (isHorizontal) {
                setValueByMouse(mouseX);
            } else {
                setValueByMouse(mouseY);
            }
//        }
        if (Double.isNaN(sliderValue)) {
            sliderValue = 0;
        }
    }

    public int getValueInt() {
        return (int) Math.round(sliderValue * (maxValue - minValue) + minValue);
    }

    public double getValue() {
        return sliderValue * (maxValue - minValue) + minValue;
    }

    public double getSliderInternalValue() {
        return sliderValue;
    }

    public void setValue(double value) {
        if (value != 0 && tickVal != 0) {
            value = value - (value % tickVal);
        }
        this.sliderValue = MathUtils.clampDouble((value - minValue) / (maxValue - minValue), 0.0, 1.0);
    }

    public void setValueByMouse(double value) {
        if (this.isEnabled() && this.isVisible() && dragging) {

            if (this.getClass().toString().contains("VanillaTinkerSlider")) {
                NuminaLogger.logDebug("setting by dragging " + this.id() + ", sliderValue: " +sliderValue +", " + this.getClass());
            }
            if (isHorizontal) {
                this.sliderValue = MathUtils.clampDouble((value - centerX()) / getSize() + 0.5, 0.0D, 1.0D);
            } else {
                this.sliderValue = MathUtils.clampDouble((value - centerY()) / getSize() + 0.5, 0, 1);
            }
        }
        else {
            if (this.getClass().toString().contains("VanillaTinkerSlider")) {
                NuminaLogger.logDebug("NOT setting by dragging " + this.id() + ", sliderValue: " +sliderValue +", " + this.getClass());
            }
            this.sliderValue = MathUtils.clampDouble(sliderValue, 0.0, 1.0);
        }
    }

    public double getMinVal() {
        return minValue;
    }

    public AbstractSlider setMinValue(double min) {
        this.minValue = min;
        return this;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public AbstractSlider setMaxValue(double max) {
        this.maxValue = max;
        return this;
    }

    void drawSingleLine(GuiGraphics gfx, double xStart, double yStart, double xEnd, double yEnd, Color color) {
        PoseStack poseStack = gfx.pose();
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder builder = tessellator.getBuilder();
        Matrix4f matrix4f = poseStack.last().pose();

        preDraw(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        RenderSystem.lineWidth(1);
        builder.vertex(matrix4f, (float) xStart, (float) yStart, getZLevel()).color(color.r, color.g, color.b, color.a).endVertex();
        builder.vertex(matrix4f, (float) xEnd, (float) yEnd, getZLevel()).color(color.r, color.g, color.b, color.a).endVertex();
        builder.end();
        postDraw(builder);
    }
}
