package com.github.lehjr.powersuits.client.gui.modding.module.craft_install_salvage;

import com.github.lehjr.numina.util.client.gui.frame.IGuiFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.IDrawable;
import com.github.lehjr.numina.util.client.gui.gemoetry.IRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Just a TextFieldWidget wrapped in an IGuiFrame for better positioning control
 */
public class RectTextFieldWidget extends TextFieldWidget implements IGuiFrame {
    RelativeRect rect;
    public RectTextFieldWidget(FontRenderer fontRenderer, int left, int top, int width, int height, ITextComponent message) {
        this(fontRenderer, left, top, width, height, (TextFieldWidget)null, message);
    }

    public RectTextFieldWidget(FontRenderer fontRenderer, int left, int top, int width, int height, @Nullable TextFieldWidget p_i232259_6_, ITextComponent message) {
        super(fontRenderer, left, top, width, height, p_i232259_6_, message);
        rect = new RelativeRect();
        rect.setHeight(width);
        rect.setHeight(height);
        rect.setLeft(left);
        rect.setTop(top);
        rect.setDoThisOnChange(change->updateWidgetPos());
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        return super.mouseScrolled(mouseX, mouseY, dWheel);
    }

    @Override
    public IRect getRect() {
        return rect;
    }

    @Override
    public void update(double mouseX, double mouseY) {

    }

    void updateWidgetPos() {
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
        super.width = (int) rect.finalWidth();
        super.height = (int) rect.finalHeight();
    }

    @Override
    public List<ITextComponent> getToolTip(int i, int i1) {
        return null;
    }

    @Override
    public IRect setUL(MusePoint2D ul) {
        return getRect().setUL(ul);
    }

    @Override
    public IRect setWH(MusePoint2D wh) {
        return getRect().setWH(wh);
    }

    @Override
    public IRect setLeft(double value) {
        return getRect().setLeft(value);
    }

    @Override
    public IRect setRight(double value) {
        return getRect().setRight(value);
    }

    @Override
    public IRect setTop(double value) {
        return getRect().setTop(value);
    }

    @Override
    public IRect setBottom(double value) {
        return getRect().setBottom(value);
    }

    @Override
    public IRect setWidth(double value) {
        return getRect().setWidth(value);
    }

    @Override
    public IRect setHeight(double value) {
        return getRect().setHeight(value);
    }

    @Override
    public void move(MusePoint2D moveAmount) {
        getRect().move(moveAmount);
    }

    @Override
    public void move(double x, double y) {
        getRect().move(x, y);
    }

    @Override
    public void setPosition(MusePoint2D position) {
        getRect().setPosition(position);
    }

    @Override
    public IRect setMeLeftOf(IRect otherRightOfMe) {
        return getRect().setMeLeftOf(otherRightOfMe);
    }

    @Override
    public IRect setMeRightOf(IRect otherLeftOfMe) {
        return getRect().setMeRightOf(otherLeftOfMe);
    }

    @Override
    public IRect setMeAbove(IRect otherBelowMe) {
        return getRect().setMeAbove(otherBelowMe);
    }

    @Override
    public IRect setMeBelow(IRect otherAboveMe) {
        return getRect().setMeBelow(otherAboveMe);
    }

    @Override
    public void initGrowth() {
        rect.initGrowth();
    }

    @Override
    public boolean growFromMiddle() {
        return rect.growFromMiddle();
    }

    @Override
    public double finalWidth() {
        return rect.finalWidth();
    }

    @Override
    public double height() {
        return rect.height();
    }

    @Override
    public double finalHeight() {
        return rect.finalHeight();
    }

    @Override
    public double bottom() {
        return rect.bottom();
    }

    @Override
    public double finalBottom() {
        return rect.finalBottom();
    }

    @Override
    public double width() {
        return rect.width();
    }

    @Override
    public void setEnabled(boolean b) {

    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setOnInit(IInit iInit) {
        rect.setOnInit(iInit);
    }

    @Override
    public void onInit() {
        rect.onInit();
    }

    @Override
    public void doThisOnChange() {

    }

    @Override
    public void setDoThisOnChange(IDoThis iDoThis) {

    }

    @Override
    public float getZLevel() {
        return 0;
    }

    @Override
    public IDrawable setZLevel(float v) {
        return this;
    }

    @Override
    public MusePoint2D getUL() {
        return rect.getUL();
    }

    @Override
    public MusePoint2D getWH() {
        return rect.getWH();
    }

    @Override
    public double left() {
        return rect.left();
    }

    @Override
    public double finalLeft() {
        return rect.finalLeft();
    }

    @Override
    public double top() {
        return rect.top();
    }

    @Override
    public double finalTop() {
        return rect.finalTop();
    }

    @Override
    public double right() {
        return rect.right();
    }

    @Override
    public double finalRight() {
        return rect.finalRight();
    }
}