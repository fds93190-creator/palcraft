package com.palcraft;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/** Hook: items. The Pal Sphere plus one spawn egg per Pal (for testing). */
public final class ModItems {
    public static Item PAL_SPHERE;

    private ModItems() {}

    public static void register() {
        PAL_SPHERE = Registry.register(Registries.ITEM, Identifier.of(PalcraftMod.MOD_ID, "pal_sphere"),
                new PalSphereItem(new Item.Settings().maxCount(GeneratedSheets.SPHERE_MAX_STACK)));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(e -> e.add(PAL_SPHERE));

        for (PalDef d : GeneratedSheets.PALS) {
            Item egg = Registry.register(Registries.ITEM, Identifier.of(PalcraftMod.MOD_ID, d.id() + "_spawn_egg"),
                    new SpawnEggItem(ModEntities.PALS.get(d.id()), d.eggPrimary(), d.eggSecondary(), new Item.Settings()));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(e -> e.add(egg));
        }
    }
}
