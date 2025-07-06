package com.lehjr.powersuits.common.item.electric.armor;

import com.google.common.util.concurrent.AtomicDouble;
import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.AdditionalInfo;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.registration.MPSArmorMaterial;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
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

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
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
        ItemAttributeModifiers modifiers = super.getDefaultAttributeModifiers(stack);

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

        if(true) {
            return builder.build();
        }

        if (stack.getItem() instanceof ArmorItem) {
            EquipmentSlot slotType = ItemUtils.getEquipmentSlotForItem(stack);

            EquipmentSlotGroup equipmentslotgroup = EquipmentSlotGroup.bySlot(type.getSlot());
            ResourceLocation resourcelocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());

            double armorVal = 0;
            double toughness = 0;
            double knockbackResistance = 0;
            double speed = 0;
            double movementResistance = 0;
            double swimBoost = 0;
            float stepHeight = 0.5001F;

            IModularItem iModularItem = stack.getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);

            if(iModularItem != null) {
                // Armor **should** only occupy one slot
                Pair<Integer, Integer> range = iModularItem.getRangeForCategory(ModuleCategory.ARMOR);
                if (range != null) {
                    for (int i = range.getFirst(); i < range.getSecond(); i++) {
                        IPowerModule pm = iModularItem.getStackInSlot(i).getCapability(NuminaCapabilities.Module.POWER_MODULE);
                        if(pm != null) {
                            if (pm.isAllowed()) {
                                // physical armor and hybrid energy/physical armor
                                double armorDouble = pm.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_PHYSICAL);

                                if (pm instanceof IToggleableModule && pm.isModuleOnline()) {
                                    armorDouble += pm.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_ENERGY);
                                }

                                if (armorDouble > 0) {
                                    armorVal += armorDouble;
                                    knockbackResistance += pm.applyPropertyModifiers(MPSConstants.KNOCKBACK_RESISTANCE);
                                    toughness += pm.applyPropertyModifiers(MPSConstants.ARMOR_TOUGHNESS);
                                            /*
                                                toughness for diamond = 2 per
                                            */
                                }
                            }
                        }
                    }
                }

                //                BiConsumer<Holder<Attribute>, AttributeModifier> test = (a, m)-> NuminaLogger.logDebug("attribute: " + a + ", modifier: " + m);
                //
                ///*
                //[12:19:52] [Render thread/INFO] [co.le.nu.co.ba.NuminaLogger/]: attribute: Reference{ResourceKey[minecraft:attribute / minecraft:generic.armor]=net.minecraft.world.entity.ai.attributes.RangedAttribute@b7b81ca}, modifier: AttributeModifier[id=minecraft:armor.helmet, amount=0.0, operation=ADD_VALUE]
                //[12:19:52] [Render thread/INFO] [co.le.nu.co.ba.NuminaLogger/]: attribute: Reference{ResourceKey[minecraft:attribute / minecraft:generic.armor_toughness]=net.minecraft.world.entity.ai.attributes.RangedAttribute@6eddbf90}, modifier: AttributeModifier[id=minecraft:armor.helmet, amount=0.0, operation=ADD_VALUE]
                //[12:19:52] [Render thread/INFO] [co.le.nu.co.ba.NuminaLogger/]: slotType HEAD
                // */
                //                // BiConsumer<Holder<Attribute>, AttributeModifier> action
                //                modifiers.forEach(equipmentslotgroup, test);



                if (slotType == EquipmentSlot.LEGS) {
                    for (int i = 0; i < iModularItem.getSlots(); i++) {
                        /** Note: attribute should already be removed when module is offline */
                        IPowerModule iPowerModule = iModularItem.getStackInSlot(i).getCapability(NuminaCapabilities.Module.POWER_MODULE);
                        if(iPowerModule != null) {
//                            movementResistance += iPowerModule.applyPropertyModifiers(MPSConstants.MOVEMENT_RESISTANCE);
//                            iPowerModule.getModule().getAttributeModifiers(slotType).get(Attributes.MOVEMENT_SPEED).forEach(attributeModifier -> speed.getAndAdd(attributeModifier.getAmount()));
//                            iPowerModule.getModule().getAttributeModifiers(slotType).get(NeoForgeMod.SWIM_SPEED)).forEach(attributeModifier -> {
//                                swimBoost.getAndAdd(attributeModifier.getAmount());
//                            });
//                            if (!iPowerModule.getModule().getAttributeModifiers(iPowerModule.getModule()).get(Attributes.STEP_HEIGHT).isEmpty()) {
//                                stepHeight =0.5F;
//                            }
                        }
                    }
                }
            }

            if (armorVal > 0) {
                builder.add(Attributes.ARMOR, new AttributeModifier(resourcelocation, armorVal, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
            }

            if (knockbackResistance > 0) {
                builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourcelocation, knockbackResistance, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
            }

            if (toughness > 0) {
                builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourcelocation, toughness, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
            }

            if(stepHeight > 0.5001F) {
                builder.add(Attributes.STEP_HEIGHT, new AttributeModifier(resourcelocation, stepHeight, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
            }

/*
        this.defaultModifiers = Suppliers.memoize(() -> {
            int i = ((ArmorMaterial)material.value()).getDefense(type);
            float f = ((ArmorMaterial)material.value()).toughness();
            ItemAttributeModifiers.Builder itemattributemodifiers$builder = ItemAttributeModifiers.builder();
            EquipmentSlotGroup equipmentslotgroup = EquipmentSlotGroup.bySlot(type.getSlot());
            ResourceLocation resourcelocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());
            itemattributemodifiers$builder.add(Attributes.ARMOR, new AttributeModifier(resourcelocation, (double)i, Operation.ADD_VALUE), equipmentslotgroup);

            float f1 = ((ArmorMaterial)material.value()).knockbackResistance();
            if (f1 > 0.0F) {
                itemattributemodifiers$builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourcelocation, (double)f1, Operation.ADD_VALUE), equipmentslotgroup);
            }

            return itemattributemodifiers$builder.build();
        });
    }
 */
            //            for (Map.Entry entry : multimap.entries()) {
            //                builder.add((Attribute) entry.getKey(), (AttributeModifier) entry.getValue());
            //            }


            //
            //
            //            if (speed.get() != 0 || movementResistance.get() != 0) {
            //                        /*
            //                            --------------------------
            //                            kinetic gen max speed hit: 0.025 (total walking speed: 0.075) ( total running speed: 0.08775)
            //                            --------------------------
            //
            //                            -----------------------
            //                            sprint max speed boost: 0.1625 ( total: 0.34125 )
            //                            ----------------------
            //
            //                            walking max speed boost: 0.1 ( total: 0.2 )
            //                            -----------------------
            //
            //                            vanilla walk speed:
            //                            -------------------
            //                            0.13
            //
            //                            vanilla sprint speed:
            //                            ---------------------
            //                            0.1
            //
            //                            resistance should be up to about 80% of walking speed or 0.08
            //                         */
            //
            //                builder.add((Attributes.MOVEMENT_SPEED,
            //                    new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()],
            //                        Attributes.MOVEMENT_SPEED.getDescriptionId(),
            //                        (speed.get() - movementResistance.get() * 0.16), // up to 80% walking speed restriction
            //                        AttributeModifier.Operation.ADDITION));
            //            }
            //
            //            if (swimBoost.get() > 0) {
            //                builder.add(NeoForgeMod.SWIM_SPEED,
            //                    new AttributeModifier(
            //                        ARMOR_MODIFIERS[slot.getIndex()],
            //                        NeoForgeMod.SWIM_SPEED.getRegisteredName(),
            //                        swimBoost.get(),
            //                        AttributeModifier.Operation.ADD_VALUE));
            //            }

            return builder.build();
        }
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
