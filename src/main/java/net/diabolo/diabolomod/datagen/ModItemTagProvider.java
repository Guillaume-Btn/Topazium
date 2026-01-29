package net.diabolo.diabolomod.datagen;

import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.item.ModItems;
import net.diabolo.diabolomod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
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
    }
}