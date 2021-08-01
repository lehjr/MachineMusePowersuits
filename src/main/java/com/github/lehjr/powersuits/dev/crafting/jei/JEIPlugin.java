package com.github.lehjr.powersuits.dev.crafting.jei;

import com.github.lehjr.powersuits.constants.MPSConstants;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private static final ResourceLocation UID = new ResourceLocation(MPSConstants.MOD_ID, "main");
    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new TransferInfo());
    }
}