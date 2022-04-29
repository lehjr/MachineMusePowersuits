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

package lehjr.numina.item;

import lehjr.numina.basemod.NuminaObjects;
import lehjr.numina.entity.NuminaArmorStandEntity;
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

public class NuminaArmorStandItem extends ArmorStandItem {
    public NuminaArmorStandItem(Item.Properties builder) {
        super(builder);
    }

    /**
     * Called when this item is used when targetting a Block
     */
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        Direction direction = context.getClickedFace();
        if (direction == Direction.DOWN) {
            return ActionResultType.FAIL;
        } else {
            World world = context.getLevel();
            BlockItemUseContext blockitemusecontext = new BlockItemUseContext(context);
            BlockPos blockpos = blockitemusecontext.getClickedPos();
            ItemStack itemstack = context.getItemInHand();
            Vector3d vector3d = Vector3d.atBottomCenterOf(blockpos);
            AxisAlignedBB axisalignedbb = EntityType.ARMOR_STAND.getDimensions().makeBoundingBox(vector3d.x(), vector3d.y(), vector3d.z());
            if (world.noCollision(null, axisalignedbb, (p_242390_0_) -> true) && world.getEntities(null, axisalignedbb).isEmpty()) {
                if (world instanceof ServerWorld) {
                    ServerWorld serverworld = (ServerWorld)world;
                    NuminaArmorStandEntity armorstandentity = NuminaObjects.ARMOR_WORKSTATION__ENTITY_TYPE.get().create(serverworld, itemstack.getTag(), null, context.getPlayer(), blockpos, SpawnReason.SPAWN_EGG, true, true);
                    if (armorstandentity == null) {
                        return ActionResultType.FAIL;
                    }
                    serverworld.addFreshEntityWithPassengers(armorstandentity);
                    float f = (float)MathHelper.floor((MathHelper.wrapDegrees(context.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    armorstandentity.moveTo(armorstandentity.getX(), armorstandentity.getY(), armorstandentity.getZ(), f, 0.0F);
                    this.applyRandomRotations(armorstandentity, world.random);
                    // tries to add duplicate? results in error message about the same entity with the same uuid already added.
//                    world.addEntity(armorstandentity);
                    world.playSound(null, armorstandentity.getX(), armorstandentity.getY(), armorstandentity.getZ(), SoundEvents.ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F);
                }

                itemstack.shrink(1);
                return ActionResultType.sidedSuccess(world.isClientSide);
            } else {
                return ActionResultType.FAIL;
            }
        }
    }

    private void applyRandomRotations(NuminaArmorStandEntity armorStand, Random rand) {
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
}