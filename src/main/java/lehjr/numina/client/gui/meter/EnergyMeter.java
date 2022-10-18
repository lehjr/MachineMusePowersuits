package lehjr.numina.client.gui.meter;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.render.NuminaRenderer;
import lehjr.numina.common.math.Colour;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class EnergyMeter extends HeatMeter {
    final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("minecraft:block/water_still");

    public Colour getColour() {
        return Colour.LIGHT_GREEN;
    }

    public TextureAtlasSprite getTexture() {
        return Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(TEXTURE_LOCATION);
    }

    @Override
    public float getAlpha() {
        return 0.8F;
    }

    public void draw(MatrixStack poseStack, float xpos, float ypos, float value) {
        super.draw(poseStack, xpos, ypos, value);
        RenderSystem.enableBlend();
            NuminaRenderer.drawMPDLightning(poseStack,
                    xpos + xsize * value, (float) (ypos + ysize * (Math.random() / 2F + 0.25)),
                    1F,
                    xpos, (float) (ypos + ysize * (Math.random() / 2 + 0.25)),
                    1F, Colour.WHITE,
                    4, 1);
        RenderSystem.disableBlend();
    }
}