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

package lehjr.numina.common.capabilities.render;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.render.modelspec.ModelRegistry;
import lehjr.numina.common.capabilities.render.modelspec.SpecType;
import lehjr.numina.common.capabilities.render.modelspec.TexturePartSpec;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.math.Color;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public interface IModelSpec {
    @Nonnull
    ItemStack getItemStack();

    SpecType getSpecType();

    CompoundTag getRenderTag();

    CompoundTag setRenderTag(CompoundTag renderDataIn, String tagName);

    CompoundTag getDefaultRenderTag();

    List<Integer> addNewColourstoList(List<Integer> colours, List<Integer> coloursToAdd);

    int[] getColorArray();

    int getNewColourIndex(List<Integer> colours, List<Integer> oldColours, Integer index);

    CompoundTag setColorArray(int[] colors);

    default Color getColorFromItemStack() {
        try {
            CompoundTag renderTag = getRenderTag();
            if (renderTag == null || renderTag.isEmpty()) {
                return Color.WHITE;
            }
            if (renderTag.contains(TagConstants.TEXTURESPEC)) {
                TexturePartSpec partSpec = (TexturePartSpec) ModelRegistry.getInstance().getPart(renderTag.getCompound(TagConstants.TEXTURESPEC));
                CompoundTag specTag = renderTag.getCompound(TagConstants.TEXTURESPEC);
                int index = partSpec.getColourIndex(specTag);
                int[] colours = getColorArray();
                if (colours.length > index) {
                    return new Color(colours[index]);
                }
            }
        } catch (Exception e) {
            NuminaLogger.logException("something failed here: ", e);
        }
        return Color.WHITE;
    }
}