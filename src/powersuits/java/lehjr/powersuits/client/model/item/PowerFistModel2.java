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

package lehjr.powersuits.client.model.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 1/13/2013 3:17:20 AM
 *  Template version 1.1
 * Java generated by Techne
 * Keep in mind that you still need to fill in some blanks
 * - ZeuX
 */
@OnlyIn(Dist.CLIENT)
public class PowerFistModel2 extends Model {
    public static final ResourceLocation TEXTURE = new ResourceLocation(MPSConstants.MOD_ID, "textures/models/powerfist.png");

    public int boltSize = 0;
    ModelPart mainarm;
//    ModelPart armorright;
//    ModelPart armorleft;
//    ModelPart wristtopright;
//    ModelPart wristtopleft;
//    ModelPart wristbottomright;
//    ModelPart wristbottomleft;
//    ModelPart fingerguard;
//    ModelPart crystalholder;
//    ModelPart crystal;
//    ModelPart supportright1;
//    ModelPart supportright2;
//    ModelPart supportright3;
//    ModelPart supportright4;
//    ModelPart supportright5;
//    ModelPart supportbaseright;
//    ModelPart supportbaseleft;
//    ModelPart supportleftfront;
//    ModelPart supportrightfront;
//    ModelPart supportleft1;
//    ModelPart supportleft2;
//    ModelPart supportleft3;
//    ModelPart supportleft4;
//    ModelPart supportleft5;

    // Hand parts
    ModelPart palm;
    ModelPart index1;
    ModelPart index2;
    ModelPart middlefinger1;
    ModelPart middlefinger2;
    ModelPart ringfinger1;
    ModelPart ringfinger2;
    ModelPart pinky1;
    ModelPart pinky2;
    ModelPart thumb1;
    ModelPart thumb2;

    boolean isRightHand = true;

    private final ModelPart root;

    Map<String, ModelPart> partMap = new HashMap<>();

    public PowerFistModel2(boolean isRightHand) {
        this(createLayer(isRightHand).bakeRoot(), isRightHand);
    }


    public PowerFistModel2(ModelPart root, boolean isRightHand) {
        super(RenderType::itemEntityTranslucentCull);
        this.root = root;
        this.isRightHand = isRightHand;

        mainarm = root.getChild("mainarm");
//        armorright = root.getChild("armorright");
//        armorleft = root.getChild("armorleft");
//        wristtopright = root.getChild("wristtopright");
//        wristtopleft = root.getChild("wristtopleft");
//        wristbottomright = root.getChild("wristbottomright");
//        wristbottomleft = root.getChild("wristbottomleft");
//        fingerguard = root.getChild("fingerguard");
//        crystalholder = root.getChild("crystalholder");
//        crystal = root.getChild("crystal");
//
//        supportright1 = root.getChild("supportright1");
//        supportright2 = root.getChild("supportright2");
//        supportright3 = root.getChild("supportright3");
//        supportright4 = root.getChild("supportright4");
//        supportright5 = root.getChild("supportright5");
//        supportbaseright = root.getChild("supportbaseright");
//
//        supportbaseleft = root.getChild("supportbaseleft");
//        supportleftfront = root.getChild("supportleftfront");
//        supportrightfront = root.getChild("supportrightfront");
//        supportleft1 = root.getChild("supportleft1");
//        supportleft2 = root.getChild("supportleft2");
//        supportleft3 = root.getChild("supportleft3");
//        supportleft4 = root.getChild("supportleft4");
//        supportleft5 = root.getChild("supportleft5");

        palm = root.getChild("palm");

        index1 = palm.getChild("index1");
        index2 = index1.getChild("index2");

        middlefinger1 = palm.getChild("middlefinger1");
        middlefinger2 = middlefinger1.getChild("middlefinger2");

        ringfinger1 = palm.getChild("ringfinger1");
        ringfinger2 = ringfinger1.getChild("ringfinger2");

        pinky1 = palm.getChild("pinky1");
        pinky2 = pinky1.getChild("pinky2");

        thumb1 = palm.getChild("thumb1");
        thumb2 = thumb1.getChild("thumb2");
    }

