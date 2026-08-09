package lehjr.powersuits.common.item.module.tool.misc;

import lehjr.numina.client.config.NuminaClientConfig;
import lehjr.numina.client.sound.Musique;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.common.config.module.ToolModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.IShearable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User: Andrew2448
 * 7:13 PM 4/21/13
 */
public class LeafBlowerModule extends AbstractPowerModule {
    public static class Ticker extends PlayerTickModule implements IRightClickModule {
        public Ticker(ItemStack module) {
            super(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.leafBlowerModuleEnergyConsumptionBase, "FE");
            addTradeoffProperty(MPSConstants.RADIUS, MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.leafBlowerModuleEnergyConsumptionRadiusMultipler);
            //
            addBaseProperty(MPSConstants.RADIUS, 1, "m");
            addIntTradeoffProperty(MPSConstants.RADIUS,
                MPSConstants.RADIUS, ToolModuleConfig.leafBlowerModuleRadiusMax -1, "m", 1, 1);

        }

        @Override
        public void onPlayerTickActive(Player player, Level level, @Nonnull ItemStack itemStackIn) {
            int energyUsage = getEnergyUsage();
            if(getPlayerEnergy(player) < energyUsage) {
                toggleModule(false);
                player.stopUsingItem();
            } else if(player.isUsingItem()) {
                ElectricItemUtils.drainPlayerEnergy(player, energyUsage, false);
            } else {
                Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_LEAF_BLOWER.get());
            }
        }

