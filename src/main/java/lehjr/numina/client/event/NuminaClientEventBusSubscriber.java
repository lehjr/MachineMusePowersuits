package lehjr.numina.client.event;

import lehjr.numina.client.gui.NuminaIcons;
import lehjr.numina.client.model.helper.ModelLayerHelper;
import lehjr.numina.client.overlay.ModeChangingIconOverlay;
import lehjr.numina.client.render.entity.NuminaArmorStandRenderer;
import lehjr.numina.client.render.item.NuminaArmorStandItemRenderer;
import lehjr.numina.client.screen.ArmorStandScreen;
import lehjr.numina.client.screen.ChargingBaseScreen;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.registration.NuminaEntities;
import lehjr.numina.common.registration.NuminaItems;
import lehjr.numina.common.registration.NuminaMenus;
import lehjr.numina.common.utils.IconUtils;
import net.forge.client.NuminaObjLoader;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;

@EventBusSubscriber(modid = NuminaConstants.MOD_ID, value = Dist.CLIENT)
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
        NuminaLogger.logDebug("adding layers");

        // Add our own custom armor and elytra (TODO? jetpack maybe?) layer to the various player renderers
        for (PlayerSkin.Model skin : event.getSkins()) {
            //Note: We expect this to always be an instanceof PlayerRenderer, but we just bother checking if it is a LivingEntityRenderer
            // additional note: this should also apply to most humanoid mobs
            if (event.getSkin(skin) instanceof LivingEntityRenderer<?, ?> renderer) {
                ModelLayerHelper.addCustomLayers(EntityType.PLAYER, renderer, event.getContext());
            }
        }
    }

    @SubscribeEvent
    public static void onAddAdditional(ModelEvent.RegisterAdditional e) {
        ModelBakeEventHandler.onAddAdditional(e);
    }

    @SubscribeEvent
    public static void doClientStuff(final FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.register(NuminaFOVUpdateEventHandler.class);
    }

    //
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
