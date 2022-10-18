package lehjr.numina.client.gui.clickable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.gemoetry.DrawableTile;
import lehjr.numina.client.gui.gemoetry.IRect;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.common.math.Colour;
import lehjr.numina.common.math.MathUtils;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Slider extends DrawableTile implements IClickable {
    IPressable onPressed;
    IReleasable onReleased;

    protected boolean isVisible = true;

    protected boolean isEnabled = true;

    private boolean isCreatingNewRects = false;

    /** useful for differentiating sliders */
    String id="";
    /** whether the slider is horizontal or vertically oriented */
    protected boolean isHorizontal;
    /** used for setting up tick marks and ticks (slider ticks not game cycle ticks) */
    protected double tickVal = 0;
    /** whether or not to show tick lines */
    protected boolean showTickLines = false;
    /** The value of this slider control. Based on a value representing 0 - 100%*/
    public double internalVal = 0;
    /** z offset for rendering. AKA blitOffset*/
    protected final double thickness = 8;
    /** width of the slider. Unchanged in vertical mode */
    protected double size = 16;
    /** the "knob" of the slider */
    DrawableTile knobRect;
    /** Is this slider control being dragged.*/
    public boolean dragging = false;
    @Nullable
    public ISlider parent = null;

    public Slider(MusePoint2D position, double size, String id) {
        this(position, true, size, id, 0);
    }

    public Slider(MusePoint2D position,
                  boolean isHorizontal,
                  double size,
                  String id,
                  double currentVal) {
        this(position, isHorizontal, size, id, currentVal, null);
    }

    public Slider(MusePoint2D position,
                  boolean isHorizontal,
                  double size,
                  String id,
                  double currentVal,
                  @Nullable ISlider iSlider) {
        super(1,1,1,1);
        this.isHorizontal = isHorizontal;
        this.size = size;
        this.id = id;
        parent = iSlider;
        setValue(currentVal);
        this.setPosition(position);
        this.createNewRects();
        calculateTickCoordinates();
    }

    public String id() {
        return this.id;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        if (this.isVisible()) {
            super.render(matrixStack, mouseX, mouseY, frameTime);
            if (isHorizontal) {
                this.knobRect.setPosition(new MusePoint2D(this.centerx() + this.size * (this.internalVal - 0.5), this.centery()));
            } else {
                this.knobRect.setPosition(new MusePoint2D(this.centerx(), this.centery() +  this.size * (this.internalVal - 0.5)));
            }

            if (showTickLines && tickVal != 0) {
                if (isHorizontal) {
                    for (double val : calculateTickCoordinates()) {
                        drawSingleLine(matrixStack, val, top(), val, bottom(), Colour.WHITE);
                    }
                } else {
                    for (double val : calculateTickCoordinates()) {
                        drawSingleLine(matrixStack, left(), val, right(), val, Colour.WHITE);
                    }
                }
            }
            this.knobRect.render(matrixStack, mouseX, mouseY, frameTime);
        }
    }

    public void update(double mouseX, double mouseY) {
        double siderStart = this.internalVal;
        if (this.isEnabled() && this.isVisible() && dragging) {
            if (isHorizontal) {
                this.internalVal = MathUtils.clampDouble((mouseX - centerx()) / (this.size - knobRect.finalWidth() * 0.5) + 0.5, 0.0, 1.0);
            } else {
                this.internalVal = MathUtils.clampDouble((mouseY - centery()) / (this.size - knobRect.finalHeight() * 0.5) + 0.5, 0.0, 1.0);
            }
        } else {
            this.internalVal = MathUtils.clampDouble(internalVal, 0.0, 1.0);
        }
        // updates the value in classes that extend this
        setValue(getValue());

        if (siderStart != internalVal && parent != null) {
            parent.onChangeSliderValue(this);
        }
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (IClickable.super.mouseClicked(mouseX, mouseY, button)) {
            this.dragging = true;
            update(mouseX, mouseY);
            return true;
        }
        return false;
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

    public double getValue() {
        return internalVal;
    }

    public double getInternalVal() {
        return internalVal;
    }

    public void setValue(double value) {
        internalVal = MathUtils.clampDouble(value, 0, 1);
    }

    public void setValueByX(double value) {
        if (isHorizontal) {
            this.internalVal = MathUtils.clampDouble((value - centerx()) / this.width() + 0.5D, 0.0D, 1.0D);
        } else {
            internalVal = MathUtils.clampDouble((value - centery()) / size + 0.5F, 0, 1);
        }
    }

    void createNewRects() {
        isCreatingNewRects = true;
        if (isHorizontal) {
            super.setHeight(thickness);
            super.setWidth(size);

            this.knobRect = new DrawableTile(
                    centerx() - 4,
                    centery() - 1 - thickness * 0.5,
                    centerx() + 4,
                    centery() + 1 + thickness * 0.5);
        } else {
            super.setHeight(size);
            super.setWidth(thickness);

            // should put it right in the center
            this.knobRect = new DrawableTile(
                    centerx() - 1 - thickness * 0.5,
                    centery() - 4,
                    centerx() + 1 + thickness * 0.5,
                    centery() + 4);
        }

        this.knobRect.setBackgroundColour(Colour.LIGHT_GREY);
        this.knobRect.setBottomBorderColour(Colour.BLACK);
        this.knobRect.setTopBorderColour(Colour.WHITE);

        this.setBackgroundColour(Colour.DARK_GREY);
        this.setBottomBorderColour(Colour.WHITE);
        this.setTopBorderColour(Colour.BLACK);
        isCreatingNewRects = false;
    }

    List<Double> calculateTickCoordinates() {
        List<Double> vals = new ArrayList<>();
        if (tickVal !=0) {
            for (double i = getMinVal() + tickVal; i < getMaxValue(); i += tickVal) {
                vals.add((this.isHorizontal ?
                        finalLeft() : finalTop())
                        + this.size  * (MathUtils.clampDouble((i - getMinVal()) / (getMaxValue() - getMinVal()), 0.0, 1.0)));
            }
        }
        return vals;
    }

    public double getMinVal() {
        return 0;
    }

    public double getMaxValue() {
        return 1;
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

    @Override
    public IRect setWH(MusePoint2D wh) {
        setHeight(wh.getY());
        setWidth(wh.getX());
        return this;
    }

    @Override
    public IRect setWidth(double value) {
        if (isHorizontal) {
            super.setWidth(value);
        }
        return this;
    }

    @Override
    public IRect setHeight(double value) {
        if(!isHorizontal) {
            super.setHeight(value);
        }
        return this;
    }

    @Override
    public boolean containsPoint(double x, double y) {
        return x > left() - 2 && x < right() + 2 && y > top() - 2 && y < bottom() + 2;
    }

    @Override
    public void setEnabled(boolean b) {
        this.isEnabled = b;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setVisible(boolean b) {
        this.isVisible = b;
    }

    @Override
    public boolean isVisible() {
        return this.isVisible;
    }

    @Override
    public void doThisOnChange() {
        if (!isCreatingNewRects) {
            createNewRects();
        }
        super.doThisOnChange();
    }

    @Override
    public void setOnPressed(IPressable onPressed) {
        this.onPressed = onPressed;
    }

    @Override
    public void setOnReleased(IReleasable onReleased) {
        this.onReleased = onReleased;
    }

    @Override
    public void onPressed() {
        if (this.isVisible() && this.isEnabled() && this.onPressed != null) {
            this.onPressed.onPressed(this);
        }
    }

    @Override
    public void onReleased() {
        if (this.isVisible() && this.isEnabled() && this.onReleased != null) {
            this.onReleased.onReleased(this);
        }
    }

    public interface ISlider {
        void onChangeSliderValue(Slider slider);
    }
}