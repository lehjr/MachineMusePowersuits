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

package lehjr.powersuits.common.base;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds a list of module registry names of modules that can be installed in the armor and power fist.
 * Used in the installation/removal Gui.
 */
public enum MPSModules {
    INSTANCE;

    protected List<ResourceLocation> moduleRegNames = new ArrayList<ResourceLocation>() {{
        add(new ResourceLocation(NuminaConstants.MOD_ID, NuminaConstants.MODULE_BATTERY_BASIC__REGNAME));
        add(new ResourceLocation(NuminaConstants.MOD_ID, NuminaConstants.MODULE_BATTERY_ADVANCED__REGNAME));
        add(new ResourceLocation(NuminaConstants.MOD_ID, NuminaConstants.MODULE_BATTERY_ELITE__REGNAME));
        add(new ResourceLocation(NuminaConstants.MOD_ID, NuminaConstants.MODULE_BATTERY_ULTIMATE__REGNAME));
        add(MPSRegistryNames.TINKER_TABLE);

        add(new ResourceLocation("clock"));
        add(new ResourceLocation("compass"));
        add(new ResourceLocation("crafting_table"));
    }};

    public void addModule(ResourceLocation regName) {
        moduleRegNames.add(regName);
    }

    public List<ResourceLocation> getModuleRegNames () {
        return moduleRegNames;
    }
}