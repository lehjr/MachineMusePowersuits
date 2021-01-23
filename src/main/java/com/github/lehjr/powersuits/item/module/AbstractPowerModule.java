package com.github.lehjr.powersuits.item.module;

import com.github.lehjr.numina.util.string.AdditionalInfo;
import com.github.lehjr.powersuits.basemod.MPSObjects;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractPowerModule extends Item {

    public AbstractPowerModule() {
        super(new Item.Properties()
                .maxStackSize(1)
                .group(MPSObjects.creativeTab)
                .defaultMaxDamage(-1)
                .setNoRepair());
    }

    public static RayTraceResult rayTrace(World worldIn, PlayerEntity player, RayTraceContext.FluidMode fluidMode, double range) {
        float pitch = player.rotationPitch;
        float yaw = player.rotationYaw;
        Vector3d vec3d = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-pitch * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-pitch * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vector3d vec3d1 = vec3d.add((double)f6 * range, (double)f5 * range, (double)f7 * range);
        return worldIn.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
    }

    @Nullable
    @Override
    public abstract ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt);

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<ITextComponent> tooltips, ITooltipFlag flagIn) {
        if (worldIn != null) {
            AdditionalInfo.addInformation(itemStack, worldIn, tooltips, flagIn);
        }
    }
}