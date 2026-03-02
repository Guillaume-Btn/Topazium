package net.diabolo.diabolomod.screen.custom;

import net.diabolo.diabolomod.block.ModBlocks;
import net.diabolo.diabolomod.screen.ModMenuTypes;
import net.diabolo.diabolomod.entity.custom.crystal_infuser.CrystalInfuserBlockEntity;
import net.diabolo.diabolomod.util.ModTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import org.jspecify.annotations.NonNull;

public class CrystalInfuserMenu extends AbstractContainerMenu {
    public final CrystalInfuserBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public CrystalInfuserMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, null, new SimpleContainerData(4));
    }

    public CrystalInfuserMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.CRYSTAL_INFUSER_MENU.get(), pContainerId);

        this.blockEntity = ((CrystalInfuserBlockEntity) entity);
        this.level = inv.player.level();
        this.data = data;

        // Ton code d'origine pour l'inventaire du joueur
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inv, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }

        // Ton code d'origine pour la hotbar du joueur
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inv, i, 8 + i * 18, 142));
        }

        var handler = (this.blockEntity != null) ? this.blockEntity.itemHandler : new ItemStacksResourceHandler(4) {};

        this.addSlot(new ResourceHandlerSlot(handler, handler::set, 0, 54, 16) {
            @Override
            public boolean mayPlace(@NonNull ItemStack stack) {
                return stack.is(ModTags.Items.CAN_BE_INFUSED);
            }
        });

        this.addSlot(new ResourceHandlerSlot(handler, handler::set, 1, 54, 53) {
            @Override
            public boolean mayPlace(@NonNull ItemStack stack) {
                return stack.is(ModTags.Items.IS_INFUSER);
            }
        });

        this.addSlot(new ResourceHandlerSlot(handler, handler::set, 2, 104, 34) {
            @Override
            public boolean mayPlace(@NonNull ItemStack stack) {
                return false; // Le joueur ne peut rien mettre ici
            }
        });

        this.addSlot(new ResourceHandlerSlot(handler, handler::set, 3, 133, 34) {
            @Override
            public boolean mayPlace(@NonNull ItemStack stack) {
                return false; // Le joueur ne peut rien mettre ici
            }
        });

        addDataSlots(data);
    }

    public int getScaledBubbleProgress1() {
        int progress = this.data.get(2);
        int maxProgress = this.data.get(1);
        int arrowPixelSize = 16;
        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int arrowPixelSize = 24;
        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 4;

    @Override
    public @NonNull ItemStack quickMoveStack(@NonNull Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Depuis slots VANILLA (0-35) → vers TE (36-39)
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        }
        // Depuis slots TE (36-39) → vers VANILLA (0-35)
        else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(@NonNull Player pPlayer) {
        if (this.blockEntity == null) return true;

        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.CRYSTAL_INFUSER.get());
    }
}
