package net.diabolo.diabolomod.entity.custom.topaz_golem;

import com.google.common.collect.Maps;
import net.diabolo.diabolomod.DiaboloMod;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class TopazGolemRenderer extends MobRenderer<TopazGolemEntity, TopazGolemRenderState, TopazGolemModel> {
    private static final Map<TopazGolemVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(TopazGolemVariant.class), map -> {
                map.put(TopazGolemVariant.BASIC_BASIC,
                        Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "textures/entity/topaz_golem/topaz_golem.png"));
                map.put(TopazGolemVariant.BLASTER_BASIC,
                        Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "textures/entity/topaz_golem/topaz_golem_blaster.png"));
                map.put(TopazGolemVariant.MINER_BASIC,
                        Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "textures/entity/topaz_golem/topaz_golem_miner.png"));
                map.put(TopazGolemVariant.BLASTER_SPEED,
                        Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "textures/entity/topaz_golem/topaz_golem_blaster.png"));
                map.put(TopazGolemVariant.MINER_SPEED,
                        Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "textures/entity/topaz_golem/topaz_golem_miner.png"));
                map.put(TopazGolemVariant.BASIC_SPEED,
                        Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "textures/entity/topaz_golem/topaz_golem.png"));
            });

    public TopazGolemRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new TopazGolemModel(ctx.bakeLayer(TopazGolemModel.LAYER_LOCATION)), 0.7F);
        this.addLayer(new TopazGolemCracksLayer(this));
    }

    @Override
    public Identifier getTextureLocation(TopazGolemRenderState state) {
        return LOCATION_BY_VARIANT.getOrDefault(state.variant, LOCATION_BY_VARIANT.get(TopazGolemVariant.BASIC_BASIC));
    }

    @Override
    public TopazGolemRenderState createRenderState() {
        return new TopazGolemRenderState();
    }

    @Override
    public void extractRenderState(TopazGolemEntity entity, TopazGolemRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);

        state.variant = entity.getVariant();
        state.attackTicksRemaining = entity.getAttackAnimationTick() > 0.0F
                ? entity.getAttackAnimationTick() - partialTick : 0.0F;

        state.crackiness = entity.getCrackiness();
    }
}
