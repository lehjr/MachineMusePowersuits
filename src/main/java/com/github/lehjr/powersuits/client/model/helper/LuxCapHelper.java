package com.github.lehjr.powersuits.client.model.helper;

import com.github.lehjr.numina.util.math.Colour;
import com.google.common.collect.ImmutableMap;
import forge.OBJPartData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;

public class LuxCapHelper {
    public static IModelData getModelData(int colour) {
        ImmutableMap.Builder<String, IModelData> builder = ImmutableMap.builder();
        builder.put("lightlens", OBJPartData.makeOBJPartData(true, true, colour));
        builder.put("lightBase", OBJPartData.makeOBJPartData(false, true, Colour.WHITE.getInt()));
        return new ModelDataMap.Builder().withInitial(OBJPartData.SUBMODEL_DATA, new OBJPartData(builder.build())).build();
    }
}