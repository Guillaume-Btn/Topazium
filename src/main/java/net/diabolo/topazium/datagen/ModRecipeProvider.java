package net.diabolo.topazium.datagen;

import net.diabolo.topazium.Topazium;
import net.diabolo.topazium.block.ModBlocks;
import net.diabolo.topazium.item.ModItems;
import net.diabolo.topazium.recipe.CrystalInfuserRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        super(provider, recipeOutput);
    }

    @Override
    protected void buildRecipes() {
        List<ItemLike> TOPAZ_SMELTABLES = List.of(ModItems.RAW_TOPAZ,
                ModBlocks.TOPAZ_ORE, ModBlocks.TOPAZ_DEEPSLATE_ORE);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TOPAZ_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.TOPAZ.get())
                .unlockedBy("has_topaz", has(ModItems.TOPAZ)).save(output);

        shapeless(RecipeCategory.MISC, ModItems.TOPAZ.get(), 9)
                .requires(ModBlocks.TOPAZ_BLOCK)
                .unlockedBy("has_topaz_block", has(ModBlocks.TOPAZ_BLOCK)).save(output);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_TOPAZ_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.BLUE_TOPAZ.get())
                .unlockedBy("has_blue_topaz", has(ModItems.BLUE_TOPAZ)).save(output);

        shapeless(RecipeCategory.MISC, ModItems.BLUE_TOPAZ.get(), 9)
                .requires(ModBlocks.BLUE_TOPAZ_BLOCK)
                .unlockedBy("has_blue_topaz_block", has(ModBlocks.BLUE_TOPAZ_BLOCK)).save(output);

        oreSmelting(output, TOPAZ_SMELTABLES, RecipeCategory.MISC, ModItems.TOPAZ.get(), 0.25f, 200, "topaz");
        oreBlasting(output, TOPAZ_SMELTABLES, RecipeCategory.MISC, ModItems.TOPAZ.get(), 0.25f, 100, "topaz");

        shaped(RecipeCategory.TOOLS, ModItems.TOPAZ_SWORD.get())
                .pattern(" B ")
                .pattern(" B ")
                .pattern(" A ")
                .define('B', ModItems.TOPAZ.get())
                .define('A', Items.STICK)
                .unlockedBy("has_topaz", has(ModItems.TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.TOPAZ_PICKAXE.get())
                .pattern("BBB")
                .pattern(" A ")
                .pattern(" A ")
                .define('B', ModItems.TOPAZ.get())
                .define('A', Items.STICK)
                .unlockedBy("has_topaz", has(ModItems.TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.TOPAZ_AXE.get())
                .pattern("BB ")
                .pattern("BA ")
                .pattern(" A ")
                .define('B', ModItems.TOPAZ.get())
                .define('A', Items.STICK)
                .unlockedBy("has_topaz", has(ModItems.TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.TOPAZ_SHOVEL.get())
                .pattern(" B ")
                .pattern(" A ")
                .pattern(" A ")
                .define('B', ModItems.TOPAZ.get())
                .define('A', Items.STICK)
                .unlockedBy("has_topaz", has(ModItems.TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.TOPAZ_HOE.get())
                .pattern("BB ")
                .pattern(" A ")
                .pattern(" A ")
                .define('B', ModItems.TOPAZ.get())
                .define('A', Items.STICK)
                .unlockedBy("has_topaz", has(ModItems.TOPAZ))
                .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.TOPAZ_SPEAR.get())
                .pattern("  B")
                .pattern(" A ")
                .pattern("A  ")
                .define('B', ModItems.TOPAZ.get())
                .define('A', Items.STICK)
                .unlockedBy("has_topaz", has(ModItems.TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.TOPAZ_HAMMER.get())
                .pattern("BCB")
                .pattern("BAB")
                .pattern(" A ")
                .define('A', Items.STICK)
                .define('B', ModItems.TOPAZ.get())
                .define('C', ModBlocks.TOPAZ_BLOCK)
                .unlockedBy("has_topaz", has(ModItems.TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.BLUE_TOPAZ_SWORD.get())
                .pattern(" B ")
                .pattern(" B ")
                .pattern(" A ")
                .define('B', ModItems.BLUE_TOPAZ.get())
                .define('A', Items.STICK)
                .unlockedBy("has_blue_topaz", has(ModItems.BLUE_TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.BLUE_TOPAZ_PICKAXE.get())
                .pattern("BBB")
                .pattern(" A ")
                .pattern(" A ")
                .define('B', ModItems.BLUE_TOPAZ.get())
                .define('A', Items.STICK)
                .unlockedBy("has_blue_topaz", has(ModItems.BLUE_TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.BLUE_TOPAZ_AXE.get())
                .pattern("BB ")
                .pattern("BA ")
                .pattern(" A ")
                .define('B', ModItems.BLUE_TOPAZ.get())
                .define('A', Items.STICK)
                .unlockedBy("has_blue_topaz", has(ModItems.BLUE_TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.BLUE_TOPAZ_SHOVEL.get())
                .pattern(" B ")
                .pattern(" A ")
                .pattern(" A ")
                .define('B', ModItems.BLUE_TOPAZ.get())
                .define('A', Items.STICK)
                .unlockedBy("has_blue_topaz", has(ModItems.BLUE_TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.BLUE_TOPAZ_HOE.get())
                .pattern("BB ")
                .pattern(" A ")
                .pattern(" A ")
                .define('B', ModItems.BLUE_TOPAZ.get())
                .define('A', Items.STICK)
                .unlockedBy("has_blue_topaz", has(ModItems.BLUE_TOPAZ))
                .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.BLUE_TOPAZ_HAMMER.get())
                .pattern("BCB")
                .pattern("BAB")
                .pattern(" A ")
                .define('B', ModItems.BLUE_TOPAZ.get())
                .define('A', Items.STICK)
                .define('C', ModBlocks.BLUE_TOPAZ_BLOCK)
                .unlockedBy("has_blue_topaz", has(ModItems.BLUE_TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.TOPAZ_HELMET.get())
                .pattern("BBB")
                .pattern("B B")
                .pattern("   ")
                .define('B', ModItems.TOPAZ.get())
                .unlockedBy("has_topaz", has(ModItems.TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.TOPAZ_CHESTPLATE.get())
                .pattern("B B")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.TOPAZ.get())
                .unlockedBy("has_topaz", has(ModItems.TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.TOPAZ_LEGGINGS.get())
                .pattern("BBB")
                .pattern("B B")
                .pattern("B B")
                .define('B', ModItems.TOPAZ.get())
                .unlockedBy("has_topaz", has(ModItems.TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.TOPAZ_BOOTS.get())
                .pattern("   ")
                .pattern("B B")
                .pattern("B B")
                .define('B', ModItems.TOPAZ.get())
                .unlockedBy("has_topaz", has(ModItems.TOPAZ)).save(output);

        shapeless(RecipeCategory.MISC, ModItems.COBALT_POWDER.get(), 2)
                .requires(Items.LAPIS_LAZULI)
                .requires(Items.IRON_NUGGET)
                .unlockedBy("has_lapiz_lazuli", has(Items.LAPIS_LAZULI)).save(output);

        shaped(RecipeCategory.MISC, ModBlocks.CRYSTAL_INFUSER.get())
                .pattern("IGI")
                .pattern("PDP")
                .pattern("OTO")
                .define('I', Items.IRON_INGOT)
                .define('G', Items.GLASS)
                .define('P', Items.PISTON)
                .define('D', Items.DIAMOND)
                .define('O', Items.OBSIDIAN)
                .define('T', ModBlocks.TOPAZ_BLOCK)
                .unlockedBy("has_topaz", has(ModItems.TOPAZ))
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .unlockedBy("has_obsidian", has(Items.OBSIDIAN)).save(output);

//        stonecutterResultFromBase(RecipeCategory.MISC,ModItems.BLUE_TOPAZ.get(),ModItems.RAW_BLUE_TOPAZ);

        buildCrystalInfuserRecipe();
    }

    protected void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                               float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                               float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }


    protected <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, AbstractCookingRecipe.Factory<T> factory, List<ItemLike> pIngredients, RecipeCategory pCategory,
                                ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            CookingBookCategory cookingCategory = CookingBookCategory.MISC;
            SimpleCookingRecipeBuilder.generic(
                            Ingredient.of(itemlike),
                            pCategory,
                            cookingCategory,
                            pResult,
                            pExperience,
                            pCookingTime,
                            factory)
                    .group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, Topazium.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

    private void buildCrystalInfuserRecipe() {
        ResourceKey<Recipe<?>> recipeKey = ResourceKey.create(Registries.RECIPE,
                Identifier.fromNamespaceAndPath("topazium", "topaz_to_blue_topaz"));

        CrystalInfuserRecipe recipeInstance = new CrystalInfuserRecipe(
                Ingredient.of(ModItems.TOPAZ.get()),
                5,
                Ingredient.of(ModItems.COBALT_SOLUTION.get()),
                1,
                ModItems.BLUE_TOPAZ.get(),
                Items.GLASS_BOTTLE,
                1
        );
        output.accept(recipeKey, recipeInstance, null);

        recipeKey = ResourceKey.create(Registries.RECIPE,
                Identifier.fromNamespaceAndPath("topazium", "blue_topaz_to_topaz"));
        recipeInstance = new CrystalInfuserRecipe(
                Ingredient.of(ModItems.BLUE_TOPAZ.get()),
                5,
                Ingredient.of(Items.GLASS_BOTTLE),
                1,
                ModItems.TOPAZ.get(),
                ModItems.COBALT_SOLUTION.get(),
                1
        );
        output.accept(recipeKey, recipeInstance, null);

        recipeKey = ResourceKey.create(Registries.RECIPE,
                Identifier.fromNamespaceAndPath("topazium", "topaz_to_topaz_blue_from_dye"));
        recipeInstance = new CrystalInfuserRecipe(
                Ingredient.of(ModItems.TOPAZ.get()),
                64,
                Ingredient.of(Items.BLUE_DYE),
                1,
                ModItems.BLUE_TOPAZ.get(),
                Items.GLASS_BOTTLE,
                1
        );
        output.accept(recipeKey, recipeInstance, null);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
            super(packOutput, provider);
        }

        @Override
        protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider provider, @NonNull RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public @NonNull String getName() {
            return "My Recipes";
        }
    }


}