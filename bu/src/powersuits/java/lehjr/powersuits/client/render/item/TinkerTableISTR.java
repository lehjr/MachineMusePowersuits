//package lehjr.powersuits.client.render.item;
//
//import lehjr.powersuits.block.TinkerTable;
//import lehjr.powersuits.client.model.block.TinkerTableModel2;
//import lehjr.powersuits.item.block.TinkerTableItem;
//import lehjr.powersuits.tile_entity.TinkerTableTileEntity;
//import com.mojang.blaze3d.matrix.MatrixStack;
//import net.minecraft.block.Block;
//import net.minecraft.client.renderer.IRenderTypeBuffer;
//import net.minecraft.client.renderer.model.ItemCameraTransforms;
//import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
//import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
//import net.minecraft.item.BlockItem;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//
//public class TinkerTableISTR extends ItemStackTileEntityRenderer {
//    TinkerTableModel2 model = new TinkerTableModel2();
//    private final TinkerTableTileEntity tinkerTable = new TinkerTableTileEntity();
//
//    public TinkerTableISTR() {
//        super();
//    }
//
//    @Override
//    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
//        Item item = stack.getItem();
//        System.out.println("made it here");
//
//        if (item instanceof TinkerTableItem) {
//            System.out.println("made it here 2");
//            Block block = ((BlockItem)item).getBlock();
//            if (block instanceof TinkerTable) {
//                System.out.println("made it here 3");
//
//                TileEntity tileentity;
//
//                tileentity = this.tinkerTable;
//                boolean test =
//
//                TileEntityRendererDispatcher.instance.renderItem(tileentity, matrixStack, buffer, combinedLight, combinedOverlay);
//
//
//                System.out.println("test: " + test);
//            }
//        }
//    }
//}
