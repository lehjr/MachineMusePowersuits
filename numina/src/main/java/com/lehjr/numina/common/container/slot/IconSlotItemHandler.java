package com.lehjr.numina.common.container.slot;

import com.lehjr.numina.client.gui.NuminaIcons;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.math.Color;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.IItemHandler;

public abstract class IconSlotItemHandler extends HideableSlotItemHandler implements IIConProvider {
    public IconSlotItemHandler(IItemHandler itemHandler, int parent, int index, int xPosition, int yPosition) {
        super(itemHandler, parent, index, xPosition, yPosition);
    }


//    public IconSlotItemHandler(IItemHandler itemHandler, int parent, int index, int xPosition, int yPosition, boolean isEnabled) {
//        super(itemHandler, parent, index, xPosition, yPosition, isEnabled);
//    }
}
