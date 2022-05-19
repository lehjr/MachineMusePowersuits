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

package lehjr.powersuits.event;

import lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.util.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.util.capabilities.module.powermodule.CapabilityPowerModule;
import lehjr.numina.util.energy.ElectricItemUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;

public class HarvestEventHandler {
    @SubscribeEvent
    public static void handleHarvestCheck(PlayerEvent.HarvestCheck event) {
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }

        BlockState state = event.getTargetBlock();
        if (state == null ||state.getBlock() == null) {
            return;
        }

        player.getInventory().getSelected().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .ifPresent(iItemHandler -> {
                    if (!state.requiresCorrectToolForDrops()) {
                        event.setCanHarvest(true);
                        return;
                    }

                    RayTraceResult rayTraceResult = rayTrace(player.level, player, ClipContext.Fluid.SOURCE_ONLY);
                    if (rayTraceResult == null || rayTraceResult.getType() != RayTraceResult.Type.BLOCK)
                        return;

                    BlockPos pos = new BlockPos(rayTraceResult.getLocation());
                    if (pos == null) {
                        return;
                    }

                    int playerEnergy = ElectricItemUtils.getPlayerEnergy(player);

                    for (ItemStack module : iItemHandler.getInstalledModulesOfType(IBlockBreakingModule.class)) {
                        if (module.getCapability(CapabilityPowerModule.POWER_MODULE)
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
    protected static RayTraceResult rayTrace(World worldIn, Player player, RayTraceContext.FluidMode fluidMode) {
        float pitch = player.xRot;
        float yaw = player.yRot;
        Vector3d vec3d = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-pitch * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-pitch * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = player.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue();
        Vector3d vec3d1 = vec3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return worldIn.clip(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
    }

    @SubscribeEvent
    public static void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
        // Note: here we can actually get the position if needed. we can't easily om the harvest check.
        Player player = event.getPlayer();
        ItemStack stack = player.getInventory().getSelected();
        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .ifPresent(iItemHandler -> {
                    BlockState state = event.getState();

                    // wait... what is this again? Looks like resetting speed
                    if (event.getNewSpeed() < event.getOriginalSpeed()) {
                        event.setNewSpeed(event.getOriginalSpeed());
                    }
                    int playerEnergy = ElectricItemUtils.getPlayerEnergy(player);

                    for (ItemStack module : iItemHandler.getInstalledModulesOfType(IBlockBreakingModule.class)) {
                        module.getCapability(CapabilityPowerModule.POWER_MODULE)
                                .filter(IBlockBreakingModule.class::isInstance)
                                .map(IBlockBreakingModule.class::cast)
                                .ifPresent(pm -> {
                                    if (pm.canHarvestBlock(stack, state, player, event.getPos(), playerEnergy)) {
                                        if (event.getNewSpeed() == 0) {
                                            event.setNewSpeed(1);
                                        }
                                        pm.handleBreakSpeed(event);
                                    }
                                });
                    }
                });
    }
}