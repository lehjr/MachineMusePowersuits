package lehjr.numina.common.utils;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.item.ComponentItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdditionalInfo {
    public static void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag, boolean doAdditionalInfo) {
        NuminaCapabilities.getOptionalModularItemOrModeChangingCapability(stack).ifPresent(iModularItem -> {
            // Mode changing item such as power fist
            if (iModularItem instanceof IModeChangingItem) {
                ItemStack activeModule = ((IModeChangingItem) iModularItem).getActiveModule();
                if (!activeModule.isEmpty()) {

                    // MutableComponent
                    // Component.translatable
                    MutableComponent localizedName = (MutableComponent) activeModule.getDisplayName();
                    components.add(
                            Component.translatable(NuminaConstants.TOOLTIP_MODE)
//                                        .appendString(" ")
                                    .append(Component.literal(" "))
                                    .append(localizedName.setStyle(Style.EMPTY.applyFormat(ChatFormatting.RED))));
                } else {
                    components.add(Component.translatable(NuminaConstants.TOOLTIP_CHANGE_MODES));
                }
            }

            if (doAdditionalInfo) {
                List<Component> installed = new ArrayList<>();
                Map<Component, FluidInfo> fluids = new HashMap<>();

                for (ItemStack module : iModularItem.getInstalledModules()) {
                    installed.add(((MutableComponent)module.getDisplayName()).setStyle(Style.EMPTY.applyFormat((ChatFormatting.LIGHT_PURPLE))));

                    // check mpodule for fluid
                    NuminaCapabilities.getCapability(module, Capabilities.FluidHandler.ITEM).ifPresent(fluidHandler ->{
                        int numTanks = fluidHandler.getTanks();

                        for(int i=0; i < numTanks; i++) {
                            FluidStack fluidStack = fluidHandler.getFluidInTank(i);
                            if (fluidStack.isEmpty()) {
                                continue;
                            }
                            int capacity = fluidHandler.getTankCapacity(i);

                            Component fluidName = fluidHandler.getFluidInTank(i).getHoverName();
                            FluidInfo fluidInfo = fluids.getOrDefault(fluidName, new FluidInfo(fluidName)).addAmmount(fluidStack.getAmount()).addMax(capacity);
                            fluids.put(fluidName, fluidInfo);
                        }
                    });
                }

                if (!fluids.isEmpty()) {
                    for(FluidInfo info : fluids.values()) {
                        components.add(info.getOutput());
                    }
                }

                if (installed.isEmpty()) {
                    Component message = Component.translatable(NuminaConstants.TOOLTIP_NO_MODULES);
                    components.addAll(StringUtils.wrapComponentToLength(message, 30));
                } else {
                    components.add(Component.translatable(NuminaConstants.TOOLTIP_INSTALLED_MODULES));
                    components.addAll(installed);
                }
            } else {
                components.add(additionalInfoInstructions());
            }
        });

        IPowerModule pm = stack.getCapability(NuminaCapabilities.Module.POWER_MODULE);
        if(pm != null || stack.getItem() instanceof ComponentItem) {
            addDesc(stack, components, doAdditionalInfo);
        }

        NuminaCapabilities.getCapability(stack, Capabilities.EnergyStorage.ITEM).ifPresent(energyCap->
                // FIXME use Component.translatable??? !!!
                components.add(Component.literal(I18n.get(NuminaConstants.TOOLTIP_BATTERY_ENERGY,
                                StringUtils.formatNumberShort(energyCap.getEnergyStored()),
                                StringUtils.formatNumberShort(energyCap.getMaxEnergyStored())))
                        .setStyle(Style.EMPTY.applyFormat(ChatFormatting.AQUA).withItalic(true))));
    }

    public static void addDesc(ItemStack stack, List<Component> components, boolean doAdditionalInfo) {
        if (doAdditionalInfo) {
            Component description = Component.translatable( stack.getItem().getDescriptionId() + ".desc");
            components.addAll(StringUtils.wrapComponentToLength(description, 30));
        } else {
            components.add(additionalInfoInstructions());
        }
    }

    static class FluidInfo {
        Component displayName;
        int currentAmount=0;
        int maxAmount=0;

        FluidInfo(Component displayName) {
            this.displayName = displayName;
        }

        public Component getDisplayName() {
            return displayName;
        }

        public int getMaxAmount() {
            return maxAmount;
        }

        public int getCurrentAmount() {
            return currentAmount;
        }

        public FluidInfo addMax(int maxAmountIn) {
            maxAmount += maxAmountIn;
            return this;
        }

        public FluidInfo addAmmount(int currentAmountIn) {
            currentAmount += currentAmountIn;
            return this;
        }

        public Component getOutput() {
            return displayName.copy().append(Component.literal(": ")).append(Component.literal("/" + maxAmount))
                    .setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_AQUA).withItalic(true));
        }
    }

    public static Component additionalInfoInstructions() {
        return Component.translatable(NuminaConstants.TOOLTIP_PRESS_SHIFT)
                .setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY).withItalic(true));
    }
}