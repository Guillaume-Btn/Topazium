package net.diabolo.diabolomod.datagen;

import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.block.ModBlocks;
import net.diabolo.diabolomod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, DiaboloMod.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.TOPAZ_BLOCK.get())
                .add(ModBlocks.TOPAZ_ORE.get())
                .add(ModBlocks.TOPAZ_DEEPSLATE_ORE.get())
                .add(ModBlocks.BLUE_TOPAZ_BLOCK.get())
                .add(ModBlocks.CRYSTAL_INFUSER.get())
                .add(ModBlocks.COMPONENT_TABLE.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.TOPAZ_ORE.get())
                .add(ModBlocks.TOPAZ_BLOCK.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.TOPAZ_DEEPSLATE_ORE.get())
                .add(ModBlocks.BLUE_TOPAZ_BLOCK.get());


        tag(ModTags.Blocks.NEEDS_TOPAZ_TOOL)
                .add(ModBlocks.TOPAZ_DEEPSLATE_ORE.get())
                .add(ModBlocks.CRYSTAL_INFUSER.get())
                .add(ModBlocks.COMPONENT_TABLE.get())
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_TOPAZ_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTags.Blocks.NEEDS_TOPAZ_TOOL);

        tag(ModTags.Blocks.NEEDS_BLUE_TOPAZ_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_BLUE_TOPAZ_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
                .remove(ModTags.Blocks.NEEDS_BLUE_TOPAZ_TOOL);

    }
}
