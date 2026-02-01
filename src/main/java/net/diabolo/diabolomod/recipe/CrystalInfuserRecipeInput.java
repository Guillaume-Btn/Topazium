package net.diabolo.diabolomod.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record CrystalInfuserRecipeInput(ItemStack input, ItemStack fuel) implements RecipeInput {  // ← fuel ajouté
    @Override
    public ItemStack getItem(int i) {
        return switch (i) {
            case 0 -> input;
            case 1 -> fuel;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public int size() {
        return 2;  // ← 2 slots maintenant
    }
}
