//package com.github.lehjr.powersuits.client.event;
//
//import com.github.lehjr.powersuits.basemod.MPSConstants;
//import com.github.lehjr.powersuits.basemod.MPSObjects;
//import net.minecraft.client.world.ClientWorld;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.item.ItemEntity;
//import net.minecraft.entity.item.ItemFrameEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.*;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.nbt.NBTUtil;
//import net.minecraft.util.Direction;
//import net.minecraft.util.RegistryKey;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.math.vector.Vector3d;
//import net.minecraft.world.World;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import javax.annotation.Nullable;
//import java.util.Optional;
//
//public class RegisterModelProperties {
//    public static void registerPropertyOverride() {
//        IItemPropertyGetter TIME = ItemModelsProperties.func_239417_a_(Items.CLOCK, new ResourceLocation("time"));
//        IItemPropertyGetter ANGLE = ItemModelsProperties.func_239417_a_(Items.COMPASS, new ResourceLocation("angle"));
//
//        System.out.println("time null? " + (TIME == null));
//        System.out.println("angle null? " + (ANGLE == null));
//
//
//        ItemModelsProperties.registerProperty(MPSObjects.CLOCK_MODULE.get(), new ResourceLocation(MPSConstants.MOD_ID, "clock"), TIME);
//        ItemModelsProperties.registerProperty(MPSObjects.COMPASS_MODULE.get(), /*new ResourceLocation(MPSConstants.MOD_ID, "compass")*/
//                new ResourceLocation(MPSConstants.MOD_ID, "angle"),
//                new IItemPropertyGetter() {
//                    private final Angle field_239439_a_ = new Angle();
//                    private final Angle field_239440_b_ = new Angle();
//
//                    public float call(ItemStack itemStack, @Nullable ClientWorld world, @Nullable LivingEntity entityIn) {
//                        Entity entity = entityIn != null ? entityIn : itemStack.getAttachedEntity();
//                        if (entity == null) {
//                            return 0.0F;
//                        } else {
//                            if (world == null && entity.world instanceof ClientWorld) {
//                                world = (ClientWorld)entity.world;
//                            }
//
//                            BlockPos blockpos = CompassItem.func_234670_d_(itemStack) ? func_239442_a_(world, itemStack.getOrCreateTag()) : func_239444_a_(world);
//                            long i = world.getGameTime();
//                            if (blockpos != null && !(entity.getPositionVec().squareDistanceTo((double)blockpos.getX() + 0.5D, entity.getPositionVec().getY(), (double)blockpos.getZ() + 0.5D) < (double)1.0E-5F)) {
//                                boolean flag = entityIn instanceof PlayerEntity && ((PlayerEntity)entityIn).isUser();
//                                double d1 = 0.0D;
//                                if (flag) {
//                                    d1 = entityIn.rotationYaw;
//                                } else if (entity instanceof ItemFrameEntity) {
//                                    d1 = func_239441_a_((ItemFrameEntity)entity);
//                                } else if (entity instanceof ItemEntity) {
//                                    d1 = 180.0F - ((ItemEntity)entity).getItemHover(0.5F) / ((float)Math.PI * 2F) * 360.0F;
//                                } else if (entityIn != null) {
//                                    d1 = entityIn.renderYawOffset;
//                                }
//
//                                d1 = MathHelper.positiveModulo(d1 / 360.0D, 1.0D);
//                                double d2 = func_239443_a_(Vector3d.copyCentered(blockpos), entity) / (double)((float)Math.PI * 2F);
//                                double d3;
//                                if (flag) {
//                                    if (this.field_239439_a_.func_239448_a_(i)) {
//                                        this.field_239439_a_.func_239449_a_(i, 0.5D - (d1 - 0.25D));
//                                    }
//
//                                    d3 = d2 + this.field_239439_a_.field_239445_a_;
//                                } else {
//                                    d3 = 0.5D - (d1 - 0.25D - d2);
//                                }
//
//                                return MathHelper.positiveModulo((float)d3, 1.0F);
//                            } else {
//                                if (this.field_239440_b_.func_239448_a_(i)) {
//                                    this.field_239440_b_.func_239449_a_(i, Math.random());
//                                }
//
//                                double d0 = this.field_239440_b_.field_239445_a_ + (double)((float)itemStack.hashCode() / 2.14748365E9F);
//                                return MathHelper.positiveModulo((float)d0, 1.0F);
//                            }
//                        }
//                    }
//                });
//    }
//
//
//
//
//    private static double func_239441_a_(ItemFrameEntity p_239441_1_) {
//        Direction direction = p_239441_1_.getHorizontalFacing();
//        int i = direction.getAxis().isVertical() ? 90 * direction.getAxisDirection().getOffset() : 0;
//        return MathHelper.wrapDegrees(180 + direction.getHorizontalIndex() * 90 + p_239441_1_.getRotation() * 45 + i);
//    }
//
//    @Nullable
//    private static BlockPos func_239442_a_(World world, CompoundNBT nbt) {
//        boolean flag = nbt.contains("LodestonePos");
//        boolean flag1 = nbt.contains("LodestoneDimension");
//        if (flag && flag1) {
//            Optional<RegistryKey<World>> optional = CompassItem.func_234667_a_(nbt);
//            if (optional.isPresent() && world.getDimensionKey() == optional.get()) {
//                return NBTUtil.readBlockPos(nbt.getCompound("LodestonePos"));
//            }
//        }
//
//        return null;
//    }
//
//    private static double func_239443_a_(Vector3d p_239443_1_, Entity entity) {
//        return Math.atan2(p_239443_1_.getZ() - entity.getPosZ(), p_239443_1_.getX() - entity.getPosX());
//    }
//
//    @Nullable
//    private static BlockPos func_239444_a_(ClientWorld clientWorld) {
//        return clientWorld.getDimensionType().isNatural() ? clientWorld.func_239140_u_() : null;
//    }
//
//
//    static class Angle {
//        private double field_239445_a_;
//        private double field_239446_b_;
//        private long field_239447_c_;
//
//        private Angle() {
//        }
//
//        private boolean func_239448_a_(long p_239448_1_) {
//            return this.field_239447_c_ != p_239448_1_;
//        }
//
//        private void func_239449_a_(long p_239449_1_, double p_239449_3_) {
//            this.field_239447_c_ = p_239449_1_;
//            double d0 = p_239449_3_ - this.field_239445_a_;
//            d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
//            this.field_239446_b_ += d0 * 0.1D;
//            this.field_239446_b_ *= 0.8D;
//            this.field_239445_a_ = MathHelper.positiveModulo(this.field_239445_a_ + this.field_239446_b_, 1.0D);
//        }
//    }
//}
