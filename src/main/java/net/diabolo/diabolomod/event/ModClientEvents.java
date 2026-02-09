package net.diabolo.diabolomod.event;

import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.entity.ModEntities;
import net.diabolo.diabolomod.entity.client.TopazGolemModel;
import net.diabolo.diabolomod.entity.client.TopazGolemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = DiaboloMod.MODID, value = Dist.CLIENT)
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
