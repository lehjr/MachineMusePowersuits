package lehjr.powersuits.common.item.module.tool.blockbreaking.farming;

import lehjr.numina.common.capability.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.rightclick.RightClickModule;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

// FIXME IShearable ?? pretty much a dead thing now?
public class ShearsModule extends AbstractPowerModule {
//    static final ArrayList<Material> materials =
//            new ArrayList<Material>() {{
//                add(Material.PLANT);
//                add(Material.WATER_PLANT);
//                add(Material.REPLACEABLE_PLANT);
//                add(Material.REPLACEABLE_WATER_PLANT);
//                add(Material.WEB);
//                add(Material.WOOL);
//                add(Material.LEAVES);
//
//            }};


    class BlockBreaker extends RightClickModule implements IBlockBreakingModule {
        public BlockBreaker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target) {
            super(module, category, target);
//            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 1000, "FE");
//            addBaseProperty(MPSConstants.HARVEST_SPEED , 8, "x");
        }

        @Override
        public boolean mineBlock(@NotNull ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, double playerEnergy) {
//                if (entityLiving.level.isClientSide() || state.getBlock().is(BlockTags.FIRE)) {
//                    return false;
//                }
//                Block block = state.getBlock();
//
//                if (block instanceof IShearable && ElectricItemUtils.getPlayerEnergy(entityLiving) > getEnergyUsage()) {
//                    IShearable target = (IShearable) block;
//                    if (target.isShearable(powerFist, entityLiving.level, pos)) {
//                        List<ItemStack> drops = target.onSheared((Player)entityLiving, powerFist, entityLiving.level, pos, EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, powerFist));
//                        Random rand = new Random();
//                        drops.forEach(d -> {
//                            ItemEntity ent = entityLiving.spawnAtLocation(d, 1.0F);
//                            ent.setDeltaMovement(ent.getDeltaMovement().add((rand.nextFloat() - rand.nextFloat()) * 0.1F, rand.nextFloat() * 0.05F, (rand.nextFloat() - rand.nextFloat()) * 0.1F));
//                        });
//                        ElectricItemUtils.drainPlayerEnergy(entityLiving, getEnergyUsage());
//                    }
//                    return true;
//                }
            return false;
        }

        @Override
        public ItemStack getEmulatedTool() {
            return new ItemStack(Items.SHEARS);
        }

        @Override
        public int getEnergyUsage() {
//            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            return 0;
        }

        @Override
        public void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
//            event.setNewSpeed((float) (event.getNewSpeed() * applyPropertyModifiers(MPSConstants.HARVEST_SPEED )));
        }

        @Override
        public InteractionResultHolder<ItemStack> interactLivingEntity(ItemStack itemStackIn, Player playerIn, LivingEntity entity, InteractionHand hand) {
            if (playerIn.level().isClientSide) {
                return InteractionResultHolder.pass(itemStackIn);
            }
//            if (entity instanceof IShearable && ElectricItemUtils.getPlayerEnergy(playerIn) > getEnergyUsage()) {
//                IShearable target = (IShearable)entity;
//                BlockPos pos = entity.blockPosition();
//                if (target.isShearable(itemStackIn, entity.level(), pos)) {
//                    List<ItemStack> drops = target.onSheared(playerIn, itemStackIn, entity.level(), pos,
//                            EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, itemStackIn));
//                    Random rand = new Random();
//                    drops.forEach(d -> {
//                        ItemEntity ent = entity.spawnAtLocation(d, 1.0F);
//                        ent.setDeltaMovement(ent.getDeltaMovement().add((rand.nextFloat() - rand.nextFloat()) * 0.1F, rand.nextFloat() * 0.05F, (rand.nextFloat() - rand.nextFloat()) * 0.1F));
//                    });
//                    ElectricItemUtils.drainPlayerEnergy(playerIn, getEnergyUsage());
//                }
//                return InteractionResultHolder.success(itemStackIn);
//            }
            return InteractionResultHolder.pass(itemStackIn);
        }
    }
}
