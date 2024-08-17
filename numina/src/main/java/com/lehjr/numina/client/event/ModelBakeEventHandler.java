package com.lehjr.numina.client.event;

import com.lehjr.numina.client.model.helper.ModelSpecLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.neoforge.client.event.ModelEvent;

import java.util.ArrayList;
import java.util.List;

public class ModelBakeEventHandler {
    public static void onAddAdditional(ModelEvent.RegisterAdditional e) {
        ModelSpecLoader.INSTANCE.parse();

        locations.forEach(location -> {
            e.register(location);
        });
    }

    public static void addLocation(ModelResourceLocation location) {
        locations.add(location);
    }

    private static List<ModelResourceLocation> locations = new ArrayList<>();

    public static BakedModel getBakedItemModel(ModelResourceLocation location) {
        return  Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(location);
    }
}
