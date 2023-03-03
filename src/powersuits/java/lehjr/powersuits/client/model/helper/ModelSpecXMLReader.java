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

package lehjr.powersuits.client.model.helper;

import com.google.common.collect.ImmutableMap;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import lehjr.numina.client.model.helper.ModelHelper;
import lehjr.numina.client.model.obj.OBJBakedCompositeModel;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.render.modelspec.*;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.string.StringUtils;
import lehjr.powersuits.common.config.MPSSettings;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.TransformationHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 8:44 AM, 4/28/13
 * <p>
 * Ported to Java by lehjr on 11/8/16.
 */
@OnlyIn(Dist.CLIENT)
public enum ModelSpecXMLReader {
    INSTANCE;

    public static void parseFile(URL file, @Nullable TextureStitchEvent.Pre event, ForgeModelBakery bakery) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputSource x = new InputSource(file.openStream());
            Document xml = dBuilder.parse(new InputSource(file.openStream()));
            parseXML(xml, event, bakery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parseFile(File file, @Nullable TextureStitchEvent.Pre event, ForgeModelBakery bakery) {
        if (file.exists()) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = null;
                dBuilder = dbFactory.newDocumentBuilder();
                Document xml = dBuilder.parse(file);
                parseXML(xml, event, bakery);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void parseXML(Document xml, @Nullable TextureStitchEvent.Pre event, ForgeModelBakery bakery) {
        if (xml != null) {
            try {
                xml.normalizeDocument();
                if (xml.hasChildNodes()) {
                    NodeList specList = xml.getElementsByTagName("modelSpec");
                    for (int i = 0; i < specList.getLength(); i++) {
                        Node specNode = specList.item(i);
                        if (specNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) specNode;
                            SpecType specType = SpecType.getTypeFromName(eElement.getAttribute("type"));

                            if (specType == null) {
                                System.out.println("type: "+ eElement.getAttribute("type"));
                            }

                            String specName = eElement.getAttribute("specName");

                            boolean isDefault = (eElement.hasAttribute("default") ? Boolean.parseBoolean(eElement.getAttribute("default")) : false);

                            switch (specType) {
                                case HANDHELD:
                                    // only allow custom models if allowed by config
//                                    if (isDefault || MPSSettings::getModuleConfig.allowCustomPowerFistModels())
                                    parseModelSpec(specNode, event, bakery, SpecType.HANDHELD, specName, isDefault);
                                    break;

                                case ARMOR_MODEL:
                                    // only allow these models if allowed by config
                                    if (MPSSettings.allowHighPollyArmor()) {
                                        parseModelSpec(specNode, event, bakery, SpecType.ARMOR_MODEL, specName, isDefault);
                                    }
                                    break;

                                case ARMOR_SKIN:
                                    if (event == null) {
                                        TextureSpec textureSpec = new TextureSpec(specName, isDefault);
                                        parseTextureSpec(specNode, textureSpec);
                                    }
                                    break;

                                default:
                                    break;
                            }
                        }
                    }
                } else
                    System.out.println("XML reader: document has no nodes!!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void parseTextureSpec(Node specNode, TextureSpec textureSpec) {
        // ModelBase textures are not registered.
        TextureSpec existingspec = (TextureSpec) ModelRegistry.getInstance().put(textureSpec.getName(), textureSpec);
        NodeList textures = specNode.getOwnerDocument().getElementsByTagName("texture");
        for (int i = 0; i < textures.getLength(); i++) {
            Node textureNode = textures.item(i);
            if (textureNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) textureNode;
                ResourceLocation fileLocation = new ResourceLocation(eElement.getAttribute("file"));
                NodeList bindings = eElement.getElementsByTagName("binding");
                for (int j = 0; j < bindings.getLength(); j++) {
                    SpecBinding binding = getBinding(bindings.item(j));
                    getTexturePartSpec(existingspec, bindings.item(j), binding.getSlot(), fileLocation);
                }
            }
        }
    }

    /**
     * Biggest difference between the ModelSpec for Armor vs PowerFistModel2 is that the armor models don't need item camera transforms
     */
    public static void parseModelSpec(Node specNode, TextureStitchEvent.Pre event, ForgeModelBakery bakery, SpecType specType, String specName, boolean isDefault) {
        NodeList models = specNode.getOwnerDocument().getElementsByTagName(TagConstants.MODEL);
        java.util.List<String> textures = new ArrayList<>();
        ModelState modelTransform = null;

        for (int i = 0; i < models.getLength(); i++) {
            Node modelNode = models.item(i);
            if (modelNode.getNodeType() == Node.ELEMENT_NODE) {
                Element modelElement = (Element) modelNode;

                // Register textures
                if (event != null) {
                    List<String> tempTextures = Arrays.asList(modelElement.getAttribute("textures").split(","));
                    for (String texture : tempTextures)
                        if (!(textures.contains(texture))) {
                            textures.add(texture);
                        }
                } else {
                    String modelLocation = modelElement.getAttribute("file");
                    // IModelStates should be per model, not per spec
                    NodeList cameraTransformList = modelElement.getElementsByTagName("modelTransforms");
                    // check for item camera transforms, then fall back on single transform for model
                    if (cameraTransformList.getLength() > 0) {
                        Node cameraTransformNode = cameraTransformList.item(0);
                        modelTransform = getIModelTransform(cameraTransformNode);
                    } else {
                        // Get the transform for the model and add to the registry
                        NodeList transformNodeList = modelElement.getElementsByTagName("transformationMatrix");
                        if (transformNodeList.getLength() > 0) {
                            ImmutableMap.Builder<ItemTransforms.TransformType, Transformation> builder = ImmutableMap.builder();
                            builder.put(ItemTransforms.TransformType.NONE, getTransform(transformNodeList.item(0)));
                            modelTransform =  new SimpleModelState(builder.build());
                            // TODO... check and see how this works.. not sure about this
                            //modelTransform = new SimpleModelState(getTransform(transformNodeList.item(0)));
                        } else {
                            modelTransform = SimpleModelState.IDENTITY;
                        }
                    }





                    /*


                        / **
     * Gets the vanilla camera transforms data.
     * Do not use for non-vanilla code. For general usage, prefer getCombinedState.
     * /
                    @Deprecated
                    ItemTransforms getCameraTransforms();

                    / **
                     * @return The combined transformation state including vanilla and forge transforms data.
                     * /
                    IModelTransform getCombinedTransform();

                    this(model.useSmoothLighting(), // true
                    model.isShadedInGui(), // true
                    model.isSideLit(), // false
                    model.getCameraTransforms(),
                    overrides);


                    loadBakedModel(
                    IModelConfiguration owner,
                    ModelBakery bakery,
                    Function<Material, TextureAtlasSprite> spriteGetter,
                    IModelTransform modelTransform,
                    ItemOverrides overrides,
                    ResourceLocation modelLocation)
                     */


                    OBJBakedCompositeModel bakedModel =
//                    BlockModelConfiguration

                    //public static OBJBakedCompositeModel loadBakedModel(IModelTransform modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) {


                        ModelHelper.loadBakedModel(
                            modelTransform,
                            ItemOverrides.EMPTY,
                            new ResourceLocation(modelLocation));

                    // ModelSpec stuff
                    if (bakedModel != null && bakedModel instanceof OBJBakedCompositeModel) {
                        ModelSpec modelspec = new ModelSpec(bakedModel, modelTransform, specName, isDefault, specType);

                        NodeList bindingNodeList = ((Element) modelNode).getElementsByTagName("binding");
                        if (bindingNodeList.getLength() > 0) {
                            for (int k = 0; k < bindingNodeList.getLength(); k++) {
                                Node bindingNode = bindingNodeList.item(k);
                                SpecBinding binding = getBinding(bindingNode);
                                NodeList partNodeList = ((Element) bindingNode).getElementsByTagName(TagConstants.PART);
                                for (int j = 0; j < partNodeList.getLength(); j++) {
                                    getModelPartSpec(modelspec, partNodeList.item(j), binding);
                                }
                            }
                        }

                        ModelRegistry.getInstance().put(StringUtils.extractName(modelLocation), modelspec);

                    } else {
                        NuminaLogger.logger.error("Model file " + modelLocation + " not found! D:");
                    }
                }
            }
        }

        // Register textures
        if (event != null) {
            // this is the atlas used
            if (event.getAtlas().location() == TextureAtlas.LOCATION_BLOCKS) {
                for (String texture : textures) {
                    event.addSprite(new ResourceLocation(texture));
                }
            }
        }
    }

    // since the skinned armor can't have more than one texture per EquipmentSlot the TexturePartSpec is named after the itemSlot
    public static void getTexturePartSpec(TextureSpec textureSpec, Node bindingNode, EquipmentSlot slot, ResourceLocation fileLocation) {
        Element partSpecElement = (Element) bindingNode;
        Color colour = partSpecElement.hasAttribute("defaultColor") ?
                parseColour(partSpecElement.getAttribute("defaultColor")) : Color.WHITE;

        if (colour.a == 0)
            colour = colour.withAlpha(1.0F);

        if (!Objects.equals(slot, null) && Objects.equals(slot.getType(), EquipmentSlot.Type.ARMOR)) {
            textureSpec.put(slot.getName(),
                    new TexturePartSpec(textureSpec,
                            new SpecBinding(null, slot, "all"),
                            textureSpec.addColorIfNotExist(colour), slot.getName(), fileLocation));
        }
    }

    /**
     * ModelPartSpec is a group of settings for each model part
     */
    public static void getModelPartSpec(ModelSpec modelSpec, Node partSpecNode, SpecBinding binding) {
        Element partSpecElement = (Element) partSpecNode;
        String partname = validatePolygroup(partSpecElement.getAttribute("partName"), modelSpec);
        boolean glow = Boolean.parseBoolean(partSpecElement.getAttribute("defaultglow"));
        Color colour = partSpecElement.hasAttribute("defaultColor") ?
                parseColour(partSpecElement.getAttribute("defaultColor")) : Color.WHITE;

        if (colour.a == 0)
            colour = colour.withAlpha(1.0F);

        if (partname == null) {
            NuminaLogger.logError("partName is NULL!!");
            NuminaLogger.logError("ModelSpec model: " + modelSpec.getName());
            NuminaLogger.logError("glow: " + glow);
            NuminaLogger.logError("colour: " + colour.hexColour());
        } else
            modelSpec.put(partname, new ModelPartSpec(modelSpec,
                    binding,
                    partname,
                    modelSpec.addColorIfNotExist(colour),
                    glow));
    }

    @Nullable
    public static String validatePolygroup(String s, ModelSpec m) {
        return m.getModel().getPart(s) != null ? s : null;
    }

    /**
     * This gets the map of TransformType, Transformation> used for handheld items
     *
     * @param itemCameraTransformsNode
     * @return
     */
    public static ModelState getIModelTransform(Node itemCameraTransformsNode) {
        ImmutableMap.Builder<ItemTransforms.TransformType, Transformation> builder = ImmutableMap.builder();
        NodeList transformationList = ((Element) itemCameraTransformsNode).getElementsByTagName("transformationMatrix");
        for (int i = 0; i < transformationList.getLength(); i++) {
            Node transformationNode = transformationList.item(i);
            ItemTransforms.TransformType transformType =
                    ItemTransforms.TransformType.valueOf(((Element) transformationNode).getAttribute("type").toUpperCase());
            Transformation trsrTransformation = getTransform(transformationNode);
            builder.put(transformType, trsrTransformation);
        }
        return new SimpleModelState(builder.build());
    }

    /**
     * This gets the transforms for baking the models. Transformation is also used for item camera transforms to alter the
     * position, scale, and translation of a held/dropped/framed item
     *
     * @param transformationNode
     * @return
     */
    public static Transformation getTransform(Node transformationNode) {
        Vector3f translation = parseVector(((Element) transformationNode).getAttribute("translation"));
        Vector3f rotation = parseVector(((Element) transformationNode).getAttribute("rotation"));
        Vector3f scale = parseVector(((Element) transformationNode).getAttribute("scale"));
        return getTransform(translation, rotation, scale);
    }

    /**
     * SpecBinding is a subset if settings for the ModelPartSpec
     */
    public static SpecBinding getBinding(Node bindingNode) {
        return new SpecBinding(
                (((Element) bindingNode).hasAttribute("target")) ?
                        MorphTarget.getMorph(((Element) bindingNode).getAttribute("target")) : null,
                (((Element) bindingNode).hasAttribute("itemSlot")) ?
                        EquipmentSlot.byName(((Element) bindingNode).getAttribute("itemSlot").toLowerCase()) : null,
                (((Element) bindingNode).hasAttribute("itemState")) ?
                        ((Element) bindingNode).getAttribute("itemState") : "all"
        );
    }

    /**
     * Simple transformation for armor models. Powerfist (and shield?) will need one of these for every conceivable case except GUI which will be an icon
     */
    public static Transformation getTransform(@Nullable Vector3f translation, @Nullable Vector3f rotation, @Nullable Vector3f scale) {
        if (translation == null)
            translation = new Vector3f(0, 0, 0);
        if (rotation == null)
            rotation = new Vector3f(0, 0, 0);
        if (scale == null)
            scale = new Vector3f(1, 1, 1);


        /// Transformation(@Nullable Vector3f translationIn, @Nullable Quaternion rotationLeftIn, @Nullable Vector3f scaleIn, @Nullable Quaternion rotationRightIn)

        return new Transformation(
                // Transform
                new Vector3f(translation.x() / 16, translation.y() / 16, translation.z() / 16),
                // Angles
                TransformationHelper.quatFromXYZ(rotation, true),
                // Scale
                scale,
                null);
    }

    @Nullable
    public static Vector3f parseVector(String s) {
        try {
            String[] ss = s.split(",");
            float x = Float.parseFloat(ss[0]);
            float y = Float.parseFloat(ss[1]);
            float z = Float.parseFloat(ss[2]);
            return new Vector3f(x, y, z);
        } catch (Exception e) {
            return null;
        }
    }

    public static Color parseColour(String colourString) {
        return Color.fromHexString(colourString);
    }
}