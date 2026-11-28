package lehjr.powersuits.common.base;

import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.event.MovementManager;
import lehjr.powersuits.common.event.PlayerEventHandler;
import lehjr.powersuits.common.network.MPSPackets;
import lehjr.powersuits.common.registration.MPSArmorMaterial;
import lehjr.powersuits.common.registration.MPSBlocks;
import lehjr.powersuits.common.registration.MPSCapabilities;
import lehjr.powersuits.common.registration.MPSConfigurations;
import lehjr.powersuits.common.registration.MPSEntities;
import lehjr.powersuits.common.registration.MPSItems;
import lehjr.powersuits.common.registration.MPSMenuTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MPSConstants.MOD_ID)
public class ModularPowersuits {

    public ModularPowersuits(IEventBus modEventBus, ModContainer modContainer) {
        MPSItems.MPS_ITEMS.register(modEventBus);
        MPSItems.MPS_CREATIVE_MODE_TAB.register(modEventBus);
        MPSBlocks.MPS_BLOCKS.register(modEventBus);
        MPSBlocks.MPS_BLOCKENTITY_TYPES.register(modEventBus);
        MPSEntities.MPS_ENTITY_TYPES.register(modEventBus);
        MPSMenuTypes.MPS_MENU_TYPES.register(modEventBus);
        MPSArmorMaterial.ARMOR_MATERIALS.register(modEventBus);

        NeoForge.EVENT_BUS.register(MovementManager.INSTANCE);

        MPSSoundDictionary.MPS_SOUND_EVENTS.register(modEventBus);

        NeoForge.EVENT_BUS.register(PlayerEventHandler.class);
//        NeoForge.EVENT_BUS.register(ItemEvent::onAttributeEvent);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
//        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(MPSPackets::register);
        modEventBus.addListener(MPSCapabilities::registerCapabilities);

        // Register all the configuration files
        MPSConfigurations.setup(modContainer);
    }
}
