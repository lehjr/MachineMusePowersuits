package com.lehjr.numina.common.container.slot;

import com.lehjr.numina.client.gui.NuminaIcons;
import com.lehjr.numina.common.math.Color;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.IItemHandler;

public class EquipmentIconSlotItemHandler extends IconSlotItemHandler{
    final EquipmentSlot equipmentSlot;
    public EquipmentIconSlotItemHandler(IItemHandler itemHandler, int parent, int index, int xPosition, int yPosition, EquipmentSlot equipmentSlot) {
        super(itemHandler, parent, index, xPosition, yPosition);
        this.equipmentSlot = equipmentSlot;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        return SlotBackgrounds.getSlotBackground(equipmentSlot);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void drawIconAt(PoseStack matrixStack, double posX, double posY, Color color) {
        Pair<ResourceLocation, ResourceLocation> iconPair = SlotBackgrounds.getSlotBackground(equipmentSlot);
        NuminaIcons.DrawableIcon icon = NuminaIcons.getIcon(iconPair.getSecond());
        if (icon != null) {
            icon.renderIconScaledWithColor(matrixStack, posX, posY, 16, 16, Color.WHITE);
        }
    }
}
