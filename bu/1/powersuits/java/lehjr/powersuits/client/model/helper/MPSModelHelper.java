///*
// * Copyright (c) 2021. MachineMuse, Lehjr
// *  All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// *      Redistributions of source code must retain the above copyright notice, this
// *      list of conditions and the following disclaimer.
// *
// *     Redistributions in binary form must reproduce the above copyright notice,
// *     this list of conditions and the following disclaimer in the documentation
// *     and/or other materials provided with the distribution.
// *
// *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
// *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package lehjr.powersuits.client.model.helper;
//
//import com.lehjr.numina.common.base.NuminaLogger;
//import net.minecraft.client.resources.model.ModelBakery;
//import net.minecraftforge.client.event.TextureStitchEvent;
//
//import javax.annotation.Nullable;
//import java.net.URL;
//import java.util.ArrayList;
//
//public class MPSModelHelper {
//    // One pass just to register the textures called from texture stitch event
//    // another to register the models called from model bake event (second run)
//
//    // TODO: extract to subdir of config dir and scan for others ... also rename this
//
//
//    public static void loadArmorModels(@Nullable TextureStitchEvent event, ModelBakery bakery) {
////        ArrayList<String> resourceList = new ArrayList<String>() {{
////            add("/assets/powersuits/modelspec/armor2.xml");
////            add("/assets/powersuits/modelspec/default_armor.xml");
////            add("/assets/powersuits/modelspec/default_armorskin.json");
////            add("/assets/powersuits/modelspec/armor_skin2.xml");
////            add("/assets/powersuits/modelspec/default_powerfist.xml");
////        }};
//
////        for (String resourceString : resourceList) {
////            parseSpecFile(resourceString, event, bakery);
////        }
////
////        URL resource = MPSModelHelper.class.getResource("/assets/powersuits/models/item/armor/modelspec.xml");
////        ModelSpecXMLReader.INSTANCE.parseFile(resource, event);
////        URL otherResource = MPSModelHelper.class.getResource("/assets/powersuits/models/item/armor/armor2.xml");
////        ModelSpecXMLReader.INSTANCE.parseFile(otherResource, event);
//
////        ModelPowerFistHelper.INSTANCE.loadPowerFistModels(event);
//
////        ModelRegistry.getInstance().getNames().forEach(name->NuminaLogger.logDebug("modelregistry name: " + name));
//    }
//
////    public static void parseSpecFile(String resourceString, @Nullable TextureStitchEvent event, ModelBakery bakery) {
////        NuminaLogger.logError("loading cosmetic presets");
////        URL resource = MPSModelHelper.class.getResource(resourceString);
//////        ModelSpecXMLReader.INSTANCE.parseFile(resource, event, bakery); // FIXME replace with proper loader
////    }
//}