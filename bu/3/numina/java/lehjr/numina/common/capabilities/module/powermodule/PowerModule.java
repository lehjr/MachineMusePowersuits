package lehjr.numina.common.capabilities.module.powermodule;

import lehjr.numina.common.utils.TagUtils;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class PowerModule implements IPowerModule {
    final ModuleCategory category;
    final ModuleTarget target;
    final String categoryTitle; // TODO: replace with translation key?

    Map<String, List<IPropertyModifier>> propertyModifiers;

    public PowerModule(ModuleCategory category,
                       ModuleTarget target) {
        this.category = category;
        this.target = target;
        this.categoryTitle = category.getConfigTitle().trim().replaceAll(" ", "_");
        this.propertyModifiers = new HashMap<>();
    }

    @Override
    public ModuleCategory getCategory() {
        return category;
    }

    @Override
    public ModuleTarget getTarget() {
        return target;
    }

    /**
     * Override with proper config key
     * @return
     */
    @Override
    public boolean isAllowed() {
        return false;
    }

    @Override
    public Map<String, List<IPropertyModifier>> getPropertyModifiers() {
        return propertyModifiers;
    }

    @Override
    public void addBaseProperty(Callable<IConfigDoubleGetter> configBase, String propertyName, double baseVal, double propFromConfig) {
        addPropertyModifier(propertyName, new PropertyModifierFlatAdditive(configBase, propFromConfig));
    }

    @Override
    public void addBaseProperty(Callable<IConfigDoubleGetter> configBase, String propertyName, double baseVal, double multiplier, String unit) {
        UnitMap.addUnitLabel(propertyName, unit);
        addPropertyModifier(propertyName, new PropertyModifierFlatAdditive(configBase, multiplier));
    }

    @Override
    public void addTradeoffProperty(Callable<IConfigDoubleGetter> configMultiplier, String tradeoffName, String propertyName, double multiplier) {
        addPropertyModifier(propertyName, new PropertyModifierLinearDoubleAdditive(configMultiplier, tradeoffName, multiplier));
    }

    @Override
    public void addTradeoffProperty(Callable<IConfigDoubleGetter> configMultiplier, String tradeoffName, String propertyName, double multiplier, String unit) {
        addUnitLabel(propertyName, unit);
        addTradeoffProperty(configMultiplier, tradeoffName, propertyName, multiplier);
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

    @Override
    public void addIntTradeoffProperty(Callable<IConfigIntGetter> config, String tradeoffName, String propertyName, int defaultMultiplier, String unit, int roundTo, int offset) {
        addUnitLabel(propertyName, unit);
        addPropertyModifier(propertyName, new PropertyModifierIntLinearAdditive(config, tradeoffName, defaultMultiplier, roundTo, offset));
    }

    @Override
    public double applyPropertyModifiers(String propertyName) {
        return applyPropertyModifiers(propertyName, TagUtils.getTag(getModule()));
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
}
