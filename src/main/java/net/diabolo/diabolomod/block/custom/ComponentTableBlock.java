package net.diabolo.diabolomod.block.custom;

import com.mojang.serialization.MapCodec;
import net.diabolo.diabolomod.entity.custom.component_table.ComponentTableBlockEntity;
import net.diabolo.diabolomod.item.custom.HammerItem;
import net.diabolo.diabolomod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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

public class ComponentTableBlock extends BaseEntityBlock {
    public static final MapCodec<ComponentTableBlock> CODEC = simpleCodec(ComponentTableBlock::new);

    public ComponentTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        // On crée bien une instance de TON InfuserBlockEntity
        return new ComponentTableBlockEntity(blockPos, blockState);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                          Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (level.isClientSide()) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof ComponentTableBlockEntity shaper)) return InteractionResult.PASS;

        // 1. SI LE JOUEUR A UN MARTEAU -> ON FAÇONNE
        if (stack.getItem() instanceof HammerItem) {
            if (!shaper.getItemHandler().getStackInSlot(0).isEmpty()) {
                // Particules (On pourrait ajouter un packet pour ça plus tard)
                boolean crafted = shaper.hammerHit();
                if (shaper.canHit()) {
                    level.playSound(null, pos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 0.5f, 1.2f); // Son métallique lourd
                    int amount = (int) (Math.random() * 10 + 10); // entre 10 et 20
                    stack.hurtAndBreak(amount, player, hand);
                }
                if (crafted) {
                    level.playSound(null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1f, 1f);
                    player.displayClientMessage(Component.literal("Pièce terminée !"), true);
                    int amount = (int) (Math.random() * 10 + 20); // entre 20 et 30
                    stack.hurtAndBreak(amount, player, hand);
                }
                return InteractionResult.SUCCESS;
            }
        }
        // 2. SI LE JOUEUR A UN BLOC DE TOPAZE (ou autre input) -> ON POSE
        if (!stack.isEmpty() && shaper.getItemHandler().getStackInSlot(0).isEmpty()) {
            if (stack.is(ModTags.Items.CAN_BE_HAMMERED)) {
                shaper.getItemHandler().insertItem(0, stack.split(1), false);
                level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1f, 1f);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }

        // 3. SI MAIN VIDE + SNEAK -> ON RÉCUPÈRE LE BLOC
        if (stack.isEmpty() && player.isCrouching()) {
            ItemStack extracted = shaper.getItemHandler().extractItem(0, 1, false);
            player.addItem(extracted);
            return InteractionResult.SUCCESS;
        }

        // 4. SI MAIN VIDE (Sans Sneak) -> ON CHANGE DE PATRON
        if (stack.isEmpty()) {
            shaper.cyclePattern();
            // Affiche le patron sélectionné au-dessus de la hotbar
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
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.cycle(HorizontalDirectionalBlock.FACING); // ✅ CYCLE au lieu de rotate()
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.mirror(mirror); // ✅ Ça c'est correct
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HorizontalDirectionalBlock.FACING); // ✅ OBLIGATOIRE !
    }
}
