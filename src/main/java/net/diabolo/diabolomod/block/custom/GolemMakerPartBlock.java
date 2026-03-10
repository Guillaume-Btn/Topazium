package net.diabolo.diabolomod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

// Pour l'instant, c'est juste un Block classique.
// Plus tard, on en fera un EntityBlock pour stocker la position du Maître.
public class GolemMakerPartBlock extends Block {

    // On crée la propriété booléenne (true/false)
    public static final BooleanProperty ASSEMBLED = BooleanProperty.create("assembled");

    public static final MapCodec<GolemMakerPartBlock> CODEC = simpleCodec(GolemMakerPartBlock::new);

    public GolemMakerPartBlock(Properties properties) {
        super(properties);
        // On définit l'état par défaut (non assemblé)
        this.registerDefaultState(this.stateDefinition.any().setValue(ASSEMBLED, false));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    // On ajoute la propriété à la définition du bloc (OBLIGATOIRE !)
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ASSEMBLED);
    }
}