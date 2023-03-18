package lehjr.powersuits.client.event;

import lehjr.powersuits.client.render.blockentity.LuxCapacitorBER;
import lehjr.powersuits.client.render.blockentity.TinkerTableBER;
import lehjr.powersuits.client.render.entity.LuxCapacitorEntityRenderer;
import lehjr.powersuits.client.render.entity.PlasmaBoltEntityRenderer;
import lehjr.powersuits.client.render.entity.RailGunBoltRenderer;
import lehjr.powersuits.client.render.entity.SpinningBladeEntityRenderer;
import lehjr.powersuits.common.base.MPSObjects;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {


    @SubscribeEvent
    public static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MPSObjects.RAILGUN_BOLT_ENTITY_TYPE.get(), RailGunBoltRenderer::new);
        event.registerEntityRenderer(MPSObjects.LUX_CAPACITOR_ENTITY_TYPE.get(), LuxCapacitorEntityRenderer::new);
        event.registerEntityRenderer(MPSObjects.PLASMA_BALL_ENTITY_TYPE.get(), PlasmaBoltEntityRenderer::new);
        event.registerEntityRenderer(MPSObjects.SPINNING_BLADE_ENTITY_TYPE.get(), SpinningBladeEntityRenderer::new);
        event.registerBlockEntityRenderer(MPSObjects.LUX_CAP_BLOCK_ENTITY_TYPE.get(), LuxCapacitorBER::new);
        event.registerBlockEntityRenderer(MPSObjects.TINKER_TABLE_BLOCKENTITY_TYPE.get(), TinkerTableBER::new);
    }
}


