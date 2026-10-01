package lehjr.numina.common.jei;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.item.Battery;
import lehjr.numina.common.registration.NuminaCodecs;
import lehjr.numina.common.registration.NuminaItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class NuminaJeiPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, "jei_plugin");
    }

    // 1. TELL JEI HOW TO DIFFERENTIATE THEM
    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        List<DeferredHolder<Item, Battery>> batteries = List.of(
            NuminaItems.BATTERY_1,
            NuminaItems.BATTERY_2,
            NuminaItems.BATTERY_3,
            NuminaItems.BATTERY_4
        );

        for (DeferredHolder<Item, Battery> holder : batteries) {
            if (holder.isBound()) {
                registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, holder.get(), (itemStack, context) -> {
                    Integer energy = itemStack.get(NuminaCodecs.ENERGY);
                    if (energy != null && energy > 0) {
                        // Check if it's fully charged or just partially charged
                        IEnergyStorage energyCapability = itemStack.getCapability(Capabilities.EnergyStorage.ITEM);
                        if (energyCapability != null && energy >= energyCapability.getMaxEnergyStored()) {
                            return "charged";
                        }
                        return "charged"; // Grouping all non-empty configurations prevents subtype bloat
                    }
                    return "empty";
                });
            }
        }
    }

    // 2. INJECT BOTH INTO THE JEI INGREDIENT LIST AT RUNTIME
    @Override
    public void onRuntimeAvailable(mezz.jei.api.runtime.IJeiRuntime jeiRuntime) {
        List<ItemStack> batteryVariants = new ArrayList<>();

        List<DeferredHolder<Item, Battery>> batteries = List.of(
            NuminaItems.BATTERY_1,
            NuminaItems.BATTERY_2,
            NuminaItems.BATTERY_3,
            NuminaItems.BATTERY_4
        );

        for (DeferredHolder<Item, Battery> holder : batteries) {
            if (!holder.isBound()) continue;
            Item batteryItem = holder.get();

            // Create Fully Empty Variant
            ItemStack emptyStack = new ItemStack(batteryItem);
            emptyStack.set(NuminaCodecs.ENERGY, 0);
            batteryVariants.add(emptyStack);

            // Create Fully Charged Variant
            ItemStack chargedStack = new ItemStack(batteryItem);
            IEnergyStorage energyCapability = chargedStack.getCapability(Capabilities.EnergyStorage.ITEM);
            if (energyCapability != null) {
                chargedStack.set(NuminaCodecs.ENERGY, energyCapability.getMaxEnergyStored());
            } else {
                chargedStack.set(NuminaCodecs.ENERGY, 1000000);
            }
            batteryVariants.add(chargedStack);
        }

        if (!batteryVariants.isEmpty()) {
            // Correct API call to add extra ItemStacks to the active ingredient list
            jeiRuntime.getIngredientManager().addIngredientsAtRuntime(VanillaTypes.ITEM_STACK, batteryVariants);
        }
    }
}
