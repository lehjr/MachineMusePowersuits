/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.common.item.electric.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.datafixers.util.Pair;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.AdditionalInfo;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.capability.legacy.PowerArmorLegacyCap;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class ElectricArmor extends ArmorItem {
    public static final UUID[] ARMOR_MODIFIERS = new UUID[]{
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID()};

    public ElectricArmor(ArmorItem.Type type) {
        super(MPSArmorMaterial.EMPTY_ARMOR, type, new Item.Properties()
                .stacksTo(1)
                .durability(0)
                .setNoRepair());
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        IModularItem iModularItem =  stack.getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM).resolve().orElse(null);
        if(iModularItem != null) {
            return iModularItem.isModuleOnline(MPSConstants.GLIDER_MODULE);
        }
        return false;
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        // FIXME: module? also address frostbite
        return this.type == Type.BOOTS;
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        return true;
    }

    //    /*
//        returning a value higher than 0 is applied damage to the armor, even if the armor is not setup to take damage.
//        TODO: does this even work???? Is this even needed?
//
//      */
    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        // FIXME: this should probably factor in the damage value
        int enerConsum = 0;
        IModularItem iModularItem = NuminaCapabilities.getModularItem(stack);
        if (iModularItem != null) {
            Pair<Integer, Integer> range = iModularItem.getRangeForCategory(ModuleCategory.ARMOR);
            for (int x = range.getFirst(); x < range.getSecond(); x++) {
                IPowerModule pm = iModularItem.getModuleCapability(iModularItem.getStackInSlot(x));
                if (pm != null) {
                    enerConsum += (int) pm.applyPropertyModifiers(MPSConstants.ARMOR_ENERGY_CONSUMPTION);
                }
            }

            // protects as long as there is energy to drain I guess
            if (enerConsum > 0 && entity != null) {
                ElectricItemUtils.drainPlayerEnergy(entity, enerConsum, false);
            }
        }
        return 0;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        // PropertyModifier tags applied directly to armor will disable the ItemStack sensitive version
        stack.removeTagKey("AttributeModifiers");

        Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        EquipmentSlot slotType = Mob.getEquipmentSlotForItem(stack);
        if (slot != slotType) {
            return multimap;
        }

        double armorVal = 0;
        double toughnessVal = 0;
        double knockbackResistance = 0;
        double speed = 0;
        double movementResistance = 0;
        float stepHeight = 0.5001F;

        IModularItem iModularItem = NuminaCapabilities.getModularItem(stack);
        if(iModularItem != null) {
            movementResistance = iModularItem.getMovementResistance();
            Pair<Integer, Integer> range = iModularItem.getRangeForCategory(ModuleCategory.ARMOR);
            if (range != null) {
                for (int i = range.getFirst(); i < range.getSecond(); i++) {
                    if(iModularItem.isSlotValid(i)) {
                        IPowerModule pm = iModularItem.getModuleCapability(iModularItem.getStackInSlot(i));
                        if(pm != null && pm.isAllowed() && pm.isModuleOnline()) {
                            armorVal += pm.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_PHYSICAL);
                            if (pm instanceof IToggleableModule && pm.isModuleOnline()) {
                                armorVal += pm.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_ENERGY);
                            }
                            knockbackResistance += pm.applyPropertyModifiers(MPSConstants.KNOCKBACK_RESISTANCE);
                            toughnessVal += pm.applyPropertyModifiers(MPSConstants.ARMOR_TOUGHNESS);

//                            if(slot == EquipmentSlot.LEGS) {
//                                pm.getModule().getAttributeModifiers(slotType).get(Attributes.MOVEMENT_SPEED).forEach(attributeModifier -> speed +=. getAndAdd(attributeModifier.getAmount()));
//                                if (!pm.getModule().getAttributeModifiers(slotType).get(ForgeMod.STEP_HEIGHT_ADDITION.get()).isEmpty()) {
//                                    stepHeight = 0.5F;
//                                }
//                            }
                        }
                    }
                }
            }
            if(slot == EquipmentSlot.LEGS) {
                int index = iModularItem.findInstalledModule(MPSConstants.CLIMB_ASSIST_MODULE);
                if (iModularItem.isSlotValid(index)) {
                    IPowerModule pm = iModularItem.getModuleCapability(iModularItem.getStackInSlot(index));
                    if (pm != null && pm.isAllowed() && pm.isModuleOnline()) {
                        stepHeight = (float) pm.applyPropertyModifiers(MPSConstants.STEP_HEIGHT);
                    } else {
                        stepHeight = 0.5F;
                    }
                } else {
                    stepHeight = 0.5F;
                }
            }
        }

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        for (Map.Entry entry : multimap.entries()) {
            builder.put((Attribute) entry.getKey(), (AttributeModifier) entry.getValue());
        }

        if (armorVal > 0) {
            builder.put(Attributes.ARMOR, new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor modifier", armorVal, AttributeModifier.Operation.ADDITION));
        }

        if (knockbackResistance > 0) {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Knockback resistance", knockbackResistance, AttributeModifier.Operation.ADDITION));
        }

        if (toughnessVal > 0) {
            builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor toughness", toughnessVal, AttributeModifier.Operation.ADDITION));
        }

        if(stepHeight > 0.5001F) {
            builder.put(ForgeMod.STEP_HEIGHT_ADDITION.get(), new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], ForgeMod.STEP_HEIGHT_ADDITION.getId().getNamespace(), stepHeight, AttributeModifier.Operation.ADDITION));
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

