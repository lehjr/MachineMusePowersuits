package com.lehjr.numina.common.utils.block;

import net.minecraft.core.Direction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BlockChecked {
    Map<Direction, Checked> checkedMap = new HashMap<>();
    Checked isMatch = Checked.NOT_CHECKED;

    public BlockChecked() {
    }

    public BlockChecked(Direction direction) {
        setChecked(direction.getOpposite(), Checked.CHECKED_MATCH);
    }

    public void setChecked(Direction direction, Checked checked) {
        checkedMap.put(direction, checked);
    }

    public void setChecked(Direction direction, boolean isMatch) {
        Checked checked = isMatch ? Checked.CHECKED_MATCH : Checked.CHECKED_NO_MATCH;
        checkedMap.put(direction, checked);
    }

    public void setMatch(boolean isMatch) {
        this.isMatch = isMatch ? Checked.CHECKED_MATCH : Checked.CHECKED_NO_MATCH;
    }

    public Checked isMatch() {
        return isMatch;
    }

    public boolean isChecked() {
        if(isMatch.equals(Checked.NOT_CHECKED)) {
            return false;
        }
        // if not a match don't go any further
        if(isMatch.equals(Checked.CHECKED_NO_MATCH)) {
            return true;
        }
        return Arrays.stream(Direction.values()).noneMatch(direction -> getChecked(direction).equals(Checked.NOT_CHECKED));
    }

    public Checked getChecked(Direction direction) {
        return checkedMap.getOrDefault(direction, Checked.NOT_CHECKED);
    }
}
