package com.github.lehjr.powersuits.event;

import com.github.lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import com.github.lehjr.numina.util.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
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
        PlayerEntity player = event.getPlayer();
        if (player == null) {
            return;
        }

        BlockState state = event.getTargetBlock();
        if (state == null ||state.getBlock() == null) {
            return;
        }

        ItemStack stack = player.inventory.getCurrentItem();
        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            if(iItemHandler instanceof IModeChangingItem) {
                if (!state.getRequiresTool()) {
                    event.setCanHarvest(true);
                    return;
                }

                RayTraceResult rayTraceResult = rayTrace(player.world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
                if (rayTraceResult == null || rayTraceResult.getType() != RayTraceResult.Type.BLOCK)
                    return;

                BlockPos pos = new BlockPos(rayTraceResult.getHitVec());
                if (pos == null) {
                    return;
                }

                int playerEnergy = ElectricItemUtils.getPlayerEnergy(player);

                for (ItemStack module : ((IModeChangingItem) iItemHandler).getInstalledModulesOfType(IBlockBreakingModule.class)) {
                    if (module.getCapability(PowerModuleCapability.POWER_MODULE)
                            .map(pm->pm instanceof IBlockBreakingModule && ((IBlockBreakingModule) pm).canHarvestBlock(stack, state, player, pos, playerEnergy)).orElse(false)) {
                        event.setCanHarvest(true);
                        return;
                    }
                }
            }
        });
    }

    // copied from vanilla item
    protected static RayTraceResult rayTrace(World worldIn, PlayerEntity player, RayTraceContext.FluidMode fluidMode) {
        float pitch = player.rotationPitch;
        float yaw = player.rotationYaw;
        Vector3d vec3d = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-pitch * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-pitch * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = player.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue();;
        Vector3d vec3d1 = vec3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return worldIn.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
    }

    @SubscribeEvent
    public static void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
        // Note: here we can actually get the position if needed. we can't easily om the harvest check.
        PlayerEntity player = event.getPlayer();
        ItemStack stack = player.inventory.getCurrentItem();
        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            BlockState state = event.getState();

            // wait... what is this again? Looks like resetting speed
            if (event.getNewSpeed() < event.getOriginalSpeed()) {
                event.setNewSpeed(event.getOriginalSpeed());
            }
            int playerEnergy = ElectricItemUtils.getPlayerEnergy(player);

            for (ItemStack module : ((IModeChangingItem) iItemHandler).getInstalledModulesOfType(IBlockBreakingModule.class)) {
                module.getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(pm -> {
                    if(pm instanceof IBlockBreakingModule && ((IBlockBreakingModule) pm).canHarvestBlock(stack, state, player, event.getPos(), playerEnergy)) {
                        if (event.getNewSpeed() == 0) {
                            event.setNewSpeed(1);
                        }
                        ((IBlockBreakingModule) pm).handleBreakSpeed(event);
                    }
                });
            }
        });
    }
}