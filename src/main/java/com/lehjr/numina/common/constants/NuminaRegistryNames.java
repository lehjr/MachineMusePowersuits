package com.lehjr.numina.common.constants;

import net.minecraft.resources.ResourceLocation;

public class NuminaRegistryNames {

    // RegistryNames
    public static final ResourceLocation CHARGING_BASE_REGNAME = makeRegName("charging_base");
    public static final ResourceLocation ARMORSTAND_REGNAME = makeRegName("armor_stand");

    public static final ResourceLocation  MODULE_BATTERY_BASIC__REGNAME = makeRegName("battery_basic");
    public static final ResourceLocation  MODULE_BATTERY_ADVANCED__REGNAME = makeRegName("battery_advanced");
    public static final ResourceLocation  MODULE_BATTERY_ELITE__REGNAME = makeRegName("battery_elite");
    public static final ResourceLocation  MODULE_BATTERY_ULTIMATE__REGNAME = makeRegName("battery_ultimate");

    public static final ResourceLocation  ARMOR_STAND__ENTITY_TYPE_REGNAME = makeRegName(ARMORSTAND_REGNAME.getPath() + "_entity");

    /**
     * Components ---------------------------------------------------------------------------------
     */
    public static final ResourceLocation  COMPONENT__WIRING__REGNAME = makeRegName("component_wiring");
    public static final ResourceLocation  COMPONENT__SOLENOID__REGNAME = makeRegName("component_solenoid");
    public static final ResourceLocation  COMPONENT__SERVO__REGNAME = makeRegName("component_servo");
    public static final ResourceLocation  COMPONENT__GLIDER_WING__REGNAME = makeRegName("component_glider_wing");
    public static final ResourceLocation  COMPONENT__ION_THRUSTER__REGNAME = makeRegName("component_ion_thruster");
    public static final ResourceLocation  COMPONENT__PARACHUTE__REGNAME = makeRegName("component_parachute");
    public static final ResourceLocation  COMPONENT__FIELD_EMITTER__REGNAME = makeRegName("component_field_emitter");
    public static final ResourceLocation  COMPONENT__LASER_EMITTER__REGNAME = makeRegName("component_laser_emitter");
    public static final ResourceLocation  COMPONENT__CARBON_MYOFIBER__REGNAME = makeRegName("component_carbon_myofiber");
    public static final ResourceLocation  COMPONENT__CONTROL_CIRCUIT_BASIC__REGNAME = makeRegName("component_control_circuit_basic");
    public static final ResourceLocation  COMPONENT__CONTROL_CIRCUIT_ADVANCED__REGNAME = makeRegName("component_control_circuit_advanced");
    public static final ResourceLocation  COMPONENT__CONTROL_CIRCUIT_ELITE__REGNAME = makeRegName("component_control_circuit_elite");
    public static final ResourceLocation  COMPONENT__CONTROL_CIRCUIT_ULTIMATE__REGNAME = makeRegName("component_control_circuit_ultimate");
    public static final ResourceLocation  COMPONENT__MYOFIBER_GEL__REGNAME = makeRegName("component_myofiber_gel");
    public static final ResourceLocation  COMPONENT__ARTIFICIAL_MUSCLE__REGNAME = makeRegName("component_artificial_muscle");
    public static final ResourceLocation  COMPONENT__SOLAR_PANEL__REGNAME = makeRegName("component_solar_panel");
    public static final ResourceLocation  COMPONENT__MAGNET__REGNAME = makeRegName("component_magnet");
    public static final ResourceLocation  COMPONENT__COMPUTER_CHIP__REGNAME = makeRegName("component_computer_chip");
    public static final ResourceLocation  COMPONENT__RUBBER_HOSE__REGNAME = makeRegName("component_rubber_hose");


    static ResourceLocation makeRegName(String path) {
        return new ResourceLocation(NuminaConstants.MOD_ID, path);
    }
}
