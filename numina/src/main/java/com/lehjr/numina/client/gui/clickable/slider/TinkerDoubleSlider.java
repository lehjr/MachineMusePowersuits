package com.lehjr.numina.client.gui.clickable.slider;

import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import net.minecraft.network.chat.Component;

public class TinkerDoubleSlider extends AbstractTinkerSlider {
    /**
     * Fixme: replace Compound tag with a getter? Maybe find a better workaround to send tag data to server and sync from there
     * OnMouseUpdate is the best location since value is changed with user input, other options would just create buffer overflow
     *
     * @param ul
     * @param width
     * @param id
     * @param label
     */
    public TinkerDoubleSlider(MusePoint2D ul, double width, String id, Component label) {
        super(ul, width, id, label);
    }

    @Override
    public void setValueByMouse(double value) {
        super.setValueByMouse(value);
        checkIfUpdated();
    }
}
