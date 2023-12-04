//package lehjr.numina.mixin.common.entity.player;
//
//import com.google.common.collect.ImmutableList;
//import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
//import lehjr.numina.common.capabilities.module.powermodule.PowerModule;
//import lehjr.numina.common.item.ItemUtils;
//import lehjr.numina.common.network.NuminaPackets;
//import lehjr.numina.common.network.packets.clientbound.HandStorageSyncClientBound;
//import lehjr.numina.imixin.common.entity.player.IMixinInventory;
//import net.minecraft.core.NonNullList;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.nbt.ListTag;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraftforge.common.capabilities.ForgeCapabilities;
//import net.minecraftforge.network.PacketDistributor;
//import org.jetbrains.annotations.NotNull;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.Unique;
//import org.spongepowered.asm.mixin.injection.*;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//import java.util.List;
//
//@Mixin(Inventory.class)
//public class MixinInventory implements IMixinInventory {
//    @Unique
//    public NonNullList<ItemStack> offHandStorage = NonNullList.withSize(1, ItemStack.EMPTY);
//    @Unique
//    public NonNullList<ItemStack> mainHandStorage = NonNullList.withSize(1, ItemStack.EMPTY);
//    @Final
//    @Shadow
//    public NonNullList<ItemStack> items;
//    @Final
//    @Shadow
//    public NonNullList<ItemStack> armor;
//    @Final
//    @Shadow
//    public NonNullList<ItemStack> offhand;
//
////    @Final
////    @Shadow
////    private List<NonNullList<ItemStack>> compartments = ImmutableList.of(
////                                                                            items,
////                                                                            armor,
////                                                                            offhand,
////                                                                            mainHandStorage,
////                                                                            offHandStorage);
//
//
//    @Final
//    @Shadow
//    public static int INVENTORY_SIZE;
//    @Shadow
//    @Final public Player player;
//
//    @Shadow public ItemStack getSelected() {
//        throw new IllegalStateException("Mixin failed to shadow getSelected()");
//    }
//
//    @Inject(method = "<init>*", at = @At("RETURN"), remap = false)
//    private void onConstructed(final Player playerIn, final CallbackInfo ci) {
//        this.offHandStorage = NonNullList.withSize(1, ItemStack.EMPTY);
//        this.mainHandStorage = NonNullList.withSize(1, ItemStack.EMPTY);
//
//        if (playerIn != null) {
//            System.out.println("player.level is client side? " + playerIn.level.isClientSide());
//        }
//    }
//
//    @Override
//    public void setOffHandStorage(@NotNull ItemStack itemStack) {
//        if (this.offHandStorage == null) {
//            this.offHandStorage = NonNullList.withSize(1, ItemStack.EMPTY);
//        }
//        this.offHandStorage.set(0, itemStack);
//    }
//
//    @Override
//    public void setMainHandStorage(@NotNull ItemStack itemStack) {
//        if (this.mainHandStorage == null) {
//            this.mainHandStorage = NonNullList.withSize(1, ItemStack.EMPTY);
//        }
//
//
//        this.mainHandStorage.set(0, itemStack);
//    }
//
//    @NotNull
//    @Override
//    public ItemStack getOffHandStorage() {
//        return this.offHandStorage.get(0);
//    }
//
//    @NotNull
//    @Override
//    public ItemStack getMainHandStorage() {
//        return this.mainHandStorage.get(0);
//    }
//
//    @Inject(at = @At("TAIL"), method = "load(Lnet/minecraft/nbt/ListTag;)V")
//    private void load(ListTag pListTag, CallbackInfo callbackInfo) {
//        System.out.println("List tag size: " + pListTag.size() +", isClientSide: " + player.level.isClientSide());
//
//        this.offHandStorage.clear();
//        this.mainHandStorage.clear();
//
//
//        for(int i = INVENTORY_SIZE -1; i < pListTag.size(); ++i) {
//            CompoundTag compoundtag = pListTag.getCompound(i);
//            int j = compoundtag.getByte("Slot") & 255;
//
//            ItemStack itemstack = ItemStack.of(compoundtag);
//            if (itemstack.isEmpty()) {
//                itemstack = new ItemStack(Items.CHICKEN);
//            }
//
//            if (!itemstack.isEmpty()) {
//
//                if (j >= 200 && j < this.offHandStorage.size() + 200) {
//                    System.out.println("itemStack offHandStorage: " + itemstack +", player is client: " + player.level.isClientSide);
////                    this.machineMusePowersuits$offHandStorage.set(j - 200, itemstack);
//                    setOffHandStorage(itemstack);
//                    System.out.println("j-200: " + (j - 200));
//
//
//                } else if (j >= 250 && j < this.mainHandStorage.size() + 250) {
//                    System.out.println("itemStack mainHandStorage: " + itemstack  +", player is client: " + player.level.isClientSide);
//                    setMainHandStorage(itemstack);
////                    this.machineMusePowersuits$mainhandStorage.set(j -250, itemstack);
//                    System.out.println("j-250: " + (j - 250));
//                } else {
//                    System.out.println("itemStack unknownStorage: " + itemstack);
//                }
//            }
//
////            if (getOffHandMainStorage().isEmpty()) {
////                setOffHandStorage(new ItemStack(Items.CHICKEN));
////            }
////            if (getMainHandStorage().isEmpty()) {
////                setMainHandStorage(new ItemStack(Items.BAKED_POTATO));
////            }
//
//            if (!getMainHandStorage().isEmpty() || !getOffHandStorage().isEmpty() && player instanceof ServerPlayer) {
//
////                Enqueue
//
////
////                NuminaPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
////                        new HandStorageSyncClientBound(getMainHandStorage(), getOffHandStorage()));
//            }
//        }
//
//
//
//
//    }
//
//
//
//    /**
//     * Writes the inventory out as a list of compound tags. This is where the slot indices are used (+100 for armor, +80
//     * for crafting).
//     */
//    @Inject(at = @At("TAIL"), method = "save(Lnet/minecraft/nbt/ListTag;)Lnet/minecraft/nbt/ListTag;", cancellable = true)
//    private void save(ListTag listTag, CallbackInfoReturnable<ListTag> callableRetList){
//        System.out.println("retList size1: " + listTag.size());
//
//        for (int l = 0; l < this.offHandStorage.size(); l++) {
//            if (!this.offHandStorage.get(l).isEmpty()) {
//                CompoundTag compoundtag1 = new CompoundTag();
//                compoundtag1.putByte("Slot", (byte)(l + 200));
//                this.offHandStorage.get(l).save(compoundtag1);
//                System.out.println("compoundTag 1: " + compoundtag1  +", player is client: " + player.level.isClientSide);
//
//                listTag.add(compoundtag1);
//            }
//        }
//
//        System.out.println("retList size2: " + listTag.size());
//
//        for (int m = 0; m < this.mainHandStorage.size(); m++) {
//            if (!this.mainHandStorage.get(m).isEmpty()) {
//                CompoundTag compoundtag1 = new CompoundTag();
//                compoundtag1.putByte("Slot", (byte)(m + 250));
//                this.mainHandStorage.get(m).save(compoundtag1);
//                System.out.println("compound tag 2: " + compoundtag1);
//                listTag.add(compoundtag1);
//            }
//        }
//        System.out.println("retList size3: " + listTag.size() +", player is client: " + player.level.isClientSide);
//
//        callableRetList.setReturnValue(listTag);
//    }
//
//    @Inject(at = @At("TAIL"), method ="tick()V")
//    private void tick(CallbackInfo info) {
//        offHandStorage.forEach(stack ->stack.getItem().inventoryTick(stack, player.level, player, 200, false));
//        mainHandStorage.forEach(stack ->stack.getItem().inventoryTick(stack, player.level, player, 250, false));
//    }
//
//    @Inject(at = @At("HEAD"), method = "dropAll()V")
//    private void dropAll(CallbackInfo info) {
//        if (!offHandStorage.isEmpty()) {
//            getOffHandStorage().getCapability(ForgeCapabilities.ITEM_HANDLER)
//                    .filter(IModeChangingItem.class::isInstance)
//                    .map(IModeChangingItem.class::cast).ifPresent(iModeChangingItem -> {
//                        if (iModeChangingItem.isModuleValid(offhand.get(0))) {
//                            ItemStack module = offhand.get(0);
//                            ResourceLocation regName = ItemUtils.getRegistryName(module);
//                            for (int i = 0; i < iModeChangingItem.getSlots(); i++) {
//                                ResourceLocation regNameOther = ItemUtils.getRegistryName(iModeChangingItem.getStackInSlot(i));
//                                if (regName.equals(regNameOther)) {
//                                    iModeChangingItem.setStackInSlot(i, module);
//                                    offhand.set(0, iModeChangingItem.getModularItemStack());
//                                }
//                            }
//                        }
//                    });
//        }
//        if (!mainHandStorage.isEmpty()) {
//            getMainHandStorage().getCapability(ForgeCapabilities.ITEM_HANDLER)
//                    .filter(IModeChangingItem.class::isInstance)
//                    .map(IModeChangingItem.class::cast).ifPresent(iModeChangingItem -> {
//                        if (iModeChangingItem.isModuleValid(getSelected())) {
//                            ItemStack module = getSelected();
//                            ResourceLocation regName = ItemUtils.getRegistryName(module);
//                            for (int i = 0; i < iModeChangingItem.getSlots(); i++) {
//                                ResourceLocation regNameOther = ItemUtils.getRegistryName(iModeChangingItem.getStackInSlot(i));
//                                if (regName.equals(regNameOther)) {
//                                    iModeChangingItem.setStackInSlot(i, module);
//                                    player.setItemInHand(InteractionHand.MAIN_HAND, iModeChangingItem.getModularItemStack());
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
//
//
//}
