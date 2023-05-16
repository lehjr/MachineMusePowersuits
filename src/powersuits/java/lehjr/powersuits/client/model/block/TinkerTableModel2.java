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

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.model.TransformationHelper;

import java.util.function.Function;

public class TinkerTableModel2 extends Model {
    ModelRenderer cube;
    ModelRenderer screen3;
    ModelRenderer screen2;
    ModelRenderer screen1;
    ModelRenderer middletable;
    ModelRenderer uppertable;
    ModelRenderer particles;
    ModelRenderer footbase;
    ModelRenderer foot1;
    ModelRenderer fatfoot2;
    ModelRenderer fatfoot1;
    ModelRenderer backsupport;
    ModelRenderer tank3;
    ModelRenderer tank2;
    ModelRenderer tank1;
    ModelRenderer wireshort4;
    ModelRenderer wireshort3;
    ModelRenderer wireshort2;
    ModelRenderer wireshort1;
    ModelRenderer wirelong1;

    public TinkerTableModel2() {
        this(RenderType::itemEntityTranslucentCull); // this is the only one that renders correctly
    }

    public TinkerTableModel2(Function<ResourceLocation, RenderType> p_i225945_1_) {
        super(p_i225945_1_);
        texWidth = 112;
        texHeight = 70;

        this.cube = new ModelRenderer(this, 96, 20);
        this.cube.mirror = true;
        this.cube.setPos(0.0F, 0.0F, 0.0F);
        this.cube.addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);


        this.screen3 = new ModelRenderer(this, 1, 1);
        this.screen3.mirror = true;
        this.screen3.setPos(-9.67F, 3.47F, -7.0F);
        this.screen3.addBox(0.0F, 0.0F, 0.0F, 11.0F, 0.0F, 14.0F, 0.0F, 0.0F, 0.0F);


        this.screen2 = new ModelRenderer(this, 0, 32);
        this.screen2.mirror = true;
        this.screen2.setPos(2.0F, 4.966667F, -6.0F);
        this.screen2.addBox(0.0F, 0.0F, 0.0F, 8.0F, 0.0F, 11.0F, 0.0F, 0.0F, 0.0F);


        this.screen1 = new ModelRenderer(this, 3, 20);
        this.screen1.mirror = true;
        this.screen1.setPos(-6.0F, 2.47F, 3.0F);
        this.screen1.addBox(0.0F, 0.0F, 0.0F, 14.0F, 0.0F, 7.0F, 0.0F, 0.0F, 0.0F);


        this.middletable = new ModelRenderer(this, 40, 49);
        this.middletable.setPos(-4.0F, 10.0F, -4.0F);
        this.middletable.addBox(-5.0F, 1.0F, -5.0F, 17.0F, 3.0F, 18.0F, 0.0F, 0.0F, 0.0F);


        this.uppertable = new ModelRenderer(this, 56, 28);
        this.uppertable.mirror = true;
        this.uppertable.setPos(-8.0F, 10.0F, -8.0F);
        this.uppertable.addBox(0.0F, 0.0F, 0.0F, 12.0F, 5.0F, 16.0F, 0.0F, 0.0F, 0.0F);


        this.particles = new ModelRenderer(this, 90, 0);
        this.particles.mirror = true;
        this.particles.setPos(-3.0F, 15.0F, -3.0F);
        this.particles.addBox(0.0F, 0.0F, 0.0F, 6.0F, 7.0F, 5.0F, 0.0F, 0.0F, 0.0F);


        this.footbase = new ModelRenderer(this, 0, 54);
        this.footbase.mirror = true;
        this.footbase.setPos(-1.0F, 14.0F, -1.0F);
        this.footbase.addBox(-5.0F, 8.0F, -5.0F, 12.0F, 2.0F, 11.0F, 0.0F, 0.0F, 0.0F);


