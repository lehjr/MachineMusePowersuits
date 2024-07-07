package lehjr.powersuits.client.event;

import lehjr.powersuits.client.control.KeyMappingReaderWriter;
import lehjr.powersuits.client.control.KeymappingKeyHandler;
import lehjr.powersuits.client.gui.overlay.MPSOverlay;
import lehjr.powersuits.client.render.blockentity.TinkerTableBER;
import lehjr.powersuits.client.render.entity.LuxCapacitorEntityRenderer;
import lehjr.powersuits.client.render.entity.PlasmaBoltEntityRenderer;
import lehjr.powersuits.client.render.entity.SpinningBladeEntityRenderer;
import lehjr.powersuits.common.base.MPSBlocks;
import lehjr.powersuits.common.base.MPSEntities;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.neoforged.api.distmarker.Dist;

import java.util.Arrays;





    @SubscribeEvent
    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
//        NuminaLogger.logDebug("keymappingevent loaded");
        KeyMappingReaderWriter.INSTANCE.readInKeybinds(); // read existing keybindings


        KeymappingKeyHandler.loadKeyBindings(); // check for possible additional
        KeymappingKeyHandler.keyMappings.values().forEach(kb-> event.register(kb)); // register keybinds
        Arrays.stream(KeymappingKeyHandler.keybindArray).forEach(kb->event.register(kb));








        MPSOverlay.makeKBDisplayList();
    }


}


