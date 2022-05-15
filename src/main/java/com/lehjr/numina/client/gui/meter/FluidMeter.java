package com.lehjr.numina.client.gui.meter;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.material.Fluid;

public class FluidMeter extends HeatMeter {
    Fluid fluid;

    public FluidMeter(Fluid fluid) {
        this.fluid = fluid;
    }

    @Override
    public TextureAtlasSprite getTexture() {
        return null; // Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getStill().toString());
    }
}
