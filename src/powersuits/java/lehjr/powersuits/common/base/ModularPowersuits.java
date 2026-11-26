/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.common.base;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.event.AttachCapabilityEventHandler;
import lehjr.powersuits.common.event.EntityDamageEvent;
import lehjr.powersuits.common.event.MovementManager;
import lehjr.powersuits.common.event.PlayerEventHandler;
import lehjr.powersuits.common.network.MPSPackets;
import lehjr.powersuits.common.registration.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MPSConstants.MOD_ID)
public class ModularPowersuits {
    public ModularPowersuits() {
        NuminaLogger.logDebug("Loaded ModularPowersuits!!!!");


        ModLoadingContext modContainer = ModLoadingContext.get();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        modEventBus.addListener(this::setup);
        MPSItems.MPS_ITEMS.register(modEventBus);
        MPSItems.MPS_CREATIVE_MODE_TAB.register(modEventBus);
        MPSBlocks.MPS_BLOCKS.register(modEventBus);
        MPSBlocks.MPS_BLOCKENTITY_TYPES.register(modEventBus);
        MPSEntities.MPS_ENTITY_TYPES.register(modEventBus);
        MPSMenuTypes.MPS_MENU_TYPES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(MovementManager.INSTANCE);

        MPSSoundDictionary.MPS_SOUND_EVENTS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(PlayerEventHandler.class);


        // Config
//        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MPSSettings.CLIENT_SPEC, ConfigHelper.setupConfigFile("mps-client-only.toml", MPSConstants.MOD_ID).getAbsolutePath());
//        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, MPSSettings.SERVER_SPEC); // note config file location for dedicated server is stored in the world config








        // Register the doClientStuff method for modloading
//        modEventBus.addListener(this::setupClient);
//
//        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.addListener(EntityDamageEvent::handleEntityDamageEvent); // FIXME: does nothing
        MinecraftForge.EVENT_BUS.addListener(EntityDamageEvent::entityAttackEventHandler); // FIXME: does nothing


        MPSConfigurations.setup(ModLoadingContext.get());
    }

    /**
     * Setup common (clien/server) stuff
     */
    private void setup(final FMLCommonSetupEvent event) {
        NuminaLogger.logDebug("Loaded ModularPowersuits setup!!!!");

        MPSPackets.registerMPAPackets();
    }



    /**
     * Attach capabilities to a few existing items in order to use them as modules
     */
    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<ItemStack> event) {
        AttachCapabilityEventHandler.attachCapability(event);
    }
}