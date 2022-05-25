package com.lehjr.numina.client.model.obj;

import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.geometry.IModelGeometryPart;
import org.jetbrains.annotations.Nullable;

public class OBJModelConfiguration implements IModelConfiguration {
    @Nullable
    @Override
    public UnbakedModel getOwnerModel() {
        return null;
    }

    @Override
    public String getModelName() {
        return null;
    }

    @Override
    public boolean isTexturePresent(String name) {
        return false;
    }

    @Override
    public Material resolveTexture(String name) {
        return null;
    }

    @Override
    public boolean isShadedInGui() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public boolean useSmoothLighting() {
        return false;
    }

    @Override
    public ItemTransforms getCameraTransforms() {
        return null;
    }

    @Override
    public ModelState getCombinedTransform() {
        return null;
    }

    @Override
    public boolean getPartVisibility(IModelGeometryPart part, boolean fallback) {
        return IModelConfiguration.super.getPartVisibility(part, fallback);
    }

    @Override
    public boolean getPartVisibility(IModelGeometryPart part) {
        return IModelConfiguration.super.getPartVisibility(part);
    }
}
