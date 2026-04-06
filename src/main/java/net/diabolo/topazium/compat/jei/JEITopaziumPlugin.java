package net.diabolo.topazium.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.diabolo.topazium.Topazium;
import net.diabolo.topazium.block.ModBlocks;
import net.diabolo.topazium.recipe.CrystalInfuserRecipe;
import net.diabolo.topazium.recipe.ModRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@JeiPlugin
public class JEITopaziumPlugin implements IModPlugin {

    @Override
    public @NotNull Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(Topazium.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CrystalInfuserRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(@NonNull IRecipeRegistration registration) {
        RecipeManager recipeManager = null;

        Minecraft mc = Minecraft.getInstance();
        if (mc.getSingleplayerServer() != null) {
            recipeManager = mc.getSingleplayerServer().getRecipeManager();
        }
        if (recipeManager == null) {
            return;
        }
        List<CrystalInfuserRecipe> recipes = recipeManager.getRecipes().stream()
                .filter(holder -> holder.value().getType() == ModRecipes.CRYSTAL_INFUSER_TYPE.get())
                .map(holder -> (CrystalInfuserRecipe) holder.value())
                .toList();

        registration.addRecipes(CrystalInfuserRecipeCategory.RECIPE_TYPE, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(CrystalInfuserRecipeCategory.RECIPE_TYPE,new ItemStack(ModBlocks.CRYSTAL_INFUSER.get()));
    }
}
