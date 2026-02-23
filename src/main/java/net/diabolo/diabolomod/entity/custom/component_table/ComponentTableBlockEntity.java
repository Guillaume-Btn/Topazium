package net.diabolo.diabolomod.entity.custom.component_table;

import net.diabolo.diabolomod.entity.ModBlockEntities;
import net.diabolo.diabolomod.item.ModItems;
import net.diabolo.diabolomod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ComponentTableBlockEntity extends BlockEntity {
    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 4);
            }
        }
    };
    private final List<Item> AVAILABLE_PATTERNS = List.of(
            ModItems.GOLEM_HEAD_BASIC.get(),
            ModItems.GOLEM_ARMS_BASIC.get(),
            ModItems.GOLEM_ARMS_MINER.get(),
            ModItems.GOLEM_ARMS_BLASTER.get(),
            ModItems.GOLEM_LEGS_BASIC.get(),
            ModItems.GOLEM_LEGS_WHEELS.get()
    );
    private int selectedPatternIndex = 0; // Quel item on va crafter ?
    private int hammerHits = 0; // Progression du craft
    public ComponentTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.COMPONENT_TABLE_BE.get(), pos, blockState);
    }

    public boolean can_be_hammered() {
        return itemHandler.getStackInSlot(0).is(ModTags.Items.CAN_BE_HAMMERED);
    }

    public void cyclePattern() {
        selectedPatternIndex++;
        if (selectedPatternIndex >= AVAILABLE_PATTERNS.size()) {
            selectedPatternIndex = 0;
        }
        hammerHits = 0; // Reset la progression si on change de plan
        setChanged();
    }

    public Item getCurrentPatternOutput() {
        return AVAILABLE_PATTERNS.get(selectedPatternIndex);
    }

    public boolean canHit() {
        if (itemHandler.getStackInSlot(0).isEmpty()) return false;
        if (!can_be_hammered()) return false;
        if (itemHandler.getStackInSlot(0).getItem().equals(AVAILABLE_PATTERNS.get(selectedPatternIndex))) return false;
        return true;
    }

    public boolean hammerHit() {
        if (!canHit()) return false;
        hammerHits++;
        setChanged();

        // C'est prêt ?
        // Il faut taper 4 fois
        int HITS_REQUIRED = 4;
        if (hammerHits >= HITS_REQUIRED) {
            craftItem();
            hammerHits = 0;
            return true; // Retourne true si le craft est fini
        }
        return false; // Craft en cours
    }

    private void craftItem() {
        // Consomme le bloc d'entrée
        itemHandler.extractItem(0, 1, false);

        // Crée le résultat
        ItemStack result = new ItemStack(getCurrentPatternOutput());

        itemHandler.insertItem(0, result, false);
        // Drop l'item sur le bloc (ou spawn dans le monde)
//        Containers.dropItemStack(level, worldPosition.getX() + 0.5, worldPosition.getY() + 1, worldPosition.getZ() + 0.5, result);
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public ItemStack getDisplayedStack() {
        return itemHandler.getStackInSlot(0); // adapte si ton handler a un autre nom
    }


    @Override
    protected void saveAdditional(ValueOutput output) {
        itemHandler.serialize(output);
        output.putInt("component_table.hits", hammerHits);
        output.putInt("component_table.pattern", selectedPatternIndex);

        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        itemHandler.deserialize(input);
        hammerHits = input.getIntOr("component_table.hits", 0);
        selectedPatternIndex = input.getIntOr("component_table.pattern", 0);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
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
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        // Utilise la version vanilla/NeoForge qui s’appuie sur saveAdditional
        return saveWithoutMetadata(registries);
    }

}
