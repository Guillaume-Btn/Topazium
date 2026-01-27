package net.diabolo.diabolomod.item;

import net.diabolo.diabolomod.DiaboloMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DiaboloMod.MODID);
    // Remplacez votre ligne actuelle par celle-ci :
    public static final DeferredItem<Item> TOPAZ = ITEMS.registerSimpleItem("topaz", props -> props);
    public static final DeferredItem<Item> RAW_TOPAZ = ITEMS.registerSimpleItem("raw_topaz", props -> props);

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
