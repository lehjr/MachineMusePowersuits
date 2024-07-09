package lehjr.powersuits.client.gui.module.select;

import lehjr.numina.client.gui.clickable.ClickableModule;
import lehjr.numina.client.gui.frame.AbstractGuiFrame;
import lehjr.numina.client.gui.geometry.*;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.serverbound.ModeChangeRequestPacketServerBound;
import lehjr.numina.common.utils.IconUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/*
TODO: revamp with a set of concentric circles to sort modules by category
 */
public class RadialModeSelectionFrame extends AbstractGuiFrame<DrawableTile> {
    boolean visible = true;
    boolean enabled = true;
    protected final long spawnTime;
    protected List<ClickableModule> modeButtons = new ArrayList<>();
    protected int selectedModuleOriginal = -1;
    protected int selectedModuleNew = -1;

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
        mcmi = player.getMainHandItem().getCapability(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM);
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
        if (mcmi != null && getSelectedModule() != null && selectedModuleOriginal != selectedModuleNew) {
            // update to detect mode changes
            selectedModuleOriginal = selectedModuleNew;
            NuminaCapabilities.getModeChangingModularItemCapability(player).ifPresent(handler-> NuminaPackets.sendToServer(new ModeChangeRequestPacketServerBound(getSelectedModule().getInventorySlot())));
        }
    }

    @Override
    public void render(GuiGraphics matrixStackIn, int mouseX, int mouseY, float partialTick) {
        NuminaLogger.logDebug("module list size: " + modeButtons);

        //Draw the installed power fist modes
        for (ClickableModule mode : modeButtons) {
            NuminaLogger.logDebug("rendering mode: " + mode.getModule());
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
                List<Integer> modes = mcmi.getValidModes();
                int activeMode = mcmi.getActiveMode();
                if (activeMode > 0)
                    selectedModuleOriginal = activeMode;
                int modeNum = 0;
                for (int mode : modes) {
                    ClickableModule clickie = new ClickableModule(mcmi.getStackInSlot(mode), new SpiralPointToPoint2D(center(), radius, ((3D * Math.PI / 2) - ((2D * Math.PI * modeNum) / modes.size())), 250D), mode, ModuleCategory.NONE);
                    NuminaLogger.logDebug("makign new clickie, " + clickie.getModule());

                    modeButtons.add(clickie);
                    modeNum ++;
                }
            }
        }
    }

    private void selectModule(double x, double y) {
        if (modeButtons != null) {
            for(int i =0; i<modeButtons.size(); i++ ) {
                if (modeButtons.get(i).containsPoint(x, y)) {
                    selectedModuleNew = i;
                    break;
                }
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