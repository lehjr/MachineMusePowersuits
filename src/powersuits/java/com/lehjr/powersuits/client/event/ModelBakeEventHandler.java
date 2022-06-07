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

package com.lehjr.powersuits.client.event;


import com.lehjr.numina.client.model.obj.OBJBakedCompositeModel;
import com.lehjr.powersuits.client.model.block.LuxCapacitorModelWrapper;
import com.lehjr.powersuits.client.model.helper.ModelSpecXMLReader;
import com.lehjr.powersuits.client.model.item.PowerFistModel;
import com.lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.net.URL;
import java.util.ArrayList;


public enum ModelBakeEventHandler {
    INSTANCE;

    ModelResourceLocation luxCapItemLocation = new ModelResourceLocation(MPSRegistryNames.LUX_CAPACITOR, "inventory");
    ModelResourceLocation luxCapModuleLocation = new ModelResourceLocation(MPSRegistryNames.LUX_CAPACITOR_MODULE, "inventory");

    public static final ModelResourceLocation powerFistIconLocation = new ModelResourceLocation(MPSRegistryNames.POWER_FIST, "inventory");

    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event) {
        /**
         * Notes: looks like all current models are "SimpleBakedModels"
         */
        // replace LuxCapacitor model with one that can generate the model data needed to color the lens for the item model
        BakedModel luxCapItemModel = event.getModelRegistry().get(luxCapItemLocation);
        if (luxCapItemModel instanceof OBJBakedCompositeModel) {
            event.getModelRegistry().put(luxCapItemLocation, new LuxCapacitorModelWrapper((OBJBakedCompositeModel) luxCapItemModel));
        }

        // and the same for the module
        BakedModel luxCapModuleModel = event.getModelRegistry().get(luxCapModuleLocation);
        if (luxCapItemModel instanceof OBJBakedCompositeModel) {
            event.getModelRegistry().put(luxCapModuleLocation, new LuxCapacitorModelWrapper((OBJBakedCompositeModel) luxCapModuleModel));
        }

        // powerfist ... preserve icon while using the real model for everythign else
        BakedModel powerFistIcon = event.getModelRegistry().get(powerFistIconLocation);
        if (!OBJBakedCompositeModel.class.isInstance(powerFistIcon)) {
            System.out.println("powerfist model doing something");

            event.getModelRegistry().put(powerFistIconLocation, new PowerFistModel(powerFistIcon));
        }

        loadArmorModels(null);
    }


    public static void loadArmorModels(@Nullable TextureStitchEvent.Pre event) {
        ArrayList<String> resourceList = new ArrayList<>() {{
            add("/assets/powersuits/modelspec/armor2.xml");
            add("/assets/powersuits/modelspec/default_armor.xml");
            add("/assets/powersuits/modelspec/default_armorskin.xml");
            add("/assets/powersuits/modelspec/armor_skin2.xml");
            add("/assets/powersuits/modelspec/default_powerfist.xml");
        }};

        for (String resourceString : resourceList) {
            parseSpecFile(resourceString, event);
        }
//
//        URL resource = MPSModelHelper.class.getResource("/assets/powersuits/models/item/armor/modelspec.xml");
//        ModelSpecXMLReader.INSTANCE.parseFile(resource, event);
//        URL otherResource = MPSModelHelper.class.getResource("/assets/powersuits/models/item/armor/armor2.xml");
//        ModelSpecXMLReader.INSTANCE.parseFile(otherResource, event);

//        ModelPowerFistHelper.INSTANCE.loadPowerFistModels(event);

//        ModelRegistry.getInstance().getNames().forEach(name->System.out.println("modelregistry name: " + name));
    }

    public static void parseSpecFile(String resourceString, @Nullable TextureStitchEvent.Pre event) {
        URL resource = ModelBakeEventHandler.class.getResource(resourceString);
        ModelSpecXMLReader.INSTANCE.parseFile(resource, event);
    }
}