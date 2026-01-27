package net.diabolo.diabolomod.datagen;

import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.item.ModItems;
import net.diabolo.diabolomod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, DiaboloMod.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(ModTags.Items.TOPAZ_REPAIRABLE)
                .add(ModItems.TOPAZ.get());

//        tag(ItemTags.SWORDS)
//                .add(ModItems.BISMUTH_SWORD.get());
//        tag(ItemTags.PICKAXES)
//                .add(ModItems.BISMUTH_PICKAXE.get());
//        tag(ItemTags.SHOVELS)
//                .add(ModItems.BISMUTH_SHOVEL.get());
//        tag(ItemTags.AXES)
//                .add(ModItems.BISMUTH_AXE.get());
//        tag(ItemTags.HOES)
//                .add(ModItems.BISMUTH_HOE.get());
    }
}