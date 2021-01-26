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

package com.github.lehjr.powersuits.client.event;


public class EventRegisterRenderers {
//    @SubscribeEvent
//    public void registerRenderers(ModelRegistryEvent event) {
//        MPSObjects mpsItems = MPSObjects.INSTANCE;
//
//        // PowerFistModel2
//        regRenderer(mpsItems.powerFist);
//
//        // Armor
//        regRenderer(mpsItems.powerArmorHead);
//        regRenderer(mpsItems.powerArmorTorso);
//        regRenderer(mpsItems.powerArmorLegs);
//        regRenderer(mpsItems.powerArmorFeet);
//
//        // Tinker Table
//        regRenderer(Item.getItemFromBlock(MPSObjects.INSTANCE.tinkerTable));
//
//        // Lux Capacitor
//        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(MPSObjects.INSTANCE.luxCapacitor), 0, ModelLuxCapacitor.modelResourceLocation);
//
//        // Components
//        Item components = mpsItems.components;
//        if (components != null) {
//            for (Integer meta : ((ItemComponent) components).names.keySet()) {
//                String oredictName = ((ItemComponent) components).names.get(meta);
//                ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(MPSResourceConstants.COMPONENTS_PREFIX + oredictName, "inventory");
//                ModelLoader.setCustomModelResourceLocation(components, meta, itemModelResourceLocation);
//                OreDictionary.registerOre(oredictName, new ItemStack(components, 1, meta));
//            }
//        }
//
//        RenderingRegistry.registerEntityRenderingHandler(SpinningBladeEntity.class, EntityRendererSpinningBlade::new);
//        RenderingRegistry.registerEntityRenderingHandler(PlasmaBallEntity.class, EntityRendererPlasmaBolt::new);
//        RenderingRegistry.registerEntityRenderingHandler(LuxCapacitorEntity.class, EntityRendererLuxCapacitorEntity::new);
//
//
//
//
//
//
//
//        ModelResourceLocation liquid_nitrogen_location = new ModelResourceLocation(MPSObjects.INSTANCE.blockLiquidNitrogen.getRegistryName(), "normal");
//        Item fluid = Item.getItemFromBlock(MPSObjects.INSTANCE.blockLiquidNitrogen);
//
//        ModelBakery.registerItemVariants(fluid);
//        ModelLoader.setCustomMeshDefinition(fluid, stack -> liquid_nitrogen_location);
//        ModelLoader.setCustomStateMapper(MPSObjects.INSTANCE.blockLiquidNitrogen, new StateMapperBase() {
//            @Override
//            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
//                return liquid_nitrogen_location;
//            }
//        });
//
//    }
//
////
////    ModelResourceLocation fluidLocation = new ModelResourceLocation(MODID.toLowerCase() + ":" + FiniteFluidBlock.id, "normal");
////
////    Item fluid = Item.getItemFromBlock(FiniteFluidBlock.instance);
////            ModelLoader.setCustomModelResourceLocation(EmptyFluidContainer.instance, 0, new ModelResourceLocation("forge:bucket", "inventory"));
////            ModelLoader.setBucketModelDefinition(FluidContainer.instance);
////    // no need to pass the locations here, since they'll be loaded by the block model logic.
////            ModelBakery.registerItemVariants(fluid);
////            ModelLoader.setCustomMeshDefinition(fluid, stack -> fluidLocation);
////            ModelLoader.setCustomStateMapper(MPSObjects.INSANCE., new StateMapperBase() {
////        @Override
////        protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
////            return fluidLocation;
////        }
////    });
//
//
//    private void regRenderer(Item item) {
//        ModelResourceLocation location = new ModelResourceLocation(item.getRegistryName(), "inventory");
//        ModelLoader.setCustomModelResourceLocation(item, 0, location);
//    }
}