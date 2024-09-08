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

package com.lehjr.numina.common.recipe;

import com.lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RecipeSerializersRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, NuminaConstants.MOD_ID);
    public static final DeferredHolder<RecipeSerializer<?>, ShapedEnergyRecipe.EnergySerializer> ENERGY_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_energy_item", ShapedEnergyRecipe.EnergySerializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, ShapedEnchantmentRecipe.EnchantmentSerializer> ENCHANTMENT_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_enchantment_item", ShapedEnchantmentRecipe.EnchantmentSerializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, ShapedModularItemUpgradeRecipe.ModularItemSerializer> MODULAR_ITEM_UPGRADE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_modular_item", ShapedModularItemUpgradeRecipe.ModularItemSerializer::new);
//    public static final DeferredHolder<RecipeSerializer<?>, ShapedModularItemUpgradeRecipe.ModularItemSerializer> MODULAR_ITEM_UPGRADE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_modular_item", () -> new SimpleCraftingRecipeSerializer<>(ShapedModularItemUpgradeRecipe::new));

    public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<ModuleInstallationRecipe>> ATTACHMENT = RECIPE_SERIALIZERS.register("module_install", () -> new SimpleCraftingRecipeSerializer<>(ModuleInstallationRecipe::new));

}
