package lehjr.numina.client.event;

import lehjr.numina.client.model.helper.ModelSpecLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
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

    public static void addLocation(ResourceLocation location) {
        locations.add(location);
    }

    private static List<ResourceLocation> locations = new ArrayList<>();

    public static BakedModel getBakedItemModel(ResourceLocation location) {
        return  Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(location);
    }
}
