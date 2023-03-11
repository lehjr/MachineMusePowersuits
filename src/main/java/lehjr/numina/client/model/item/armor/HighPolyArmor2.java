package lehjr.numina.client.model.item.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;

public class HighPolyArmor2 <T extends LivingEntity> extends HumanoidModel<T> {
    public HighPolyArmor2(ModelPart pRoot) {
        super(pRoot);
    }


    public static MeshDefinition createMesh(CubeDeformation pCubeDeformation, float p_170683_) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        //addOrReplaceChild(head, "head", 0.0F, 24.0F, 0.0F);
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, pCubeDeformation),
                PartPose.offset(0.0F, 0.0F + p_170683_, 0.0F));

        partdefinition.addOrReplaceChild("hat",
                CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, pCubeDeformation.extend(0.5F)), PartPose.offset(0.0F, 0.0F + p_170683_, 0.0F));

//        addOrReplaceChild(body, "body", 0.0F, 24.0F, 0.0F);
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, pCubeDeformation), PartPose.offset(0.0F, 0.0F + p_170683_, 0.0F));

//        addOrReplaceChild(rightArm, "right_arm", 5.0F, 24.0F, 0.0F);
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, pCubeDeformation), PartPose.offset(-5.0F, 2.0F + p_170683_, 0.0F));

//        addOrReplaceChild(leftArm, "left_arm",-5.0F, 24.0F, 0.0F);
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, pCubeDeformation), PartPose.offset(5.0F, 2.0F + p_170683_, 0.0F));

//        addOrReplaceChild(rightLeg, "right_leg",1.9F, 12.0F, 0.0F);
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, pCubeDeformation), PartPose.offset(-1.9F, 12.0F + p_170683_, 0.0F));

//        addOrReplaceChild(leftLeg, "left_leg", -1.9F, 12.0F, 0.0F);
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, pCubeDeformation), PartPose.offset(1.9F, 12.0F + p_170683_, 0.0F));
        return meshdefinition;
    }







}
