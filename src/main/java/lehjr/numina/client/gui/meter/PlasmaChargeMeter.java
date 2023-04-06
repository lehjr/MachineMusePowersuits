package lehjr.numina.client.gui.meter;

import lehjr.numina.client.config.IMeterConfig;

import java.util.concurrent.Callable;

/**
 * Created by leon on 4/9/17.
 */
public class PlasmaChargeMeter extends HeatMeter {
    public PlasmaChargeMeter(Callable<IMeterConfig> config) {
        super(config);
    }
}