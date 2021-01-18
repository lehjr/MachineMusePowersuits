package com.github.lehjr.numina.util.client.gui.slot;

import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;

public interface IHideableSlot {
    void enable();

    void disable();

    boolean isEnabled();

    void setPosition(MusePoint2D position);
}