package net.diabolo.diabolomod.entity.custom.golem_maker;

import net.diabolo.diabolomod.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull; // ou l'import que tu utilises pour @NonNull
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class GolemMakerPartEntity extends BlockEntity {
    private BlockPos masterPos = null;

    public GolemMakerPartEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GOLEM_MAKER_PART_BE.get(), pos, state);
    }

    public void setMasterPos(BlockPos pos) {
        this.masterPos = pos;
        this.setChanged();
    }

    public BlockPos getMasterPos() {
        return this.masterPos;
    }

    public GolemMakerControllerEntity getMasterEntity() {
        if (masterPos != null && level != null) {
            BlockEntity be = level.getBlockEntity(masterPos);
            if (be instanceof GolemMakerControllerEntity) {
                return (GolemMakerControllerEntity) be;
            }
        }
        return null;
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        boolean hasMaster = (masterPos != null);
        output.putBoolean("has_master", hasMaster);
        if (hasMaster) {
            output.putInt("master_x", masterPos.getX());
            output.putInt("master_y", masterPos.getY());
            output.putInt("master_z", masterPos.getZ());
        }
        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        boolean hasMaster = input.getBooleanOr("has_master", false);
        if (hasMaster) {
            int x = input.getIntOr("master_x", 0);
            int y = input.getIntOr("master_y", 0);
            int z = input.getIntOr("master_z", 0);
            this.masterPos = new BlockPos(x, y, z);
        } else {
            this.masterPos = null;
        }
    }
}
