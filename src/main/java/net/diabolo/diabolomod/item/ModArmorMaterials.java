package net.diabolo.diabolomod.item;

import net.diabolo.diabolomod.DiaboloMod;
import net.diabolo.diabolomod.util.ModTags;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.EnumMap;

public class ModArmorMaterials {
    static ResourceKey<? extends Registry<EquipmentAsset>> ROOT_ID = ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));
    public static ResourceKey<EquipmentAsset> TOPAZ = ResourceKey.create(ROOT_ID, Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "topaz"));
    public static ResourceKey<EquipmentAsset> BLUE_TOPAZ = ResourceKey.create(ROOT_ID, Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "blue_topaz"));

    public static final ArmorMaterial TOPAZ_ARMOR_MATERIAL = new ArmorMaterial(1500,
            Util.make(new EnumMap<>(ArmorType.class), attribute -> {
                attribute.put(ArmorType.BOOTS, 4);
                attribute.put(ArmorType.LEGGINGS, 6);
                attribute.put(ArmorType.CHESTPLATE, 8);
                attribute.put(ArmorType.HELMET, 4);
                attribute.put(ArmorType.BODY, 11);
            }), 16, SoundEvents.ARMOR_EQUIP_IRON,
            2f, 0.1f, ModTags.Items.TOPAZ_REPAIRABLE, TOPAZ);

    public static final ArmorMaterial BLUE_TOPAZ_ARMOR_MATERIAL = new ArmorMaterial(2500,
            Util.make(new EnumMap<>(ArmorType.class), attribute -> {
                attribute.put(ArmorType.BOOTS, 5);
                attribute.put(ArmorType.LEGGINGS, 7);
                attribute.put(ArmorType.CHESTPLATE, 9);
                attribute.put(ArmorType.HELMET, 5);
                attribute.put(ArmorType.BODY, 20);
            }), 25, SoundEvents.ARMOR_EQUIP_DIAMOND,
            4.0f, 0.2f, ModTags.Items.BLUE_TOPAZ_REPAIRABLE, BLUE_TOPAZ);
}