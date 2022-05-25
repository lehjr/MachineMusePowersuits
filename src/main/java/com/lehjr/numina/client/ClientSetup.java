package com.lehjr.numina.client;

import com.lehjr.numina.client.gui.GuiIcon;
import com.lehjr.numina.client.gui.IconUtils;
import com.lehjr.numina.client.model.obj.forge.NuminaOBJLoader;
import com.lehjr.numina.client.render.NuminaSpriteUploader;
import com.lehjr.numina.client.screen.ArmorStandScreen;
import com.lehjr.numina.client.screen.ChargingBaseScreen;
import com.lehjr.numina.common.base.Numina;
import com.lehjr.numina.common.base.NuminaObjects;
import com.lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@OnlyIn(Dist.CLIENT)
public final class ClientSetup {
    @SubscribeEvent
    public static void handleSetupEvent(final FMLClientSetupEvent event) {
        MenuScreens.register(NuminaObjects.CHARGING_BASE_CONTAINER_TYPE.get(), ChargingBaseScreen::new);
        MenuScreens.register(NuminaObjects.ARMOR_STAND_CONTAINER_TYPE.get(), ArmorStandScreen::new);
    }

    // Ripped from JEI
    public static void clientStart(IEventBus modEventBus) {
        if (Minecraft.getInstance() != null) {
            ModelLoaderRegistry.registerLoader(new ResourceLocation(NuminaConstants.MOD_ID, "obj"), NuminaOBJLoader.INSTANCE); // crashes if called in mod constructor
        }

        EventBusHelper.addListener(Numina.class, modEventBus, ColorHandlerEvent.Block.class, setupEvent -> {
            NuminaSpriteUploader iconUploader = new NuminaSpriteUploader();
            GuiIcon icons = new GuiIcon(iconUploader);
            ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
            if (resourceManager instanceof ReloadableResourceManager) {
                ReloadableResourceManager reloadableResourceManager = (ReloadableResourceManager) resourceManager;
                reloadableResourceManager.registerReloadListener(iconUploader);
            }
            EventBusHelper.addLifecycleListener(Numina.class, modEventBus, FMLLoadCompleteEvent.class, loadCompleteEvent ->
                    IconUtils.setIconInstance(icons));
        });
    }

    public static void miscClientReg() {
    }



    private void onClientSetup(FMLClientSetupEvent event) {
//        MinecraftForge.EVENT_BUS.register(new FOVUpdateEventHandler());
//        MinecraftForge.EVENT_BUS.register(new RenderGameOverlayEventHandler());
//
////        MinecraftForge.EVENT_BUS.register(new LogoutEventHandler());
//
//        MinecraftForge.EVENT_BUS.register(new ToolTipEvent());

//
//        ScreenManager.register(NuminaObjects.ARMOR_STAND_CONTAINER_TYPE.get(), ArmorStandGui::new);
//
//        ScreenManager.register(NuminaObjects.SCANNER_CONTAINER.get(), MPSGuiScanner::new);
    }

}
