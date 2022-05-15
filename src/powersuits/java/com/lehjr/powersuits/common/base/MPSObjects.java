package com.lehjr.powersuits.common.base;

import com.lehjr.powersuits.common.block.LuxCapacitor;
import com.lehjr.powersuits.common.block.TinkerTable;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.constants.MPSRegistryNames;
import com.lehjr.powersuits.common.entity.SpinningBladeEntity;
import com.lehjr.powersuits.common.item.block.TinkerTableItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MPSObjects {
    public static final MPSCreativeTab creativeTab = new MPSCreativeTab();

    /**
     * Blocks ------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MPSConstants.MOD_ID);

    public static final RegistryObject<TinkerTable> TINKER_TABLE_BLOCK = BLOCKS.register(MPSRegistryNames.TINKER_TABLE.getPath(), TinkerTable::new);

    public static final RegistryObject<LuxCapacitor> LUX_CAPACITOR_BLOCK = BLOCKS.register(MPSRegistryNames.LUX_CAPACITOR.getPath(), LuxCapacitor::new);

    /**
     * Entity Types ------------------------------------------------------------------------------
     */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MPSConstants.MOD_ID);

//    private static final RegistryObject<EntityType<TestEntity>> TEST_ENTITY = ENTITIES.register("test_entity", () -> EntityType.Builder.of(TestEntity::new, MobCategory.CREATURE).sized(16.0F, 8.0F).clientTrackingRange(10).build("test_entity"));
//
//

    public static final RegistryObject<EntityType<SpinningBladeEntity>> SPINNING_BLADE_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.SPINNING_BLADE.getPath(),
            ()-> EntityType.Builder.<SpinningBladeEntity>of(SpinningBladeEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F) // FIXME! check size
                    .build(MPSRegistryNames.SPINNING_BLADE.getPath()));










    /**
     * Items -------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MPSConstants.MOD_ID);

    /* BlockItems --------------------------------------------------------------------------------- */
    // use directly as a module
    public static final RegistryObject<Item> TINKER_TABLE_ITEM = ITEMS.register(MPSRegistryNames.TINKER_TABLE.getPath(),
            () -> new TinkerTableItem(TINKER_TABLE_BLOCK.get()));

//    public static final RegistryObject<Item> LUX_CAPACITOR_ITEM = ITEMS.register(MPSRegistryNames.LUX_CAPACITOR,
//            () -> new BlockItem(LUX_CAPACITOR_BLOCK.get(), new Item.Properties()
//                    .tab(MPSObjects.creativeTab)
//                    .stacksTo(64)
//                    .defaultDurability(-1)
//                    .setNoRepair()));



}
