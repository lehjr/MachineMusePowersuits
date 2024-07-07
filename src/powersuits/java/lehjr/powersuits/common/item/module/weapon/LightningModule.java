package lehjr.powersuits.common.item.module.weapon;


import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.rightclick.RightClickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.HeatUtils;
import lehjr.powersuits.common.config.MPSCommonConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Created by User: Andrew2448
 * 5:56 PM 6/14/13
 */
public class LightningModule extends AbstractPowerModule {
    public static class RightClickie extends RightClickModule {

        public RightClickie(ItemStack module) {
            super(module, ModuleCategory.WEAPON, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ()-> ()-> MPSCommonConfig.lightningSummonerEnergyConsumption, "FE");
            addBaseProperty(MPSConstants.HEAT_EMISSION, ()-> ()-> MPSCommonConfig.lightningSummonerHeatEmission, "");
        }

        @Override
        public InteractionResultHolder<ItemStack> use(@NonNull ItemStack itemStackIn, Level level, Player playerIn, InteractionHand hand) {
            if (hand == InteractionHand.MAIN_HAND) {
                int energyConsumption = getEnergyUsage();
                if (energyConsumption < ElectricItemUtils.getPlayerEnergy(playerIn)) {
                    if (!level.isClientSide()) {
                        double range = 64;

                        HitResult raytraceResult = rayTrace(level, playerIn, ClipContext.Fluid.SOURCE_ONLY, range);
                        if (raytraceResult != null && raytraceResult.getType() != HitResult.Type.MISS) {
                            BlockPos targetPos = new BlockPos((int)raytraceResult.getLocation().x, (int)raytraceResult.getLocation().y, (int)raytraceResult.getLocation().z);
                            if (level.canSeeSky(targetPos)) {
                                ElectricItemUtils.drainPlayerEnergy(playerIn, energyConsumption, false);
                                HeatUtils.heatPlayer(playerIn, applyPropertyModifiers(MPSConstants.HEAT_EMISSION));
                                LightningBolt sparkie = EntityType.LIGHTNING_BOLT.create(level);

                                if (sparkie != null) {
                                    sparkie.setCause((ServerPlayer) playerIn);
                                    sparkie.moveTo(Vec3.atBottomCenterOf(targetPos));
                                    sparkie.setCause(playerIn instanceof ServerPlayer ? (ServerPlayer)playerIn : null);
                                    level.addFreshEntity(sparkie);
                                }
                            }
                        }
                    }
                    return InteractionResultHolder.success(itemStackIn);
                }
            }
            return InteractionResultHolder.pass(itemStackIn);
        }

        @Override
        public boolean isAllowed() {
            return MPSCommonConfig.lightningSummonerIsAllowed;
        }

        @Override
        public int getEnergyUsage() {
            return (int) Math.round(applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION));
        }

        @Override
        public int getTier() {
            return 2;
        }
    }
}