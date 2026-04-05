package net.diabolo.diabolomod.event;

import net.diabolo.diabolomod.entity.ModEntities;
import net.diabolo.diabolomod.entity.custom.topaz_golem.TopazGolemEntity;
import net.diabolo.diabolomod.entity.custom.topaz_golem.TopazGolemPattern;
import net.diabolo.diabolomod.item.ModItems;
import net.diabolo.diabolomod.item.custom.HammerItem;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
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
                for(BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                    if(pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                        continue;
                    }

                    HARVESTED_BLOCKS.add(pos);
                    serverPlayer.gameMode.destroyBlock(pos);
                    HARVESTED_BLOCKS.remove(pos);
                }
            }
//            else {
//                for(BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
//                    if(pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
//                        continue;
//                    }
//
//                    HARVESTED_BLOCKS.add(pos);
//                    serverPlayer.gameMode.destroyBlock(pos);
//                    HARVESTED_BLOCKS.remove(pos);
//                }
//            }
        }
    }

    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        ItemStack awkwardStack = PotionContents.createItemStack(Items.POTION, Potions.AWKWARD);
        // Le "true" veut dire "match strict des composants" (donc que Awkward, pas Water)
        Ingredient inputIngredient = DataComponentIngredient.of(true, awkwardStack);
        Ingredient reagentIngredient = Ingredient.of(ModItems.COBALT_POWDER);
        ItemStack resultStack = new ItemStack(ModItems.COBALT_SOLUTION.get());
        builder.addRecipe(inputIngredient, reagentIngredient, resultStack);
    }


    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getLevel().isClientSide()) return;

        BlockState placedBlock = event.getState();

        // On vérifie si on vient de poser une citrouille (ou ton bloc "tête")
        if (placedBlock.is(Blocks.CARVED_PUMPKIN) || placedBlock.is(Blocks.JACK_O_LANTERN)) {
            checkAndSpawnGolem((Level) event.getLevel(), event.getPos());
        }
    }

    private static void checkAndSpawnGolem(Level level, BlockPos pos) {
        // Définition du pattern (Forme en T debout : 1 bloc bas, 3 blocs milieu, 1 tête haut)
        // C'est un peu verbeux, on s'inspire de CarvedPumpkinBlock

        BlockPattern.BlockPatternMatch match = TopazGolemPattern.getTopazGolemPattern().find(level, pos);

        if (match != null) {
            // 1. Supprimer les blocs de construction
            for (int i = 0; i < match.getWidth(); ++i) {
                for (int j = 0; j < match.getHeight(); ++j) {
                    for (int k = 0; k < match.getDepth(); ++k) {
                        BlockInWorld blockinworld = match.getBlock(i, j, k);
                        // On retire les blocs qui font partie du golem (sauf l'air)
                        if (!blockinworld.getState().isAir()) {
                            level.setBlock(blockinworld.getPos(), Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
                            level.levelEvent(2001, blockinworld.getPos(), Block.getId(blockinworld.getState()));
                        }
                    }
                }
            }

            // 2. Créer l'entité
            TopazGolemEntity golem = ModEntities.TOPAZ_GOLEM.get().create(level, EntitySpawnReason.TRIGGERED);
            if (golem != null) {
                // Utilise setPos au lieu de moveTo
                golem.setPos(
                        (double)pos.getX() + 0.5D,
                        (double)pos.getY() - 1.95D,
                        (double)pos.getZ() + 0.5D
                );

                // Optionnel : Fixe la rotation si besoin
                golem.setYRot(0.0F);
                golem.setXRot(0.0F);

                level.addFreshEntity(golem);

                // Trigger pour les achievements
                for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, golem.getBoundingBox().inflate(5.0D))) {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(player, golem);
                }

                // Effets visuels
                for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, golem.getBoundingBox().inflate(5.0D))) {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(player, golem);
                }
            }
        }
    }
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        String registryName = BuiltInRegistries.ITEM.getKey(event.getItemStack().getItem()).toString();
        if (registryName.startsWith("diabolomod:")) {
            if (!event.getToolTip().isEmpty()) {
                Component originalName = event.getToolTip().getFirst();
                event.getToolTip().set(0, Component.literal(originalName.getString()).withStyle(ChatFormatting.WHITE));
            }
        }
    }
}
