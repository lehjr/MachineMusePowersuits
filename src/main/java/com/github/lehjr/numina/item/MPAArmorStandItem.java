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

package com.github.lehjr.numina.item;

import com.github.lehjr.numina.basemod.NuminaObjects;
import com.github.lehjr.numina.entity.MPAArmorStandEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class MPAArmorStandItem extends ArmorStandItem {
    public MPAArmorStandItem(Item.Properties builder) {
        super(builder);
    }

    /**
     * Called when this item is used when targetting a Block
     */
    public ActionResultType onItemUse(ItemUseContext context) {
        Direction direction = context.getFace();
        if (direction == Direction.DOWN) {
            return ActionResultType.FAIL;
        } else {
            World world = context.getWorld();
            BlockItemUseContext blockitemusecontext = new BlockItemUseContext(context);
            BlockPos blockpos = blockitemusecontext.getPos();
            ItemStack itemstack = context.getItem();
            Vector3d vector3d = Vector3d.copyCenteredHorizontally(blockpos);
            AxisAlignedBB axisalignedbb = EntityType.ARMOR_STAND.getSize().func_242285_a(vector3d.getX(), vector3d.getY(), vector3d.getZ());
            if (world.hasNoCollisions(null, axisalignedbb, (p_242390_0_) -> true) && world.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb).isEmpty()) {
                if (world instanceof ServerWorld) {
                    ServerWorld serverworld = (ServerWorld)world;
                    MPAArmorStandEntity armorstandentity = NuminaObjects.ARMOR_WORKSTATION__ENTITY_TYPE.get().create(serverworld, itemstack.getTag(), null, context.getPlayer(), blockpos, SpawnReason.SPAWN_EGG, true, true);
                    if (armorstandentity == null) {
                        return ActionResultType.FAIL;
                    }
                    serverworld.func_242417_l(armorstandentity);
                    float f = (float)MathHelper.floor((MathHelper.wrapDegrees(context.getPlacementYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    armorstandentity.setLocationAndAngles(armorstandentity.getPosX(), armorstandentity.getPosY(), armorstandentity.getPosZ(), f, 0.0F);
                    this.applyRandomRotations(armorstandentity, world.rand);
                    // tries to add duplicate? results in error message about the same entity with the same uuid already added.
//                    world.addEntity(armorstandentity);
                    world.playSound(null, armorstandentity.getPosX(), armorstandentity.getPosY(), armorstandentity.getPosZ(), SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F);
                }

                itemstack.shrink(1);
                return ActionResultType.func_233537_a_(world.isRemote);
            } else {
                return ActionResultType.FAIL;
            }
        }
    }

    private void applyRandomRotations(MPAArmorStandEntity armorStand, Random rand) {
        Rotations rotations = armorStand.getHeadRotation();
        float f = rand.nextFloat() * 5.0F;
        float f1 = rand.nextFloat() * 20.0F - 10.0F;
        Rotations rotations1 = new Rotations(rotations.getX() + f, rotations.getY() + f1, rotations.getZ());
        armorStand.setHeadRotation(rotations1);
        rotations = armorStand.getBodyRotation();
        f = rand.nextFloat() * 10.0F - 5.0F;
        rotations1 = new Rotations(rotations.getX(), rotations.getY() + f, rotations.getZ());
        armorStand.setBodyRotation(rotations1);
    }
}