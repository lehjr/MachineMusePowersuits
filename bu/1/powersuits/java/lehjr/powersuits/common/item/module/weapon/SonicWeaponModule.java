//package lehjr.powersuits.item.module.weapon;
//
//import lehjr.powersuits.basemod.MPSConstants;
//import lehjr.powersuits.item.module.AbstractPowerModule;
//import lehjr.numina.common.capabilities.module.powermodule.IConfig;
//import lehjr.numina.common.capabilities.module.powermodule.EnumModuleCategory;
//import lehjr.numina.common.capabilities.module.powermodule.EnumModuleTarget;
//import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
//import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
//import lehjr.numina.common.energy.ElectricItemUtils;
//import lehjr.numina.heat.HeatUtils;
//import lehjr.numina.nbt.NBTUtils;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.particles.ParticleTypes;
//import net.minecraft.util.*;
//import net.minecraft.util.math.*;
//import net.minecraft.world.level.Level;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.ICapabilityProvider;
//import net.minecraftforge.common.util.LazyOptional;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//
///*
//See these for ideas:
//    net.minecraft.entity.monster.GuardianEntity (damage)
//    net.minecraft.client.renderer.entity.GuardianRenderer (rendering)
//   */
//
//
//public class SonicWeaponModule extends AbstractPowerModule {
//    public SonicWeaponModule(String regname) {
//        super(regname);
//    }
//
//    @Nullable
//    @Override
//    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
//        return null;
//    }
//
//    public class CapProvider implements ICapabilityProvider {
//        ItemStack module;
//        IRightClickModule clickie;
//
//        public CapProvider(@Nonnull ItemStack module) {
//
//        }
//
//        @Nonnull
//        @Override
//        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//            return null;
//        }
//
//        class Ticker extends PlayerTickModule implements IRightClickModule {
//            public Ticker(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config, boolean defBool) {
//                super(module, category, target, config, defBool);
//            }
//
//            @Override
//            public void onPlayerTickActive(Player player, @Nonnull ItemStack itemStackIn) {
//                double timer = NBTUtils.getModularItemDoubleOrZero(itemStackIn, MPSConstants.TIMER);
//                if (timer > 0)
//                    NBTUtils.setModularItemDoubleOrRemove(itemStackIn, MPSConstants.TIMER, timer - 1 > 0 ? timer - 1 : 0);
//            }
//
//            @Override
//            public ActionResult onItemRightClick(ItemStack itemStackIn, Level worldIn, Player playerIn, Hand hand) {
//                if (hand == InteractionHand.MAIN_HAND) {
//                    double range = 64;
//                    double timer = NBTUtils.getModularItemDoubleOrZero(itemStackIn, MPSConstants.TIMER);
//                    double energyConsumption = getEnergyUsage();
//                    if (ElectricItemUtils.getPlayerEnergy(playerIn) > energyConsumption && timer == 0) {
//                        ElectricItemUtils.drainPlayerEnergy(playerIn, (int) energyConsumption);
//                        NBTUtils.setModularItemDoubleOrRemove(itemStackIn, MPSConstants.TIMER, 10);
//
//                        HeatUtils.heatPlayer(playerIn, applyPropertyModifiers(MPSConstants.RAILGUN_HEAT_EMISSION));
//                        HitResult raytraceresult = rayTrace(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);
//
//                        if (raytraceresult != null) {
////                            double damage = applyPropertyModifiers(MPSConstants.RAILGUN_TOTAL_IMPULSE) / 100.0;
//                            double damage = 100;
//                            double knockback = damage / 20.0;
//                            Vec3d lookVec = playerIn.getLookVec();
//
//                            switch (raytraceresult.getType()) {
//                                case MISS:
//                                    worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundSource.PLAYERS, 0.5F, 0.4F / ((float) Math.random() * 0.4F + 0.8F));
//                                    break;
//
//                                case BLOCK:
//                                    drawParticleStreamTo(playerIn, worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
//                                    worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundSource.PLAYERS, 0.5F, 0.4F / ((float) Math.random() * 0.4F + 0.8F));
//                                    break;
//
//
//                                case ENTITY:
//                                    drawParticleStreamTo(playerIn, worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
//                                    worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundSource.PLAYERS, 0.5F, 0.4F / ((float) Math.random() * 0.4F + 0.8F));
//                                    DamageSource damageSource = DamageSource.causePlayerDamage(playerIn);
//                                    if (((EntityHitResult) raytraceresult).getEntity().attackEntityFrom(damageSource, (int) damage)) {
//                                        ((EntityHitResult) raytraceresult).getEntity().addVelocity(lookVec.x * knockback, Math.abs(lookVec.y + 0.2f) * knockback, lookVec.z * knockback);
//                                    }
//                                    break;
//                            }
//                            playerIn.addVelocity(-lookVec.x * knockback, Math.abs(-lookVec.y + 0.2f) * knockback, -lookVec.z * knockback);
//                        }
//                    }
//                    playerIn.setActiveHand(hand);
//                    return new ActionResult(InteractionResult.SUCCESS, itemStackIn);
//                }
//                return new ActionResult(InteractionResult.PASS, itemStackIn);
//            }
//
//            public void drawParticleStreamTo(Player source, Level world, double x, double y, double z) {
//                Vec3d direction = source.getLookVec().normalize();
//                double xoffset = 1.3f;
//                double yoffset = -.2;
//                double zoffset = 0.3f;
//                Vec3d horzdir = direction.normalize();
//                horzdir = new Vec3d(horzdir.x, 0, horzdir.z);
//                horzdir = horzdir.normalize();
//                double cx = source.posX + direction.x * xoffset - direction.y * horzdir.x * yoffset - horzdir.z * zoffset;
//                double cy = source.posY + source.getEyeHeight() + direction.y * xoffset + (1 - Math.abs(direction.y)) * yoffset;
//                double cz = source.posZ + direction.z * xoffset - direction.y * horzdir.z * yoffset + horzdir.x * zoffset;
//                double dx = x - cx;
//                double dy = y - cy;
//                double dz = z - cz;
//                double ratio = Math.sqrt(dx * dx + dy * dy + dz * dz);
//
//                while (Math.abs(cx - x) > Math.abs(dx / ratio)) {
//                    world.addParticle(ParticleTypes.MYCELIUM, cx, cy, cz, 0.0D, 0.0D, 0.0D);
//                    cx += dx * 0.1 / ratio;
//                    cy += dy * 0.1 / ratio;
//                    cz += dz * 0.1 / ratio;
//                }
//            }
//
//
//
//
//            private final ResourceLocation GUARDIAN_BEAM_TEXTURE = new ResourceLocation("textures/entity/guardian_beam.png");
//
//
//            public void doRender(Player source, double x, double y, double z, float entityYaw, float partialTicks) {
//////                super.doRender(attacker, x, y, z, entityYaw, partialTicks);
////                LivingEntity target = source.getTargetedEntity();
////                if (target != null) {
////                    float lvt_11_1_ = source.getAttackAnimationScale(partialTicks);
////                    Tesselator tessellator = Tesselator.getInstance();
////                    BufferBuilder buffer = tessellator.getBuffer();
////                    this.bindTexture(GUARDIAN_BEAM_TEXTURE);
////                    GlStateManager.texParameter(3553, 10242, 10497);
////                    GlStateManager.texParameter(3553, 10243, 10497);
////                    GlStateManager.disableLighting();
////                    GlStateManager.disableCull();
////                    GlStateManager.disableBlend();
////                    GlStateManager.depthMask(true);
////                    float lvt_14_1_ = 240.0F;
////                    GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 240.0F, 240.0F);
////                    GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
////                    float lvt_15_1_ = (float)source.world.getGameTime() + partialTicks;
////                    float lvt_16_1_ = lvt_15_1_ * 0.5F % 1.0F;
////                    float eyeHeight = source.getEyeHeight();
////                    GlStateManager.pushMatrix();
////                    GlStateManager.translatef((float)x, (float)y + eyeHeight, (float)z);
////                    Vec3d lvt_18_1_ = this.getPosition(target, (double)target.getHeight() * 0.5D, partialTicks);
////                    Vec3d lvt_19_1_ = this.getPosition(source, (double)eyeHeight, partialTicks);
////                    Vec3d lvt_20_1_ = lvt_18_1_.subtract(lvt_19_1_);
////                    double lvt_21_1_ = lvt_20_1_.length() + 1.0D;
////                    lvt_20_1_ = lvt_20_1_.normalize();
////                    float lvt_23_1_ = (float)Math.acos(lvt_20_1_.y);
////                    float lvt_24_1_ = (float)Math.atan2(lvt_20_1_.z, lvt_20_1_.x);
////                    GlStateManager.rotatef((1.5707964F - lvt_24_1_) * 57.295776F, 0.0F, 1.0F, 0.0F);
////                    GlStateManager.rotatef(lvt_23_1_ * 57.295776F, 1.0F, 0.0F, 0.0F);
////                    int lvt_25_1_ = true;
////                    double lvt_26_1_ = (double)lvt_15_1_ * 0.05D * -1.5D;
////                    buffer.begin(7, DefaultVertexFormat.POSITION_TEX_COLOR);
////                    float lvt_28_1_ = lvt_11_1_ * lvt_11_1_;
////                    int lvt_29_1_ = 64 + (int)(lvt_28_1_ * 191.0F);
////                    int lvt_30_1_ = 32 + (int)(lvt_28_1_ * 191.0F);
////                    int lvt_31_1_ = 128 - (int)(lvt_28_1_ * 64.0F);
////                    double lvt_32_1_ = 0.2D;
////                    double lvt_34_1_ = 0.282D;
////                    double lvt_36_1_ = 0.0D + Math.cos(lvt_26_1_ + 2.356194490192345D) * 0.282D;
////                    double lvt_38_1_ = 0.0D + Math.sin(lvt_26_1_ + 2.356194490192345D) * 0.282D;
////                    double lvt_40_1_ = 0.0D + Math.cos(lvt_26_1_ + 0.7853981633974483D) * 0.282D;
////                    double lvt_42_1_ = 0.0D + Math.sin(lvt_26_1_ + 0.7853981633974483D) * 0.282D;
////                    double lvt_44_1_ = 0.0D + Math.cos(lvt_26_1_ + 3.9269908169872414D) * 0.282D;
////                    double lvt_46_1_ = 0.0D + Math.sin(lvt_26_1_ + 3.9269908169872414D) * 0.282D;
////                    double lvt_48_1_ = 0.0D + Math.cos(lvt_26_1_ + 5.497787143782138D) * 0.282D;
////                    double lvt_50_1_ = 0.0D + Math.sin(lvt_26_1_ + 5.497787143782138D) * 0.282D;
////                    double lvt_52_1_ = 0.0D + Math.cos(lvt_26_1_ + 3.141592653589793D) * 0.2D;
////                    double lvt_54_1_ = 0.0D + Math.sin(lvt_26_1_ + 3.141592653589793D) * 0.2D;
////                    double lvt_56_1_ = 0.0D + Math.cos(lvt_26_1_ + 0.0D) * 0.2D;
////                    double lvt_58_1_ = 0.0D + Math.sin(lvt_26_1_ + 0.0D) * 0.2D;
////                    double lvt_60_1_ = 0.0D + Math.cos(lvt_26_1_ + 1.5707963267948966D) * 0.2D;
////                    double lvt_62_1_ = 0.0D + Math.sin(lvt_26_1_ + 1.5707963267948966D) * 0.2D;
////                    double lvt_64_1_ = 0.0D + Math.cos(lvt_26_1_ + 4.71238898038469D) * 0.2D;
////                    double lvt_66_1_ = 0.0D + Math.sin(lvt_26_1_ + 4.71238898038469D) * 0.2D;
////                    double lvt_70_1_ = 0.0D;
////                    double lvt_72_1_ = 0.4999D;
////                    double lvt_74_1_ = (double)(-1.0F + lvt_16_1_);
////                    double lvt_76_1_ = lvt_21_1_ * 2.5D + lvt_74_1_;
////                    buffer.pos(lvt_52_1_, lvt_21_1_, lvt_54_1_).tex(0.4999D, lvt_76_1_).color(lvt_29_1_, lvt_30_1_, lvt_31_1_, 255).endVertex();
////                    buffer.pos(lvt_52_1_, 0.0D, lvt_54_1_).tex(0.4999D, lvt_74_1_).color(lvt_29_1_, lvt_30_1_, lvt_31_1_, 255).endVertex();
////                    buffer.pos(lvt_56_1_, 0.0D, lvt_58_1_).tex(0.0D, lvt_74_1_).color(lvt_29_1_, lvt_30_1_, lvt_31_1_, 255).endVertex();
////                    buffer.pos(lvt_56_1_, lvt_21_1_, lvt_58_1_).tex(0.0D, lvt_76_1_).color(lvt_29_1_, lvt_30_1_, lvt_31_1_, 255).endVertex();
////                    buffer.pos(lvt_60_1_, lvt_21_1_, lvt_62_1_).tex(0.4999D, lvt_76_1_).color(lvt_29_1_, lvt_30_1_, lvt_31_1_, 255).endVertex();
////                    buffer.pos(lvt_60_1_, 0.0D, lvt_62_1_).tex(0.4999D, lvt_74_1_).color(lvt_29_1_, lvt_30_1_, lvt_31_1_, 255).endVertex();
////                    buffer.pos(lvt_64_1_, 0.0D, lvt_66_1_).tex(0.0D, lvt_74_1_).color(lvt_29_1_, lvt_30_1_, lvt_31_1_, 255).endVertex();
////                    buffer.pos(lvt_64_1_, lvt_21_1_, lvt_66_1_).tex(0.0D, lvt_76_1_).color(lvt_29_1_, lvt_30_1_, lvt_31_1_, 255).endVertex();
////                    double lvt_78_1_ = 0.0D;
////                    if (source.ticksExisted % 2 == 0) {
////                        lvt_78_1_ = 0.5D;
////                    }
////                    buffer.pos(lvt_36_1_, lvt_21_1_, lvt_38_1_).tex(0.5D, lvt_78_1_ + 0.5D).color(lvt_29_1_, lvt_30_1_, lvt_31_1_, 255).endVertex();
////                    buffer.pos(lvt_40_1_, lvt_21_1_, lvt_42_1_).tex(1.0D, lvt_78_1_ + 0.5D).color(lvt_29_1_, lvt_30_1_, lvt_31_1_, 255).endVertex();
////                    buffer.pos(lvt_48_1_, lvt_21_1_, lvt_50_1_).tex(1.0D, lvt_78_1_).color(lvt_29_1_, lvt_30_1_, lvt_31_1_, 255).endVertex();
////                    buffer.pos(lvt_44_1_, lvt_21_1_, lvt_46_1_).tex(0.5D, lvt_78_1_).color(lvt_29_1_, lvt_30_1_, lvt_31_1_, 255).endVertex();
////                    tessellator.draw();
////                    GlStateManager.popMatrix();
////                }
//            }
//
//            private Vec3d getPosition(LivingEntity entity, double p_177110_2_, float p_177110_4_) {
//                double x = MathHelper.lerp((double)p_177110_4_, entity.lastTickPosX, entity.posX);
//                double y = MathHelper.lerp((double)p_177110_4_, entity.lastTickPosY, entity.posY) + p_177110_2_;
//                double z = MathHelper.lerp((double)p_177110_4_, entity.lastTickPosZ, entity.posZ);
//                return new Vec3d(x, y, z);
//            }
//        }
//    }
//
//
////    @Override
////    public String getCategory() {
////        return MuseCommonStrings.CATEGORY_WEAPON;
////    }
////
////    @Override
////    public String getDataName() {
////        return MODULE_SONIC_WEAPON;
////    }
////
////    @Override
////    public String getUnlocalizedName() { return "sonicWeapon";
////    }
////
////    @Override
////    public String getDescription() {
////        return "A high-amplitude, complex-frequency soundwave generator can have shattering or disorienting effects on foes and blocks alike.";
////    }
////
////    @Override
////    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, Level worldIn, Player playerIn, EnumHand hand) {
////        return null;
////    }
////
////    @Override
////    public InteractionResult onItemUse(ItemStack stack, Player playerIn, Level worldIn, BlockPos pos, EnumHand hand, Direction facing, float hitX, float hitY, float hitZ) {
////        return null;
////    }
////
////    @Override
////    public InteractionResult onItemUseFirst(ItemStack stack, Player player, Level world, BlockPos pos, Direction side, float hitX, float hitY, float hitZ, EnumHand hand) {
////        return null;
////    }
////
////    @Override
////    public void onPlayerStoppedUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
////    }
////
////    @Override
////    public TextureAtlasSprite getIcon(ItemStack item) {
////        return MuseIcon.sonicWeapon;
////    }
//}