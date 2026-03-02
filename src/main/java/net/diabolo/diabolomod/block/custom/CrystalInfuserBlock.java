package net.diabolo.diabolomod.block.custom;

import com.mojang.serialization.MapCodec;
import net.diabolo.diabolomod.entity.ModBlockEntities;
import net.diabolo.diabolomod.entity.custom.crystal_infuser.CrystalInfuserBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class CrystalInfuserBlock extends BaseEntityBlock {
    public static final MapCodec<CrystalInfuserBlock> CODEC = simpleCodec(CrystalInfuserBlock::new);

    public CrystalInfuserBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    /* BLOCK ENTITY */

    @Override
    protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
        // On crée bien une instance de TON InfuserBlockEntity
        return new CrystalInfuserBlockEntity(blockPos, blockState);
    }

    @Override
    protected @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, Level level, @NonNull BlockPos pos,
                                                   @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {

        if (level.getBlockEntity(pos) instanceof CrystalInfuserBlockEntity crystalInfuserBlockEntity) {

            if (!level.isClientSide()) {
                // Cette ligne demande au serveur d'ouvrir l'écran pour le joueur
                player.openMenu(new SimpleMenuProvider(
                        crystalInfuserBlockEntity,
                        Component.translatable("block.diabolomod.crystal_infuser") // Le titre en haut de la fenêtre
                ), pos);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NonNull Level level, @NonNull BlockState state, @NonNull BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.CRYSTAL_INFUSER_BE.get(),
                (level1, blockPos, blockState, blockEntity) -> blockEntity.tick(level1, blockPos, blockState));
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(HorizontalDirectionalBlock.FACING,
                context.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NonNull BlockState rotate(BlockState state, @NonNull Rotation rot) {
        return state.cycle(HorizontalDirectionalBlock.FACING); // ✅ CYCLE au lieu de rotate()
    }

    @Override
    public @NonNull BlockState mirror(BlockState state, @NonNull Mirror mirror) {
        return state.mirror(mirror); // ✅ Ça c'est correct
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HorizontalDirectionalBlock.FACING); // ✅ OBLIGATOIRE !
    }
}
