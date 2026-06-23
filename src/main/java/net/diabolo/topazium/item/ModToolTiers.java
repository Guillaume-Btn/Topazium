package net.diabolo.topazium.item;

import net.diabolo.topazium.util.ModTags;
import net.minecraft.world.item.ToolMaterial;

public class ModToolTiers {
    public static final ToolMaterial TOPAZ= new ToolMaterial(ModTags.Blocks.INCORRECT_FOR_TOPAZ_TOOL,
            750,7.0f,2.5f,14,ModTags.Items.TOPAZ_REPAIRABLE);

    public static final ToolMaterial BLUE_TOPAZ= new ToolMaterial(ModTags.Blocks.INCORRECT_FOR_BLUE_TOPAZ_TOOL,
            2000,8.5f,3.5f,14,ModTags.Items.BLUE_TOPAZ_REPAIRABLE);

//     IRON new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6.0F, 2.0F, 14,ItemTags.IRON_TOOL_MATERIALS);
//     DIAMAND new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 8.0F, 3.0F, 10, ItemTags.DIAMOND_TOOL_MATERIALS);
//     NETHERITE new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 9.0F, 4.0F, 15, ItemTags.NETHERITE_TOOL_MATERIALS);

}
