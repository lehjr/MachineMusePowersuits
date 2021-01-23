package com.github.lehjr.powersuits.tile_entity;


import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.tileentity.MuseTileEntity;
import com.github.lehjr.powersuits.basemod.MPSObjects;

public class LuxCapacitorTileEntity extends MuseTileEntity {
    private Colour color = Colour.CYAN;

    public LuxCapacitorTileEntity() {
        super(MPSObjects.LUX_CAP_TILE_TYPE.get());
        this.color = /*LuxCapacitorBlock.defaultColor*/ Colour.CYAN;
    }

    public LuxCapacitorTileEntity(Colour colour) {
        super(MPSObjects.LUX_CAP_TILE_TYPE.get());
        this.color = colour;
    }

    public void setColor(Colour colour) {
        this.color = colour;
    }

//    @Override
//    public CompoundNBT write(CompoundNBT nbt) {
//        super.write(nbt);
//        if (color == null)
//            color = LuxCapacitorBlock.defaultColor;
//        nbt.putInt("c", color.getInt());
//        return nbt;
//    }
//
//    @Nonnull
//    @Override
//    public IModelData getModelData() {
//        return LuxCapHelper.getModelData(getColor().getInt());
//    }
//
//    @Override
//    public void read(CompoundNBT nbt) {
//        super.read(nbt);
//        if (nbt.contains("c")) {
//            color = new Colour(nbt.getInt("c"));
//        } else {
//            MuseLogger.logger.debug("No NBT found! D:");
//        }
//    }
//
//    public Colour getColor() {
//        return color != null ? color : LuxCapacitorBlock.defaultColor;
//    }
}
