package net.diabolo.diabolomod.entity.rendere;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class CrystalInfuserBlockEntityRenderState extends BlockEntityRenderState{
        public BlockPos lightPosition;
        public Level blockEntityLevel;
        public float rotation;
        final ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
}
