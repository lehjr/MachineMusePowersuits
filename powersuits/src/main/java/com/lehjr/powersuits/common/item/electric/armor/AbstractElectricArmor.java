package com.lehjr.powersuits.common.item.electric.armor;

import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.AdditionalInfo;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.registration.MPSArmorMaterial;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

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
