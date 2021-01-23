package com.github.lehjr.powersuits.client.model.helper;

import com.github.lehjr.numina.basemod.MuseLogger;
import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.util.capabilities.render.modelspec.*;
import com.github.lehjr.numina.util.client.model.helper.ModelHelper;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.string.MuseStringUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.google.common.collect.ImmutableMap;
import forge.OBJBakedCompositeModel;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.SimpleModelTransform;
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

    public static void parseFile(URL file, @Nullable TextureStitchEvent.Pre event, ModelLoader bakery) {
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

    public static void parseFile(File file, @Nullable TextureStitchEvent.Pre event, ModelLoader bakery) {
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

    public static void parseXML(Document xml, @Nullable TextureStitchEvent.Pre event, ModelLoader bakery) {
        if (xml != null) {
            try {
                xml.normalizeDocument();
                if (xml.hasChildNodes()) {
                    NodeList specList = xml.getElementsByTagName("modelSpec");
                    for (int i = 0; i < specList.getLength(); i++) {
                        Node specNode = specList.item(i);
                        if (specNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) specNode;
                            EnumSpecType specType = EnumSpecType.getTypeFromName(eElement.getAttribute("type"));

                            if (specType == null) {
                                System.out.println("type: "+ eElement.getAttribute("type"));
                            }

                            String specName = eElement.getAttribute("specName");

                            boolean isDefault = (eElement.hasAttribute("default") ? Boolean.parseBoolean(eElement.getAttribute("default")) : false);

                            switch (specType) {
                                case HANDHELD:
                                    // only allow custom models if allowed by config
//                                    if (isDefault || MPSSettings::getModuleConfig.allowCustomPowerFistModels())
                                    parseModelSpec(specNode, event, bakery, EnumSpecType.HANDHELD, specName, isDefault);
                                    break;

                                case ARMOR_MODEL:
                                    // only allow these models if allowed by config
                                    if (MPSSettings.allowHighPollyArmor()) {
                                        parseModelSpec(specNode, event, bakery, EnumSpecType.ARMOR_MODEL, specName, isDefault);
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
                String fileLocation = eElement.getAttribute("file");
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
    public static void parseModelSpec(Node specNode, TextureStitchEvent.Pre event, ModelLoader bakery, EnumSpecType specType, String specName, boolean isDefault) {
        NodeList models = specNode.getOwnerDocument().getElementsByTagName(NuminaConstants.TAG_MODEL);
        java.util.List<String> textures = new ArrayList<>();
        IModelTransform modelTransform = null;

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
                            ImmutableMap.Builder<ItemCameraTransforms.TransformType, TransformationMatrix> builder = ImmutableMap.builder();
                            builder.put(ItemCameraTransforms.TransformType.NONE, getTransform(transformNodeList.item(0)));
                            modelTransform =  new SimpleModelTransform(builder.build());
                            // TODO... check and see how this works.. not sure about this
                            //modelTransform = new SimpleModelTransform(getTransform(transformNodeList.item(0)));
                        } else {
                            modelTransform = SimpleModelTransform.IDENTITY;
                        }
                    }





                    /*


                        / **
     * Gets the vanilla camera transforms data.
     * Do not use for non-vanilla code. For general usage, prefer getCombinedState.
     * /
                    @Deprecated
                    ItemCameraTransforms getCameraTransforms();

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
                    ItemOverrideList overrides,
                    ResourceLocation modelLocation)
                     */


                    OBJBakedCompositeModel bakedModel =
//                    BlockModelConfiguration

                    //public static OBJBakedCompositeModel loadBakedModel(IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {


                        ModelHelper.loadBakedModel(
                            modelTransform,
                            ItemOverrideList.EMPTY,
                            new ResourceLocation(modelLocation));

                    // ModelSpec stuff
                    if (bakedModel != null && bakedModel instanceof OBJBakedCompositeModel) {
                        ModelSpec modelspec = new ModelSpec(bakedModel, modelTransform, specName, isDefault, specType);

                        NodeList bindingNodeList = ((Element) modelNode).getElementsByTagName("binding");
                        if (bindingNodeList.getLength() > 0) {
                            for (int k = 0; k < bindingNodeList.getLength(); k++) {
                                Node bindingNode = bindingNodeList.item(k);
                                SpecBinding binding = getBinding(bindingNode);
                                NodeList partNodeList = ((Element) bindingNode).getElementsByTagName(NuminaConstants.TAG_PART);
                                for (int j = 0; j < partNodeList.getLength(); j++) {
                                    getModelPartSpec(modelspec, partNodeList.item(j), binding);
                                }
                            }
                        }

                        ModelRegistry.getInstance().put(MuseStringUtils.extractName(modelLocation), modelspec);

                    } else {
                        MuseLogger.logger.error("Model file " + modelLocation + " not found! D:");
                    }
                }
            }
        }

        // Register textures
        if (event != null) {
            // this is the atlas used
            if (event.getMap().getTextureLocation() == AtlasTexture.LOCATION_BLOCKS_TEXTURE) {
                for (String texture : textures) {
                    event.addSprite(new ResourceLocation(texture));
                }
            }
        }
    }

    // since the skinned armor can't have more than one texture per EquipmentSlotType the TexturePartSpec is named after the itemSlot
    public static void getTexturePartSpec(TextureSpec textureSpec, Node bindingNode, EquipmentSlotType slot, String fileLocation) {
        Element partSpecElement = (Element) bindingNode;
        Colour colour = partSpecElement.hasAttribute("defaultColor") ?
                parseColour(partSpecElement.getAttribute("defaultColor")) : Colour.WHITE;

        if (colour.a == 0)
            colour = colour.withAlpha(1.0F);

        if (!Objects.equals(slot, null) && Objects.equals(slot.getSlotType(), EquipmentSlotType.Group.ARMOR)) {
            textureSpec.put(slot.getName(),
                    new TexturePartSpec(textureSpec,
                            new SpecBinding(null, slot, "all"),
                            textureSpec.addColourIfNotExist(colour), slot.getName(), fileLocation));
        }
    }

    /**
     * ModelPartSpec is a group of settings for each model part
     */
    public static void getModelPartSpec(ModelSpec modelSpec, Node partSpecNode, SpecBinding binding) {
        Element partSpecElement = (Element) partSpecNode;
        String partname = validatePolygroup(partSpecElement.getAttribute("partName"), modelSpec);
        boolean glow = Boolean.parseBoolean(partSpecElement.getAttribute("defaultglow"));
        Colour colour = partSpecElement.hasAttribute("defaultColor") ?
                parseColour(partSpecElement.getAttribute("defaultColor")) : Colour.WHITE;

        if (colour.a == 0)
            colour = colour.withAlpha(1.0F);

        if (partname == null) {
            System.out.println("partName is NULL!!");
            System.out.println("ModelSpec model: " + modelSpec.getName());
            System.out.println("glow: " + glow);
            System.out.println("colour: " + colour.hexColour());
        } else
            modelSpec.put(partname, new ModelPartSpec(modelSpec,
                    binding,
                    partname,
                    modelSpec.addColourIfNotExist(colour),
                    glow));
    }

    @Nullable
    public static String validatePolygroup(String s, ModelSpec m) {
        return m.getModel().getPart(s) != null ? s : null;
    }

    /**
     * This gets the map of TransformType, TransformationMatrix> used for handheld items
     *
     * @param itemCameraTransformsNode
     * @return
     */
    public static IModelTransform getIModelTransform(Node itemCameraTransformsNode) {
        ImmutableMap.Builder<ItemCameraTransforms.TransformType, TransformationMatrix> builder = ImmutableMap.builder();
        NodeList transformationList = ((Element) itemCameraTransformsNode).getElementsByTagName("transformationMatrix");
        for (int i = 0; i < transformationList.getLength(); i++) {
            Node transformationNode = transformationList.item(i);
            ItemCameraTransforms.TransformType transformType =
                    ItemCameraTransforms.TransformType.valueOf(((Element) transformationNode).getAttribute("type").toUpperCase());
            TransformationMatrix trsrTransformation = getTransform(transformationNode);
            builder.put(transformType, trsrTransformation);
        }
        return new SimpleModelTransform(builder.build());
    }

    /**
     * This gets the transforms for baking the models. TransformationMatrix is also used for item camera transforms to alter the
     * position, scale, and translation of a held/dropped/framed item
     *
     * @param transformationNode
     * @return
     */
    public static TransformationMatrix getTransform(Node transformationNode) {
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
                        EquipmentSlotType.fromString(((Element) bindingNode).getAttribute("itemSlot").toLowerCase()) : null,
                (((Element) bindingNode).hasAttribute("itemState")) ?
                        ((Element) bindingNode).getAttribute("itemState") : "all"
        );
    }

    /**
     * Simple transformation for armor models. Powerfist (and shield?) will need one of these for every conceivable case except GUI which will be an icon
     */
    public static TransformationMatrix getTransform(@Nullable Vector3f translation, @Nullable Vector3f rotation, @Nullable Vector3f scale) {
        if (translation == null)
            translation = new Vector3f(0, 0, 0);
        if (rotation == null)
            rotation = new Vector3f(0, 0, 0);
        if (scale == null)
            scale = new Vector3f(1, 1, 1);


        /// TransformationMatrix(@Nullable Vector3f translationIn, @Nullable Quaternion rotationLeftIn, @Nullable Vector3f scaleIn, @Nullable Quaternion rotationRightIn)

        return new TransformationMatrix(
                // Transform
                new Vector3f(translation.getX() / 16, translation.getY() / 16, translation.getZ() / 16),
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

    public static Colour parseColour(String colourString) {
        return Colour.fromHexString(colourString);
    }
}