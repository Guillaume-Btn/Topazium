package net.diabolo.diabolomod.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public record CrystalInfuserRecipe(
        Ingredient inputItem,
        int inputCount,
        Ingredient fuelItem,
        int fuelCount,
        ItemStack output,
        ItemStack outputBottle,
        int outputCount
) implements Recipe<CrystalInfuserRecipeInput> {
    // inputItem & output ==> Read From JSON File!
    // GrowthChamberRecipeInput --> INVENTORY of the Block Entity

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        list.add(fuelItem);
        return list;
    }

    @Override
    public boolean matches(CrystalInfuserRecipeInput input, Level level) {
        if (level.isClientSide()) return false;
        // Vérifie input ET fuel (slot 0 et slot 1)
        return inputItem.test(input.getItem(0)) && fuelItem.test(input.getItem(1));
    }

    // NOUVEAU : getters pour les quantités
    public int inputCount() { return inputCount; }
    public int fuelCount() { return fuelCount; }
    public int outputCount() { return outputCount; }
    public Ingredient fuelItem() { return fuelItem; }

    public ItemStack output() {
        return output;
    }

    @Override
    public ItemStack outputBottle() {
        return outputBottle;
    }

    @Override
    public Ingredient inputItem() {
        return inputItem;
    }

    @Override
    public @NonNull ItemStack assemble(CrystalInfuserRecipeInput crystalInfuserRecipeInput, HolderLookup.@NonNull Provider provider) {
        return output.copy();
    }

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<CrystalInfuserRecipeInput>> getSerializer() {
        return ModRecipes.CRYSTAL_INFUSER_SERIALIZER.get();
    }

    @Override
    public @NonNull RecipeType<? extends Recipe<CrystalInfuserRecipeInput>> getType() {
        return ModRecipes.CRYSTAL_INFUSER_TYPE.get();
    }

    @Override
    public @NonNull PlacementInfo placementInfo() {
        return PlacementInfo.create(inputItem);
    }

    @Override
    public @NonNull RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    public static class Serializer implements RecipeSerializer<CrystalInfuserRecipe> {
        public static final MapCodec<CrystalInfuserRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.fieldOf("input_item").forGetter(CrystalInfuserRecipe::inputItem),
                Codec.INT.fieldOf("input_count").orElse(1).forGetter(CrystalInfuserRecipe::inputCount),     // ← NOUVEAU
                Ingredient.CODEC.fieldOf("fuel_item").forGetter(CrystalInfuserRecipe::fuelItem),            // ← NOUVEAU
                Codec.INT.fieldOf("fuel_count").orElse(1).forGetter(CrystalInfuserRecipe::fuelCount),       // ← NOUVEAU
                ItemStack.CODEC.fieldOf("result").forGetter(CrystalInfuserRecipe::output),
                ItemStack.CODEC.fieldOf("result_bottle").forGetter(CrystalInfuserRecipe::outputBottle),
                Codec.INT.fieldOf("output_count").orElse(1).forGetter(CrystalInfuserRecipe::outputCount)
        ).apply(inst, CrystalInfuserRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CrystalInfuserRecipe> STREAM_CODEC =
                new StreamCodec<>() {
                    @Override
                    public CrystalInfuserRecipe decode(RegistryFriendlyByteBuf buf) {
                        Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                        int inputCount = buf.readVarInt();
                        Ingredient fuel = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                        int fuelCount = buf.readVarInt();
                        ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
                        int outPutCount = buf.readVarInt();
                        ItemStack output2 = ItemStack.STREAM_CODEC.decode(buf);
                        return new CrystalInfuserRecipe(input, inputCount, fuel, fuelCount, output, output2, outPutCount);
                    }

                    @Override
                    public void encode(RegistryFriendlyByteBuf buf, CrystalInfuserRecipe recipe) {
                        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputItem());
                        buf.writeVarInt(recipe.inputCount());
                        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.fuelItem());
                        buf.writeVarInt(recipe.fuelCount());
                        ItemStack.STREAM_CODEC.encode(buf, recipe.output());
                        buf.writeVarInt(recipe.outputCount);
                        ItemStack.STREAM_CODEC.encode(buf, recipe.outputBottle());
                    }
                };


        @Override
        public @NonNull MapCodec<CrystalInfuserRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NonNull StreamCodec<RegistryFriendlyByteBuf, CrystalInfuserRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
