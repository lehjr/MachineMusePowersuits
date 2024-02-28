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

package lehjr.powersuits.client.model.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.TransformationHelper;

import java.util.Random;

public class TinkerTableModel extends Model {
    public static final ResourceLocation TEXTURE = new ResourceLocation(MPSConstants.MOD_ID, "textures/models/tinkertable_tx.png");
    private final ModelPart root;
    private static Random random = new Random();
    public TinkerTableModel(ModelPart root) {
        super(RenderType::itemEntityTranslucentCull);
        this.root = root;
    }

    public TinkerTableModel() {
        this(createLayer().bakeRoot());
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("foot1", CubeListBuilder.create().texOffs( 82, 13).addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 3.0F).mirror(), PartPose.offset(-7.0F, 21.0F, -2.0F));
        partdefinition.addOrReplaceChild("fatfoot1", CubeListBuilder.create().texOffs( 96, 13).addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 4.0F).mirror(), PartPose.offset(-2.0F, 21.0F, 2.0F));
        partdefinition.addOrReplaceChild("fatfoot2", CubeListBuilder.create().texOffs( 96, 13).addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 4.0F).mirror(), PartPose.offsetAndRotation(2.0F, 21.0F, -3.0F,3.141592653589793F, -8.742277643018003E-8F, 3.141592653589793F));
        partdefinition.addOrReplaceChild("cube", CubeListBuilder.create().texOffs( 96, 20).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F).mirror(), PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("screen3", CubeListBuilder.create().texOffs( 1, 1).addBox(0.0F, 0.0F, 0.0F, 11.0F, 0.0F, 14.0F).mirror(), PartPose.offset(-9.67F, 3.47F, -7.0F));
        partdefinition.addOrReplaceChild("screen2", CubeListBuilder.create().texOffs( 0, 32).addBox(0.0F, 0.0F, 0.0F, 8.0F, 0.0F, 11.0F).mirror(), PartPose.offset(2.0F, 4.966667F, -6.0F));
        partdefinition.addOrReplaceChild("screen1", CubeListBuilder.create().texOffs( 3, 20).addBox(0.0F, 0.0F, 0.0F, 14.0F, 0.0F, 7.0F).mirror(), PartPose.offset(-6.0F, 2.47F, 3.0F));
        partdefinition.addOrReplaceChild("middletable", CubeListBuilder.create().texOffs( 40, 49).addBox(-5.0F, 1.0F, -5.0F, 17.0F, 3.0F, 18.0F), PartPose.offset(-4.0F, 10.0F, -4.0F));
        partdefinition.addOrReplaceChild("uppertable", CubeListBuilder.create().texOffs( 56, 28).addBox(0.0F, 0.0F, 0.0F, 12.0F, 5.0F, 16.0F).mirror(), PartPose.offset(-8.0F, 10.0F, -8.0F));
        partdefinition.addOrReplaceChild("particles", CubeListBuilder.create().texOffs( 90, 0).addBox(0.0F, 0.0F, 0.0F, 6.0F, 7.0F, 5.0F).mirror(), PartPose.offset(-3.0F, 15.0F, -3.0F));
        partdefinition.addOrReplaceChild("footbase",  CubeListBuilder.create().texOffs( 0, 54).addBox(-5.0F, 8.0F, -5.0F, 12.0F, 2.0F, 11.0F).mirror(), PartPose.offset(-1.0F, 14.0F, -1.0F));
        partdefinition.addOrReplaceChild("backsupport", CubeListBuilder.create().texOffs( 38, 34).addBox(0.0F, 0.0F, -2.0F, 2.0F, 8.0F, 7.0F).mirror(), PartPose.offset(3.0F, 14.0F, -2.0F));
        partdefinition.addOrReplaceChild("tank3", CubeListBuilder.create().texOffs( 51, 18).addBox(0.0F, 0.0F, 0.0F, 3.0F, 5.0F, 3.0F), PartPose.offset(6.0F, 10.0F, 3.0F));
        partdefinition.addOrReplaceChild("tank2", CubeListBuilder.create().texOffs( 51, 18).addBox(0.0F, 0.0F, 0.0F, 3.0F, 5.0F, 3.0F), PartPose.offset(6.0F, 10.0F, -2.0F));
        partdefinition.addOrReplaceChild("tank1", CubeListBuilder.create().texOffs( 51, 18).addBox(0.0F, 0.0F, 0.0F, 3.0F, 5.0F, 3.0F), PartPose.offset(6.0F, 10.0F, -7.0F));
        partdefinition.addOrReplaceChild("wireshort4", CubeListBuilder.create().texOffs( 71, 15).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F).mirror(), PartPose.offset(7.0F, 15.0F, -1.0F));
        partdefinition.addOrReplaceChild("wireshort3", CubeListBuilder.create().texOffs( 71, 15).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F).mirror(), PartPose.offset(7.0F, 15.0F, -6.0F));
        partdefinition.addOrReplaceChild("wireshort2", CubeListBuilder.create().texOffs( 69, 13).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F).mirror(), PartPose.offset(5.0F, 17.0F, -1.0F));
        partdefinition.addOrReplaceChild("wireshort1",  CubeListBuilder.create().texOffs( 71, 15).addBox(0.0F, 0.0F, 5.0F, 1.0F, 2.0F, 1.0F).mirror(), PartPose.offset(7.0F, 15.0F, -1.0F));
        partdefinition.addOrReplaceChild("wirelong1", CubeListBuilder.create().texOffs( 77, 1).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 11.0F).mirror(), PartPose.offset(7.0F, 17.0F, -6.0F));

        return LayerDefinition.create(meshdefinition, 112, 70);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(TransformationHelper.quatFromXYZ(new Vector3f(180.0F, 0.0F, 0.0F), true));
        matrixStackIn.translate(0.5F, -1.5F, -0.5F);
        root.children.keySet().stream().filter(name -> !name.equals("cube")).forEach(name ->
                root.children.get(name).render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        int timestep = (int) ((System.currentTimeMillis()) % 10000);
        double angle = timestep * Math.PI / 5000.0;
        matrixStackIn.translate(0.5f, 1.05f, 0.5f);
        matrixStackIn.translate(0, 0.02f * Math.sin(angle * 3), 0);
        matrixStackIn.mulPose(Axis.YP.rotationDegrees((float) (angle * 57.2957795131)));
        matrixStackIn.mulPose(Axis.XP.rotationDegrees(45f));
        // arctangent of 0.5.
//        matrixStackIn.mulPose(new Vector3f(0,1,1).rotationDegrees(35.2643897f));// <- FIXME!!!

//        new Quaternionf().rotationZ(35.2643897f)


        matrixStackIn.scale(0.5f, 0.5f, 0.5f);
        root.children.get("cube").render(matrixStackIn, bufferIn, LightTexture.FULL_BRIGHT, packedOverlayIn, 1, 1, 1, 0.8F);
        matrixStackIn.popPose();
    }


    // FIXME!! remove when done figurign out
    @FunctionalInterface
    public interface Axis1 {
        Axis1 XN = (xrot) -> (new Quaternionf()).rotationX(-xrot);
        Axis1 XP = (xrot) -> (new Quaternionf()).rotationX(xrot);
        Axis1 YN = (yrot) -> (new Quaternionf()).rotationY(-yrot);
        Axis1 YP = (yrot) -> (new Quaternionf()).rotationY(yrot);
        Axis1 ZN = (zrot) -> (new Quaternionf()).rotationZ(-zrot);
        Axis1 ZP = (zrot) -> (new Quaternionf()).rotationZ(zrot);

        static Axis1 of(Vector3f pAxis) {
            return (rot) -> (new Quaternionf()).rotationAxis(rot, pAxis);
        }

        Quaternionf rotation(float pRadians);

        default Quaternionf rotationDegrees(float pDegrees) {
            return this.rotation(pDegrees * ((float)Math.PI / 180F));
        }
    }
}