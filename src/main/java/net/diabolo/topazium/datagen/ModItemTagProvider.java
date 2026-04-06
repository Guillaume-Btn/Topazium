package net.diabolo.topazium.datagen;

import net.diabolo.topazium.Topazium;
import net.diabolo.topazium.block.ModBlocks;
import net.diabolo.topazium.item.ModItems;
import net.diabolo.topazium.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Topazium.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {

        tag(ModTags.Items.TOPAZ_REPAIRABLE)
                .add(ModItems.TOPAZ.get());

        tag(ModTags.Items.BLUE_TOPAZ_REPAIRABLE)
                .add(ModItems.BLUE_TOPAZ.get());

        tag(ItemTags.SWORDS)
                .add(ModItems.TOPAZ_SWORD.get())
                .add(ModItems.BLUE_TOPAZ_SWORD.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.TOPAZ_PICKAXE.get())
                .add(ModItems.BLUE_TOPAZ_PICKAXE.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.TOPAZ_SHOVEL.get())
                .add(ModItems.BLUE_TOPAZ_SHOVEL.get());
        tag(ItemTags.AXES)
                .add(ModItems.TOPAZ_AXE.get())
                .add(ModItems.BLUE_TOPAZ_AXE.get());
        tag(ItemTags.HOES)
                .add(ModItems.TOPAZ_HOE.get())
                .add(ModItems.BLUE_TOPAZ_HOE.get());
        tag(ItemTags.SPEARS)
                .add(ModItems.BLUE_TOPAZ_SPEAR.get())
                .add(ModItems.TOPAZ_SPEAR.get());

        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.TOPAZ_HELMET.get())
                .add(ModItems.TOPAZ_CHESTPLATE.get())
                .add(ModItems.TOPAZ_LEGGINGS.get())
                .add(ModItems.TOPAZ_BOOTS.get());

        tag(ModTags.Items.CAN_BE_INFUSED)
                .add(ModItems.TOPAZ.get())
                .add(ModItems.BLUE_TOPAZ.get());

        tag(ModTags.Items.IS_INFUSER)
                .add(ModItems.COBALT_SOLUTION.get())
                .add(Items.GLASS_BOTTLE)
                .add(Items.BLUE_DYE);

        tag(ModTags.Items.CAN_BE_HAMMERED)
                .add(ModBlocks.TOPAZ_BLOCK.asItem())
                .add(ModItems.GOLEM_ARMS_BASIC.get())
                .add(ModItems.GOLEM_ARMS_MINER.get())
                .add(ModItems.GOLEM_ARMS_BLASTER.get())
                .add(ModItems.GOLEM_LEGS_BASIC.get())
                .add(ModItems.GOLEM_LEGS_WHEELS.get())
                .add(ModItems.GOLEM_HEAD_BASIC.get())
        ;
    }
}