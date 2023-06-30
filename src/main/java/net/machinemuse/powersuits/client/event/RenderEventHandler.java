package net.machinemuse.powersuits.client.event;

import com.google.common.util.concurrent.AtomicDouble;
import net.machinemuse.numina.client.render.MuseIconUtils;
import net.machinemuse.numina.client.render.MuseRenderer;
import net.machinemuse.numina.client.render.MuseTextureUtils;
import net.machinemuse.numina.common.item.IModularItem;
import net.machinemuse.numina.common.math.Colour;
import net.machinemuse.numina.client.gui.geometry.DrawableMuseRect;
import net.machinemuse.numina.common.module.IPowerModule;
import net.machinemuse.powersuits.client.control.KeybindManager;
import net.machinemuse.powersuits.client.control.MPSKeyBinding;
import net.machinemuse.powersuits.common.config.MPSSettings;
import net.machinemuse.powersuits.common.constants.MPSModuleConstants;
import net.machinemuse.powersuits.client.model.helper.ModelHelper;
import net.machinemuse.powersuits.common.base.ModuleManager;
import net.machinemuse.powersuits.common.config.MPSConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Ported to Java by lehjr on 10/24/16.
 */
public class RenderEventHandler {
    private static final MPSConfig config = MPSConfig.INSTANCE;
    private static boolean ownFly;
    private final DrawableMuseRect frame = new DrawableMuseRect(config.keybindHUDx(), config.keybindHUDy(), config.keybindHUDx() + (double) 16, config.keybindHUDy() + (double) 16, true, Colour.DARKGREEN.withAlpha(0.2), Colour.GREEN.withAlpha(0.2));

    public RenderEventHandler() {
        this.ownFly = false;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void preTextureStitch(TextureStitchEvent.Pre event) {
        if (event.getMap().equals( Minecraft.getMinecraft().getTextureMapBlocks())) {
            MuseIcon.registerIcons(event.getMap());
            ModelHelper.loadArmorModels(event.getMap());
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Post event) {

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderLast(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution screen = new ScaledResolution(mc);
    }

    @SubscribeEvent
    public void onPreRenderPlayer(RenderPlayerEvent.Pre event) {
        if (!event.getEntityPlayer().capabilities.isFlying && !event.getEntityPlayer().onGround && this.playerHasFlightOn(event.getEntityPlayer())) {
            event.getEntityPlayer().capabilities.isFlying = true;
            RenderEventHandler.ownFly = true;
        }
    }

    private boolean playerHasFlightOn(EntityPlayer player) {
        return ModuleManager.INSTANCE.itemHasActiveModule(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST), MPSModuleConstants.MODULE_JETPACK__DATANAME) ||
                ModuleManager.INSTANCE.itemHasActiveModule(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST), MPSModuleConstants.MODULE_GLIDER__DATANAME) ||
                ModuleManager.INSTANCE.itemHasActiveModule(player.getItemStackFromSlot(EntityEquipmentSlot.FEET), MPSModuleConstants.MODULE_JETBOOTS__DATANAME) ||
                ModuleManager.INSTANCE.itemHasActiveModule(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD), MPSModuleConstants.MODULE_FLIGHT_CONTROL__DATANAME);
    }

    @SubscribeEvent
    public void onPostRenderPlayer(RenderPlayerEvent.Post event) {
        if (RenderEventHandler.ownFly) {
            RenderEventHandler.ownFly = false;
            event.getEntityPlayer().capabilities.isFlying = false;
        }
    }

