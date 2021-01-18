package forge;

import com.github.lehjr.numina.util.client.model.helper.ModelHelper;
import com.github.lehjr.numina.util.math.Colour;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static forge.OBJPartData.*;

public class OBJBakedPart extends BakedModelWrapper {
    public OBJBakedPart(IBakedModel originalModel) {
        super(originalModel);
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
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
                Colour colour = extraData.hasProperty(COLOUR) ? new Colour(extraData.getData(COLOUR)) : Colour.WHITE;

                return ModelHelper.getColoredQuadsWithGlow(originalModel.getQuads(state, side, rand, extraData), colour, glow);
            }
        }
        return new ArrayList<>();
    }
}