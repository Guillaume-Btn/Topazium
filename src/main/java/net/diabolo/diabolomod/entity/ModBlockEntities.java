package net.diabolo.diabolomod.entity;

import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, DiaboloMod.MODID);

    public static final Supplier<BlockEntityType<CrystalInfuserBlockEntity>> CRYSTAL_INFUSER_BE =
            BLOCK_ENTITIES.register("infuser_be", () -> new BlockEntityType<>(
                    CrystalInfuserBlockEntity::new, ModBlocks.CRYSTAL_INFUSER.get()));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
