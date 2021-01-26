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

package com.github.lehjr.numina.util.capabilities.render.modelspec;


import com.github.lehjr.numina.util.map.MuseRegistry;
import com.github.lehjr.numina.util.math.Colour;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:44 AM, 4/28/13
 * <p>
 * Ported to Java by lehjr on 11/8/16.
 */
public abstract class SpecBase extends MuseRegistry<PartSpecBase> {
    private final String name;
    private final boolean isDefault;
    private final EnumSpecType specType;
    private List<Integer> colours = new ArrayList() {{
        add(Colour.WHITE.getInt());
    }};

    public SpecBase(final String name, final boolean isDefault, final EnumSpecType specType) {
        this.name = name;
        this.isDefault = isDefault;
        this.specType = specType;
    }

    public abstract String getDisaplayName();

    public Iterable<PartSpecBase> getPartSpecs() {
        return this.elems();
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

    public EnumSpecType getSpecType() {
        return specType;
    }

    public List<Integer> getColours() {
        return colours;
    }

    /**
     * Only adds the colour if it doesn't already exist.
     *
     * @param colour
     * @return returns the index of the colour
     */
    public int addColourIfNotExist(Colour colour) {
        int colourInt = colour.getInt();
        if (!colours.contains(colourInt)) {
            colours.add(colourInt);
            return colours.size() - 1; // index of last entry
        } else
            return colours.indexOf(colourInt);
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
        return isDefault == specBase.isDefault &&
                Objects.equals(name, specBase.name) &&
                specType == specBase.specType &&
                Objects.equals(colours, specBase.colours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isDefault, specType, colours);
    }
}