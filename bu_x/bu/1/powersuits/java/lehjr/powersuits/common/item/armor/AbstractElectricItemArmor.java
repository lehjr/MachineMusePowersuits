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

package lehjr.powersuits.common.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.AtomicDouble;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.energy.ElectricItemUtils;
import com.lehjr.numina.common.string.AdditionalInfo;
import com.lehjr.powersuits.common.capability.PowerArmorCap;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.constants.MPSRegistryNames;
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
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public abstract class AbstractElectricItemArmor extends ArmorItem {


    public AbstractElectricItemArmor(ArmorItem.Type type) {
        super(MPSArmorMaterial.EMPTY_ARMOR, type, new Item.Properties()
                .stacksTo(1)
                .durability(0)
                .setNoRepair());
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

        AtomicDouble armorVal = new AtomicDouble(0);
        AtomicDouble toughnessVal = new AtomicDouble(0);
        AtomicDouble knockbackResistance = new AtomicDouble(0);
        AtomicDouble speed = new AtomicDouble(0);
        AtomicDouble movementResistance = new AtomicDouble(0);

        AtomicDouble swimBoost = new AtomicDouble(0);
        AtomicReference<Float> stepHeight = new AtomicReference<>(0.5001F);

        stack.getCapability(ForgeCapabilities.ITEM_HANDLER)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .ifPresent(iItemHandler -> {

                    // Armor **should** only occupy one slot
                    Pair<Integer, Integer> range = iItemHandler.getRangeForCategory(ModuleCategory.ARMOR);
                    if (range != null) {
                        for (int i = range.getLeft(); i < range.getRight(); i++) {
                            iItemHandler.getStackInSlot(i).getCapability(NuminaCapabilities.POWER_MODULE).ifPresent(pm -> {
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

                    if (slotType == EquipmentSlot.LEGS) {
                        for (int i = 0; i < iItemHandler.getSlots(); i++) {
                            /** Note: attribute should already be removed when module is offline */
                            iItemHandler.getStackInSlot(i).getCapability(NuminaCapabilities.POWER_MODULE)
                                    .filter(IPowerModule.class::isInstance)
                                    .map(IPowerModule.class::cast)
                                    .filter(IPowerModule::isModuleOnline)
                                    .ifPresent(iPowerModule -> {
                                        movementResistance.getAndAdd(iPowerModule.applyPropertyModifiers(MPSConstants.MOVEMENT_RESISTANCE));
                                        iPowerModule.getModule().getAttributeModifiers(slotType).get(Attributes.MOVEMENT_SPEED).forEach(attributeModifier -> speed.getAndAdd(attributeModifier.getAmount()));
                                        iPowerModule.getModule().getAttributeModifiers(slotType).get(ForgeMod.SWIM_SPEED.get()).forEach(attributeModifier -> {
                                            swimBoost.getAndAdd(attributeModifier.getAmount());
                                        });
                                        if (!iPowerModule.getModule().getAttributeModifiers(slotType).get(ForgeMod.STEP_HEIGHT.get()).isEmpty()) {
                                            stepHeight.set(0.5F);
                                        }
                                    });
                        }
                    }
                });

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        for (Map.Entry entry : multimap.entries()) {
            builder.put((Attribute) entry.getKey(), (AttributeModifier) entry.getValue());
        }

        if (armorVal.get() > 0) {
            builder.put(Attributes.ARMOR, new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor modifier", armorVal.get(), AttributeModifier.Operation.ADDITION));
        }

        if (knockbackResistance.get() > 0) {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Knockback resistance", knockbackResistance.get(), AttributeModifier.Operation.ADDITION));
        }

        if (toughnessVal.get() > 0) {
            builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor toughness", toughnessVal.get(), AttributeModifier.Operation.ADDITION));
        }

        if(stepHeight.get() > 0.5001F) {
            builder.put(ForgeMod.STEP_HEIGHT.get(), new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], ForgeMod.STEP_HEIGHT.getId().getNamespace(), stepHeight.get(), AttributeModifier.Operation.ADDITION));
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

            builder.put(Attributes.MOVEMENT_SPEED,
                    new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()],
                            Attributes.MOVEMENT_SPEED.getDescriptionId(),
                            (speed.get() - movementResistance.get() * 0.16), // up to 80% walking speed restriction
                            AttributeModifier.Operation.ADDITION));
        }

        if (swimBoost.get() > 0) {
            builder.put(ForgeMod.SWIM_SPEED.get(),
                    new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()],
                            ForgeMod.SWIM_SPEED.get().getDescriptionId(),
                            swimBoost.get(),
                            AttributeModifier.Operation.ADDITION));
        }

        return builder.build();
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return NuminaConstants.BLANK_ARMOR_MODEL_PATH.toString();
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
//                .orElse(InventoryMenu.BLOCK_ATLAS.toString());
//    }

//    @Override
//    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
//        consumer.accept(new IClientItemExtensions() {
//            @Override
//            public @Nonnull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
//                return itemStack.getCapability(NuminaCapabilities.RENDER).map(spec -> {
//                    CompoundTag renderTag = spec.getRenderTag();
//                    EquipmentSlot slot = Mob.getEquipmentSlotForItem(itemStack);
//                    /** sets up default spec tags. A tag with all parts disabled should still have a color tag rather than being empty or null */
//                    if ((renderTag == null || renderTag.isEmpty()) && livingEntity == Minecraft.getInstance().player && armorSlot == slot) {
//                        renderTag = spec.getDefaultRenderTag();
//                        if (renderTag != null && !renderTag.isEmpty()) {
//                            spec.setRenderTag(renderTag, NuminaConstants.RENDER);
//                            NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(armorSlot, NuminaConstants.RENDER, renderTag));
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
        return new PowerArmorCap(stack, this.type);
    }





    @Override
    public int getBarColor(ItemStack stack) {

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
}
