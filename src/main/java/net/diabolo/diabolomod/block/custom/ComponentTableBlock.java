package net.diabolo.diabolomod.block.custom;

import com.mojang.serialization.MapCodec;
import net.diabolo.diabolomod.block.ModBlocks;
import net.diabolo.diabolomod.entity.custom.component_table.ComponentTableBlockEntity;
import net.diabolo.diabolomod.item.custom.HammerItem;
import net.diabolo.diabolomod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.NonNull;

public class ComponentTableBlock extends BaseEntityBlock {
    public static final MapCodec<ComponentTableBlock> CODEC = simpleCodec(ComponentTableBlock::new);

    public ComponentTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.MODEL;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
        // On crée bien une instance de TON InfuserBlockEntity
        return new ComponentTableBlockEntity(blockPos, blockState);
    }

    @Override
    protected @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, Level level, @NonNull BlockPos pos,
                                                   @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {

        if (level.isClientSide()) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof ComponentTableBlockEntity shaper)) return InteractionResult.FAIL;

        // 1. SI LE JOUEUR A UN MARTEAU -> ON FAÇONNE
        if (stack.getItem() instanceof HammerItem) {
            // Nouvelle façon de vérifier si le slot 0 n'est pas vide
            if (!shaper.getItemHandler().getResource(0).isEmpty()) {
                boolean crafted = shaper.hammerHit();
                if (shaper.canHit()) {
                    level.playSound(null, pos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 0.5f, 1.2f);
                    int amount = (int) (Math.random() * 10 + 10);
                    stack.hurtAndBreak(amount, player, hand);
                }
                if (crafted) {
                    level.playSound(null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1f, 1f);
                    player.displayClientMessage(Component.literal("Pièce terminée !"), true);
                    ((ServerLevel) level).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, ModBlocks.TOPAZ_BLOCK.get().defaultBlockState()),
                            pos.getX() + 0.5, pos.getY() + 1.0,
                            pos.getZ() + 0.5, 5, 0, 0, 0, 1);
                    int amount = (int) (Math.random() * 10 + 20);
                    stack.hurtAndBreak(amount, player, hand);
                }
                return InteractionResult.SUCCESS;
            }
        }

        // 2. SI LE JOUEUR A UN BLOC DE TOPAZE -> ON POSE
        if (!stack.isEmpty() && shaper.getItemHandler().getResource(0).isEmpty()) {
            if (stack.is(ModTags.Items.CAN_BE_HAMMERED)) {

                try (Transaction tx = Transaction.openRoot()) {
                    // On insère 1 item depuis le stack du joueur
                    int inserted = shaper.getItemHandler().insert(0, ItemResource.of(stack), 1, tx);

                    if (inserted == 1) {
                        tx.commit(); // Valide l'insertion dans la machine
                        stack.shrink(1); // Retire 1 du stack dans la main du joueur
                        level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1f, 1f);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            return InteractionResult.FAIL;
        }

        // 3. SI MAIN VIDE + SNEAK -> ON RÉCUPÈRE LE BLOC
        if (stack.isEmpty() && player.isCrouching()) {
            ItemResource resourceToExtract = shaper.getItemHandler().getResource(0);

            if (!resourceToExtract.isEmpty()) {
                try (Transaction tx = Transaction.openRoot()) {
                    // On extrait 1 item
                    int extracted = shaper.getItemHandler().extract(0, resourceToExtract, 1, tx);

                    if (extracted == 1) {
                        tx.commit(); // Valide l'extraction de la machine
                        player.addItem(resourceToExtract.toStack(1)); // Donne l'item au joueur
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            return InteractionResult.FAIL;
        }

        // 4. SI MAIN VIDE (Sans Sneak) -> ON CHANGE DE PATRON
        if (stack.isEmpty()) {
            shaper.cyclePattern();
            String patternName = shaper.getCurrentPatternOutput().getName().getString();
            player.displayClientMessage(Component.literal("Patron : " + patternName), true);
            level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1f, 1f);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(HorizontalDirectionalBlock.FACING,
                context.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NonNull BlockState rotate(BlockState state, @NonNull Rotation rot) {
        return state.cycle(HorizontalDirectionalBlock.FACING);
    }

    @Override
    public @NonNull BlockState mirror(BlockState state, @NonNull Mirror mirror) {
        return state.mirror(mirror);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HorizontalDirectionalBlock.FACING);
    }
}
