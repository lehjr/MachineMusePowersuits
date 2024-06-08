package lehjr.numina.client.gui;

import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;

public class NuminaSpriteUploader extends TextureAtlasHolder {
    static final ResourceLocation GUI_ATLAS = new ResourceLocation(NuminaConstants.MOD_ID,"gui");

    public NuminaSpriteUploader(TextureManager textureManager) {
        super(textureManager, NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS, GUI_ATLAS);
    }

    /**
     * Overridden to make it public
     */
    @Override
    public TextureAtlasSprite getSprite(ResourceLocation location) {
        return super.getSprite(location);
    }

    public TextureAtlas getAtlas() {
        return super.textureAtlas;
    }
}
