package net.diabolo.diabolomod.block.custom;

import com.mojang.serialization.MapCodec;
import net.diabolo.diabolomod.entity.CrystalInfuserBlockEntity; // L'import correct de TA classe
import net.diabolo.diabolomod.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class InfuserBlock extends BaseEntityBlock {
    public static final MapCodec<InfuserBlock> CODEC = simpleCodec(InfuserBlock::new);

    public InfuserBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    /* BLOCK ENTITY */

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        // On crée bien une instance de TON InfuserBlockEntity
        return new CrystalInfuserBlockEntity(blockPos, blockState);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                          Player player, InteractionHand hand, BlockHitResult hitResult) {

        // 1. On vérifie que c'est bien notre BlockEntity
        if (level.getBlockEntity(pos) instanceof CrystalInfuserBlockEntity crystalInfuserBlockEntity) {

            // 2. On ouvre le menu UNIQUEMENT côté serveur (le client suit automatiquement)
            if (!level.isClientSide()) {
                // Cette ligne demande au serveur d'ouvrir l'écran pour le joueur
                player.openMenu(new SimpleMenuProvider(
                        crystalInfuserBlockEntity,
                        Component.literal("Infuseur Cristallin") // Le titre en haut de la fenêtre
                ), pos);
            }

            // 3. On dit "C'est bon, j'ai géré le clic"
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()) {
            return null;
        }

        return createTickerHelper(blockEntityType, ModBlockEntities.CRYSTAL_INFUSER_BE.get(),
                (level1, blockPos, blockState, blockEntity) -> blockEntity.tick(level1, blockPos, blockState));
    }
}
