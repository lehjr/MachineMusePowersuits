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

package lehjr.powersuits.common.config;

import com.lehjr.numina.client.config.IMeterConfig;
import com.lehjr.numina.common.capabilities.module.powermodule.IConfig;
import com.lehjr.numina.common.config.ModuleConfig;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.math.MathUtils;
import com.lehjr.powersuits.client.config.ClientConfig;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

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



    /**
     * Server -------------------------------------------------------------------------------------
     */
    // General ------------------------------------------------------------------------------------
    public static double getMaxFlyingSpeed() {
        return SERVER_SPEC.isLoaded() ? SERVER_CONFIG.GENERAL_MAX_FLYING_SPEED.get() : 25.0;
    }

    public static double getMaxHeatPowerFist() {
        return SERVER_SPEC.isLoaded() ? SERVER_CONFIG.GENERAL_BASE_MAX_HEAT_POWERFIST.get() : 5.0D;
    }

    public static double getMaxHeatHelmet() {
        return SERVER_SPEC.isLoaded() ? SERVER_CONFIG.GENERAL_BASE_MAX_HEAT_HELMET.get() : 5.0D;
    }

    public static double getMaxHeatChestplate() {
        return SERVER_SPEC.isLoaded() ? SERVER_CONFIG.GENERAL_BASE_MAX_HEAT_CHEST.get() : 20.0D;
    }

    public static double getMaxHeatLegs() {
        return SERVER_SPEC.isLoaded() ? SERVER_CONFIG.GENERAL_BASE_MAX_HEAT_LEGS.get() : 15.0D;
    }

    public static double getMaxHeatBoots() {
        return SERVER_SPEC.isLoaded() ? SERVER_CONFIG.GENERAL_BASE_MAX_HEAT_FEET.get() : 15.0D;
    }

    // Cosmetic -----------------------------------------------------------------------------------
    public static boolean useLegacyCosmeticSystem() {
        return SERVER_SPEC.isLoaded() ? SERVER_CONFIG.COSMETIC_USE_LEGACY_COSMETIC_SYSTEM.get() : false;
    }

    public static boolean allowHighPollyArmor() {
        return SERVER_SPEC.isLoaded() ? SERVER_CONFIG.COSMETIC_ALLOW_HIGH_POLLY_ARMOR_MODELS.get() : true;
    }

    public static boolean allowPowerFistCustomization() {
        return SERVER_SPEC.isLoaded() ? SERVER_CONFIG.COSMETIC_ALLOW_POWER_FIST_CUSTOMIZATOIN.get() : true;
    }

    public static List<ResourceLocation> getOreList() {
        List<?> ores = SERVER_SPEC.isLoaded() ?
                SERVER_CONFIG.GENERAL_VEIN_MINER_ORE_LIST.get() : new ArrayList<>();
        List<ResourceLocation> retList = new ArrayList<>();
        ores.stream().filter(o->o instanceof String).map(Object::toString).forEach(ore-> {
            retList.add(ResourceLocation.fromNamespaceAndPath(ore));;
        });
        return retList;
    }

    public static List<ResourceLocation> getBlockList() {
        List<?> blocks = SERVER_SPEC.isLoaded() ?
                SERVER_CONFIG.GENERAL_VEIN_MINER_BLOCK_LIST.get() : new ArrayList<>();
        List<ResourceLocation> retList = new ArrayList<>();
        blocks.stream().filter(o->o instanceof String).map(Object::toString).forEach(block-> {
            retList.add(ResourceLocation.fromNamespaceAndPath(block));;
        });
        return retList;
    }

    /** Modules ----------------------------------------------------------------------------------- */
    static NonNullLazy<IConfig> moduleConfig = NonNullLazy.of(() ->new ModuleConfig(MPSConstants.MOD_ID));
    public static IConfig getModuleConfig() {
        return moduleConfig.get();
    }

    static float toFloat(double val) {
        return (float)val;
    }
}