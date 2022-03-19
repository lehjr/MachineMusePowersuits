package lehjr.mpsrecipecreator.basemod.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final Settings COMMON_CONFIG;
    public static final ForgeConfigSpec COMMON_SPEC;

    public static final Settings SERVER_CONFIG;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        {
            final Pair<Settings, ForgeConfigSpec> serverConfig = new ForgeConfigSpec.Builder().configure(Settings::new);
            SERVER_SPEC = serverConfig.getRight();
            SERVER_CONFIG = serverConfig.getLeft();
        }
        {
            final Pair<Settings, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(Settings::new);
            COMMON_SPEC = serverSpecPair.getRight();
            COMMON_CONFIG = serverSpecPair.getLeft();
        }
    }

    public static Boolean overwriteRecipes() {
        if (SERVER_CONFIG != null) {
            return SERVER_CONFIG.OVERWRITE_RECIPES.get();
        } else {
            return COMMON_CONFIG.OVERWRITE_RECIPES.get();
        }
    }

    public static boolean allowOppedPlayersToCreateOnServer() {
        if (SERVER_CONFIG != null) {
            return SERVER_CONFIG.OPPED_PLAYERS_CAN_CREATE_ON_SERVER.get();
        } else {
            return COMMON_CONFIG.OPPED_PLAYERS_CAN_CREATE_ON_SERVER.get();
        }
    }

    public static int getOpLevelNeeded() {
        if (SERVER_CONFIG != null) {
            return SERVER_CONFIG.OP_LEVEL_REQUIRED.get();
        } else {
            return COMMON_CONFIG.OP_LEVEL_REQUIRED.get();
        }
    }
}
