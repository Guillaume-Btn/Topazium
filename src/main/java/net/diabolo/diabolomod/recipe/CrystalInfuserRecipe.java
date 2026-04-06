package net.diabolo.diabolomod.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public record CrystalInfuserRecipe(
        Ingredient inputItem,
        int inputCount,
        Ingredient fuelItem,
        int fuelCount,
        Item output,       // On garde Item pour éviter le crash JSON
        Item outputBottle,     // On garde Item pour la bouteille
        int outputCount
) implements Recipe<CrystalInfuserRecipeInput> {

    // 1. Le MapCodec avec Item.CODEC
    // 1. Le MapCodec
    public static final MapCodec<CrystalInfuserRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("input_item").forGetter(CrystalInfuserRecipe::inputItem),
            Codec.INT.fieldOf("input_count").orElse(1).forGetter(CrystalInfuserRecipe::inputCount),
            Ingredient.CODEC.fieldOf("fuel_item").forGetter(CrystalInfuserRecipe::fuelItem),
            Codec.INT.fieldOf("fuel_count").orElse(1).forGetter(CrystalInfuserRecipe::fuelCount),

            // On utilise .builtInRegistryHolder() pour convertir l'Item en Holder<Item>
            Item.CODEC.fieldOf("result").forGetter(recipe -> recipe.output().builtInRegistryHolder()),
            Item.CODEC.fieldOf("result_bottle").forGetter(recipe -> recipe.outputBottle().builtInRegistryHolder()),

            Codec.INT.fieldOf("output_count").orElse(1).forGetter(CrystalInfuserRecipe::outputCount)
    ).apply(inst, (input, inCount, fuel, fuelCount, resHolder, resBottleHolder, outCount) ->
            // Quand le Codec décode, il nous donne des Holder<Item>, donc on extrait la valeur (.value())
            // pour recréer notre objet CrystalInfuserRecipe qui attend de simples Item
            new CrystalInfuserRecipe(input, inCount, fuel, fuelCount, resHolder.value(), resBottleHolder.value(), outCount)
    ));

    // 2. Le StreamCodec
    public static final StreamCodec<RegistryFriendlyByteBuf, CrystalInfuserRecipe> STREAM_CODEC =
            new StreamCodec<>() {
                @Override
                public CrystalInfuserRecipe decode(RegistryFriendlyByteBuf buf) {
                    Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                    int inputCount = buf.readVarInt();
                    Ingredient fuel = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                    int fuelCount = buf.readVarInt();

                    Item resItem = Item.STREAM_CODEC.decode(buf).value();
                    int outPutCount = buf.readVarInt();
                    Item resBottle = Item.STREAM_CODEC.decode(buf).value();

                    return new CrystalInfuserRecipe(input, inputCount, fuel, fuelCount, resItem, resBottle, outPutCount);
                }

                @Override
                public void encode(RegistryFriendlyByteBuf buf, CrystalInfuserRecipe recipe) {
                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputItem());
                    buf.writeVarInt(recipe.inputCount());
                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.fuelItem());
                    buf.writeVarInt(recipe.fuelCount());

                    Item.STREAM_CODEC.encode(buf, recipe.output().builtInRegistryHolder());
                    buf.writeVarInt(recipe.outputCount());
                    Item.STREAM_CODEC.encode(buf, recipe.outputBottle().builtInRegistryHolder());
                }
            };

    // 3. Le Serializer 1.26.1
    public static final RecipeSerializer<CrystalInfuserRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

    // --- MÉTHODES DE L'INTERFACE RECIPE ---

    @Override
    public boolean matches(CrystalInfuserRecipeInput input, Level level) {
        if (level.isClientSide()) return false;
        return inputItem.test(input.getItem(0)) && fuelItem.test(input.getItem(1));
    }

    @Override
    public @NonNull ItemStack assemble(CrystalInfuserRecipeInput crystalInfuserRecipeInput) {
        // C'est la SEULE méthode de l'interface qui renvoie le résultat maintenant !
        return new ItemStack(output, outputCount);
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public @NonNull String group() {
        return "";
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

    // --- MÉTHODES UTILES POUR TOI ET JEI (Non requises par l'interface) ---

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        list.add(fuelItem);
        return list;
    }

    // Pour JEI
    public ItemStack getResultStack() {
        return new ItemStack(output, outputCount);
    }

    // Pour JEI
    public ItemStack getBottleStack() {
        return new ItemStack(outputBottle, 1);
    }
}