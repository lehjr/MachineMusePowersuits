package com.lehjr.numina.client;

import com.lehjr.numina.api.gui.GuiIcon;
import com.lehjr.numina.api.gui.IconUtils;
import com.lehjr.numina.api.render.NuminaSpriteUploader;
import com.lehjr.numina.client.screen.ArmorStandScreen;
import com.lehjr.numina.client.screen.ChargingBaseScreen;
import com.lehjr.numina.common.Numina;
import com.lehjr.numina.common.NuminaObjects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
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
