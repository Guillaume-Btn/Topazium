package net.diabolo.diabolomod.block;


import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.item.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS= DeferredRegister.createBlocks(DiaboloMod.MODID);

    public static final  DeferredBlock<Block> TOPAZ_BLOCK = BLOCKS.registerBlock(//seul methode qui marche
            "topaz_block",
            Block::new, // factory qui reçoit les properties avec l'id déjà mis
            BlockBehaviour.Properties.of()
                    .strength(2.4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
    );
    public static final  DeferredBlock<Block> TOPAZ_ORE = BLOCKS.registerBlock(//seul methode qui marche
            "topaz_ore",
            Block::new, // factory qui reçoit les properties avec l'id déjà mis
            BlockBehaviour.Properties.of()
                    .strength(2.4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
    );

    public static final  DeferredBlock<Block> TOPAZ_DEEPSLATE_ORE = BLOCKS.registerBlock(//seul methode qui marche
            "topaz_deepslate_ore",
            Block::new, // factory qui reçoit les properties avec l'id déjà mis
            BlockBehaviour.Properties.of()
                    .strength(3.4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)
    );

    static {
        ModItems.ITEMS.registerSimpleBlockItem("topaz_block", TOPAZ_BLOCK);
        ModItems.ITEMS.registerSimpleBlockItem("topaz_ore", TOPAZ_ORE);
        ModItems.ITEMS.registerSimpleBlockItem("topaz_deepslate_ore", TOPAZ_DEEPSLATE_ORE);
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
