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

package lehjr.numina.client.model.obj;

import lehjr.numina.client.model.helper.ModelHelper;
import lehjr.numina.common.math.Color;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class OBJBakedPart extends BakedModelWrapper {
    public OBJBakedPart(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        return super.getQuads(state, side, rand);
    }

    @Override
    public @Nonnull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull RandomSource rand, @Nonnull ModelData extraData, @Nullable RenderType renderType) {
        // no extra data
        if (extraData == ModelData.EMPTY) {
            return originalModel.getQuads(state, side, rand, extraData, renderType);
        } else {
            // part is visibile
            boolean visible = extraData.has(OBJPartData.VISIBLE) ? extraData.get(OBJPartData.VISIBLE) : true;
            if (visible) {
                // glow is opposite ambient occlusion
                boolean glow = (extraData.has(OBJPartData.GLOW) ? extraData.get(OBJPartData.GLOW) : false);
                // color applied to all quads in the part
                Color color = extraData.has(OBJPartData.COLOR) ? new Color(extraData.get(OBJPartData.COLOR)) : Color.WHITE;

//                NuminaLogger.logError("color in baked part: " + color);

                return ModelHelper.getColoredQuadsWithGlow(originalModel.getQuads(state, side, rand, extraData, renderType), color, glow);
            }
        }
        return new ArrayList<>();
    }
}