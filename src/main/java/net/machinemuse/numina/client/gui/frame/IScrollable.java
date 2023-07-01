package net.machinemuse.numina.client.gui.frame;

import net.machinemuse.numina.client.gui.IDrawable;
import net.machinemuse.numina.client.render.RenderState;
import net.machinemuse.numina.common.math.Colour;
import net.machinemuse.numina.common.math.MuseMathUtils;
import org.lwjgl.opengl.GL11;

public interface IScrollable extends IGuiFrame {
    void setTotalSize(int totalSize);

    int getButtonSize();

    void setButtonSize(int buttonSize);

    int getTotalSize();

    double getCurrentScrollPixels();

    void setCurrentScrollPixels(double scrollPixels);

    default double getMaxScrollPixels() {
        return Math.max(getTotalSize() - height(), 0);
    }

    default double getScrollAmount() {
        return 8D;
    }

    default void setScrollAmount(double scrollAmount) {
        setCurrentScrollPixels((int) MuseMathUtils.clampDouble(scrollAmount, 0, getMaxScrollPixels()));
    }

    @Override
    default boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        if (this.containsPoint(mouseX, mouseY)) {
            // prevent negative total scroll values
            setCurrentScrollPixels(MuseMathUtils.clampDouble(getCurrentScrollPixels() - dWheel * getScrollAmount(), 0, getMaxScrollPixels()));
            return true;
        }
        return false;
    }

    @Override
    default boolean mouseClicked(double x, double y, int button) {
        if (isVisible() && containsPoint(x, y) && button == 0) {
            double dscroll = 0;
            if (y - top() < getButtonSize() && this.getCurrentScrollPixels() > 0) {
                dscroll = (dscroll - this.getScrollAmount());
            } else if (bottom() - y < getButtonSize()) {
                dscroll = (dscroll + this.getScrollAmount());
            }
            if (dscroll != 0) {
                setCurrentScrollPixels(MuseMathUtils.clampDouble(this.getCurrentScrollPixels() + dscroll, 0.0D, this.getMaxScrollPixels()));
            }
            return true;
        }
        return false;
    }

    default void preRender(double mouseX, double mouseY, float partialTicks) {
        if (getRect() instanceof IDrawable) {
            ((IDrawable) getRect()).render(mouseX, mouseY, partialTicks);
        }
        RenderState.glowOn();
        RenderState.texturelessOn();
        GL11.glBegin(GL11.GL_TRIANGLES);
        Colour.LIGHTBLUE.doGL();
        setCurrentScrollPixels(Math.min(getCurrentScrollPixels(), getMaxScrollPixels()));

        // Can scroll down
        if (getCurrentScrollPixels() + height() < getTotalSize()) {
            GL11.glVertex3d(left() + width() / 2, bottom(), 1);
            GL11.glVertex3d(left() + width() / 2 + 2, bottom() - 4, 1);
            GL11.glVertex3d(left() + width() / 2 - 2, bottom() - 4, 1);
        }
        // Can scroll up
        if (getCurrentScrollPixels() > 0) {
            GL11.glVertex3d(left() + width() / 2, top(), 1);
            GL11.glVertex3d(left() + width() / 2 - 2, top() + 4, 1);
            GL11.glVertex3d(left() + width() / 2 + 2, top() + 4, 1);
        }
        Colour.WHITE.doGL();
        GL11.glEnd();
        RenderState.texturelessOff();
        RenderState.scissorsOn(left() + 4, top() + 4, width() - 8, height() - 8);
    }

    default void postRender(double mouseX, double mouseY, float partialTicks) {
        if (isVisible()) {
            RenderState.scissorsOff();
            RenderState.glowOff();
        }
    }
}
