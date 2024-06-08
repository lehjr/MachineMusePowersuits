package lehjr.numina.common.capabilities.module.powermodule;

import lehjr.numina.common.utils.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class PowerModule implements IPowerModule {
    final ModuleCategory category;
    final ModuleTarget target;
    final String categoryTitle; // TODO: replace with translation key?
    final ItemStack module;

    Map<String, List<IPropertyModifier>> propertyModifiers;

    public PowerModule(ItemStack module, ModuleCategory category,
                       ModuleTarget target) {
        this.module = module;
        this.category = category;
        this.target = target;
        this.categoryTitle = category.getConfigTitle().trim().replaceAll(" ", "_");
        this.propertyModifiers = new HashMap<>();
    }

    @Override
    public ItemStack getModule() {
        return module;
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
     * @return if module is allowed to be used
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
    public void addBaseProperty(String propertyName, Callable<IConfigDoubleGetter> configValGetter) {
        addPropertyModifier(propertyName, new PropertyModifierFlatAdditive(configValGetter));
    }

    @Override
    public void addBaseProperty(String propertyName, Callable<IConfigDoubleGetter> configBase, String unit) {
        UnitMap.addUnitLabel(propertyName, unit);
        addPropertyModifier(propertyName, new PropertyModifierFlatAdditive(configBase));
    }

    @Override
    public void addTradeoffProperty(Callable<IConfigDoubleGetter> configMultiplier, String tradeoffName, String propertyName) {
        addPropertyModifier(propertyName, new PropertyModifierLinearDoubleAdditive(configMultiplier, tradeoffName));
    }

    @Override
    public void addTradeoffProperty(Callable<IConfigDoubleGetter> configMultiplier, String tradeoffName, String propertyName, String unit) {
        addUnitLabel(propertyName, unit);
        addTradeoffProperty(configMultiplier, tradeoffName, propertyName);
    }

    @Override
    public void addPropertyModifier(String propertyName, IPropertyModifier modifier) {
        List<IPropertyModifier> modifiers = propertyModifiers.get(propertyName);
        if (modifiers == null) {
            modifiers = new LinkedList<>();
        }
        modifiers.add(modifier);
        propertyModifiers.put(propertyName, modifiers);
    }

    @Override
    public void addIntTradeoffProperty(Callable<IConfigIntGetter> config, String tradeoffName, String propertyName, String unit, int roundTo, int offset) {
        addUnitLabel(propertyName, unit);
        addPropertyModifier(propertyName, new PropertyModifierIntLinearAdditive(config, tradeoffName, roundTo, offset));
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

