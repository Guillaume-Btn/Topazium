package net.diabolo.topazium.entity.custom.crystal_infuser;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class CrystalInfuserRenderState extends BlockEntityRenderState {
        public Level blockEntityLevel;
        public BlockPos lightPosition;
        public float rotation; // Stocke la rotation calculée
        public final ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
}