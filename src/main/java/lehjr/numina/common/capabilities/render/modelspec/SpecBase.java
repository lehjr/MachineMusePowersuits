/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.numina.common.capabilities.render.modelspec;


import lehjr.numina.common.map.NuminaRegistry;
import lehjr.numina.common.math.Color;
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
        return this.elemsAsStream().map(partSpecBase -> partSpecBase.getBinding().getSlot().equals(slot) && slot.getType() == EquipmentSlot.Type.ARMOR).findFirst().isPresent();
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