package com.lehjr.numina.client.gui;

import com.lehjr.numina.client.gui.geometry.MusePoint2D;

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