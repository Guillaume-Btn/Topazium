package net.diabolo.diabolomod.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.block.ModBlocks;
import net.diabolo.diabolomod.recipe.CrystalInfuserRecipe;
import net.diabolo.diabolomod.recipe.ModRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;
import java.util.List;

@JeiPlugin
public class JEIDiaboloModPlugin implements IModPlugin {

    @Override
    public @NotNull Identifier getPluginUid() {
        // En 1.21.11, c'est Identifier.of
        return Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CrystalInfuserRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = null;

        Minecraft mc = Minecraft.getInstance();

        // 1. Essai via le serveur intégré (Singleplayer) - C'est le plus fiable en dev !
        if (mc.getSingleplayerServer() != null) {
            recipeManager = mc.getSingleplayerServer().getRecipeManager();
        }
        // 2. Si on est sur un serveur dédié, cette astuce ne marche pas.
        // Mais pour l'instant, testons si ça débloque ton crash.

        if (recipeManager == null) {
            // Log d'erreur si on ne trouve rien
            return;
        }

        // On a le VRAI RecipeManager du serveur, donc on a toutes les recettes !
        List<CrystalInfuserRecipe> recipes = recipeManager.getRecipes().stream()
                .filter(holder -> holder.value().getType() == ModRecipes.CRYSTAL_INFUSER_TYPE.get())
                .map(holder -> (CrystalInfuserRecipe) holder.value())
                .toList();

        registration.addRecipes(CrystalInfuserRecipeCategory.RECIPE_TYPE, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CRYSTAL_INFUSER.get()), CrystalInfuserRecipeCategory.RECIPE_TYPE);
    }
}
