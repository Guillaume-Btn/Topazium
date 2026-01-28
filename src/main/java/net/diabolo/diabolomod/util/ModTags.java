package net.diabolo.diabolomod.util;

import net.diabolo.diabolomod.DiaboloMod;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks{
        public static final TagKey<Block> NEEDS_TOPAZ_TOOL=createTag("needs_topaz_tool");
        public static final TagKey<Block> INCORRECT_FOR_TOPAZ_TOOL=createTag("incorrect_for_topaz_tool");


        public static final TagKey<Block> NEEDS_BLUE_TOPAZ_TOOL=createTag("needs_blue_topaz_tool");
        public static final TagKey<Block> INCORRECT_FOR_BLUE_TOPAZ_TOOL=createTag("incorrect_for_blue_topaz_tool");

        private static TagKey<Block> createTag(String name){
            return BlockTags.create(Identifier.fromNamespaceAndPath(DiaboloMod.MODID,name));
        }
    }

    public static class Items{
        public static final TagKey<Item> TOPAZ_REPAIRABLE =createTag("topaz_repair");
        public static final TagKey<Item> BLUE_TOPAZ_REPAIRABLE =createTag("blue_topaz_repair");

        private static TagKey<Item> createTag(String name){
            return ItemTags.create(Identifier.fromNamespaceAndPath(DiaboloMod.MODID,name));
        }
    }
}