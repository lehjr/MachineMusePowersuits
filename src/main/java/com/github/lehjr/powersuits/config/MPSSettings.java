package com.github.lehjr.powersuits.config;

import com.github.lehjr.numina.config.ModuleConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.powersuits.constants.MPSConstants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class MPSSettings {
    public static final ClientConfig CLIENT_CONFIG;
    public static final ForgeConfigSpec CLIENT_SPEC;

    public static final ServerConfig SERVER_CONFIG;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        {
            final Pair<ClientConfig, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
            CLIENT_SPEC = clientSpecPair.getRight();
            CLIENT_CONFIG = clientSpecPair.getLeft();
        }
        {
            final Pair<ServerConfig, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
            SERVER_SPEC = serverSpecPair.getRight();
            SERVER_CONFIG = serverSpecPair.getLeft();
        }
    }

    /** Client ------------------------------------------------------------------------------------ */
    // HUD ---------------------------------------------------------------------------------------
    public static boolean useGraphicalMeters() {
        return CLIENT_CONFIG != null ? CLIENT_CONFIG.HUD_USE_GRAPHICAL_METERS.get() : false;
    }

    public static boolean displayHud() {
        return CLIENT_CONFIG != null ? CLIENT_CONFIG.HUD_DISPLAY_HUD.get() : false;
    }

    public static boolean use24HourClock() {
        return CLIENT_CONFIG != null ? CLIENT_CONFIG.HUD_USE_24_HOUR_CLOCK.get() : false;
    }

    public static float getHudKeybindX() {
        return CLIENT_CONFIG != null ? toFloat(CLIENT_CONFIG.HUD_KEYBIND_X.get()) : 8.0F;
    }

    public static float getHudKeybindY() {
        return CLIENT_CONFIG != null ? toFloat(CLIENT_CONFIG.HUD_KEYBIND_Y.get()) : 32.0F;
    }

    // General -----------------------------------------------------------------------------------
    public static boolean allowConfictingKeyBinds() {
        return CLIENT_CONFIG != null ? CLIENT_CONFIG.GENERAL_ALLOW_CONFLICTING_KEYBINDS.get() : true;
    }

    /**
     * Server -------------------------------------------------------------------------------------
     */
    // Recipes ------------------------------------------------------------------------------------
    public static boolean useVanillaRecipes() {
        return SERVER_CONFIG != null ? SERVER_CONFIG.RECIPES_USE_VANILLA.get() : false;
    }

    // General ------------------------------------------------------------------------------------
    public static double getMaxFlyingSpeed() {
        return SERVER_CONFIG != null ? SERVER_CONFIG.GENERAL_MAX_FLYING_SPEED.get() : 25.0;
    }

    public static double getMaxHeatPowerFist() {
        return SERVER_CONFIG != null ? SERVER_CONFIG.GENERAL_BASE_MAX_HEAT_POWERFIST.get() : 5.0D;
    }

    public static double getMaxHeatHelmet() {
        return SERVER_CONFIG != null ? SERVER_CONFIG.GENERAL_BASE_MAX_HEAT_HELMET.get() : 5.0D;
    }

    public static double getMaxHeatChestplate() {
        return SERVER_CONFIG != null ? SERVER_CONFIG.GENERAL_BASE_MAX_HEAT_CHEST.get() : 20.0D;
    }

    public static double getMaxHeatLegs() {
        return SERVER_CONFIG != null ? SERVER_CONFIG.GENERAL_BASE_MAX_HEAT_LEGS.get() : 15.0D;
    }

    public static double getMaxHeatBoots() {
        return SERVER_CONFIG != null ? SERVER_CONFIG.GENERAL_BASE_MAX_HEAT_FEET.get() : 15.0D;
    }

    // Cosmetic -----------------------------------------------------------------------------------
    public static boolean useLegacyCosmeticSystem() {
        return SERVER_CONFIG != null ? SERVER_CONFIG.COSMETIC_USE_LEGACY_COSMETIC_SYSTEM.get() : false;
    }

    public static boolean allowHighPollyArmor() {
        return SERVER_CONFIG != null ? SERVER_CONFIG.COSMETIC_ALLOW_HIGH_POLLY_ARMOR_MODELS.get() : true;
    }

    public static boolean allowPowerFistCustomization() {
        return SERVER_CONFIG != null ? SERVER_CONFIG.COSMETIC_ALLOW_POWER_FIST_CUSTOMIZATOIN.get() : true;
    }

    public static List<ResourceLocation> getOreList() {
        List<String> ores = SERVER_CONFIG != null ?
                (List<String>) SERVER_CONFIG.GENERAL_VEIN_MINER_ORE_LIST.get() : new ArrayList<>();
        List<ResourceLocation> retList = new ArrayList<>();
        ores.forEach(ore-> {
            retList.add(new ResourceLocation(ore));;
        });
        return retList;
    }

    public static List<ResourceLocation> getBlockList() {
        List<String> blocks = SERVER_CONFIG != null ?
                (List<String>) SERVER_CONFIG.GENERAL_VEIN_MINER_BLOCK_LIST.get() : new ArrayList<>();
        List<ResourceLocation> retList = new ArrayList<>();
        blocks.forEach(block-> {
            retList.add(new ResourceLocation(block));;
        });
        return retList;
    }

    /** Modules ----------------------------------------------------------------------------------- */
    private static volatile ModuleConfig moduleConfig;
    public static IConfig getModuleConfig() {
        if (moduleConfig == null) {
            synchronized (ModuleConfig.class) {
                if (moduleConfig == null) {
                    moduleConfig = new ModuleConfig(MPSConstants.MOD_ID);
                }
            }
        }
        return moduleConfig;
    }

    static float toFloat(double val) {
        return (float)val;
    }
}