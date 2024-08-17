package com.lehjr.numina.common.capabilities.module.powermodule;

import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PowerModule implements IPowerModule {
    final ItemStack module;
    final ModuleCategory category;
    final ModuleTarget target;
    final String categoryTitle; // TODO: replace with translation key?
    Map<String, List<IPropertyModifier>> propertyModifiers;
    boolean isAllowed;

    public PowerModule(ItemStack module,
                       ModuleCategory category,
                       ModuleTarget target) {
        this(module, category, target, false);
    }

    public PowerModule(ItemStack module,
                       ModuleCategory category,
                       ModuleTarget target, boolean isAllowed) {
        this.module = module;
        this.category = category;
        this.target = target;
        this.categoryTitle = category.getConfigTitle().trim().replaceAll(" ", "_");
        this.propertyModifiers = new HashMap<>();
        this.isAllowed = isAllowed;
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
        return isAllowed;
    }

    @Override
    public Map<String, List<IPropertyModifier>> getPropertyModifiers() {
        return propertyModifiers;
    }


    @Override
    public void addBaseProperty(String propertyName, double configValue) {
        addPropertyModifier(propertyName, new PropertyModifierFlatAdditive(configValue));
    }

    @Override
    public void addBaseProperty(String propertyName, double configValue, String unit) {
        UnitMap.addUnitLabel(propertyName, unit);
        addPropertyModifier(propertyName, new PropertyModifierFlatAdditive(configValue));
    }

    @Override
    public void addTradeoffProperty(String tradeoffName, String propertyName, double multiplierConfigValue) {
        addPropertyModifier(propertyName, new PropertyModifierLinearAdditive(multiplierConfigValue, tradeoffName));
    }

    @Override
    public void addTradeoffProperty(String tradeoffName, String propertyName, double mulitiplierConfigValue, String unit) {
        addUnitLabel(propertyName, unit);
        addTradeoffProperty(tradeoffName, propertyName, mulitiplierConfigValue);
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
    public ItemStack save() {
        return getModule();
    }

    @Override
    public void load() {

    }

    @Override
    public void addIntTradeoffProperty(String tradeoffName, String propertyName, int mulitiplierConfigValue, String unit, int roundTo, int offset) {
        addUnitLabel(propertyName, unit);
        addPropertyModifier(propertyName, new PropertyModifierIntLinearAdditive(mulitiplierConfigValue, tradeoffName, roundTo, offset));
    }
}

