package net.diabolo.diabolomod.entity.custom.crystal_infuser;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CrystalInfuserRenderer implements BlockEntityRenderer<CrystalInfuserBlockEntity, CrystalInfuserRenderState> {
    private final ItemModelResolver itemModelResolver;

    public CrystalInfuserRenderer(BlockEntityRendererProvider.Context context) {
        itemModelResolver = context.itemModelResolver();
    }

    @Override
    public CrystalInfuserRenderState createRenderState() {
        return new CrystalInfuserRenderState();
    }

    @Override
    public void extractRenderState(CrystalInfuserBlockEntity blockEntity, CrystalInfuserRenderState renderState, float partialTick,
                                   Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

        renderState.lightPosition = blockEntity.getBlockPos();
        renderState.blockEntityLevel = blockEntity.getLevel();

        // --- MODIFICATION ICI ---
        // On passe partialTick pour avoir l'interpolation fluide
        renderState.rotation = blockEntity.getRenderingRotation(partialTick);

        itemModelResolver.updateForTopItem(renderState.itemStackRenderState,
                blockEntity.itemHandler.getStackInSlot(0), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
    }

    @Override
    public void submit(CrystalInfuserRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();

        // Position au centre
        poseStack.translate(0.5f, 0.5f, 0.5f);

        // Taille de l'item
        poseStack.scale(0.35f, 0.35f, 0.35f);

        // Rotation (qui sera maintenant rapide si ça craft)
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.rotation));

        renderState.itemStackRenderState.submit(poseStack, nodeCollector, getLightLevel(renderState.blockEntityLevel,
                renderState.lightPosition), OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}