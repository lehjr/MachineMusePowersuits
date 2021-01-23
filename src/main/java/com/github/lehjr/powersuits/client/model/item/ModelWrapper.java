package com.github.lehjr.powersuits.client.model.item;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraftforge.client.model.BakedModelWrapper;

public class ModelWrapper extends BakedModelWrapper {
    public ModelWrapper(IBakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return super.getOverrides();
    }
}
