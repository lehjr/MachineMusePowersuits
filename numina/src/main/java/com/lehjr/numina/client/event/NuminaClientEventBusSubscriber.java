package com.lehjr.numina.client.event;

import com.lehjr.numina.client.gui.NuminaIcons;
import com.lehjr.numina.client.overlay.ModeChangingIconOverlay;
import com.lehjr.numina.client.render.entity.NuminaArmorStandRenderer;
import com.lehjr.numina.client.render.item.NuminaArmorLayer;
import com.lehjr.numina.client.render.item.NuminaArmorStandItemRenderer;
import com.lehjr.numina.client.screen.ArmorStandScreen;
import com.lehjr.numina.client.screen.ChargingBaseScreen;
import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.registration.NuminaEntities;
import com.lehjr.numina.common.registration.NuminaItems;
import com.lehjr.numina.common.registration.NuminaMenus;
import com.lehjr.numina.common.utils.IconUtils;
import net.forge.client.NuminaObjLoader;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = NuminaConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NuminaClientEventBusSubscriber {
    @SubscribeEvent
    public static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(NuminaEntities.ARMOR_STAND__ENTITY_TYPE.get(), NuminaArmorStandRenderer::new);
    }

    @SubscribeEvent
    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        event.register(NuminaFOVUpdateEventHandler.fovToggleKey.get());
    }

    @SubscribeEvent
    public static void registerScreen(RegisterMenuScreensEvent event) {
        event.register(NuminaMenus.CHARGING_BASE_CONTAINER_TYPE.get(), ChargingBaseScreen::new);
        event.register(NuminaMenus.ARMOR_STAND_CONTAINER_TYPE.get(), ArmorStandScreen::new);
    }

    public static void onRegisterReloadListenerEvent(RegisterClientReloadListenersEvent event) {
        NuminaIcons icon = IconUtils.getIcon();
        event.registerReloadListener(icon.getSpriteUploader());
    }

    public static void modelRegistry(ModelEvent.RegisterGeometryLoaders event) {
        event.register(ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, "obj"), NuminaObjLoader.INSTANCE);
    }
    //
    //    @SubscribeEvent
    //    public static void renderLayer(RenderGuiLayerEvent event) {
    //        LayeredDraw.Layer layer = event.getLayer();
    //        GuiGraphics gfx = event.getGuiGraphics();
    //        ResourceLocation name = event.getName();
    //        float partialTick = event.getPartialTick();
    //
    //
    //    }

    @SubscribeEvent
    public static void registerOverlay(RegisterGuiLayersEvent event) {
        event.registerAboveAll(NuminaConstants.MODE_CHANGING_ICON_RENDERER, ModeChangingIconOverlay.MODE_CHANGING_ICON_OVERLAY);
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        // Add our own custom armor and elytra (TODO? jetpack maybe?) layer to the various player renderers
        for (PlayerSkin.Model skin : event.getSkins()) {
            //Note: We expect this to always be an instanceof PlayerRenderer, but we just bother checking if it is a LivingEntityRenderer
            // additional note: this should also apply to most humanoid mobs
            if (event.getSkin(skin) instanceof LivingEntityRenderer<?, ?> renderer) {
                addCustomLayers(EntityType.PLAYER, renderer, event.getContext());
            }
        }

        //        //Add our own custom armor and elytra layer to everything that has an armor layer
        //        //Note: This includes any modded mobs that have vanilla's HumanoidArmorLayer or ElytraLayer added to them
        //        for (EntityType<?> entityType : event.getEntityTypes()) {
        //            if (event.getRenderer(entityType) instanceof LivingEntityRenderer<?, ?> renderer) {
        //                addCustomLayers(entityType, renderer, event.getContext());
        //            }
        //        }
    }

    private static <T extends LivingEntity, M extends EntityModel<T>> void addCustomLayers(@Nonnull EntityType<?> type,
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

    @SubscribeEvent
    public static void onAddAdditional(ModelEvent.RegisterAdditional e) {
        ModelBakeEventHandler.onAddAdditional(e);
    }



    //    public static void doClientStuff(final FMLClientSetupEvent event) {
    ////        NeoForge.EVENT_BUS.register(new NuminaFOVUpdateEventHandler());
    ////        NeoForge.EVENT_BUS.register(new ToolTipEvent());
    ////        event.enqueueWork(() -> {
    ////            MenuScreens.register(NuminaObjects.CHARGING_BASE_CONTAINER_TYPE.get(), ChargingBaseScreen::new);
    ////            MenuScreens.register(NuminaObjects.ARMOR_STAND_CONTAINER_TYPE.get(), ArmorStandScreen::new);
    ////            //        ScreenManager.func_216911_a(NuminaObjects.SCANNER_CONTAINER.get(), MPSGuiScanner::new);
    ////        });
    //
    ////        MinecraftForge.EVENT_BUS.addListener((InputEvent.Key e) -> {
    //////            ModelTransformCalibration.CALIBRATION.transformCalibration(e);
    ////        });
    //    }


    @SubscribeEvent
    public static void clientExtensionEvent(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
                               private final BlockEntityWithoutLevelRenderer renderer = new NuminaArmorStandItemRenderer();
                               @Override
                               public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                                   return renderer;
                               }
                           }, NuminaItems.ARMOR_STAND_ITEM.get()
        );
    }
}
