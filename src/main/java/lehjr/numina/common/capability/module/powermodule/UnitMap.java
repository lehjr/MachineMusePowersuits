package lehjr.numina.common.capability.module.powermodule;

import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.network.chat.Component;

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
    protected Map<String, String> units = new HashMap<>();

    public static void addUnitLabel(@Nonnull String propertyName, String unit) {
        if (unit != null && !unit.isEmpty()) {
            if (!MAP.units.containsKey(propertyName)) {
                if (unit.startsWith(NuminaConstants.MODULE_TRADEOFF_PREFIX)) {
                    MAP.units.put(propertyName, Component.translatable(unit).getString());
                } else {
                   MAP.units.put(propertyName, unit);
                }
            }
        }
    }

    public static String getUnit(@Nonnull String propertyName) {
        return MAP.units.getOrDefault(propertyName, "");
    }
}