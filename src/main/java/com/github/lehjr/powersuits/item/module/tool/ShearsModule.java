package com.github.lehjr.powersuits.item.module.tool;

import com.github.lehjr.numina.util.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import com.github.lehjr.numina.util.capabilities.module.rightclick.RightClickModule;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

// FIXME IForgeShearable ?? pretty much a dead thing now?
public class ShearsModule extends AbstractPowerModule {
    static final ArrayList<Material> materials =
            new ArrayList<Material>() {{
                add(Material.PLANTS);
                add(Material.OCEAN_PLANT);
                add(Material.TALL_PLANTS);
                add(Material.SEA_GRASS);
                add(Material.WEB);
                add(Material.WOOL);
                add(Material.LEAVES);

            }};

    public ShearsModule() {
        super();
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IRightClickModule rightClick;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.rightClick = new BlockBreaker(module, EnumModuleCategory.TOOL, EnumModuleTarget.TOOLONLY, MPSSettings::getModuleConfig);
            this.rightClick.addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 1000, "FE");
            this.rightClick.addBaseProperty(MPSConstants.HARVEST_SPEED, 8, "x");
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> rightClick));
        }

        class BlockBreaker extends RightClickModule implements IBlockBreakingModule {
            public BlockBreaker(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public boolean onBlockDestroyed(ItemStack itemStack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, int playerEnergy) {
                if (entityLiving.world.isRemote()) {
                    return false;
                }
                Block block = state.getBlock();

                if (block instanceof IForgeShearable && ElectricItemUtils.getPlayerEnergy(((PlayerEntity) entityLiving)) > getEnergyUsage()) {
                    IForgeShearable target = (IForgeShearable) block;
                    if (target.isShearable(itemStack, entityLiving.world, pos)) {
                        // default List<ItemStack> onSheared(@Nullable PlayerEntity player, @Nonnull ItemStack item, World world, BlockPos pos, int fortune)
                        List<ItemStack> drops = target.onSheared((PlayerEntity)entityLiving, itemStack, entityLiving.world, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemStack));
                        Random rand = new Random();

                        for (ItemStack stack : drops) {
                            float f = 0.7F;
                            double d = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                            double d1 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                            double d2 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                            ItemEntity entityitem = new ItemEntity(entityLiving.world, (double) pos.getX() + d, (double) pos.getY() + d1, (double) pos.getZ() + d2, stack);
                            entityitem.setDefaultPickupDelay(); // this is 10
                            entityitem.world.addEntity(entityitem);
                        }
                        ElectricItemUtils.drainPlayerEnergy((PlayerEntity) entityLiving, getEnergyUsage());
                        ((PlayerEntity) (entityLiving)).addStat(block.getRegistryName());
                        return true;
                    }
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
                event.setNewSpeed((float) (event.getNewSpeed() * applyPropertyModifiers(MPSConstants.HARVEST_SPEED)));
            }

            @Override
            public ActionResult onItemRightClick(ItemStack itemStackIn, World worldIn, PlayerEntity playerIn, Hand hand) {
                if (playerIn.world.isRemote) {
                    return ActionResult.resultPass(itemStackIn);
                }

                RayTraceResult rayTraceResult = rayTrace(playerIn.world, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
                if (rayTraceResult != null && rayTraceResult instanceof EntityRayTraceResult
                        && ((EntityRayTraceResult) rayTraceResult).getEntity() instanceof IForgeShearable) {
                    IForgeShearable target = (IForgeShearable) ((EntityRayTraceResult) rayTraceResult).getEntity();
                    Entity entity = ((EntityRayTraceResult) rayTraceResult).getEntity();
                    if (target.isShearable(itemStackIn, entity.world, entity.getPosition())) {
                        List<ItemStack> drops = target.onSheared(playerIn, itemStackIn, entity.world, entity.getPosition(), 0);
                        Random rand = new Random();
                        for (ItemStack drop : drops) {
                            ItemEntity ent = entity.entityDropItem(drop, 1.0F);
                            Vector3d motion = ent.getMotion();
                            ent.setMotion(
                                    motion.x + (rand.nextFloat() - rand.nextFloat()) * 0.1F,
                                    motion.y + rand.nextFloat() * 0.05F,
                                    motion.z + (rand.nextFloat() - rand.nextFloat()) * 0.1F);
                        }
                        ElectricItemUtils.drainPlayerEnergy(playerIn, getEnergyUsage());
                        return ActionResult.resultSuccess(itemStackIn);
                    }
                }
                return ActionResult.resultPass(itemStackIn);
            }
        }
    }
}