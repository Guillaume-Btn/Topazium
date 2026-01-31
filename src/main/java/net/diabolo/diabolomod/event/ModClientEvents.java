package net.diabolo.diabolomod.event;

import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.block.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

// Cette annotation dit à NeoForge : "Écoute les événements de ce fichier, mais SEULEMENT sur le Client (pas le serveur)"
@EventBusSubscriber(modid = DiaboloMod.MODID)
public class ModClientEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // C'est ici qu'on définit la transparence !

        // Option 1 : CUTOUT (Pour du verre classique, des barreaux, de l'herbe)
        // Les pixels sont soit 100% visibles, soit 100% invisibles. Pas de "demi-transparence".
//         C'est le plus courant pour les machines vitrées.
//        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTAL_INFUSER.get(), RenderType.cutout());

        // Option 2 : TRANSLUCENT (Pour de la glace teintée, de l'eau, des vitres colorées)
        // Permet la semi-transparence (pixels à 50% d'opacité).
        // Choisis celle-ci si ta texture blanche était semi-transparente.
        //ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTAL_INFUSER.get(), RenderType.translucent());

    }
}