//            builder.put(Attributes.MOVEMENT_SPEED,
//                    new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()],
//                            Attributes.MOVEMENT_SPEED.getDescriptionId(),
//                            (speed - movementResistance * 0.16), // up to 80% walking speed restriction
//                            AttributeModifier.Operation.ADDITION));
        }

//        if (swimBoost.get() > 0) {
//            builder.put(ForgeMod.SWIM_SPEED.get(),
//                    new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()],
//                            ForgeMod.SWIM_SPEED.get().getDescriptionId(),
//                            swimBoost.get(),
//                            AttributeModifier.Operation.ADDITION));
//        }

        return builder.build();
    }




    //    /**
//     * This will work for the vanilla type models. This will not work for high polly models due to how the rendering works
//     *
//     * @param armor
//     * @param entity
//     * @param equipmentSlotType
//     * @param type
//     * @return
//     */
//    @Nullable
//    @Override
//    public String getArmorTexture(ItemStack armor, Entity entity, EquipmentSlot equipmentSlotType, String type) {
//        if (type == "overlay") { // this is to allow a tint to be applied tot the armor
//            return NuminaConstants.BLANK_ARMOR_MODEL_PATH.toString();
//        }
//
//        if (!equipmentSlotType.equals(armor.getEquipmentSlot())) {
//            return NuminaConstants.BLANK_ARMOR_MODEL_PATH.toString();
//        }
//
//        if (entity instanceof LivingEntity) {
//            if (armor.getCapability(ForgeCapabilities.ITEM_HANDLER)
//                    .filter(IModularItem.class::isInstance)
//                    .map(IModularItem.class::cast)
//                    .map(iItemHandler -> iItemHandler.isModuleOnline(MPSRegistryNames.TRANSPARENT_ARMOR_MODULE)).orElse(false)) {
//                return NuminaConstants.BLANK_ARMOR_MODEL_PATH.toString();
//            }
//        }
//
//        return armor.getCapability(NuminaCapabilities.RENDER)
//                .filter(IArmorModelSpecNBT.class::isInstance)
//                .map(IArmorModelSpecNBT.class::cast)
//                .map(spec -> {
//                    if (spec.getSpecList() == SpecType.NONE) {
//                        return NuminaConstants.BLANK_ARMOR_MODEL_PATH.toString();
//                    }
//
//                    if (spec.getRenderTag() != null && spec.getRenderTag().isEmpty()) {
//                        return NuminaConstants.BLANK_ARMOR_MODEL_PATH.toString();
//                    }
//                    return spec.getArmorTexture().toString();
//                })
//                .orElse(TextureAtlas.LOCATION_BLOCKS.toString());
//    }

