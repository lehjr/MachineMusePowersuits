/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.client.event;


import lehjr.numina.util.client.model.obj.OBJBakedCompositeModel;
import lehjr.powersuits.client.model.block.LuxCapacitorModelWrapper;
import lehjr.powersuits.client.model.helper.MPSModelHelper;
import lehjr.powersuits.client.model.item.PowerFistModel;
import lehjr.powersuits.constants.MPSConstants;
import lehjr.powersuits.constants.MPSRegistryNames;
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
            this.generateFlatItem(Items.COMPASS, String.format("_%02d", j), StockModelShapes.GENERATED);
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

        MPSModelHelper.loadArmorModels(null, event.getModelLoader());
    }
}