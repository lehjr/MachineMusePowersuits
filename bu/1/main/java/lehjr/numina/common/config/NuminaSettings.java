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

package lehjr.numina.common.config;

import lehjr.numina.client.config.ClientConfig;
import lehjr.numina.client.config.IMeterConfig;
import lehjr.numina.common.capabilities.module.powermodule.IConfig;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.math.MathUtils;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.util.NonNullLazy;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

public class NuminaSettings {
    public static final ClientConfig CLIENT_CONFIG;
    public static final ModConfigSpec CLIENT_SPEC;

    public static final ServerConfig SERVER_CONFIG;
    public static final ModConfigSpec SERVER_SPEC;

    static {
        {
            final Pair<ClientConfig, ModConfigSpec> clientSpecPair = new ModConfigSpec.Builder().configure(ClientConfig::new);
            CLIENT_SPEC = clientSpecPair.getRight();
            CLIENT_CONFIG = clientSpecPair.getLeft();
        }
        {
            final Pair<ServerConfig, ModConfigSpec> serverSpecPair = new ModConfigSpec.Builder().configure(ServerConfig::new);
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

    public static IMeterConfig getEnergyMeterConfig() {
        return EnergyMeterConfig.INSTANCE;
    }

    public enum EnergyMeterConfig implements IMeterConfig {
        INSTANCE;

        @Override
        public float getDebugValue() {
            return (float) (0.01 * MathUtils.clampDouble(CLIENT_CONFIG.CHARGING_BASE_ENERGY_METER_DEBUG_VAL.get(), 0, 100));
        }

        @Override
        public Color getGlassColor() {
            float red = CLIENT_CONFIG.CHARGING_BASE_ENERGY_METER_GLASS_RED.get() * 0.01F;
            float green = CLIENT_CONFIG.CHARGING_BASE_ENERGY_METER_GLASS_GREEN.get() * 0.01F;
            float blue = CLIENT_CONFIG.CHARGING_BASE_ENERGY_METER_GLASS_BLUE.get() * 0.01F;
            float alpha = CLIENT_CONFIG.CHARGING_BASE_ENERGY_METER_GLASS_ALPHA.get() * 0.01F;

            //"#d9 43 ff 64" = 217, 67, 255, 100; = 85, 26, 100, 39



            return new Color(red, green, blue, alpha);
        }

        @Override
        public Color getBarColor() {
            float red = CLIENT_CONFIG.CHARGING_BASE_ENERGY_METER_BAR_RED.get() * 0.01F;
            float green = CLIENT_CONFIG.CHARGING_BASE_ENERGY_METER_BAR_GREEN.get() * 0.01F;
            float blue = CLIENT_CONFIG.CHARGING_BASE_ENERGY_METER_BAR_BLUE.get() * 0.01F;
            float alpha = CLIENT_CONFIG.CHARGING_BASE_ENERGY_METER_BAR_ALPHA.get() * 0.01F;

            return new Color(red, green, blue, alpha);
        }
    }
    /** Development ------------------------------------------------------------------------------- */

    public static boolean enableDebugging() {
        return CLIENT_CONFIG != null ? CLIENT_CONFIG.DEBUGGING_INFO.get() : false;
    }




}