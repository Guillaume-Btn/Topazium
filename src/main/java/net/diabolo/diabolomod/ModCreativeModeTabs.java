package net.diabolo.diabolomod;

import net.diabolo.diabolomod.block.ModBlocks;
import net.diabolo.diabolomod.item.ModItems;
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
                        output.accept(ModItems.BLUE_TOPAZ);
                        output.accept(ModItems.COBALT_SOLUTION);
                        output.accept(ModItems.COBALT_POWDER);
                        output.accept(ModItems.RAW_BLUE_TOPAZ);
                    }))
                    .build());

    public static final Supplier<CreativeModeTab> BLOCKS_TAB=CREATIVE_MODE_TAB.register("blocks_tab",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModBlocks.TOPAZ_BLOCK))
                    .title(Component.translatable("creativetab.diabolomod.blocks"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.TOPAZ_BLOCK);
                        output.accept(ModBlocks.BLUE_TOPAZ_BLOCK);
                        output.accept(ModBlocks.TOPAZ_ORE);
                        output.accept(ModBlocks.TOPAZ_DEEPSLATE_ORE);
                        output.accept(ModBlocks.CRYSTAL_INFUSER);
                        output.accept(ModBlocks.COMPONENT_TABLE);
                    }))
                    .build());

    public static final Supplier<CreativeModeTab> TOOLS_TAB=CREATIVE_MODE_TAB.register("tools_tab",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModItems.TOPAZ_SWORD.get()))
                    .title(Component.translatable("creativetab.diabolomod.tools"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(ModItems.TOPAZ_SWORD);
                        output.accept(ModItems.TOPAZ_PICKAXE);
                        output.accept(ModItems.TOPAZ_AXE);
                        output.accept(ModItems.TOPAZ_SHOVEL);
                        output.accept(ModItems.TOPAZ_HOE);
                        output.accept(ModItems.TOPAZ_HAMMER);
                        output.accept(ModItems.TOPAZ_SPEAR);
                        output.accept(ModItems.BLUE_TOPAZ_SPEAR);
                        output.accept(ModItems.BLUE_TOPAZ_SWORD);
                        output.accept(ModItems.BLUE_TOPAZ_PICKAXE);
                        output.accept(ModItems.BLUE_TOPAZ_AXE);
                        output.accept(ModItems.BLUE_TOPAZ_SHOVEL);
                        output.accept(ModItems.BLUE_TOPAZ_HOE);
                        output.accept(ModItems.BLUE_TOPAZ_HAMMER);

                        output.accept(ModItems.TOPAZ_HELMET);
                        output.accept(ModItems.TOPAZ_CHESTPLATE);
                        output.accept(ModItems.TOPAZ_LEGGINGS);
                        output.accept(ModItems.TOPAZ_BOOTS);

                        output.accept(ModItems.BLUE_TOPAZ_HELMET);
                        output.accept(ModItems.BLUE_TOPAZ_CHESTPLATE);
                        output.accept(ModItems.BLUE_TOPAZ_LEGGINGS);
                        output.accept(ModItems.BLUE_TOPAZ_BOOTS);
                    }))
                    .build());

    public static final Supplier<CreativeModeTab> GOLEM_TAB=CREATIVE_MODE_TAB.register("golem_tab",
            () -> CreativeModeTab.builder().icon(
                            () -> new ItemStack(ModItems.GOLEM_HEAD_BASIC.get()))
                    .title(Component.translatable("creativetab.diabolomod.golem"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(ModItems.GOLEM_HEAD_BASIC);
                        output.accept(ModItems.GOLEM_ARMS_BASIC);
                        output.accept(ModItems.GOLEM_ARMS_BLASTER);
                        output.accept(ModItems.GOLEM_ARMS_MINER);
                        output.accept(ModItems.GOLEM_LEGS_BASIC);
                        output.accept(ModItems.GOLEM_LEGS_WHEELS);
                    }))
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
