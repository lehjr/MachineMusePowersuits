package com.github.lehjr.powersuits.client.event;


import com.github.lehjr.powersuits.client.model.block.LuxCapacitorModelWrapper;
import com.github.lehjr.powersuits.client.model.helper.MPAModelHelper;
import com.github.lehjr.powersuits.client.model.item.PowerFistModel;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.constants.MPSRegistryNames;
import forge.OBJBakedCompositeModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public enum ModelBakeEventHandler {
    INSTANCE;

    ModelResourceLocation luxCapItemLocation = new ModelResourceLocation(new ResourceLocation(MPSConstants.MOD_ID, MPSRegistryNames.LUX_CAPACITOR), "inventory");
    ModelResourceLocation luxCapModuleLocation = new ModelResourceLocation(new ResourceLocation(MPSConstants.MOD_ID, MPSRegistryNames.LUX_CAPACITOR_MODULE), "inventory");

    public static final ModelResourceLocation powerFistIconLocation = new ModelResourceLocation(new ResourceLocation(MPSConstants.MOD_ID, MPSRegistryNames.POWER_FIST), "inventory");

    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event) {
        /*

        from itemModelProvider
              for(int j = 0; j < 32; ++j) {
         if (j != 16) {
            this.func_240077_a_(Items.COMPASS, String.format("_%02d", j), StockModelShapes.GENERATED);
         }
      }
         */



        /**
         * Notes: looks like all current models are "SimpleBakedModels"
         */
        // replace LuxCapacitor model with one that can generate the model data needed to color the lens for the item model
        IBakedModel luxCapItemModel = event.getModelRegistry().get(luxCapItemLocation);
        if (luxCapItemModel instanceof OBJBakedCompositeModel) {
            event.getModelRegistry().put(luxCapItemLocation, new LuxCapacitorModelWrapper((OBJBakedCompositeModel) luxCapItemModel));
        }

        IBakedModel luxCapModuleModel = event.getModelRegistry().get(luxCapModuleLocation);
        if (luxCapItemModel instanceof OBJBakedCompositeModel) {
            event.getModelRegistry().put(luxCapModuleLocation, new LuxCapacitorModelWrapper((OBJBakedCompositeModel) luxCapModuleModel));
        }

        IBakedModel powerFistIcon = event.getModelRegistry().get(powerFistIconLocation);
        if (luxCapItemModel instanceof OBJBakedCompositeModel) {
            event.getModelRegistry().put(powerFistIconLocation, new PowerFistModel(powerFistIcon));
        }

        MPAModelHelper.loadArmorModels(null, event.getModelLoader());
    }
}