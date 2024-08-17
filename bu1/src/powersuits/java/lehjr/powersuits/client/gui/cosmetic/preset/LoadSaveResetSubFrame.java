//package lehjr.powersuits.client.gui.tinker.frame;
//
//import com.lehjr.numina.client.gui.frame.IGuiFrame;
//import net.minecraft.network.chat.Component;
//
//import java.util.List;
//
//public class LoadSaveResetSubFrame implements IGuiFrame {
//    @Override
//    public boolean mouseClicked(double v, double v1, int i) {
//        return false;
//    }
//
//    @Override
//    public boolean mouseReleased(double v, double v1, int i) {
//        return false;
//    }
//
//    @Override
//    public boolean onMouseScrolled(double v, double v1, double v2) {
//        return false;
//    }
//
//    @Override
//    public void update(double v, double v1) {
//
//    }
//
//    @Override
//    public void render(int i, int i1, float v) {
//
//    }
//
//    @Override
//    public List<Component> getToolTip(int i, int i1) {
//        return null;
//    }
////    Player player;
////    public ItemSelectionFrame itemSelector;
////    public DrawableRect border;
////    protected ClickableButton loadButton;
////    protected ClickableButton saveButton;
////    protected ClickableButton resetButton;
////    ColorPickerFrame colorpicker;
////    ScrollableLabel saveAsLabel;
////    final boolean usingCosmeticPresets;
////    final boolean allowCosmeticPresetCreation;
////
////    final double originalBottom;
////    final double originalTop;
////    final double originalHeight;
////    final double newHeight;
////    protected PartManipAbstractContainerMenu partframe;
////    protected CosmeticPresetAbstractContainerMenu cosmeticFrame;
////    protected boolean isEditing;
//////    protected Map<Integer, String> lastCosmeticPresets;
////
////    TextFieldWidget presetNameInputBox;
////
////    public LoadSaveResetSubFrame(ColorPickerFrame colorpicker, Player player, Rect borderRef, Color insideColor, Color borderColor, ItemSelectionFrame itemSelector, boolean usingCosmeticPresetsIn, boolean allowCosmeticPresetCreationIn, PartManipAbstractContainerMenu partframe, CosmeticPresetAbstractContainerMenu cosmeticFrame) {
////        this.player = player;
////        this.border = new DrawableRect(borderRef, insideColor, borderColor);
////        this.originalTop = border.top();
////        this.originalHeight = border.height();
////        this.originalBottom = border.bottom();
////        this.newHeight = (Math.abs(colorpicker.getBorder().top() - originalBottom));
////        double sizex = border.right() - border.left();
////        double sizey = border.bottom() - border.top();
////        this.itemSelector = itemSelector;
////        this.colorpicker = colorpicker;
////        this.saveAsLabel = new ScrollableLabel(I18n.format("gui.powersuits.saveAs"),  new RelativeRect(border.left(), colorpicker.getBorder().top() + 20, border.right(), colorpicker.getBorder().top() + 30)).setEnabled(false);
////        presetNameInputBox = new TextFieldWidget(0, Renderer.getFontRenderer(), (int) (border.left()) + 2, (int) (saveAsLabel.bottom() + 10), (int) border.width() - 4, 20);
////
////        this.loadButton = new ClickableButton(
////                I18n.format("gui.powersuits.load"),
////                new Point2F(border.left() + sizex * 2.5 / 12.0, border.bottom() - sizey / 2.0), true);
////        this.saveButton = new ClickableButton(
////                I18n.format("gui.powersuits.save"),
////                new Point2F(border.right() - sizex * 2.5 / 12.0, border.bottom() - sizey / 2.0), true);
////
////        this.resetButton = new ClickableButton(
////                I18n.format("gui.powersuits.reset"),
////                new Point2F(border.left() + sizex / 2.0, border.bottom() - sizey / 2.0), true);
////
////        textInputOff();
////        presetNameInputBox.setMaxStringLength((int) border.width());
////        presetNameInputBox.setText("");
////
////        this.usingCosmeticPresets = usingCosmeticPresetsIn;
////        this.allowCosmeticPresetCreation = allowCosmeticPresetCreationIn;
////
////        this.partframe = partframe;
////        this.cosmeticFrame = cosmeticFrame;
////        this.isEditing = false;
////
////        if (usingCosmeticPresets) {
////            if (allowCosmeticPresetCreation)
////                cosmeticPresetCreator ();
////            else
////                cosmeticPresetsNormal();
////        } else
////            setLegacyMode();
////    }
////
////    /**
////     * settings for the classic style cosmetic configuration
////     */
////    void setLegacyMode() {
////        saveButton.buttonOff();
////        loadButton.buttonOff();
////        showPartManipFrame();
////        colorPickerSetOpen();
////    }
////
////    /**
////     * settings for cosmetic preset mode (normal user)
////     */
////    void cosmeticPresetsNormal() {
////        saveButton.buttonOff();
////        loadButton.buttonOff();
////        colorpickerSetClosed();
////        textInputOff();
////        showPresetFrame();
////    }
////
////    /**
////     * settings for cosmetic preset mode (creator)
////     */
////    void cosmeticPresetCreator () {
////        if (isEditing) {
////            loadButton.buttonOn();
////            loadButton.setLable(I18n.format("gui.powersuits.cancel"));
////            saveButton.buttonOn();
////            saveButton.setLable(I18n.format("gui.powersuits.save"));
////            showPartManipFrame();
////            // save as dialog
////            if (presetNameInputBox.getVisible()) {
////                colorpickerSetClosed();
////            } else {
////                colorPickerSetOpen();
////            }
////        } else {
////            textInputOff();
////            showPresetFrame();
////            colorpickerSetClosed();
////            loadButton.buttonOff();
////            saveButton.buttonOn();
////            saveButton.setLable(I18n.format("gui.powersuits.new"));
////        }
////    }
////
////    void colorPickerSetOpen() {
////        this.border.setTop(originalTop).setHeight(originalHeight);
////        colorpicker.frameOn();
////        saveAsLabel.setEnabled(false);
////    }
////
////    void colorpickerSetClosed() {
////        this.border.setTop(colorpicker.getBorder().top()).setHeight(newHeight);
////        colorpicker.frameOff();
////    }
////
////    void textInputOn () {
////        presetNameInputBox.setEnabled(true);
////        presetNameInputBox.setVisible(true);
////        presetNameInputBox.setFocused(true);
////        saveAsLabel.setEnabled(true);
////    }
////
////    void textInputOff() {
////        presetNameInputBox.setEnabled(false);
////        presetNameInputBox.setVisible(false);
////        presetNameInputBox.setFocused(false);
////        saveAsLabel.setEnabled(false);
////    }
////
////    void showPresetFrame() {
////        cosmeticFrame.frameOn();
////        partframe.frameOff();
////    }
////
////    void showPartManipFrame() {
////        cosmeticFrame.frameOff();
////        partframe.frameOn();
////    }
////
////    /**
////     * Get's the equipment itemSlot the item is for.
////     */
////    public EquipmentSlot getEquipmentSlot() {
////        ItemStack selectedItem = getSelectedItem().getItem();
////        if (!selectedItem.isEmpty() && selectedItem.getItem() instanceof ItemPowerArmor)
////            return ((ItemPowerArmor) selectedItem.getItem()).getEquipmentSlot();
////
////        Minecraft minecraft = Minecraft.getInstance();
////        Player player = minecraft.player;
////        ItemStack heldItem = player.getHeldItemOffhand();
////
////        if (!heldItem.isEmpty() && ItemStack.areItemStacksEqual(selectedItem, heldItem))
////            return EquipmentSlot.OFFHAND;
////        return EquipmentSlot.MAINHAND;
////    }
////
////    void checkAndFixItem(ClickableItem clickie) {
////        if (clickie != null) {
////            ItemStack itemStack = clickie.getItem();
////            CompoundTag itemNBT = NBTUtils.getMuseItemTag(itemStack);
////            if (itemNBT.contains(NuminaConstants.RENDER,Constants.NBT.TAG_COMPOUND)) {
////                BiMap<String, CompoundTag> presetMap = MPSSettings::getModuleConfig.getCosmeticPresets(itemStack);
////                if (presetMap.containsValue(itemNBT.getCompound(NuminaConstants.RENDER))) {
////                    String name = presetMap.inverse().get(itemNBT.getCompound(NuminaConstants.RENDER));
////                    MPSPackets.sendToServer(new MusePacketCosmeticPreset(Minecraft.getInstance().player.getEntityId(), clickie.inventorySlot, name));
////                } else
////                    MPSPackets.sendToServer(new MusePacketCosmeticPreset(Minecraft.getInstance().player.getEntityId(), clickie.inventorySlot, "Default"));
////            }
////        }
////    }
////
////    /**
////     * This is just for resetting the cosmetic tag to a preset and is called when either
////     * switching to a new tab or when exiting the GUI altogether
////     */
////    public void onGuiClosed() {
//////        NuminaLogger.logDebug("creator gui closed and was editing: " + isEditing);
////        if (allowCosmeticPresetCreation && isEditing) {
////            for (ClickableItem clickie : itemSelector.itemButtons) {
////                checkAndFixItem(clickie);
////            }
////        }
////    }
////
////    @Override
////    public void update(double mousex, double mousey) {
////        if (usingCosmeticPresets ||
////                (!MPSSettings::getModuleConfig.COSMETIC_ALLOW_POWER_FIST_CUSTOMIZATOIN.get() &&
////                        itemSelector.getSelectedItem() != null && getSelectedItem().getItem().getItem() instanceof ItemPowerFist)) {
////            // normal preset user
////            if (allowCosmeticPresetCreation)
////                cosmeticPresetCreator();
////            else
////                cosmeticPresetsNormal();
////        } else
////            setLegacyMode();
////    }
////
////    CompoundTag getDefaultPreset(@Nonnull ItemStack itemStack) {
////        return MPSSettings::getModuleConfig.getPresetNBTFor(itemStack, "Default");
////    }
////
////    public boolean isValidItem(ClickableItem clickie, EquipmentSlot slot) {
////        if (clickie != null) {
////            if (clickie.getItem().getItem() instanceof ItemPowerArmor)
////                return clickie.getItem().getItem().canEquip(clickie.getItem(), slot, Minecraft.getInstance().player);
////            else if (clickie.getItem().getItem() instanceof ItemPowerFist && slot.getSlotType().equals(EquipmentSlot.Type.HAND))
////                return true;
////        }
////        return false;
////    }
////
////    public ClickableItem getSelectedItem() {
////        return this.itemSelector.getSelectedItem();
////    }
////
////    @Override
////    public void onMouseDown(double x, double y, int button) {
////        if (itemSelector.getSelectedItem() == null || itemSelector.getSelectedItem().getItem().isEmpty())
////            return;
////
////        if (usingCosmeticPresets ||
////                (!MPSSettings::getModuleConfig.COSMETIC_ALLOW_POWER_FIST_CUSTOMIZATOIN.get() &&
////                        getSelectedItem() != null && getSelectedItem().getItem().getItem() instanceof ItemPowerFist)) {
////            if (allowCosmeticPresetCreation) {
////                if (isEditing) {
////                    // todo: insert check for new item selected and save tag for previous item selected
////
//////                    if (itemSelector.getLastItemSlot() != -1 && itemSelector.selectedItemStack != itemSelector.getLastItemSlot()) {
//////
//////                        NuminaLogger.logDebug("previous item index: " + itemSelector.getSelectedItemSlot());
//////                        NuminaLogger.logDebug("current item index: " + itemSelector.getSelectedItemSlot());
//////
//////                        NuminaLogger.logDebug("this is where we would save the cosmetic preset tag for the previous item:");
//////                    }
////
////                    if (saveButton.hitBox(x, y)) {
////                        // save as dialog is open
////                        if (presetNameInputBox.getVisible()) {
////                            String name = presetNameInputBox.getText();
////                            ItemStack itemStack = getSelectedItem().getItem();
////
////                            boolean save = CosmeticPresetSaveLoad.savePreset(name, itemStack);
////                            if (save) {
////                                if (isValidItem(getSelectedItem(), getEquipmentSlot())) {
////                                    // get the render tag for the item
////                                    CompoundTag nbt = MPSModelHelper.getRenderTag(itemStack).copy();
////                                    MPSPackets.sendToServer(new MusePacketCosmeticPresetUpdate(itemStack.getItem().getRegistryName(), name, nbt));
////                                }
////                            }
////                        } else {
////                            // enabling here opens save dialog in update loop
////                            textInputOn();
////                        }
////
////                        // reset tag to cosmetic copy of cosmetic preset default as legacy tag for editing.
////                    } else if (resetButton.hitBox(x, y)) {
////                        if (isValidItem(getSelectedItem(), getEquipmentSlot())) {
////                            CompoundTag nbt = getDefaultPreset(itemSelector.getSelectedItem().getItem());
////                            MPSPackets.sendToServer(new MusePacketCosmeticInfo(player.getEntityId(), itemSelector.getSelectedItem().inventorySlot, NuminaConstants.RENDER, nbt));
////                        }
////                        // cancel creation
////                    } else if (loadButton.hitBox(x, y)) {
////                        if (isValidItem(getSelectedItem(), getEquipmentSlot())) {
////                            MPSPackets.sendToServer(new MusePacketCosmeticPreset(Minecraft.getInstance().player.getEntityId(), this.getSelectedItem().inventorySlot, "Default"));
////                        }
////                        isEditing = false;
////                    }
////                } else {
////                    if (saveButton.hitBox(x, y)) {
////                        if (isValidItem(getSelectedItem(), getEquipmentSlot())) {
////                            isEditing = true;
////                            CompoundTag nbt = MPSModelHelper.getRenderTag(getSelectedItem().getItem(), getEquipmentSlot());
////                            MPSPackets.sendToServer(new MusePacketCosmeticInfo(Minecraft.getInstance().player.getEntityId(), this.getSelectedItem().inventorySlot, NuminaConstants.RENDER, nbt));
////                        }
////                    } else if (resetButton.hitBox(x, y)) {
////                        if (isValidItem(getSelectedItem(), getEquipmentSlot())) {
////                            MPSPackets.sendToServer(new MusePacketCosmeticPreset(Minecraft.getInstance().player.getEntityId(), this.getSelectedItem().inventorySlot, "Default"));
////                        }
////                    }
////                }
////            } else {
////                if (resetButton.hitBox(x, y)) {
////                    if (isValidItem(getSelectedItem(), getEquipmentSlot())) {
////                        MPSPackets.sendToServer(new MusePacketCosmeticPreset(Minecraft.getInstance().player.getEntityId(), this.getSelectedItem().inventorySlot, "Default"));
////                    }
////                }
////            }
////            // legacy mode
////        } else {
////            if (resetButton.hitBox(x, y)) {
////                if (isValidItem(getSelectedItem(), getEquipmentSlot())) {
////                    CompoundTag nbt = DefaultModelSpec.makeModelPrefs(itemSelector.getSelectedItem().getItem());
////                    MPSPackets.sendToServer(new MusePacketCosmeticInfo(player.getEntityId(), itemSelector.getSelectedItem().inventorySlot, NuminaConstants.RENDER, nbt));
////                }
////            }
////        }
////    }
////
////    @Override
////    public void onMouseUp(double x, double y, int button) {
////
////    }
////
////    @Override
////    public void render(int mouseX, int mouseY, float partialTicks) {
////        border.draw();
////        loadButton.render(mouseX, mouseY, partialTicks);
////        saveButton.render(mouseX, mouseY, partialTicks);
////        resetButton.render(mouseX, mouseY, partialTicks);
////        saveAsLabel.render(mouseX, mouseY, partialTicks);
////        presetNameInputBox.drawTextField(mouseX, mouseY, partialTicks);
////    }
////
////    private static boolean isValidCharacterForName(char typedChar, int keyCode) {
////        if (keyCode == 14 || // backspace;
////                keyCode == 12 || // - ; 147 is _; 52 is .
////                keyCode == 147 || //
////                Character.isDigit(typedChar) ||
////                Character.isLetter(typedChar ) ||
////                Character.isSpaceChar(typedChar))
////            return true;
////        return false;
////    }
////
////    @Override
////    public List<Component> getToolTip(int x, int y) {
////        return null;
////    }
////
////    public void charTyped(char typedChar, int keyCode) {
////        if (this.presetNameInputBox.getVisible() && isValidCharacterForName(typedChar, keyCode)) {
////            this.presetNameInputBox.charTyped(typedChar, keyCode);
////        }
////    }
//}