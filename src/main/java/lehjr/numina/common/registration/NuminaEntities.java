package lehjr.numina.common.registration;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.entity.NuminaArmorStand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NuminaEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NuminaConstants.MOD_ID);

    public static final RegistryObject<EntityType<NuminaArmorStand>> ARMOR_STAND__ENTITY_TYPE = ENTITY_TYPES.register(NuminaConstants.ARMOR_STAND__ENTITY_TYPE_REGNAME,
            () -> EntityType.Builder.<NuminaArmorStand>of(NuminaArmorStand::new, MobCategory.CREATURE)
                    .sized(0.5F, 1.975F) // Hitbox Size
                    .build(new ResourceLocation(NuminaConstants.MOD_ID, NuminaConstants.ARMOR_STAND__ENTITY_TYPE_REGNAME).toString()));

    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(NuminaEntities.ARMOR_STAND__ENTITY_TYPE.get(), NuminaArmorStand.createAttributes().build());
    }
}
