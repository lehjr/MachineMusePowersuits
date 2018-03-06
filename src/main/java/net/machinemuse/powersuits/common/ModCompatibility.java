package net.machinemuse.powersuits.common;

import net.machinemuse.numina.api.module.ModuleManager;
import net.machinemuse.numina.common.config.NuminaConfig;
import net.machinemuse.numina.utils.MuseLogger;
import net.machinemuse.powersuits.api.constants.MPSModuleConstants;
import net.machinemuse.powersuits.common.config.MPSConfig;
import net.machinemuse.powersuits.item.module.environmental.AirtightSealModule;
import net.machinemuse.powersuits.item.module.environmental.ApiaristArmorModule;
import net.machinemuse.powersuits.item.module.environmental.HazmatModule;
import net.machinemuse.powersuits.item.module.tool.*;
import net.machinemuse.powersuits.item.module.vision.NightVisionModule;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModAPIManager;
import net.minecraftforge.fml.common.ModContainer;

import java.util.Collections;
import java.util.List;

public class ModCompatibility {
    public static boolean isTechRebornLoaded() {
        return Loader.isModLoaded("techreborn");
    }

    public static boolean isGregTechLoaded() {
        return Loader.isModLoaded("gregtech");
    }

    // Industrialcraft common
    public static boolean isIndustrialCraftLoaded() {
        return Loader.isModLoaded("IC2");
    }


    public static final boolean isIndustrialCraftExpLoaded() {
        if (!isIndustrialCraftLoaded())
            return false;

        List<ModContainer> list = Loader.instance().getModList();
        for (ModContainer container : list) {
            if (container.getModId().toLowerCase().equals("ic2")) {
                if (container.getName().equals("IndustrialCraft 2"))
                    return true;
                return false;
            }
        }
        return false;
    }

    // Industrialcraft 2 classic (note redundant code is intentional for "just in case")
    public static final boolean isIndustrialCraftClassicLoaded() {
        if (!isIndustrialCraftLoaded())
            return false;

        List<ModContainer> list = Loader.instance().getModList();
        for (ModContainer container : list) {
            if (container.getModId().toLowerCase().equals("ic2")) {
                if (container.getName().equals("Industrial Craft Classic"))
                    return true;
                return false;
            }
        }
        return false;
    }

    public static boolean isThaumCraftLoaded() {
        return Loader.isModLoaded("thaumcraft");
    }

    public static boolean isThermalExpansionLoaded() {
        return Loader.isModLoaded("thermalexpansion") && Loader.isModLoaded("thermalfoundation");
    }

    public static boolean isGalacticraftLoaded() {
        return Loader.isModLoaded("galacticraftcore");
    }

    public static boolean isRFAPILoaded() {
        return ModAPIManager.INSTANCE.hasAPI("redstoneflux");
    }

    public static boolean isCOFHLibLoaded() {
        return ModAPIManager.INSTANCE.hasAPI("cofhlib");
    }

    public static boolean isCOFHCoreLoaded() {
//        return ModAPIManager.INSTANCE.hasAPI("cofhcore");
        return Loader.isModLoaded("cofhcore");
    }

    public static boolean isForestryLoaded() {
        return Loader.isModLoaded("forestry");
    }

    public static boolean isChiselLoaded() {
        return Loader.isModLoaded("chisel");
    }

    public static boolean isEnderIOLoaded() {
        return Loader.isModLoaded("enderio");
    }

    public static boolean isAppengLoaded() {
        return Loader.isModLoaded("appliedenergistics2");
    }

    public static boolean isExtraCellsLoaded() {
        return Loader.isModLoaded("extracells");
    }

    public static boolean isMFRLoaded() {
        return Loader.isModLoaded("MineFactoryReloaded");
    }

    public static boolean isRailcraftLoaded() {
        return Loader.isModLoaded("railcraft");
    }

    public static boolean isCompactMachinesLoaded() {
        return Loader.isModLoaded("cm2");
    }

    public static boolean isRenderPlayerAPILoaded() {
        return Loader.isModLoaded("RenderPlayerAPI");
    }

    public static boolean isRefinedStorageLoaded() {
        return Loader.isModLoaded("refinedstorage");
    }

    public static boolean isScannableLoaded() {
        return Loader.isModLoaded("scannable");
    }

    public static boolean isWirelessCraftingGridLoaded() {
        return Loader.isModLoaded("wcg");
    }

