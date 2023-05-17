//package lehjr.numina.common.integration.refinedstorage;
//
//import com.refinedmods.refinedstorage.api.network.INetwork;
//import com.refinedmods.refinedstorage.inventory.player.PlayerSlot;
//import com.refinedmods.refinedstorage.item.NetworkItem;
//import com.refinedmods.refinedstorage.util.NetworkUtils;
//import lehjr.numina.common.capabilities.module.powermodule.IConfig;
//import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
//import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
//import lehjr.numina.common.capabilities.module.powermodule.PowerModuleCapability;
//import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
//import lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
//import lehjr.numina.common.energy.ElectricItemUtils;
//import lehjr.numina.common.item.ItemUtils;
//import net.minecraft.core.Direction;
//import net.minecraft.world.item.context.UseOnContext;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.Level;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.ICapabilityProvider;
//import net.minecraftforge.common.util.LazyOptional;
//import net.minecraftforge.event.AttachCapabilitiesEvent;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import java.util.concurrent.Callable;
//
//public class RSWirelessHandler {
//    private static final String NBT_NODE_X = "NodeX";
//    private static final String NBT_NODE_Y = "NodeY";
//    private static final String NBT_NODE_Z = "NodeZ";
//    private static final String NBT_DIMENSION = "Dimension";
//
//    // refinedstorage
//    public static void attach(AttachCapabilitiesEvent<ItemStack> event, Callable<IConfig> moduleConfigGetterIn) {
//        final ItemStack itemStack = event.getObject();
//        final ResourceLocation regName = itemStack.getItem().getRegistryName();
//        final ResourceLocation wireless_grid = new ResourceLocation("refinedstorage:wireless_grid");
//        final ResourceLocation wireless_fluid_grid = new ResourceLocation("refinedstorage:wireless_fluid_grid");
//            /*
//            known possibilities
//            --------------------
//            refinedstorage:wireless_grid
//            refinedstorage:wireless_fluid_grid
//             */
//        if (regName.equals(wireless_grid) || regName.equals(wireless_fluid_grid)) {
//            IRightClickModule rsTerminal = new RightClickModule(itemStack, ModuleCategory.TOOL, ModuleTarget.TOOLONLY, moduleConfigGetterIn) {
//                @Override
//                public ActionResult use(ItemStack itemStackIn, Level worldIn, Player playerIn, Hand hand) {
//                    ItemStack module = ItemUtils.getActiveModuleOrEmpty(itemStackIn);
//
//                    if (!worldIn.isClientSide()) {
//                        int totalEnergy = ElectricItemUtils.getPlayerEnergy(playerIn);
//                        int energyNeeded = ElectricItemUtils.chargeItem(module, totalEnergy, true);
//                        if (energyNeeded > 0) {
//                            energyNeeded = ElectricItemUtils.drainPlayerEnergy(playerIn, energyNeeded, false);
//                            ElectricItemUtils.chargeItem(module, energyNeeded, false);
//                        }
//                    }
//
//                    if (!worldIn.isClientSide && module.getItem() instanceof NetworkItem) {
//                        ((NetworkItem)module.getItem()).applyNetwork(worldIn.func_73046_m(), module, n -> n.getNetworkItemManager().open(playerIn, module, PlayerSlot.getSlotForHand(playerIn, hand)), err -> playerIn.sendMessage(err, playerIn.getUUID()));
//                    }
//                    return ActionResult.func_226248_a_(itemStackIn);
//                }
//
//                @Override
//                public InteractionResult onItemUseFirst(ItemStack itemStackIn, UseOnContext context) {
//                    ItemStack module = ItemUtils.getActiveModuleOrEmpty(itemStackIn);
//                    INetwork network = NetworkUtils.getNetworkFromNode(NetworkUtils.getNodeFromTile(context.getLevel().getBlockEntity(context.func_195995_a())));
//                    if (network != null) {
//                        CompoundTag tag = module.getTag();
//
//                        if (tag == null) {
//                            tag = new CompoundTag();
//                        }
//
//                        tag.putInt(NBT_NODE_X, network.getPosition().getX());
//                        tag.putInt(NBT_NODE_Y, network.getPosition().getY());
//                        tag.putInt(NBT_NODE_Z, network.getPosition().getZ());
//                        tag.putString(NBT_DIMENSION, context.getLevel().func_234923_W_().func_240901_a_().toString());
//
//                        module.setTag(tag);
//
//                        return InteractionResult.SUCCESS;
//                    }
//
//                    return InteractionResult.PASS;
//                }
//            };
//
//            event.addCapability(regName, new ICapabilityProvider() {
//                @Nonnull
//                @Override
//                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//                    if (cap == NuminaCapabilities.POWER_MODULE) {
//                        return LazyOptional.of(() -> (T) rsTerminal);
//                    }
//                    return LazyOptional.empty();
//                }
//            });
//        }
//    }
//}
