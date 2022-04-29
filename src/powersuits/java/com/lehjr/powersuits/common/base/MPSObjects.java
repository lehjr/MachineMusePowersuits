package com.lehjr.powersuits.common.base;

import com.lehjr.powersuits.common.constants.MPSContstants;
import com.lehjr.powersuits.common.constants.MPSRegistryNames;
import com.lehjr.powersuits.common.entity.SpinningBladeEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MPSObjects {


    /**
     * Entity Types ------------------------------------------------------------------------------
     */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MPSContstants.MOD_ID);

//    private static final RegistryObject<EntityType<TestEntity>> TEST_ENTITY = ENTITIES.register("test_entity", () -> EntityType.Builder.of(TestEntity::new, MobCategory.CREATURE).sized(16.0F, 8.0F).clientTrackingRange(10).build("test_entity"));
//
//

    public static final RegistryObject<EntityType<SpinningBladeEntity>> SPINNING_BLADE_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.SPINNING_BLADE.getPath(),
            ()-> EntityType.Builder.<SpinningBladeEntity>of(SpinningBladeEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F) // FIXME! check size
                    .build(MPSRegistryNames.SPINNING_BLADE.getPath()));
}
