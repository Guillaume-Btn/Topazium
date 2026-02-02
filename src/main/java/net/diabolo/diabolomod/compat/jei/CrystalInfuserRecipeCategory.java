package net.diabolo.diabolomod.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.block.ModBlocks;
import net.diabolo.diabolomod.recipe.CrystalInfuserRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CrystalInfuserRecipeCategory implements IRecipeCategory<CrystalInfuserRecipe> {
    public static final Identifier UID = Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "crystal_infuser");
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "textures/gui/crystal_infuser/crystal_infuser_gui.png");

    public static final RecipeType<CrystalInfuserRecipe> RECIPE_TYPE =
            new RecipeType<>(UID, CrystalInfuserRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public CrystalInfuserRecipeCategory(IGuiHelper helper) {
        // Définit la zone de l'image à afficher (x, y, width, height)
        // Ajuste ces valeurs selon ton image GUI réelle !
        this.background = helper.createDrawable(TEXTURE, 3, 3, 170, 80);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CRYSTAL_INFUSER.get()));
    }

    @Override
    public RecipeType<CrystalInfuserRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.diabolomod.crystal_infuser");
    }

    @Override
    public int getWidth() {
        return 176;
    }

    @Override
    public int getHeight() {
        return 85;
    }
    @Override
    public void draw(CrystalInfuserRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        // Dessine le fond en (0, 0)
        this.background.draw(guiGraphics);
    }
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CrystalInfuserRecipe recipe, IFocusGroup focuses) {
        // Input Principal (Topaze) - Slot 0
        builder.addSlot(RecipeIngredientRole.INPUT, 51, 13) // Coordonnées X,Y dans ton GUI
                .addIngredients(recipe.inputItem());

        // Input Secondaire (Solvant) - Slot 1
        builder.addSlot(RecipeIngredientRole.OUTPUT, 51, 48)
                .addIngredients(recipe.fuelItem());

        // Output (Résultat) - Slot 2 (Le gros slot à droite)
        builder.addSlot(RecipeIngredientRole.OUTPUT, 101, 31)
                .addItemStack(recipe.output());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 130, 31)
                .addItemStack(recipe.outputBottle());

        // (Optionnel) Ajoute la bouteille vide en output secondaire si tu veux l'afficher
    }
}

