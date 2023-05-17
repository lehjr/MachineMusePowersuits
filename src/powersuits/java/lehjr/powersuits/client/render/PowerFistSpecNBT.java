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

package lehjr.powersuits.client.render;

import lehjr.numina.common.capabilities.render.IHandHeldEntitySpecNBT;
import lehjr.numina.common.capabilities.render.ModelSpecStorage;
import lehjr.numina.common.capabilities.render.modelspec.NuminaModelSpecRegistry;
import lehjr.numina.common.capabilities.render.modelspec.PartSpecBase;
import lehjr.numina.common.capabilities.render.modelspec.SpecBase;
import lehjr.numina.common.capabilities.render.modelspec.SpecType;
import lehjr.numina.common.constants.TagConstants;
import lehjr.powersuits.common.item.tool.PowerFist;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class PowerFistSpecNBT extends ModelSpecStorage implements IHandHeldEntitySpecNBT {
    public PowerFistSpecNBT(@Nonnull ItemStack itemStackIn) {
        super(itemStackIn);
    }

    @Override
    public CompoundTag getDefaultRenderTag() {
        if (getItemStack().isEmpty())
            return new CompoundTag();

        List<CompoundTag> prefArray = new ArrayList<>();

        // ModelPartSpecs
        ListTag specList = new ListTag();

        // TextureSpecBase (only one texture visible at a time)
        CompoundTag texSpecTag = new CompoundTag();

        // List of EnumColor indexes
        List<Integer> colors = new ArrayList<>();

        // temp data holder
        CompoundTag tempNBT;

        EquipmentSlot slot = getItemStack().getEquipmentSlot();

        for (SpecBase spec : NuminaModelSpecRegistry.getInstance().getSpecs()) {
            // Only generate NBT data from Specs marked as "default"
            if (spec.isDefault()) {
                if (getItemStack().getItem() instanceof PowerFist && (spec.getSpecType().equals(SpecType.HANDHELD_OBJ_MODEL) || spec.getSpecType().equals(SpecType.HANDHELD_JAVA_MODEL))) {
                    colors = addNewColorstoList(colors, spec.getColors()); // merge new color int arrays in

                    for (PartSpecBase partSpec : spec.getPartSpecs()) {
//                        if (partSpec instanceof ModelPartSpec) {
                            prefArray.add(partSpec.multiSet(new CompoundTag(),
                                    getNewColorIndex(colors, spec.getColors(), partSpec.getDefaultColorIndex()),
                                    partSpec.getGlow()));
//                        }
                    }
                }
            }
        }

        CompoundTag nbt = new CompoundTag();
        for (CompoundTag elem : prefArray) {
            nbt.put(elem.getString(TagConstants.MODEL) + "." + elem.getString(TagConstants.PART), elem);
        }

        if (!specList.isEmpty())
            nbt.put(TagConstants.SPECLIST, specList);

//        if (!texSpecTag.isEmpty())
//            nbt.put(TagConstants.TEXTURESPEC, texSpecTag);

        nbt.put(TagConstants.COLORS, new IntArrayTag(colors));
        return nbt;
    }
}
