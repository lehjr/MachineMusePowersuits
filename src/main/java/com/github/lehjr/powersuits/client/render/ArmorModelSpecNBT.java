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

package com.github.lehjr.powersuits.client.render;

import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.util.capabilities.render.IArmorModelSpecNBT;
import com.github.lehjr.numina.util.capabilities.render.ModelSpecNBT;
import com.github.lehjr.numina.util.capabilities.render.modelspec.*;
import com.github.lehjr.numina.util.nbt.MuseNBTUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import net.minecraft.entity.MobEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.ListNBT;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ArmorModelSpecNBT extends ModelSpecNBT implements IArmorModelSpecNBT {
    public ArmorModelSpecNBT(@Nonnull ItemStack itemStackIn) {
        super(itemStackIn);
    }

    @Override
    public EnumSpecType getSpecType() {
        CompoundNBT renderTag = getRenderTag();
        if (renderTag == null || renderTag.isEmpty()) {
              renderTag = getDefaultRenderTag();
        }

        try {
            TexturePartSpec partSpec = (TexturePartSpec) ModelRegistry.getInstance().getPart(renderTag.getCompound(NuminaConstants.NBT_TEXTURESPEC_TAG));
            if (partSpec != null) {
                return EnumSpecType.ARMOR_SKIN;
            }
        } catch (Exception ignored) {
        }

        for (String key : renderTag.getAllKeys()) {
            if (key.equals("colours")) {
                continue;
            }
            if (renderTag.get(key) instanceof CompoundNBT) {
                SpecBase testSpec = ModelRegistry.getInstance().getModel(renderTag.getCompound(key));
                if (testSpec instanceof ModelSpec) {
                    return EnumSpecType.ARMOR_MODEL;
                }
            }
        }
        return EnumSpecType.NONE;
    }

    @Override
    public CompoundNBT getDefaultRenderTag() {
        if (getItemStack().isEmpty())
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

        EquipmentSlotType slot = getItemStack().getEquipmentSlot();
        if (slot == null) {
            slot = MobEntity.getEquipmentSlotForItem(getItemStack());
        }

        for (SpecBase spec : ModelRegistry.getInstance().getSpecs()) {
            // Only generate NBT data from Specs marked as "default"
            if (spec.isDefault()) {
                if (getItemStack().getItem() instanceof ArmorItem) {
                    colours = addNewColourstoList(colours, spec.getColours()); // merge new color int arrays in

                    // Armor Skin
                    if (spec.getSpecType().equals(EnumSpecType.ARMOR_SKIN) && spec.get(slot.getName()) != null) {
                        // only a single texture per equipment slot can be used at a time
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

    @Override
    public String getArmorTexture() {
        CompoundNBT itemTag = MuseNBTUtils.getMuseItemTag(getItemStack());
        CompoundNBT renderTag = itemTag.getCompound(NuminaConstants.TAG_RENDER);
        try {
            TexturePartSpec partSpec = (TexturePartSpec) ModelRegistry.getInstance().getPart(renderTag.getCompound(NuminaConstants.NBT_TEXTURESPEC_TAG));
            return partSpec.getTextureLocation();
        } catch (Exception ignored) {
            return NuminaConstants.BLANK_ARMOR_MODEL_PATH;
        }
    }
}
