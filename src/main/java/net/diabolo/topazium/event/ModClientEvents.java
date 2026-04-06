package net.diabolo.topazium.event;

import net.diabolo.topazium.Topazium;
import net.diabolo.topazium.entity.ModEntities;
import net.diabolo.topazium.entity.custom.topaz_golem.TopazGolemModel;
import net.diabolo.topazium.entity.custom.topaz_golem.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Topazium.MODID, value = Dist.CLIENT)
public final class ModClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.TOPAZ_GOLEM.get(), TopazGolemRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(TopazGolemModel.LAYER_LOCATION, TopazGolemModel::createBodyLayer);
    }
}
