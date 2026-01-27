package net.diabolo.diabolomod.item;

import net.diabolo.diabolomod.DiaboloMod;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DiaboloMod.MODID);
    // Remplacez votre ligne actuelle par celle-ci :
    public static final DeferredItem<Item> TOPAZ = ITEMS.registerSimpleItem("topaz", props -> props);
    public static final DeferredItem<Item> RAW_TOPAZ = ITEMS.registerSimpleItem("raw_topaz", props -> props);

    public static final DeferredItem<Item> TOPAZ_SWORD = ITEMS.registerItem("topaz_sword",
            (properties) -> new Item(properties.sword(ModToolTiers.TOPAZ, 5, -2.4f)));
    public static final DeferredItem<Item> TOPAZ_PICKAXE = ITEMS.registerItem("topaz_pickaxe",
            (properties) -> new Item(properties.pickaxe(ModToolTiers.TOPAZ, 1.0F, -2.8f)));
    public static final DeferredItem<ShovelItem> TOPAZ_SHOVEL = ITEMS.registerItem("topaz_shovel",
            (properties) -> new ShovelItem(ModToolTiers.TOPAZ, 1.5F, -3.0f, properties));
    public static final DeferredItem<AxeItem> TOPAZ_AXE = ITEMS.registerItem("topaz_axe",
            (properties) -> new AxeItem(ModToolTiers.TOPAZ, 6.0F, -3.2f, properties));
    public static final DeferredItem<HoeItem> TOPAZ_HOE = ITEMS.registerItem("topaz_hoe",
            (properties) -> new HoeItem(ModToolTiers.TOPAZ, 0F, -3.0f, properties));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
