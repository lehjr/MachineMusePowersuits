package powersuits.common.loot;

import lehjr.powersuits.common.base.MPSBlocks;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MPSBlockTagProvider extends BlockTagsProvider {
    public MPSBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MPSConstants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(MPSBlocks.TINKER_TABLE_BLOCK.get());
    }

    @Override
    public String getName() {
        return "MPSBlockTagProvider";
    }
}