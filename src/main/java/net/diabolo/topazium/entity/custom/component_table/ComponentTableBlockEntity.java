package net.diabolo.topazium.entity.custom.component_table;

import net.diabolo.topazium.entity.ModBlockEntities;
import net.diabolo.topazium.item.ModItems;
import net.diabolo.topazium.util.ModTags;
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

import java.util.List;

public class ComponentTableBlockEntity extends BlockEntity {
    private final ItemStacksResourceHandler itemHandler = new ItemStacksResourceHandler(1) {
        @Override
        protected void onContentsChanged(int slot, ItemStack previousContents) {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_INVISIBLE);
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
        return itemHandler.getResource(0).is(ModTags.Items.CAN_BE_HAMMERED);
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
        if (itemHandler.getResource(0).isEmpty()) return false;
        if (!can_be_hammered()) return false;
        return !itemHandler.getResource(0).getItem().equals(AVAILABLE_PATTERNS.get(selectedPatternIndex));
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
        try (Transaction tx = Transaction.openRoot()) {
            int extracted = itemHandler.extract(0, itemHandler.getResource(0), 1, tx);
            if (extracted == 1) {
                // 2. Insérer le résultat (1 quantité)
                int inserted = itemHandler.insert(0, ItemResource.of(getCurrentPatternOutput()), 1, tx);
                if (inserted == 1) {
                    tx.commit();
                }
            }
        }
    }

    public ItemStacksResourceHandler getItemHandler() {
        return itemHandler;
    }

    public ItemStack getDisplayedStack() {
        return itemHandler.getResource(0).toStack(); // adapte si ton handler a un autre nom
    }


    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        itemHandler.serialize(output);
        output.putInt("component_table.hits", hammerHits);
        output.putInt("component_table.pattern", selectedPatternIndex);

        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
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
        // size() remplace l'ancien getSlots()
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
    public @NonNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider registries) {
        // Utilise la version vanilla/NeoForge qui s’appuie sur saveAdditional
        return saveWithoutMetadata(registries);
    }

}
