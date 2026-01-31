package net.diabolo.diabolomod.entity.rendere;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.diabolo.diabolomod.entity.CrystalInfuserBlockEntity;
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

public class CrystalInfuserBlockEntityRenderer implements BlockEntityRenderer<CrystalInfuserBlockEntity, CrystalInfuserBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;

    public CrystalInfuserBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        itemModelResolver = context.itemModelResolver();
    }

    @Override
    public CrystalInfuserBlockEntityRenderState createRenderState() {
        return new CrystalInfuserBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(CrystalInfuserBlockEntity blockEntity, CrystalInfuserBlockEntityRenderState renderState, float partialTick,
                                   Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

        renderState.lightPosition = blockEntity.getBlockPos();
        renderState.blockEntityLevel = blockEntity.getLevel();
        renderState.rotation = blockEntity.getRenderingRotation();

        itemModelResolver.updateForTopItem(renderState.itemStackRenderState,
                blockEntity.itemHandler.getStackInSlot(0), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
    }

    @Override
    public void submit(CrystalInfuserBlockEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();

        // --- MODIFICATION ICI ---
        // Avant (Piédestal) : 1.15f en Y (c'est-à-dire 1.15 bloc de hauteur, donc au-dessus)
        // poseStack.translate(0.5f, 1.15f, 0.5f);

        // Maintenant (Infuseur) : 0.5f en Y (c'est-à-dire la moitié de la hauteur, pile au milieu)
        poseStack.translate(0.5f, 0.5f, 0.5f);

        // --- OPTIONNEL : LA TAILLE ---
        // Si l'item touche les vitres de ton bloc, réduis un peu l'échelle ici (ex: 0.4f)
        poseStack.scale(0.35f, 0.35f, 0.35f);

        // La rotation (l'item tourne sur lui-même au centre)
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
