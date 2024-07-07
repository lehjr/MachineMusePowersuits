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

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import lehjr.numina.common.config.ConfigHelper;
import lehjr.powersuits.client.control.KeymappingKeyHandler;
import lehjr.powersuits.client.event.ModelBakeEventHandler;
import lehjr.powersuits.client.event.RenderEventHandler;
import lehjr.powersuits.client.gui.module.module.install_salvage.InstallSalvageGui;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.event.*;
import lehjr.powersuits.common.network.MPSPackets;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(MPSConstants.MOD_ID)
public class ModularPowersuits {
    public ModularPowersuits(IEventBus modEventBus) {
        // Config
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MPSSettings.CLIENT_SPEC, ConfigHelper.setupConfigFile("mps-client-only.toml", MPSConstants.MOD_ID).getAbsolutePath());
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, MPSSettings.SERVER_SPEC); // note config file location for dedicated server is stored in the world config

        // Register the setup method for modloading
        modEventBus.addListener(this::setup);

        MPSItems.MPS_ITEMS.register(modEventBus);
        MPSBlocks.BLOCKS.register(modEventBus);
        MPSBlocks.BLOCKENTITY_TYPES.register(modEventBus);
        MPSEntities.ENTITY_TYPES.register(modEventBus);
        MPSMenuTypes.CONTAINER_TYPES.register(modEventBus);
        MPSItems.register(modEventBus);


        // Register the doClientStuff method for modloading
        modEventBus.addListener(this::setupClient);
//
//        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(EntityDamageEvent::handleEntityDamageEvent); // FIXME: does nothing
        MinecraftForge.EVENT_BUS.addListener(EntityDamageEvent::entityAttackEventHandler); // FIXME: does nothing
        MinecraftForge.EVENT_BUS.register(new PlayerUpdateHandler());
        MinecraftForge.EVENT_BUS.register(MovementManager.INSTANCE);
////
        MinecraftForge.EVENT_BUS.addListener(HarvestEventHandler::handleHarvestCheck);
        MinecraftForge.EVENT_BUS.addListener(HarvestEventHandler::handleBreakSpeed);
        MPSSoundDictionary.MPS_SOUND_EVENTS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(new LoginLogoutEventHandler());

        // handles loading and reloading event
        modEventBus.addListener((ModConfigEvent event) -> {
            new RuntimeException("Got config " + event.getConfig() + " name " + event.getConfig().getModId() + ":" + event.getConfig().getFileName());

            final ModConfig config = event.getConfig();
            if (config.getSpec()!= null && config.getSpec() == MPSSettings.SERVER_SPEC) {
                MPSSettings.getModuleConfig().setServerConfig(config);

                // This is actually for a feature that isn't even currently enabled :P
                // getFullPath can't be used on client if it isn't also hosting the server
                if (config instanceof CommentedFileConfig) {
                    CosmeticPresetSaveLoad.setConfigDirString(config.getFullPath().getParent().toString());
                    CosmeticPresetSaveLoad.copyPresetsFromJar(config.getFullPath().getParent().toString());
                }
            }
        });
    }

    /**
     * Setup common (clien/server) stuff
     */
    private void setup(final FMLCommonSetupEvent event) {
        MPSPackets.registerMPAPackets();
    }

    /**
     * Setup client related stuff
     */
    private void setupClient(final FMLClientSetupEvent event) {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(ModelBakeEventHandler.INSTANCE::onModelBake);
        modEventBus.addListener(ModelBakeEventHandler.INSTANCE::onAddAdditional);
        modEventBus.addListener(RenderEventHandler.INSTANCE::preTextureStitch);

        MinecraftForge.EVENT_BUS.register(RenderEventHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(new KeymappingKeyHandler());

        MenuScreens.register(MPSMenuTypes.INSTALL_SALVAGE_MENU_TYPE.get(), InstallSalvageGui::new);

    }

    /**
     * Attach capabilities to a few existing items in order to use them as modules
     */
    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<ItemStack> event) {
        AttachCapabilityEventHandler.attachCapability(event);
    }
}