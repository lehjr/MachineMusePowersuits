package lehjr.powersuits.common.item.electric.armor;

import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.datafixers.util.Pair;
import com.lehjr.numina.common.capabilities.NuminaCapabilities;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import com.lehjr.numina.common.utils.AdditionalInfo;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.registration.MPSArmorMaterial;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class AbstractElectricArmor extends ArmorItem {
    public static final UUID[] ARMOR_MODIFIERS = new UUID[]{
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID()};

    public AbstractElectricArmor(Type pType) {
        super(MPSArmorMaterial.NOTHING, pType, new Properties().setNoRepair());
    }


//    protected void addReachModifier(Map<Attribute, AttributeModifier> attributeModifiers, double reach) {
//        attributeModifiers.put((Attribute) Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", reach, AttributeModifier.Operation.ADD_VALUE));
//    }

    @Override
    public ItemAttributeModifiers getAttributeModifiers(ItemStack stack) {
        List<ItemAttributeModifiers.Entry> entries = new ArrayList<>();


        ItemAttributeModifiers modifiers = super.getAttributeModifiers(stack);
        EquipmentSlot slot = Mob.getEquipmentSlotForItem(stack);

//            // PropertyModifier tags applied directly to armor will disable the ItemStack sensitive version
//            stack.removeTagKey("AttributeModifiers");
//
//            Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
//
//            if (slot != slot) {
//                return multimap;
//            }

        AtomicDouble armorVal = new AtomicDouble(0);
        AtomicDouble toughnessVal = new AtomicDouble(0);
        AtomicDouble knockbackResistance = new AtomicDouble(0);
        AtomicDouble speed = new AtomicDouble(0);
        AtomicDouble movementResistance = new AtomicDouble(0);

        AtomicDouble swimBoost = new AtomicDouble(0);
        AtomicReference<Float> stepHeight = new AtomicReference<>(0.5001F);

        NuminaCapabilities.getCapability(stack, NuminaCapabilities.Inventory.MODULAR_ITEM).ifPresent(iItemHandler -> {

                    // Armor **should** only occupy one slot
                    Pair<Integer, Integer> range = iItemHandler.getRangeForCategory(ModuleCategory.ARMOR);
                    if (range != null) {
                        for (int i = range.getFirst(); i < range.getSecond(); i++) {
                            NuminaCapabilities.getCapability(iItemHandler.getStackInSlot(i), NuminaCapabilities.Module.POWER_MODULE).ifPresent(pm -> {
                                if (pm.isAllowed()) {
                                    // physical armor and hybrid energy/physical armor
                                    double armorDouble = pm.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_PHYSICAL);
                                    double knockBack = 0;

                                    if (pm instanceof IToggleableModule && pm.isModuleOnline()) {
                                        armorDouble += pm.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_ENERGY);
                                    }

                                    if (armorDouble > 0) {
                                        armorVal.getAndAdd(armorDouble);
                                        knockbackResistance.getAndAdd(pm.applyPropertyModifiers(MPSConstants.KNOCKBACK_RESISTANCE));
                                        toughnessVal.addAndGet(pm.applyPropertyModifiers(MPSConstants.ARMOR_TOUGHNESS));

                                        /*
                                        toughness for diamond = 2 per
                                        */
                                    }
                                }
                            });
                        }
                    }
// FIXME: re-enable when module is ported
//                    if (slot == EquipmentSlot.LEGS) {
//                        for (int i = 0; i < iItemHandler.getSlots(); i++) {
//                            /** Note: attribute should already be removed when module is offline */
//                            NuminaCapabilities.getCapability(iItemHandler.getStackInSlot(i), NuminaCapabilities.PowerModule.POWER_MODULE)
//                                    .filter(IPowerModule::isModuleOnline)
//                                    .ifPresent(iPowerModule -> {
//                                        movementResistance.getAndAdd(iPowerModule.applyPropertyModifiers(MPSConstants.MOVEMENT_RESISTANCE));
//                                        iPowerModule.getModule().getAttributeModifiers(slot).get(Attributes.MOVEMENT_SPEED).forEach(attributeModifier -> speed.getAndAdd(attributeModifier.amount()));
//                                        iPowerModule.getModule().getAttributeModifiers(slot).get(NeoForgeMod.SWIM_SPEED).forEach(attributeModifier -> {
//                                            swimBoost.getAndAdd(attributeModifier.amount());
//                                        });
//                                        if (!iPowerModule.getModule().getAttributeModifiers(slot).get(Attributes.STEP_HEIGHT).isEmpty()) {
//                                            stepHeight.set(0.5F);
//                                        }
//                                    });
//                        }
//                    }
                });


        for (ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
            entries.add(new ItemAttributeModifiers.Entry(entry.attribute(), entry.modifier(), EquipmentSlotGroup.bySlot(slot)));
        }

        if (armorVal.get() > 0) {
            entries.add(new ItemAttributeModifiers.Entry(Attributes.ARMOR,
                    new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor modifier", armorVal.get(), AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.bySlot(slot)));
        }

        if (knockbackResistance.get() > 0) {
            entries.add(new ItemAttributeModifiers.Entry(Attributes.KNOCKBACK_RESISTANCE,
                    new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Knockback resistance", knockbackResistance.get(), AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.bySlot(slot)));
        }

        if (toughnessVal.get() > 0) {
            entries.add(new ItemAttributeModifiers.Entry(Attributes.ARMOR_TOUGHNESS,
                    new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor toughness", toughnessVal.get(), AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.bySlot(slot)));;
        }

        if (stepHeight.get() > 0.5001F) {
            entries.add(new ItemAttributeModifiers.Entry(Attributes.STEP_HEIGHT,
                    new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], Attributes.STEP_HEIGHT.getRegisteredName(), stepHeight.get(), AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.bySlot(slot)));;
        }


        if (speed.get() != 0 || movementResistance.get() != 0) {
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

            entries.add(new ItemAttributeModifiers.Entry(Attributes.MOVEMENT_SPEED,
                    new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()],
                            Attributes.MOVEMENT_SPEED.getRegisteredName(),
                            (speed.get() - movementResistance.get() * 0.16), // up to 80% walking speed restriction
                            AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.bySlot(slot)));
        }

        if (swimBoost.get() > 0) {
            entries.add(new ItemAttributeModifiers.Entry(NeoForgeMod.SWIM_SPEED,
                    new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()],
                            NeoForgeMod.SWIM_SPEED.getRegisteredName(),
                            swimBoost.get(),
                            AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.bySlot(slot)));
        }

        // Create and return ItemAttributeModifiers instance with the list of entries
        return new ItemAttributeModifiers(entries, true);
    }



    /*
        returning a value higher than 0 is applied damage to the armor, even if the armor is not setup to take damage.
        TODO: does this even work???? Is this even needed?

      */
    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Runnable onBroken) {
        // FIXME: this should probably factor in the damage value

        int enerConsum = (int) Math.round(NuminaCapabilities.getCapability(stack, NuminaCapabilities.Inventory.MODULAR_ITEM)
                .map(iItemHandler -> {
                    Pair<Integer, Integer> range = iItemHandler.getRangeForCategory(ModuleCategory.ARMOR);
                    double energyUsed = 0;
                    assert range != null;
                    for (int x = range.getFirst(); x < range.getSecond(); x++) {
                        energyUsed += NuminaCapabilities.getCapability(iItemHandler.getStackInSlot(x), NuminaCapabilities.Module.POWER_MODULE)
                                .map(pm -> pm.applyPropertyModifiers(MPSConstants.ARMOR_ENERGY_CONSUMPTION)).orElse(0D);
                    }
                    return energyUsed;
                }).orElse(0D));

        // protects as long as there is energy to drain I guess
        if (enerConsum > 0 && entity instanceof LivingEntity) {
            ElectricItemUtils.drainPlayerEnergy(entity, enerConsum, false);
        }
        return 0;
    }

// FIXME: re-enable when module is ported
//    @Override
//    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
//        return NuminaCapabilities.getCapability(stack, NuminaCapabilities.Inventory.MODULAR_ITEM)
//                .map(iModularItem -> iModularItem.isModuleOnline(MPSConstants.PIGLIN_PACIFICATION_MODULE)).orElse(false);
//    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);
        AdditionalInfo.appendHoverText(stack, context, components, flag, Screen.hasShiftDown());
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return NuminaCapabilities.getCapability(stack, Capabilities.EnergyStorage.ITEM).map(iEnergyStorage -> iEnergyStorage.getMaxEnergyStored() > 1).orElse(false);
    }


    @Override
    public int getBarWidth(ItemStack stack) {
        double retVal = NuminaCapabilities.getCapability(stack, Capabilities.EnergyStorage.ITEM).map(iEnergyStorage -> iEnergyStorage.getEnergyStored() * 13D / iEnergyStorage.getMaxEnergyStored()).orElse(1D);
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
