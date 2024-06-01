//package lehjr.numina.client.gui.clickable;
//
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.BufferBuilder;
//import com.mojang.blaze3d.vertex.DefaultVertexFormat;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.Tesselator;
//import org.joml.Matrix4f;
//import lehjr.numina.client.gui.geometry.DrawableTile;
//import lehjr.numina.client.gui.geometry.IDrawable;
//import lehjr.numina.client.gui.geometry.MusePoint2D;
//import lehjr.numina.client.gui.geometry.Rect;
//import lehjr.numina.common.math.Color;
//import lehjr.numina.common.math.MathUtils;
//import lehjr.numina.common.string.StringUtils;
//import net.minecraft.network.chat.Component;
//import org.lwjgl.opengl.GL11;
//
//import javax.annotation.Nullable;
//import java.util.ArrayList;
//import java.util.List;
//
//// TODO: ticks and tickmarks
//@Deprecated
//public class RangedSlider extends Clickable {
//    /** useful for differentiating sliders */
//    String id="";
//    /** whether the slider is horizontal or vertically oriented */
//    protected boolean isHorizontal = true;
//    /** used for setting up tick marks and ticks (slider ticks not game cycle ticks) */
//    protected double tickVal = 0;
//    /** whether or not to show tick lines */
//    protected boolean showTickLines = false;
//
//    /** The value of this slider control. Based on a value representing 0 - 100%*/
//    public double sliderValue = 1.0F;
//    /** minimum value when slider is at lowest setting */
//    public double minValue = 0.0;
//    /** max value when slider is at highest setting */
//    public double maxValue = 5.0;
//    /** z offset for rendering. AKA blitOffset*/
//    float zLevel = 0;
//    /** height of the slider. Unchanged in horizontal mode */
//    protected final double thickness = 8;
//    /** width of the slider. Unchanged in vertical mode */
//    protected double size = 16;
//    /** just a label for the slider */
//    private Component label;
//    /** the "knob" of the slider */
//    DrawableTile knobRect;
//    /** the "track" of the slider */
//    DrawableTile trackRect;
//
//    /** Is this slider control being dragged.*/
//    public boolean dragging = false;
//
//    @Nullable
//    public ISlider parent = null;
//
//    public RangedSlider(MusePoint2D position,
//                        boolean isHorizontal,
//                        double size,
//                        Component label,
//                        String id,
//                        double minVal,
//                        double maxVal,
//                        double currentVal) {
//        this(position, isHorizontal, size, label, id, minVal, maxVal, currentVal, null);
//    }
//
//    public RangedSlider(MusePoint2D position,
//                        boolean isHorizontal,
//                        double size,
//                        Component label,
//                        String id,
//                        double minVal,
//                        double maxVal,
//                        double currentVal,
//                        @Nullable ISlider iSlider) {
//        super(MusePoint2D.ZERO, MusePoint2D.ZERO);
//        this.isHorizontal = isHorizontal;
//        this.size = size;
//        this.label = label;
//        this.id = id;
//        minValue = minVal;
//        maxValue = maxVal;
//        parent = iSlider;
//        setValue(currentVal);
//        this.setPosition(position);
//        calculateTickCoordinates();
////        setDoThisOnChange(doThis-> createNewRects());
//    }
//
//    public String id() {
//        return id;
//    }
//
//    public void setTickVal(double tickVal) {
//        this.tickVal = Math.abs(tickVal);
//        calculateTickCoordinates();
//    }
//
//    public void setShowTickLines(boolean showTickLines) {
//        this.showTickLines = showTickLines;
//    }
//
//    List<Double> calculateTickCoordinates() {
//        List<Double> vals = new ArrayList<>();
//        if (tickVal !=0) {
//            for (double i = minValue + tickVal; i < maxValue; i += tickVal) {
//                vals.add((this.isHorizontal ?
//                        trackRect.left() : trackRect.top())
//                        + this.size  * (MathUtils.clampDouble((i - minValue) / (maxValue - minValue), 0.0, 1.0)));
//            }
//        }
//        return vals;
//    }
//
//    void createNewRects() {
//        // Mostly OK... knob position is way off
//        if (isHorizontal) {
//
//            // FIXME
//            this.knobRect = new DrawableTile(
//                    centerX() - 4,
//                    centerY() - 1 - thickness * 0.5,
//                    centerX() + 4,
//                    centerY() + 1 + thickness * 0.5);
//
//            // FIXME
//            this.trackRect = new DrawableTile(
//                    centerX() - size * 0.5,
//                    centerY() - thickness * 0.5,
//                    centerX() + size * 0.5,
//                    centerY() + thickness * 0.5);
//
//            // both Knob and track are wrong
//        } else {
//            // should put it right in the center
//            this.knobRect = new DrawableTile(
//                    centerX() - 1 - thickness * 0.5,
//                    centerY() - 4,
//                    centerX() + 1 + thickness * 0.5,
//                    centerY() + 4);
//
//
//            this.trackRect = new DrawableTile(
//                    centerX() - thickness * 0.5,
//                    centerY() - size * 0.5F,
//                    centerX() + thickness * 0.5,
//                    centerY() + size * 0.5);
//        }
//
//        this.knobRect.setBackgroundColor(Color.LIGHT_GREY);
//        this.knobRect.setBottomBorderColor(Color.BLACK);
//        this.knobRect.setTopBorderColor(Color.WHITE);
//
//        this.trackRect.setBackgroundColor(Color.DARK_GREY);
//        this.trackRect.setBottomBorderColor(Color.WHITE);
//        this.trackRect.setTopBorderColor(Color.BLACK);
//        super.setWH(new MusePoint2D(trackRect.width(), trackRect.height()));
//    }
//
//    @Override
//    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
//        if (this.isVisible()) {
//            if (label != null) {
//                StringUtils.drawShadowedStringCentered(matrixStack, label.getString(), centerX(), centerY());
//            }
//
//            this.trackRect.render(matrixStack, mouseX, mouseY, partialTick);
//            if (isHorizontal) {
//                this.knobRect.setPosition(new MusePoint2D(
//                        this.centerX() + this.size * (this.sliderValue - 0.5),
//                        this.trackRect.centerY()));
//            } else {
//                this.knobRect.setPosition(
//                        new MusePoint2D(this.trackRect.centerX(),
//                                this.centerY() +  this.size * (this.sliderValue - 0.5)));
//            }
//
//            if (showTickLines && tickVal != 0) {
//                if (isHorizontal) {
//                    for (double val : calculateTickCoordinates()) {
//                        drawSingleLine(matrixStack, val, trackRect.top(), val, trackRect.bottom(), Color.WHITE);
//                    }
//                } else {
//                    for (double val : calculateTickCoordinates()) {
//                        drawSingleLine(matrixStack, trackRect.left(), val, trackRect.right(), val, Color.WHITE);
//                    }
//                }
//            }
//            this.knobRect.render(matrixStack, mouseX, mouseY, partialTick);
//        }
//    }
//
//    @Override
//    public float getZLevel() {
//        return zLevel;
//    }
//
//    @Override
//    public IDrawable setZLevel(float zLevel) {
//        knobRect.setZLevel(zLevel);
//        trackRect.setZLevel(zLevel);
//        this.zLevel = zLevel;
//        return this;
//    }
//
//    public void update(double mouseX, double mouseY) {
//        double siderStart = this.sliderValue;
//        if (this.isEnabled() && this.isVisible() && dragging) {
//            if (isHorizontal) {
//                this.sliderValue = MathUtils.clampDouble((mouseX - this.centerX()) / (this.size - knobRect.width() * 0.5) + 0.5, 0.0, 1.0);
//            } else {
//                this.sliderValue = MathUtils.clampDouble((mouseY - this.centerY()) / (this.size - knobRect.height() * 0.5) + 0.5, 0.0, 1.0);
//            }
//        } else {
//            this.sliderValue = MathUtils.clampDouble(sliderValue, 0.0, 1.0);
//        }
//        // updates the value
//        setValue(getValue());
//
//        if (siderStart != sliderValue && parent != null) {
//            parent.onChangeSliderValue(this);
//        }
//    }
//
//    public void setLabel(Component label) {
//        this.label = label;
//    }
//
//    public int getValueInt() {
//        return (int) Math.round(sliderValue * (maxValue - minValue) + minValue);
//    }
//
//    public double getValue() {
//        return sliderValue * (maxValue - minValue) + minValue;
//    }
//
//    public void setValue(double value) {
//        if (value != 0 && tickVal != 0) {
//            value = value - (value % tickVal);
//        }
//
//        this.sliderValue = MathUtils.clampDouble((value - minValue) / (maxValue - minValue), 0.0, 1.0);
//    }
//
//    /**
//     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
//     */
//    @Override
//    public boolean mouseReleased(double mouseX, double mouseY, int button) {
//        // no need to use hitbox for release
//        if (this.dragging) {
//            update(mouseX, mouseY);
//            this.dragging = false;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean mouseClicked(double mouseX, double mouseY, int button) {
//        if (this.isEnabled() && this.isVisible() && this.containsPoint(mouseX, mouseY)) {
//            update(mouseX, mouseY);
//            this.dragging = true;
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean containsPoint(double x, double y) {
//        return x > trackRect.left() - 2 && x < trackRect.right() + 2 && y > trackRect.top() - 2 && y < trackRect.bottom() + 2;
//    }
//
//    public interface ISlider {
//        void onChangeSliderValue(RangedSlider slider);
//    }
//
//    @Override
//    public void setPosition(MusePoint2D position) {
//        super.setPosition(position);
//        createNewRects();
//    }
//
//    @Override
//    public Rect setWH(MusePoint2D wh) {
//        createNewRects();
//        return (Rect) getRect();
//    }
//
//    @Override
//    public Rect setWidth(double value) {
//        createNewRects();
//        return (Rect) getRect();
//    }
//
//    @Override
//    public Rect setHeight(double value) {
//        createNewRects();
//        return (Rect) getRect();
//    }
//
//    void drawSingleLine(PoseStack matrixStack, double xStart, double yStart, double xEnd, double yEnd, Color color) {
//        Tesselator tessellator = Tesselator.getInstance();
//        BufferBuilder builder = tessellator.getBuilder();
//        Matrix4f matrix4f = matrixStack.last().pose();
//
//        preDraw(GL11.GL_LINES, DefaultVertexFormat.POSITION_COLOR);
//        RenderSystem.lineWidth(1);
//        builder.vertex(matrix4f, (float) xStart, (float) yStart, getZLevel()).color(color.r, color.g, color.b, color.a).endVertex();
//        builder.vertex(matrix4f, (float) xEnd, (float) yEnd, getZLevel()).color(color.r, color.g, color.b, color.a).endVertex();
//        drawTesselator();
//        postDraw();
//    }
//}