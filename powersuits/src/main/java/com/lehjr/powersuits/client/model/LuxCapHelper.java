/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.lehjr.powersuits.client.model;

import com.google.common.collect.ImmutableMap;
import com.lehjr.numina.client.model.obj.OBJPartData;
import com.lehjr.numina.common.math.Color;
import net.neoforged.neoforge.client.model.data.ModelData;

public class LuxCapHelper {
    static final String LIGHT_BASE_LOWER = "lightBaseLower";
    static final String LIGHT_BASE_UPPER = "lightBaseUpper";
    static final String LIGHT_LENS = "lightlens";


    public static ModelData getBlockBaseModelData() {
        ImmutableMap.Builder<String, ModelData> builder = ImmutableMap.builder();
        builder.put(LIGHT_LENS, OBJPartData.makeOBJPartData(false, false, Color.WHITE.getARGBInt()));
        builder.put(LIGHT_BASE_LOWER, OBJPartData.makeOBJPartData(false, true, Color.WHITE.getAGBRInt()));
        builder.put(LIGHT_BASE_UPPER, OBJPartData.makeOBJPartData(false, true, Color.WHITE.getARGBInt()));
        return ModelData.builder().with(OBJPartData.SUBMODEL_DATA, new OBJPartData(builder.build())).build();
    }

    public static ModelData getBlockLensModelData(int color) {
        ImmutableMap.Builder<String, ModelData> builder = ImmutableMap.builder();
        builder.put(LIGHT_LENS, OBJPartData.makeOBJPartData(true, true, color));
//        builder.put(LIGHT_LENS, OBJPartData.makeOBJPartData(true, true, Color.WHITE.getARGBInt()));
        builder.put(LIGHT_BASE_LOWER, OBJPartData.makeOBJPartData(false, false, Color.WHITE.getAGBRInt()));
        builder.put(LIGHT_BASE_UPPER, OBJPartData.makeOBJPartData(false, false, Color.WHITE.getARGBInt()));
        return ModelData.builder().with(OBJPartData.SUBMODEL_DATA, new OBJPartData(builder.build())).build();
    }

//    public static IModelData getLensModelData(int color) {
//        ImmutableMap.Builder<String, IModelData> builder = ImmutableMap.builder();
//        builder.put(LIGHT_LENS, OBJPartData.makeOBJPartData(true, true, color));
//        return new ModelDataMap.Builder().withInitial(OBJPartData.SUBMODEL_DATA, new OBJPartData(builder.build())).build();
//    }

    public static ModelData getItemModelData(int color) {
        ImmutableMap.Builder<String, ModelData> builder = ImmutableMap.builder();
        builder.put(LIGHT_LENS, OBJPartData.makeOBJPartData(true, true, color));

//        builder.put(LIGHT_LENS, OBJPartData.makeOBJPartData(true, true, Color.WHITE.getARGBInt()));
        builder.put(LIGHT_BASE_LOWER, OBJPartData.makeOBJPartData(false, true, Color.WHITE.getAGBRInt()));
        builder.put(LIGHT_BASE_UPPER, OBJPartData.makeOBJPartData(false, true, Color.WHITE.getAGBRInt()));
        return ModelData.builder().with(OBJPartData.SUBMODEL_DATA, new OBJPartData(builder.build())).build();
    }
}