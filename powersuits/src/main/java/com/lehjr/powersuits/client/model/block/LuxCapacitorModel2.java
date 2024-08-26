package com.lehjr.powersuits.client.model.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class LuxCapacitorModel2 extends Model {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "luxcapacitor"), "main");
    public final ModelPart baseLower;
    public final ModelPart baseUpper;
    public final ModelPart lens;

    private final ModelPart root;
    public LuxCapacitorModel2(Function<ResourceLocation, RenderType> pRenderType) {
        super(pRenderType);
        this.root = createLayer().bakeRoot();
        this.baseLower = root.getChild("baseLower");
        this.baseUpper = root.getChild("baseUpper");
        this.lens = root.getChild("lens");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition baseLower = partdefinition.addOrReplaceChild("baseLower", CubeListBuilder.create().texOffs(0, 0).addBox(-6.5F, 22.0F, -6.5F, 13.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition baseUpper = partdefinition.addOrReplaceChild("baseUpper", CubeListBuilder.create().texOffs(0, 0).addBox(-5.25F, 21.0F, -5.75F, 11.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition lens = partdefinition.addOrReplaceChild("lens", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 20.8F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));


        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        baseLower.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        baseUpper.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        lens.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
