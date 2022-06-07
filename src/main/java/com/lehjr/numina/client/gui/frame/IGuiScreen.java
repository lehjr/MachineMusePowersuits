package com.lehjr.numina.client.gui.frame;

/**
 * For the top level screen class
 */
public interface IGuiScreen extends IGuiFrame {

    @Override
    default void setEnabled(boolean enabled) {}

    @Override
    default boolean isEnabled() {
        return true;
    }

    @Override
    default void setVisible(boolean visible) {}

    @Override
    default boolean isVisible() {
        return true;
    }

    int getScreenWidth();

    int getScreenHeight();

    int getImageWidth();

    int getImageHeight();
}
