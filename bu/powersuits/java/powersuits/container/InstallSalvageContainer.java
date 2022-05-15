package lehjr.powersuits.container;

import com.mojang.blaze3d.matrix.PoseStack;
import lehjr.numina.basemod.NuminaObjects;
import lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.util.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.util.client.gui.slot.HideableSlotItemHandler;
import lehjr.numina.util.client.gui.slot.IHideableSlot;
import lehjr.numina.util.client.gui.slot.IIConProvider;
import lehjr.numina.util.client.render.MuseIconUtils;
import lehjr.numina.util.math.Color;
import lehjr.numina.util.math.MuseMathUtils;
import lehjr.powersuits.basemod.MPSObjects;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlot;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class InstallSalvageContainer  extends Container {
    EquipmentSlot slotType;
    int mainInventoryStart = 0;
    int hotbarInventoryStart = 0;

    public InstallSalvageContainer(int containerID, PlayerInventory playerInventory, EquipmentSlot slotType) {
        super(MPSObjects.INSTALL_SALVAGE_CONTAINER_TYPE.get(), containerID);
        this.slotType = slotType;

        int row, col;
        int parentSlot = slotType == EquipmentSlot.MAINHAND ? playerInventory.selected : equipmentSlotToParent(slotType);

        playerInventory.player.getItemBySlot(slotType).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .ifPresent(iItemHandler -> {
                    for (int modularItemInvIndex = 0; modularItemInvIndex < iItemHandler.getSlots(); modularItemInvIndex ++) {
                        if (MuseMathUtils.isIntInRange(iItemHandler.getRangeForCategory(ModuleCategory.ARMOR), modularItemInvIndex)) {
                            addSlot(new IconSlot(iItemHandler, parentSlot, modularItemInvIndex, -1000, -1000) {

                                @OnlyIn(Dist.CLIENT)
                                @Override
                                public com.mojang.datafixers.util.Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                                    return NuminaObjects.getSlotBackground(EquipmentSlot.OFFHAND);
                                }

                                @OnlyIn(Dist.CLIENT)
                                @Override
                                public void drawIconAt(PoseStack matrixStack, double v, double v1, Color colour) {

                                }
                            });
                        } else if (MuseMathUtils.isIntInRange(iItemHandler.getRangeForCategory(ModuleCategory.ENERGY_STORAGE), modularItemInvIndex)) {
                            addSlot(new IconSlot(iItemHandler, parentSlot, modularItemInvIndex, -1000, -1000) {
                                @OnlyIn(Dist.CLIENT)
                                @Override
                                public void drawIconAt(PoseStack matrixStack, double posX, double posY, Color colour) {
                                    MuseIconUtils.getIcon().energyStorageBackground.renderIconScaledWithColor(matrixStack, posX, posY, 16, 16, Color.WHITE);
                                }
                            });

                        } else if (MuseMathUtils.isIntInRange(iItemHandler.getRangeForCategory(ModuleCategory.ENERGY_GENERATION), modularItemInvIndex)) {
                            addSlot(new IconSlot(iItemHandler, parentSlot, modularItemInvIndex, -1000, -1000) {
                                @OnlyIn(Dist.CLIENT)
                                @Override
                                public void drawIconAt(PoseStack matrixStack, double posX, double posY, Color colour) {
                                    MuseIconUtils.getIcon().energyGenerationBackground.renderIconScaledWithColor(matrixStack, posX, posY, 16, 16, Color.WHITE);
                                }
                            });
                        } else {
                            addSlot(new HideableSlotItemHandler(iItemHandler, parentSlot, modularItemInvIndex, -1000, -1000));
                        }
                    }
                });

        mainInventoryStart = this.slots.size();

        /** Main inventory */
        for(row = 0; row < 3; ++row) {
            for(col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        hotbarInventoryStart = this.slots.size();

        /** Hotbar with pickup disabled for modular items */
        for(col = 0; col < 9; ++col) {
            if (col == playerInventory.selected &&
                    playerInventory.getItem(playerInventory.selected).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                            .filter(IModularItem.class::isInstance)
                            .map(IModularItem.class::cast)
                            .isPresent()) {
                this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142) {
                    @Override
                    public boolean mayPickup(Player player) {
                        return false;
                    }
                });
            } else {
                this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142)
                );
            }
        }

        for (Slot slot : this.slots) {
            if(slot instanceof IHideableSlot) {
                ((IHideableSlot) slot).disable();
            }
        }
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

