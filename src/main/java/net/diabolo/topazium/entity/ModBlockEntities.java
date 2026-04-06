package net.diabolo.topazium.entity;

import net.diabolo.topazium.Topazium;
import net.diabolo.topazium.block.ModBlocks;
import net.diabolo.topazium.entity.custom.component_table.ComponentTableBlockEntity;
import net.diabolo.topazium.entity.custom.crystal_infuser.CrystalInfuserBlockEntity;
import net.diabolo.topazium.entity.custom.golem_maker.GolemMakerControllerEntity;
import net.diabolo.topazium.entity.custom.golem_maker.GolemMakerPartEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Topazium.MODID);

    public static final Supplier<BlockEntityType<CrystalInfuserBlockEntity>> CRYSTAL_INFUSER_BE =
            BLOCK_ENTITIES.register("infuser_be", () -> new BlockEntityType<>(
                    CrystalInfuserBlockEntity::new, ModBlocks.CRYSTAL_INFUSER.get()));

    public static final Supplier<BlockEntityType<ComponentTableBlockEntity>> COMPONENT_TABLE_BE =
            BLOCK_ENTITIES.register("component_table_be", () -> new BlockEntityType<>(
                    ComponentTableBlockEntity::new, ModBlocks.COMPONENT_TABLE.get()));

    public static final Supplier<BlockEntityType<GolemMakerControllerEntity>> GOLEM_MAKER_CONTROLLER_BE =
            BLOCK_ENTITIES.register("golem_maker_controller_be", () -> new BlockEntityType<>(
                    GolemMakerControllerEntity::new, ModBlocks.GOLEM_MAKER_CONTROLLER.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GolemMakerPartEntity>> GOLEM_MAKER_PART_BE =
            BLOCK_ENTITIES.register("golem_maker_part", () -> new BlockEntityType<>(
                    GolemMakerPartEntity::new,
                    ModBlocks.GOLEM_MAKER_CASING.get(),
                    ModBlocks.GOLEM_MAKER_FOUNDATION.get(),
                    ModBlocks.GOLEM_MAKER_COOLER.get(),
                    ModBlocks.GOLEM_MAKER_ASSEMBLER.get(),
                    ModBlocks.GOLEM_MAKER_TRANSFORMER.get(),
                    ModBlocks.GOLEM_MAKER_CATALYST.get()
            ));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
