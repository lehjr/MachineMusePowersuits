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

package com.lehjr.powersuits.client.render;


import com.lehjr.numina.common.capabilities.render.IArmorModelSpecNBT;
import com.lehjr.numina.common.capabilities.render.ModelSpecNBT;
import com.lehjr.numina.common.capabilities.render.modelspec.*;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.constants.TagConstants;
import com.lehjr.powersuits.common.config.MPSSettings;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ArmorModelSpecNBT extends ModelSpecNBT implements IArmorModelSpecNBT {
    public ArmorModelSpecNBT(@Nonnull ItemStack itemStackIn) {
        super(itemStackIn);
    }

    @Override
    public SpecType getSpecType() {
        CompoundTag renderTag = getRenderTag();
        if (renderTag == null || renderTag.isEmpty()) {
              renderTag = getDefaultRenderTag();
        }

        try {
            TexturePartSpec partSpec = (TexturePartSpec) ModelRegistry.getInstance().getPart(renderTag.getCompound(TagConstants.TEXTURESPEC));
            if (partSpec != null) {
                return SpecType.ARMOR_SKIN;
            }
        } catch (Exception ignored) {
        }

        for (String key : renderTag.getAllKeys()) {
            if (key.equals("colours")) {
                continue;
            }
            if (renderTag.get(key) instanceof CompoundTag) {
                SpecBase testSpec = ModelRegistry.getInstance().getModel(renderTag.getCompound(key));
                if (testSpec instanceof ModelSpec) {
                    return SpecType.ARMOR_MODEL;
                }
            }
        }
        return SpecType.NONE;
    }

    @Override
    public CompoundTag getDefaultRenderTag() {
        if (getItemStack().isEmpty()) {
            return new CompoundTag();
        }

        List<CompoundTag> prefArray = new ArrayList<>();

        // ModelPartSpecs
        ListTag specList = new ListTag();

        // TextureSpecBase (only one texture visible at a time)
        CompoundTag texSpecTag = new CompoundTag();

        // List of EnumColor indexes
        List<Integer> colours = new ArrayList<>();

        // temp data holder
        CompoundTag tempNBT;

        EquipmentSlot slot = Mob.getEquipmentSlotForItem(getItemStack());

        for (SpecBase spec : ModelRegistry.getInstance().getSpecs()) {
            // Only generate NBT data from Specs marked as "default"
            if (spec.isDefault()) {
                if (getItemStack().getItem() instanceof ArmorItem) {
                    colours = addNewColorstoList(colours, spec.getColors()); // merge new color int arrays in

                    // Armor Skin
                    if (spec.getSpecType().equals(SpecType.ARMOR_SKIN) && spec.get(slot.getName()) != null) {
                        // only a single texture per equipment slot can be used at a time
                        texSpecTag = spec.get(slot.getName()).multiSet(new CompoundTag(),
                                getNewColorIndex(colours, spec.getColors(), spec.get(slot.getName()).getDefaultColorIndex()));
                    }

                    // Armor models
                    else if (spec.getSpecType().equals(SpecType.ARMOR_MODEL) && MPSSettings.allowHighPollyArmor()) {
                        for (PartSpecBase partSpec : spec.getPartSpecs()) {
                            if (partSpec.getBinding().getSlot() == slot) {
                                /*
                                // jet pack model not displayed by default
                                if (partSpec.binding.getItemState().equals("all") ||
                                        (partSpec.binding.getItemState().equals("jetpack") &&
                                                ModuleManager.INSTANCE.itemHasModule(stack, MPSModuleConstants.MODULE_JETPACK__DATANAME))) { */
                                prefArray.add(((ModelPartSpec) partSpec).multiSet(new CompoundTag(),
                                        getNewColorIndex(colours, spec.getColors(), partSpec.getDefaultColorIndex()),
                                        ((ModelPartSpec) partSpec).getGlow()));
                                /*} */
                            }
                        }
                    }
                }
            }
        }

        CompoundTag nbt = new CompoundTag();
        for (CompoundTag elem : prefArray) {
            nbt.put(elem.getString(TagConstants.MODEL) + "." + elem.getString(TagConstants.PART), elem);
        }

        if (!specList.isEmpty()) {
            nbt.put(TagConstants.SPECLIST, specList);
        }

        if (!texSpecTag.isEmpty()) {
            nbt.put(TagConstants.TEXTURESPEC, texSpecTag);
        }

        nbt.put(TagConstants.COLORS, new IntArrayTag(colours));
        return nbt;
    }

    @Override
    public ResourceLocation getArmorTexture() {
        CompoundTag itemTag = getItemStack().getOrCreateTag();
        CompoundTag renderTag = itemTag.getCompound(TagConstants.RENDER);
        try {
            TexturePartSpec partSpec = (TexturePartSpec) ModelRegistry.getInstance().getPart(renderTag.getCompound(TagConstants.TEXTURESPEC));
            return partSpec.getTextureLocation();
        } catch (Exception ignored) {
            return NuminaConstants.BLANK_ARMOR_MODEL_PATH;
        }
    }
}
