package net.machinemuse.powersuits.client.gui.module.tinker;

import net.machinemuse.numina.client.gui.clickable.ClickableButton;
import net.machinemuse.numina.client.gui.clickable.ClickableItem;
import net.machinemuse.numina.client.gui.geometry.DrawableMuseRect;
import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.numina.client.gui.scrollable.ScrollableFrame;
import net.machinemuse.numina.client.render.MuseRenderer;
import net.machinemuse.numina.client.sound.Musique;
import net.machinemuse.numina.common.item.MuseItemUtils;
import net.machinemuse.numina.common.math.Colour;
import net.machinemuse.numina.common.module.IPowerModule;
import net.machinemuse.powersuits.client.gui.common.ClickableModule;
import net.machinemuse.powersuits.client.gui.common.ItemSelectionFrame;
import net.machinemuse.powersuits.client.sound.SoundDictionary;
import net.machinemuse.powersuits.common.base.ModuleManager;
import net.machinemuse.powersuits.common.config.MPSConfig;
import net.machinemuse.powersuits.common.network.MPSPackets;
import net.machinemuse.powersuits.common.network.packets.MusePacketInstallModuleRequest;
import net.machinemuse.powersuits.common.network.packets.MusePacketSalvageModuleRequest;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class InstallSalvageFrame extends ScrollableFrame {
    protected ItemSelectionFrame targetItem;
    protected ModuleSelectionFrame targetModule;
    protected ClickableButton installButton;
    protected ClickableButton salvageButton;
    protected EntityPlayerSP player;

    public InstallSalvageFrame(EntityPlayerSP player, MusePoint2D topleft,
                               MusePoint2D bottomright,
                               Colour borderColour, Colour insideColour,
                               ItemSelectionFrame targetItem, ModuleSelectionFrame targetModule) {
        super(new DrawableMuseRect(topleft, bottomright, borderColour, insideColour));
        this.player = player;
        this.targetItem = targetItem;
        this.targetModule = targetModule;
        double sizex = right() - left();
        double sizey = bottom() - top();

        this.installButton = new ClickableButton(I18n.format("gui.powersuits.install"), new MusePoint2D(
                right() - sizex / 2.0, bottom() - sizey
                / 4.0),
                true);
        this.salvageButton = new ClickableButton(I18n.format("gui.powersuits.salvage"), new MusePoint2D(
                left() + sizex / 2.0, top() + sizey / 4.0),
                true);

    }

    @Override
    public void update(double mouseX, double mouseY) {
//        if (targetItem.getSelectedItem() != null && targetModule.getSelectedModule() != null)
//            System.out.println("update method would run here");


        // TODO: update the install/uninstall button when a module is installed
    }

    @Override
    public List<String> getToolTip(double mouseX, double mouseY) {
        if (targetItem.getSelectedItem() != null
                && targetModule.getSelectedModule() != null) {
            ItemStack stack = targetItem.getSelectedItem().getItem();
            IPowerModule module = targetModule.getSelectedModule().getModule();
            NonNullList<ItemStack> itemsToCheck = ModuleManager.INSTANCE.getInstallCost(module.getDataName());
            double yoffset;
            if (!ModuleManager.INSTANCE.itemHasModule(stack, module.getDataName())) {
                yoffset = top() + 4;
            } else {
                yoffset = bottom() - 20;
            }
            if (yoffset + 16 > mouseY && yoffset < mouseY) {
                double xoffset = -8.0 * itemsToCheck.size()
                        + (left() + right()) / 2;
                if (xoffset + 16 * itemsToCheck.size() > mouseX && xoffset < mouseX) {
                    int index = (int) (mouseX - xoffset) / 16;
                    return itemsToCheck.get(index).getTooltip(player, ITooltipFlag.TooltipFlags.NORMAL);
                }
            }
        }
        return null;
    }

    @Override
    public void render(double mouseX, double mouseY, float partialTicks) {
        if (targetItem.getSelectedItem() != null
                && targetModule.getSelectedModule() != null) {
            drawBackground(mouseX, mouseY, partialTicks);
            drawItems(mouseX, mouseY, partialTicks);
            drawButtons(mouseX, mouseY, partialTicks);
        }
    }

    private void drawBackground(double mouseX, double mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
    }

    private void drawItems(double mouseX, double mouseY, float partialTicks) {
        ItemStack stack = targetItem.getSelectedItem().getItem();
        IPowerModule module = targetModule.getSelectedModule().getModule();
        NonNullList<ItemStack> itemsToDraw = ModuleManager.INSTANCE.getInstallCost(module.getDataName());
        double yoffset;
        if (!ModuleManager.INSTANCE.itemHasModule(stack, module.getDataName())) {
            yoffset = top() + 4;
        } else {
            yoffset = bottom() - 20;
        }
        double xoffset = -8.0 * itemsToDraw.size()
                + (left() + right()) / 2;
        int i = 0;
        for (ItemStack costItem : itemsToDraw) {
            MuseRenderer.drawItemAt(
                    16 * i++ + xoffset,
                    yoffset,
                    costItem);
        }
    }

    private void drawButtons(double mouseX, double mouseY, float partialTicks) {
        ItemStack stack = targetItem.getSelectedItem().getItem();
        IPowerModule module = targetModule.getSelectedModule().getModule();
        if (!ModuleManager.INSTANCE.itemHasModule(stack, module.getDataName())) {
            int installedModulesOfType = ModuleManager.INSTANCE.getNumberInstalledModulesOfType(stack, module.getCategory());
            installButton.setEnabled(player.capabilities.isCreativeMode ||
                    (MuseItemUtils.hasInInventory(ModuleManager.INSTANCE.getInstallCost(module.getDataName()), player.inventory) &&
                    installedModulesOfType < MPSConfig.INSTANCE.getMaxModulesOfType(module.getCategory())));
            installButton.render(mouseX, mouseY, partialTicks);
        } else {
            salvageButton.render(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        ClickableItem selItem = targetItem.getSelectedItem();
        ClickableModule selModule = targetModule.getSelectedModule();
        if (selItem != null && selModule != null) {
            ItemStack stack = selItem.getItem();
            IPowerModule module = selModule.getModule();

            if (!ModuleManager.INSTANCE.itemHasModule(stack, module.getDataName())) {
                if (installButton.containsPoint(mouseX, mouseY)) {
                    doInstall();
                    return true;
                }
            } else {
                if (salvageButton.containsPoint(mouseX, mouseY)) {
                    doSalvage();
                    return true;
                }
            }
        }
        return false;
    }

    private void doSalvage() {
        IPowerModule module = targetModule.getSelectedModule().getModule();
        MPSPackets.sendToServer(new MusePacketSalvageModuleRequest(
                player,
                targetItem.getSelectedItem().inventorySlot,
                module.getDataName()));
    }

    /**
     * Performs all the functions associated with the install button. This
     * requires communicating with the server.
     */
    private void doInstall() {
        IPowerModule module = targetModule.getSelectedModule().getModule();
        if (player.capabilities.isCreativeMode || MuseItemUtils.hasInInventory(ModuleManager.INSTANCE.getInstallCost(module.getDataName()), player.inventory)) {
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_INSTALL, SoundCategory.BLOCKS, 1, null);
            // Now send request to server to make it legit
            MPSPackets.sendToServer(new MusePacketInstallModuleRequest(
                    player,
                    targetItem.getSelectedItem().inventorySlot,
                    module.getDataName()));
        }
    }
}