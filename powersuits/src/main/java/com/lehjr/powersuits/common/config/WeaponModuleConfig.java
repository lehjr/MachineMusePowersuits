package com.lehjr.powersuits.common.config;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class WeaponModuleConfig {
    // Blade Launcher
    private static final ModConfigSpec.Builder BLADE_LAUNCHER_MODULE__SETTINGS_BUILDER = new ModConfigSpec.Builder().push("Weapon_Modules").push("Blade_Launcher");
    private static final ModConfigSpec.BooleanValue BLADE_LAUNCHER_MODULE__IS_ALLOWED = BLADE_LAUNCHER_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue BLADE_LAUNCHER_MODULE__ENERGY_CONSUMPTION_BASE = BLADE_LAUNCHER_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 5000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue BLADE_LAUNCHER_MODULE__SPINNING_BLADE_DAMAGE = BLADE_LAUNCHER_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.BLADE_DAMAGE, 6, 0, 100000.0D);

    // Lightning Summoner
    private static final ModConfigSpec.Builder LIGHTNING_SUMMONER_MODULE__SETTINGS_BUILDER = BLADE_LAUNCHER_MODULE__SETTINGS_BUILDER.pop().push("Lightning_Summoner_Launcher");
    private static final ModConfigSpec.BooleanValue LIGHTNING_SUMMONER_MODULE__IS_ALLOWED = LIGHTNING_SUMMONER_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue LIGHTNING_SUMMONER__ENERGY_CONSUMPTION_BASE = LIGHTNING_SUMMONER_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 4900000.0, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue LIGHTNING_SUMMONER__HEAT_EMISSION = LIGHTNING_SUMMONER_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.HEAT_EMISSION, 100, 0, 100000.0D);

    // Melee Assist
    private static final ModConfigSpec.Builder MELEE_ASSIST_MODULE__SETTINGS_BUILDER = LIGHTNING_SUMMONER_MODULE__SETTINGS_BUILDER.pop().push("Melee_Assist");
    private static final ModConfigSpec.BooleanValue MELEE_ASSIST_MODULE__IS_ALLOWED = MELEE_ASSIST_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue MELEE_ASSIST__ENERGY_CONSUMPTION_BASE = MELEE_ASSIST_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 10.0, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue MELEE_ASSIST__ENERGY_CONSUMPTION_IMPACT_MULTIPLIER = MELEE_ASSIST_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_IMPACT_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue MELEE_ASSIST__ENERGY_CONSUMPTION_CARRY_THROUGH_MULTIPLIER = MELEE_ASSIST_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_CARRY_THROUGH_MULTIPLIER, 200, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue MELEE_ASSIST__PUNCH_DAMAGE_BASE = MELEE_ASSIST_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.PUNCH_DAMAGE_BASE, 2, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue MELEE_ASSIST__PUNCH_DAMAGE_IMPACT_MULTIPLIER = MELEE_ASSIST_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.PUNCH_DAMAGE_IMPACT_MULTIPLIER, 2, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue MELEE_ASSIST__PUNCH_KNOCKBACK_CARRY_THROUGH_MULTIPLIER = MELEE_ASSIST_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.PUNCH_KNOCKBACK_CARRY_THROUGH_MULTIPLIER, 1, 0, 100000.0D);

    // Plasma Cannon
    private static final ModConfigSpec.Builder PLASMA_CANNON_MODULE__SETTINGS_BUILDER = MELEE_ASSIST_MODULE__SETTINGS_BUILDER.pop().push("Plasma_Cannon");
    private static final ModConfigSpec.BooleanValue PLASMA_CANNON_MODULE__IS_ALLOWED = PLASMA_CANNON_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue PLASMA_CANNON__ENERGY_PER_TICK_BASE = PLASMA_CANNON_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_PER_TICK_BASE, 100, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue PLASMA_CANNON__ENERGY_PER_TICK_VOLTAGE_MULTIPLIER = PLASMA_CANNON_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_PER_TICK_VOLTAGE_MULTIPLIER, 500, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue PLASMA_CANNON__ENERGY_PER_TICK_AMPERAGE_MULTIPLIER = PLASMA_CANNON_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_PER_TICK_AMPERAGE_MULTIPLIER, 1500, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue PLASMA_CANNON__DAMAGE_AT_FULL_CHARGE_BASE = PLASMA_CANNON_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE_BASE, 2, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue PLASMA_CANNON__DAMAGE_AT_FULL_CHARGE_AMPERAGE_MULTIPLIER = PLASMA_CANNON_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE_AMPERAGE_MULTIPLIER, 38, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue PLASMA_CANNON__EXPLOSIVENESS_VOLTAGE_MULTIPLIER = PLASMA_CANNON_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.PLASMA_CANNON_EXPLOSIVENESS_VOLTAGE_MULTIPLIER, 0.5, 0, 100000.0D);

    // RailGun
    private static final ModConfigSpec.Builder RAILGUN_MODULE__SETTINGS_BUILDER = PLASMA_CANNON_MODULE__SETTINGS_BUILDER.pop().push("Railgun");
    private static final ModConfigSpec.BooleanValue RAILGUN_MODULE__IS_ALLOWED = RAILGUN_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue RAILGUN_MODULE__ENERGY_CONSUMPTION_BASE= RAILGUN_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 5000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue RAILGUN_MODULE__ENERGY_CONSUMPTION_VOLTAGE_MULTIPLIER = RAILGUN_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_VOLTAGE_MULTIPLIER, 25000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue RAILGUN_MODULE__TOTAL_IMPULSE_BASE= RAILGUN_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.TOTAL_IMPULSE_BASE, 500, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue RAILGUN_MODULE__TOTAL_IMPULSE_VOLTAGE_MULTIPLIER = RAILGUN_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.TOTAL_IMPULSE_VOLTAGE_MULTIPLIER, 25000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue RAILGUN_MODULE__HEAT_EMISSION_BASE= RAILGUN_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.HEAT_EMMISSION_BASE, 5, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue RAILGUN_MODULE__HEAT_EMISSION_VOLTAGE_MULTIPLIER = RAILGUN_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.HEAT_EMMISSION_BASE, 10, 0, 100000.0D);

    // Sonic Weapon
