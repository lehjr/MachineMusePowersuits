package powersuits.client.model.block;

import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.registration.MPSBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.client.model.generators.CustomLoaderBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MPSBlockModelProvider extends BlockModelProvider {
    public MPSBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MPSConstants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        getBuilder(MPSBlocks.LUX_CAPACITOR_BLOCK.get())
            .parent(getExistingFile(ResourceLocation.withDefaultNamespace("block/block")))
            .texture("particle", ResourceLocation.fromNamespaceAndPath("neoforge", "white"))
            // Pass a custom anonymous builder that manually appends extra properties to the json output
            .customLoader((parent, existingFileHelper) ->
                    new CustomLoaderBuilder<>(ResourceLocation.fromNamespaceAndPath("numina", "obj"), parent, existingFileHelper, false) {
                        @Override
                        public com.google.gson.JsonObject toJson(com.google.gson.JsonObject json) {
                            // Always call super to ensure the loader type and visibility arrays are included
                            super.toJson(json);

                            // Manually append your custom loader parameters directly into the JSON root
                            json.addProperty("model", MPSConstants.MOD_ID + ":models/block/luxcapacitor/luxcapacitor.obj");
                            json.addProperty("flip_v", true);

                            return json;
                        }
                    });

        getBuilder(MPSBlocks.TINKER_TABLE_BLOCK.get())
            .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
            .texture("particle", mcLoc("block/coal_block"));
    }

    BlockModelBuilder getBuilder(Block block) {
        return getBuilder(BuiltInRegistries.BLOCK.getKey(block).getPath());
    }
}
