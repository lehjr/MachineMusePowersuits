package lehjr.powersuits.common.item.module.debug;

import lehjr.powersuits.common.item.module.AbstractPowerModule;

public class DebugModule extends AbstractPowerModule {
//    @javax.annotation.Nullable
//    @Override
//    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
//        return new CapProvider(stack);
//    }
//
//    public class CapProvider implements ICapabilityProvider {
//        ItemStack module;
//        private final RightClickie rightClickie;
//        private final LazyOptional<IPowerModule> powerModuleHolder;
//
//        public CapProvider(@Nonnull ItemStack module) {
//            this.module = module;
//            this.rightClickie = new RightClickie(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig);
//            this.powerModuleHolder = LazyOptional.of(() -> rightClickie);
//        }
//
//        class RightClickie extends RightClickModule {
//            public RightClickie(@NotNull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> moduleConfigGetterIn) {
//                super(module, category, target, moduleConfigGetterIn);
//            }
//
//            @Override
//            public InteractionResultHolder<ItemStack> use(@NotNull ItemStack itemStackIn, Level worldIn, Player playerIn, InteractionHand hand) {
////                playerIn.getCapability(NuminaCapabilities.PLAYER_HAND_STORAGE).ifPresent(cap -> {
////                    System.out.println("mainhand: " + cap.getMainHandStorage());
////                    System.out.println("offHand: " + cap.getOffHandStorage());
////                });
//
//
//
//
//
//
//                System.out.println("use");
///*
//slot#: 0, stack: powersuits:powerfist
//slot#: 1, stack: theoneprobe:probenote
//slot#: 2, stack: ae2:entropy_manipulator
//slot#: 3, stack: ae2:certus_quartz_wrench
//slot#: 4, stack: ae2:network_tool
//slot#: 5, stack: ae2:portable_item_cell_1k
//slot#: 6, stack: ae2:portable_item_cell_4k
//slot#: 7, stack: ae2:portable_item_cell_16k
//slot#: 8, stack: ae2:portable_item_cell_64k
//slot#: 9, stack: minecraft:air
//slot#: 10, stack: minecraft:air
//slot#: 11, stack: minecraft:air
//slot#: 12, stack: minecraft:air
//slot#: 13, stack: minecraft:air
//slot#: 14, stack: minecraft:air
//slot#: 15, stack: minecraft:air
//slot#: 16, stack: minecraft:air
//slot#: 17, stack: minecraft:air
//slot#: 18, stack: minecraft:air
//slot#: 19, stack: minecraft:air
//slot#: 20, stack: minecraft:air
//slot#: 21, stack: minecraft:air
//slot#: 22, stack: minecraft:air
//slot#: 23, stack: minecraft:air
//slot#: 24, stack: minecraft:air
//slot#: 25, stack: minecraft:air
//slot#: 26, stack: minecraft:air
//slot#: 27, stack: ae2:portable_fluid_cell_1k
//slot#: 28, stack: ae2:portable_fluid_cell_4k
//slot#: 29, stack: ae2:portable_fluid_cell_16k
//slot#: 30, stack: ae2:portable_fluid_cell_64k
//slot#: 31, stack: ae2:portable_fluid_cell_256k
//slot#: 32, stack: minecraft:air
//slot#: 33, stack: minecraft:air
//slot#: 34, stack: minecraft:air
//slot#: 35, stack: ae2:portable_item_cell_256k
//slot#: 36, stack: powersuits:powerarmor_feet
//slot#: 37, stack: powersuits:powerarmor_legs
//slot#: 38, stack: powersuits:powerarmor_torso
//slot#: 39, stack: powersuits:powerarmor_head
// */
//
//                for (int i = 0; i < playerIn.getInventory().getContainerSize(); i++) {
//                    ItemStack stack = playerIn.getInventory().getItem(i);
//                    System.out.println("slot#: " + i + ", stack: " + ForgeRegistries.ITEMS.getKey(stack.getItem()));
//                }
//                return super.use(itemStackIn, worldIn, playerIn, hand);
//            }
//
//            @Override
//            public InteractionResult useOn(UseOnContext context) {
//                System.out.println("useOn");
//
//                return super.useOn(context);
//            }
//
//            @Override
//            public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
//                System.out.println("onItemUseFirst");
//                return super.onItemUseFirst(stack, context);
//            }
//
//            @Override
//            public void releaseUsing(@NotNull ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
//                System.out.println("releaseUsing");
//                super.releaseUsing(stack, worldIn, entityLiving, timeLeft);
//            }
//
//            @Override
//            public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
//                System.out.println("finishUsing");
//                return super.finishUsingItem(itemStack, level, entity);
//            }
//        }
//
//        /**
//         * ICapabilityProvider -----------------------------------------------------------------------
//         */
//        @Override
//        @Nonnull
//        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
//            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
//            if (powerModuleCapability.isPresent()) {
//                return powerModuleCapability;
//            }
//            return LazyOptional.empty();
//        }
//    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand hand) {
//        for (int i = 0; i < playerIn.getInventory().getContainerSize(); i++) {
//            ItemStack stack = playerIn.getInventory().getItem(i);
//            System.out.println("slot#: " + i + ", stack: " + ForgeRegistries.ITEMS.getKey(stack.getItem()));
//        }
//        return super.use(level, playerIn, hand);
//    }
}
