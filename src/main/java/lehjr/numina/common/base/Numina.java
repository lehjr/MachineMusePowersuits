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

package lehjr.numina.common.base;

import lehjr.numina.client.config.NuminaClientConfig;
import lehjr.numina.client.sound.SoundDictionary;
import lehjr.numina.common.capabilities.player.keystates.PlayerKeyStateWrapper;
import lehjr.numina.common.config.ConfigHelper;
import lehjr.numina.common.config.NuminaCommonConfig;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.event.HarvestEventHandler;
import lehjr.numina.common.event.PlayerUpdateHandler;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.registration.RecipeSerializersRegistry;
import lehjr.numina.common.registration.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NuminaConstants.MOD_ID)
public class Numina {
    public Numina(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        NuminaItems.NUMINA_ITEMS.register(modEventBus);
        NuminaItems.CREATIVE_MODE_TABS.register(modEventBus);

        NuminaBlocks.NUMINA_BLOCKS.register(modEventBus);
        NuminaBlockEntities.BLOCKENTITY_TYPES.register(modEventBus);
        NuminaEntities.ENTITY_TYPES.register(modEventBus);

        NuminaMenus.MENU_TYPES.register(modEventBus);

        // Register the setup method for modloading
        modEventBus.addListener(this::setup);

        modEventBus.addListener(NuminaEntities::addEntityAttributes);
        modEventBus.addListener(NuminaCapabilities::registerCapabilities);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        SoundDictionary.NUMINA_SOUND_EVENTS.register(modEventBus);

        RecipeSerializersRegistry.RECIPE_SERIALIZERS.register(modEventBus);

        MinecraftForge.EVENT_BUS.addListener(HarvestEventHandler::handleHarvestCheck);
        MinecraftForge.EVENT_BUS.addListener(HarvestEventHandler::handleBreakSpeed);

        context.registerConfig(ModConfig.Type.CLIENT, NuminaClientConfig.CLIENT_SPEC, ConfigHelper.setupConfigFile("numina-client-only.toml", NuminaConstants.MOD_ID).getAbsolutePath());
        context.registerConfig(ModConfig.Type.COMMON, NuminaCommonConfig.COMMON_SPEC, ConfigHelper.setupConfigFile("numina-common.toml", NuminaConstants.MOD_ID).getAbsolutePath());
    }

    private void setup(final FMLCommonSetupEvent event) {
        NuminaPackets.registerNuminaPackets();
        NuminaIngredientTypes.regIngredients(event);
        MinecraftForge.EVENT_BUS.register(new PlayerUpdateHandler());
    }

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player) || event.getObject() == null) {
            return;
        }
        event.addCapability(ResourceLocation.tryBuild(NuminaConstants.MOD_ID, "player_keystates1"), new PlayerKeyStateWrapper((Player) event.getObject()));
    }
}
