package lehjr.numina.common.capabilities.inventory;

import com.mojang.datafixers.util.Pair;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModularItem extends ItemStackHandler implements IModularItem {
    final boolean isTool;
    ItemStack modularItem;
    Map<ModuleCategory, RangedWrapper> rangedWrappers;
    ModuleTarget target;

    public ModularItem(@Nonnull ItemStack modularItem, int size) {
        this(modularItem, size, false);
    }

    public ModularItem(@Nonnull ItemStack modularItem, int size, boolean isTool) {
        this(modularItem, NonNullList.withSize(size, ItemStack.EMPTY), isTool);
    }

    public ModularItem(@Nonnull ItemStack modularItem, NonNullList<ItemStack> stacks) {
        super(stacks);
        this.modularItem = modularItem;
        this.rangedWrappers = new HashMap<>();
        this.isTool = false;
    }

    public ModularItem(@Nonnull ItemStack modularItem, NonNullList<ItemStack> stacks, boolean isTool) {
        super(stacks);
        this.modularItem = modularItem;
        this.rangedWrappers = new HashMap<>();
        this.isTool = isTool;
    }



    @Override
    public void setRangedWrapperMap(Map<ModuleCategory, RangedWrapper> rangedWrappers) {

    }

    @Override
    public boolean isModuleValid(@NotNull ItemStack module) {
        System.out.println("Fixme");
        return false;
    }

    @Override
    public boolean isModuleInstalled(ResourceLocation regName) {
        System.out.println("Fixme");
        return false;
    }

    @Override
    public boolean isModuleOnline(ResourceLocation moduleName) {
        System.out.println("Fixme");
        return false;
    }

    @Override
    public void toggleModule(ResourceLocation moduleName, boolean online) {
        System.out.println("Fixme");
    }

    @Nullable
    @Override
    public Pair<Integer, Integer> getRangeForCategory(ModuleCategory category) {
        System.out.println("Fixme");
        return Pair.of(1, 1);
    }

    @Override
    public List<ResourceLocation> getInstalledModuleNames() {

        return List.of();
    }

    @Override
    public NonNullList<ItemStack> getInstalledModules() {
        System.out.println("Fixme");
        return NonNullList.create();
    }

    @Override
    public NonNullList<ItemStack> getInstalledModulesOfType(Class<? extends IPowerModule> type) {
        System.out.println("Fixme");
        return NonNullList.create();
    }

    @Override
    public ItemStack getOnlineModuleOrEmpty(ResourceLocation regName) {
        System.out.println("fixme");
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getModularItemStack() {
        System.out.println("fixme");
        return ItemStack.EMPTY;
    }

    @Override
    public void tick(Player player) {
        System.out.println("fixme");
    }


    @Override
    public boolean setModuleTweakDouble(ResourceLocation moduleName, String key, double value) {
        System.out.println("fixme");
        return false;
    }
}
