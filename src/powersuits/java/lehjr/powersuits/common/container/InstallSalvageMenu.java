package lehjr.powersuits.common.container;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.slot.HideableSlotItemHandler;
import lehjr.numina.client.gui.slot.IIConProvider;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.math.MathUtils;
import lehjr.powersuits.common.base.MPSMenuTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

public class InstallSalvageMenu extends AbstractContainerMenu {
    EquipmentSlot slotType;
    int mainInventoryStart = 0;
    int hotbarInventoryStart = 0;

    public InstallSalvageMenu(int containerID, Inventory playerInventory, EquipmentSlot slotType) {
        super(MPSMenuTypes.INSTALL_SALVAGE_MENU_TYPE.get(), containerID);
        this.slotType = slotType;

        int row, col;
        int parentSlot = slotType == EquipmentSlot.MAINHAND ? playerInventory.selected : equipmentSlotToParent(slotType);

        playerInventory.player.getItemBySlot(slotType).getCapability(ForgeCapabilities.ITEM_HANDLER)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .ifPresent(iItemHandler -> {
                    int innerrow = 0;
                    int innercol = 0;
                    for (int modularItemInvIndex = 0; modularItemInvIndex < iItemHandler.getSlots(); modularItemInvIndex ++) {
                        innercol = modularItemInvIndex % 16;
                        innerrow = (modularItemInvIndex - innercol)/16;

                        if (MathUtils.isIntInRange(iItemHandler.getRangeForCategory(ModuleCategory.ARMOR), modularItemInvIndex)) {
                            addSlot(new IconSlot(iItemHandler, parentSlot, modularItemInvIndex, 178 + innercol * 18, 14 + innerrow * 18) {

                                @OnlyIn(Dist.CLIENT)
                                @Override
                                public com.mojang.datafixers.util.Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                                    return IconUtils.getSlotBackground(EquipmentSlot.OFFHAND);
                                }

                                @OnlyIn(Dist.CLIENT)
                                @Override
                                public void drawIconAt(PoseStack matrixStack, double v, double v1, Color color) {

                                }
                            });
                        } else if (MathUtils.isIntInRange(iItemHandler.getRangeForCategory(ModuleCategory.ENERGY_STORAGE), modularItemInvIndex)) {
                            addSlot(new IconSlot(iItemHandler, parentSlot, modularItemInvIndex, 178 + innercol * 18, 14 + innerrow * 18) {
                                @OnlyIn(Dist.CLIENT)
                                @Override
                                public void drawIconAt(PoseStack matrixStack, double posX, double posY, Color color) {
                                    IconUtils.getIcon().energystorage.renderIconScaledWithColor(matrixStack, posX, posY, 16, 16, Color.WHITE);
                                }
                            });

                        } else if (MathUtils.isIntInRange(iItemHandler.getRangeForCategory(ModuleCategory.ENERGY_GENERATION), modularItemInvIndex)) {
                            addSlot(new IconSlot(iItemHandler, parentSlot, modularItemInvIndex, 178 + innercol * 18, -1000) {
                                @OnlyIn(Dist.CLIENT)
                                @Override
                                public void drawIconAt(PoseStack matrixStack, double posX, double posY, Color color) {
                                    IconUtils.getIcon().energygeneration.renderIconScaledWithColor(matrixStack, posX, posY, 16, 16, Color.WHITE);
                                }
                            });
                        } else {
                            addSlot(new HideableSlotItemHandler(iItemHandler, parentSlot, modularItemInvIndex, 178 + innercol * 18, 14 + innerrow * 18));
                        }
                    }
                });

        mainInventoryStart = this.slots.size();

        /** Main inventory */
        for(row = 0; row < 3; ++row) {
            for(col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 178 + col * 18, 133 + row * 18));
            }
        }

        hotbarInventoryStart = this.slots.size();

        /** Hotbar with pickup disabled for modular items */
        for(col = 0; col < 9; ++col) {
            if (col == playerInventory.selected &&
                    playerInventory.getItem(playerInventory.selected).getCapability(ForgeCapabilities.ITEM_HANDLER)
                            .filter(IModularItem.class::isInstance)
                            .map(IModularItem.class::cast)
                            .isPresent()) {
                this.addSlot(new Slot(playerInventory, col, 178 + col * 18, 191) {
                    @Override
                    public boolean mayPickup(Player player) {
                        return false;
                    }
                });
            } else {
                this.addSlot(new Slot(playerInventory, col, 178 + col * 18, 191));
            }
        }

//        for (Slot slot : this.slots) {
//            if(slot instanceof IHideableSlot) {
//                ((IHideableSlot) slot).disable();
//            }
//        }
    }

    public EquipmentSlot getEquipmentSlot() {
        return slotType;
    }

    int equipmentSlotToParent(EquipmentSlot slotType) {
        switch (slotType) {
            case HEAD:
                return 39;
            case CHEST:
                return 38;
            case LEGS:
                return 37;
            case FEET:
                return 36;
             case OFFHAND:
                return 40;
            default:
                return  -1;
        }
    }

    public int getMainInventoryStart() {
        return mainInventoryStart;
    }

    public int getHotbarInventoryStart() {
        return hotbarInventoryStart;
    }

    // InventoryMenu version
    public void removed(Player playerEntity) {
        super.removed(playerEntity);
    }

    // player container version
    public boolean stillValid(Player playerEntity) {
        return true;
    }


    /**
     * Only handles shift clicking
     * @param player
     * @param index
     * @return
     */
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < mainInventoryStart) {
                if (!this.moveItemStackTo(itemstack1, mainInventoryStart, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, mainInventoryStart, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    abstract class IconSlot extends HideableSlotItemHandler implements IIConProvider {
        public IconSlot(IItemHandler itemHandler, int parent, int index, int xPosition, int yPosition) {
            super(itemHandler, parent, index, xPosition, yPosition);
        }

        public IconSlot(IItemHandler itemHandler, int parent, int index, int xPosition, int yPosition, boolean isEnabled) {
            super(itemHandler, parent, index, xPosition, yPosition, isEnabled);
        }
    }
}

