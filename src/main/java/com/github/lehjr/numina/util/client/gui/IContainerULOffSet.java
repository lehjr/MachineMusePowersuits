package com.github.lehjr.numina.util.client.gui;

import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;

public interface IContainerULOffSet {
    void setULGetter(IContainerULOffSet.ulGetter ulGetter);

    MusePoint2D getULShift(IContainerULOffSet frame);

    interface ulGetter {
        MusePoint2D getULShift(IContainerULOffSet frame);
    }
}
