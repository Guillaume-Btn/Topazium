package net.diabolo.diabolomod.datagen;

import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.item.ModArmorMaterials;
import net.minecraft.client.data.models.model.*;
import net.minecraft.data.PackOutput;
import net.diabolo.diabolomod.block.ModBlocks;
import net.diabolo.diabolomod.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;


import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, DiaboloMod.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.TOPAZ.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_TOPAZ.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.COBALT_SOLUTION.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.COBALT_POWDER.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.TOPAZ_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.TOPAZ_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.TOPAZ_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.TOPAZ_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.TOPAZ_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.TOPAZ_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.TOPAZ_SPEAR.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModels.generateTrimmableItem(ModItems.TOPAZ_HELMET.get(), ModArmorMaterials.TOPAZ, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModels.generateTrimmableItem(ModItems.TOPAZ_CHESTPLATE.get(), ModArmorMaterials.TOPAZ, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModels.generateTrimmableItem(ModItems.TOPAZ_LEGGINGS.get(), ModArmorMaterials.TOPAZ, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModels.generateTrimmableItem(ModItems.TOPAZ_BOOTS.get(), ModArmorMaterials.TOPAZ, ItemModelGenerators.TRIM_PREFIX_BOOTS,  false);


        /* BLOCKS */
        blockModels.createTrivialCube(ModBlocks.TOPAZ_ORE.get());
        blockModels.createTrivialCube(ModBlocks.TOPAZ_DEEPSLATE_ORE.get());

        //itemModels.generateFlatItem(ModBlocks.CRYSTAL_INFUSER.asItem(), ModelTemplates.FLAT_ITEM);

        blockModels.family(ModBlocks.TOPAZ_BLOCK.get())
//                .fence(ModBlocks.TOPAZ_FENCE.get())
//                .fenceGate(ModBlocks.TOPAZ_FENCE_GATE.get())
//                .wall(ModBlocks.TOPAZ_WALL.get())
//                .stairs(ModBlocks.TOPAZ_STAIRS.get())
//                .slab(ModBlocks.TOPAZ_SLAB.get())
//                .button(ModBlocks.TOPAZ_BUTTON.get())
//                .pressurePlate(ModBlocks.TOPAZ_PRESSURE_PLATE.get())
//                .door(ModBlocks.TOPAZ_DOOR.get())
//                .trapdoor(ModBlocks.TOPAZ_TRAPDOOR.get()
                ;

        blockModels.createTrivialCube(ModBlocks.BLUE_TOPAZ_BLOCK.get());
        itemModels.generateFlatItem(ModItems.BLUE_TOPAZ.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_BLUE_TOPAZ.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BLUE_TOPAZ_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.BLUE_TOPAZ_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.BLUE_TOPAZ_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.BLUE_TOPAZ_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.BLUE_TOPAZ_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.BLUE_TOPAZ_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.BLUE_TOPAZ_SPEAR.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModels.generateTrimmableItem(ModItems.BLUE_TOPAZ_HELMET.get(), ModArmorMaterials.BLUE_TOPAZ, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModels.generateTrimmableItem(ModItems.BLUE_TOPAZ_CHESTPLATE.get(), ModArmorMaterials.BLUE_TOPAZ, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModels.generateTrimmableItem(ModItems.BLUE_TOPAZ_LEGGINGS.get(), ModArmorMaterials.BLUE_TOPAZ, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModels.generateTrimmableItem(ModItems.BLUE_TOPAZ_BOOTS.get(), ModArmorMaterials.BLUE_TOPAZ, ItemModelGenerators.TRIM_PREFIX_BOOTS,  false);

        blockModels.createTrivialBlock(
                ModBlocks.COMPONENT_TABLE.get(),
                TexturedModel.CUBE_TOP_BOTTOM
        );
    }

    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {

        return ModBlocks.BLOCKS.getEntries().stream()
                // AJOUTE CE FILTRE :
                .filter(b -> !b.get().equals(ModBlocks.CRYSTAL_INFUSER.get()))
                .map(block -> (Holder<Block>) block);
    }

    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return ModItems.ITEMS.getEntries().stream()
        .filter(x -> !x.is(ModItems.GOLEM_ARMS_BASIC.getId())
                && !x.is(ModItems.GOLEM_HEAD_BASIC.getId())
                && !x.is(ModItems.GOLEM_ARMS_BLASTER.getId())
                && !x.is(ModItems.GOLEM_ARMS_MINER.getId())
                && !x.is(ModItems.GOLEM_LEGS_BASIC.getId())
                && !x.is(ModItems.GOLEM_LEGS_WHEELS.getId())
        );
//         EXEMPLE POUR METTRE DES ITEMS QUI S'AFFICHE DIFFEREMENT
    }
}