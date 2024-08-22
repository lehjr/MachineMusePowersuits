package com.lehjr.powersuits.client.gui.module.select;

import com.lehjr.numina.client.gui.clickable.ClickableModule;
import com.lehjr.numina.client.gui.frame.AbstractGuiFrame;
import com.lehjr.numina.client.gui.geometry.*;
import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.network.NuminaPackets;
import com.lehjr.numina.common.network.packets.serverbound.ModeChangeRequestPacketServerBound;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.IconUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;


/*
TODO: revamp with a set of concentric circles to sort modules by category
 */
public class RadialModeSelectionFrame extends AbstractGuiFrame<DrawableTile> {
    boolean visible = true;
    boolean enabled = true;
    protected final long spawnTime;
    protected List<ClickableModule> modeButtons = new ArrayList<>();
    ClickableModule selectedModule = null;
    ClickableModule selectedModuleNew = null;

    protected Player player;
    protected double radius;
    float zLevel;
    SwirlyMuseCircle circle;
    IModeChangingItem mcmi;

    public RadialModeSelectionFrame(MusePoint2D topLeft, MusePoint2D bottomRight, Player player, float zLevel) {
        super(new DrawableTile(topLeft, bottomRight));
        spawnTime = System.currentTimeMillis();
        this.player = player;
        this.radius = Math.min(width(), height());
        this.zLevel = zLevel;
        mcmi = NuminaCapabilities.getModeChangingModularItem(player.getMainHandItem());
        loadItems();
        circle = new SwirlyMuseCircle();
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
        if (mcmi != null && selectedModuleNew != null) {
            // update to detect mode changes
            selectedModule = selectedModuleNew;
            NuminaLogger.logDebug("should be setting new mode: " + getSelectedModule().getInventorySlot());
            NuminaPackets.sendToServer(new ModeChangeRequestPacketServerBound(getSelectedModule().getInventorySlot()));
            selectedModuleNew = null;
        }
    }

    @Override
    public void render(GuiGraphics matrixStackIn, int mouseX, int mouseY, float partialTick) {
        //Draw the installed power fist modes
        for (ClickableModule mode : modeButtons) {
            mode.render(matrixStackIn, mouseX, mouseY, partialTick);
        }
        //Draw the selected mode indicator
        drawSelection(matrixStackIn);
    }

    @Override
    public float getZLevel() {
        return 0;
    }

    @Override
    public IDrawable setZLevel(float v) {
        return this;
    }

    private void loadItems() {
        if (player != null && modeButtons.isEmpty()) {
            if (mcmi != null) {
                int activeMode = mcmi.getActiveMode();

                List<Integer> modes = mcmi.getValidModes();

                int modeNum = 0;
                for (int mode : modes) {
                    ClickableModule clickie = new ClickableModule(mcmi.getStackInSlot(mode), new SpiralPointToPoint2D(center(), radius, ((3D * Math.PI / 2) - ((2D * Math.PI * modeNum) / modes.size())), 250D), mode, ModuleCategory.NONE);
                    modeButtons.add(clickie);
                    if (mode == activeMode) {
                        selectedModule = clickie;
                    }
                    modeNum ++;
                }
            }
        }
    }

    private void selectModule(double x, double y) {
        if (modeButtons != null) {
            for (ClickableModule clickie: modeButtons) {
                if(clickie.containsPoint(x, y) && clickie != selectedModule) {
                    selectedModuleNew = clickie;
                    break;
                }
            }
        }
    }

    public ClickableModule getSelectedModule() {
        return selectedModule;
    }

    public void drawSelection(GuiGraphics gfx) {
        ClickableModule module = getSelectedModule();
        if (module != null) {
            MusePoint2D pos = module.center();
            IconUtils.drawCircleAround(circle, gfx.pose(), pos.x(), pos.y(), 10, zLevel);
        }
    }

    @Override
    public List<Component> getToolTip(int x, int y) {
        ClickableModule module = getSelectedModule();
        if (module != null) {
            return module.getToolTip(x, y);
        }
        return null;
    }

    @Override
    public void setEnabled(boolean b) {
        enabled = b;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setVisible(boolean b) {
        visible = b;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }
}