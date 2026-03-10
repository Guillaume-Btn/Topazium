package net.diabolo.diabolomod.entity.custom.golem_maker;

import net.diabolo.diabolomod.block.ModBlocks;
import net.diabolo.diabolomod.block.custom.GolemMakerControllerBlock;
import net.diabolo.diabolomod.block.custom.GolemMakerPartBlock;
import net.diabolo.diabolomod.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.level.block.Block.UPDATE_CLIENTS;

public class GolemMakerControllerEntity extends BlockEntity {
    private final List<BlockPos> structureBlocks = new ArrayList<>();
    private boolean isAssembled = false;
    private int tickCounter = 0;
    private boolean isFirstTick = true;

    public GolemMakerControllerEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GOLEM_MAKER_CONTROLLER_BE.get(), pos, state);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level == null || level.isClientSide()) return;
        tickCounter++;
        if (tickCounter % 20 == 0) {
            Direction facing = state.getValue(GolemMakerControllerBlock.FACING);
            boolean isValid = checkStructure(level, pos, facing);
            if (isFirstTick) {
                this.isAssembled = isValid;
                this.isFirstTick = false;
                if (isValid) {
                    updateStructureState(level, state, true);
                }
                return;
            }
            if (isValid != isAssembled) {
                this.isAssembled = isValid;
                updateStructureState(level, state, isValid);
                AABB machineBox = new AABB(
                        pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2,
                        pos.getX() + 3, pos.getY() + 2, pos.getZ() + 2
                );
                AABB searchArea = machineBox.inflate(5.0D);
                List<Player> nearbyPlayers = level.getEntitiesOfClass(Player.class, searchArea);
                for (Player player : nearbyPlayers) {
                    if (isValid) {
                        player.displayClientMessage(Component.literal("§a[Golem Maker] : ON"), false);
                    } else {
                        player.displayClientMessage(Component.literal("§c[Golem Maker] : OFF"), false);
                    }
                }
            }
        }
    }

    private void updateStructureState(Level level, BlockState state, boolean assembled) {
        level.setBlock(this.worldPosition, state.setValue(GolemMakerControllerBlock.ASSEMBLED, assembled), Block.UPDATE_NEIGHBORS | UPDATE_CLIENTS);

        for (BlockPos partPos : structureBlocks) {
            BlockState partState = level.getBlockState(partPos);
            if (partState.hasProperty(GolemMakerPartBlock.ASSEMBLED)) {
                level.setBlock(partPos, partState.setValue(GolemMakerPartBlock.ASSEMBLED, assembled), Block.UPDATE_NEIGHBORS | UPDATE_CLIENTS);

                BlockEntity be = level.getBlockEntity(partPos);
                if (be instanceof GolemMakerPartEntity partEntity) {
                    if (assembled) {
                        partEntity.setMasterPos(this.worldPosition); // Donne la position
                    } else {
                        partEntity.setMasterPos(null); // Retire la position si la machine se casse
                    }
                }
            }
        }
        if (!assembled) {
            structureBlocks.clear();
        }
    }

    private boolean checkStructure(Level level, BlockPos center, Direction facing) {
        structureBlocks.clear();
        char[][][] pattern = {
                {
                        {'F', 'F', 'F', 'F', 'F', 'F'},
                        {'F', 'F', 'F', 'F', 'C', 'F'},
                        {'F', 'F', 'F', 'F', 'F', 'F'}
                },
                {
                        {'C', 'R', 'C', 'Y', 'Y', 'Y'},
                        {'R', 'A', 'C', 'Y', 'Y', 'Y'},
                        {'C', 'R', 'C', 'Y', 'Y', 'Y'}
                },
                {
                        {'C', 'R', 'C', 'Y', 'Y', 'Y'},
                        {'R', 'A', 'B', 'Y', 'Y', 'Y'},
                        {'C', 'R', 'C', 'Y', 'Y', 'Y'}
                },
                {
                        {'C', 'C', 'C', 'Y', 'Y', 'Y'},
                        {'C', 'T', 'C', 'Y', 'Y', 'Y'},
                        {'C', 'C', 'C', 'Y', 'Y', 'Y'}
                },
                {
                        {'Y', 'Y', 'Y', 'Y', 'Y', 'Y'},
                        {'Y', 'D', 'Y', 'Y', 'Y', 'Y'},
                        {'Y', 'Y', 'Y', 'Y', 'Y', 'Y'}
                }
        };
        for (int y = 0; y < pattern.length; y++) {
            for (int z = 0; z < pattern[y].length; z++) {
                for (int x = 0; x < pattern[y][z].length; x++) {
                    char expectedBlock = pattern[y][z][x];
                    // On ignore 'Y' (vide/n'importe quoi) et 'B' (le contrôleur lui-même)
                    if (expectedBlock == 'Y' || expectedBlock == 'B') continue;

                    // IMPORTANT : On doit calculer l'offset par rapport au contrôleur 'B'
                    // 'B' est à y=2, z=1, x=2 dans ce tableau.
                    int offsetX = x - 2;
                    int offsetY = y - 2; // attention, la couche 1 dans ton tab c'est y=0, mais physiquement c'est y=-1
                    int offsetZ = z - 1;

                    BlockPos targetPos = rotateOffset(center, offsetX, offsetY, offsetZ, facing);
                    if (!isValidBlock(expectedBlock, level.getBlockState(targetPos).getBlock())) {
                        structureBlocks.clear();
                        return false;
                    }

                    structureBlocks.add(targetPos);
                }
            }
        }
        return true;
    }


    private boolean isValidBlock(char character, Block blockInWorld) {
        return switch (character) {
            case 'C' -> blockInWorld == ModBlocks.GOLEM_MAKER_CASING.get();
            case 'F' -> blockInWorld == ModBlocks.GOLEM_MAKER_FOUNDATION.get();
            case 'B' -> blockInWorld == ModBlocks.GOLEM_MAKER_CONTROLLER.get();
            case 'D' -> blockInWorld == ModBlocks.GOLEM_MAKER_CATALYST.get();
            case 'T' -> blockInWorld == ModBlocks.GOLEM_MAKER_TRANSFORMER.get();
            case 'R' -> blockInWorld == ModBlocks.GOLEM_MAKER_COOLER.get();
            case 'A' -> blockInWorld == ModBlocks.GOLEM_MAKER_ASSEMBLER.get();
            default -> false;
        };
    }

    private BlockPos rotateOffset(BlockPos center, int offsetX, int offsetY, int offsetZ, Direction facing) {
        return switch (facing) {
            case WEST -> center.offset(-offsetX, offsetY, -offsetZ);
            case SOUTH -> center.offset(-offsetZ, offsetY, offsetX);
            case NORTH -> center.offset(offsetZ, offsetY, -offsetX);
            default -> center.offset(offsetX, offsetY, offsetZ);
        };
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
        output.putBoolean("is_assembled", this.isAssembled);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        this.isAssembled = input.getBooleanOr("is_assembled", false);
    }
}
