package lehjr.numina.client.event;

import lehjr.numina.client.model.helper.ModelSpecLoader;
import lehjr.numina.common.base.Numina;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

public enum ModelBakeEventHandler {
    INSTANCE;

    public void onAddAdditional(ModelEvent.RegisterAdditional e) {
        ModelSpecLoader.INSTANCE.parse();
        INSTANCE.locations.forEach(location -> {
            e.register(location);
        });
    }

    public void addLocation(ResourceLocation location) {
        INSTANCE.locations.add(location);
    }

    private List<ResourceLocation> locations = new ArrayList<>();

    public BakedModel getBakedItemModel(ResourceLocation location) {
        return  Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(location);
    }
}
