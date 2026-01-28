package net.diabolo.diabolomod.datagen;

import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.block.ModBlocks;
import net.diabolo.diabolomod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        super(provider, recipeOutput);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
            super(packOutput, provider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return "My Recipes";
        }
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

//        shapeless(RecipeCategory.MISC, ModItems.BISMUTH.get(), 18)
//                .requires(ModBlocks.MAGIC_BLOCK)
//                .unlockedBy("has_magic_block", has(ModBlocks.MAGIC_BLOCK))
//                .save(output, "tutorialmod:bismuth_from_magic_block");//

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
                .pattern(" BB")
                .pattern(" AB")
                .pattern(" A ")
                .define('B', ModItems.TOPAZ.get())
                .define('A', Items.STICK)
                .group("topaz_axe")
                .unlockedBy("has_topaz", has(ModItems.TOPAZ))
                .save(output,"diabolomod:topaz_axe_alt");

        shaped(RecipeCategory.TOOLS, ModItems.TOPAZ_AXE.get())
                .pattern("BB ")
                .pattern("BA ")
                .pattern(" A ")
                .define('B', ModItems.TOPAZ.get())
                .define('A', Items.STICK)
                .group("topaz_axe")
                .unlockedBy("has_topaz", has(ModItems.TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.TOPAZ_SHOVEL.get())
                .pattern(" B ")
                .pattern(" A ")
                .pattern(" A ")
                .define('B', ModItems.TOPAZ.get())
                .define('A', Items.STICK)
                .unlockedBy("has_topaz", has(ModItems.TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.TOPAZ_HOE.get())
                .pattern(" BB")
                .pattern(" A ")
                .pattern(" A ")
                .define('B', ModItems.TOPAZ.get())
                .define('A', Items.STICK)
                .group("topaz_hoe")
                .unlockedBy("has_topaz", has(ModItems.TOPAZ)).save(output);

        shaped(RecipeCategory.TOOLS, ModItems.TOPAZ_HOE.get())
                .pattern("BB ")
                .pattern(" A ")
                .pattern(" A ")
                .define('B', ModItems.TOPAZ.get())
                .define('A', Items.STICK)
                .group("topaz_hoe")
                .unlockedBy("has_topaz", has(ModItems.TOPAZ))
                .save(output, "diabolomod:topaz_hoe_alt");

        // Throws error
        // trimSmithing(ModItems.KAUPEN_SMITHING_TEMPLATE.get(), ResourceKey.create(Registries.TRIM_PATTERN, Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "kaupen")),
        //         ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "kaupen")));
    }

    protected void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                               float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                               float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, DiaboloMod.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}