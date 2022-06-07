package com.lehjr.powersuits.client.event;

import com.lehjr.powersuits.client.control.KeybindKeyHandler;
import com.lehjr.powersuits.client.gui.inventory.ModularItemInventoryScreen;
import com.lehjr.powersuits.client.render.entity.LuxCapacitorEntityRenderer;
import com.lehjr.powersuits.common.base.MPSObjects;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    public static final ClientSetup INSTANCE = new ClientSetup();

    @SubscribeEvent
    public static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event) {


        event.registerEntityRenderer(MPSObjects.LUX_CAPACITOR_ENTITY_TYPE.get(), LuxCapacitorEntityRenderer::new);
    }


    @SubscribeEvent
    public void preTextureStitch(TextureStitchEvent.Pre event) {
        ModelBakeEventHandler.loadArmorModels(event);
    }

    @SubscribeEvent
    public static void handleSetupEvent(final FMLClientSetupEvent event) {
        // register gui stuff here

//        RenderingRegistry.registerEntityRenderingHandler(MPSObjects.RAILGUN_BOLT_ENTITY_TYPE.get(), RailGunBoltRenderer::new);

//        RenderingRegistry.registerEntityRenderingHandler(MPSObjects.PLASMA_BALL_ENTITY_TYPE.get(), PlasmaBoltEntityRenderer::new);
//        RenderingRegistry.registerEntityRenderingHandler(MPSObjects.SPINNING_BLADE_ENTITY_TYPE.get(), SpinningBladeEntityRenderer::new);
        MenuScreens.register(MPSObjects.MODULAR_ITEM_INVENTORY_MENU_TYPE.get(), ModularItemInventoryScreen::new);
    }

    public static void miscClientReg() {
        MinecraftForge.EVENT_BUS.register(RenderEventHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(new KeybindKeyHandler());
    }


    public void clientStart(IEventBus modEventBus) {
        modEventBus.addListener(ModelBakeEventHandler.INSTANCE::onModelBake);
        modEventBus.addListener(this::preTextureStitch);
    }
}
