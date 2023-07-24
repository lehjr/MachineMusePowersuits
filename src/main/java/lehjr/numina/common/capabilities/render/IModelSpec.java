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

import lehjr.numina.common.capabilities.render.modelspec.NuminaModelSpecRegistry;
import lehjr.numina.common.capabilities.render.modelspec.SpecBase;
import lehjr.numina.common.constants.TagConstants;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public interface IModelSpec {
    @Nonnull
    ItemStack getItemStack();

    @Nonnull
    default NonNullList<SpecBase> getSpecList() {
        CompoundTag renderTag = getRenderTag();
        if (renderTag == null || renderTag.isEmpty()) {
            renderTag = getDefaultRenderTag();
        }
        NonNullList<SpecBase> specs = NonNullList.create();
        for (String key : renderTag.getAllKeys()) {
            if (key.equals(TagConstants.COLORS)) {
                continue;
            }
            if (renderTag.get(key) instanceof CompoundTag) {
                SpecBase testSpec = NuminaModelSpecRegistry.getInstance().getModel(renderTag.getCompound(key));
                if (isSpecValid(testSpec)) {
                    specs.add(testSpec);
                }
            }
        }
        return specs;
    }

    default boolean isSpecValid(SpecBase spec) {
        return true;
    }

    CompoundTag getRenderTag();

    CompoundTag setRenderTag(CompoundTag renderDataIn, String tagName);

    CompoundTag getDefaultRenderTag();

    List<Integer> addNewColorstoList(List<Integer> colors, List<Integer> colorsToAdd);

    int[] getColorArray();

    int getNewColorIndex(List<Integer> colors, List<Integer> oldColors, Integer index);

    CompoundTag setColorArray(int[] colors);
}