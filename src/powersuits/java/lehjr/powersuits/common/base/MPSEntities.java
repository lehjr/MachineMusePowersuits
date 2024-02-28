package lehjr.powersuits.common.base;

import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import lehjr.powersuits.common.entity.LuxCapacitorEntity;
import lehjr.powersuits.common.entity.PlasmaBallEntity;
import lehjr.powersuits.common.entity.SpinningBladeEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MPSEntities {
    /**
     * Entity Types ------------------------------------------------------------------------------
     */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MPSConstants.MOD_ID);

    public static final RegistryObject<EntityType<LuxCapacitorEntity>> LUX_CAPACITOR_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.LUX_CAPACITOR.getPath(),
            ()-> EntityType.Builder.<LuxCapacitorEntity>of(LuxCapacitorEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .build(MPSRegistryNames.LUX_CAPACITOR.toString()));

    public static final RegistryObject<EntityType<SpinningBladeEntity>> SPINNING_BLADE_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.SPINNING_BLADE.getPath(),
            ()-> EntityType.Builder.<SpinningBladeEntity>of(SpinningBladeEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F) // FIXME! check size
                    .build(MPSRegistryNames.SPINNING_BLADE.toString()));

    public static final RegistryObject<EntityType<PlasmaBallEntity>> PLASMA_BALL_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.PLASMA_BALL.getPath(),
            ()-> EntityType.Builder.<PlasmaBallEntity>of(PlasmaBallEntity::new, MobCategory.MISC)
//                    .size(0.25F, 0.25F)
                    .build(MPSRegistryNames.PLASMA_BALL.toString()));

//    public static final RegistryObject<EntityType<RailgunBoltEntity>> RAILGUN_BOLT_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.RAILGUN_BOLT.getPath(),
//            ()-> EntityType.Builder.<RailgunBoltEntity>of(RailgunBoltEntity::new, MobCategory.MISC)
//                    .sized(0.25F, 0.25F)
//                    .build(MPSRegistryNames.RAILGUN_BOLT.toString()));
}
