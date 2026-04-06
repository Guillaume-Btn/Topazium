package net.diabolo.topazium.recipe;

import net.diabolo.topazium.Topazium;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Topazium.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, Topazium.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CrystalInfuserRecipe>> CRYSTAL_INFUSER_SERIALIZER =
            SERIALIZERS.register("crystal_infuser", () -> CrystalInfuserRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeType<?>, RecipeType<CrystalInfuserRecipe>> CRYSTAL_INFUSER_TYPE =
            TYPES.register("crystal_infuser", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "crystal_infuser";
                }
            });


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
