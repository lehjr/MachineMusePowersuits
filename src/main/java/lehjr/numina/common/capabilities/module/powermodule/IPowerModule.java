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
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public interface IPowerModule extends IConfigGetter {
    /**
     * Returns the enum corresponding to the EntityEquipment slot that the parent itemStack (Head, Chest... ALL.. )
     *
     * @return
     */
    ItemStack getModuleStack();

    ModuleTarget getTarget();

    ModuleCategory getCategory();

    default int getTier() {
        return -1;
    }

    default String getModuleGroup() {
        return "";
    }

    void addTradeoffProperty(String tradeoffName, String propertyName, double multiplier);

    void addPropertyModifier(String propertyName, IPropertyModifier modifier);

    void addTradeoffProperty(String tradeoffName, String propertyName, double multiplier, String unit);

    void addBaseProperty(String propertyName, double baseVal);

    void addBaseProperty(String propertyName, double baseVal, String unit);

    double applyPropertyModifiers(String propertyName);

    double applyPropertyModifiers(String propertyName, CompoundTag moduleTag);

    Map<String, List<IPropertyModifier>> getPropertyModifiers();

    void addIntTradeoffProperty(String tradeoffName, String propertyName, int multiplier, String unit, int roundTo, int offset);

    boolean isAllowed();

    default CompoundTag getModuleTag() {
        return TagUtils.getModuleTag(getModuleStack());
    }

    default boolean isModuleOnline() {
        return true;
    }

    // TODO: move to somewhere else??
    @OnlyIn(Dist.CLIENT) // only used by the client for display purposes
    default String getUnit(@Nonnull String propertyName) {
        return UnitMap.MAP.getUnit(propertyName);
    }

    default void addUnitLabel(@Nonnull String propertyName, String unit) {
        UnitMap.MAP.addUnitLabel(propertyName, unit);
    }

    interface IPropertyModifier extends IConfigGetter {
        double applyModifier(CompoundTag moduleTag, double value);
    }

    class PropertyModifierFlatAdditive implements IPropertyModifier {
        ImmutableList<String> configKey;
        Callable<IConfig> config;
        double defaultVal;

        public PropertyModifierFlatAdditive(ImmutableList<String> configKey, Callable<IConfig> config, double baseVal) {
            this.configKey = configKey;
            this.config = config;
            this.defaultVal = baseVal;
        }

        /**
         * @param moduleTag unused
         * @param value
         * @return getValue + this.valueAdded
         */
        @Override
        public double applyModifier(CompoundTag moduleTag, double value) {
            return value + getConfig(config).map(config-> config.getBasePropertyDoubleOrDefault(configKey, defaultVal)).orElse(defaultVal);
        }
    }

    class PropertyModifierIntLinearAdditive extends PropertyModifierLinearAdditive {
        protected int roundTo = 1;
        protected int offset = 0;

        public PropertyModifierIntLinearAdditive(
                ImmutableList<String> configKey,
                Callable<IConfig> config,
                String tradeoffName,
                double defaultMultiplier,
                int roundTo,
                int offset) {
            super(configKey, config, tradeoffName, defaultMultiplier);
            this.roundTo = roundTo;
            this.offset = offset;
        }

        @Override
        public double applyModifier(CompoundTag moduleTag, double value) {
            int multiplier = getConfig(config).map(config->
                    config.getTradeoffPropertyIntegerOrDefault(configKey, (int)defaultMultiplier)).orElse((int)defaultMultiplier);

            long result = (long) (value + multiplier * TagUtils.getDoubleOrZero(moduleTag, tradeoffName));
            return Double.valueOf(roundWithOffset(result, roundTo, offset));
        }

        public double getScaledDouble(CompoundTag moduleTag, double value) {
            double scaledVal = applyModifier(moduleTag, value);
            double ret = (scaledVal - value)/ defaultMultiplier;
            TagUtils.setDoubleOrRemove(moduleTag, tradeoffName, ret);
            return ret;
        }

        public long roundWithOffset(double input, int roundTo, int offset) {
            return Math.round((input + offset) / roundTo) * roundTo - offset;
        }

        public int getRoundTo() {
            return roundTo;
        }

        public int getOffset() {
            return offset;
        }
    }

    class PropertyModifierLinearAdditive implements IPropertyModifier {
        public final String tradeoffName;
        public double defaultMultiplier;
        ImmutableList<String> configKey;
        Callable<IConfig> config;

        public PropertyModifierLinearAdditive(ImmutableList<String> configKey, Callable<IConfig> config, String tradeoffName, double defaultMultiplier) {
            this.defaultMultiplier = defaultMultiplier;
            this.tradeoffName = tradeoffName;
            this.configKey = configKey;
            this.config = config;
        }

        @Override
        public double applyModifier(CompoundTag moduleTag, double value) {
            double multiplier = getConfig(config).map(config->
                    config.getTradeoffPropertyDoubleOrDefault(configKey, defaultMultiplier)).orElse(defaultMultiplier);
            return value + multiplier * TagUtils.getDoubleOrZero(moduleTag, tradeoffName);
        }

        public String getTradeoffName() {
            return tradeoffName;
        }
    }
}