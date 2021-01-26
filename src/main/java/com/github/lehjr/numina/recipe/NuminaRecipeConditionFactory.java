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

package com.github.lehjr.numina.recipe;

import com.github.lehjr.numina.config.NuminaSettings;
import com.github.lehjr.numina.constants.NuminaConstants;
import com.google.gson.JsonObject;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class NuminaRecipeConditionFactory implements ICondition {
    static final ResourceLocation NAME = new ResourceLocation(NuminaConstants.MOD_ID, "flag");

    String conditionName;

    public NuminaRecipeConditionFactory(String conditionName) {
        this.conditionName = conditionName;
    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test() {
        switch (conditionName) {
            // Vanilla reciples only as fallback
            case "vanilla_recipes_enabled": {
                return (NuminaSettings.useVanillaRecipes());
            }

            case "vanilla_recipes_disabled": {
                return (!NuminaSettings.useVanillaRecipes());
            }
        }
        return false;
    }

    public static class Serializer implements IConditionSerializer<NuminaRecipeConditionFactory> {

        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, NuminaRecipeConditionFactory value) {
            // Don't think anything else needs to be added here, as this is now working
//            System.out.println("json: " + json.toString());
//            System.out.println("value: " + value.conditionName);
//            json.addProperty("condition", value.conditionName);
        }

        @Override
        public NuminaRecipeConditionFactory read(JsonObject json) {
            return new NuminaRecipeConditionFactory(JSONUtils.getString(json, "flag"));
        }

        @Override
        public ResourceLocation getID() {
            return NuminaRecipeConditionFactory.NAME;
        }
    }
}