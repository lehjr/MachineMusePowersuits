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

package com.lehjr.numina.api.capabilities.module.powermodule;

import net.minecraft.network.chat.TranslatableComponent;

/**
 * The module categories
 */
public enum ModuleCategory {
    NONE("numina.module.category.none", "None"),
    DEBUG("numina.module.category.debug", "Debug_Modules"),
    ARMOR("numina.module.category.armor", "Armor_Modules"),
    ENERGY_STORAGE("numina.module.category.energystorage", "Energy_Storage_Modules"),
    ENERGY_GENERATION("numina.module.category.energygeneration", "Energy_Generation_Modules"),
    TOOL("numina.module.category.tool", "Tool_Modules"),
    WEAPON("numina.module.category.weapon", "Weapon_Modules"),
    MOVEMENT("numina.module.category.movement", "Movement_Modules"),
    COSMETIC("numina.module.category.cosmetic", "Cosmetic_Modules"),
    VISION("numina.module.category.vision", "Vision_Modules"),
    ENVIRONMENTAL("numina.module.category.environment", "Environment_Modules"),
    SPECIAL("numina.module.category.special", "Special_Modules"),
    MINING_ENHANCEMENT("numina.module.category.miningenhancement", "Mining_Enhancement_Modules");

    private final String configTitle;
    private final TranslatableComponent translation;

    ModuleCategory(String translationString, String configTitle) {
        this.translation = new TranslatableComponent(translationString);
        this.configTitle = configTitle;
    }

    public TranslatableComponent getTranslation() {
        return translation;
    }

    public String getConfigTitle() {
        return configTitle;
    }
}
