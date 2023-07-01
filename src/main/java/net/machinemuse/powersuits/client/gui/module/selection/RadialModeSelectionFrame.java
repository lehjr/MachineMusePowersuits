package net.machinemuse.powersuits.client.gui.module.selection;

import net.machinemuse.numina.client.gui.frame.IGuiFrame;
import net.machinemuse.numina.client.gui.geometry.IRect;
import net.machinemuse.numina.client.gui.geometry.MuseRect;
import net.machinemuse.numina.client.render.MuseRenderer;
import net.machinemuse.numina.common.item.IModeChangingItem;
import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.numina.client.gui.geometry.SpiralPointToPoint2D;
import net.machinemuse.numina.common.module.IPowerModule;
import net.machinemuse.numina.common.module.IRightClickModule;
import net.machinemuse.numina.common.network.packets.MusePacketModeChangeRequest;
import net.machinemuse.powersuits.common.base.ModuleManager;
import net.machinemuse.powersuits.client.gui.common.ClickableModule;
import net.machinemuse.powersuits.common.network.MPSPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RadialModeSelectionFrame<T extends IRect> implements IGuiFrame {
    protected final long spawnTime;
    protected List<ClickableModule> modeButtons = new ArrayList<>();
    protected int selectedModuleOriginal = -1;
    protected int selectedModuleNew = -1;

    protected EntityPlayer player;
    protected double radius;
    protected ItemStack stack;
    T border;


    public RadialModeSelectionFrame(MusePoint2D topleft, MusePoint2D bottomright, EntityPlayer player) {
        spawnTime = System.currentTimeMillis();
        this.player = player;
        setRect(new MuseRect(topleft, bottomright));
        this.radius = Math.min(border.center().minus(topleft).getX(), border.center().minus(topleft).getY());
        this.stack = player.inventory.getCurrentItem();
        loadItems();
        //Determine which mode is currently active
        if (!stack.isEmpty() && stack.getItem() instanceof IModeChangingItem) {
            if (modeButtons != null) {
                int i = 0;
                for (ClickableModule mode : modeButtons) {
                    if (mode.getModule().getDataName().equals(((IModeChangingItem) stack.getItem()).getActiveMode(stack))) {
                        selectedModuleOriginal = i;
                        break;
                    }
                    i++;
                }
            }
        }
    }

    @Override
    public IRect getRect() {
        return border;
    }

    @Override
    public void setRect(IRect rect) {
        this.border = (T)rect;
    }

    public RadialModeSelectionFrame() {
        spawnTime = System.currentTimeMillis();
    }

    private boolean alreadyAdded(IRightClickModule module) {
        for (ClickableModule clickie : modeButtons) {
            if (clickie.getModule().getDataName().equals(module.getDataName())) {
                return true;
            }
        }
        return false;
    }

    private void loadItems() {
        if (player != null) {
            List<IRightClickModule> modes = new ArrayList<>();
            for (IPowerModule module : ModuleManager.INSTANCE.getModulesOfType(IRightClickModule.class)) {
                if (ModuleManager.INSTANCE.isValidForItem(stack, module))
                    if (ModuleManager.INSTANCE.itemHasModule(stack, module.getDataName()))
                        modes.add((IRightClickModule) module);
            }

            int modeNum = 0;
            for (IRightClickModule module : modes) {
                if (!alreadyAdded(module)) {
                    ClickableModule clickie = new ClickableModule(module, new SpiralPointToPoint2D(border.center(), radius, (3 * Math.PI / 2) - ((2 * Math.PI * modeNum) / modes.size()), 250));
                    modeButtons.add(clickie);
                    modeNum++;
                }
            }
        }
    }

    private void selectModule(double x, double y) {
        if (modeButtons != null) {
            int i = 0;
            for (ClickableModule module : modeButtons) {
                if (module.containsPoint(x, y)) {
                    selectedModuleNew = i;
                    break;
                }
                i++;
            }
        }
    }

    public ClickableModule getSelectedModule() {
        if (modeButtons.size() > selectedModuleNew && selectedModuleNew != -1) {
            return modeButtons.get(selectedModuleNew);
        } else {
            return null;
        }
    }

    @Override
    public void update(double mouseX, double mouseY) {
        //Update items
        loadItems();
        //Determine which mode is selected
        if (System.currentTimeMillis() - spawnTime > 250) {
            selectModule(mouseX, mouseY);
        }
        //Switch to selected mode if mode changed
        if (getSelectedModule() != null && selectedModuleOriginal != selectedModuleNew && !stack.isEmpty() && stack.getItem() instanceof IModeChangingItem) {
            // update to detect mode changes
            selectedModuleOriginal = selectedModuleNew;
            ((IModeChangingItem) stack.getItem()).setActiveMode(stack, getSelectedModule().getModule().getDataName());
            MPSPackets.sendToServer(new MusePacketModeChangeRequest(player, getSelectedModule().getModule().getDataName(), player.inventory.currentItem));
        }
    }

    public void drawSelection() {
        ClickableModule module = getSelectedModule();
        if (module != null) {
            MusePoint2D pos = module.getPosition();
            MuseRenderer.drawCircleAround(pos.getX(), pos.getY(), 10);
        }
    }

    @Override
    public void render(double mouseX, double mouseY, float partialTicks) {
        //Draw the installed power fist modes
        for (ClickableModule mode : modeButtons) {
            mode.render(mouseX, mouseY, partialTicks);
        }
        //Draw the selected mode indicator
        drawSelection();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public List<String> getToolTip(double mouseX, double mouseY) {
        ClickableModule module = getSelectedModule();
        if (module != null) {
            IPowerModule selectedModule = module.getModule();
            return Collections.singletonList(module.getLocalizedName(selectedModule));
        }
        return null;
    }
}
