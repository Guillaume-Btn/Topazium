package net.diabolo.diabolomod.recipe;

import net.diabolo.diabolomod.DiaboloMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, DiaboloMod.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, DiaboloMod.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CrystalInfuserRecipe>> CRYSTAL_INFUSER_SERIALIZER =
            SERIALIZERS.register("crystal_infuser", CrystalInfuserRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<CrystalInfuserRecipe>> CRYSTAL_INFUSER_TYPE =
            TYPES.register("crystal_infuser", () -> new RecipeType<CrystalInfuserRecipe>() {
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
