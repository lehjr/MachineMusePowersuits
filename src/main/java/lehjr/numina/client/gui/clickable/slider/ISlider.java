package lehjr.numina.client.gui.clickable.slider;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ISlider {
    void setOnUpdated(IUpdated updated);

    void onUpdated();

    @OnlyIn(Dist.CLIENT)
    interface IUpdated {
        void onUpdated();
    }
}
