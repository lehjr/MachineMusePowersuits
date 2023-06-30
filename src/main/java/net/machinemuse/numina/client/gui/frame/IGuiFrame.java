package net.machinemuse.numina.client.gui.frame;

import net.machinemuse.numina.client.gui.geometry.IRectWrapper;

import java.util.List;

public interface IGuiFrame extends IRectWrapper {
    /**
     * @param mouseX
     * @param mouseY
     * @param button
     * @return true if mouse click is inside this frame and is handled here, else false
     */
    default boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    /**
     * @param mouseX
     * @param mouseY
     * @param button
     * @return true if mouse release is inside this frame and is handled here, else false
     */
    default boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    /**
     * @param mouseX
     * @param mouseY
     * @param dWheel
     * @return true if mouse pointer is inside this frame and scroll is handled here, else false
     */
    default boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        return false;
    }

    /**
     * Miscellaneous functions required before rendering
     * @param mouseX
     * @param mouseY
     */
    default void update(double mouseX, double mouseY) {

    }

    /**
     * Render elements of this frame. Ordering is important.
     *
     * @param mouseX
     * @param mouseY
     * @param partialTick
     */
    default void render(double mouseX, double mouseY, float partialTick) {
    }

    /**
     * @param mouseX mouseX
     * @param mouseY mouseY
     * @return tooltip or null if not returning tooltip;
     */
    List<String> getToolTip(double mouseX, double mouseY);
}