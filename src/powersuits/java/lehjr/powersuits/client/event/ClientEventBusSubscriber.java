package lehjr.powersuits.client.event;

import lehjr.powersuits.client.control.KeyMappingReaderWriter;
import lehjr.powersuits.client.control.KeymappingKeyHandler;
import lehjr.powersuits.client.gui.overlay.MPSHud;
//import lehjr.powersuits.client.render.blockentity.LuxCapacitorBER;
import lehjr.powersuits.client.render.blockentity.TinkerTableBER;
import lehjr.powersuits.client.render.entity.LuxCapacitorEntityRenderer;
import lehjr.powersuits.client.render.entity.PlasmaBoltEntityRenderer;
import lehjr.powersuits.client.render.entity.RailGunBoltRenderer;
import lehjr.powersuits.client.render.entity.SpinningBladeEntityRenderer;
import lehjr.powersuits.common.base.MPSBlocks;
import lehjr.powersuits.common.base.MPSEntities;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;

@Mod.EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MPSEntities.RAILGUN_BOLT_ENTITY_TYPE.get(), RailGunBoltRenderer::new);
        event.registerEntityRenderer(MPSEntities.LUX_CAPACITOR_ENTITY_TYPE.get(), LuxCapacitorEntityRenderer::new);
        event.registerEntityRenderer(MPSEntities.PLASMA_BALL_ENTITY_TYPE.get(), PlasmaBoltEntityRenderer::new);
        event.registerEntityRenderer(MPSEntities.SPINNING_BLADE_ENTITY_TYPE.get(), SpinningBladeEntityRenderer::new);
        event.registerBlockEntityRenderer(MPSBlocks.TINKER_TABLE_BLOCKENTITY_TYPE.get(), TinkerTableBER::new);
    }

    @SubscribeEvent
    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        KeyMappingReaderWriter.INSTANCE.readInKeybinds(); // read existing keybindings
        KeymappingKeyHandler.loadKeyBindings(); // check for possible additional
        KeymappingKeyHandler.keyMappings.values().forEach(kb-> event.register(kb));
        Arrays.stream(KeymappingKeyHandler.keybindArray).forEach(kb->event.register(kb));
    }

    @SubscribeEvent
    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("mps_keybinds", MPSHud.KEYMAPING_HUD);
        event.registerAboveAll("mps_hud", MPSHud.MPS_HUD);
    }
}


