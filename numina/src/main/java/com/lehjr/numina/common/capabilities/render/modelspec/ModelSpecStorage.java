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

package com.lehjr.numina.common.capabilities.render.modelspec;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.numina.common.utils.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class ModelSpecStorage implements IModelSpec {
    ItemStack itemStack;

    public ModelSpecStorage(@Nonnull ItemStack itemStackIn){
        this.itemStack = itemStackIn;
    }

    @Nonnull
    @Override
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @Override
    public ItemStack setRenderTag(CompoundTag renderDataIn, String tagName) {
        CompoundTag renderTag = getRenderTag();
        NuminaLogger.logDebug("RenderTag start: " + renderTag);

        if (tagName != null) {
            // set up clean render tag
            if (Objects.equals(tagName, NuminaConstants.RENDER_TAG)) {
                NuminaLogger.logger.debug("Removing render tag");

                if (!renderDataIn.isEmpty()) {
                    NuminaLogger.logger.debug("Setting tag render : " + renderDataIn);
                    renderTag = renderDataIn;
                } else {
                    renderTag = new CompoundTag();
                }
                // setup new colors
                if(!renderTag.contains(NuminaConstants.COLORS)) {
                    renderTag.putIntArray(NuminaConstants.COLORS, new int[]{-1});
                }
                return TagUtils.setRenderTag(itemStack, renderTag);

            } else {
                if (renderDataIn.isEmpty()) {
                    NuminaLogger.logger.debug("Removing tag " + tagName);
                    renderTag.remove(tagName);
                    renderTag.remove(tagName.replace(".", ""));
                } else {
                    NuminaLogger.logger.debug("Adding tag " + tagName + " : " + renderDataIn);
                    renderTag.put(tagName, renderDataIn);
                }

                return TagUtils.setRenderTag(itemStack, renderTag);
            }
        }
        return itemStack;
    }

    @Override
    public CompoundTag getRenderTag() {
        return TagUtils.getRenderTag(itemStack);
    }

    @Override
    public CompoundTag getDefaultRenderTag() {
        return new CompoundTag();
    }

    /**
     * When dealing with possibly multiple specs and color lists, new list needs to be created, since there is only one list per item.
     */
    @Override
    public List<Integer> addNewColorstoList(List<Integer> colors, List<Integer> colorsToAdd) {
        for (Integer i : colorsToAdd) {
            if (!colors.contains(i))
                colors.add(i);
        }
        return colors;
    }

    @Override
    public int[] getColorArray() {
        return  getRenderTag().getIntArray(NuminaConstants.COLORS);
    }

    /**
     * new array means setting a new array index for the same getValue
     */
    @Override
    public int getNewColorIndex(List<Integer> colors, List<Integer> oldColors, Integer index) {
        return colors.indexOf(oldColors.get(index != null ? index : 0));
    }

    @Override
    public ItemStack setColorArray(int[] colors) {
        return TagUtils.setColorArray(itemStack, colors);
    }
}
