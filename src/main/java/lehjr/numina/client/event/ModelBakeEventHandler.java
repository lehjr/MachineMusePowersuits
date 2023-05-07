package lehjr.numina.client.event;

import lehjr.numina.client.model.helper.ModelSpecLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public enum ModelBakeEventHandler {
    INSTANCE;

    @SubscribeEvent
    public void onAddAdditional(ModelEvent.RegisterAdditional e) {
        ModelSpecLoader.INSTANCE.parse();

        System.out.println("ModelBakeEventHandler adding mdoels size " + locations.size());

        /*
            ModelBakeEventHandler model location here: powersuits:models/item/armor/json/armor2
            ModelBakeEventHandler model location here: powersuits:models/item/armor/json/mps_helm
            ModelBakeEventHandler model location here: powersuits:models/item/armor/json/mps_chest
            ModelBakeEventHandler model location here: powersuits:models/item/armor/json/jetpack
            ModelBakeEventHandler model location here: powersuits:models/item/armor/json/mps_arms
            ModelBakeEventHandler model location here: powersuits:models/item/armor/json/mps_pantaloons
            ModelBakeEventHandler model location here: powersuits:models/item/armor/json/mps_boots
        */

        INSTANCE.locations.forEach(location -> {
            System.out.println("ModelBakeEventHandler model location here: " + location);
            e.register(location);
        });
    }

    public void addLocation(ResourceLocation location) {
        System.out.println("ModelBakeEventHandler model location here: " + location);

        INSTANCE.locations.add(location);
    }

    private List<ResourceLocation> locations = new ArrayList<>();

    public BakedModel getBakedItemModel(ResourceLocation location) {
        return  Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(location);
    }
}
