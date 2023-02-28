//package lehjr.mpsrecipecreator.block;
//
//import lehjr.mpsrecipecreator.container.MPARCAbstractContainerMenu;
//import lehjr.numina.client.sound.SoundDictionary;
//import net.minecraft.block.SoundType;
//import net.minecraft.block.material.Material;
//import net.minecraft.core.BlockPos;
//import net.minecraft.inventory.container.INamedAbstractContainerMenuProvider;
//import net.minecraft.inventory.container.SimpleNamedAbstractContainerMenuProvider;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.TranslatableComponent;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.util.ContainerLevelAccess;
//import net.minecraft.util.Hand;
//import net.minecraft.world.phys.BlockHitResult;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.BlockState;
//
///**
// * @author lehjr
// */
//public class RecipeWorkbench extends Block {
//    public RecipeWorkbench(String regName) {
//        this(new ResourceLocation(regName));
//    }
//
//    public RecipeWorkbench(ResourceLocation regName) {
//        super(Block.Properties.func_200945_a(Material.field_151575_d)
//                .func_200948_a(1.5F, 1000.0F)
//                .func_200947_a(SoundType.field_185852_e)
//                .func_208770_d()
//                .func_235838_a_((state) -> 15));
//        setRegistryName(regName);
//        func_180632_j(this.field_176227_L.func_177621_b());
//    }
//
//    @Override
//    public InteractionResult func_225533_a_(BlockState state, Level worldIn, BlockPos pos, Player player, Hand handIn, BlockHitResult hit) {
//        player.func_184185_a(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1.0F, 1.0F);
//        if (worldIn.isClientSide) {
//            return InteractionResult.SUCCESS;
//        } else {
//            player.func_213829_a(state.func_215699_b(worldIn, pos));
////            player.addStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
//            return InteractionResult.SUCCESS;
//        }
//    }
//
//    private static final Component title = new TranslatableComponent("container.crafting");
//    public INamedAbstractContainerMenuProvider func_220052_b(BlockState state, Level worldIn, BlockPos pos) {
//        return new SimpleNamedAbstractContainerMenuProvider((windowID, playerInventory, playerEntity) ->
//                new MPARCAbstractContainerMenu(windowID, playerInventory, ContainerLevelAccess.create(worldIn, pos)), title);
//    }
//}