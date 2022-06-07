package com.lehjr.numina.client.model.obj;

import com.lehjr.numina.client.model.helper.ModelHelper;
import com.lehjr.numina.common.math.Color;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.lehjr.numina.client.model.obj.OBJPartData.*;

public class OBJBakedPart extends BakedModelWrapper {
    public OBJBakedPart(BakedModel originalModel) {
        super(originalModel);
    }

    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull Random rand, @NotNull IModelData extraData) {
        // no extra data
        if (extraData == EmptyModelData.INSTANCE) {
            return originalModel.getQuads(state, side, rand, extraData);
        } else {
            // part is visibile
            boolean visible = extraData.hasProperty(VISIBLE) ? extraData.getData(VISIBLE) : true;
            if (visible) {
                // glow is opposite ambient occlusion
                boolean glow = (extraData.hasProperty(GLOW) ? extraData.getData(GLOW) : false);
                // color applied to all quads in the part
                Color colour = extraData.hasProperty(COLOR) ? new Color(extraData.getData(COLOR)) : Color.WHITE;
                return ModelHelper.getColoredQuadsWithGlow(originalModel.getQuads(state, side, rand, extraData), colour, glow);
            }
        }
        return new ArrayList<>();
    }
}