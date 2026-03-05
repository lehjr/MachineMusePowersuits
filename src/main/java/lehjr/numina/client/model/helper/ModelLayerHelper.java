package lehjr.numina.client.model.helper;

import lehjr.numina.client.render.item.NuminaArmorLayer;
import lehjr.numina.common.base.NuminaLogger;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class ModelLayerHelper {
    public static <T extends LivingEntity, M extends EntityModel<T>> void addCustomLayers(@Nonnull EntityType<?> type,
        @Nonnull LivingEntityRenderer<T, M> renderer,
        @Nonnull EntityRendererProvider.Context context) {
        int layerTypes = 2;
        Map<String, RenderLayer<T, M>> layersToAdd = new HashMap<>(layerTypes);
        for (RenderLayer<T, M> layerRenderer : renderer.layers) {
            //Validate against the layer render being null, as it seems like some mods do stupid things and add in null layers
            if (layerRenderer != null) {
                //Only allow an exact class match, so we don't add to modded entities that only have a modded extended armor or elytra layer
                Class<?> layerClass = layerRenderer.getClass();
                if (layerClass == HumanoidArmorLayer.class) {
                    //Note: We know that the MODEL is actually an instance of HumanoidModel, or there wouldn't be a
                    //noinspection unchecked,rawtypes
                    layersToAdd.put("Armor", new NuminaArmorLayer(renderer, (HumanoidArmorLayer<T, ?, ?>) layerRenderer, context.getModelManager()));
                    if (layersToAdd.size() == layerTypes) {
                        break;
                    }
                }
                //                else if (layerClass == ElytraLayer.class) {
                //                    layersToAdd.put("Elytra", new NuminaElytraLayer<>(renderer, context.getModelSet()));
                //                    if (layersToAdd.size() == layerTypes) {
                //                        break;
                //                    }
                //                }
            }
        }
        if (!layersToAdd.isEmpty()) {
            ResourceLocation entityName = BuiltInRegistries.ENTITY_TYPE.getKey(type);
            for (Map.Entry<String, RenderLayer<T, M>> entry : layersToAdd.entrySet()) {
                renderer.addLayer(entry.getValue());
                NuminaLogger.getLogger().debug("Added NuminaArmor {} Layer to entity of type: {}", entry.getKey(), entityName);
            }
        }
    }
}
