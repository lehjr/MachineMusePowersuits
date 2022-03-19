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

package lehjr.numina.config;

import lehjr.numina.constants.NuminaConstants;
import lehjr.numina.util.capabilities.module.powermodule.IConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.util.NonNullLazy;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

public class NuminaSettings {
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

    /** Client settings --------------------------------------------------------------------------- */
    public static boolean useFovFix() {
        return CLIENT_CONFIG != null ? CLIENT_CONFIG.USE_FOV_FIX.get() : true;
    }

    public static boolean useFovFixInPrincessMode() {
        return CLIENT_CONFIG != null ? CLIENT_CONFIG.USE_FOV_FIX.get() : true;
    }

    public static boolean useFovNormalize() {
        return CLIENT_CONFIG != null ? CLIENT_CONFIG.USE_FOV_NORMALIZE.get() : false;
    }

    public static boolean fovFixDefaultState() {
        return CLIENT_CONFIG != null ? CLIENT_CONFIG.FOV_FIX_DEAULT_STATE.get() : true;
    }

    public static boolean useSounds() {
        return CLIENT_CONFIG != null ? CLIENT_CONFIG.USE_SOUNDS.get() : true;
    }

    public static boolean enableDebugging() {
        return CLIENT_CONFIG != null ? CLIENT_CONFIG.DEBUGGING_INFO.get() : false;
    }

    /** Server Settings --------------------------------------------------------------------------- */
    public static int chargingBaseMaxPower() {
        return getActiveConfig().map(config-> config.ARMOR_STAND_MAX_POWER.get()).orElse(10000);
    }

    static Optional<ServerConfig> getActiveConfig() {
        return Optional.ofNullable(SERVER_SPEC.isLoaded() ? SERVER_CONFIG : null);
    }

    /** Modules ----------------------------------------------------------------------------------- */
//    private static volatile ModuleConfig moduleConfig;
    static NonNullLazy<IConfig> moduleConfig = NonNullLazy.of(() ->new ModuleConfig(NuminaConstants.MOD_ID));

    public static IConfig getModuleConfig() {
//        if (moduleConfig == null) {
//            synchronized (ModuleConfig.class) {
//                if (moduleConfig == null) {
//                    moduleConfig = new ModuleConfig(NuminaConstants.MOD_ID);
//                }
//            }
//        }
//        return moduleConfig;
        return moduleConfig.get();
    }
}