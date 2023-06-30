package net.machinemuse.numina.client.gui;

public interface IDrawable {
    /**
     * @param mouseX
     * @param mouseY
     * @param partialTick
     */
    default void render(double mouseX, double mouseY, float partialTick) {
    }
}
