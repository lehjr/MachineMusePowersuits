package lehjr.powersuits.client.model.helper;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ModelSpecLoader implements /*IGeometryLoader<NuminaObjModel>, */ResourceManagerReloadListener {
public static ModelSpecLoader INSTANCE = new ModelSpecLoader();


    @Override
    public CompletableFuture<Void> reload(PreparationBarrier pStage, ResourceManager pResourceManager, ProfilerFiller pPreparationsProfiler, ProfilerFiller pReloadProfiler, Executor pBackgroundExecutor, Executor pGameExecutor) {
        return ResourceManagerReloadListener.super.reload(pStage, pResourceManager, pPreparationsProfiler, pReloadProfiler, pBackgroundExecutor, pGameExecutor);
    }

    @Override
    public String getName() {
        return ResourceManagerReloadListener.super.getName();
    }

    @Override
    public void onResourceManagerReload(ResourceManager pResourceManager) {

    }
}