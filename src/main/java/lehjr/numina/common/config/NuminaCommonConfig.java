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

import lehjr.numina.common.constants.NuminaConstants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = NuminaConstants.MOD_ID)
public class NuminaCommonConfig {
        /**
         * Charging Base ------------------------------------------------------------------------------
         */
        private static final ForgeConfigSpec.Builder CHARGING_BASE_BUILDER = new ForgeConfigSpec.Builder().push("ChargingBase settings");

        private static final ForgeConfigSpec.IntValue CHARGING_BASE_MAX_ENERGY = CHARGING_BASE_BUILDER
                .comment("Max base power for charging base")
                .defineInRange("chargingBaseMaxEnergy", 1000000, 100, 10000000);

        private static final ForgeConfigSpec.IntValue CHARGING_BASE_MAX_TRANSFER = CHARGING_BASE_BUILDER
                .comment("Max power transfer for charging base")
                .defineInRange("chargingBaseMaxTransfer", 10000, 10, 1000000);

        /**
         * Batteries ----------------------------------------------------------------------------------
         */

        // Basic
        private static final ForgeConfigSpec.Builder BATTERIES_BUILDER1 = CHARGING_BASE_BUILDER.pop().push("Batteries").push(NuminaConstants.MODULE_BATTERY_1__REGNAME);
        private static final ForgeConfigSpec.IntValue BATTERY_1__MAX_ENERGY = BATTERIES_BUILDER1.defineInRange(NuminaConstants.CONFIG_MAX_ENERGY, 1000000, 0, Integer.MAX_VALUE);
        private static final ForgeConfigSpec.IntValue BATTERY_1__MAX_TRANSFER = BATTERIES_BUILDER1.defineInRange(NuminaConstants.CONFIG_MAX_TRANSFER, 1250000, 0, Integer.MAX_VALUE);
        private static final ForgeConfigSpec.BooleanValue BATTERY_1__IS_ALLOWED = BATTERIES_BUILDER1.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

        // Advanced
        private static final ForgeConfigSpec.Builder BATTERIES_BUILDER2 = BATTERIES_BUILDER1.pop().push(NuminaConstants.MODULE_BATTERY_2__REGNAME);
        private static final ForgeConfigSpec.IntValue BATTERY_2__MAX_ENERGY = BATTERIES_BUILDER2.defineInRange(NuminaConstants.CONFIG_MAX_ENERGY, 10000000, 0, Integer.MAX_VALUE);
        private static final ForgeConfigSpec.IntValue BATTERY_2__MAX_TRANSFER = BATTERIES_BUILDER2.defineInRange(NuminaConstants.CONFIG_MAX_TRANSFER, 1250000, 0, Integer.MAX_VALUE);
        private static final ForgeConfigSpec.BooleanValue BATTERY_2__IS_ALLOWED = BATTERIES_BUILDER2.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

        // Elite
        private static final ForgeConfigSpec.Builder BATTERIES_BUILDER3 = BATTERIES_BUILDER2.pop().push(NuminaConstants.MODULE_BATTERY_3__REGNAME);
        private static final ForgeConfigSpec.IntValue BATTERY_ELITE__MAX_ENERGY = BATTERIES_BUILDER3.defineInRange(NuminaConstants.CONFIG_MAX_ENERGY, 100000000, 0, Integer.MAX_VALUE);
        private static final ForgeConfigSpec.IntValue BATTERY_ELITE__MAX_TRANSFER = BATTERIES_BUILDER3.defineInRange(NuminaConstants.CONFIG_MAX_TRANSFER, 1250000, 0, Integer.MAX_VALUE);
        private static final ForgeConfigSpec.BooleanValue BATTERY_ELITE__IS_ALLOWED = BATTERIES_BUILDER3.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

        // Ultimate
        private static final ForgeConfigSpec.Builder BATTERIES_BUILDER4 = BATTERIES_BUILDER3.pop().push(NuminaConstants.MODULE_BATTERY_4__REGNAME);
        private static final ForgeConfigSpec.IntValue BATTERY_4__MAX_ENERGY = BATTERIES_BUILDER4.defineInRange(NuminaConstants.CONFIG_MAX_ENERGY, 1000000000, 0, Integer.MAX_VALUE);
        private static final ForgeConfigSpec.IntValue BATTERY_4__MAX_TRANSFER = BATTERIES_BUILDER4.defineInRange(NuminaConstants.CONFIG_MAX_TRANSFER, 1250000, 0, Integer.MAX_VALUE);
        private static final ForgeConfigSpec.BooleanValue BATTERY_4__IS_ALLOWED = BATTERIES_BUILDER4.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

        private static final ForgeConfigSpec.Builder KEEP_MODULAR_ITEMS_ON_DEATH_BUILDER = BATTERIES_BUILDER4.pop().pop().push("PlayerDeath");
        private static final ForgeConfigSpec.BooleanValue KEEP_MODULAR_ITEMS_ON_DEATH = KEEP_MODULAR_ITEMS_ON_DEATH_BUILDER.define(NuminaConstants.KEEP_MODULAR_ITEMS_ON_DEATH, true);


        private static final ForgeConfigSpec.Builder BUILDER = KEEP_MODULAR_ITEMS_ON_DEATH_BUILDER.pop();

        public static final ForgeConfigSpec COMMON_SPEC = BUILDER.build();


        public static int chargingBaseMaxEnergy;
        public static int chargingBaseMaxTransfer;


        public static int batteryMaxEnergy1;
        public static int batteryMaxTransfer1;
        public static boolean batteryIsAllowed1;

        public static int batteryMaxEnergy2;
        public static int batteryMaxTransfer2;
        public static boolean batteryIsAllowed2;

        public static int batteryMaxEnergy3;
        public static int batteryMaxTransfer3;
        public static boolean batteryIsAllowed3;

        public static int batteryMaxEnergy4;
        public static int batteryMaxTransfer4;
        public static boolean batteryIsAllowed4;

        public static boolean keepModularItemsOnDeath;

        @SubscribeEvent
        static void onLoad(final ModConfigEvent event) {
                if (event.getConfig().getSpec() == COMMON_SPEC) {
                        chargingBaseMaxEnergy = CHARGING_BASE_MAX_ENERGY.get();
                        chargingBaseMaxTransfer = CHARGING_BASE_MAX_TRANSFER.get();

                        batteryMaxEnergy1 = BATTERY_1__MAX_ENERGY.get();
                        batteryMaxTransfer1 = BATTERY_1__MAX_TRANSFER.get();
                        batteryIsAllowed1 = BATTERY_1__IS_ALLOWED.get();

                        batteryMaxEnergy2 = BATTERY_2__MAX_ENERGY.get();
                        batteryMaxTransfer2 = BATTERY_2__MAX_TRANSFER.get();
                        batteryIsAllowed2 = BATTERY_2__IS_ALLOWED.get();

                        batteryMaxEnergy3 = BATTERY_ELITE__MAX_ENERGY.get();
                        batteryMaxTransfer3 = BATTERY_ELITE__MAX_TRANSFER.get();
                        batteryIsAllowed3 = BATTERY_ELITE__IS_ALLOWED.get();

                        batteryMaxEnergy4 = BATTERY_4__MAX_ENERGY.get();
                        batteryMaxTransfer4 = BATTERY_4__MAX_TRANSFER.get();
                        batteryIsAllowed4 = BATTERY_4__IS_ALLOWED.get();

                        keepModularItemsOnDeath = KEEP_MODULAR_ITEMS_ON_DEATH.get();
                }
        }
}