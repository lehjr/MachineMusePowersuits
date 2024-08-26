package com.lehjr.numina.client.gui.meter;

import com.lehjr.numina.client.config.IMeterConfig;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.utils.IconUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

import java.util.concurrent.Callable;

public class EnergyMeter extends HeatMeter {
    final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath("minecraft", "block/water_still");

    public EnergyMeter(Callable<IMeterConfig> config) {
        super(config);
    }

    public TextureAtlasSprite getTexture() {
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(TEXTURE_LOCATION);
    }

    @Override
    public void drawAdditional(GuiGraphics gfx, float xpos, float ypos, float value) {
        if (value > 0) {
            IconUtils.drawMPDLightning(gfx,
                xpos + xsize * value, (float) (ypos + ysize * (Math.random() / 2F + 0.25)),
                1F,
                xpos, (float) (ypos + ysize * (Math.random() / 2 + 0.25)),
                1F, Color.WHITE,
                4, 1);
        }
    }
}
