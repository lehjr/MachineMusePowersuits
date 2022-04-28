package com.lehjr.numina.common.entity;

import com.lehjr.numina.common.menu.ArmorStandMenu;
import com.lehjr.numina.common.NuminaObjects;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import com.lehjr.numina.api.sound.SoundDictionary;

public class NuminaArmorStand extends ArmorStand {
    public NuminaArmorStand(EntityType<? extends ArmorStand> entityType, Level level) {
        super(entityType, level);
        setShowArms(true);
    }

    public NuminaArmorStand(Level level, double p_31557_, double p_31558_, double p_31559_) {
        super(level, p_31557_, p_31558_, p_31559_);
        setShowArms(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        System.out.println("doing somethinghere");
        return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 10.0D);
    }

    @Override
    public void brokenByPlayer(DamageSource source) {
        Block.popResource(this.level, this.blockPosition(), new ItemStack(NuminaObjects.ARMOR_STAND_ITEM.get()));
        this.brokenByAnything(source);
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!this.isMarker() && itemstack.getItem() != Items.NAME_TAG) {
            if (player.isSpectator()) {
                return InteractionResult.SUCCESS;
            } else if (player.level.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                player.playSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1.0F, 1.0F);
                NetworkHooks.openGui((ServerPlayer) player,
                        new SimpleMenuProvider((windowID, playerInventory, playerEntity) ->
                                new ArmorStandMenu(windowID, playerInventory, (ArmorStand) this),
                                new TranslatableComponent("screen.numina.armor_stand")),
                        buf -> buf.writeInt(getId()));
                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.PASS;
        }
    }
}