package net.diabolo.diabolomod.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.block.ModBlocks;
import net.diabolo.diabolomod.recipe.CrystalInfuserRecipe;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class CrystalInfuserRecipeCategory implements IRecipeCategory<CrystalInfuserRecipe> {
    public static final Identifier UID = Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "crystal_infuser");
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "textures/gui/crystal_infuser/crystal_infuser_gui.png");

    public static final IRecipeType<CrystalInfuserRecipe> RECIPE_TYPE =
            IRecipeType.create(UID, CrystalInfuserRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public CrystalInfuserRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 3, 3, 170, 80);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CRYSTAL_INFUSER.get()));
    }

    @Override
    public @NonNull IRecipeType<CrystalInfuserRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public @NonNull Component getTitle() {
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
    public void draw(CrystalInfuserRecipe recipe, @NonNull IRecipeSlotsView recipeSlotsView, @NonNull GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
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
    public void setRecipe(IRecipeLayoutBuilder builder, CrystalInfuserRecipe recipe, @NonNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 51, 13)
                .add(recipe.inputItem());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 51, 50)
                .add(recipe.fuelItem());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 101, 31)
                .add(recipe.output());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 130, 31)
                .add(recipe.outputBottle());
    }
}