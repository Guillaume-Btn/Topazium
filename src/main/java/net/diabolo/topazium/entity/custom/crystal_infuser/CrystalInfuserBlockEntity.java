package net.diabolo.topazium.entity.custom.crystal_infuser;

import net.diabolo.topazium.entity.ModBlockEntities;
import net.diabolo.topazium.recipe.CrystalInfuserRecipe;
import net.diabolo.topazium.recipe.CrystalInfuserRecipeInput;
import net.diabolo.topazium.recipe.ModRecipes;
import net.diabolo.topazium.screen.custom.CrystalInfuserMenu;
import net.diabolo.topazium.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

import static net.minecraft.world.level.block.Block.UPDATE_CLIENTS;

public class CrystalInfuserBlockEntity extends BlockEntity implements MenuProvider {
    private static final int INPUT_SLOT = 0;
    private static final int FUEL_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private static final int OUTPUT_FUEL_SLOT = 3;
    public final ItemStacksResourceHandler itemHandler = new ItemStacksResourceHandler(4) {

        @Override
        protected void onContentsChanged(int slot, ItemStack previousContents) {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_INVISIBLE);
            }
        }

        // isItemValid devient isValid et prend un ItemResource
        @Override
        public boolean isValid(int slot, ItemResource resource) {
            if (resource.isEmpty()) return true;

            return switch (slot) {
                case 0 -> resource.is(ModTags.Items.CAN_BE_INFUSED);
                case 1 -> resource.is(ModTags.Items.IS_INFUSER);
                case 2, 3 -> false; // Slots Output : On ne peut rien insérer
                default -> super.isValid(slot, resource);
            };
        }
    };
    protected final ContainerData data;
    public float renderingRotation = 0f;
    public float prevRenderingRotation = 0f; // Nécessaire pour l'interpolation
    private float rotation;
    private int progressArrow = 0;
    private int progressBubble = 0;
    private int maxProgress = 72;
    private int maxProgress_bubble = 72; // Temps total
    private float currentSpeed = 1.0f;

    public CrystalInfuserBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CRYSTAL_INFUSER_BE.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> CrystalInfuserBlockEntity.this.progressArrow;
                    case 1 -> CrystalInfuserBlockEntity.this.maxProgress;
                    case 2 -> CrystalInfuserBlockEntity.this.progressBubble;
                    case 3 -> CrystalInfuserBlockEntity.this.maxProgress_bubble;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0:
                        CrystalInfuserBlockEntity.this.progressArrow = value;
                    case 1:
                        CrystalInfuserBlockEntity.this.maxProgress = value;
                    case 2:
                        CrystalInfuserBlockEntity.this.progressBubble = value;
                    case 3:
                        CrystalInfuserBlockEntity.this.maxProgress_bubble = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public void clearContents() {
        itemHandler.set(INPUT_SLOT, ItemResource.of(ItemStack.EMPTY), 0);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(itemHandler.size());
        for (int i = 0; i < itemHandler.size(); i++) {
            ItemResource res = itemHandler.getResource(i);
            if (!res.isEmpty()) {
                inv.setItem(i, res.toStack((int) itemHandler.getAmountAsLong(i)));
            }
        }

        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inv);
        }
    }

    @Override
    public void preRemoveSideEffects(@NonNull BlockPos pos, @NonNull BlockState state) {
        drops();
        super.preRemoveSideEffects(pos, state);
    }

    @Override
    public @NonNull Component getDisplayName() {
        return Component.literal("Crystal Infuser");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @NonNull Inventory inventory, @NonNull Player player) {
        return new CrystalInfuserMenu(i, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        itemHandler.serialize(output);
        output.putInt("crystal_infuser.progress", progressArrow);
        output.putInt("crystal_infuser.max_progress", maxProgress);
        output.putInt("crystal_infuser.bubble_progress", progressBubble);
        output.putInt("crystal_infuser.max_progress_bubble", maxProgress_bubble);

        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);

        itemHandler.deserialize(input);
        progressArrow = input.getIntOr("crystal_infuser.progress", 0);
        maxProgress = input.getIntOr("crystal_infuser.max_progress", 0);
        progressBubble = input.getIntOr("crystal_infuser.progress_bubble", 0);
        maxProgress_bubble = input.getIntOr("crystal_infuser.max_progress_bubble", 0);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        // 1. LOGIQUE CLIENT (Animation, Rotation, Particules)
        if (level.isClientSide()) {
            prevRenderingRotation = renderingRotation;

            float targetSpeed = isCrafting() ? 50.0f : 1.0f;

            // Accélération douce (plus la valeur 0.5f est petite, plus c'est lent à accélérer)
            if (currentSpeed < targetSpeed) {
                currentSpeed += 0.5f; // Accélère
            } else if (currentSpeed > targetSpeed) {
                currentSpeed -= 0.5f; // Ralentit
            }

            // Applique la vitesse actuelle
            renderingRotation += currentSpeed;

            if (renderingRotation >= 360f) {
                renderingRotation -= 360f;
                prevRenderingRotation -= 360f;
            }
            return;
        }

        // 2. LOGIQUE SERVEUR (Craft, Inventaire, Son)

        // Jouer le son toutes les 80 ticks si on craft
        if (isCrafting()) {
            // Joue un son toutes les 40 ticks (2 secondes) pour pas que ça soit insupportable
            if (level.getGameTime() % 40 == 0) {
                level.playSound(
                        null, // null = joue pour tout le monde aux alentours
                        pos,
                        SoundEvents.BEACON_POWER_SELECT, // Choisis un son (ex: BEACON_AMBIENT, ANVIL_USE, BUBBLE_COLUMN_BUBBLE_POP)
                        SoundSource.BLOCKS,
                        1.0f, // Volume
                        0.3f  // Pitch (Aigu/Grave)
                );
            }
        }
        boolean wasCrafting = isCrafting(); // On stocke l'état avant modif
        boolean hasChanged = false;

        if (hasRecipe() && canCraft()) {
            increaseCraftingProgress();
            setChanged(level, pos, state);
            hasChanged = true;

            if (hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }

        // ✅ Si l'état de craft a changé (on commence ou on finit de travailler)
        // On force la mise à jour du client pour qu'il accélère/ralentisse l'animation
        if (wasCrafting != isCrafting()) {
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_NEIGHBORS | UPDATE_CLIENTS);
        } else if (hasChanged) {
            // Optionnel : sauvegarde disque sans envoi réseau inutile si on est juste en train d'avancer
            setChanged(level, pos, state);
        }
    }


    // Nouvelle méthode pour le renderer
    public float getRenderingRotation(float partialTick) {
        // Calcule la position "entre" le tick d'avant et le tick actuel
        return prevRenderingRotation + (renderingRotation - prevRenderingRotation) * partialTick;
    }

    // Méthode utilitaire pour savoir si la machine tourne
    private boolean isCrafting() {
        return this.progressArrow > 0;
    }

    private void craftItem() {
        Optional<RecipeHolder<CrystalInfuserRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;
        CrystalInfuserRecipe r = recipe.get().value();
        try (Transaction tx = Transaction.openRoot()) {
            int extractedInput = itemHandler.extract(INPUT_SLOT, itemHandler.getResource(INPUT_SLOT), r.inputCount(), tx);
            int extractedFuel = itemHandler.extract(FUEL_SLOT, itemHandler.getResource(FUEL_SLOT), r.fuelCount(), tx);
            if (extractedInput == r.inputCount() && extractedFuel == r.fuelCount()) {
                int currentOutCount = (int) itemHandler.getAmountAsLong(OUTPUT_SLOT);
                int currentBottleCount = (int) itemHandler.getAmountAsLong(OUTPUT_FUEL_SLOT);
                itemHandler.set(OUTPUT_SLOT, ItemResource.of(r.output()), currentOutCount + r.outputCount());
                itemHandler.set(OUTPUT_FUEL_SLOT, ItemResource.of(r.outputBottle()), currentBottleCount + r.outputCount());
                tx.commit();
            }
        }
    }

    private void resetProgress() {
        progressArrow = 0;
        progressBubble = 0;
        maxProgress = 72;
        maxProgress_bubble = 120;
    }

    private boolean hasCraftingFinished() {
        return this.progressArrow >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        progressArrow++;
        progressBubble++;
    }

    private boolean canInsertItemIntoOutputSlot(ItemResource normalOutput, ItemResource bottleOutput) {
        ItemResource outRes = itemHandler.getResource(OUTPUT_SLOT);
        ItemResource bottleRes = itemHandler.getResource(OUTPUT_FUEL_SLOT);

        return (outRes.isEmpty() || outRes.equals(normalOutput)) &&
                (bottleRes.isEmpty() || bottleRes.equals(bottleOutput));
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<CrystalInfuserRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        CrystalInfuserRecipe r = recipe.get().value();
        ItemResource normalOutput = ItemResource.of(r.output());
        ItemResource bottleOutput = ItemResource.of(r.outputBottle());

        return canInsertItemIntoOutputSlot(normalOutput, bottleOutput) &&
                canInsertAmountIntoOutputSlot(r.outputCount());
    }


    private Optional<RecipeHolder<CrystalInfuserRecipe>> getCurrentRecipe() {
        ItemStack inputStack = itemHandler.getResource(INPUT_SLOT).toStack((int) itemHandler.getAmountAsLong(INPUT_SLOT));
        ItemStack fuelStack = itemHandler.getResource(FUEL_SLOT).toStack((int) itemHandler.getAmountAsLong(FUEL_SLOT));

        assert level != null;
        return ((ServerLevel) level).recipeAccess()
                .getRecipeFor(ModRecipes.CRYSTAL_INFUSER_TYPE.get(),
                        new CrystalInfuserRecipeInput(inputStack, fuelStack), level);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        // maxStackSize() n'est plus accessible directement, on passe par toStack temporaire ou getCapacity
        ItemResource outRes = itemHandler.getResource(OUTPUT_SLOT);
        int maxCountNormal = outRes.isEmpty() ? 64 : outRes.toStack().getMaxStackSize();
        int currentCountNormal = (int) itemHandler.getAmountAsLong(OUTPUT_SLOT);

        ItemResource bottleRes = itemHandler.getResource(OUTPUT_FUEL_SLOT);
        int maxCountBottle = bottleRes.isEmpty() ? 64 : bottleRes.toStack().getMaxStackSize();
        int currentCountBottle = (int) itemHandler.getAmountAsLong(OUTPUT_FUEL_SLOT);

        return (maxCountNormal >= currentCountNormal + count) &&
                (maxCountBottle >= currentCountBottle + count);
    }

    private boolean canCraft() {
        Optional<RecipeHolder<CrystalInfuserRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        boolean enoughItem = itemHandler.getAmountAsLong(INPUT_SLOT) >= recipe.get().value().inputCount();
        boolean enoughFuel = itemHandler.getAmountAsLong(FUEL_SLOT) >= recipe.get().value().fuelCount();
        return enoughItem && enoughFuel;
    }

    @Override
    public @NonNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
}