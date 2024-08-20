package com.lehjr.numina.common.base;

import com.mojang.serialization.Codec;
import com.lehjr.numina.common.block.ChargingBase;
import com.lehjr.numina.common.blockentity.ChargingBaseBlockEntity;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.capabilities.player.keystates.PlayerKeyStateStorage;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.container.ArmorStandMenu;
import com.lehjr.numina.common.container.ChargingBaseMenu;
import com.lehjr.numina.common.entity.NuminaArmorStand;
import com.lehjr.numina.common.item.Battery;
import com.lehjr.numina.common.item.ComponentItem;
import com.lehjr.numina.common.item.NuminaArmorStandItem;
import com.lehjr.numina.common.recipe.ingredients.MinEnchantedIngredient;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class NuminaObjects {

    // Items ==========================================================================================================


    // Entity Types ===================================================================================================


    // Menu Types =====================================================================================================


    // Fluid Types ====================================================================================================
    // TODO

    // DataComponentTypes =============================================================================================