    // warning no error handling
    ModelPart getPart(String path) {
        ModelPart part = this.root;
        if (path.contains(".")) {
            String[] splitPath = path.split(".");
            for (int i = 0; i < splitPath.length; i++) {
                part = part.getChild(splitPath[i]);
            }
        } else {
            part = part.getChild(path);
        }
        return part;
    }


    public static LayerDefinition createLayer(boolean isRightHand) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // Main arm parts
        /** mainarm is supposed to be a first person representation of the player's actual arm */
        partdefinition.addOrReplaceChild("mainarm", CubeListBuilder.create().texOffs( 0, 16).addBox(-3F, 0F, -8F, 6, 6, 10).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0.2617994F, 0F, 0F));
        partdefinition.addOrReplaceChild("armorright", CubeListBuilder.create().texOffs( 42, 0).addBox(1F, -1F, -9F, 3, 5, 8).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0.2617994F, 0F, 0F));
        partdefinition.addOrReplaceChild("armorleft", CubeListBuilder.create().texOffs( 42, 0).addBox(-4F, -1F, -9F, 3, 5, 8).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0.2617994F, 0F, 0F));
        partdefinition.addOrReplaceChild("wristtopright", CubeListBuilder.create().texOffs( 0, 11).addBox(1F, 1F, 2F, 1, 1, 4).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0.2617994F, 0F, 0F));
        partdefinition.addOrReplaceChild("wristtopleft", CubeListBuilder.create().texOffs( 0, 11).addBox(-2F, 1F, 2F, 1, 1, 4).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0.2617994F, 0F, 0F));
        partdefinition.addOrReplaceChild("wristbottomright", CubeListBuilder.create().texOffs( 0, 11).addBox(1F, 3F, 2F, 1, 1, 4).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0.2617994F, 0F, 0F));
        partdefinition.addOrReplaceChild("wristbottomleft", CubeListBuilder.create().texOffs( 0, 11).addBox(-2F, 3F, 2F, 1, 1, 4).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0.2617994F, 0F, 0F));
        partdefinition.addOrReplaceChild("fingerguard", CubeListBuilder.create().texOffs( 28, 9).addBox(-3F, -2F, 8.1F, 5, 2, 2).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        partdefinition.addOrReplaceChild("crystalholder", CubeListBuilder.create().texOffs( 48, 13).addBox(-2F, -1F, -3F, 4, 4, 4).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        partdefinition.addOrReplaceChild("crystal", CubeListBuilder.create().texOffs( 32, 27).addBox(-1F, -2F, -2F, 2, 2, 2).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));

        // Hand Parts ---------------------------------------------------------------------------------------------------
        PartDefinition palmPartDef = partdefinition.addOrReplaceChild("palm", CubeListBuilder.create().texOffs( 18, 0).addBox(isRightHand? -4F : -3F, -1F, 5F, 7, 4, 5).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));

        // Thumb
        PartDefinition thumb1PartDef = palmPartDef.addOrReplaceChild("thumb1", CubeListBuilder.create().texOffs( 16, 9).addBox(-.5F, -1F, 0F, 1, 2, 4).mirror(isRightHand), PartPose.offsetAndRotation(isRightHand ? -4F : 4F, 1.5F, 8F, 0F, isRightHand? -0.4014257F : 0.4014257F, 0F));
        thumb1PartDef.addOrReplaceChild("thumb2", CubeListBuilder.create().texOffs( 10, 0).addBox(-.5F, -.5F, 0F, 1, 1, 3).mirror(isRightHand), PartPose.offsetAndRotation(0, 0, 4F, 0F, 0F, 0F));

        // Index finger
        PartDefinition index1PartDef = palmPartDef.addOrReplaceChild("index1", CubeListBuilder.create().texOffs(34, 13).addBox(-.5F, -.5F, 0F, 1, 1, 5).mirror(isRightHand), PartPose.offsetAndRotation(isRightHand? -3.5F : 3.5F, -1.5F, 10F, 0.2617994F, 0F, 0F));
        index1PartDef.addOrReplaceChild("index2", CubeListBuilder.create().texOffs( 34, 13).addBox(-.5F, -.5F, 0F, 1, 1, 4).mirror(isRightHand), PartPose.offsetAndRotation(0, 0, 5F, -0.2617994F * 2, 0F, 0F));

        // Middle finger
        PartDefinition middlefinger1PartDef = palmPartDef.addOrReplaceChild("middlefinger1", CubeListBuilder.create().texOffs( 34, 13).addBox(-.5F, -.5F, 0F, 1, 1, 6).mirror(isRightHand), PartPose.offsetAndRotation(isRightHand? -1.5F : 1.5F, -1.5F, 10F, 0.2617994F, 0F, 0F));
        middlefinger1PartDef.addOrReplaceChild("middlefinger2", CubeListBuilder.create().texOffs( 34, 13).addBox(-.5F, -.5F, 0F, 1, 1, 4).mirror(isRightHand), PartPose.offsetAndRotation(0, 0, 6F, -0.3444116F, 0F, 0F));

        // Ring finger
        PartDefinition ringfinger1PartDef = palmPartDef.addOrReplaceChild("ringfinger1", CubeListBuilder.create().texOffs( 34, 13).addBox(-.5F, -.5F, 0F, 1, 1, 5).mirror(isRightHand), PartPose.offsetAndRotation(isRightHand? 0.5F : -0.5F, -1.5F, 10F, 0.2617994F, 0F, 0F));
        ringfinger1PartDef.addOrReplaceChild("ringfinger2", CubeListBuilder.create().texOffs( 34, 13).addBox(-.5F, -.5F, 0F, 1, 1, 4).mirror(isRightHand), PartPose.offsetAndRotation(0, 0, 5F, -0.2617994F, 0F, 0F));

        // Pinky
        PartDefinition pinky1PartDef = palmPartDef.addOrReplaceChild("pinky1", CubeListBuilder.create().texOffs( 34, 13).addBox(-.5F, -.5F, 0F, 1, 1, 4).mirror(isRightHand), PartPose.offsetAndRotation(isRightHand? 2.5F : -2.5F, -1.5F, 10F, 0.2617994F, 0F, 0F));
        pinky1PartDef.addOrReplaceChild("pinky2", CubeListBuilder.create().texOffs( 34, 13).addBox(-.5F, -.5F, 0F, 1, 1, 4).mirror(isRightHand), PartPose.offsetAndRotation(0, 0, 4F, -0.4537856F, 0F, 0F));

        /** FINISH me below *--------------------*/
        // Left supports
        partdefinition.addOrReplaceChild("supportbaseleft", CubeListBuilder.create().texOffs( 47, 21).addBox(-4.4F, -0.6666667F, -5.4F, 3, 3, 5).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0.2617994F, 0F, 0F));
        partdefinition.addOrReplaceChild("supportleftfront", CubeListBuilder.create().texOffs( 49, 23).addBox(isRightHand? -4.333333F : -3.333333F, 0.3333333F, 4.666667F, 1, 2, 3).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F,  0F, 0F, 0F));
        partdefinition.addOrReplaceChild("supportleft1", CubeListBuilder.create().texOffs( 54, 27).addBox(-2.2F, -0.4F, -6.066667F, 4, 1, 1).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0.2722714F, 1.066978F, 0F));
        partdefinition.addOrReplaceChild("supportleft2", CubeListBuilder.create().texOffs( 52, 21).addBox(-6F, 0.4666667F, 2.5F, 2, 2, 1).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, -0.6329727F, 0F));
        partdefinition.addOrReplaceChild("supportleft3", CubeListBuilder.create().texOffs(52, 21).addBox(-6.5F, 1F, -0.5F, 1, 1, 5).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        partdefinition.addOrReplaceChild("supportleft4", CubeListBuilder.create().texOffs(52, 21).addBox(-7.9F, 0.4666667F, 1.7F, 2, 2, 1).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0.3688462F, 0F));
        partdefinition.addOrReplaceChild("supportleft5", CubeListBuilder.create().texOffs(54, 27).addBox(-0.8666667F, 1F, isRightHand? 7F : 6.333333F, 4, 1, 1).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F,0F, -0.7714355F, 0F));

        // Right supports
        partdefinition.addOrReplaceChild("supportbaseright", CubeListBuilder.create().texOffs( 47, 21).addBox(1.433333F, -0.6666667F, -5.4F, 3, 3, 5).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0.2617994F, 0F, 0F));
        partdefinition.addOrReplaceChild("supportrightfront", CubeListBuilder.create().texOffs( 49, 23).addBox(isRightHand? 2.3F : 3.3F, 0.3333333F, 4.666667F, 1, 2, 3).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        partdefinition.addOrReplaceChild("supportright1", CubeListBuilder.create().texOffs( 54, 27).addBox(-1.8F, -0.8F, -6.066667F, 4, 1, 1).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0.2722714F, -1.066972F, 0F));
        partdefinition.addOrReplaceChild("supportright2",  CubeListBuilder.create().texOffs( 52, 21).addBox(4F, 0.4666667F, 2.5F, 2, 2, 1).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0.6329786F, 0F));
        partdefinition.addOrReplaceChild("supportright3", CubeListBuilder.create().texOffs( 52, 21).addBox(5.1F, 1F, -0.8333333F, 1, 1, 5).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        partdefinition.addOrReplaceChild("supportright4", CubeListBuilder.create().texOffs( 52, 21).addBox(5.633333F, 0.4666667F, 1.7F, 2, 2, 1).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, -0.3688404F, 0F));
        partdefinition.addOrReplaceChild("supportright5", CubeListBuilder.create().texOffs( 54, 27).addBox(-2.866667F, 1F, isRightHand? 6.333333F : 7F, 4, 1, 1).mirror(isRightHand), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0.7714355F, 0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public void renderPart(String part, PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        partlMap.getOrDefault(part, mainarm).render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override // public void render(PoseStack pPoseStack, VertexConsumer pVertexConsumer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha)
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.mainarm.visible = true;
        this.root.children.values().forEach(part-> {
            part.visible = true;
            part.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    private void setRotation(ModelPart model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

    public void setPose(float indexOpen, float indexFlex, float thumbOpen, float thumbFlex, float otherFingersOpen, float otherFingersFlex) {
        index1.xRot = indexOpen;
        index2.xRot = indexFlex;
        middlefinger1.xRot = otherFingersOpen;
        middlefinger2.xRot = otherFingersFlex;
        ringfinger1.xRot = otherFingersOpen;
        ringfinger2.xRot = otherFingersFlex;
        pinky1.xRot = otherFingersOpen - 0.1f;
        pinky2.xRot = otherFingersFlex;
        // fixme left and right hand
        thumb1.yRot = isRightHand? -thumbOpen : thumbOpen;
        thumb2.yRot = isRightHand? -thumbFlex : thumbFlex;
    }

    // FIXME
    public void setPoseForPlayer(Player player) {
        if (player.isUsingItem() && player.getUseItem() != ItemStack.EMPTY) {
            player.getUseItem().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModeChangingItem.class::isInstance)
                    .map(IModeChangingItem.class::cast)
                    .ifPresent(iItemHandler -> {
                        if (iItemHandler.hasActiveModule(MPSRegistryNames.PLASMA_CANNON_MODULE)) {
                            setPose(1.5f, -1, 1.5f, -1, 1.5f, -1);
                            this.boltSize = player.getUseItemRemainingTicks() > 50 ? 50 : player.getUseItemRemainingTicks();
                        }
                    });
        } else {
            setPose(0.5f, -1, 0.5f, -1, 0.5f, -1);
            this.boltSize = 0;
        }
    }

    public void setFiringBoltPose(int boltSize) {
        this.boltSize = boltSize;
        setFiringPose();
    }


    public void setFiringPose() {
        // FIXME: left and right hand values
        setPose(1.5f, -1, 1.5f, -1, 1.5f, -1);
    }


    public void setNeutralPose() {
        // FIXME: left and right hand values
        setPose(0.5f, -1, 0.5f, -1, 0.5f, -1);
        this.boltSize = 0;
    }

    void test() {
//        List<ModelPart.TexturedQuad> list = new ArrayList<>();
//
//        for (String key: partlMap.keySet()) {
//            ModelPart part = partlMap.get(key);
//            for(ModelPart.ModelBox box : part.cubeList) {
//                for(ModelPart.TexturedQuad texturedQuad : box.quads) {
//                    list.add(texturedQuad);
//                }
//            }
//        }
    }




//    public PowerFistModel2(Function<ResourceLocation, RenderType> renderTypeIn) {
//        super(RenderType::getEntitySolid);
//    }
}