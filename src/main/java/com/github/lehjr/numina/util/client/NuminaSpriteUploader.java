package com.github.lehjr.numina.util.client;

import com.github.lehjr.numina.constants.NuminaConstants;
import net.minecraft.client.renderer.texture.SpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class NuminaSpriteUploader extends SpriteUploader {
    private final Set<ResourceLocation> registeredSprites = new HashSet<>();

    public NuminaSpriteUploader(TextureManager textureManager, String prefix) {
        super(textureManager, NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS, prefix);
    }

    public void registerIcon(ResourceLocation location) {
        registeredSprites.add(location);
    }

    public void registerSprite(ResourceLocation location) {
        registeredSprites.add(location);
    }

    @Override
    protected Stream<ResourceLocation> getResourceLocations() {
        return Collections.unmodifiableSet(registeredSprites).stream();
    }

    /**
     * Overridden to make it public
     */
    @Override
    public TextureAtlasSprite getSprite(ResourceLocation location) {
        return super.getSprite(location);
    }
}