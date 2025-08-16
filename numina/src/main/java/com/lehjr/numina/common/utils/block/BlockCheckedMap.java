package com.lehjr.numina.common.utils.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class BlockCheckedMap {
    private final Map<BlockPos, BlockChecked> checkedMap = new HashMap<>();
    private final NonNullList<BlockPos> outOfBoundsPositions = NonNullList.create();

    public BlockCheckedMap() {
    }

    public boolean isChecked(BlockPos pos) {
        return getChecked(pos).isChecked();
    }

    public void setMatch(BlockPos pos, boolean match) {
        BlockChecked blockChecked = checkedMap.getOrDefault(pos.immutable(), new BlockChecked());
        blockChecked.setMatch(match);
        checkedMap.put(pos.immutable(), blockChecked);
    }

    public BlockChecked setDirectionMatch(BlockPos pos, Direction direction, boolean match) {
        BlockChecked blockChecked = checkedMap.getOrDefault(pos.immutable(), new BlockChecked());
        blockChecked.setChecked(direction, match);
        checkedMap.put(pos.immutable(), blockChecked);

        BlockPos pos2 = pos.relative(direction);
        setMatch(pos2, match);

        return blockChecked;
    }

    public NonNullList<BlockPos> getMatches() {
        NonNullList<BlockPos> retMatches = NonNullList.create();
        checkedMap.forEach((blockPos, checked) -> {
            if(checked.isMatch == Checked.CHECKED_MATCH) {
                retMatches.add(blockPos.immutable());
            }
        });
        return retMatches;
    }

    public int getSize() {
        return checkedMap.size();
    }

    public boolean hasUnchecked() {
        return getFirstUnchecked() != null;
    }

    public NonNullList<BlockPos> getAllUnchecked() {
        NonNullList<BlockPos> retUnchecked = NonNullList.create();
        checkedMap.forEach((blockPos, checked) -> {
            if(!checked.isChecked()) {
                retUnchecked.add(blockPos.immutable());
            }
        });
        return retUnchecked;
    }


    @Nullable
    public BlockPos getFirstUnchecked() {
        for (Map.Entry<BlockPos, BlockChecked> entry : checkedMap.entrySet()) {
            if(!entry.getValue().isChecked()) {
                return entry.getKey();
            }
        }
        return null;
    }

    public BlockChecked getChecked(BlockPos pos) {
        BlockChecked blockChecked;

        if(checkedMap.containsKey(pos)) {
            blockChecked = checkedMap.get(pos.immutable());
        } else {
            blockChecked = new BlockChecked();
            checkedMap.put(pos.immutable(), blockChecked);
        }
        return blockChecked;
    }
}
