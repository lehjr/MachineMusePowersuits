package com.lehjr.numina.common.utils.block;

import com.lehjr.numina.common.base.NuminaLogger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.level.Level;

public class CheckBlocksFrom {
    public final BlockPos startPos;
    public final int blockLimit;
    final BlockCheckedMap blockCheckedMap = new BlockCheckedMap();

    public CheckBlocksFrom(BlockPos startPos, int blockLimit) {
        this.startPos = startPos.immutable();
        this.blockLimit = blockLimit;
    }

    public boolean checkPositionMatch(Level level, BlockPos pos) {
        // check if within radius and limit not reached
        double distanceSq = getDistance(pos);
        if(distanceSq > 400 || blockCheckedMap.getMatches().size() > blockLimit) {
            blockCheckedMap.setMatch(pos,  false);
            return false;
        }

        boolean match = pos == startPos || level.getBlockState(startPos) == level.getBlockState(pos);
        blockCheckedMap.setMatch(pos, match);
        return match;
    }


    public NonNullList<BlockPos> startCheck(Level level) {
//        NuminaLogger.logDebug("starting check");

        long timeStart = System.currentTimeMillis();

        checkPosition(level, startPos);
        int i = 0;
        while(blockCheckedMap.hasUnchecked() && blockCheckedMap.getMatches().size() < blockLimit && i < 1000) {
            BlockPos pos2 = blockCheckedMap.getFirstUnchecked();

//            NuminaLogger.logDebug("unchecked size: " + blockCheckedMap.getAllUnchecked().size());
            checkPosition(level, pos2);
            i++;

        }
//        long timeEnd = System.currentTimeMillis() - timeStart;

//        NuminaLogger.logDebug("process took " + timeEnd + " milliSeconds");


//        NuminaLogger.logDebug("i: " + i);
//        NuminaLogger.logDebug("blocks checked: " + blockCheckedMap.getMatches().size());
        return blockCheckedMap.getMatches();
    }



    public boolean checkPosition(Level level, BlockPos pos) {
//        NuminaLogger.logDebug("checking " + pos +", size: " + blockCheckedMap.getSize());

        BlockChecked checked = blockCheckedMap.getChecked(pos);

        // no need to check something already checked
        if(checked.isChecked()) {
//            NuminaLogger.logDebug("already checked");
            return checked.isMatch() == Checked.CHECKED_MATCH;
        }
//        else {
//            debugPrintChecked(pos, checked);
//        }

        boolean match = checkPositionMatch(level, pos);

        if(match) {
            for (Direction direction : Direction.values()) {
                BlockPos pos2 = pos.relative(direction);
                boolean match2 = checkPositionMatch(level, pos2);
                blockCheckedMap.setDirectionMatch(pos, direction, match2);
            }
//            NuminaLogger.logDebug("isCheckedNow?: " + blockCheckedMap.getChecked(pos).isChecked());

        }
//        NuminaLogger.logDebug("checked: " + pos + ", " + match);

        return match;
    }

    void debugPrintChecked(BlockPos pos, BlockChecked checked) {
        switch (checked.isMatch()) {
        case NOT_CHECKED -> NuminaLogger.logDebug(pos + " not checked");
        case CHECKED_MATCH -> NuminaLogger.logDebug(pos + " checked match");
        default -> NuminaLogger.logDebug(pos + " checked NO Match");
        }
        for (Direction direction : Direction.values()) {
            switch (checked.getChecked(direction)) {
            case NOT_CHECKED -> NuminaLogger.logDebug(pos + " " + direction.getName() + " not checked");
            case CHECKED_MATCH -> NuminaLogger.logDebug(pos + " " + direction.getName() + " checked match");
            default -> NuminaLogger.logDebug(pos + " " + direction.getName() + " checked NO Match");
            }
        }
    }


    double getDistance(BlockPos pos) {
        if(pos == startPos) {
            return 0;
        }

        return startPos.distSqr(pos);
    }
}
