package net.diabolo.diabolomod.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record CrystalInfuserRecipe(Ingredient inputItem, ItemStack output) implements Recipe<CrystalInfuserRecipeInput> {
    // inputItem & output ==> Read From JSON File!
    // GrowthChamberRecipeInput --> INVENTORY of the Block Entity

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(CrystalInfuserRecipeInput crystalInfuserRecipeInput, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        return inputItem.test(crystalInfuserRecipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(CrystalInfuserRecipeInput crystalInfuserRecipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<CrystalInfuserRecipeInput>> getSerializer() {
        return ModRecipes.CRYSTAL_INFUSER_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<CrystalInfuserRecipeInput>> getType() {
        return ModRecipes.CRYSTAL_INFUSER_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(inputItem);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    public static class Serializer implements RecipeSerializer<CrystalInfuserRecipe> {
        public static final MapCodec<CrystalInfuserRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter(CrystalInfuserRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(CrystalInfuserRecipe::output)
        ).apply(inst, CrystalInfuserRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CrystalInfuserRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, CrystalInfuserRecipe::inputItem,
                        ItemStack.STREAM_CODEC, CrystalInfuserRecipe::output,
                        CrystalInfuserRecipe::new);

        @Override
        public MapCodec<CrystalInfuserRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CrystalInfuserRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