//    @Override
//    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
//        consumer.accept(new IClientItemExtensions() {
//            @Override
//            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
//                return itemStack.getCapability(NuminaCapabilities.RENDER).map(spec -> {
//                    CompoundTag renderTag = spec.getRenderTag();
//                    EquipmentSlot slot = Mob.getEquipmentSlotForItem(itemStack);
//                    /** sets up default spec tags. A tag with all parts disabled should still have a color tag rather than being empty or null */
//                    if ((renderTag == null || renderTag.isEmpty()) && livingEntity == Minecraft.getInstance().player && armorSlot == slot) {
//                        renderTag = spec.getDefaultRenderTag();
//                        if (renderTag != null && !renderTag.isEmpty()) {
//                            spec.setRenderTag(renderTag, TagConstants.RENDER);
//                            NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(armorSlot, TagConstants.RENDER, renderTag));
//                        }
//                    }
//
//                    /** Armor skin uses vanilla model, but returning _default for EnumSpecType.NONE renders a garbage model */
//                    if (spec.getRenderTag() != null && (spec.getSpecList() == SpecType.ARMOR_SKIN)) {
//                        return _default;
//                    }
//
//                    HumanoidModel model = ArmorModelInstance.getInstance();
//                    ItemStack chestplate = ItemUtils.getItemFromEntitySlot(livingEntity, EquipmentSlot.CHEST);
//                    if (chestplate.getCapability(ForgeCapabilities.ITEM_HANDLER)
//                            .filter(IModularItem.class::isInstance)
//                            .map(IModularItem.class::cast)
//                            .map(iItemHandler -> iItemHandler.isModuleOnline(MPSRegistryNames.ACTIVE_CAMOUFLAGE_MODULE)).orElse(false)) {
//                        ((HighPolyArmor) model).setVisibleSection(null);
//                    } else {
//                        if (renderTag != null) {
//                            ((HighPolyArmor) model).setVisibleSection(slot);
//                            ((HighPolyArmor) model).setRenderSpec(renderTag);
//                        }
//                    }
//                    return model;
//                }).orElse(_default);
//            }
//        });
//    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        IModularItem iModularItem = stack.getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM).resolve().orElse(null);
        if(iModularItem != null) {
            return iModularItem.isModuleOnline(MPSConstants.PIGLIN_PACIFICATION_MODULE);
        }
        return false;
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return NuminaConstants.BLANK_ARMOR_MODEL_PATH.toString();
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (worldIn != null) {
            AdditionalInfo.appendHoverText(stack, worldIn, tooltip, flagIn);
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundTag nbt) {
        assert stack != null;
        return new PowerArmorLegacyCap(stack, this.type);
    }

    @Override
    public boolean canBeDepleted() {
        return false;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    /** Durability bar for showing energy level ------------------------------------------------------------------ */
    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.ENERGY).map(iEnergyStorage -> iEnergyStorage.getMaxEnergyStored() > 1).orElse(false);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        double retVal = 1;
        IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY).resolve().orElse(null);
        if(energy != null) {
            retVal =  energy.getEnergyStored() * 13D / energy.getMaxEnergyStored();
        }
        return (int) retVal;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY).resolve().orElse(null);
        if (energy == null) {
            return super.getBarColor(stack);
        }
        return Mth.hsvToRgb(Math.max(0.0F, (float) energy.getEnergyStored() / (float) energy.getMaxEnergyStored()) / 3.0F, 1.0F, 1.0F);
    }
}