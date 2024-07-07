package lehjr.numina.common.capability.module.powermodule;

import lehjr.numina.common.utils.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

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

    default void load(CompoundTag nbt) {

    }

    default void save() {

    }

    default void addUnitLabel(String propertyName, String unit) {
        UnitMap.addUnitLabel(propertyName, unit);
    }

    Map<String, List<IPropertyModifier>> getPropertyModifiers();

    default int getEnergyUsage() {
        return 0;
    }

    void addBaseProperty(String propertyName, Callable<IConfigDoubleGetter> configValGetter);

    void addBaseProperty(String propertyName, Callable<IConfigDoubleGetter> config, String unit);

    void addTradeoffProperty(String tradeoffName, String propertyName, Callable<IConfigDoubleGetter> configMultiplier);

    void addTradeoffProperty(String tradeoffName, String propertyName, Callable<IConfigDoubleGetter> configMultiplier, String unit);

    void addIntTradeoffProperty(Callable<IConfigIntGetter> config, String tradeoffName, String propertyName, String unit, int roundTo, int offset);

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
        Callable<IConfigDoubleGetter> config;

        public PropertyModifierFlatAdditive(Callable<IConfigDoubleGetter> config) {
            this.config = config;
        }

        /**
         * @param ignoredTag unused
         * @param value
         * @return getValue + this.valueAdded
         */
        @Override
        public double applyModifier(CompoundTag ignoredTag, double value) {
            try {
                return value + config.call().getValue();
            } catch (Exception ignored) {
            }
            return value;
        }
    }

    class PropertyModifierIntLinearAdditive extends PropertyModifierLinearAdditive<Integer, IConfigIntGetter> {
        protected int roundTo = 1;
        protected int offset = 0;

        public PropertyModifierIntLinearAdditive(
                Callable<IConfigIntGetter> config,
                String tradeoffName,
                int roundTo,
                int offset) {
            super(config, tradeoffName);
            this.roundTo = roundTo;
            this.offset = offset;
        }

        int getMultiplier() {
            try {
                return config.call().getValue();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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

    class PropertyModifierLinearDoubleAdditive extends PropertyModifierLinearAdditive<Double, IConfigDoubleGetter> {
        public PropertyModifierLinearDoubleAdditive(Callable<IConfigDoubleGetter> config, String tradeoffName) {
            super(config, tradeoffName);
        }

        @Override
        public double applyModifier(CompoundTag moduleTag, double value) {
            double multiplier = 0;
            try {
                multiplier = config.call().getValue();
            } catch (Exception ignored) {
            }
            return value + multiplier * TagUtils.getDoubleOrZero(moduleTag, tradeoffName);
        }
    }

    abstract class PropertyModifierLinearAdditive<I extends Number, T extends IConfigValueGetter<I>> implements IPropertyModifier {
        public final String tradeoffName;
        Callable<T> config;

        public PropertyModifierLinearAdditive(Callable<T> config, String tradeoffName) {
            this.tradeoffName = tradeoffName;
            this.config = config;
        }

        @Override
        public abstract double applyModifier(CompoundTag moduleTag, double value);

        public String getTradeoffName() {
            return tradeoffName;
        }
    }


    interface IConfigValueGetter<T> {
        T getValue();
    }

    interface IConfigIntGetter extends IConfigValueGetter<Integer> {
        @Override
        Integer getValue();
    }

    interface IConfigDoubleGetter extends IConfigValueGetter<Double> {
        @Override
        Double getValue();
    }

    interface IConfigBoolGetter extends IConfigValueGetter<Boolean> {
        @Override
        Boolean getValue();
    }
}