package com.lehjr.numina.common.capabilities.render.modelspec;


import com.lehjr.numina.common.map.NuminaRegistry;
import com.lehjr.numina.common.math.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:44 AM, 4/28/13
 * <p>
 * Ported to Java by lehjr on 11/8/16.
 */
public abstract class SpecBase extends NuminaRegistry<PartSpecBase> {
    private final String name;
    private final boolean isDefault;
    private final SpecType specType;
    private List<Integer> colors = new ArrayList() {{
        add(Color.WHITE.getARGBInt());
    }};

    public SpecBase(final String name, final boolean isDefault, final SpecType specType) {
        this.name = name;
        this.isDefault = isDefault;
        this.specType = specType;
    }

    public abstract Component getDisaplayName();

    public Iterable<PartSpecBase> getPartSpecs() {
        return this.elems();
    }

    public Stream<PartSpecBase> getPartsAsStream() {
        return this.elemsAsStream();
    }

    public boolean hasArmorEquipmentSlot(EquipmentSlot slot) {
        return this.elemsAsStream().map(partSpecBase -> partSpecBase.getBinding().getSlot().equals(slot) && slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR).findFirst().isPresent();
    }


    /**
     * returns the parent spec id
     *
     * @return
     */
    public String getName() {
        return name;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public SpecType getSpecType() {
        return specType;
    }

    public List<Integer> getColors() {
        return colors;
    }

    /**
     * Only adds the color if it doesn't already exist.
     *
     * @param color
     * @return returns the index of the color
     */
    public int addColorIfNotExist(Color color) {
        int colorInt = color.getARGBInt();
        if (!colors.contains(colorInt)) {
            colors.add(colorInt);
            return colors.size() - 1; // index of last entry
        } else
            return colors.indexOf(colorInt);
    }

    /**
     * returns the short id of the model. Used for NBT tags
     * Implement on top level classes due to equals and hash checks will make this fail here
     *
     * @return
     */
    public abstract String getOwnName();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecBase specBase = (SpecBase) o;
        return Objects.equals(name, specBase.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isDefault, specType, colors);
    }
}
