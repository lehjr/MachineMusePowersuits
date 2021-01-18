/*
 * Copyright (c) 2019 MachineMuse, Lehjr
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.numina.util.capabilities.module.powermodule;

import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.util.nbt.MuseNBTUtils;
import com.github.lehjr.numina.util.nbt.propertymodifier.IPropertyModifier;
import com.github.lehjr.numina.util.nbt.propertymodifier.PropertyModifierFlatAdditive;
import com.github.lehjr.numina.util.nbt.propertymodifier.PropertyModifierIntLinearAdditive;
import com.github.lehjr.numina.util.nbt.propertymodifier.PropertyModifierLinearAdditive;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.Callable;

public class PowerModule implements IPowerModule {
    protected static Map<String, String> units;
    protected ItemStack module;
    protected final EnumModuleCategory category;
    protected final EnumModuleTarget target;
    protected Map<String, List<IPropertyModifier>> propertyModifiers;
    Callable<IConfig> moduleConfigGetter;

    public PowerModule(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target,
                       Callable<IConfig> moduleConfigGetterIn) {
        propertyModifiers = new HashMap<>();
        units = new HashMap<>();
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
    public EnumModuleTarget getTarget() {
        return target;
    }

    @Override
    public EnumModuleCategory getCategory() {
        return category;
    }


    // TODO: move to somewhere else??
    @OnlyIn(Dist.CLIENT)
    @Override
    public String getUnit(String propertyName) {
        String unit = units.get(propertyName);
        if (unit != null && unit.startsWith(NuminaConstants.MODULE_TRADEOFF_PREFIX))
            unit = I18n.format(unit);

        return unit == null ? "" : unit;
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
        addPropertyModifier(propertyName, new PropertyModifierLinearAdditive(tradeoffName, propFromConfig));
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
        units.put(propertyName, unit);
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
        double propFromConfig =
                getConfig().map(config->
                        config.getBasePropertyDoubleOrDefault(category, module, propertyName, baseVal)).orElse(baseVal);
        addPropertyModifier(propertyName, new PropertyModifierFlatAdditive(propFromConfig));
    }

    /**
     * Adds a base key and getValue to the map based on the config setting.
     * Also adds a [ propertyName, unitOfMeasureLabel ] k-v pair to a map used for displyaing a label
     */
    @Override
    public void addBaseProperty(String propertyName, double baseVal, String unit) {
        units.put(propertyName, unit);
        addBaseProperty(propertyName, baseVal);
    }

    @Override
    public double applyPropertyModifiers(String propertyName) {
        return applyPropertyModifiers(propertyName, MuseNBTUtils.getModuleTag(module));
    }

    @Override
    public double applyPropertyModifiers(String propertyName, CompoundNBT moduleTag) {
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
        units.put(propertyName, unit);
        int propFromConfig = getConfig().map(config->
                config.getTradeoffPropertyIntegerOrDefault(category, module, tradeoffName, propertyName, multiplier)).orElse(multiplier);
        addPropertyModifier(propertyName, new PropertyModifierIntLinearAdditive(tradeoffName, propFromConfig, roundTo, offset));
    }

    @Override
    public boolean isAllowed() {
        return getConfig().map(config-> config.isModuleAllowed(category, module)).orElse(true);
    }
}