package com.github.lehjr.powersuits.tile_entity;

import com.github.lehjr.numina.util.tileentity.MuseTileEntity;
import com.github.lehjr.powersuits.basemod.MPSObjects;
import net.minecraft.util.Direction;

/**
 * @author MachineMuse
 * <p>
 * Ported to Java by lehjr on 10/21/16.
 */
public class WorkBenchTileEntity extends MuseTileEntity {
    Direction facing;

    public WorkBenchTileEntity() {
        super(MPSObjects.WORKBENCH_TILE_TYPE.get());
        this.facing = Direction.NORTH;
    }

    public WorkBenchTileEntity(Direction facing) {
        super(MPSObjects.WORKBENCH_TILE_TYPE.get());
        this.facing = facing;
    }

    public Direction getFacing() {
        return (this.facing != null) ? this.facing : Direction.NORTH;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }





//    @Override
//    public CompoundNBT write(CompoundNBT nbt) {
//        super.write(nbt);
//        nbt.putInt("f", facing.ordinal());
//        return nbt;
//    }
//
//    @Override
//    public void read(CompoundNBT nbt) {
//        super.read(nbt);
//        if (nbt.contains("f")) {
//            facing = Direction.values()[nbt.getInt("f")];
//        } else {
//            MuseLogger.logger.debug("No NBT found! D:");
//        }
//    }
}