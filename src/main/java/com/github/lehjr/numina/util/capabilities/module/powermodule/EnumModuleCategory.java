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

package com.github.lehjr.numina.util.capabilities.module.powermodule;

/**
 * The module categories
 */
public enum EnumModuleCategory {
    NONE("None", "None"),
    DEBUG("Debug", "Debug_Modules"),
    ARMOR("Armor", "Armor_Modules"),
    ENERGY_STORAGE("Energy Storage", "Energy_Storage_Modules"),
    ENERGY_GENERATION("Energy Generation", "Energy_Generation_Modules"),
    TOOL("Tool", "Tool_Modules"),
    WEAPON("Weapon", "Weapon_Modules"),
    MOVEMENT("Movement", "Movement_Modules"),
    COSMETIC("Cosmetic", "Cosmetic_Modules"),
    VISION("Vision", "Vision_Modules"),
    ENVIRONMENTAL("Environment", "Environment_Modules"),
    SPECIAL("Special", "Special_Modules"),
    MINING_ENHANCEMENT("Mining Enhancement", "Mining_Enhancement_Modules");


    private final String name;
    private final String configTitle;

    //TODO: add translation stuff
    EnumModuleCategory(String name, String configTitle) {
        this.name = name;
        this.configTitle = configTitle;
    }

    public String getName() {
        return name;
    }

    public String getConfigTitle() {
        return configTitle;
    }
}
