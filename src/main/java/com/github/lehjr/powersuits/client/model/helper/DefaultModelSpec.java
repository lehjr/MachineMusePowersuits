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

package com.github.lehjr.powersuits.client.model.helper;

import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.util.capabilities.render.modelspec.*;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.item.armor.AbstractElectricItemArmor;
import com.github.lehjr.powersuits.item.tool.PowerFist;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.ListNBT;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 9:11 AM, 29/04/13
 * <p>
 * Ported to Java by lehjr on 11/8/16.
 * rewritten to be custom model compatible by lehjr on 12/26/17
 * <p>
 * Special note: tried forEach() with a filter, but speed was up to 8 times slower
 */

// FIXME: update to respect config settings...


//@OnlyIn(Dist.CLIENT)
public class DefaultModelSpec {
    public static CompoundNBT makeModelPrefs(@Nonnull ItemStack stack) {
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof AbstractElectricItemArmor)
                return makeModelPrefs(stack, ((AbstractElectricItemArmor) stack.getItem()).getEquipmentSlot());
            if (stack.getItem() instanceof PowerFist)
                return makeModelPrefs(stack, EquipmentSlotType.MAINHAND);
        }
        return new CompoundNBT();
    }

    public static CompoundNBT makeModelPrefs(@Nonnull ItemStack stack, EquipmentSlotType slot) {
        if (stack.isEmpty())
            return new CompoundNBT();

        List<CompoundNBT> prefArray = new ArrayList<>();

        // ModelPartSpecs
        ListNBT specList = new ListNBT();

        // TextureSpecBase (only one texture visible at a time)
        CompoundNBT texSpecTag = new CompoundNBT();

        // List of EnumColour indexes
        List<Integer> colours = new ArrayList<>();

        // temp data holder
        CompoundNBT tempNBT;

        // here we loop through the registry looking for the default that applies to the ItemStack
        for (SpecBase spec : ModelRegistry.getInstance().getSpecs()) {
            // Only generate NBT data from Specs marked as "default"
            if (spec.isDefault()) {

                /** Power Fist -------------------------------------------------------------------- */
                if (stack.getItem() instanceof PowerFist && spec.getSpecType().equals(EnumSpecType.HANDHELD)) {
                    colours = addNewColourstoList(colours, spec.getColours()); // merge new color int arrays in

                    for (PartSpecBase partSpec : spec.getPartSpecs()) {
                        if (partSpec instanceof ModelPartSpec) {
                            prefArray.add(((ModelPartSpec) partSpec).multiSet(new CompoundNBT(),
                                    getNewColourIndex(colours, spec.getColours(), partSpec.getDefaultColourIndex()),
                                    ((ModelPartSpec) partSpec).getGlow()));
                        }
                    }

                    /** Power Armor ------------------------------------------------------------------- */
                } else if (stack.getItem() instanceof AbstractElectricItemArmor) {
                    colours = addNewColourstoList(colours, spec.getColours()); // merge new color int arrays in

                    // Armor Skin
                    if (spec.getSpecType().equals(EnumSpecType.ARMOR_SKIN) && spec.get(slot.getName()) != null) {
                        // only a single texture per equipment itemSlot can be used at a time
                        texSpecTag = spec.get(slot.getName()).multiSet(new CompoundNBT(),
                                getNewColourIndex(colours, spec.getColours(), spec.get(slot.getName()).getDefaultColourIndex()));
                    }

                    // Armor models
                    else if (spec.getSpecType().equals(EnumSpecType.ARMOR_MODEL) && MPSSettings.allowHighPollyArmor()) {

                        for (PartSpecBase partSpec : spec.getPartSpecs()) {
                            if (partSpec.getBinding().getSlot() == slot) {
                                /*
                                // jet pack model not displayed by default
                                if (partSpec.binding.getItemState().equals("all") ||
                                        (partSpec.binding.getItemState().equals("jetpack") &&
                                                ModuleManager.INSTANCE.itemHasModule(stack, MPSModuleConstants.MODULE_JETPACK__DATANAME))) { */
                                    prefArray.add(((ModelPartSpec) partSpec).multiSet(new CompoundNBT(),
                                            getNewColourIndex(colours, spec.getColours(), partSpec.getDefaultColourIndex()),
                                            ((ModelPartSpec) partSpec).getGlow()));
                                /*} */
                            }
                        }
                    }
                }
            }
        }

        CompoundNBT nbt = new CompoundNBT();
        for (CompoundNBT elem : prefArray) {
            nbt.put(elem.getString(NuminaConstants.TAG_MODEL) + "." + elem.getString(NuminaConstants.TAG_PART), elem);
        }

        if (!specList.isEmpty())
            nbt.put(NuminaConstants.NBT_SPECLIST_TAG, specList);

        if (!texSpecTag.isEmpty())
            nbt.put(NuminaConstants.NBT_TEXTURESPEC_TAG, texSpecTag);

        nbt.put(NuminaConstants.TAG_COLOURS, new IntArrayNBT(colours));

        return nbt;
    }

    /**
     * When dealing with possibly multiple specs and color lists, new list needs to be created, since there is only one list per item.
     */
    static List<Integer> addNewColourstoList(List<Integer> colours, List<Integer> coloursToAdd) {
        for (Integer i : coloursToAdd) {
            if (!colours.contains(i))
                colours.add(i);
        }
        return colours;
    }

    /**
     * new array means setting a new array index for the same getValue
     */
    public static int getNewColourIndex(List<Integer> colours, List<Integer> oldColours, Integer index) {
        return colours.indexOf(oldColours.get(index != null ? index : 0));
    }
}