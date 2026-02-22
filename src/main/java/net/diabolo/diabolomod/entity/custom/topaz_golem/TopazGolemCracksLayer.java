package net.diabolo.diabolomod.entity.custom.topaz_golem;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class TopazGolemCracksLayer extends RenderLayer<TopazGolemRenderState, TopazGolemModel> {
    private static final Map<TopazGolemEntity.Crackiness, Identifier> RESOURCE_LOCATIONS = Util.make(Maps.newEnumMap(TopazGolemEntity.Crackiness.class), (map) -> {
        map.put(TopazGolemEntity.Crackiness.LOW, Identifier.withDefaultNamespace("textures/entity/iron_golem/iron_golem_crackiness_low.png"));
        map.put(TopazGolemEntity.Crackiness.MEDIUM, Identifier.withDefaultNamespace("textures/entity/iron_golem/iron_golem_crackiness_medium.png"));
        map.put(TopazGolemEntity.Crackiness.HIGH, Identifier.withDefaultNamespace("textures/entity/iron_golem/iron_golem_crackiness_high.png"));
    });

    public TopazGolemCracksLayer(RenderLayerParent<TopazGolemRenderState, TopazGolemModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, TopazGolemRenderState state, float yRot, float xRot) {
        if (state.crackiness == TopazGolemEntity.Crackiness.NONE) {
            return;
        }

        Identifier texture = RESOURCE_LOCATIONS.get(state.crackiness);
        coloredCutoutModelCopyLayerRender(
                this.getParentModel(),
                texture,
                poseStack,
                nodeCollector,
                packedLight,
                state,
                -1,
                0
        );
    }
}
