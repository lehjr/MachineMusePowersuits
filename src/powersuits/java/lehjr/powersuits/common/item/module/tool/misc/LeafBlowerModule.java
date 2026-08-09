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

            addBaseProperty(MPSConstants.RADIUS, 1, "m");
            addIntTradeoffProperty(MPSConstants.RADIUS, MPSConstants.RADIUS, ToolModuleConfig.leafBlowerModuleRadiusMax -1, "m", 1, 1);
        }

        @Override
        public void onPlayerTickActive(Player player, Level level, @Nonnull ItemStack itemStackIn) {
            int energyUsage = getEnergyUsage();
            if(getPlayerEnergy(player) < energyUsage) {
                toggleModule(false);
                player.stopUsingItem();
            } else if(player.isUsingItem()) {
                useBlower(player, level, 60);
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
                if(getPlayerEnergy(player) >= getEnergyUsage()) {
                    player.startUsingItem(hand);
                    return InteractionResultHolder.success(itemStackIn);
                }
            }
            return InteractionResultHolder.pass(itemStackIn);
        }

        @Override
        public boolean isAllowed() {
            return ToolModuleConfig.leafBlowerModuleIsAllowed;
        }

        /**
         *
         * @param player
         * @param level
         * @param fovDegrees
         */
        private void useBlower(Player player, Level level, double fovDegrees) {
            double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
            if(playerEnergy >= getEnergyUsage()) {
                int radius = (int) applyPropertyModifiers(MPSConstants.RADIUS);

                if (NuminaClientConfig.useSounds) {
                    Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_LEAF_BLOWER.get(), SoundSource.PLAYERS, 1, 1F, true);
                }

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
            return;
        }

        ItemStack shears = new ItemStack(Items.SHEARS);
        if(shears.isCorrectToolForDrops(state)) {
            block.playerDestroy(world, player, pos, state, world.getBlockEntity(pos), new ItemStack(Items.SHEARS));
            world.removeBlock(pos, false);
            return;
        }

        if ((block instanceof IShearable || block instanceof BushBlock || block instanceof LeavesBlock)
            && block.canHarvestBlock(state, world, pos, player)) {
            block.playerDestroy(world, player, pos, state, world.getBlockEntity(pos), new ItemStack(Items.SHEARS));
            world.removeBlock(pos, false);
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
