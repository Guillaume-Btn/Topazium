package net.diabolo.topazium.entity.custom.component_table;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import javax.annotation.Nullable;

// package client.renderer.blockentity;
public class ComponentTableRenderer implements BlockEntityRenderer<ComponentTableBlockEntity, ComponentTableRenderState> {
    private final ItemModelResolver itemModelResolver;

    public ComponentTableRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public ComponentTableRenderState createRenderState() {
        return new ComponentTableRenderState();
    }

    @Override
    public void extractRenderState(ComponentTableBlockEntity blockEntity,
                                   ComponentTableRenderState renderState,
                                   float partialTick,
                                   @NonNull Vec3 cameraPosition,
                                   @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

        renderState.lightPosition = blockEntity.getBlockPos();
        renderState.blockEntityLevel = blockEntity.getLevel();

        ItemStack stack = blockEntity.getDisplayedStack();

        itemModelResolver.updateForTopItem(
                renderState.itemStackRenderState,
                stack,
                ItemDisplayContext.FIXED,
                blockEntity.getLevel(),
                null,
                0
        );
    }


    @Override
    public void submit(ComponentTableRenderState renderState,
                       @NonNull PoseStack poseStack,
                       @NonNull SubmitNodeCollector nodeCollector,
                       @NonNull CameraRenderState cameraRenderState) {
        if (renderState.itemStackRenderState.isEmpty()) return;

        poseStack.pushPose();
        poseStack.translate(0.5f, 1.01f, 0.5f);
        poseStack.scale(0.6f, 0.6f, 0.6f);

        poseStack.mulPose(Axis.XP.rotationDegrees(90f));

        renderState.itemStackRenderState.submit(
                poseStack,
                nodeCollector,
                getLightLevel(renderState.blockEntityLevel, renderState.lightPosition),
                OverlayTexture.NO_OVERLAY,
                0
        );

        poseStack.popPose();
    }


    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightCoordsUtil.pack(bLight, sLight);
    }

}


