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

package lehjr.numina.common.entity;

import lehjr.numina.client.sound.SoundDictionary;
import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.container.ArmorStandMenu;
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

/**
 * TODO: decide if keeping this or just using vanilla armor stand. This really doesn't do anything, ... YET?
 */


public class NuminaArmorStand extends ArmorStand {
    public NuminaArmorStand(EntityType<? extends ArmorStand> entityType, Level level) {
        super(entityType, level);
        setShowArms(true);
        createAttributes();
    }

    public NuminaArmorStand(Level level, double p_31557_, double p_31558_, double p_31559_) {
        super(level, p_31557_, p_31558_, p_31559_);
        setShowArms(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
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
