package com.lehjr.powersuits.client.event;

import com.lehjr.powersuits.client.control.KeybindKeyHandler;
import com.lehjr.powersuits.client.gui.inventory.ModularItemInventoryScreen;
import com.lehjr.powersuits.common.base.MPSObjects;
import com.mojang.blaze3d.platform.ScreenManager;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    @SubscribeEvent
    public static void handleSetupEvent(final FMLClientSetupEvent event) {
        // register gui stuff here

//        RenderingRegistry.registerEntityRenderingHandler(MPSObjects.RAILGUN_BOLT_ENTITY_TYPE.get(), RailGunBoltRenderer::new);
//        RenderingRegistry.registerEntityRenderingHandler(MPSObjects.LUX_CAPACITOR_ENTITY_TYPE.get(), LuxCapacitorEntityRenderer::new);
//        RenderingRegistry.registerEntityRenderingHandler(MPSObjects.PLASMA_BALL_ENTITY_TYPE.get(), PlasmaBoltEntityRenderer::new);
//        RenderingRegistry.registerEntityRenderingHandler(MPSObjects.SPINNING_BLADE_ENTITY_TYPE.get(), SpinningBladeEntityRenderer::new);
        MenuScreens.register(MPSObjects.MODULAR_ITEM_INVENTORY_MENU_TYPE.get(), ModularItemInventoryScreen::new);
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
