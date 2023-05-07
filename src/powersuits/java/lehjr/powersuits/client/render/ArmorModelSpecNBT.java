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

import lehjr.numina.common.capabilities.render.IArmorModelSpecNBT;
import lehjr.numina.common.capabilities.render.ModelSpecStorage;
import lehjr.numina.common.capabilities.render.modelspec.*;
import lehjr.numina.common.constants.TagConstants;
import lehjr.powersuits.common.config.MPSSettings;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.NonNullList;
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

public class ArmorModelSpecNBT extends ModelSpecStorage implements IArmorModelSpecNBT {
    public ArmorModelSpecNBT(@Nonnull ItemStack itemStackIn) {
        super(itemStackIn);
    }

    @Override
    public NonNullList<SpecBase> getSpecList() {
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
                if (testSpec instanceof ObjModelSpec || testSpec instanceof JavaModelSpec) {
                    specs.add(testSpec);
                }
            }
        }
        return specs;
    }

    @Override
    public CompoundTag getDefaultRenderTag() {
        if (getItemStack().isEmpty()) {
            return new CompoundTag();
        }

        List<CompoundTag> prefArray = new ArrayList<>();

        // ModelPartSpecs
        ListTag specList = new ListTag();

        // List of EnumColour indexes
        List<Integer> colors = new ArrayList<>();

        // temp data holder
        CompoundTag tempNBT;

        EquipmentSlot slot = Mob.getEquipmentSlotForItem(getItemStack());

        for (SpecBase spec : NuminaModelSpecRegistry.getInstance().getSpecs()) {
            // Only generate NBT data from Specs marked as "default"
            if (spec.isDefault()) {
                if (getItemStack().getItem() instanceof ArmorItem) {
                    colors = addNewColourstoList(colors, spec.getColors()); // merge new color int arrays in

                    // Armor Skin
                    if (spec.getSpecType().equals(SpecType.ARMOR_SKIN) && spec.get(slot.getName()) != null) {
                        for (PartSpecBase partSpec : spec.getPartSpecs()) {
                            if (partSpec.getBinding().getSlot() == slot) {
                                prefArray.add(partSpec.multiSet(new CompoundTag(),
                                        getNewColourIndex(colors, spec.getColors(), partSpec.getDefaultColourIndex()),
                                        partSpec.getGlow()));
                            }
                        }
                    }

                    // Armor models
                    else if (spec.getSpecType().equals(SpecType.ARMOR_OBJ_MODEL) && MPSSettings.allowHighPollyArmor()) {
                        for (PartSpecBase partSpec : spec.getPartSpecs()) {
                            if (partSpec.getBinding().getSlot() == slot) {
                                /*
                                // jet pack model not displayed by default
                                if (partSpec.binding.getItemState().equals("all") ||
                                        (partSpec.binding.getItemState().equals("jetpack") &&
                                                ModuleManager.INSTANCE.itemHasModule(stack, MPSModuleConstants.MODULE_JETPACK__DATANAME))) { */
                                prefArray.add(partSpec.multiSet(new CompoundTag(),
                                        getNewColourIndex(colors, spec.getColors(), partSpec.getDefaultColourIndex()),
                                        partSpec.getGlow()));
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
        nbt.put(TagConstants.COLORS, new IntArrayTag(colors));
        return nbt;
    }

    @Override
    public ResourceLocation getArmorTexture(PartSpecBase part) {
        if (part instanceof JavaPartSpec) {
            return ((JavaPartSpec) part).getTextureLocation();
        }
        // TODO: eventually, maybe put armor textures in their own atlas?
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
