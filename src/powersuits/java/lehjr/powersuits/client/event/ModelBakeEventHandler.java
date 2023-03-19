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


import lehjr.numina.client.model.obj.OBJBakedCompositeModel;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.powersuits.client.model.block.LuxCapacitorModelWrapper;
import lehjr.powersuits.client.model.helper.MPSModelHelper;
import lehjr.powersuits.client.model.item.PowerFistModel;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public enum ModelBakeEventHandler {
    INSTANCE;

    ModelResourceLocation luxCapItemLocation = new ModelResourceLocation(MPSRegistryNames.LUX_CAPACITOR, "inventory");
    ModelResourceLocation luxCapModuleLocation = new ModelResourceLocation(MPSRegistryNames.LUX_CAPACITOR_MODULE, "inventory");

    public static final ModelResourceLocation powerFistIconLocation = new ModelResourceLocation(MPSRegistryNames.POWER_FIST, "inventory");

    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event) {
        NuminaLogger.logError("baking something here");

        event.getModelRegistry().keySet().stream().filter(resourceLocation -> resourceLocation.toString().contains("powersuits:luxcapacitor")).forEach(resourceLocation -> NuminaLogger.logError("modelLocation: " + resourceLocation));
        event.getModelRegistry().keySet().stream().filter(resourceLocation -> resourceLocation.toString().contains("powersuits:powerfist")).forEach(resourceLocation -> NuminaLogger.logError("modelLocation: " + resourceLocation));

        /**
         * Notes: looks like all current models are "SimpleBakedModels"
         */
        // replace LuxCapacitor model with one that can generate the model data needed to color the lens for the item model
        BakedModel luxCapItemModel = event.getModelRegistry().get(luxCapItemLocation);
        if (luxCapItemModel instanceof OBJBakedCompositeModel) {
            event.getModelRegistry().put(luxCapItemLocation, new LuxCapacitorModelWrapper((OBJBakedCompositeModel) luxCapItemModel));
        }

        BakedModel luxCapModuleModel = event.getModelRegistry().get(luxCapModuleLocation);
        if (luxCapItemModel instanceof OBJBakedCompositeModel) {
            event.getModelRegistry().put(luxCapModuleLocation, new LuxCapacitorModelWrapper((OBJBakedCompositeModel) luxCapModuleModel));
        }

        BakedModel powerFistIcon = event.getModelRegistry().get(powerFistIconLocation);
        if (!OBJBakedCompositeModel.class.isInstance(powerFistIcon)) {
            event.getModelRegistry().put(powerFistIconLocation, new PowerFistModel(powerFistIcon));
        }

        MPSModelHelper.loadArmorModels(null, event.getModelLoader());
    }
}