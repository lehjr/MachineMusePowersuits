package lehjr.numina.client.gui.meter;

import lehjr.numina.client.config.IMeterConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

import java.util.concurrent.Callable;

/**
 * Created by leon on 4/9/17.
 */
public class PlasmaChargeMeter extends HeatMeter {
    final ResourceLocation TEXTURE_LOCATION = new ResourceLocation( "block/water_still");

    public PlasmaChargeMeter(Callable<IMeterConfig> config) {
        super(config);
    }
    public TextureAtlasSprite getTexture() {
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(TEXTURE_LOCATION);
    }
}