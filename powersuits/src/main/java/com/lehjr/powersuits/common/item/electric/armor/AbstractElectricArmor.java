package com.lehjr.powersuits.common.item.electric.armor;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.AdditionalInfo;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.registration.MPSArmorMaterial;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AbstractElectricArmor extends ArmorItem {
    public AbstractElectricArmor(Type pType) {
        super(MPSArmorMaterial.NOTHING, pType, new Properties().setNoRepair());
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
//    protected void addReachModifier(Map<Attribute, AttributeModifier> attributeModifiers, double reach) {
//        attributeModifiers.put((Attribute) Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", reach, AttributeModifier.Operation.ADD_VALUE));
//    }

//    @Override
//    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
//        return super.getArmorTexture(stack, entity, slot, layer, innerModel);
//    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return NuminaConstants.BLANK_ARMOR_MODEL_PATH;
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        EquipmentSlot slot = type.getSlot();
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        ItemAttributeModifiers modifiers = super.getDefaultAttributeModifiers(stack);
        EquipmentSlotGroup equipmentslotgroup = EquipmentSlotGroup.bySlot(slot);
        ResourceLocation resourcelocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());

        for (ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
            builder.add(entry.attribute(), entry.modifier(), equipmentslotgroup);
        }

        double armorVal = 0;
        double toughnessVal = 0;
        double knockbackResistance = 0;
        double speed = 0;
        double movementResistance = 0;
        double swimBoost = 0;
        float stepHeight = 0.5001F;

        IModularItem iModularItem = NuminaCapabilities.getModularItem(stack);
        if(iModularItem != null) {
            // Armor **should** only occupy one slot
            Pair<Integer, Integer> range = iModularItem.getRangeForCategory(ModuleCategory.ARMOR);
            if (range != null) {
                for (int i = range.getFirst(); i < range.getSecond(); i++) {
                    IPowerModule pm = iModularItem.getModuleCapability(iModularItem.getStackInSlot(i));
                    if(pm != null && pm.isAllowed() && pm.isModuleOnline()) {
                        // physical armor and hybrid energy/physical armor
                        armorVal += pm.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_PHYSICAL);
                        armorVal += pm.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_ENERGY);
                        knockbackResistance += pm.applyPropertyModifiers(MPSConstants.KNOCKBACK_RESISTANCE);
                        toughnessVal += pm.applyPropertyModifiers(MPSConstants.ARMOR_TOUGHNESS);
                                        /*
                                        toughness for diamond = 2 per
                                        */
                    }
                }
            }

            if (type.getSlot() == EquipmentSlot.LEGS) {
                for (int i = 0; i < iModularItem.getSlots(); i++) {
                    /** Note: attribute should already be removed when module is offline */
                    IPowerModule iPowerModule = iModularItem.getModuleCapability(iModularItem.getStackInSlot(i));
                    if(iPowerModule != null && iPowerModule.isModuleOnline()) {
                        movementResistance += iPowerModule.applyPropertyModifiers(MPSConstants.MOVEMENT_RESISTANCE);
                        ItemAttributeModifiers moduleModifiers = iPowerModule.getModule().getAttributeModifiers();

                        for (ItemAttributeModifiers.Entry entry : moduleModifiers.modifiers()) {
                            if (entry.attribute().is(Attributes.MOVEMENT_SPEED)) {
                                speed += entry.modifier().amount();
                            }
                            if (entry.attribute().is(NeoForgeMod.SWIM_SPEED)) {
                                swimBoost += entry.modifier().amount();
                            }
                            if (entry.attribute().is(Attributes.STEP_HEIGHT)) {
                                stepHeight = 0.5F;
                            }
                        }
                    }
                }
            }
        }

        if (armorVal > 0) {
            NuminaLogger.logDebug("adding armor value of + " + armorVal + "for slot: " + slot);


            builder.add(Attributes.ARMOR, new AttributeModifier(resourcelocation, armorVal, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(slot));
        }

        if (knockbackResistance > 0) {
            builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourcelocation, knockbackResistance, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(slot));
        }

        if (toughnessVal > 0) {
            builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourcelocation, toughnessVal, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(slot));;
        }

        if (stepHeight > 0.5001F) {
            builder.add(Attributes.STEP_HEIGHT, new AttributeModifier(resourcelocation, stepHeight, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(slot));
        }

        if (speed != 0 || movementResistance != 0) {
            /*
                --------------------------
                kinetic gen max speed hit: 0.025 (total walking speed: 0.075) ( total running speed: 0.08775)
                --------------------------

                -----------------------
                sprint max speed boost: 0.1625 ( total: 0.34125 )
                ----------------------

                walking max speed boost: 0.1 ( total: 0.2 )
                -----------------------

                vanilla walk speed:
                -------------------
                0.13

                vanilla sprint speed:
                ---------------------
                0.1

                resistance should be up to about 80% of walking speed or 0.08
             */

            // up to 80% walking speed restriction
            builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(resourcelocation, (speed - movementResistance * 0.16), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(slot));
        }

        if (swimBoost > 0) {
            builder.add(NeoForgeMod.SWIM_SPEED, new AttributeModifier(resourcelocation, swimBoost, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.bySlot(slot));
        }

        // Create and return ItemAttributeModifiers instance with the list of entries
        return builder.build();
    }

    /*
        returning a value higher than 0 is applied damage to the armor, even if the armor is not setup to take damage.
        TODO: does this even work???? Is this even needed?
    */
    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
        // FIXME: this should probably factor in the damage value
        int enerConsum = 0;
        IModularItem iModularItem = NuminaCapabilities.getModularItem(stack);
        if (iModularItem != null) {
            Pair<Integer, Integer> range = iModularItem.getRangeForCategory(ModuleCategory.ARMOR);
            assert range != null;
            for (int x = range.getFirst(); x < range.getSecond(); x++) {
                IPowerModule pm = iModularItem.getModuleCapability(iModularItem.getStackInSlot(x));
                if (pm != null) {
                    enerConsum += (int) pm.applyPropertyModifiers(MPSConstants.ARMOR_ENERGY_CONSUMPTION);
                }
            }

            // protects as long as there is energy to drain I guess
            if (enerConsum > 0 && entity instanceof LivingEntity) {
                ElectricItemUtils.drainPlayerEnergy(entity, enerConsum, false);
            }
        }
        return 0;
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        IModularItem iModularItem = stack.getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
        if(iModularItem != null) {
            return iModularItem.isModuleOnline(MPSConstants.PIGLIN_PACIFICATION_MODULE);
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);
        AdditionalInfo.appendHoverText(stack, context, components, flag, Screen.hasShiftDown());
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getCapability(Capabilities.EnergyStorage.ITEM) != null;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        double retVal = 1;
        IEnergyStorage iEnergyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if(iEnergyStorage != null) {
            retVal =  iEnergyStorage.getEnergyStored() * 13D / iEnergyStorage.getMaxEnergyStored();
        }
        return (int) retVal;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy == null) {
            return super.getBarColor(stack);
        }
        return Mth.hsvToRgb(Math.max(0.0F, (float) energy.getEnergyStored() / (float) energy.getMaxEnergyStored()) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
//        super.setDamage(stack, damage);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}
