package com.lehjr.numina.common.capabilities.module.powermodule;

import com.lehjr.numina.common.utils.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

public interface IPowerModule {

    ModuleCategory getCategory();

    default int getTier() {
        return -1;
    }

    ModuleTarget getTarget();

    default String getModuleGroup() {
        return "";
    }

    default ItemStack getModule() {
        return ItemStack.EMPTY;
    }

    default CompoundTag getModuleTag() {
        return TagUtils.getModuleTag(getModule());
    }

    /**
     * Override for normal implementations. Ignore for adding items from other mods as modules
     * @return
     */
    default boolean isAllowed() {
        return true;
    }

    default boolean isModuleOnline() {
        return true;
    }

    void load();

    ItemStack save();

    default void addUnitLabel(String propertyName, String unit) {
        UnitMap.addUnitLabel(propertyName, unit);
    }

    Map<String, List<IPropertyModifier>> getPropertyModifiers();

    default int getEnergyUsage() {
        return 0;
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
        return applyPropertyModifiers(propertyName, TagUtils.getModuleTag(getModule()));
    }

    default double applyPropertyModifiers(String propertyName, CompoundTag moduleTag) {
        double propertyValue = 0;
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
    }

    class PropertyModifierIntLinearAdditive extends PropertyModifierLinearAdditive {
        protected int roundTo = 1;
        protected int offset = 0;

        public PropertyModifierIntLinearAdditive(
                double config,
                String tradeoffName,
                int roundTo,
                int offset) {
            super(config, tradeoffName);
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
            TagUtils.setDouble(moduleTag, tradeoffName, ret);
            return ret;
        }

        public long roundWithOffset(double input) {
            return Math.round((input + offset) / roundTo) * roundTo - offset;
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
    }
}
