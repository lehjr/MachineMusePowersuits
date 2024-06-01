package lehjr.numina.client.gui.meter;

import lehjr.numina.client.config.IMeterConfig;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.utils.IconUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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

    public void draw(GuiGraphics gfx, float xpos, float ypos, float value) {
        super.draw(gfx, xpos, ypos, value);
        value = Mth.clamp(value + getConfig().getDebugValue(), 0F, 1F);
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
