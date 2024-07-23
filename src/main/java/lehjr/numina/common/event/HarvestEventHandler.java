package lehjr.numina.common.event;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capability.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capability.module.enhancement.IMiningEnhancementModule;
import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.capability.render.highlight.IHighlight;
import lehjr.numina.common.utils.ElectricItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HarvestEventHandler {
    @SubscribeEvent
    public static void handleHarvestCheck(PlayerEvent.HarvestCheck event) {
        Player player = event.getEntity();
        BlockState state = event.getTargetBlock();

        IModeChangingItem mci = player.getInventory().getSelected().getCapability(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM);
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
                if (pm instanceof IBlockBreakingModule bbm && bbm.canHarvestBlock(mci.getModularItemStack(), state, player, pos, playerEnergy)) {
                    event.setCanHarvest(true);
                    return;
                }
            }
        }
    }

    static ResourceLocation test(Enchantment enchantment) {
        return BuiltInRegistries.ENCHANTMENT.getKey(enchantment);
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack tool = player.getMainHandItem();
        IModeChangingItem mci = tool.getCapability(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM);
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
        double d0 = player.blockInteractionRange();//.getAttribute(NeoForgeMod.ENTITY_REACH.get()).getValue();
        Vec3 vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
        return level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, fluidMode, player));
    }

    // Adapted from Minecraft's level renderer so that the highlighted block display matches what's actually being harvested
    private static HitResult pick(Player player, float pPartialTick) {
        double blockInteractionRange = player.blockInteractionRange();
        double entityInteractionRange = player.entityInteractionRange();
        double max = Math.max(blockInteractionRange, entityInteractionRange);
        double maxSquared = Mth.square(max);

        Vec3 eyePosition = player.getEyePosition(pPartialTick);
        HitResult hitresult = player.pick(max, pPartialTick, false);
        double distanceToEyeSqr = hitresult.getLocation().distanceToSqr(eyePosition);
        if (hitresult.getType() != HitResult.Type.MISS) {
            maxSquared = distanceToEyeSqr;
            max = Math.sqrt(distanceToEyeSqr);
        }

        Vec3 vec31 = player.getViewVector(pPartialTick);
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
        IModeChangingItem mci = tool.getCapability(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM);
        double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
        NonNullList<IBlockBreakingModule> modules = NonNullList.create();

        if (mci != null) {
            HitResult rayTraceResult = rayTrace(level, player, ClipContext.Fluid.SOURCE_ONLY);
            if (rayTraceResult instanceof BlockHitResult blockHitResult) {
                BlockState state = event.getState();
                BlockPos pos = event.getPosition().orElse(blockHitResult.getBlockPos()).immutable();

                // Reuse this list instead of making a new one
                for (int i = 0; i < mci.getSlots(); i++) {
                    IPowerModule pm = mci.getModuleCapability(mci.getStackInSlot(i));
                    if (pm instanceof IBlockBreakingModule bbm) {
                        modules.add(bbm);
                    }
                }

                // If using a MingingEnhancement, account for multiple block breaking at once
                ItemStack module = mci.getActiveModule();

                IPowerModule moduleCap = mci.getModuleCapability(module);
                if (moduleCap instanceof IMiningEnhancementModule me && me.isModuleOnline()) {
                    playerEnergy -= me.getEnergyUsage();
                    if (playerEnergy > 0) {

                        NonNullList<IHighlight.BlockPostions> blockPositionsList = me.getBlockPositions(tool,
                                blockHitResult,
                                player,
                                player.level(),
                                modules,
                                playerEnergy
                        );
                        Map<IBlockBreakingModule, List<BlockPos>> speedMap = new HashMap<>();

                        for (IHighlight.BlockPostions blockPostions : blockPositionsList) {
                            if (blockPostions.bbm() != null) {
                                if (blockPostions.canHarvest()) {
                                    List<BlockPos> tmpList = speedMap.getOrDefault(blockPostions.bbm(), new ArrayList<>());
                                    tmpList.add(blockPostions.pos());
                                    speedMap.put(blockPostions.bbm(), tmpList);
                                }
                            }
                        }

                        List<Double> correctedSpeeds = new ArrayList<>();

                        for (Map.Entry<IBlockBreakingModule, List<BlockPos>> entry : speedMap.entrySet()) {
                            IBlockBreakingModule bbm = entry.getKey();
                            List<BlockPos> posList = entry.getValue();
                            // FIXME: move tag key to Numina Constants
                            double speed = entry.getKey().applyPropertyModifiers("harvestSpeed");
                            speed = speed / posList.size();
                            correctedSpeeds.add(speed);
                        }
                        double finalSpeed = (correctedSpeeds.stream().mapToDouble(Double::doubleValue).average().orElse(1.0) * 1.2); // slight boost
                        event.setNewSpeed((float) finalSpeed);
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