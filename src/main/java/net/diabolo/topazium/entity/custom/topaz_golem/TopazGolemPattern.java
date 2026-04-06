package net.diabolo.topazium.entity.custom.topaz_golem;

import net.diabolo.topazium.block.ModBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;

import javax.annotation.Nullable;

public class TopazGolemPattern {
    @Nullable
    private static BlockPattern topazGolemPattern;

    public static BlockPattern getTopazGolemPattern() {
        if (topazGolemPattern == null) {
            topazGolemPattern = BlockPatternBuilder.start()
                    .aisle("~^~", "#A#", "~#~") // ^ = Tête, # = Corps, ~ = Air/Rien
                    .where('^', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.CARVED_PUMPKIN)))
                    .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.TOPAZ_BLOCK.get()))) // Ton bloc
                    .where('A', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.BLUE_TOPAZ_BLOCK.get()))) // Ton bloc
                    .where('~', BlockInWorld.hasState(BlockStatePredicate.ANY)) // Accepte l'air ou n'importe quoi autour
                    .build();
        }
        return topazGolemPattern;
    }
}

