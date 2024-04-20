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

package lehjr.numina.common.item;

import lehjr.numina.client.render.item.NuminaArmorStandItemRenderer;
import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.entity.NuminaArmorStand;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Rotations;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorStandItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class NuminaArmorStandItem extends ArmorStandItem {
    public NuminaArmorStandItem(Item.Properties builder) {
        super(builder);
    }

    /**
     * Called when this item is used when targeting a Block
     */
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Direction direction = pContext.getClickedFace();
        if (direction == Direction.DOWN) {
            return InteractionResult.FAIL;
        } else {
            Level level = pContext.getLevel();
            BlockPlaceContext blockplacecontext = new BlockPlaceContext(pContext);
            BlockPos blockpos = blockplacecontext.getClickedPos();
            ItemStack itemstack = pContext.getItemInHand();
            Vec3 vec3 = Vec3.atBottomCenterOf(blockpos);
            AABB aabb = EntityType.ARMOR_STAND.getDimensions().makeBoundingBox(vec3.x(), vec3.y(), vec3.z());
            if (level.noCollision((Entity)null, aabb) && level.getEntities((Entity)null, aabb).isEmpty()) {
                if (level instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel)level;
                    Consumer<NuminaArmorStand> consumer = EntityType.createDefaultStackConfig(serverlevel, itemstack, pContext.getPlayer());
                    NuminaArmorStand armorstand = NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get().create(serverlevel, itemstack.getTag(), consumer, blockpos, MobSpawnType.SPAWN_EGG, true, true);
                    if (armorstand == null) {
                        return InteractionResult.FAIL;
                    }
                    float f = (float)Mth.floor((Mth.wrapDegrees(pContext.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    armorstand.moveTo(armorstand.getX(), armorstand.getY(), armorstand.getZ(), f, 0.0F);
                    this.randomizePose(armorstand, level.random);
                    serverlevel.addFreshEntityWithPassengers(armorstand);
                    level.playSound((Player)null, armorstand.getX(), armorstand.getY(), armorstand.getZ(), SoundEvents.ARMOR_STAND_PLACE, SoundSource.BLOCKS, 0.75F, 0.8F);
                    armorstand.gameEvent(GameEvent.ENTITY_PLACE, pContext.getPlayer());
                }

                itemstack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.FAIL;
            }
        }
    }

    private void randomizePose(NuminaArmorStand armorStand, RandomSource rand) {
        Rotations rotations = armorStand.getHeadPose();
        float f = rand.nextFloat() * 5.0F;
        float f1 = rand.nextFloat() * 20.0F - 10.0F;
        Rotations rotations1 = new Rotations(rotations.getX() + f, rotations.getY() + f1, rotations.getZ());
        armorStand.setHeadPose(rotations1);
        rotations = armorStand.getBodyPose();
        f = rand.nextFloat() * 10.0F - 5.0F;
        rotations1 = new Rotations(rotations.getX(), rotations.getY() + f, rotations.getZ());
        armorStand.setBodyPose(rotations1);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
       consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new NuminaArmorStandItemRenderer();

           @Override
           public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }
}