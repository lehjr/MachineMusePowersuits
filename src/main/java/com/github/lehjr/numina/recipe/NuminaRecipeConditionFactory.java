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