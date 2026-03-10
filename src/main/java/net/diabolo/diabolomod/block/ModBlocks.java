package net.diabolo.diabolomod.block;


import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.block.custom.ComponentTableBlock;
import net.diabolo.diabolomod.block.custom.CrystalInfuserBlock;
import net.diabolo.diabolomod.block.custom.GolemMakerControllerBlock;
import net.diabolo.diabolomod.block.custom.GolemMakerPartBlock;
import net.diabolo.diabolomod.item.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DiaboloMod.MODID);

    public static final DeferredBlock<Block> TOPAZ_BLOCK = BLOCKS.registerBlock(
            "topaz_block",
            Block::new, // factory qui reçoit les properties avec l'id déjà mis
            () -> BlockBehaviour.Properties.of()
                    .strength(2.4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
    );

    public static final DeferredBlock<Block> TOPAZ_ORE = BLOCKS.registerBlock(
            "topaz_ore",
            Block::new, // factory qui reçoit les properties avec l'id déjà mis
            () -> BlockBehaviour.Properties.of()
                    .strength(2.4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
    );

    public static final DeferredBlock<Block> TOPAZ_DEEPSLATE_ORE = BLOCKS.registerBlock(
            "topaz_deepslate_ore",
            Block::new, // factory qui reçoit les properties avec l'id déjà mis
            () -> BlockBehaviour.Properties.of()
                    .strength(3.4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)
    );

    public static final DeferredBlock<Block> BLUE_TOPAZ_BLOCK = BLOCKS.registerBlock(
            "blue_topaz_block",
            Block::new, // factory qui reçoit les properties avec l'id déjà mis
            () -> BlockBehaviour.Properties.of()
                    .strength(2.4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
    );

    public static final DeferredBlock<Block> CRYSTAL_INFUSER = BLOCKS.registerBlock(
            "crystal_infuser",
            CrystalInfuserBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(4f)
                    .requiresCorrectToolForDrops()
    );

    public static final DeferredBlock<Block> COMPONENT_TABLE = BLOCKS.registerBlock(
            "component_table",
            ComponentTableBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(4f)
                    .requiresCorrectToolForDrops()
    );

    public static final DeferredBlock<Block> GOLEM_MAKER_CONTROLLER = BLOCKS.registerBlock(
            "golem_maker_controller",
            GolemMakerControllerBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .strength(3f)
                    .requiresCorrectToolForDrops()
    );

    public static final DeferredBlock<Block> GOLEM_MAKER_CASING = BLOCKS.registerBlock(
            "golem_maker_casing",
            GolemMakerPartBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .strength(3f)
                    .requiresCorrectToolForDrops()
    );

    public static final DeferredBlock<Block> GOLEM_MAKER_FOUNDATION = BLOCKS.registerBlock(
            "golem_maker_foundation",
            GolemMakerPartBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .strength(3f)
                    .requiresCorrectToolForDrops()
    );

    public static final DeferredBlock<Block> GOLEM_MAKER_ASSEMBLER = BLOCKS.registerBlock(
            "golem_maker_assembler",
            GolemMakerPartBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .strength(3f)
                    .requiresCorrectToolForDrops()
    );

    public static final DeferredBlock<Block> GOLEM_MAKER_COOLER = BLOCKS.registerBlock(
            "golem_maker_cooler",
            GolemMakerPartBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .strength(3f)
                    .requiresCorrectToolForDrops()
    );

    public static final DeferredBlock<Block> GOLEM_MAKER_CATALYST = BLOCKS.registerBlock(
            "golem_maker_catalyst",
            GolemMakerPartBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .strength(3f)
                    .requiresCorrectToolForDrops()
    );

    public static final DeferredBlock<Block> GOLEM_MAKER_TRANSFORMER = BLOCKS.registerBlock(
            "golem_maker_transformer",
            GolemMakerPartBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .strength(3f)
                    .requiresCorrectToolForDrops()
    );

    static {
        ModItems.ITEMS.registerSimpleBlockItem("topaz_block", TOPAZ_BLOCK);
        ModItems.ITEMS.registerSimpleBlockItem("topaz_ore", TOPAZ_ORE);
        ModItems.ITEMS.registerSimpleBlockItem("topaz_deepslate_ore", TOPAZ_DEEPSLATE_ORE);
        ModItems.ITEMS.registerSimpleBlockItem("blue_topaz_block", BLUE_TOPAZ_BLOCK);
        ModItems.ITEMS.registerSimpleBlockItem("crystal_infuser", CRYSTAL_INFUSER);
        ModItems.ITEMS.registerSimpleBlockItem("component_table", COMPONENT_TABLE);
        ModItems.ITEMS.registerSimpleBlockItem("golem_maker_foundation", GOLEM_MAKER_FOUNDATION);
        ModItems.ITEMS.registerSimpleBlockItem("golem_maker_casing", GOLEM_MAKER_CASING);
        ModItems.ITEMS.registerSimpleBlockItem("golem_maker_controller", GOLEM_MAKER_CONTROLLER);
        ModItems.ITEMS.registerSimpleBlockItem("golem_maker_assembler", GOLEM_MAKER_ASSEMBLER);
        ModItems.ITEMS.registerSimpleBlockItem("golem_maker_cooler", GOLEM_MAKER_COOLER);
        ModItems.ITEMS.registerSimpleBlockItem("golem_maker_catalyst", GOLEM_MAKER_CATALYST);
        ModItems.ITEMS.registerSimpleBlockItem("golem_maker_transformer", GOLEM_MAKER_TRANSFORMER);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
