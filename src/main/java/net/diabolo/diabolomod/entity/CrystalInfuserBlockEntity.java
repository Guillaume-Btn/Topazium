package net.diabolo.diabolomod.entity;

import net.diabolo.diabolomod.item.ModItems;
import net.diabolo.diabolomod.recipe.CrystalInfuserRecipe;
import net.diabolo.diabolomod.recipe.CrystalInfuserRecipeInput;
import net.diabolo.diabolomod.recipe.ModRecipes;
import net.diabolo.diabolomod.screen.custom.CrystalInfuserMenu;
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
    };
    private float rotation;
    private static final int INPUT_SLOT = 0;
    private static final int FUEL_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private static final int OUTPUT_FUEL_SLOT = 3;


    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;

    public CrystalInfuserBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CRYSTAL_INFUSER_BE.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> CrystalInfuserBlockEntity.this.progress;
                    case 1 -> CrystalInfuserBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0: CrystalInfuserBlockEntity.this.progress = value;
                    case 1: CrystalInfuserBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
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
        output.putInt("crystal_infuser.progress", progress);
        output.putInt("crystal_infuser.max_progress", maxProgress);

        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        itemHandler.deserialize(input);
        progress = input.getIntOr("crystal_infuser.progress", 0);
        maxProgress = input.getIntOr("crystal_infuser.max_progress", 0);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if(hasRecipe()) {
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
        ItemStack output = recipe.get().value().output();

        itemHandler.extractItem(INPUT_SLOT, 1, false);
        itemHandler.extractItem(FUEL_SLOT, 1, false);

        itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));

        itemHandler.setStackInSlot(OUTPUT_FUEL_SLOT,new ItemStack(output.getItem(),
                itemHandler.getStackInSlot(OUTPUT_FUEL_SLOT).getCount()+output.getCount()));
    }

    private void resetProgress() {
        progress = 0;
        maxProgress = 72;
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<CrystalInfuserRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) {
            return false;
        }
        boolean hasFuel = itemHandler.getStackInSlot(FUEL_SLOT).getItem() == ModItems.COBALT_SOLUTION.get();
        ItemStack output = recipe.get().value().output();
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output) && hasFuel;
    }

    private Optional<RecipeHolder<CrystalInfuserRecipe>> getCurrentRecipe() {
        return ((ServerLevel) this.level).recipeAccess()
                .getRecipeFor(ModRecipes.CRYSTAL_INFUSER_TYPE.get(), new CrystalInfuserRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT)), level);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return (itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                itemHandler.getStackInSlot(OUTPUT_SLOT).getItem() == output.getItem())
                && (itemHandler.getStackInSlot(OUTPUT_FUEL_SLOT).isEmpty() ||
                itemHandler.getStackInSlot(OUTPUT_FUEL_SLOT).getItem() == output.getItem());
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = itemHandler.getStackInSlot(OUTPUT_SLOT).getCount();

        int maxCountFuel = itemHandler.getStackInSlot(OUTPUT_FUEL_SLOT).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        int currentCountFuel = itemHandler.getStackInSlot(OUTPUT_FUEL_SLOT).getCount();

        return (maxCount >= currentCount + count) && (maxCountFuel>=currentCountFuel + count);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
}