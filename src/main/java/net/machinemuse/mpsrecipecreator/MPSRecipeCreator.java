package net.machinemuse.mpsrecipecreator;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = MPSRecipeCreator.MODID,
        name = MPSRecipeCreator.NAME,
        version = MPSRecipeCreator.VERSION
)
public class MPSRecipeCreator {
    public static final String MODID = "mpsrecipecreator";
    public static final String NAME = "MPS-RecipeCreator";
    public static final String VERSION = "@VERSION@";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent preinit) {
        LOGGER.info("Hello, world!");
    }
}
