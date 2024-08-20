package com.lehjr.numina.common.capabilities.render.modelspec;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.utils.TagUtils;
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
            if (key.equals(NuminaConstants.COLORS)) {
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

    default CompoundTag getRenderTag() {
        return TagUtils.getRenderTag(getItemStack());
    }

    CompoundTag setRenderTag(CompoundTag renderDataIn, String tagName);

    /**
     * Primarrily used for getting a default tag for rendering without setting anything
     * @return
     */
    default CompoundTag getRenderTagOrDefault() {
        CompoundTag renderTag;
        renderTag = getRenderTag();
        if (renderTag == null || renderTag.isEmpty()) {
            renderTag = getDefaultRenderTag();
        }
        return renderTag;
    }

    CompoundTag getDefaultRenderTag();

    List<Integer> addNewColorstoList(List<Integer> colors, List<Integer> colorsToAdd);

    int[] getColorArray();

    default int[] getColorArrayOrDefault() {
        return getRenderTagOrDefault().getIntArray(NuminaConstants.COLORS);
    }

    int getNewColorIndex(List<Integer> colors, List<Integer> oldColors, Integer index);

    CompoundTag setColorArray(int[] colors);
}
