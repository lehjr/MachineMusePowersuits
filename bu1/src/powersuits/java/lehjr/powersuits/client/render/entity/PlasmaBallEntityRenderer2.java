//package lehjr.powersuits.client.render.entity;
//
//import com.mojang.blaze3d.vertex.DefaultVertexFormat;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import com.mojang.blaze3d.vertex.VertexFormat;
//import com.lehjr.numina.client.gui.geometry.DrawableMuseCircle;
//import com.lehjr.numina.common.constants.NuminaConstants;
//import com.lehjr.numina.common.math.Color;
//import com.lehjr.numina.common.utils.IconUtils;
//import com.lehjr.numina.common.utils.MathUtils;
//import lehjr.powersuits.common.entity.PlasmaBallEntity;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.GameRenderer;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderStateShard;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
//import net.minecraft.client.renderer.entity.EntityRenderer;
//import net.minecraft.client.renderer.entity.EntityRendererProvider;
//import net.minecraft.resources.ResourceLocation;
//
//import java.nio.FloatBuffer;
//
//// TODO: get this working??
//public class PlasmaBallEntityRenderer2 extends EntityRenderer<PlasmaBallEntity> {
//    protected static final RenderStateShard.LightmapStateShard NO_LIGHTMAP = new RenderStateShard.LightmapStateShard(false);
//    protected static final RenderStateShard.ShaderStateShard RENDERTYPE_TRANSLUCENT_SHADER = new RenderStateShard.ShaderStateShard(
//            GameRenderer::getRendertypeTranslucentShader
//    );
////    protected static final RenderStateShard.EmptyTextureStateShard NO_TEXTURE = new RenderStateShard.EmptyTextureStateShard();
//
//    private static final RenderType PLASMA_BALL = RenderType.create(
//            "plasmaball",
//            DefaultVertexFormat.POSITION_COLOR,
//            VertexFormat.Mode.TRIANGLE_FAN,
//            4194304,
//            true,
//            false,
//            RenderType.CompositeState.builder()
//                    .setLightmapState(NO_LIGHTMAP)
//                    .setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
//                    .setTextureState(RenderStateShard.NO_TEXTURE)
//                    .createCompositeState(true)
//    );
//
//
//
//
//    /*
//     Need custom render type
//     -----------------------
//     Shader: RenderSystem.setShader(GameRenderer::getPositionColorShader);
//     Blend: RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
//                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
//                GlStateManager.SourceFactor.ONE,
//                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//
//     Old setup
//     ---------------------
//     RenderState.on2D();
//     RenderState.arraysOnC();
//     enderState.texturelessOn();
//     RenderSystem.enableBlend();
//     GL11.glColorPointer(4, 0, color); // rgba
//     GL11.glVertexPointer(3, 0, points); // 3 vertices
//     GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, points.limit() / 3);
//
//     RenderState.arraysOff(); ?
//
//VertexFormat.Mode.GL11.GL_TRIANGLE_FAN
//     */
//
//
//
//
//    protected static DrawableMuseCircle circle1;
//    protected static DrawableMuseCircle circle2;
//    protected static DrawableMuseCircle circle3;
//    protected static DrawableMuseCircle circle4;
//    private static final RenderType RENDER_TYPE = RenderType.glintTranslucent();
//
//    public PlasmaBallEntityRenderer2(EntityRendererProvider.Context pContext) {
//        super(pContext);
//        Color c1 = new Color(.3F, .3F, 1F, 0.3F);
//        circle1 = new DrawableMuseCircle(c1, c1);
//        c1 = new Color(.3F, .3F, 1, 0.6F);
//        circle2 = new DrawableMuseCircle(c1, c1);
//        c1 = new Color(.3F, .3F, 1, 1);
//        circle3 = new DrawableMuseCircle(c1, c1);
//        circle4 = new DrawableMuseCircle(c1, new Color(1, 1, 1, 1));
//
//    }
//
//    @Override
//    public ResourceLocation getTextureLocation(PlasmaBallEntity entity) {
//        return NuminaConstants.TEXTURE_WHITE;
//    }
//
//    @Override
//    public void render(PlasmaBallEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
//        float size = entityIn.getChargePercent();
//
////        double size = (bolt.size) / 10.0;
//        matrixStackIn.pushPose();
////        matrixStackIn.translate(x, y, z);
////        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
//        matrixStackIn.scale(0.5F, 0.5F, 0.5F);
//        VertexConsumer vertexconsumer = bufferIn.getBuffer(PLASMA_BALL);
//        doRender(matrixStackIn, size, vertexconsumer);
////        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//        matrixStackIn.popPose();
//    }
//
//    public static FloatBuffer unrotatebuffer;
//
//    public static void doRender(PoseStack poseStack, float size, VertexConsumer vertexConsumer) {
//        poseStack.pushPose();
////        look at DragonFireballRenderer for clues how to unrotate without using "unRotate()
//
////        BillboardHelper.unRotate();
//
//        poseStack.mulPose(getEntityRenderDispatcher().cameraOrientation());
//        float scale = size * MathUtils.DIV_16F;
//        poseStack.scale(scale, scale, scale);
//        int millisPerCycle = 500;
//        double timeScale = Math.cos((System.currentTimeMillis() % millisPerCycle) * 2.0 / millisPerCycle - 1.0);
////        RenderState.glowOn();
//
//
//        circle1.render(vertexConsumer, poseStack, 4, 0, 0, 0);
//        poseStack.translate(0, 0, 0.001);
//        circle2.render(vertexConsumer, poseStack, 3 + timeScale / 2, 0, 0, 0);
//        poseStack.translate(0, 0, 0.001);
//        circle3.render(vertexConsumer, poseStack, 2 + timeScale, 0, 0, 0);
//        poseStack.translate(0, 0, 0.001);
//        circle4.render(vertexConsumer, poseStack, 1 + timeScale, 0, 0, 0);
//        for (int i = 0; i < 3; i++) {
//            double angle1 = (Math.random() * 2 * Math.PI);
//            double angle2 = (Math.random() * 2 * Math.PI);
//            IconUtils.drawLightning(Math.cos(angle1) * 0.5, Math.sin(angle1) * 0.5, 0, Math.cos(angle2) * 5, Math.sin(angle2) * 5, 1,
//                    new Color(1, 1, 1, 0.9F));
//        }
////        RenderState.glowOff();
//        poseStack.popPose();
//    }
//
//    static EntityRenderDispatcher getEntityRenderDispatcher() {
//        return Minecraft.getInstance().getEntityRenderDispatcher();
//    }
//
//}