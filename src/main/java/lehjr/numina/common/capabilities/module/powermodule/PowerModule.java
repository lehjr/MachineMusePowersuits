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

package lehjr.numina.common.capabilities.module.powermodule;

import com.google.common.collect.ImmutableList;
import lehjr.numina.common.tags.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class PowerModule implements IPowerModule {
    protected ItemStack module;
    protected final ModuleCategory category;
    protected final ModuleTarget target;
    protected Map<String, List<IPropertyModifier>> propertyModifiers;
    Callable<IConfig> moduleConfigGetter;
    ImmutableList<String> isAllowedConfigKey;
    final String moduleName;
    final String categoryTitle;

    public PowerModule(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target,
                       Callable<IConfig> moduleConfigGetterIn) {
        propertyModifiers = new HashMap<>();
        this.module = module;
        this.category = category;
        this.target = target;
        this.moduleConfigGetter = moduleConfigGetterIn;
        moduleName = itemTranslationKeyToConfigKey();
        categoryTitle = category.getConfigTitle().trim().replaceAll(" ", "_");
        isAllowedConfigKey = getConfigKey("isAllowed");
    }

    @Override
    public ItemStack getModuleStack() {
        return module;
    }

    @Override
    public ModuleTarget getTarget() {
        return target;
    }

    @Override
    public ModuleCategory getCategory() {
        return category;
    }

    /** Double ------------------------------------------------------------------------------------- */
    /**
     * Adds a base key and multiplierValue to the map based on the config setting.
     */
    @Override
    public void addTradeoffProperty(String tradeoffName, String propertyName, double defaultMultiplier) {
        ImmutableList<String> configKey = getConfigKey(new StringBuilder(propertyName).append("_").append(tradeoffName).append("_multiplier").toString());
        addPropertyModifier(propertyName, new PropertyModifierLinearAdditive(configKey, moduleConfigGetter, tradeoffName, defaultMultiplier));
    }

    @Override
    public void addPropertyModifier(String propertyName, IPropertyModifier modifier) {
        List<IPropertyModifier> modifiers = propertyModifiers.get(propertyName);
        if (modifiers == null) {
            modifiers = new LinkedList();
        }
        modifiers.add(modifier);
        propertyModifiers.put(propertyName, modifiers);
    }

    /**
     * Adds a base key and getValue to the map based on the config setting.
     * Also adds a [ propertyName, unitOfMeasureLabel ] k-v pair to a map used for displyaing a label
     */
    @Override
    public void addTradeoffProperty(String tradeoffName, String propertyName, double multiplier, String unit) {
        addUnitLabel(propertyName, unit);
        addTradeoffProperty(tradeoffName, propertyName, multiplier);
    }

    public void addSimpleTradeoff(String tradeoffName,
                                  String firstPropertyName,
                                  String firstUnits,
                                  double firstPropertyBase,
                                  double firstPropertyMultiplier,
                                  String secondPropertyName,
                                  String secondUnits,
                                  double secondPropertyBase,
                                  double secondPropertyMultiplier) {
        this.addBaseProperty(firstPropertyName, firstPropertyBase, firstUnits);
        this.addTradeoffProperty(tradeoffName, firstPropertyName, firstPropertyMultiplier);
        this.addBaseProperty(secondPropertyName, secondPropertyBase, secondUnits);
        this.addTradeoffProperty(tradeoffName, secondPropertyName, secondPropertyMultiplier);
    }

    /**
     * Adds a base key and getValue to the map based on the config setting.
     */
    @Override
    public void addBaseProperty(String propertyName, double baseVal) {
        ImmutableList<String> configKey = getConfigKey(new StringBuilder("base_").append(propertyName).toString());
        addPropertyModifier(propertyName, new PropertyModifierFlatAdditive(configKey, moduleConfigGetter, baseVal));
    }

    /**
     * Adds a base key and getValue to the map based on the config setting.
     * Also adds a [ propertyName, unitOfMeasureLabel ] k-v pair to a map used for displyaing a label
     */
    @Override
    public void addBaseProperty(String propertyName, double baseVal, String unit) {
        addUnitLabel(propertyName, unit);
        addBaseProperty(propertyName, baseVal);
    }

    @Override
    public double applyPropertyModifiers(String propertyName) {
        return applyPropertyModifiers(propertyName, TagUtils.getModuleTag(module));
    }

    @Override
    public double applyPropertyModifiers(String propertyName, CompoundTag moduleTag) {
        double propertyValue = 0;
        if (propertyModifiers.containsKey(propertyName)) {
            Iterable<IPropertyModifier> propertyModifiersIterable = propertyModifiers.get(propertyName);
            for (IPropertyModifier modifier : propertyModifiersIterable) {
                propertyValue = modifier.applyModifier(moduleTag, propertyValue);
            }
        }
        return propertyValue;
    }

    @Override
    public Map<String, List<IPropertyModifier>> getPropertyModifiers() {
        return propertyModifiers;
    }

    /**
     * Integer -----------------------------------------------------------------------------------
     */
    // This is the only one of these that will give an integer using the double system
    public void addIntTradeoffProperty(String tradeoffName, String propertyName, int defaultMultiplier, String unit, int roundTo, int offset) {
        ImmutableList<String> configKey = getConfigKey(new StringBuilder(propertyName).append("_").append(tradeoffName).append("_multiplier").toString());
        addUnitLabel(propertyName, unit);
        addPropertyModifier(propertyName, new PropertyModifierIntLinearAdditive(
                configKey, moduleConfigGetter, tradeoffName, defaultMultiplier, roundTo, offset));
    }

    @Override
    public boolean isAllowed() {
        return getConfig(moduleConfigGetter).map(config-> config.isModuleAllowed(isAllowedConfigKey)).orElse(true);
    }

    ImmutableList<String> getConfigKey(String entry) {
        return ImmutableList.of(
                "Modules",
                categoryTitle,
                moduleName,
                entry);
    }

    String itemTranslationKeyToConfigKey() {
        String translationKey = module.getItem().getDescriptionId();
        ResourceLocation regName = ForgeRegistries.ITEMS.getKey(module.getItem());
        // drop the prefix for MPS modules and replace "dots" with underscores
        final String itemPrefix = "item." + regName.getNamespace() + ".";
        if (translationKey.startsWith(itemPrefix )){
            translationKey = translationKey.substring(itemPrefix .length());
        }
        return translationKey.replace(".", "_");
    }
}