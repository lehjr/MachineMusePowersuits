package lehjr.powersuits.client.model.item;

// Made with Blockbench 4.6.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ToolModel2<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "toolmodel"), "main");
    private final ModelPart mainarm;
    private final ModelPart armorright;
    private final ModelPart armorleft;
    private final ModelPart wristtopright;
    private final ModelPart wristtopleft;
    private final ModelPart wristbottomright;
    private final ModelPart wristbottomleft;
    private final ModelPart index1;
    private final ModelPart middlefinger1;
    private final ModelPart ringfinger1;
    private final ModelPart pinky1;
    private final ModelPart thumb1;
    private final ModelPart fingerguard;
    private final ModelPart crystalholder;
    private final ModelPart crystal;
    private final ModelPart supportright1;
    private final ModelPart supportright2;
    private final ModelPart supportright3;
    private final ModelPart supportright4;
    private final ModelPart supportright5;
    private final ModelPart supportbaseright;
    private final ModelPart palm;
    private final ModelPart supportbaseleft;
    private final ModelPart supportleftfront;
    private final ModelPart supportrightfront;
    private final ModelPart supportleft1;
    private final ModelPart supportleft2;
    private final ModelPart supportleft3;
    private final ModelPart supportleft4;
    private final ModelPart supportleft5;

    public ToolModel2(ModelPart root) {
        this.mainarm = root.getChild("mainarm");
        this.armorright = root.getChild("armorright");
        this.armorleft = root.getChild("armorleft");
        this.wristtopright = root.getChild("wristtopright");
        this.wristtopleft = root.getChild("wristtopleft");
        this.wristbottomright = root.getChild("wristbottomright");
        this.wristbottomleft = root.getChild("wristbottomleft");
        this.index1 = root.getChild("index1");
        this.middlefinger1 = root.getChild("middlefinger1");
        this.ringfinger1 = root.getChild("ringfinger1");
        this.pinky1 = root.getChild("pinky1");
        this.thumb1 = root.getChild("thumb1");
        this.fingerguard = root.getChild("fingerguard");
        this.crystalholder = root.getChild("crystalholder");
        this.crystal = root.getChild("crystal");
        this.supportright1 = root.getChild("supportright1");
        this.supportright2 = root.getChild("supportright2");
        this.supportright3 = root.getChild("supportright3");
        this.supportright4 = root.getChild("supportright4");
        this.supportright5 = root.getChild("supportright5");
        this.supportbaseright = root.getChild("supportbaseright");
        this.palm = root.getChild("palm");
        this.supportbaseleft = root.getChild("supportbaseleft");
        this.supportleftfront = root.getChild("supportleftfront");
        this.supportrightfront = root.getChild("supportrightfront");
        this.supportleft1 = root.getChild("supportleft1");
        this.supportleft2 = root.getChild("supportleft2");
        this.supportleft3 = root.getChild("supportleft3");
        this.supportleft4 = root.getChild("supportleft4");
        this.supportleft5 = root.getChild("supportleft5");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition mainarm = partdefinition.addOrReplaceChild("mainarm",
                CubeListBuilder.create().texOffs(0, 16)
                        .addBox(-3.0F, 0.0F, -8.0F, 6.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition armorright = partdefinition.addOrReplaceChild("armorright",
                CubeListBuilder.create().texOffs(42, 0).mirror().addBox(-4.0F, -1.0F, -9.0F, 3.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition armorleft = partdefinition.addOrReplaceChild("armorleft",
                CubeListBuilder.create().texOffs(42, 0).addBox(1.0F, -1.0F, -9.0F, 3.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition wristtopright = partdefinition.addOrReplaceChild("wristtopright",
                CubeListBuilder.create().texOffs(0, 11).addBox(-2.0F, 1.0F, 2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition wristtopleft = partdefinition.addOrReplaceChild("wristtopleft",
                CubeListBuilder.create().texOffs(0, 11).addBox(1.0F, 1.0F, 2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition wristbottomright = partdefinition.addOrReplaceChild("wristbottomright",
                CubeListBuilder.create().texOffs(0, 11).addBox(-2.0F, 3.0F, 2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition wristbottomleft = partdefinition.addOrReplaceChild("wristbottomleft",
                CubeListBuilder.create().texOffs(0, 11).addBox(1.0F, 3.0F, 2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition index1 = partdefinition.addOrReplaceChild("index1",
                CubeListBuilder.create(), PartPose.offsetAndRotation(-3.5F, -1.5F, 10.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition index2 = index1.addOrReplaceChild("index2",
                CubeListBuilder.create(), PartPose.offset(-3.5F, -1.5F, 10.0F));

        PartDefinition middlefinger1 = partdefinition.addOrReplaceChild("middlefinger1",
                CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, -1.5F, 10.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition middlefinger2 = middlefinger1.addOrReplaceChild("middlefinger2",
                CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3444F, 0.0F, 0.0F));

        PartDefinition ringfinger1 = partdefinition.addOrReplaceChild("ringfinger1",
                CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, -1.5F, 10.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition ringfinger2 = ringfinger1.addOrReplaceChild("ringfinger2",
                CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition pinky1 = partdefinition.addOrReplaceChild("pinky1",
                CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, -1.5F, 10.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition pinky2 = pinky1.addOrReplaceChild("pinky2",
                CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4538F, 0.0F, 0.0F));

        PartDefinition thumb1 = partdefinition.addOrReplaceChild("thumb1",
                CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0F, 1.5F, 8.0F, 0.0F, -0.4014F, 0.0F));

        PartDefinition thumb2 = thumb1.addOrReplaceChild("thumb2",
                CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fingerguard = partdefinition.addOrReplaceChild("fingerguard",
                CubeListBuilder.create().texOffs(28, 9).addBox(-2.0F, -2.0F, 8.1F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition crystalholder = partdefinition.addOrReplaceChild("crystalholder",
                CubeListBuilder.create().texOffs(48, 13).addBox(-2.0F, -1.0F, -3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition crystal = partdefinition.addOrReplaceChild("crystal",
                CubeListBuilder.create().texOffs(32, 27).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition supportright1 = partdefinition.addOrReplaceChild("supportright1",
                CubeListBuilder.create().texOffs(54, 27).addBox(-2.2F, -0.8F, -6.0667F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2723F, -1.067F, 0.0F));

        PartDefinition supportright2 = partdefinition.addOrReplaceChild("supportright2",
                CubeListBuilder.create().texOffs(52, 21).addBox(-6.0F, 0.4667F, 2.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.633F, 0.0F));

        PartDefinition supportright3 = partdefinition.addOrReplaceChild("supportright3",
                CubeListBuilder.create().texOffs(52, 21).addBox(-6.1F, 1.0F, -0.8333F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition supportright4 = partdefinition.addOrReplaceChild("supportright4",
                CubeListBuilder.create().texOffs(52, 21).addBox(-7.6333F, 0.4667F, 1.7F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3688F, 0.0F));

        PartDefinition supportright5 = partdefinition.addOrReplaceChild("supportright5",
                CubeListBuilder.create().texOffs(54, 27).addBox(-1.1333F, 1.0F, 6.3333F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7714F, 0.0F));

        PartDefinition supportbaseright = partdefinition.addOrReplaceChild("supportbaseright",
                CubeListBuilder.create().texOffs(47, 21).addBox(-4.4333F, -0.6667F, -5.4F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition palm = partdefinition.addOrReplaceChild("palm",
                CubeListBuilder.create().texOffs(18, 0).addBox(-3.0F, -1.0F, 5.0F, 7.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition supportbaseleft = partdefinition.addOrReplaceChild("supportbaseleft",
                CubeListBuilder.create().texOffs(47, 21).addBox(1.4F, -0.6667F, -5.4F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition supportleftfront = partdefinition.addOrReplaceChild("supportleftfront",
                CubeListBuilder.create().texOffs(49, 23).addBox(3.3333F, 0.3333F, 4.6667F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition supportrightfront = partdefinition.addOrReplaceChild("supportrightfront",
                CubeListBuilder.create().texOffs(49, 23).addBox(-3.3F, 0.3333F, 4.6667F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition supportleft1 = partdefinition.addOrReplaceChild("supportleft1",
                CubeListBuilder.create().texOffs(54, 27).addBox(-1.8F, -0.4F, -6.0667F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2723F, 1.067F, 0.0F));

        PartDefinition supportleft2 = partdefinition.addOrReplaceChild("supportleft2",
                CubeListBuilder.create().texOffs(52, 21).addBox(4.0F, 0.4667F, 2.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.633F, 0.0F));

        PartDefinition supportleft3 = partdefinition.addOrReplaceChild("supportleft3",
                CubeListBuilder.create().texOffs(52, 21).addBox(5.5F, 1.0F, -0.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition supportleft4 = partdefinition.addOrReplaceChild("supportleft4",
                CubeListBuilder.create().texOffs(52, 21)
                        .addBox(5.9F, 0.4667F, 1.7F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3688F, 0.0F));

        PartDefinition supportleft5 = partdefinition.addOrReplaceChild("supportleft5",
                CubeListBuilder.create().texOffs(54, 27)
                        .addBox(-3.1333F, 1.0F, 7.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7714F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        mainarm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        armorright.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        armorleft.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        wristtopright.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        wristtopleft.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        wristbottomright.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        wristbottomleft.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        index1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        middlefinger1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        ringfinger1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        pinky1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        thumb1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        fingerguard.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        crystalholder.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        crystal.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        supportright1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        supportright2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        supportright3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        supportright4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        supportright5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        supportbaseright.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        palm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        supportbaseleft.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        supportleftfront.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        supportrightfront.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        supportleft1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        supportleft2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        supportleft3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        supportleft4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        supportleft5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}