        this.foot1 = new ModelRenderer(this, 82, 13);
        this.foot1.mirror = true;
        this.foot1.setPos(-7.0F, 21.0F, -2.0F);
        this.foot1.addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);


        this.fatfoot2 = new ModelRenderer(this, 96, 13);
        this.fatfoot2.mirror = true;
        this.fatfoot2.setPos(2.0F, 21.0F, -3.0F);
        this.fatfoot2.addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(fatfoot2, 3.141592653589793F, -8.742277643018003E-8F, 3.141592653589793F);


        this.fatfoot1 = new ModelRenderer(this, 96, 13);
        this.fatfoot1.mirror = true;
        this.fatfoot1.setPos(-2.0F, 21.0F, 2.0F);
        this.fatfoot1.addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);


        this.backsupport = new ModelRenderer(this, 38, 34);
        this.backsupport.mirror = true;
        this.backsupport.setPos(3.0F, 14.0F, -2.0F);
        this.backsupport.addBox(0.0F, 0.0F, -2.0F, 2.0F, 8.0F, 7.0F, 0.0F, 0.0F, 0.0F);


        this.tank3 = new ModelRenderer(this, 51, 18);
        this.tank3.setPos(6.0F, 10.0F, 3.0F);
        this.tank3.addBox(0.0F, 0.0F, 0.0F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);


        this.tank2 = new ModelRenderer(this, 51, 18);
        this.tank2.setPos(6.0F, 10.0F, -2.0F);
        this.tank2.addBox(0.0F, 0.0F, 0.0F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);


        this.tank1 = new ModelRenderer(this, 51, 18);
        this.tank1.setPos(6.0F, 10.0F, -7.0F);
        this.tank1.addBox(0.0F, 0.0F, 0.0F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);


        this.wireshort4 = new ModelRenderer(this, 71, 15);
        this.wireshort4.mirror = true;
        this.wireshort4.setPos(7.0F, 15.0F, -1.0F);
        this.wireshort4.addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);


        this.wireshort3 = new ModelRenderer(this, 71, 15);
        this.wireshort3.mirror = true;
        this.wireshort3.setPos(7.0F, 15.0F, -6.0F);
        this.wireshort3.addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);


        this.wireshort2 = new ModelRenderer(this, 69, 13);
        this.wireshort2.mirror = true;
        this.wireshort2.setPos(5.0F, 17.0F, -1.0F);
        this.wireshort2.addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);


        this.wireshort1 = new ModelRenderer(this, 71, 15);
        this.wireshort1.mirror = true;
        this.wireshort1.setPos(7.0F, 15.0F, -1.0F);
        this.wireshort1.addBox(0.0F, 0.0F, 5.0F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);


        this.wirelong1 = new ModelRenderer(this, 77, 1);
        this.wirelong1.mirror = true;
        this.wirelong1.setPos(7.0F, 17.0F, -6.0F);
        this.wirelong1.addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 11.0F, 0.0F, 0.0F, 0.0F);
    }


    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.popPose();
        matrixStackIn.mulPose(TransformationHelper.quatFromXYZ(new Vector3f(180, 0, 0), true));
        matrixStackIn.translate(0.5F, -1.5F, -0.5F);
        ImmutableList.of(this.fatfoot2, this.tank2, this.wireshort4, this.wireshort3, this.screen2, /*this.cube, */this.screen3, this.screen1, this.wireshort2, this.wireshort1, this.tank3, this.uppertable, this.middletable, this.fatfoot1, this.particles, this.wirelong1, this.foot1, this.tank1, this.backsupport, this.footbase).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });

        matrixStackIn.popPose();
        int timestep = (int) ((System.currentTimeMillis()) % 10000);
        float radians = (float) (timestep * Math.PI / 5000.0F);
        matrixStackIn.pushPose();

        matrixStackIn.translate(0.5f, 1.05f, 0.5f);
        matrixStackIn.translate(0, 0.02f * Math.sin(radians * 3), 0);
        matrixStackIn.mulPose(Vector3f.YP.rotation((radians)));
        matrixStackIn.mulPose(Vector3f.XP.rotation(45F));

        // arctangent of 0.5.
        matrixStackIn.mulPose(new Vector3f(0,1,1).rotation(35.2643897f));

//        matrixStackIn.push();
        matrixStackIn.scale(0.25F, 0.25F, 0.25F);
        cube.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, 1, 1, 1, 0.8F);
//        matrixStackIn.pop();
        matrixStackIn.popPose();
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
