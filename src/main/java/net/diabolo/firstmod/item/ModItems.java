package net.diabolo.firstmod.item;

import net.diabolo.firstmod.FirstMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FirstMod.MODID);
    // Remplacez votre ligne actuelle par celle-ci :
    public static final DeferredItem<Item> BISMUTH = ITEMS.registerSimpleItem("bismuth", props -> props);
    public static final DeferredItem<Item> RAW_BISMUTH = ITEMS.registerSimpleItem("raw_bismuth", props -> props);


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
