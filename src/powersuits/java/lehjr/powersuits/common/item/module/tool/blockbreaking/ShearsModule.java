package lehjr.powersuits.common.item.module.tool.blockbreaking;

import lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.common.config.module.ToolModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.IShearable;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

// FIXME IShearable ?? pretty much a dead thing now?
public class ShearsModule extends AbstractPowerModule {
    static final ResourceLocation CABLE_INSULATOR = ResourceLocation.fromNamespaceAndPath("energizedpower","cable_insulator");
    //new ResourceLoITEMS.register(, () -> new CableInsulatorItem(new Item.Properties()));
    
    public static class BlockBreaker extends RightClickModule implements IBlockBreakingModule {
        public BlockBreaker(ItemStack module) {
            super(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.shearsModuleEnergyConsumptionBase, "FE");
            addBaseProperty(NuminaConstants.HARVEST_SPEED , ToolModuleConfig.shearsModuleEnergyHarvestSpeedBase, "x");
        }

        @Override
        public boolean isAllowed() {
            return ToolModuleConfig.shearsModuleIsAllowed;
        }

        @Override
        public int getTier() {
            return super.getTier();
        }

        @Override
        public boolean mineBlock(ItemStack powerFist, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving, double playerEnergy) {
            if (level.isClientSide() || state.is(BlockTags.FIRE)) {
                return false;
            }

            if(ElectricItemUtils.getPlayerEnergy(entityLiving) > getEnergyUsage() && getEmulatedTool().isCorrectToolForDrops(state)) {
                getEmulatedTool().mineBlock(level, state, pos, (Player)entityLiving);
                ElectricItemUtils.drainPlayerEnergy(entityLiving, getEnergyUsage(), false);
                return true;
            }
            return false;
        }

        @Override
        public ItemStack getEmulatedTool() {
            return new ItemStack(Items.SHEARS);
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }

        @Override
        public void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
            event.setNewSpeed((float) (event.getNewSpeed() * applyPropertyModifiers(NuminaConstants.HARVEST_SPEED )));
        }

        @Override
        public InteractionResult useOn(UseOnContext context) {
            Player player = context.getPlayer();
            if(player == null) {
                return super.useOn(context);
            }

            double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
            double energyUsage = applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            
            if(energyUsage > playerEnergy) {
                return super.useOn(context);
            }
            Level level = context.getLevel();
            BlockPos blockpos = context.getClickedPos();
            BlockState blockstate = level.getBlockState(blockpos);
            ItemStack cableInsulator = new ItemStack(BuiltInRegistries.ITEM.get(CABLE_INSULATOR), 18);
            

                if (blockstate.is(BlockTags.WOOL) && !cableInsulator.isEmpty()) {
                    if (!level.isClientSide) {
                        if (!player.isCreative()) {
                            ElectricItemUtils.drainPlayerEnergy(player, energyUsage, false);
                        }

                        level.destroyBlock(blockpos, false, player);
                        ItemEntity itemEntity = new ItemEntity(level, (double)blockpos.getX() + (double)0.5F, (double)blockpos.getY() + (double)0.5F, (double)blockpos.getZ() + (double)0.5F, cableInsulator,
                            0.0F,
                            0.0F,
                            0.0F);
                        itemEntity.setPickUpDelay(20);
                        level.addFreshEntity(itemEntity);
                        level.playSound(null, blockpos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
                
            BlockState blockstate1 = blockstate.getToolModifiedState(context, ItemAbilities.SHEARS_TRIM, false);
            if (blockstate1 != null) {
                ItemStack itemstack = context.getItemInHand();
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
                }

                level.setBlockAndUpdate(blockpos, blockstate1);
                level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(context.getPlayer(), blockstate1));
                ElectricItemUtils.drainPlayerEnergy(player, getEnergyUsage(), false);

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return super.useOn(context);
            }
        }

        @Override
        public InteractionResultHolder<ItemStack> interactLivingEntity(ItemStack itemStackIn, Player player, LivingEntity entity, InteractionHand hand) {
            if (entity instanceof IShearable target && ElectricItemUtils.getPlayerEnergy(player) > getEnergyUsage()) {
                BlockPos pos = entity.blockPosition();
                if (target.isShearable(player, getEmulatedTool(), entity.level(), pos)) {
                    Level level = entity.level();
                    if (level.isClientSide) {
                        return InteractionResultHolder.success(itemStackIn);
                    }
                    for (ItemStack drop : target.onSheared(player, getEmulatedTool(), level, pos)) {
                        target.spawnShearedDrop(level, pos, drop);
                    }
                    entity.gameEvent(GameEvent.SHEAR, player);
                    ElectricItemUtils.drainPlayerEnergy(player, getEnergyUsage(), false);
                    return InteractionResultHolder.consume(itemStackIn);
                }

            }

            return InteractionResultHolder.pass(itemStackIn);
        }
    }
}
