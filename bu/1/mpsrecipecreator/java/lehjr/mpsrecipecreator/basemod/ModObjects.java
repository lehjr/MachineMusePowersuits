package lehjr.mpsrecipecreator.basemod;

import lehjr.mpsrecipecreator.block.RecipeWorkbench;
import lehjr.mpsrecipecreator.container.MPSRCMenu;
import com.lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.extensions.IMenuTypeExtension;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author lehjr
 */
@Mod.EventBusSubscriber(modid = MPSRCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public enum ModObjects {
    INSTANCE;
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MPSRCConstants.MOD_ID);

    /**
     * Block --------------------------------------------------------------------------------------
     */
    public static final RegistryObject<RecipeWorkbench> RECIPE_WORKBENCH = BLOCKS.register(MPSRCConstants.RECIPE_WORKBENCH__REGNAME.getPath(), RecipeWorkbench::new);

    /**
     * Item ---------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MPSRCConstants.MOD_ID);
    public static final RegistryObject<Item> RECIPE_WORKBENCH_ITEM = ITEMS.register(MPSRCConstants.RECIPE_WORKBENCH__REGNAME.getPath(),
            () -> new BlockItem(RECIPE_WORKBENCH.get(), new Item.Properties()));


    /**
     * AbstractContainerMenu type -----------------------------------------------------------------------------
     */
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MPSRCConstants.MOD_ID);

    public static final RegistryObject<MenuType<MPSRCMenu>> RECIPE_WORKBENCH_CONTAINER_TYPE = MENU_TYPES.register(MPSRCConstants.RECIPE_WORKBENCH_TYPE__REG_NAME.getPath(),
            () -> IMenuTypeExtension.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new MPSRCMenu(windowId, inv, ContainerLevelAccess.create(inv.player.level(), pos));
            }));


    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MPSRCConstants.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("creative.mode.tab",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(RECIPE_WORKBENCH_ITEM.get()))
                    .title(Component.translatable(NuminaConstants.CREATIVE_TAB_TRANSLATION_KEY))
                    .displayItems((parameters, output) -> {
                        output.accept(RECIPE_WORKBENCH_ITEM.get());
                    })
                    .build());
}