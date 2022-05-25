package com.lehjr.powersuits.client.event;


import com.lehjr.powersuits.client.render.entity.LuxCapacitorEntityRenderer;
import com.lehjr.powersuits.common.base.MPSObjects;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event) {


        event.registerEntityRenderer(MPSObjects.LUX_CAPACITOR_ENTITY_TYPE.get(), LuxCapacitorEntityRenderer::new);
    }
}
