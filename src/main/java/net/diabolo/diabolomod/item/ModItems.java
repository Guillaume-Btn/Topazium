package net.diabolo.diabolomod.item;

import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.item.custom.HammerItem;
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
    public static final DeferredItem<Item> TOPAZ_SPEAR = ITEMS.registerItem("topaz_spear",
            (properties) -> new Item(properties.spear(ModToolTiers.TOPAZ, 1.05F, 1.075F, 0.5F, 3.0F, 7.5F, 6.5F, 5.1F, 10.0F, 4.6F)));

    public static final DeferredItem<HammerItem> TOPAZ_HAMMER = ITEMS.registerItem("topaz_hammer",
            (properties) -> new HammerItem(properties.pickaxe(ModToolTiers.TOPAZ, 7F, -3.5f)));

    public static final DeferredItem<Item> BLUE_TOPAZ = ITEMS.registerSimpleItem("blue_topaz", props -> props);
    public static final DeferredItem<Item> BLUE_TOPAZ_SWORD = ITEMS.registerItem("blue_topaz_sword",
            (properties) -> new Item(properties.sword(ModToolTiers.BLUE_TOPAZ, 7, -2.4f)));
    public static final DeferredItem<Item> BLUE_TOPAZ_PICKAXE = ITEMS.registerItem("blue_topaz_pickaxe",
            (properties) -> new Item(properties.pickaxe(ModToolTiers.BLUE_TOPAZ, 1.0F, -2.8f)));
    public static final DeferredItem<ShovelItem> BLUE_TOPAZ_SHOVEL = ITEMS.registerItem("blue_topaz_shovel",
            (properties) -> new ShovelItem(ModToolTiers.BLUE_TOPAZ, 1.5F, -3.0f, properties));
    public static final DeferredItem<AxeItem> BLUE_TOPAZ_AXE = ITEMS.registerItem("blue_topaz_axe",
            (properties) -> new AxeItem(ModToolTiers.BLUE_TOPAZ, 6.0F, -3.2f, properties));
    public static final DeferredItem<HoeItem> BLUE_TOPAZ_HOE = ITEMS.registerItem("blue_topaz_hoe",
            (properties) -> new HoeItem(ModToolTiers.BLUE_TOPAZ, 0F, -3.0f, properties));
    public static final DeferredItem<Item> BLUE_TOPAZ_SPEAR = ITEMS.registerItem("blue_topaz_spear",
            (properties) -> new Item(properties.spear(ModToolTiers.BLUE_TOPAZ, 0.85F, 1.3F, 0.3F, 2.0F, 5.5F, 4.5F, 5.1F, 6.75F, 4.6F)));

    public static final DeferredItem<HammerItem> BLUE_TOPAZ_HAMMER = ITEMS.registerItem("blue_topaz_hammer",
            (properties) -> new HammerItem(properties.pickaxe(ModToolTiers.BLUE_TOPAZ, 7F, -3.5f)));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
