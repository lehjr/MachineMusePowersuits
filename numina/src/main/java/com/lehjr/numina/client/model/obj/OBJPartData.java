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

package com.lehjr.numina.client.model.obj;

import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;

import java.util.Map;

public class OBJPartData {
    public static final ModelProperty<OBJPartData> SUBMODEL_DATA = new ModelProperty<>();
    public static final ModelProperty<Integer> COLOR = new ModelProperty<>();
    public static final ModelProperty<Boolean> VISIBLE = new ModelProperty<>();
    public static final ModelProperty<Boolean> GLOW = new ModelProperty<>();
    private final Map<String, ModelData> parts;

    public OBJPartData(Map<String, ModelData> parts) {
        this.parts = parts;
    }

    public ModelData getSubmodelData(String name) {
        return parts.getOrDefault(name, ModelData.EMPTY);
    }

    public void putSubmodelData(String name, ModelData data) {
        parts.put(name, data);
    }

    public static ModelData makeOBJPartData(boolean glow, boolean visible, int color) {
        return ModelData.builder()
                .with(GLOW, glow)
                .with(COLOR, color)
                .with(VISIBLE, visible)
                .build();
    }

    public static ModelData getOBJPartData(ModelData extraData, String name) {
        OBJPartData data = extraData.get(SUBMODEL_DATA);
        if (data == null) {
            return ModelData.EMPTY;
        }
        return data.getSubmodelData(name);
    }
}
