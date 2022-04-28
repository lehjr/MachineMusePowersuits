//package lehjr.powersuits.client.render.item;
//
//import lehjr.powersuits.block.TinkerTable;
//import lehjr.powersuits.client.model.block.TinkerTableModel2;
//import lehjr.powersuits.item.block.TinkerTableItem;
//import lehjr.powersuits.tile_entity.TinkerTableBlockEntity;
//import com.mojang.blaze3d.matrix.PoseStack;
//import net.minecraft.block.Block;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.model.ItemTransforms;
//import net.minecraft.client.renderer.tileentity.ItemStackBlockEntityRenderer;
//import net.minecraft.client.renderer.tileentity.BlockEntityRendererDispatcher;
//import net.minecraft.item.BlockItem;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.BlockEntity;
//
//public class TinkerTableISTR extends ItemStackBlockEntityRenderer {
//    TinkerTableModel2 model = new TinkerTableModel2();
//    private final TinkerTableBlockEntity tinkerTable = new TinkerTableBlockEntity();
//
//    public TinkerTableISTR() {
//        super();
//    }
//
//    @Override
//    public void renderByItem(ItemStack stack, ItemTransforms.TransformType p_239207_2_, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
//        Item item = stack.getItem();
//        System.out.println("made it here");
//
//        if (item instanceof TinkerTableItem) {
//            System.out.println("made it here 2");
//            Block block = ((BlockItem)item).getBlock();
//            if (block instanceof TinkerTable) {
//                System.out.println("made it here 3");
//
//                BlockEntity tileentity;
//
//                tileentity = this.tinkerTable;
//                boolean test =
//
//                BlockEntityRendererDispatcher.instance.renderItem(tileentity, matrixStack, buffer, combinedLight, combinedOverlay);
//
//
//                System.out.println("test: " + test);
//            }
//        }
//    }
//}
