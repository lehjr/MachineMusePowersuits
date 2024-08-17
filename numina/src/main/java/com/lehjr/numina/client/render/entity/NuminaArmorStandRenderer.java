package com.lehjr.numina.client.render.entity;

import com.lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NuminaArmorStandRenderer extends ArmorStandRenderer {
    public NuminaArmorStandRenderer(EntityRendererProvider.Context manager) {
        super(manager);
        this.model.setAllVisible(true);
        this.model.hat.visible = false;
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public ResourceLocation getTextureLocation(ArmorStand entity) {
        return NuminaConstants.TEXTURE_ARMOR_STAND;
    }
}