    public static boolean isMekanismLoaded() {
        return Loader.isModLoaded("mekanism");
    }

    public static boolean enableThaumGogglesModule() {
        return  isThaumCraftLoaded() ? MPSConfig.getInstance().getModuleAllowed("item." + MPSModuleConstants.MODULE_THAUM_GOGGLES) : false;
    }

    // 1MJ (MPS) = 1 MJ (Mekanism)
    public static double getMekRatio() {
        return NuminaConfig.getMekRatio();
    }

    // 1 MJ = 2.5 EU
    // 1 EU = 0.4 MJ
    public static double getIC2Ratio() {
        return NuminaConfig.getIC2Ratio();
    }

    // 1 MJ = 10 RF
    // 1 RF = 0.1 MJ
    public static double getRFRatio() {
        return 1;
    }

    // (Refined Storage) 1 RS = 1 RF
    public static double getRSRatio() {
        return NuminaConfig.getRSRatio();
    }

    // 1 MJ = 5 AE
    // 1 AE = 0.2 MJ
    public static double getAE2Ratio() {
        return NuminaConfig.getAE2Ratio();
    }

    public static void registerModSpecificModules() {
        // Make the energy ratios show up in config file
        getIC2Ratio();
        getRFRatio();
        getRSRatio();

        // CoFH Lib - CoFHLib is included in CoFHCore
        if (isCOFHCoreLoaded()) {
            ModuleManager.getInstance().addModule(new ItemStack(MPSItems.getInstance().module_omniwrench));
        }

        // Thaumcraft
        if (isThaumCraftLoaded() && enableThaumGogglesModule()) {
            ModuleManager.getInstance().addModule(new ItemStack(MPSItems.getInstance().module_night_vision));
        }

        //IModule module = new MultimeterModule(Collections.singletonList((IMuseItem) MPSItems.powerfist()));

        // Industrialcraft
        if (isIndustrialCraftLoaded()) {
            ModuleManager.getInstance().addModule(new ItemStack(MPSItems.getInstance().module_hazmat));
            ModuleManager.getInstance().addModule(new ItemStack(MPSItems.getInstance().module_tree_tap));
        }

        // Galacticraft
        if (isGalacticraftLoaded()) {
            ModuleManager.getInstance().addModule(new ItemStack(MPSItems.getInstance().module_airtight_seal));
        }

        // Forestry
        if (isForestryLoaded()) {
            ModuleManager.getInstance().addModule(new ItemStack(MPSItems.getInstance().module_grafter));
            ModuleManager.getInstance().addModule(new ItemStack(MPSItems.getInstance().module_scoop));
            ModuleManager.getInstance().addModule(new ItemStack(MPSItems.getInstance().module_apiarist_armor));
        }

        // Chisel
        if(isChiselLoaded()) {
            try {
                ModuleManager.getInstance().addModule(new ItemStack(MPSItems.getInstance().module_chisel));
            } catch(Exception e) {
                MuseLogger.logException("Couldn't add Chisel module", e);
            }
        }

        // Applied Energistics
        if (isAppengLoaded()) {
            ModuleManager.getInstance().addModule(new ItemStack(MPSItems.getInstance().module_appeng_wireless));
            // Extra Cells 2
            if (isExtraCellsLoaded())
                ModuleManager.getInstance().addModule(new ItemStack(MPSItems.getInstance().module_appeng_ec_wireless_fluid));
        }

        // Multi-Mod Compatible OmniProbe
        if (isEnderIOLoaded() || isMFRLoaded() || isRailcraftLoaded()) {
            ModuleManager.getInstance().addModule(new ItemStack(MPSItems.getInstance().module_omniprobe));
        }

        // TODO: on hold for now. Needs a conditional fiuld tank and handler. May not be worth it.
        // Compact Machines Personal Shrinking Device
        if (isCompactMachinesLoaded()) {
            ModuleManager.getInstance().addModule(new ItemStack(MPSItems.getInstance().module_cmpsd));
        }


        if (isRefinedStorageLoaded()) {
            ModuleManager.getInstance().addModule(new ItemStack(MPSItems.getInstance().module_refinedstoragewirelessgrid));
        }

//        if (isScannableLoaded()) {
//            ModuleManager.addModule(new ScannableModule(Collections.singletonList((IMuseItem) MPSItems.powerfist)));
//        }
    }
}