        @Override
        public void onPlayerTickInactive(Player player, Level level, @NotNull ItemStack itemStackIn) {
            Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_LEAF_BLOWER.get());
        }

        @Override
        public InteractionResultHolder<ItemStack> use(ItemStack itemStackIn, Level level, Player player, InteractionHand hand) {
            if(isModuleOnline()) {
                int radius = (int) applyPropertyModifiers(MPSConstants.RADIUS);
                NuminaLogger.logDebug("radius " + radius);
                if(getPlayerEnergy(player) >= getEnergyUsage()) {
                    player.startUsingItem(hand);
                    useBlower(radius, player, level, 60);
                    return InteractionResultHolder.success(itemStackIn);
                }
            }
            return InteractionResultHolder.pass(itemStackIn);
        }

        @Override
        public boolean isAllowed() {
            return ToolModuleConfig.leafBlowerModuleIsAllowed;
        }

        private void useBlower(int radius,Player player, Level level, double fovDegrees) {
            double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
            if(playerEnergy >= getEnergyUsage()) {
                if (NuminaClientConfig.useSounds) {
                    Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_LEAF_BLOWER.get(), SoundSource.PLAYERS, 1, 1F, false);
                }
                getBlocksInCone(player, level, radius, fovDegrees);

                Vec3 lookVec = player.getLookAngle().normalize();


                NuminaLogger.logDebug("look angle: " + player.getLookAngle().normalize());
                //  X   (East+, West-)  |   Y   (Up+, Down-)    |   Z   (North-, South+)    |
                //---------------------------------------------------------------------------------------
                // North: look angle: (-0.26874525621565576, -0.026170557558752368, -0.9628556948882037)
                // South: look angle: (-0.01313430507065444, 0.00258859347220951, 0.9999103905921504)
                // East: look angle: (0.9996008861876374, -0.026170559198380884, 0.01063814664939885)
                // West: look angle: (-0.999045530115737, -0.013038519464809798, 0.04168963619332382)
//
//                if(lookVec.x > 0) {
//                    NuminaLogger.logDebug("facing east: " + lookVec.x);
//                } else {
//                    NuminaLogger.logDebug("facing west: " + lookVec.x);
//                }
//
//                if(lookVec.z > 0) {
//                    NuminaLogger.logDebug("facing south: " + lookVec.z);
//                } else {
//                    NuminaLogger.logDebug("facing north: " + lookVec.z);
//                }
//
//                if(lookVec.y > 0) {
//                    NuminaLogger.logDebug("facing up: " + lookVec.y);
//                } else {
//                    NuminaLogger.logDebug("facing down: " + lookVec.y);
//                }
//
//                // East +/- West
//                for (int i = pos.getX() - radius; i < pos.getX() + radius; i++) {
//                    // North -/+ South
//                    for (int j = pos.getZ() - radius; j < pos.getZ() + radius; j++) {
//                        // Up +/- Down
//                        for (int k = pos.getY() - radius; k < pos.getY() + radius; k++) {
//                            newPos = new BlockPos(i, k, j);
//                            BlockState state = level.getBlockState(newPos);
//                            if (!state.isAir()) {
//                                blockCheckAndHarvest(player, level, newPos);
//                            }
//                        }
//                    }
//                }
            }
        }

        /**
         * Selects block positions within a cone expanding in the direction of lookAngle.
         *
//         * @param eyeX      Player's eye position X
//         * @param eyeY      Player's eye position Y
//         * @param eyeZ      Player's eye position Z
//         * @param lookAngle Player's look direction (from getLookAngle())
         * @param radius    Maximum range of the cone
         * @param fovDegrees Total opening angle of the cone (e.g., 60.0 degrees)
         */
        public void getBlocksInCone(Player player,
            Level level,
            double radius,
            double fovDegrees) {

            double eyeX = player.getX();
            double eyeY = player.getEyeY();
            double eyeZ  = player.getZ();
            Vec3 lookAngle = player.getLookAngle().normalize();

            // Cosine threshold for the half-angle of the cone
            double maxAngleRad = Math.toRadians(fovDegrees / 2.0);
            double cosThreshold = Math.cos(maxAngleRad);
            double radiusSq = radius * radius;

            // Bounding box range
            int minX = (int) Math.floor(eyeX - radius);
            int maxX = (int) Math.ceil(eyeX + radius);
            int minY = (int) Math.floor(eyeY - radius);
            int maxY = (int) Math.ceil(eyeY + radius);
            int minZ = (int) Math.floor(eyeZ - radius);
            int maxZ = (int) Math.ceil(eyeZ + radius);

            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {

                        // Offset to block center
                        double dx = (x + 0.5) - eyeX;
                        double dy = (y + 0.5) - eyeY;
                        double dz = (z + 0.5) - eyeZ;

                        double distSq = dx * dx + dy * dy + dz * dz;

                        // Skip blocks outside radius sphere or at exact origin
                        if (distSq > radiusSq || distSq == 0) {
                            continue;
                        }

                        double dist = Math.sqrt(distSq);

                        // Normalize direction vector pointing toward block center
                        double dirX = dx / dist;
                        double dirY = dy / dist;
                        double dirZ = dz / dist;

                        // Dot product: lookAngle • blockDir
                        double dotProduct = (lookAngle.x * dirX) + (lookAngle.y * dirY) + (lookAngle.z * dirZ);

                        // If inside cone angle, store as BlockPos
                        if (dotProduct >= cosThreshold) {
                            blockCheckAndHarvest(player, level, new BlockPos(x, y, z));
                        }
                    }
                }
            }
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }
    }

    static void blockCheckAndHarvest(Player player, Level world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (world.isEmptyBlock(pos) || block == Blocks.BEDROCK) {
            return;
        }

        if(block == Blocks.SNOW || block == Blocks.SNOW_BLOCK || block == Blocks.POWDER_SNOW) {
            block.playerDestroy(world, player, pos, state, world.getBlockEntity(pos), new ItemStack(Items.DIAMOND_SHOVEL));
            world.removeBlock(pos, false);

            NuminaLogger.logDebug("snow type block removed");
            return;
        }

        ItemStack shears = new ItemStack(Items.SHEARS);
        if(shears.isCorrectToolForDrops(state)) {
            block.playerDestroy(world, player, pos, state, world.getBlockEntity(pos), new ItemStack(Items.SHEARS));
            world.removeBlock(pos, false);
            NuminaLogger.logDebug("shearable type block removed");

            return;
        }



        if ((block instanceof IShearable || block instanceof BushBlock || block instanceof LeavesBlock)
            && block.canHarvestBlock(state, world, pos, player)) {
            block.playerDestroy(world, player, pos, state, world.getBlockEntity(pos), new ItemStack(Items.SHEARS));
            world.removeBlock(pos, false);

//            NuminaLogger.logDebug("block removed? " + block);
            return;
        }
        if(block != Blocks.DIRT
            && block != Blocks.GRASS_BLOCK
            && block != Blocks.FARMLAND
            && block != Blocks.MYCELIUM
            && block != Blocks.STONE
            && block != Blocks.COBBLESTONE
            & block != Blocks.GRANITE
            && block != Blocks.DIORITE
            && block != Blocks.PODZOL
            && block != Blocks.ANDESITE
            && block != Blocks.DEEPSLATE
            && block != Blocks.GRAVEL
            && block != Blocks.SAND) {
            NuminaLogger.logDebug("block: " + block);
        }

    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        releaseUsing(stack, level, livingEntity, 99);
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        Musique.stopPlayerSound((Player)livingEntity, MPSSoundDictionary.SOUND_EVENT_LEAF_BLOWER.get());
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 100;
    }
}
