package lehjr.powersuits.client.event;

import lehjr.powersuits.client.gui.module.install_salvage.InstallSalvageGui;
import lehjr.powersuits.client.overlay.MPSOverlay;
import lehjr.powersuits.client.render.block.TinkerTableBER;
import lehjr.powersuits.client.render.entity.LuxCapacitorEntityRenderer;
import lehjr.powersuits.client.render.entity.PlasmaBoltEntityRenderer;
import lehjr.powersuits.client.render.entity.SpinningBladeEntityRenderer;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.registration.MPSBlocks;
import lehjr.powersuits.common.registration.MPSEntities;
import lehjr.powersuits.common.registration.MPSMenuTypes;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event) {
//        event.registerEntityRenderer(MPSEntities.RAILGUN_BOLT_ENTITY_TYPE.get(), RailGunBoltRenderer::new);
        event.registerEntityRenderer(MPSEntities.LUX_CAPACITOR_ENTITY_TYPE.get(), LuxCapacitorEntityRenderer::new);
        event.registerEntityRenderer(MPSEntities.PLASMA_BALL_ENTITY_TYPE.get(), PlasmaBoltEntityRenderer::new);
        event.registerEntityRenderer(MPSEntities.SPINNING_BLADE_ENTITY_TYPE.get(), SpinningBladeEntityRenderer::new);
        event.registerBlockEntityRenderer(MPSBlocks.TINKER_TABLE_BLOCKENTITY_TYPE.get(), TinkerTableBER::new);
    }

    @SubscribeEvent
    public static void registerScreen(RegisterMenuScreensEvent event) {
        event.register(MPSMenuTypes.INSTALL_SALVAGE_MENU_TYPE.get(), InstallSalvageGui::new);
    }


    @SubscribeEvent
    public static void registerOverlay(RegisterGuiLayersEvent event) {
        event.registerAboveAll(new ResourceLocation(MPSConstants.MOD_ID, "keybinds"), MPSOverlay.MPS_KEYBIND_OVERLAY);
//        event.registerAboveAll("mps_hud", MPSOverlay.MPS_HUD);
    }

    @SubscribeEvent
    public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        ModelBakeEventHandler.INSTANCE.onModifyBakingResult(event);
    }
}
