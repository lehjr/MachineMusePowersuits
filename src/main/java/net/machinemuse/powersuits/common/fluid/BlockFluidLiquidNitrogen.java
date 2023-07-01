package net.machinemuse.powersuits.common.fluid;

import net.machinemuse.powersuits.common.base.MPSItems;
import net.machinemuse.powersuits.common.constants.MPSModConstants;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidFinite;

public class BlockFluidLiquidNitrogen extends BlockFluidFinite {
    public BlockFluidLiquidNitrogen(ResourceLocation regName) {
        super(MPSItems.liquidNitrogen, Material.WATER);
        setRegistryName(regName);
        setTranslationKey(new StringBuilder(MPSModConstants.MODID).append(".").append(LiquidNitrogen.name).toString());
    }
}