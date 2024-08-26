package com.lehjr.numina.client.gui.clickable.slider;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public interface ISlider {



    void setOnUpdated(IUpdated updated);

    void onUpdated();

    @OnlyIn(Dist.CLIENT)
    interface IUpdated {
        void onUpdated();
    }
}
