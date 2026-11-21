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

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capabilities.module.enhancement.IMiningEnhancementModule;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.render.highlight.IHighlight;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HarvestEventHandler {
    @SubscribeEvent
    public static void handleHarvestCheck(PlayerEvent.HarvestCheck event) {
        Player player = event.getEntity();
        BlockState state = event.getTargetBlock();

        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(player.getMainHandItem());
        if (mci != null) {
            if (!state.requiresCorrectToolForDrops()) {
                event.setCanHarvest(true);
                return;
            }

            HitResult rayTraceResult = pick(player, 1F);
            //            HitResult rayTraceResult = rayTrace(player.level(), player, ClipContext.Fluid.SOURCE_ONLY);
            if (!(rayTraceResult instanceof BlockHitResult)) {
                return;
            }
            BlockPos pos = ((BlockHitResult) rayTraceResult).getBlockPos();
            double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
            for (int i = 0; i < mci.getSlots(); i++) {
                IPowerModule pm = mci.getModuleCapability(mci.getStackInSlot(i));
                if (pm instanceof IBlockBreakingModule bbm && bbm.isModuleOnline() && bbm.canHarvestBlock(mci.getModularItemStack(), state, player, pos, playerEnergy)) {
                    event.setCanHarvest(true);
                    return;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack tool = player.getMainHandItem();
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(tool);
        if (mci != null) {
            HitResult rayTraceResult = pick(player, 1F);
            if (rayTraceResult instanceof BlockHitResult blockHitResult) {
                event.setCanceled(mci.onBlockStartBreak(tool, blockHitResult, player, player.level()));
            }
        }
    }

    // copied from vanilla item
    protected static HitResult rayTrace(LevelAccessor level, Player player, ClipContext.Fluid fluidMode) {
        float pitch = player.xRotO;
        float yaw = player.getYRot();
        Vec3 vec3d = player.getEyePosition(1.0F);
        float f2 = Mth.cos(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-pitch * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-pitch * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = player.getBlockReach();//.getAttribute(NeoForgeMod.ENTITY_REACH.get()).getValue();
        Vec3 vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
        return level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, fluidMode, player));
    }

    // Adapted from Minecraft's level renderer so that the highlighted block display matches what's actually being harvested
    private static HitResult pick(Player player, float partialTick) {
        double blockInteractionRange = player.getBlockReach();
        double entityInteractionRange = player.getEntityReach();
        double max = Math.max(blockInteractionRange, entityInteractionRange);
        double maxSquared = Mth.square(max);

        Vec3 eyePosition = player.getEyePosition(partialTick);
        HitResult hitresult = player.pick(max, partialTick, false);
        double distanceToEyeSqr = hitresult.getLocation().distanceToSqr(eyePosition);
        if (hitresult.getType() != HitResult.Type.MISS) {
            maxSquared = distanceToEyeSqr;
            max = Math.sqrt(distanceToEyeSqr);
        }

        Vec3 vec31 = player.getViewVector(partialTick);
        Vec3 vec32 = eyePosition.add(vec31.x * max, vec31.y * max, vec31.z * max);
        float f = 1.0F;
        AABB aabb = player.getBoundingBox().expandTowards(vec31.scale(max)).inflate(1.0, 1.0, 1.0);
        EntityHitResult entityhitresult = ProjectileUtil.getEntityHitResult(
                player, eyePosition, vec32, aabb, p_234237_ -> !p_234237_.isSpectator() && p_234237_.isPickable(), maxSquared
        );
        return entityhitresult != null && entityhitresult.getLocation().distanceToSqr(eyePosition) < distanceToEyeSqr
                ? filterHitResult(entityhitresult, eyePosition, entityInteractionRange)
                : filterHitResult(hitresult, eyePosition, blockInteractionRange);
    }

    private static HitResult filterHitResult(HitResult pHitResult, Vec3 pPos, double blockInteractionRange) {
        Vec3 vec3 = pHitResult.getLocation();
        if (!vec3.closerThan(pPos, blockInteractionRange)) {
            Vec3 vec31 = pHitResult.getLocation();
            Direction direction = Direction.getNearest(vec31.x - pPos.x, vec31.y - pPos.y, vec31.z - pPos.z);
            return BlockHitResult.miss(vec31, direction, BlockPos.containing(vec31));
        } else {
            return pHitResult;
        }
    }

    /**
     * checks if a mining enhancement module is being used and does a speed adjustment based on a rough average for each
     * if no mining enhancement is used, then the speed adjustment falls back on the module that will break the block
     * <p>
     * TODO: overclock for mining enhancements?
     *
     * @param event
     */
    @SubscribeEvent
    public static void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
        // Note: here we can actually get the position if needed. we can't easily om the harvest check.
        Player player = event.getEntity();
        Level level = player.level();
        ItemStack tool = player.getInventory().getSelected();
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(tool);
        double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
        NonNullList<IBlockBreakingModule> modules = NonNullList.create();
        float newSpeed = event.getNewSpeed();

        if (mci != null) {
            HitResult rayTraceResult = rayTrace(level, player, ClipContext.Fluid.SOURCE_ONLY);
            if (rayTraceResult instanceof BlockHitResult blockHitResult) {
                BlockState state = event.getState();
                BlockPos pos = event.getPosition().orElse(blockHitResult.getBlockPos()).immutable();

                // Reuse this list instead of making a new one
                for (int i = 0; i < mci.getSlots(); i++) {
                    IPowerModule pm = mci.getModuleCapability(mci.getStackInSlot(i));
                    if (pm instanceof IBlockBreakingModule bbm && bbm.isModuleOnline()) {
                        modules.add(bbm);
                    }
                }

                // If using a MingingEnhancement, account for multiple block breaking at once
                ItemStack module = mci.getActiveModule();

                IPowerModule moduleCap = mci.getModuleCapability(module);
                if (moduleCap instanceof IMiningEnhancementModule me && me.isModuleOnline()) {
                    HashMap<IHighlight.BlockPostionData, Integer> blockPositionsMap = me.getBlockPositions(tool,
                            blockHitResult,
                            player,
                            player.level(),
                            modules,
                            playerEnergy
                    );

                    if(!blockPositionsMap.isEmpty()) {
                        double overclock = me.applyPropertyModifiers(NuminaConstants.HARVEST_SPEED);
                        Map<IBlockBreakingModule, List<BlockPos>> speedMap = new HashMap<>();
                        List<Double> correctedSpeeds = new ArrayList<>();

                        for (Map.Entry<IHighlight.BlockPostionData, Integer> entry : blockPositionsMap.entrySet()) {
                            IHighlight.BlockPostionData blockPostionData = entry.getKey();
                            int harvestLevel = entry.getValue();
                            if (blockPostionData.bbm() != null) {
                                if (blockPostionData.canHarvest()) {
                                    double harvestSpeed = blockPostionData.bbm().applyPropertyModifiers(NuminaConstants.HARVEST_SPEED);
                                    if (harvestLevel == 2) {
                                        harvestSpeed = harvestLevel * overclock;
                                    }
                                    correctedSpeeds.add(harvestSpeed);
                                    List<BlockPos> tmpList = speedMap.getOrDefault(blockPostionData.bbm(), new ArrayList<>());
                                    tmpList.add(blockPostionData.pos());
                                    speedMap.put(blockPostionData.bbm(), tmpList);
                                }
                            }
                        }

                        double finalSpeed = (correctedSpeeds.stream().mapToDouble(Double::doubleValue).average().orElse(1.0) * 1.2); // slight boost

                        // Speed not changed yet, just debugging to see what new values look like
//                        NuminaLogger.logDebug(
//                            "event old speed: " + event.getOriginalSpeed() + ", newSpeed: " + event.getNewSpeed() + ", speed to set: " + finalSpeed
//                                + ", finalSpeeds: " + correctedSpeeds);
//                        NuminaLogger.logDebug("mining enhancement speed adjustment: " + moduleCap.applyPropertyModifiers(NuminaConstants.HARVEST_SPEED));
                        //                        event.setNewSpeed((float) finalSpeed);
                        // Fixme testing
                        event.setNewSpeed((float) (finalSpeed * moduleCap.applyPropertyModifiers(NuminaConstants.HARVEST_SPEED)));
                        return;
                    }
                }

                boolean handled = false;

                // FIME: Pickaxe setting speed way too high
                for (IBlockBreakingModule bbm : modules) {
                    // should require this check?
                    if (bbm.canHarvestBlock(tool, state, player, pos, playerEnergy)) {
                        if (event.getNewSpeed() == 0) {
                            event.setNewSpeed(1);
                        }
                        handled = true;
                        bbm.handleBreakSpeed(event);
                        break;
                    }
                }
                if (!handled) {
                    if (state.requiresCorrectToolForDrops()) {
                        event.setNewSpeed(0);
                    } else {
                        event.setNewSpeed(1.0F);
                    }
                }
            }
        }
    }
}

//public class HarvestEventHandler {
//    @SubscribeEvent
//    public static void handleHarvestCheck(PlayerEvent.HarvestCheck event) {
//        Player player = event.getEntity();
//        if (player == null) {
//            return;
//        }
//
//        BlockState state = event.getTargetBlock();
//        if (state == null ||state.getBlock() == null) {
//            return;
//        }
//
//        player.getInventory().getSelected().getCapability(ForgeCapabilities.ITEM_HANDLER)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .ifPresent(iItemHandler -> {
//                    if (!state.requiresCorrectToolForDrops()) {
//                        event.setCanHarvest(true);
//                        return;
//                    }
//
//                    HitResult rayTraceResult = rayTrace(player.level(), player, ClipContext.Fluid.SOURCE_ONLY);
//                    if (rayTraceResult == null || rayTraceResult.getType() != HitResult.Type.BLOCK)
//                        return;
//
//                    Vec3 resultLocation = rayTraceResult.getLocation();
//
//                    BlockPos pos = new BlockPos((int)resultLocation.x(), (int)resultLocation.y(), (int)resultLocation.z());
//                    if (pos == null) {
//                        return;
//                    }
//
//                    double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
//
//                    for (ItemStack module : iItemHandler.getInstalledModulesOfType(IBlockBreakingModule.class)) {
//                        if (module.getCapability(NuminaCapabilities.Module.POWER_MODULE)
//                                .filter(IBlockBreakingModule.class::isInstance)
//                                .map(IBlockBreakingModule.class::cast)
//                                .map(pm-> pm.canHarvestBlock(iItemHandler.getModularItemStack(), state, player, pos, playerEnergy)).orElse(false)) {
//                            event.setCanHarvest(true);
//                            return;
//                        }
//                    }
//
//                });
//    }
//
//    // copied from vanilla item
//    protected static HitResult rayTrace(Level worldIn, Player player, ClipContext.Fluid fluidMode) {
//        float pitch = player.xRotO;
//        float yaw = player.getYRot();
//        Vec3 vec3d = player.getEyePosition(1.0F);
//        float f2 = Mth.cos(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
//        float f3 = Mth.sin(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
//        float f4 = -Mth.cos(-pitch * ((float)Math.PI / 180F));
//        float f5 = Mth.sin(-pitch * ((float)Math.PI / 180F));
//        float f6 = f3 * f4;
//        float f7 = f2 * f4;
//        double d0 = player.getAttribute(ForgeMod.ENTITY_REACH.get()).getValue();
//        Vec3 vec3d1 = vec3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
//        return worldIn.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, fluidMode, player));
//    }
//
//    @SubscribeEvent
//    public static void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
//        // Note: here we can actually get the position if needed. we can't easily om the harvest check.
//        Player player = event.getEntity();
//        ItemStack stack = player.getInventory().getSelected();
//        stack.getCapability(ForgeCapabilities.ITEM_HANDLER)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .ifPresent(iItemHandler -> {
//                    BlockState state = event.getState();
//
//                    // wait... what is this again? Looks like resetting speed
//                    if (event.getNewSpeed() < event.getOriginalSpeed()) {
//                        event.setNewSpeed(event.getOriginalSpeed());
//                    }
//                    double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
//
//                    for (ItemStack module : iItemHandler.getInstalledModulesOfType(IBlockBreakingModule.class)) {
//                        module.getCapability(NuminaCapabilities.Module.POWER_MODULE)
//                                .filter(IBlockBreakingModule.class::isInstance)
//                                .map(IBlockBreakingModule.class::cast)
//                                .ifPresent(pm -> {
//                                    if (pm.canHarvestBlock(stack, state, player, event.getPosition().get() /*FIXME */, playerEnergy)) {
//                                        if (event.getNewSpeed() == 0) {
//                                            event.setNewSpeed(1);
//                                        }
//                                        pm.handleBreakSpeed(event);
//                                    }
//                                });
//                    }
//                });
//    }
//}