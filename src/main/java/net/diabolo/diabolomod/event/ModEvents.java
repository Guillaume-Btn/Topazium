package net.diabolo.diabolomod.event;

import net.diabolo.diabolomod.item.ModItems;
import net.diabolo.diabolomod.item.custom.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.diabolo.diabolomod.DiaboloMod;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = DiaboloMod.MODID)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    // Done with the help of https://github.com/CoFH/CoFHCore/blob/1.19.x/src/main/java/cofh/core/event/AreaEffectEvents.java
    // Don't be a jerk License
    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if(mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }
            if(mainHandItem.getItem().toString().equals("diabolomod:blue_topaz_hammer")){
                for(BlockPos pos : HammerItem.getBlocksToBeDestroyed(2, initialBlockPos, serverPlayer)) {
                    if(pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                        continue;
                    }

                    HARVESTED_BLOCKS.add(pos);
                    serverPlayer.gameMode.destroyBlock(pos);
                    HARVESTED_BLOCKS.remove(pos);
                }
            } else {
                for(BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                    if(pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                        continue;
                    }

                    HARVESTED_BLOCKS.add(pos);
                    serverPlayer.gameMode.destroyBlock(pos);
                    HARVESTED_BLOCKS.remove(pos);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        ItemStack awkwardStack = PotionContents.createItemStack(Items.POTION, Potions.AWKWARD);
        // Le "true" veut dire "match strict des composants" (donc que Awkward, pas Water)
        Ingredient inputIngredient = DataComponentIngredient.of(true, awkwardStack);
        Ingredient reagentIngredient = Ingredient.of(Items.LAPIS_LAZULI);
        ItemStack resultStack = new ItemStack(ModItems.COBALT_SOLUTION.get());
        builder.addRecipe(inputIngredient, reagentIngredient, resultStack);
    }

}
