package lehjr.numina.common.utils.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;

import java.util.Objects;

public class CheckOreBlocksFrom extends CheckBlocksFrom {
    static final TagKey<Block> ORE_TAG = Tags.Blocks.ORES;
    static final String ORE_TAG_PATH_PREFIX = ORE_TAG.location().getPath().concat("/");
    static final String ORE_TAG_DOMAIN = ORE_TAG.location().getNamespace();
    NonNullList<TagKey<Block>> oreKeys = NonNullList.create();


    public CheckOreBlocksFrom(BlockPos startPos, int blockLimit) {
        super(startPos, blockLimit);
    }

    @Override
    public NonNullList<BlockPos> startCheck(Level level) {
        oreKeys = getOreTagList(startPos, level);
        return super.startCheck(level);
    }

    NonNullList<TagKey<Block>> getOreTagList(BlockPos pos, Level level){
        NonNullList<TagKey<Block>> retList = NonNullList.create();
        level.getBlockState(pos).getTags().forEach(blockTagKey -> {
            if(Objects.equals(ORE_TAG_DOMAIN, blockTagKey.location().getNamespace()) && blockTagKey.location().getPath().startsWith(ORE_TAG_PATH_PREFIX)) {
                retList.add(blockTagKey);
            }
        });
        return retList;
    }

    @Override
    boolean isMatch(BlockPos pos, Level level) {
        BlockState state2 = level.getBlockState(pos);
        if(state2.isAir()) {
            return false;
        }

        if(super.isMatch(pos, level)) {
            return true;
        }

        for(TagKey<Block> key : oreKeys) {
            if(state2.is(key)) {
                return true;
            }
        }
        return false;
    }
}
