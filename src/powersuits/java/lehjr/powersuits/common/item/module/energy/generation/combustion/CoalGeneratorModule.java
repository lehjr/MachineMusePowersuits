//package lehjr.powersuits.item.module.energy.generation;
//
//import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
//import lehjr.numina.module.EnumModuleCategory;
//import lehjr.numina.module.EnumModuleTarget;
//import lehjr.numina.module.IPlayerTickModule;
//import lehjr.numina.module.IToggleableModule;
//import lehjr.powersuits.common.item.module.AbstractPowerModule;
//import lehjr.powersuits.item.module.AbstractPowerModule;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//
///**
// * Created by Eximius88 on 1/16/14.
// *
// *
// *
// * // TODO: everything
// *
// * things to figure out:
// * ---------------------
//  - use all burnable fuel or just coal
//  - draw from player inventory or set up some other type of slot?
//  - charge player energy through electric item utils...
//
// *
// *
// *
// */
//public class CoalGeneratorModule extends AbstractPowerModule {
//
//    public CoalGeneratorModule() {
//
//    }
//
//
//    public CoalGeneratorModule(String pos) {
//        super(pos, EnumModuleTarget.TORSOONLY, EnumModuleCategory.CATEGORY_ENERGY_GENERATION);
////        ModuleManager.INSTANCE.addInstallCost(getDataName(), new ItemStack(Blocks.FURNACE));
////        ModuleManager.INSTANCE.addInstallCost(getDataName(), ItemUtils.copyAndResize(Iteminecraftomponent.controlCircuit, 1));
////
////        addBaseProperty(MPSModuleConstants.MAX_COAL_STORAGE, 128);
////        addBaseProperty(MPSModuleConstants.HEAT_GENERATION, 2.5);
////        addBaseProperty(MPSModuleConstants.ENERGY_PER_COAL, 300);
//    }
//
//    @Override
//    public void onPlayerTickActive(Player player, ItemStack item) {
//
//        // TODO: add charging code, change to more generic combustion types... maybe add GUI
//
////        SimpleContainer inv = player.inventory;
////        int coalNeeded = (int) ModuleManager.INSTANCE.getOrSetModularPropertyDouble(item, MPSModuleConstants.MAX_COAL_STORAGE) - CoalGenHelper.getCoalLevel(item);
////        if (coalNeeded > 0) {
////            for (int i = 0; i < inv.getSizeInventory(); i++) {
////                ItemStack stack = inv.getStackInSlot(i);
////                if (!stack.isEmpty() && stack.getItem() == Items.COAL) {
////                    int loopTimes = coalNeeded < stack.getCount() ? coalNeeded : stack.getCount();
////                    for (int i2 = 0; i2 < loopTimes; i2++) {
////                        CoalGenHelper.setCoalLevel(item, CoalGenHelper.getCoalLevel(item) + 1);
////                        player.inventory.decrStackSize(i, 1);
////                        if (stack.getCount() == 0) {
////                            player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
////                        }
////                    }
////
////
////                    if (ModuleManager.INSTANCE.getOrSetModularPropertyDouble(item, MPSModuleConstants.MAX_COAL_STORAGE) - CoalGenHelper.getCoalLevel(item) < 1) {
////                        i = inv.getSizeInventory() + 1;
////                    }
////                }
////            }
////        }
//    }
////
////    @Override
////    public void onPlayerTickInactive(Player player, ItemStack item) {
////    }
////
////    @Override
////    public ModuleCategory getCategory() {
////        return ModuleCategory.ENERGY_GENERATION;
////    }
//}