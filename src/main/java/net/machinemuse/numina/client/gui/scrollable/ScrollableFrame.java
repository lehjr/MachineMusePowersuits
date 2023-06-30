package net.machinemuse.numina.client.gui.scrollable;

import net.machinemuse.numina.client.gui.IDrawable;
import net.machinemuse.numina.client.gui.frame.IGuiFrame;
import net.machinemuse.numina.client.gui.geometry.IRect;
import net.machinemuse.numina.client.render.RenderState;
import net.machinemuse.numina.common.math.Colour;
import net.machinemuse.numina.common.math.MuseMathUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ScrollableFrame<T extends IRect> implements IGuiFrame {
    protected final int buttonsize = 5;
    protected int totalsize;
    protected int currentscrollpixels;
    protected int lastdWheel = Mouse.getDWheel();
    protected boolean visibile = true;
    protected boolean enabled = true;

    protected T border;

    public ScrollableFrame(T rect) {
        setRect(rect);
    }

    @Override
    public IRect getRect() {
        return border;
    }

    @Override
    public void setRect(IRect rect) {
        this.border = (T)rect;
    }

    protected double getScrollAmount() {
        return 8;
    }

    @Override
    public void update(double mouseX, double mouseY) {
        if (border.containsPoint(mouseX, mouseY)) {
            int dscroll = (lastdWheel - Mouse.getDWheel()) / 15;
            lastdWheel = Mouse.getDWheel();
            if (Mouse.isButtonDown(0)) {
                if ((mouseY - border.top()) < buttonsize && currentscrollpixels > 0) {
                    dscroll -= getScrollAmount();
                } else if ((border.bottom() - mouseY) < buttonsize) {
                    dscroll += getScrollAmount();
                }
            }
            currentscrollpixels = (int) MuseMathUtils.clampDouble(currentscrollpixels + dscroll, 0, getMaxScrollPixels());
        }
    }

    public void preDraw(double mouseX, double mouseY, float partialTicks) {
        if (border instanceof IDrawable) {
            ((IDrawable) border).render(mouseX, mouseY, partialTicks);
        }
        RenderState.glowOn();
        RenderState.texturelessOn();
        GL11.glBegin(GL11.GL_TRIANGLES);
        Colour.LIGHTBLUE.doGL();
        // Can scroll down
        if (currentscrollpixels + border.height() < totalsize) {
            GL11.glVertex3d(border.left() + border.width() / 2, border.bottom(), 1);
            GL11.glVertex3d(border.left() + border.width() / 2 + 2, border.bottom() - 4, 1);
            GL11.glVertex3d(border.left() + border.width() / 2 - 2, border.bottom() - 4, 1);
        }
        // Can scroll up
        if (currentscrollpixels > 0) {
            GL11.glVertex3d(border.left() + border.width() / 2, border.top(), 1);
            GL11.glVertex3d(border.left() + border.width() / 2 - 2, border.top() + 4, 1);
            GL11.glVertex3d(border.left() + border.width() / 2 + 2, border.top() + 4, 1);
        }
        Colour.WHITE.doGL();
        GL11.glEnd();
        RenderState.texturelessOff();
        RenderState.scissorsOn(border.left() + 4, border.top() + 4, border.width() - 8, border.height() - 8);
    }

    public void postDraw(double mouseX, double mouseY, float partialTicks) {
        RenderState.scissorsOff();
        RenderState.glowOff();
    }

    @Override
    public void render(double mouseX, double mouseY, float partialTicks) {
        preDraw(mouseX, mouseY, partialTicks);
        postDraw(mouseX, mouseY, partialTicks);
    }

    public void frameOff() {
        this.disable();
        this.hide();
    }

    public void frameOn() {
        this.enable();
        this.show();
    }

    public void hide () {
        this.visibile = false;
    }

    public void show() {
        this.visibile = true;
    }

    public boolean isVisibile() {
        return this.visibile;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public int getMaxScrollPixels() {
        return (int) Math.max(totalsize - border.height(), 0);
    }

    @Override
    public List<String> getToolTip(double mouseX, double mouseY) {
        return null;
    }
}
