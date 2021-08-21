package com.github.lehjr.powersuits.dev.crafting.client.gui.recipebooktest;

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

    @Override
    public List<ITextComponent> getToolTip(int i, int i1) {
        return null;
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
    public IRect setUL(MusePoint2D ul) {
        getRect().setUL(ul);
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
        return getRect();
    }

    @Override
    public IRect setWH(MusePoint2D wh) {
        getRect().setWH(wh);
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
        super.setWidth((int) finalWidth());
        super.setHeight((int) finalHeight());
        return getRect();
    }

    @Override
    public IRect setLeft(double value) {
        getRect().setLeft(value);
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
        return getRect();
    }

    @Override
    public IRect setRight(double value) {
        getRect().setRight(value);
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
        return getRect();
    }

    @Override
    public IRect setTop(double value) {
        getRect().setTop(value);
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
        return getRect();
    }

    @Override
    public IRect setBottom(double value) {
        getRect().setBottom(value);
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
        return getRect();
    }

    @Override
    public IRect setWidth(double value) {
        getRect().setWidth(value);
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
        super.setWidth((int) value);
        return getRect();
    }

    @Override
    public IRect setHeight(double value) {
        getRect().setHeight(value);
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
        super.setHeight((int) value);
        return getRect();
    }

    @Override
    public void move(MusePoint2D moveAmount) {
        getRect().move(moveAmount);
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
    }

    @Override
    public void move(double x, double y) {
        getRect().move(x, y);
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
    }

    @Override
    public void setPosition(MusePoint2D position) {
        getRect().setPosition(position);
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
    }

    @Override
    public IRect setMeLeftOf(IRect otherRightOfMe) {
        getRect().setMeLeftOf(otherRightOfMe);
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
        return getRect();
    }

    @Override
    public IRect setMeRightOf(IRect otherLeftOfMe) {
        getRect().setMeRightOf(otherLeftOfMe);
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
        return getRect();
    }

    @Override
    public IRect setMeAbove(IRect otherBelowMe) {
        getRect().setMeAbove(otherBelowMe);
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
        return getRect();
    }

    @Override
    public IRect setMeBelow(IRect otherAboveMe) {
        getRect().setMeBelow(otherAboveMe);
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
        return getRect();
    }
}