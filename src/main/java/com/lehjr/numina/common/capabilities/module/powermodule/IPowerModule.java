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
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface IPowerModule {
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
        return getModuleStack().getOrCreateTag();
    }

    default boolean isModuleOnline() {
        return true;
    }

    @OnlyIn(Dist.CLIENT) // only used by the client for display purposes
    default Component getUnit(@Nonnull String propertyName) {
        return UnitMap.MAP.getUnit(propertyName);
    }

    default void addUnitLabel(@Nonnull String propertyName, String unit) {
        UnitMap.MAP.addUnitLabel(propertyName, unit);
    }

    class IntLinearAdditive extends LinearAdditive {
        protected int roundTo = 1;
        protected int offset = 0;

        public IntLinearAdditive(String tradeoffName, double multiplier, int roundTo, int offset) {
            super(tradeoffName, multiplier);
            this.roundTo = roundTo;
            this.offset = offset;
        }

        @Override
        public double applyModifier(CompoundTag moduleTag, double value) {
            long result = (long) (value + multiplier * moduleTag.getDouble(tradeoffName));
            return Double.valueOf(roundWithOffset(result, roundTo, offset));
        }

        public double getScaledDouble(CompoundTag moduleTag, double value) {
            double scaledVal = applyModifier(moduleTag, value);
            double ret = (scaledVal - value)/multiplier;


            moduleTag.putDouble(tradeoffName, ret);
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

    class LinearAdditive implements IPropertyModifier {
        public final String tradeoffName;
        public double multiplier;

        public LinearAdditive(String tradeoffName, double multiplier) {
            this.multiplier = multiplier;
            this.tradeoffName = tradeoffName;
        }

        @Override
        public double applyModifier(CompoundTag moduleTag, double value) {
            return value + multiplier * moduleTag.getDouble(tradeoffName);
        }

        public String getTradeoffName() {
            return tradeoffName;
        }
    }
    class FlatAdditive implements IPropertyModifier {
        public double valueAdded;

        public FlatAdditive(double valueAdded) {
            this.valueAdded = valueAdded;
        }

        /**
         * @param moduleTag unused
         * @param value
         * @return getValue + this.valueAdded
         */
        @Override
        public double applyModifier(CompoundTag moduleTag, double value) {
            return value + this.valueAdded;
        }
    }

    interface IPropertyModifier {
        double applyModifier(CompoundTag moduleTag, double value);
    }
}