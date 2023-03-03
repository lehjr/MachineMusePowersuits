package lehjr.numina.client.gui.meter;

import lehjr.numina.common.config.NuminaSettings;
import lehjr.numina.common.math.Color;

/**
 * Created by leon on 4/9/17.
 */
public class PlasmaChargeMeter extends HeatMeter {
    @Override
    public float getAlpha() {
        return NuminaSettings.getPlasmaMeterColor().a;
    }

    public Color getColour() {
        return NuminaSettings.getPlasmaMeterColor();
    }
}