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

package com.lehjr.numina.common.capabilities.module.powermodule;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.Callable;

public class PowerModule implements IPowerModule {
    protected ItemStack module;
    protected final ModuleCategory category;
    protected final ModuleTarget target;
    protected Map<String, List<IPropertyModifier>> propertyModifiers;
    Callable<IConfig> moduleConfigGetter;

    public PowerModule(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target,
                       Callable<IConfig> moduleConfigGetterIn) {
        propertyModifiers = new HashMap<>();
        this.module = module;
        this.category = category;
        this.target = target;
        this.moduleConfigGetter = moduleConfigGetterIn;
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

    Optional<IConfig> getConfig() {
        try {
            return Optional.ofNullable(moduleConfigGetter.call());
        } catch (Exception e) {
            // not initialized yet
            // TODO: debug message?
            e.printStackTrace();
        }
        return null;
    }


    /** Double ------------------------------------------------------------------------------------- */
    /**
     * Adds a base key and multiplierValue to the map based on the config setting.
     */
    @Override
    public void addTradeoffProperty(String tradeoffName, String propertyName, double multiplier) {
        double propFromConfig = getConfig().map(config->
                config.getTradeoffPropertyDoubleOrDefault(category, module, tradeoffName, propertyName, multiplier)).orElse(multiplier);
        addPropertyModifier(propertyName, new LinearAdditive(tradeoffName, propFromConfig));
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
        double propFromConfig = getConfig()
                .map(config-> config.getBasePropertyDoubleOrDefault(category, module, propertyName, baseVal)).orElse(baseVal);
        addPropertyModifier(propertyName, new FlatAdditive(propFromConfig));
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
        return applyPropertyModifiers(propertyName, module.getOrCreateTag());
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
    public void addIntTradeoffProperty(String tradeoffName, String propertyName, int multiplier, String unit, int roundTo, int offset) {
        addUnitLabel(propertyName, unit);
        int propFromConfig = getConfig().map(config->
                config.getTradeoffPropertyIntegerOrDefault(category, module, tradeoffName, propertyName, multiplier)).orElse(multiplier);
        addPropertyModifier(propertyName, new IntLinearAdditive(tradeoffName, propFromConfig, roundTo, offset));
    }

    @Override
    public boolean isAllowed() {
        return getConfig().map(config-> config.isModuleAllowed(category, module)).orElse(true);
    }
}