package powersuits.client.model.block;

import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.registration.MPSBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MPSBlockStateProvider extends BlockStateProvider {
    public MPSBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MPSConstants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Grab references to your generated block models
        ModelFile tinkerTableModel = models().getExistingFile(modLoc("block/tinkertable"));
        ModelFile luxCapacitorModel = models().getExistingFile(modLoc("block/luxcapacitor"));

        // 1. Tinkertable BlockState (Horizontal Facing)
        // NeoForge automatically ignores unmapped properties like waterlogged,
        // which matches all waterlogged true/false states to these models.
        horizontalBlock(MPSBlocks.TINKER_TABLE_BLOCK.get(), tinkerTableModel, 0);

        // 2. Lux Capacitor BlockState (6-way Directional with custom rotations)
        getVariantBuilder(MPSBlocks.LUX_CAPACITOR_BLOCK.get())
            .forAllStates(state -> {
                Direction facing = state.getValue(BlockStateProperties.FACING);
                int xRot = 0;
                int yRot = 0;

                switch (facing) {
                case NORTH -> { xRot = 0;   yRot = 180; }
                case SOUTH -> { xRot = 0;   yRot = 0;   }
                case EAST  -> { xRot = 0;   yRot = 270; }
                case WEST  -> { xRot = 0;   yRot = 90;  }
                case UP    -> { xRot = 90;  yRot = 0;   }
                case DOWN  -> { xRot = 270; yRot = 0;   }
                }

                return ConfiguredModel.builder()
                    .modelFile(luxCapacitorModel)
                    .rotationX(xRot)
                    .rotationY(yRot)
                    .build();
            });
    }
}
