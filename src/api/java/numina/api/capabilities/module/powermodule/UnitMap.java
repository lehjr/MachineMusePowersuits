package numina.api.capabilities.module.powermodule;

import lehjr.numina.constants.NuminaConstants;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Just stores units of measure used for display purposes. It's just better storing them here since
 * capability objects are constantly created and destroyed. Putting the map here instead of in the capability
 * prevents glitching/flickering due to that recreation cycle not being in sync with the render loop.
 */
public enum UnitMap {
    MAP;

    protected static Map<String, String> units = new HashMap<>();

    public void addUnitLabel(@Nonnull String propertyName, String unit) {
        if (unit != null && !unit.isEmpty()) {
            if (!units.containsKey(propertyName)) {
                units.put(propertyName, unit);
            }
        }
    }

    public String getUnit(@Nonnull String propertyName) {
        String unit = units.get(propertyName);
        if (unit != null && unit.startsWith(NuminaConstants.MODULE_TRADEOFF_PREFIX)) {
            unit = I18n.get(unit);
        }
        return unit == null ? "" : unit;
    }
}