//    private static final ModConfigSpec.Builder SONIC_WEAPON_MODULE__SETTINGS_BUILDER = RAILGUN_MODULE__SETTINGS_BUILDER.pop().push("Sonic_Weapon").comment("TODO");
    // TODO


//    public static final ModConfigSpec MPS_WEAPON_MODULE_SPEC = SONIC_WEAPON_MODULE__SETTINGS_BUILDER.build();
    public static final ModConfigSpec MPS_WEAPON_MODULE_SPEC = RAILGUN_MODULE__SETTINGS_BUILDER.build();

    // Blade Launcher
    public static boolean bladeLauncherIsAllowed;
    public static double bladeLauncherEnergyConsumption;
    public static double bladeLauncherSpinningBladeDamage;

    // Lightning Summoner
    public static boolean lightningSummonerIsAllowed;
    public static double lightningSummonerEnergyConsumption;
    public static double lightningSummonerHeatEmission;

    // Melee Assist
    public static boolean meleeAssistIsAllowed;
    public static double meleeAssistEnergyConsumptionBase;
    public static double meleeAssistEnergyConsumptionImpactMultiplier;
    public static double meleeAssistEnergyConsumptionCarryThroughMultiplier;
    public static double meleeAssistPunchDamageImpactMultiplier;
    public static double meleeAssistPunchDamageBase;
    public static double meleeAssistPunchKnockBackCarryThroughMultiplier;

    // Plasma Cannon
    public static boolean plasmaCannonIsAllowed;
    public static double plasmaCannonEnergyPerTickBase;
    public static double plasmaCannonEnergyPerTickVoltageMultiplier;
    public static double plasmaCannonEnergyPerTickAmperageMultiplier;
    public static double plasmaCannonDamageAtFullChargeBase;
    public static double plasmaCannonDamageAtFullChargeAmperageMultiplier;
    public static double plasmaCannonExplosivenessVoltageMultiplier;

    // Railgun
    public static boolean railgunIsAllowed;
    public static double railgunEnergyConsumptionBase;
    public static double railgunEnergyConsumptionVoltageMultiplier;
    public static double railgunTotalImpulseBase;
    public static double railgunTotalImpulseVoltageMultiplier;
    public static double railgunHeatEmissionBase;
    public static double railgunHeatEmissionVoltageMultiplier;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_WEAPON_MODULE_SPEC) {
            // Blade Launcher
            bladeLauncherIsAllowed = BLADE_LAUNCHER_MODULE__IS_ALLOWED.get();
            bladeLauncherEnergyConsumption = BLADE_LAUNCHER_MODULE__ENERGY_CONSUMPTION_BASE.get();
            bladeLauncherSpinningBladeDamage = BLADE_LAUNCHER_MODULE__SPINNING_BLADE_DAMAGE.get();

            // Lightining Summoner
            lightningSummonerIsAllowed = LIGHTNING_SUMMONER_MODULE__IS_ALLOWED.get();
            lightningSummonerEnergyConsumption = LIGHTNING_SUMMONER__ENERGY_CONSUMPTION_BASE.get();
            lightningSummonerHeatEmission = LIGHTNING_SUMMONER__HEAT_EMISSION.get();

            // Melee Assist
            meleeAssistIsAllowed = MELEE_ASSIST_MODULE__IS_ALLOWED.get();
            meleeAssistEnergyConsumptionBase = MELEE_ASSIST__ENERGY_CONSUMPTION_BASE.get();
            meleeAssistEnergyConsumptionImpactMultiplier = MELEE_ASSIST__ENERGY_CONSUMPTION_IMPACT_MULTIPLIER.get();
            meleeAssistEnergyConsumptionCarryThroughMultiplier = MELEE_ASSIST__ENERGY_CONSUMPTION_CARRY_THROUGH_MULTIPLIER.get();
            meleeAssistPunchDamageImpactMultiplier = MELEE_ASSIST__PUNCH_DAMAGE_IMPACT_MULTIPLIER.get();
            meleeAssistPunchDamageBase = MELEE_ASSIST__PUNCH_DAMAGE_BASE.get();
            meleeAssistPunchKnockBackCarryThroughMultiplier = MELEE_ASSIST__PUNCH_KNOCKBACK_CARRY_THROUGH_MULTIPLIER.get();

            // Plasma Cannon
            plasmaCannonIsAllowed = PLASMA_CANNON_MODULE__IS_ALLOWED.get();
            plasmaCannonEnergyPerTickBase = PLASMA_CANNON__ENERGY_PER_TICK_BASE.get();
            plasmaCannonEnergyPerTickVoltageMultiplier = PLASMA_CANNON__ENERGY_PER_TICK_VOLTAGE_MULTIPLIER.get();
            plasmaCannonEnergyPerTickAmperageMultiplier = PLASMA_CANNON__ENERGY_PER_TICK_AMPERAGE_MULTIPLIER.get();
            plasmaCannonDamageAtFullChargeBase = PLASMA_CANNON__DAMAGE_AT_FULL_CHARGE_BASE.get();
            plasmaCannonDamageAtFullChargeAmperageMultiplier = PLASMA_CANNON__DAMAGE_AT_FULL_CHARGE_AMPERAGE_MULTIPLIER.get();
            plasmaCannonExplosivenessVoltageMultiplier = PLASMA_CANNON__EXPLOSIVENESS_VOLTAGE_MULTIPLIER.get();

            // Railgun
            railgunIsAllowed = RAILGUN_MODULE__IS_ALLOWED.get();
            railgunEnergyConsumptionBase = RAILGUN_MODULE__ENERGY_CONSUMPTION_BASE.get();
            railgunEnergyConsumptionVoltageMultiplier = RAILGUN_MODULE__ENERGY_CONSUMPTION_VOLTAGE_MULTIPLIER.get();
            railgunTotalImpulseBase = RAILGUN_MODULE__TOTAL_IMPULSE_BASE.get();
            railgunTotalImpulseVoltageMultiplier = RAILGUN_MODULE__TOTAL_IMPULSE_VOLTAGE_MULTIPLIER.get();
            railgunHeatEmissionBase = RAILGUN_MODULE__HEAT_EMISSION_BASE.get();
            railgunHeatEmissionVoltageMultiplier = RAILGUN_MODULE__HEAT_EMISSION_VOLTAGE_MULTIPLIER.get();
        }
    }
}
