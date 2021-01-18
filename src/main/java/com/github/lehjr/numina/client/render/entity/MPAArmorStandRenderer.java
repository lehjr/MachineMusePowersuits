package com.github.lehjr.numina.client.render.entity;

import com.github.lehjr.numina.constants.NuminaConstants;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MPAArmorStandRenderer extends ArmorStandRenderer {
    public MPAArmorStandRenderer(EntityRendererManager manager) {
        super(manager);
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public ResourceLocation getEntityTexture(ArmorStandEntity entity) {
        return NuminaConstants.TEXTURE_ARMOR_STAND;
    }
}