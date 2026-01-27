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

    public static final  DeferredBlock<Block> BISMUTH_BLOCK = BLOCKS.registerBlock(//seul methode qui marche
            "bismuth_block",
            Block::new, // factory qui reçoit les properties avec l'id déjà mis
            BlockBehaviour.Properties.of()
                    .strength(2.0f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
    );

    public static final  DeferredBlock<Block> BISMUTH_ORE = BLOCKS.registerBlock(//seul methode qui marche
            "bismuth_ore",
            Block::new, BlockBehaviour.Properties.of()
                    .strength(1.0f) // duree pour casser
                    .requiresCorrectToolForDrops()
    );


    static {
        ModItems.ITEMS.registerSimpleBlockItem("bismuth_block", BISMUTH_BLOCK);
        ModItems.ITEMS.registerSimpleBlockItem("bismuth_ore", BISMUTH_ORE);
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
