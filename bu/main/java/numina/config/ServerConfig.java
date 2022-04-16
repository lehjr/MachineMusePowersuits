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
import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
        public static ForgeConfigSpec.IntValue ARMOR_STAND_MAX_POWER;

        public ServerConfig(ForgeConfigSpec.Builder builder) {
                builder.comment("General settings").push("General");
                {
                        ARMOR_STAND_MAX_POWER = builder
                                .comment("Ignore speed boosts for field of view")
                                .translation(NuminaConstants.CONFIG_USE_FOV_FIX)
                                .defineInRange("armorStandMaxPower", 1000000, 100, 10000000);
                }
                builder.pop();

                builder.push("Modules");
                builder.push("Energy Storage");
                {
                        builder.push("battery_basic");
                        builder.defineInRange("base_maxEnergy", 1000000.0D, 0, 1.7976931348623157E308);
                        builder.define("isAllowed", true);
                        builder.defineInRange("base_maxTransfer", 1000000.0D, 0, 1.7976931348623157E308);
                        builder.pop();
                }
                {
                        builder.push("battery_advanced");
                        builder.defineInRange("base_maxEnergy", 5000000.0D, 0, 1.7976931348623157E308);
                        builder.define("isAllowed", true);
                        builder.defineInRange("base_maxTransfer", 5000000.0D, 0, 1.7976931348623157E308);
                        builder.pop();
                }
                {
                        builder.push("battery_elite");
                        builder.defineInRange("base_maxEnergy", 5.0E7D, 0, 1.7976931348623157E308);
                        builder.define("isAllowed", true);
                        builder.defineInRange("base_maxTransfer", 5.0E7D, 0, 1.7976931348623157E308);
                        builder.pop();
                }
                {
                        builder.push("battery_ultimate");
                        builder.defineInRange("base_maxEnergy", 1.0E8D, 0, 1.7976931348623157E308);
                        builder.define("isAllowed", true);
                        builder.defineInRange("base_maxTransfer", 1.0E8D, 0, 1.7976931348623157E308);
                        builder.pop();
                }
                builder.pop();
                builder.pop();
        }
}