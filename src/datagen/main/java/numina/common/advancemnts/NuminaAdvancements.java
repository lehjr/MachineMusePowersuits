package numina.common.advancemnts;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NuminaAdvancements extends AdvancementProvider {
    public NuminaAdvancements(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper,
        List<AdvancementGenerator> subProviders) {
        super(output, registries, existingFileHelper, subProviders);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return super.run(output);
    }
}
