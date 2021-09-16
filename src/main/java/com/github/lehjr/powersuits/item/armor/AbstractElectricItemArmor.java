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

package com.github.lehjr.powersuits.item.armor;

import com.github.lehjr.numina.client.model.item.armor.ArmorModelInstance;
import com.github.lehjr.numina.client.model.item.armor.HighPolyArmor;
import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.network.NuminaPackets;
import com.github.lehjr.numina.network.packets.CosmeticInfoPacket;
import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import com.github.lehjr.numina.util.capabilities.render.IArmorModelSpecNBT;
import com.github.lehjr.numina.util.capabilities.render.IModelSpecNBT;
import com.github.lehjr.numina.util.capabilities.render.ModelSpecNBTCapability;
import com.github.lehjr.numina.util.capabilities.render.modelspec.EnumSpecType;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.numina.util.string.AdditionalInfo;
import com.github.lehjr.powersuits.basemod.MPSObjects;
import com.github.lehjr.powersuits.capability.PowerArmorCap;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.constants.MPSRegistryNames;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class AbstractElectricItemArmor extends ArmorItem {
    public static final UUID[] ARMOR_MODIFIERS = new UUID[]{
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID()};

    public AbstractElectricItemArmor(EquipmentSlotType slots, Properties builder) {
        super(MPAArmorMaterial.EMPTY_ARMOR, slots, builder);
    }

    public AbstractElectricItemArmor(EquipmentSlotType slots) {
        super(MPAArmorMaterial.EMPTY_ARMOR, slots, new Item.Properties()
                .stacksTo(1)
                .tab(MPSObjects.creativeTab)
                .durability(0)
                .setNoRepair());
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .map(iModularItem -> iModularItem.isModuleOnline(MPSRegistryNames.PIGLIN_PACIFICATION_MODULE_REGNAME)).orElse(false);
    }

    //    /*
//        returning a value higher than 0 is applied damage to the armor, even if the armor is not setup to take damage.
//        TODO: does this even work???? Is this even needed?
//
//      */
    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        int enerConsum = (int) Math.round(stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .map(iItemHandler -> {
                    Pair<Integer, Integer> range = iItemHandler.getRangeForCategory(EnumModuleCategory.ARMOR);
                    double energyUsed = 0;
                    for (int x = range.getKey(); x < range.getRight(); x ++) {
                        energyUsed += iItemHandler.getStackInSlot(x).getCapability(PowerModuleCapability.POWER_MODULE)
                                .map(pm->pm.applyPropertyModifiers(MPSConstants.ARMOR_ENERGY_CONSUMPTION)).orElse(0D);
                    }
                    return energyUsed;
                }).orElse(0D));

        // protects as long as there is energy to drain I guess
        if (enerConsum > 0 && entity instanceof LivingEntity) {
            ElectricItemUtils.drainPlayerEnergy(entity, enerConsum);
        }
        return 0;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (slot != this.slot) {
            return multimap;
        }

        AtomicDouble armorVal = new AtomicDouble(0);
        AtomicDouble toughnessVal = new AtomicDouble(0);
        AtomicDouble knockbackResistance = new AtomicDouble(0);

        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .ifPresent(iItemHandler -> {
                    // Armor **should** only occupy one slot
                    Pair<Integer, Integer> range = iItemHandler.getRangeForCategory(EnumModuleCategory.ARMOR);
                    if (range != null) {
                        for (int i = range.getLeft(); i < range.getRight(); i++) {
                            iItemHandler.getStackInSlot(i).getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(pm -> {
                                if (pm.isAllowed()) {
                                    // physical armor and hybrid energy/physical armor
                                    double armorDouble = pm.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_PHYSICAL);
                                    double knockBack = 0;

                                    if (pm instanceof IToggleableModule && ((IToggleableModule) pm).isModuleOnline()) {
                                        armorDouble += pm.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_ENERGY);
                                    }

                                    if (armorDouble > 0) {
                                        knockBack = pm.applyPropertyModifiers(MPSConstants.KNOCKBACK_RESISTANCE);
                                        armorVal.getAndAdd(armorDouble);
                                    }

                                    if (knockBack > 0) {
                                        knockbackResistance.getAndAdd(knockBack);
                                    }
                                }
                            });
                        }
                    }
                });

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        for (Map.Entry entry : multimap.entries()) {
            builder.put((Attribute)entry.getKey(), (AttributeModifier)entry.getValue());
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

        return builder.build();
    }

    /**
     * This will work for the vanilla type models. This will not work for high polly models due to how the rendering works
     * @param armor
     * @param entity
     * @param equipmentSlotType
     * @param type
     * @return
     */
    @Nullable
    @Override
    public String getArmorTexture(ItemStack armor, Entity entity, EquipmentSlotType equipmentSlotType, String type) {
        if (type == "overlay") { // this is to allow a tint to be applied tot the armor
            return NuminaConstants.BLANK_ARMOR_MODEL_PATH;
        }

        return armor.getCapability(ModelSpecNBTCapability.RENDER)
                .filter(IArmorModelSpecNBT.class::isInstance)
                .map(IArmorModelSpecNBT.class::cast)
                .map(spec-> spec.getArmorTexture())
                .orElse(AtlasTexture.LOCATION_BLOCKS.toString());
    }

    /**
     * This is probably not going to work for the high polly models. Instead this will need to be done with an armor layer for more control
     * @param entityLiving
     * @param itemStack
     * @param armorSlot
     * @param _default
     * @return
     */
    @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, BipedModel _default) {
        if (!(entityLiving instanceof PlayerEntity)) {
            return _default;
        }

        return itemStack.getCapability(ModelSpecNBTCapability.RENDER)
                .filter(IModelSpecNBT.class::isInstance)
                .map(IModelSpecNBT.class::cast)
                .map(spec-> {
                    CompoundNBT renderTag = spec.getRenderTag();

                    // This sets up a default spec... not sure yet if this is will be enabled
                    PlayerEntity player = (PlayerEntity) entityLiving;
//            // only triggered by this client's player looking at their own equipped armor
                    if (renderTag == null /*|| renderTag.isEmpty() */ && player == Minecraft.getInstance().player) {
                        renderTag = spec.getDefaultRenderTag();
                        if (renderTag != null /*&& !renderTag.isEmpty()*/) {
                            spec.setRenderTag(renderTag, NuminaConstants.TAG_RENDER);
                            NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacket(armorSlot, NuminaConstants.TAG_RENDER, renderTag));
                        }
                    }

                    if (spec.getRenderTag() != null &&
                            (spec.getSpecType() == EnumSpecType.ARMOR_SKIN || spec.getSpecType() == EnumSpecType.NONE)) {
                        return _default;
                    }

                    BipedModel model = ArmorModelInstance.getInstance();
                    ItemStack chestplate = entityLiving.getItemBySlot(EquipmentSlotType.CHEST);
                    if (chestplate.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                            .filter(IModularItem.class::isInstance)
                            .map(IModularItem.class::cast)
                            .map(iItemHandler ->
                                    iItemHandler instanceof IModularItem && iItemHandler
                                            .isModuleOnline(MPSRegistryNames.ACTIVE_CAMOUFLAGE_MODULE_REGNAME)).orElse(false)) {
                        ((HighPolyArmor) model).setVisibleSection(null);
                    } else {
                        if (renderTag != null) {
                            ((HighPolyArmor) model).setVisibleSection(slot);
                            ((HighPolyArmor) model).setRenderSpec(renderTag);
                        }
                    }
                    return model;
                }).orElse(_default);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (worldIn != null) {
            AdditionalInfo.appendHoverText(stack, worldIn, tooltip, flagIn);
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt) {
        return new PowerArmorCap(stack, this.slot);
    }

    @Override
    public boolean showDurabilityBar(final ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY)
                .map( energyCap-> energyCap.getMaxEnergyStored() > 0).orElse(false);
    }

    @Override
    public double getDurabilityForDisplay(final ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY)
                .map( energyCap-> 1 - energyCap.getEnergyStored() / (double) energyCap.getMaxEnergyStored()).orElse(1D);
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