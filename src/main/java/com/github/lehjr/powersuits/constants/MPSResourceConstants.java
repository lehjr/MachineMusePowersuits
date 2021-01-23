package com.github.lehjr.powersuits.constants;

import com.github.lehjr.powersuits.basemod.ModularPowersuits;
import net.minecraft.util.ResourceLocation;

public class MPSResourceConstants {
    public static final String RESOURCE_PREFIX = MPSConstants.MOD_ID + ":";
    public static final String TEXTURE_PREFIX = RESOURCE_PREFIX + "textures/";
    public static final String RESOURCE_DOMAIN = MPSConstants.MOD_ID.toLowerCase();


    public static final ResourceLocation GLASS_TEXTURE = new ResourceLocation(TEXTURE_PREFIX + "gui/glass.png");

    public static final String BLANK_ARMOR_MODEL_PATH = TEXTURE_PREFIX + "item/armor/blankarmor.png";

    // temporary locations until model spec system up and running
    public static final String SEBK_AMROR_PANTS = TEXTURE_PREFIX + "item/armor/sebkarmorpants.png";
    public static final String SEBK_AMROR = TEXTURE_PREFIX + "item/armor/sebkarmor.png";
    public static final String CITIZEN_JOE_ARMOR_PANTS = TEXTURE_PREFIX + "item/armor/joearmorpants.png";
    public static final String CITIZEN_JOE_ARMOR = TEXTURE_PREFIX + "item/armor/joearmor.png";
}
