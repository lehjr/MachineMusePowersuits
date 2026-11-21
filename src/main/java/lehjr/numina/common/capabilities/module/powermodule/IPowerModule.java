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

import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

public interface IPowerModule {
    ModuleCategory getCategory();

    int getTier();

    ModuleTarget getTarget();

    String getModuleGroup();

    default boolean isGroup(String group) {
        return group.equalsIgnoreCase(getModuleGroup());
    }

    ItemStack getModule();

    default CompoundTag getModuleTag() {
        return TagUtils.getModuleTag(getModule());
    }

    boolean isAllowed();

    boolean isModuleOnline();

    default void addUnitLabel(String propertyName, String unit) {
        UnitMap.addUnitLabel(propertyName, unit);
    }

    Map<String, List<IPropertyModifier>> getPropertyModifiers();

    default double getEnergyUsage() {
        return 0;
    }

    default double getPlayerEnergy(Player player) {
        return ElectricItemUtils.getPlayerEnergy(player);
    }

    default void addSimpleTradeoff(String tradeoffName,
                                   String firstPropertyName,
                                   String firstUnits,
                                   double firstPropertyBase,
                                   double firstPropertyMultiplier,
                                   String secondPropertyName,
                                   String secondUnits,
                                   double secondPropertyBase,
                                   double secondPropertyMultiplier) {
        addBaseProperty(firstPropertyName, firstPropertyBase, firstUnits);
        addTradeoffProperty(tradeoffName, firstPropertyName, firstPropertyMultiplier);
        addBaseProperty(secondPropertyName, secondPropertyBase, secondUnits);
        addTradeoffProperty(tradeoffName, secondPropertyName, secondPropertyMultiplier);
    }

    void addBaseProperty(String propertyName, double configValue);

    void addBaseProperty(String propertyName, double configValue, String unit);

    void addTradeoffProperty(String tradeoffName, String propertyName, double multiplierConfigValue);

    void addTradeoffProperty(String tradeoffName, String propertyName, double mulitiplierConfigValue, String unit);

    void addIntTradeoffProperty(String tradeoffName, String propertyName, int mulitiplierConfigValue, String unit, int roundTo, int offset);

    void addPropertyModifier(String propertyName, IPropertyModifier modifier);

    default double applyPropertyModifiers(String propertyName) {
        return applyPropertyModifiers(propertyName, TagUtils.getModuleTag(getModule()), 0);
    }

    default double applyPropertyModifiers(String propertyName, CompoundTag moduleTag) {
        return applyPropertyModifiers(propertyName, moduleTag, 0);
    }

    default double applyPropertyModifiers(String propertyName, double propertyValue) {
        return applyPropertyModifiers(propertyName, TagUtils.getModuleTag(getModule()), propertyValue);
    }

    default double applyPropertyModifiers(String propertyName, CompoundTag moduleTag, double propertyValue) {
        if (getPropertyModifiers().containsKey(propertyName)) {
            Iterable<IPropertyModifier> propertyModifiersIterable = getPropertyModifiers().get(propertyName);
            for (IPropertyModifier modifier : propertyModifiersIterable) {
                propertyValue = modifier.applyModifier(moduleTag, propertyValue);
            }
        }
        return propertyValue;
    }

    interface IPropertyModifier {
        double applyModifier(CompoundTag moduleTag, double value);
    }

    class PropertyModifierFlatAdditive implements IPropertyModifier {
        double configValue;

        public PropertyModifierFlatAdditive(double configValue) {
            this.configValue = configValue;
        }

        /**
         * @param ignoredTag unused
         * @param value
         * @return getValue + this.valueAdded
         */
        @Override
        public double applyModifier(CompoundTag ignoredTag, double value) {
            return value + configValue;
        }

        @Override
        public String toString() {
            return "Class: " + getClass() + "\n"
                    + "configValue: " + configValue;
        }
    }

    class PropertyModifierIntLinearAdditive extends PropertyModifierLinearAdditive {
        protected int roundTo = 1;
        protected int offset = 0;

        public PropertyModifierIntLinearAdditive(
                double multiplier,
                String tradeoffName,
                int roundTo,
                int offset) {
            super(multiplier, tradeoffName);
            this.roundTo = roundTo;
            this.offset = offset;
        }

        int getMultiplier() {
            return (int) multiplier;
        }

        @Override
        public double applyModifier(CompoundTag moduleTag, double value) {
            long result = (long) (value + getMultiplier() * TagUtils.getDoubleOrZero(moduleTag, tradeoffName));
            return (double) roundWithOffset(result);
        }

        public double getScaledDouble(CompoundTag moduleTag, double value) {
            double scaledVal = applyModifier(moduleTag, value);
            double ret = (scaledVal - value)/ getMultiplier();
            TagUtils.setDoubleOrRemove(moduleTag, tradeoffName, ret);
            return ret;
        }

        public long roundWithOffset(double input) {
            return Math.round((input + offset) / roundTo) * roundTo - offset;
        }

        @Override
        public String toString() {
            return "Class: " + getClass() + "\n"
                    + "roundTo: " + roundTo +"\n"
                    + "offset: " + offset  +"\n"
                    + "multiplier: " + multiplier  +"\n"
                    + "tradeoffName: " + tradeoffName;
        }
    }

    class PropertyModifierLinearAdditive implements IPropertyModifier {
        public final String tradeoffName;
        double multiplier;

        public PropertyModifierLinearAdditive(double multiplier, String tradeoffName) {
            this.tradeoffName = tradeoffName;
            this.multiplier = multiplier;
        }

        @Override
        public double applyModifier(CompoundTag moduleTag, double value) {
            return value + multiplier * TagUtils.getDoubleOrZero(moduleTag, tradeoffName);
        }

        public String getTradeoffName() {
            return tradeoffName;
        }

        @Override
        public String toString() {
            return "Class: " + getClass() + "\n"
                    + "multiplier: " + multiplier  +"\n"
                    + "tradeoffName: " + tradeoffName;
        }
    }
}