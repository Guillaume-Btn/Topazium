package net.diabolo.diabolomod.item;

import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DiaboloMod.MODID);

    public static final Supplier<CreativeModeTab> ITEMS_TAB=CREATIVE_MODE_TAB.register("items_tab",
            () -> CreativeModeTab.builder().icon(
                    () -> new ItemStack(ModItems.TOPAZ.get()))
                    .title(Component.translatable("creativetab.diabolomod.items"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        // zone d'enregisrement des items dans la creative tab
                        output.accept(ModItems.TOPAZ);
                        output.accept(ModItems.RAW_TOPAZ);
                    }))
                    .build());

    public static final Supplier<CreativeModeTab> BLOCKS_TAB=CREATIVE_MODE_TAB.register("blocks_tab",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModBlocks.TOPAZ_BLOCK))
                    .title(Component.translatable("creativetab.diabolomod.blocks"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        // zone d'enregisrement des items dans la creative tab
                        output.accept(ModBlocks.TOPAZ_BLOCK);
                        output.accept(ModBlocks.TOPAZ_ORE);
                        output.accept(ModBlocks.TOPAZ_DEEPSLATE_ORE);
                    }))
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
