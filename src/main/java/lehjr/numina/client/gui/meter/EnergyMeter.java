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
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

import java.util.concurrent.Callable;

public class EnergyMeter extends HeatMeter {
    final FluidStack fluidStack = new FluidStack(Fluids.FLOWING_WATER, 1000);
    final ResourceLocation TEXTURE_LOCATION = IClientFluidTypeExtensions.of(Fluids.FLOWING_WATER).getFlowingTexture(fluidStack);

    public EnergyMeter(Callable<IMeterConfig> config) {
        super(config);
    }

    public TextureAtlasSprite getTexture() {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(TEXTURE_LOCATION);
    }

    @Override
    public void drawAdditional(GuiGraphics gfx, float left, float top, float value) {
        value = Mth.clamp(value + getConfig().getDebugValue(), 0F, 1F);
        if (value > 0) {
            IconUtils.drawMPDLightning(gfx,
                    left + xsize * value, (float) (top + ysize * (Math.random() / 2F + 0.25)),
                    1F,
                    left, (float) (top + ysize * (Math.random() / 2 + 0.25)),
                    1F, Color.WHITE,
                    4, 1);
        }
    }
}