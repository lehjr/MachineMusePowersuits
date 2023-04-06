package lehjr.numina.client.gui.meter;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.config.IMeterConfig;
import lehjr.numina.client.render.NuminaRenderer;
import lehjr.numina.common.math.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.concurrent.Callable;

public class EnergyMeter extends HeatMeter {
    final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("minecraft:block/water_still");

    public EnergyMeter(Callable<IMeterConfig> config) {
        super(config);
    }

    public TextureAtlasSprite getTexture() {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(TEXTURE_LOCATION);
    }

    public void draw(PoseStack poseStack, float xpos, float ypos, float value) {
        super.draw(poseStack, xpos, ypos, value);
        value = Mth.clamp(value + getConfig().getDebugValue(), 0F, 1F);
        if (value > 0) {
            NuminaRenderer.drawMPDLightning(poseStack,
                    xpos + xsize * value, (float) (ypos + ysize * (Math.random() / 2F + 0.25)),
                    1F,
                    xpos, (float) (ypos + ysize * (Math.random() / 2 + 0.25)),
                    1F, Color.WHITE,
                    4, 1);
        }
    }
}