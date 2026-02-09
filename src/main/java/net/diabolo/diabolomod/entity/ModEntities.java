package net.diabolo.diabolomod.entity;

import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.entity.custom.TopazGolemEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, DiaboloMod.MODID);

    public static ResourceKey<EntityType<?>> TOPAZ_GOLEM_KEY = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(DiaboloMod.MODID,"topaz_golem"));

    public static final Supplier<EntityType<TopazGolemEntity>> TOPAZ_GOLEM =
            ENTITY_TYPES.register("topaz_golem", () -> EntityType.Builder.of(TopazGolemEntity::new, MobCategory.MISC)
                    .sized(1.4f, 2.7f).clientTrackingRange(10).build(TOPAZ_GOLEM_KEY));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
