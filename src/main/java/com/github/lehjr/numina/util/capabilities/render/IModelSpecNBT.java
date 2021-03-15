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

package com.github.lehjr.numina.util.capabilities.render;

import com.github.lehjr.numina.basemod.MuseLogger;
import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.util.capabilities.render.modelspec.EnumSpecType;
import com.github.lehjr.numina.util.capabilities.render.modelspec.ModelRegistry;
import com.github.lehjr.numina.util.capabilities.render.modelspec.TexturePartSpec;
import com.github.lehjr.numina.util.math.Colour;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;
import java.util.List;

public interface IModelSpecNBT {
    @Nonnull
    ItemStack getItemStack();

    EnumSpecType getSpecType();

    CompoundNBT getRenderTag();

    CompoundNBT setRenderTag(CompoundNBT renderDataIn, String tagName);

    CompoundNBT getDefaultRenderTag();

    List<Integer> addNewColourstoList(List<Integer> colours, List<Integer> coloursToAdd);

    int[] getColorArray();

    int getNewColourIndex(List<Integer> colours, List<Integer> oldColours, Integer index);

    CompoundNBT setColorArray(int[] colors);

    default Colour getColorFromItemStack() {
        try {
            CompoundNBT renderTag = getRenderTag();
            if (renderTag.contains(NuminaConstants.NBT_TEXTURESPEC_TAG)) {
                TexturePartSpec partSpec = (TexturePartSpec) ModelRegistry.getInstance().getPart(renderTag.getCompound(NuminaConstants.NBT_TEXTURESPEC_TAG));
                CompoundNBT specTag = renderTag.getCompound(NuminaConstants.NBT_TEXTURESPEC_TAG);
                int index = partSpec.getColourIndex(specTag);
                int[] colours = getColorArray();
                if (colours.length > index)
                    return new Colour(colours[index]);
            }
        } catch (Exception e) {
            MuseLogger.logException("something failed here: ", e);
        }
        return Colour.WHITE;
    }
}