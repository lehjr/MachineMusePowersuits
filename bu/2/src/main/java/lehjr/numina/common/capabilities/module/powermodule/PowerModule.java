package lehjr.numina.common.capabilities.module.powermodule;

import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class PowerModule implements IPowerModule {
    protected final ModuleCategory category;
    protected final ModuleTarget target;
    protected Map<String, List<IPropertyModifier>> propertyModifiers;
//    Callable<IConfig> moduleConfigGetter;
    ImmutableList<String> isAllowedConfigKey;
    final String categoryTitle;
//    String translationKey;
//    ResourceLocation regName;

    public PowerModule(
//            String translationKey,
//            ResourceLocation regName,
            ModuleCategory category,
                       ModuleTarget target//,
//                       Callable<IConfig> moduleConfigGetterIn
    ) {
//        this.translationKey = translationKey;
//        this.regName = regName;
        propertyModifiers = new HashMap<>();
        this.category = category;
        this.target = target;
//        this.moduleConfigGetter = moduleConfigGetterIn;
        categoryTitle = category.getConfigTitle().trim().replaceAll(" ", "_");
        isAllowedConfigKey = getConfigKey("isAllowed");
    }

    @Override
    public ModuleTarget getTarget() {
        return target;
    }

    @Override
    public ModuleCategory getCategory() {
        return category;
    }

    /** Double ------------------------------------------------------------------------------------- */
    /**
     * Adds a base key and multiplierValue to the map based on the config setting.
     */
    @Override
    public void addTradeoffProperty(String tradeoffName, String propertyName, double defaultMultiplier) {
        ImmutableList<String> configKey = getConfigKey(new StringBuilder(propertyName).append("_").append(tradeoffName).append("_multiplier").toString());
        addPropertyModifier(propertyName, new PropertyModifierLinearAdditive(configKey, moduleConfigGetter, tradeoffName, defaultMultiplier));
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

    /**
     * Adds a base key and getValue to the map based on the config setting.
     * Also adds a [ propertyName, unitOfMeasureLabel ] k-v pair to a map used for displyaing a label
     */
    @Override
    public void addTradeoffProperty(String tradeoffName, String propertyName, double multiplier, String unit) {
        addUnitLabel(propertyName, unit);
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
        ImmutableList<String> configKey = getConfigKey(new StringBuilder("base_").append(propertyName).toString());
        addPropertyModifier(propertyName, new PropertyModifierFlatAdditive(configKey, moduleConfigGetter, baseVal));
    }

    /**
     * Adds a base key and getValue to the map based on the config setting.
     * Also adds a [ propertyName, unitOfMeasureLabel ] k-v pair to a map used for displyaing a label
     */
    @Override
    public void addBaseProperty(String propertyName, double baseVal, String unit) {
        addUnitLabel(propertyName, unit);
        addBaseProperty(propertyName, baseVal);
    }

    @Override
    public double applyPropertyModifiers(String propertyName) {
        return applyPropertyModifiers(propertyName, TagUtils.getModuleTag(module));
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

    @Override
    public Map<String, List<IPropertyModifier>> getPropertyModifiers() {
        return propertyModifiers;
    }

    /**
     * Integer -----------------------------------------------------------------------------------
     */
    // This is the only one of these that will give an integer using the double system
    public void addIntTradeoffProperty(String tradeoffName, String propertyName, int defaultMultiplier, String unit, int roundTo, int offset) {
        ImmutableList<String> configKey = getConfigKey(new StringBuilder(propertyName).append("_").append(tradeoffName).append("_multiplier").toString());
        addUnitLabel(propertyName, unit);
        addPropertyModifier(propertyName, new PropertyModifierIntLinearAdditive(
                configKey, moduleConfigGetter, tradeoffName, defaultMultiplier, roundTo, offset));
    }

    @Override
    public boolean isAllowed() {
        return getConfig(moduleConfigGetter).map(config-> config.isModuleAllowed(isAllowedConfigKey)).orElse(true);
    }

    public ImmutableList<String> getConfigKey(String entry) {
        return ImmutableList.of(
                "Modules",
                categoryTitle,
                moduleName,
                entry);
    }

    String itemTranslationKeyToConfigKey() {
        // drop the prefix for MPS modules and replace "dots" with underscores
        final String itemPrefix = "item." + regName.getNamespace() + ".";
        if (translationKey.startsWith(itemPrefix )){
            translationKey = translationKey.substring(itemPrefix .length());
        }
        return translationKey.replace(".", "_");
    }
}