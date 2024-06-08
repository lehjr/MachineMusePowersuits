package lehjr.powersuits.common.registration;

import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.armor.DiamondPlatingModule;
import lehjr.powersuits.common.item.module.armor.EnergyShieldModule;
import lehjr.powersuits.common.item.module.armor.IronPlatingModule;
import lehjr.powersuits.common.item.module.armor.NetheritePlatingModule;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class MPSItems {
    public static final DeferredRegister.Items MPS_ITEMS = DeferredRegister.createItems(MPSConstants.MOD_ID);
    /**
     * Block Items --------------------------------------------------------------------------------
     */


    /**
     * Tools --------------------------------------------------------------------------------------
     */


    /**
     * Armor --------------------------------------------------------------------------------------
     */


    /**
     * Modules ------------------------------------------------------------------------------------
     */
    // Armor --------------------------------------------------------------------------------------
    public static final DeferredHolder<Item, IronPlatingModule> IRON_PLATING_MODULE = MPS_ITEMS.register(MPSConstants.IRON_PLATING_MODULE.getPath(),
            IronPlatingModule::new);
    public static final DeferredHolder<Item, DiamondPlatingModule> DIAMOND_PLATING_MODULE = MPS_ITEMS.register(MPSConstants.DIAMOND_PLATING_MODULE.getPath(),
            DiamondPlatingModule::new);
    public static final DeferredHolder<Item, NetheritePlatingModule> NETHERITE_PLATING_MODULE = MPS_ITEMS.register(MPSConstants.NETHERITE_PLATING_MODULE.getPath(),
            NetheritePlatingModule::new);
    public static final DeferredHolder<Item, EnergyShieldModule> ENERGY_SHIELD_MODULE = MPS_ITEMS.register(MPSConstants.ENERGY_SHIELD_MODULE.getPath(),
            EnergyShieldModule::new);









    public static final DeferredRegister<CreativeModeTab> MPS_CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MPSConstants.MOD_ID);

    public static final Supplier<CreativeModeTab> MPS_TAB = MPS_CREATIVE_MODE_TAB.register("creative.mode.tab",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(IRON_PLATING_MODULE.get()))
                    .title(Component.translatable(MPSConstants.CREATIVE_TAB_TRANSLATION_KEY))
                    .displayItems((parameters, output) -> {

                        // Modules ----------------------------------------------------------------
                        // Armor
                        output.accept(IRON_PLATING_MODULE.get());
                        output.accept(DIAMOND_PLATING_MODULE.get());
                        output.accept(NETHERITE_PLATING_MODULE.get());
                        output.accept(ENERGY_SHIELD_MODULE.get());
                        //


                    })
                    .build());




    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
//        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
//            event.accept(EXAMPLE_BLOCK_ITEM);
    }

}
