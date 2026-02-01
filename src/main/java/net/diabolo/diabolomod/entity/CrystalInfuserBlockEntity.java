package net.diabolo.diabolomod.entity;

import net.diabolo.diabolomod.recipe.CrystalInfuserRecipe;
import net.diabolo.diabolomod.recipe.CrystalInfuserRecipeInput;
import net.diabolo.diabolomod.recipe.ModRecipes;
import net.diabolo.diabolomod.screen.custom.CrystalInfuserMenu;
import net.diabolo.diabolomod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CrystalInfuserBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 4);
            }
        }
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.is(ModTags.Items.CAN_BE_INFUSED);  // Slot 0 : Accepte tout (ou filtre pour Topaz seulement)
                case 1 -> stack.is(ModTags.Items.IS_INFUSER);
                case 2, 3 -> false; // Slots Output : On ne peut rien insérer (pour les hoppers)
                default -> super.isItemValid(slot, stack);
            };
        }
    };
    private float rotation;
    private static final int INPUT_SLOT = 0;
    private static final int FUEL_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private static final int OUTPUT_FUEL_SLOT = 3;


    protected final ContainerData data;
    private int progressArrow = 0;
    private int progressBubble = 0;
    private int maxProgress = 72;
    private int maxProgress_bubble = 72; // Temps total

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
                    case 0: CrystalInfuserBlockEntity.this.progressArrow = value;
                    case 1: CrystalInfuserBlockEntity.this.maxProgress = value;
                    case 2: CrystalInfuserBlockEntity.this.progressBubble= value;
                    case 3: CrystalInfuserBlockEntity.this.maxProgress_bubble= value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        if(rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    public void clearContents() {
        itemHandler.setStackInSlot(0, ItemStack.EMPTY);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        drops();
        super.preRemoveSideEffects(pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Crystal Infuser");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new CrystalInfuserMenu(i, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        itemHandler.serialize(output);
        output.putInt("crystal_infuser.progress", progressArrow);
        output.putInt("crystal_infuser.max_progress", maxProgress);
        output.putInt("crystal_infuser.bubble_progress", progressBubble);
        output.putInt("crystal_infuser.max_progress_bubble", maxProgress_bubble);

        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        itemHandler.deserialize(input);
        progressArrow = input.getIntOr("crystal_infuser.progress", 0);
        maxProgress = input.getIntOr("crystal_infuser.max_progress", 0);
        progressBubble = input.getIntOr("crystal_infuser.progress_bubble", 0);
        maxProgress_bubble = input.getIntOr("crystal_infuser.max_progress_bubble", 0);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if(hasRecipe() && canCraft()) {
            increaseCraftingProgress();
            setChanged(level, blockPos, blockState);

            if(hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void craftItem() {
        Optional<RecipeHolder<CrystalInfuserRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;
        CrystalInfuserRecipe r = recipe.get().value();

        ItemStack normalOutput = new ItemStack(r.output().getItem(), r.outputCount());
        ItemStack bottleOutput = new ItemStack(r.outputBottle().getItem(), r.outputCount());

        System.out.println(itemHandler.getStackInSlot(INPUT_SLOT).getCount());

        itemHandler.extractItem(INPUT_SLOT, r.inputCount(), false);
        itemHandler.extractItem(FUEL_SLOT, r.fuelCount(), false);

        itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(normalOutput.getItem(),
                itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + normalOutput.getCount()));

        itemHandler.setStackInSlot(OUTPUT_FUEL_SLOT, new ItemStack(bottleOutput.getItem(),
                itemHandler.getStackInSlot(OUTPUT_FUEL_SLOT).getCount() + bottleOutput.getCount()));
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

    private boolean canInsertItemIntoOutputSlot(ItemStack normalOutput, ItemStack bottleOutput) {
        return (itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                itemHandler.getStackInSlot(OUTPUT_SLOT).getItem() == normalOutput.getItem())
                && (itemHandler.getStackInSlot(OUTPUT_FUEL_SLOT).isEmpty() ||
                itemHandler.getStackInSlot(OUTPUT_FUEL_SLOT).getItem() == bottleOutput.getItem());
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<CrystalInfuserRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) return false;

        CrystalInfuserRecipe r = recipe.get().value();
        ItemStack normalOutput = new ItemStack(r.output().getItem(), r.outputCount());
        ItemStack bottleOutput = new ItemStack(r.outputBottle().getItem(), r.outputCount());

        return canInsertItemIntoOutputSlot(normalOutput, bottleOutput) &&
                canInsertAmountIntoOutputSlot(r.outputCount());
    }


    private Optional<RecipeHolder<CrystalInfuserRecipe>> getCurrentRecipe() {
        return ((ServerLevel) level).recipeAccess()
                .getRecipeFor(ModRecipes.CRYSTAL_INFUSER_TYPE.get(),
                        new CrystalInfuserRecipeInput(
                                itemHandler.getStackInSlot(INPUT_SLOT),    // slot 0
                                itemHandler.getStackInSlot(FUEL_SLOT)      // slot 1
                        ), level);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCountNormal = itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 :
                itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        int currentCountNormal = itemHandler.getStackInSlot(OUTPUT_SLOT).getCount();

        int maxCountBottle = itemHandler.getStackInSlot(OUTPUT_FUEL_SLOT).isEmpty() ? 64 :
                itemHandler.getStackInSlot(OUTPUT_FUEL_SLOT).getMaxStackSize();
        int currentCountBottle = itemHandler.getStackInSlot(OUTPUT_FUEL_SLOT).getCount();

        return (maxCountNormal >= currentCountNormal + count) &&
                (maxCountBottle >= currentCountBottle + count);
    }

    private boolean canCraft(){
        Optional<RecipeHolder<CrystalInfuserRecipe>> recipe = getCurrentRecipe();
        boolean enoughItem = itemHandler.getStackInSlot(INPUT_SLOT).getCount()>=recipe.get().value().inputCount();
        boolean enoughFuel = itemHandler.getStackInSlot(FUEL_SLOT).getCount()>=recipe.get().value().fuelCount();
        return enoughItem && enoughFuel;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
}