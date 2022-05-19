package com.lehjr.powersuits.client.event;

import com.lehjr.powersuits.client.control.KeybindKeyHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    @SubscribeEvent
    public static void handleSetupEvent(final FMLClientSetupEvent event) {
        // register gui stuff here

    }

    public static void miscClientReg() {
        MinecraftForge.EVENT_BUS.register(RenderEventHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
        MinecraftForge.EVENT_BUS.register(new KeybindKeyHandler());
    }


    public static void clientStart(IEventBus modEventBus) {




//        EventBusHelper.addListener(Numina.class, modEventBus, ColorHandlerEvent.Block.class, setupEvent -> {
//            NuminaSpriteUploader iconUploader = new NuminaSpriteUploader();
//            GuiIcon icons = new GuiIcon(iconUploader);
//            ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
//            if (resourceManager instanceof ReloadableResourceManager) {
//                ReloadableResourceManager reloadableResourceManager = (ReloadableResourceManager) resourceManager;
//                reloadableResourceManager.registerReloadListener(iconUploader);
//            }
//            EventBusHelper.addLifecycleListener(Numina.class, modEventBus, FMLLoadCompleteEvent.class, loadCompleteEvent ->
//                    IconUtils.setIconInstance(icons));
//        });
    }

}
