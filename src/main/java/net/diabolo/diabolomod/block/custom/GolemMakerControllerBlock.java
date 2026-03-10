package net.diabolo.diabolomod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

// On étend HorizontalDirectionalBlock pour avoir la propriété FACING (Nord, Sud, Est, Ouest)
public class GolemMakerControllerBlock extends HorizontalDirectionalBlock {

    public static final BooleanProperty ASSEMBLED = BooleanProperty.create("assembled");
    public static final MapCodec<GolemMakerControllerBlock> CODEC = simpleCodec(GolemMakerControllerBlock::new);

    public GolemMakerControllerBlock(Properties properties) {
        super(properties);
        // État par défaut : non assemblé, regarde vers le Nord
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ASSEMBLED, false));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    // Définit la direction quand le joueur pose le bloc (il regarde vers le joueur)
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ASSEMBLED);
    }
}
