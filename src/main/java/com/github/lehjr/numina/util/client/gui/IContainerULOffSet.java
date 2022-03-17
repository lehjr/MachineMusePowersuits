package com.github.lehjr.numina.util.client.gui;

import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;

/**
 * Stupid workaround for the container screen shifting everything before rendering
 */
public interface IContainerULOffSet {
    void setULGetter(IContainerULOffSet.ulGetter ulGetter);

    MusePoint2D getULShift();

    interface ulGetter {
        MusePoint2D getULShift();
    }
}