package numina.common.loot;

import com.mojang.datafixers.util.Pair;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class NuminaLootTableProvider extends LootTableProvider {
    public NuminaLootTableProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    public void run(CachedOutput pOutput) {
        super.run(pOutput);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        System.out.println("getting Numina tables");
        return List.of(Pair.of(NuminaBlockLoot::new, LootContextParamSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        System.out.println("validating Numina map size: " + map.size());
        map.forEach((location, lootTable) -> {
            System.out.println("validating Numina location: " + location);
            LootTables.validate(validationtracker, location, lootTable);});
        map.forEach((location, lootTable) -> LootTables.validate(validationtracker, location, lootTable));
    }


    @Override
    public String getName() {
        return super.getName();
    }
}
