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

package lehjr.numina.common.constants;


import net.minecraft.resources.ResourceLocation;

public class NuminaConstants {
  // Mod
  public static final String MOD_ID = "numina";

  public static final String ITEM_GROUP_TRANSLATION_KEY = "itemGroup." + MOD_ID;

  public static final int FULL_BRIGHTNESS = 0XF000F0;

  // Energy Storage -----------------------------------------------------------------------------
  public static final String MAX_ENERGY = "maxEnergy";
  public static final String MAX_TRAMSFER = "maxTransfer";

  // Misc
  public static final String RESOURCE_PREFIX = MOD_ID + ":";
  public static final String TEXTURE_PREFIX = RESOURCE_PREFIX + "textures/";
  public static final ResourceLocation GLASS_TEXTURE = new ResourceLocation(TEXTURE_PREFIX + "gui/glass.png");
  public static final ResourceLocation LOCATION_NUMINA_GUI_TEXTURE_ATLAS = new ResourceLocation(MOD_ID, "textures/atlas/gui.png");
  public static final ResourceLocation BLANK_ARMOR_MODEL_PATH = new ResourceLocation(TEXTURE_PREFIX + "item/armor/blankarmor.png");
  public static final ResourceLocation WEAPON_SLOT_BACKGROUND = new ResourceLocation(TEXTURE_PREFIX + "gui/weapon.png");

  // Config
  public static final String CONFIG_PREFIX = "config." + MOD_ID + ".";
  public static final String CONFIG_USE_FOV_FIX = CONFIG_PREFIX + "useFOVFix";
  public static final String CONFIG_USE_FOV_NORMALIZE = CONFIG_PREFIX + "normalizeFOV";
  public static final String CONFIG_FOV_FIX_DEAULT_STATE = CONFIG_PREFIX + "FOVFixDefaultState";
  public static final String CONFIG_USE_SOUNDS = CONFIG_PREFIX + "useSounds";
  public static final String CONFIG_DEBUGGING_INFO = CONFIG_PREFIX + "useDebuggingInfo";
  public static final String CONFIG_PREFIX_RECIPES = CONFIG_PREFIX + "recipes.";
  public static final String CONFIG_RECIPES_USE_VANILLA = CONFIG_PREFIX_RECIPES + "useVanilla";


  // String for overheat damage
  public static final String OVERHEAT_DAMAGE = "Overheat";

  // RegistryNames
  public static final String CHARGING_BASE_REGNAME = "charging_base";
  public static final String ARMORSTAND_REGNAME = "armor_stand";

  public static final String MODULE_BATTERY_BASIC__REGNAME = "battery_basic";
  public static final String MODULE_BATTERY_ADVANCED__REGNAME = "battery_advanced";
  public static final String MODULE_BATTERY_ELITE__REGNAME = "battery_elite";
  public static final String MODULE_BATTERY_ULTIMATE__REGNAME = "battery_ultimate";

  public static final String ARMOR_STAND__ENTITY_TYPE_REGNAME = ARMORSTAND_REGNAME + "_entity";

  /**
   * Components ---------------------------------------------------------------------------------
   */
  public static final String COMPONENT__WIRING__REGNAME = getComponentName("wiring");
  public static final String COMPONENT__SOLENOID__REGNAME = getComponentName("solenoid");
  public static final String COMPONENT__SERVO__REGNAME = getComponentName("servo");
  public static final String COMPONENT__GLIDER_WING__REGNAME = getComponentName("glider_wing");
  public static final String COMPONENT__ION_THRUSTER__REGNAME = getComponentName("ion_thruster");
  public static final String COMPONENT__PARACHUTE__REGNAME = getComponentName("parachute");
  public static final String COMPONENT__FIELD_EMITTER__REGNAME = getComponentName("field_emitter");
  public static final String COMPONENT__LASER_EMITTER__REGNAME = getComponentName("laser_emitter");
  public static final String COMPONENT__CARBON_MYOFIBER__REGNAME = getComponentName("carbon_myofiber");
  public static final String COMPONENT__CONTROL_CIRCUIT__REGNAME = getComponentName("control_circuit");
  public static final String COMPONENT__MYOFIBER_GEL__REGNAME = getComponentName("myofiber_gel");
  public static final String COMPONENT__ARTIFICIAL_MUSCLE__REGNAME = getComponentName("artificial_muscle");
  public static final String COMPONENT__SOLAR_PANEL__REGNAME = getComponentName("solar_panel");
  public static final String COMPONENT__MAGNET__REGNAME = getComponentName("magnet");
  public static final String COMPONENT__COMPUTER_CHIP__REGNAME = getComponentName("computer_chip");
  public static final String COMPONENT__RUBBER_HOSE__REGNAME = getComponentName("rubber_hose");

  /**
   *  Tooltips -----------------------------------------------------------------------------------------
   */
  public static final String TOOLTIP_MODE = getTooltip("mode");
  public static final String TOOLTIP_CHANGE_MODES = getTooltip(  "changeModes");
  public static final String TOOLTIP_INSTALLED_MODULES = getTooltip("installedModules");
  public static final String TOOLTIP_NO_MODULES = getTooltip("noModules");
  public static final String TOOLTIP_CHARGING_BASE = getTooltip(CHARGING_BASE_REGNAME);
  public static final String TOOLTIP_BATTERY_ENERGY = getTooltip("battery.energy");
  public static final String TOOLTIP_PRESS_SHIFT = getTooltip("pressShift");
  public static final String TOOLTIP_ENERGY = getTooltip("energy");




  static String getTooltip(String tooltipName) {
    return new StringBuilder("tooltip.").append(NuminaConstants.MOD_ID).append(".").append(tooltipName).toString();
  }



  public static final ResourceLocation TEXTURE_WHITE_SHORT = new ResourceLocation(NuminaConstants.MOD_ID, "models/white");
  public static final ResourceLocation TEXTURE_WHITE = new ResourceLocation(NuminaConstants.MOD_ID, "textures/models/white.png");
  public static final ResourceLocation TEXTURE_ARMOR_STAND = new ResourceLocation(NuminaConstants.MOD_ID, "textures/models/armorstand2.png");




  public static final String GUI_CREATIVE_INSTALL = getGUI("creative.install");
  public static final String GUI_CREATIVE_INSTALL_DESC = getGUI("creative.install.desc");

  static String getGUI(String guiString) {
    return new StringBuilder("gui.").append(MOD_ID).append(".").append(guiString).toString();
  }

  static String getComponentName(String component) {
    return new StringBuilder("component_").append(component).toString();
  }
}