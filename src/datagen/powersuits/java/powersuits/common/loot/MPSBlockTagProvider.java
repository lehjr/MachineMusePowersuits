package powersuits.common.loot;

import lehjr.powersuits.common.base.MPSBlocks;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MPSBlockTagProvider extends BlockTagsProvider {
    public MPSBlockTagProvider(DataGenerator dataGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, MPSConstants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(MPSBlocks.TINKER_TABLE_BLOCK.get());
    }

    @Override
    public String getName() {
        return "MPSBlockTagProvider";
    }
}