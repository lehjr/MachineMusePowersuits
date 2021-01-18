package com.github.lehjr.numina.config;

import com.github.lehjr.numina.constants.NuminaConstants;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    public ForgeConfigSpec.BooleanValue
            USE_FOV_FIX,
            USE_FOV_NORMALIZE,
            FOV_FIX_DEAULT_STATE,
            USE_SOUNDS,
            DEBUGGING_INFO;

    ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("General settings").push("General");

        USE_FOV_FIX = builder
                .comment("Ignore speed boosts for field of view")
                .translation(NuminaConstants.CONFIG_USE_FOV_FIX)
                .define("useFOVFix", true);

        USE_FOV_NORMALIZE = builder
                .comment("Use FOV Fix to normalize FOV changes")
                .translation(NuminaConstants.CONFIG_USE_FOV_NORMALIZE)
                .define("useFOVNormalize", true);


        FOV_FIX_DEAULT_STATE = builder
                .comment("Default state of FOVfix on login (enabled = true, disabled = false)")
                .translation(NuminaConstants.CONFIG_FOV_FIX_DEAULT_STATE)
                .define("fovFixDefaultState", true);

        USE_SOUNDS = builder
                .comment("Use sounds")
                .translation(NuminaConstants.CONFIG_USE_SOUNDS)
                .define("useSounds", true);

        DEBUGGING_INFO = builder
                .comment("Debugging info")
                .translation(NuminaConstants.CONFIG_DEBUGGING_INFO)
                .define("useSounds", true);

        builder.pop().comment("Tiers per RF/FE").push("Tiers");
    }
}
