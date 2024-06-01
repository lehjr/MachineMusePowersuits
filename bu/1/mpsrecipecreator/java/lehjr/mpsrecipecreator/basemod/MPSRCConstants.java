package lehjr.mpsrecipecreator.basemod;

import net.minecraft.resources.ResourceLocation;

/**
 * @author lehjr
 */
public class MPSRCConstants {
    public static final String MOD_ID = "mpsrecipecreator";
    public static final String NAME = "MPS Recipe Creator";
    public static final String VERSION = "@VERSION@";
    public static final String RESOURCE_DOMAIN = MOD_ID.toLowerCase();
    public static final String RESOURCE_PREFIX = RESOURCE_DOMAIN + ":";
    public static final String TEXTURE_PREFIX = RESOURCE_PREFIX + "textures/";
    public static final ResourceLocation RECIPE_WORKBENCH__REGNAME = new ResourceLocation(MOD_ID,  "recipe_workbench");
    public static final ResourceLocation RECIPE_WORKBENCH_TYPE__REG_NAME = new ResourceLocation(MOD_ID,  "recipe_workbench_container_type");
}
