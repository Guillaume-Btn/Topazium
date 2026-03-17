package net.diabolo.diabolomod.block.custom;

import com.mojang.serialization.MapCodec;
import net.diabolo.diabolomod.entity.custom.golem_maker.GolemMakerControllerEntity;
import net.diabolo.diabolomod.entity.custom.golem_maker.GolemMakerPartEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class GolemMakerPartBlock extends BaseEntityBlock {
    public static final BooleanProperty ASSEMBLED = BooleanProperty.create("assembled");
    public static final MapCodec<GolemMakerPartBlock> CODEC = simpleCodec(GolemMakerPartBlock::new);

    public GolemMakerPartBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ASSEMBLED, false));
    }

    @Override
    protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ASSEMBLED);
    }

    @Override
    public @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new GolemMakerPartEntity(pos, state);
    }

    @Override
    public @NonNull InteractionResult useWithoutItem(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull BlockHitResult hitResult) {
        if (state.getValue(ASSEMBLED)) {
            if (!level.isClientSide()) {
                BlockEntity be = level.getBlockEntity(pos);
                if (be instanceof GolemMakerPartEntity partEntity) {
                    GolemMakerControllerEntity master = partEntity.getMasterEntity();

                    if (master != null) {
                        BlockPos masterPos = partEntity.getMasterPos();
                        BlockState masterState = level.getBlockState(masterPos);
                        Block b = masterState.getBlock();

                        if (b instanceof GolemMakerControllerBlock c) {
                            return c.useWithoutItem(masterState, level, masterPos, player, hitResult);
                        }
                    }
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

}
