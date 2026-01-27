package net.diabolo.diabolomod.datagen;

import net.diabolo.diabolomod.DiaboloMod;
import net.minecraft.data.PackOutput;
import net.diabolo.diabolomod.block.ModBlocks;
import net.diabolo.diabolomod.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, DiaboloMod.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.TOPAZ.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_TOPAZ.get(), ModelTemplates.FLAT_ITEM);

        /*
        itemModels.generateFlatItem(ModItems.BISMUTH_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.BISMUTH_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.BISMUTH_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.BISMUTH_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.BISMUTH_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.BISMUTH_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModels.generateTrimmableItem(ModItems.BISMUTH_HELMET.get(), ModArmorMaterials.BISMUTH, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModels.generateTrimmableItem(ModItems.BISMUTH_CHESTPLATE.get(), ModArmorMaterials.BISMUTH, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModels.generateTrimmableItem(ModItems.BISMUTH_LEGGINGS.get(), ModArmorMaterials.BISMUTH, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModels.generateTrimmableItem(ModItems.BISMUTH_BOOTS.get(), ModArmorMaterials.BISMUTH, ItemModelGenerators.TRIM_PREFIX_BOOTS,  false);
        */


        /* BLOCKS */
        blockModels.createTrivialCube(ModBlocks.TOPAZ_ORE.get());
        blockModels.createTrivialCube(ModBlocks.TOPAZ_DEEPSLATE_ORE.get());

        blockModels.family(ModBlocks.TOPAZ_BLOCK.get())
//                .fence(ModBlocks.BISMUTH_FENCE.get())
//                .fenceGate(ModBlocks.BISMUTH_FENCE_GATE.get())
//                .wall(ModBlocks.BISMUTH_WALL.get())
//                .stairs(ModBlocks.BISMUTH_STAIRS.get())
//                .slab(ModBlocks.BISMUTH_SLAB.get())
//                .button(ModBlocks.BISMUTH_BUTTON.get())
//                .pressurePlate(ModBlocks.BISMUTH_PRESSURE_PLATE.get())
//                .door(ModBlocks.BISMUTH_DOOR.get())
//                .trapdoor(ModBlocks.BISMUTH_TRAPDOOR.get()
                ;

    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return ModItems.ITEMS.getEntries().stream();
        //.filter(x -> x.get() != ModBlocks.PEDESTAL.asItem() && x.get() != ModBlocks.CHAIR.asItem()
        //                && !x.is(ModItems.TOMAHAWK))
        // EXEMPLE POUR METTRE DES ITEMS QUI S'AFFICHE DIFFEREMENT
    }
}