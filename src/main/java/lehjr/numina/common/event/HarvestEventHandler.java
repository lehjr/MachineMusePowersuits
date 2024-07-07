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

package lehjr.numina.common.event;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capability.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class HarvestEventHandler {
    @SubscribeEvent
    public static void handleHarvestCheck(PlayerEvent.HarvestCheck event) {
        Player player = event.getEntity();
        if (player == null) {
            return;
        }

        BlockState state = event.getTargetBlock();
        if (state == null ||state.getBlock() == null) {
            return;
        }

        NuminaCapabilities.getModeChangingModularItemCapability(player.getInventory().getSelected())
                .map(IModeChangingItem.class::cast)
                .ifPresent(iItemHandler -> {
                    if (!state.requiresCorrectToolForDrops()) {
                        event.setCanHarvest(true);
                        return;
                    }

                    HitResult rayTraceResult = rayTrace(player.level(), player, ClipContext.Fluid.SOURCE_ONLY);
                    if (rayTraceResult == null || rayTraceResult.getType() != HitResult.Type.BLOCK)
                        return;

                    Vec3 resultLocation = rayTraceResult.getLocation();

                    BlockPos pos = new BlockPos((int)resultLocation.x(), (int)resultLocation.y(), (int)resultLocation.z());
                    if (pos == null) {
                        return;
                    }

                    double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);

                    for (ItemStack module : iItemHandler.getInstalledModulesOfType(IBlockBreakingModule.class)) {
                        if (NuminaCapabilities.getPowerModuleCapability(module)
                                .filter(IBlockBreakingModule.class::isInstance)
                                .map(IBlockBreakingModule.class::cast)
                                .map(pm-> pm.canHarvestBlock(iItemHandler.getModularItemStack(), state, player, pos, playerEnergy)).orElse(false)) {
                            event.setCanHarvest(true);
                            return;
                        }
                    }
                });
    }

    // copied from vanilla item
    protected static HitResult rayTrace(Level worldIn, Player player, ClipContext.Fluid fluidMode) {
        float pitch = player.xRotO;
        float yaw = player.getYRot();
        Vec3 vec3d = player.getEyePosition(1.0F);
        float f2 = Mth.cos(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-pitch * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-pitch * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = player.entityInteractionRange();//.getAttribute(NeoForgeMod.ENTITY_REACH.get()).getValue();
        Vec3 vec3d1 = vec3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return worldIn.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, fluidMode, player));
    }

    @SubscribeEvent
    public static void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
        // Note: here we can actually get the position if needed. we can't easily om the harvest check.
        Player player = event.getEntity();
        ItemStack stack = player.getInventory().getSelected();
        NuminaCapabilities.getModeChangingModularItemCapability(stack)
                .map(IModeChangingItem.class::cast)
                .ifPresent(iItemHandler -> {
                    BlockState state = event.getState();

                    // wait... what is this again? Looks like resetting speed
                    if (event.getNewSpeed() < event.getOriginalSpeed()) {
                        event.setNewSpeed(event.getOriginalSpeed());
                    }
                    double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);

                    for (ItemStack module : iItemHandler.getInstalledModulesOfType(IBlockBreakingModule.class)) {
                        NuminaCapabilities.getPowerModuleCapability(module)
                                .filter(IBlockBreakingModule.class::isInstance)
                                .map(IBlockBreakingModule.class::cast)
                                .ifPresent(pm -> {
                                    if (event.getPosition().isPresent()) {
                                        if (pm.canHarvestBlock(stack, state, player, event.getPosition().get(),  playerEnergy)) {
                                            if (event.getNewSpeed() == 0) {
                                                event.setNewSpeed(1);
                                            }
                                            pm.handleBreakSpeed(event);
                                        }
                                    }
                                });
                    }
                });
    }
}