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
import lehjr.numina.common.capabilities.render.modelspec.SpecType;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.tags.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class ModelSpecStorage implements IModelSpec, INBTSerializable<CompoundTag> {
    ItemStack itemStack;
    static final String TAG_RENDER = "render";

    public ModelSpecStorage(@Nonnull ItemStack itemStackIn){
        this.itemStack = itemStackIn;
    }

    @Nonnull
    @Override
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @Override
    public CompoundTag setRenderTag(CompoundTag renderDataIn, String tagName) {
        CompoundTag itemTag = TagUtils.getMuseItemTag(itemStack);
        if (tagName != null) {
            if (Objects.equals(tagName, TagConstants.RENDER)) {
                NuminaLogger.logger.debug("Removing render tag");
                itemTag.remove(TagConstants.RENDER);
                if (!renderDataIn.isEmpty()) {
                    NuminaLogger.logger.debug("Adding tag " + TagConstants.RENDER + " : " + renderDataIn);
                    itemTag.put(TagConstants.RENDER, renderDataIn);
                } else {
                    itemTag.put(TagConstants.RENDER, new CompoundTag());
                    setColorArray(new int[]{-1});
                }
            } else {
                CompoundTag renderTag;
                if (!itemTag.contains(TagConstants.RENDER)) {
                    renderTag = new CompoundTag();
                    itemTag.put(TagConstants.RENDER, renderTag);
                } else {
                    renderTag = itemTag.getCompound(TagConstants.RENDER);
                }
                if (renderDataIn.isEmpty()) {
                    NuminaLogger.logger.debug("Removing tag " + tagName);
                    renderTag.remove(tagName);
                    renderTag.remove(tagName.replace(".", ""));
                } else {
                    NuminaLogger.logger.debug("Adding tag " + tagName + " : " + renderDataIn);
                    renderTag.put(tagName, renderDataIn);
                }
            }
        }
        return getRenderTag();
    }

    @Override
    public SpecType getSpecType() {
        if (itemStack.getEquipmentSlot() == null) {
            return SpecType.HANDHELD;
        }
        return SpecType.NONE;
    }

    @Override
    @Nullable
    public CompoundTag getRenderTag() {
        CompoundTag itemTag = TagUtils.getMuseItemTag(itemStack);
        return itemTag.getCompound(TAG_RENDER);
    }

    @Override
    public CompoundTag getDefaultRenderTag() {
        return new CompoundTag();
    }

    /**
     * When dealing with possibly multiple specs and color lists, new list needs to be created, since there is only one list per item.
     */
    @Override
    public List<Integer> addNewColourstoList(List<Integer> colours, List<Integer> coloursToAdd) {
        for (Integer i : coloursToAdd) {
            if (!colours.contains(i))
                colours.add(i);
        }
        return colours;
    }

    @Override
    public int[] getColorArray() {
        return  getRenderTag().getIntArray(TagConstants.COLORS);
    }


    /**
     * new array means setting a new array index for the same getValue
     */
    @Override
    public int getNewColourIndex(List<Integer> colours, List<Integer> oldColours, Integer index) {
        return colours.indexOf(oldColours.get(index != null ? index : 0));
    }

    @Override
    public CompoundTag setColorArray(int[] colors) {
        getRenderTag().putIntArray(TagConstants.COLORS, colors);
        return getRenderTag();
    }

    // INBTSerializable<CompoundTag> ----------------------------------------------------------------------------------
    @Override
    public CompoundTag serializeNBT() {
        return getRenderTag();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        setRenderTag(nbt, TagConstants.RENDER);
    }
}