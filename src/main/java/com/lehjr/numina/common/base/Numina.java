package com.lehjr.numina.common.base;

import com.lehjr.numina.common.capabilities.heat.CapabilityHeat;
import com.lehjr.numina.common.capabilities.module.powermodule.CapabilityPowerModule;
import com.lehjr.numina.common.capabilities.player.CapabilityPlayerKeyStates;
import com.lehjr.numina.common.capabilities.render.CapabilityModelSpec;
import com.lehjr.numina.common.capabilities.render.chameleon.ChameleonCapability;
import com.lehjr.numina.common.capabilities.render.color.CapabilityColor;
import com.lehjr.numina.common.capabilities.render.highlight.HighLightCapability;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.client.event.ClientSetup;
import com.lehjr.numina.client.event.RenderEventHandler;
import com.lehjr.numina.common.network.NuminaPackets;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NuminaConstants.MOD_ID)
public class Numina {
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Numina() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        modEventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        modEventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        modEventBus.addListener(this::processIMC);


        NuminaObjects.ITEMS.register(modEventBus);
        NuminaObjects.BLOCKS.register(modEventBus);
        NuminaObjects.BLOCK_ENTITY_TYPES.register(modEventBus);
        NuminaObjects.ENTITY_TYPES.register(modEventBus);
        NuminaObjects.MENU_TYPES.register(modEventBus);


        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().register(ClientSetup.class));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()-> ()-> ClientSetup.clientStart(modEventBus));

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);


        if (FMLEnvironment.dist == Dist.CLIENT) {
            MinecraftForge.EVENT_BUS.addListener(RenderEventHandler.INSTANCE::onRenderTickEvent);
            MinecraftForge.EVENT_BUS.addListener(RenderEventHandler.INSTANCE::onPostRenderGameOverlayEvent);
//            MinecraftForge.EVENT_BUS.addListener(this::keyInputEvent);
        }
    }

    @SubscribeEvent
    public static void initialize(final RegisterCapabilitiesEvent event) {

        CapabilityHeat.register(event);


        CapabilityColor.register(event);

        // Modules
        CapabilityPowerModule.register(event);
        CapabilityModelSpec.register(event);
        HighLightCapability.register(event);
        ChameleonCapability.register(event);

        // Player
        CapabilityPlayerKeyStates.register(event);
    }

    private void setup(final FMLCommonSetupEvent event) {
        NuminaPackets.registerNuminaPackets();


        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // Some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }



    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // Register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}