    @SubscribeEvent
    public void onFOVUpdate(FOVUpdateEvent e) {
        ItemStack helmet = e.getEntity().getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        if (ModuleManager.INSTANCE.itemHasActiveModule(helmet, MPSModuleConstants.BINOCULARS_MODULE__DATANAME)) {
            e.setNewfov(e.getNewfov() / (float) ModuleManager.INSTANCE.getOrSetModularPropertyDouble(helmet, MPSModuleConstants.FOV));
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPostRenderGameOverlayEvent(RenderGameOverlayEvent.Post e) {
        RenderGameOverlayEvent.ElementType elementType = e.getType();
        if (RenderGameOverlayEvent.ElementType.HOTBAR.equals(elementType)) {
            this.drawKeybindToggles();
        }
    }

    static List<KBDisplay> kbDisplayList = new ArrayList<>();

    @SideOnly(Side.CLIENT)
    public void drawKeybindToggles() {
        if (config.keybindHUDon()) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayerSP player = mc.player;
            ScaledResolution screen = new ScaledResolution(mc);
            if (!Arrays.stream(EntityEquipmentSlot.values()).filter(slot -> player.getItemStackFromSlot(slot).getItem() instanceof IModularItem).collect(Collectors.toList()).isEmpty()) {
                AtomicDouble top = new AtomicDouble(MPSSettings.hud.keybindHUDy);
                kbDisplayList.forEach(kbDisplay -> {
                    if (!kbDisplay.boundKeybinds.isEmpty()) {
                        kbDisplay.setLeft(MPSSettings.hud.keybindHUDx);
                        kbDisplay.setTop(top.get());
                        kbDisplay.setBottom(top.get() + 16);
                        kbDisplay.render(0, 0, mc.getRenderPartialTicks());
                        top.getAndAdd(18);
                    }
                });
            }



//            for (MPSKeyBinding kb : KeybindManager.getKeybindings()) {
//                if (kb.showOnHud) {
//                    double stringwidth = MuseRenderer.getStringWidth(kb.getLabel());
//                    frame.setWidth(stringwidth + kb.getBoundModules().size() * 16);
//                    frame.draw();
//                    MuseRenderer.drawString(kb.getLabel(), frame.left() + 1, frame.top() + 3, (kb.toggleval) ? Colour.RED : Colour.GREEN);
//                    double x = frame.left() + stringwidth;
//                    for (ClickableModule module : kb.getBoundModules()) {
//                        MuseTextureUtils.pushTexture(MuseTextureUtils.TEXTURE_QUILT);
//                        boolean active = false;
//                        for (ItemStack stack : MuseItemUtils.getModularItemsEquipped(player)) {
//                            if (ModuleManager.INSTANCE.itemHasActiveModule(stack, module.getModule().getDataName()))
//                                active = true;
//                        }
//
//                        MuseIconUtils.drawIconAt(x, frame.top(), module.getModule().getIcon(null), (active) ? Colour.WHITE : Colour.DARKGREY.withAlpha(0.5));
//                        MuseTextureUtils.popTexture();
//                        x += 16;
//                    }
//                    frame.setTop(frame.top() + 16);
//                    frame.setBottom(frame.top() + 16);
//                }
//            }
            }
        }

        public static void makeKBDisplayList() {
            kbDisplayList.clear();
            KeybindManager.INSTANCE.getMPSKeybinds().filter(kb-> kb.getKeyCode() > Keyboard.KEY_NONE).filter(kb->kb.showOnHud).forEach(kb->{
                Optional<KBDisplay> kbDisplay = kbDisplayList.stream().filter(kbd->
                        Objects.equals(kbd.id, kb.getKeyCode()) && Objects.equals(kb.getKeyModifier(), kbd)).findFirst();
                if (kbDisplay.isPresent()) {
                    kbDisplay.map(kbd->kbd.boundKeybinds.add(kb));
                } else {
                    kbDisplayList.add(new KBDisplay(kb, MPSSettings.hud.keybindHUDx, MPSSettings.hud.keybindHUDy, MPSSettings.hud.keybindHUDx + (float) 16));
                }
            });
        }

        /**
         * Each module can be bound to its own MPSKeybinding instance, but those can be bound to a common key.
         * This just takes those common keys and displays the modules associated with them, that is, if they're
         * set to display on the HUD
         */
        static class KBDisplay extends DrawableMuseRect {
            // List of all keybindings that match
            List<MPSKeyBinding> boundKeybinds = new ArrayList<>();
            final int id;
            final KeyModifier modifier;

            String label;

            public KBDisplay(MPSKeyBinding kb, double left, double top, double right) {
                super(left, top, right, top + 16, true, Colour.DARKGREEN.withAlpha(0.2F), Colour.GREEN.withAlpha(0.2F));
                this.id = kb.getKeyCode();
                boundKeybinds.add(kb);
                this.modifier = kb.getKeyModifier();
                label = kb.getDisplayName();


//                if (modifier != KeyModifier.NONE) {
//                    label = modifier.getLocalizedComboName(id);
//
//                } else {
//                    label = id >= 0 ? Keyboard.getKeyName(id) : Keyboard.getKeyName(0);
//                }
            }

            @Override
            public void render(double mouseX, double mouseY, float partialTick) {
                float stringwidth = (float) MuseRenderer.getStringWidth(label);
                setWidth(stringwidth + 8 + boundKeybinds.stream().filter(kb->kb.showOnHud).collect(Collectors.toList()).size() * 18);
                super.render(mouseX, mouseY, partialTick);
                GlStateManager.pushMatrix();
                GlStateManager.translate(0,0,100);
                boolean kbToggleVal = boundKeybinds.stream().filter(kb->kb.toggleval).findFirst().isPresent();

                MuseRenderer.drawLeftAlignedStringString(label, (float) left() + 4, (float) top() + 4, (kbToggleVal) ? Colour.RED : Colour.GREEN);
                GlStateManager.popMatrix();
                AtomicDouble x = new AtomicDouble(left() + stringwidth + 8);

                boundKeybinds.stream().filter(kb ->kb.showOnHud).forEach(kb -> {
                    boolean active = false;
                    // just using the icon
                    IPowerModule module = ModuleManager.INSTANCE.getModule(kb.dataName);
                    if (module != null) {
                        for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
                            ItemStack stack = Minecraft.getMinecraft().player.getItemStackFromSlot(slot);
                            if (ModuleManager.INSTANCE.itemHasActiveModule(stack, module.getDataName())) {
                                active = true;
                            }
                        }

                        MuseTextureUtils.pushTexture(MuseTextureUtils.TEXTURE_QUILT);
                        MuseIconUtils.drawIconAt(x.get(), top(), module.getIcon(null), (active) ? Colour.WHITE : Colour.DARKGREY.withAlpha(0.5));
                        MuseTextureUtils.popTexture();
                        x.getAndAdd(16);
                    }
                });
            }
        }
    }