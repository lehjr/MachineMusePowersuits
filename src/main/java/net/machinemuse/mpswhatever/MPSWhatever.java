package net.machinemuse.mpswhatever;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = MPSWhatever.MODID,
        name = MPSWhatever.NAME,
        version = MPSWhatever.VERSION
)
public class MPSWhatever {
    public static final String MODID = "mpswhatever";
    public static final String NAME = "MPS-Whatever";
    public static final String VERSION = "@VERSION@";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent preinit) {
        LOGGER.info("Hello, world!");
    }
}
