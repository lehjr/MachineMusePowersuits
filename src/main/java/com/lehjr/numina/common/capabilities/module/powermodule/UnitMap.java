package com.lehjr.numina.common.capabilities.module.powermodule;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import com.lehjr.numina.common.constants.TagConstants;

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
    static final Component EMPTY = new TextComponent("");

    protected static Map<String, Component> units = new HashMap<>();

    public void addUnitLabel(@Nonnull String propertyName, String unit) {
        if (unit != null && !unit.isEmpty()) {
            if (!units.containsKey(propertyName)) {
                if (unit.startsWith(TagConstants.MODULE_TRADEOFF_PREFIX)) {
                    units.put(propertyName, new TranslatableComponent(unit));
                } else {
                    units.put(propertyName, new TextComponent(unit));
                }
            }
        }
    }

    public Component getUnit(@Nonnull String propertyName) {
        return units.getOrDefault(propertyName, EMPTY);
    }
}