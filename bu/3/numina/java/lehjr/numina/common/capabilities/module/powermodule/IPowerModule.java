package lehjr.numina.common.capabilities.module.powermodule;

import lehjr.numina.common.utils.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
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

    boolean isAllowed();

    default boolean isModuleOnline() {
        return true;
    }

    default void addUnitLabel(@Nonnull String propertyName, String unit) {
        UnitMap.addUnitLabel(propertyName, unit);
    }

    Map<String, List<IPropertyModifier>> getPropertyModifiers();

    @Nonnull
    default ItemStack getModule() {
        return ItemStack.EMPTY;
    }

    default int getEnergyUsage() {
        return 0;
    }

    void addBaseProperty(Callable<IConfigDoubleGetter> config, String propertyName, double baseVal, double propFromConfig);

    void addBaseProperty(Callable<IConfigDoubleGetter> config, String propertyName, double baseVal, double multiplier, String unit);

    void addTradeoffProperty(Callable<IConfigDoubleGetter> configMultiplier, String tradeoffName, String propertyName, double multiplier);

    void addTradeoffProperty(Callable<IConfigDoubleGetter> configMultiplier, String tradeoffName, String propertyName, double multiplier, String unit);

    void addIntTradeoffProperty(Callable<IConfigIntGetter> config, String tradeoffName, String propertyName, int defaultMultiplier, String unit, int roundTo, int offset);

    void addPropertyModifier(String propertyName, IPropertyModifier modifier);

    double applyPropertyModifiers(String propertyName);

    double applyPropertyModifiers(String propertyName, CompoundTag moduleTag);

    interface IPropertyModifier {
        double applyModifier(CompoundTag moduleTag, double value);
    }

    class PropertyModifierFlatAdditive implements IPropertyModifier {
        Callable<IConfigDoubleGetter> config;
        double defaultVal;

        public PropertyModifierFlatAdditive(Callable<IConfigDoubleGetter> config, double baseVal) {
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
            try {
                return value + config.call().getValueOrDefault(defaultVal);
            } catch (Exception ignored) {
            }
            return value + defaultVal;
        }
    }

    class PropertyModifierIntLinearAdditive extends PropertyModifierLinearAdditive<Integer, IConfigIntGetter> {
        protected int roundTo = 1;
        protected int offset = 0;

        public PropertyModifierIntLinearAdditive(
                Callable<IConfigIntGetter> config,
                String tradeoffName,
                double defaultMultiplier,
                int roundTo,
                int offset) {
            super(config, tradeoffName, defaultMultiplier);
            this.roundTo = roundTo;
            this.offset = offset;
        }

        @Override
        public double applyModifier(CompoundTag moduleTag, double value) {
            int multiplier = 0;
            try {
                multiplier = ((IConfigIntGetter)config.call()).getValueOrDefault((int) defaultMultiplier);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

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

    class PropertyModifierLinearDoubleAdditive extends PropertyModifierLinearAdditive<Double, IConfigDoubleGetter> {
        public PropertyModifierLinearDoubleAdditive(Callable<IConfigDoubleGetter> config, String tradeoffName, double defaultMultiplier) {
            super(config, tradeoffName, defaultMultiplier);
        }

        @Override
        public double applyModifier(CompoundTag moduleTag, double value) {
            double multiplier = 0;
            try {
                multiplier = ((IConfigDoubleGetter)config.call()).getValueOrDefault(defaultMultiplier);
            } catch (Exception ignored) {
            }
            return value + multiplier * TagUtils.getDoubleOrZero(moduleTag, tradeoffName);
        }

        public String getTradeoffName() {
            return tradeoffName;
        }
    }


    abstract class PropertyModifierLinearAdditive<I extends Number, T extends IConfigValueGetter<I>> implements IPropertyModifier {
        public final String tradeoffName;
        public double defaultMultiplier;
        Callable<T> config;

        public PropertyModifierLinearAdditive(Callable<T> config, String tradeoffName, double defaultMultiplier) {
            this.defaultMultiplier = defaultMultiplier;
            this.tradeoffName = tradeoffName;
            this.config = config;
        }

        @Override
        public abstract double applyModifier(CompoundTag moduleTag, double value);
    }


    interface IConfigValueGetter<T> {
        T getValueOrDefault(T defaultValue);
    }

    interface IConfigIntGetter extends IConfigValueGetter<Integer> {
        @Override
        Integer getValueOrDefault(Integer defaultValue);
    }

    interface IConfigDoubleGetter extends IConfigValueGetter<Double> {
        @Override
        Double getValueOrDefault(Double defaultValue);
    }

    interface IConfigBoolGetter extends IConfigValueGetter<Boolean> {
        @Override
        Boolean getValueOrDefault(Boolean defaultValue);
    }
}