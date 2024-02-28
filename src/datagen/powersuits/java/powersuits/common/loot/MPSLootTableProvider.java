//package powersuits.common.loot;
//
//import com.mojang.datafixers.util.Pair;
//import net.minecraft.data.PackOutput;
//import net.minecraft.data.loot.LootTableProvider;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.minecraft.world.level.storage.loot.ValidationContext;
//import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//public class MPSLootTableProvider extends LootTableProvider {
//    public MPSLootTableProvider(PackOutput output, Set<ResourceLocation> requiredTables, List<SubProviderEntry> subProviders) {
//        super(output, requiredTables, subProviders);
//    }
//
//
//
////
////    @Override
////    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationcontext) {
////        map.forEach((location, lootTable) -> {
////            LootTables.validate(validationcontext, location, lootTable);});
////    }
////
////    @Override
////    public List<SubProviderEntry> getTables() {
////        return List.of(Pair.of(MPSBlockLoot::new, LootContextParamSets.BLOCK));
////    }
//}
