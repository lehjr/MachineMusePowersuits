package numina.client.model.block;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.registration.NuminaBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * Data provider for generating BlockState and Block Model JSON files.
 * Handles POWERED and WATERLOGGED properties simultaneously.
 * Target Version: NeoForge 21.1 (Minecraft 1.21.1)
 */
public class NuminaBlockStateProvider extends BlockStateProvider {
    public NuminaBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, NuminaConstants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerPoweredWaterloggedBlock(NuminaBlocks.CHARGING_BASE_BLOCK.get());
    }

    /**
     * Registers all 4 permutations of POWERED and WATERLOGGED states.
     * Uses custom model builders to define exact element properties and precise UV arrays.
     *
     * @param block The block instance to generate states and models for.
     */
    private void registerPoweredWaterloggedBlock(Block block) {
        // 1. Generate the custom unpowered model matching your schema
        BlockModelBuilder modelOff = models().withExistingParent("base_unpowered", mcLoc("block/block"))
            .ao(false) // Sets "ambientocclusion": false
            .texture("particle", modLoc("block/base_unpowered"))
            .element()
            .from(0, 0, 0) // Element lower bound [0, 0, 0]
            .to(16, 1, 16)  // Element upper bound [16, 1, 16]
            // Define UV face maps [u1, v1, u2, v2] mapping back to the #particle key
            .face(Direction.NORTH).texture("#particle").uvs(0, 0, 16, 1).end()
            .face(Direction.EAST).texture("#particle").uvs(0, 0, 16, 1).end()
            .face(Direction.SOUTH).texture("#particle").uvs(0, 0, 16, 1).end()
            .face(Direction.WEST).texture("#particle").uvs(0, 0, 16, 1).end()
            .face(Direction.UP).texture("#particle").uvs(0, 0, 16, 16).end()
            .face(Direction.DOWN).texture("#particle").uvs(0, 0, 16, 16).end()
            .end();

        // 2. Generate the custom powered model using the alternative powered texture variant
        BlockModelBuilder modelOn = models().withExistingParent("base_powered", mcLoc("block/block"))
            .ao(false)
            .texture("particle", modLoc("block/base_powered"))
            .element()
            .from(0, 0, 0)
            .to(16, 1, 16)
            .face(Direction.NORTH).texture("#particle").uvs(0, 0, 16, 1).end()
            .face(Direction.EAST).texture("#particle").uvs(0, 0, 16, 1).end()
            .face(Direction.SOUTH).texture("#particle").uvs(0, 0, 16, 1).end()
            .face(Direction.WEST).texture("#particle").uvs(0, 0, 16, 1).end()
            .face(Direction.UP).texture("#particle").uvs(0, 0, 16, 16).emissivity(15, 15).end()
            .face(Direction.DOWN).texture("#particle").uvs(0, 0, 16, 16).end()
            .end();

        // 3. Bind the state combinations to their specific models
        getVariantBuilder(block)
            // Unpowered Variants
            .partialState()
            .with(BlockStateProperties.POWERED, false)
            .with(BlockStateProperties.WATERLOGGED, false)
            .setModels(new ConfiguredModel(modelOff))

            .partialState()
            .with(BlockStateProperties.POWERED, false)
            .with(BlockStateProperties.WATERLOGGED, true)
            .setModels(new ConfiguredModel(modelOff))

            // Powered Variants
            .partialState()
            .with(BlockStateProperties.POWERED, true)
            .with(BlockStateProperties.WATERLOGGED, false)
            .setModels(new ConfiguredModel(modelOn))

            .partialState()
            .with(BlockStateProperties.POWERED, true)
            .with(BlockStateProperties.WATERLOGGED, true)
            .setModels(new ConfiguredModel(modelOn));
    }
}
