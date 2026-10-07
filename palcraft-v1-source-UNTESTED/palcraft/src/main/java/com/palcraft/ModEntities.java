package com.palcraft;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

import java.util.LinkedHashMap;
import java.util.Map;

/** Hooks: entity_types, pal_attributes, biome_spawns, spawn_rules. All rows come from GeneratedSheets. */
public final class ModEntities {
    public static final Map<String, EntityType<PalEntity>> PALS = new LinkedHashMap<>();
    public static EntityType<PalSphereEntity> SPHERE;

    private ModEntities() {}

    public static void register() {
        for (PalDef d : GeneratedSheets.PALS) {
            EntityType.Builder<PalEntity> b = EntityType.Builder.create(PalEntity::new, SpawnGroup.CREATURE)
                    .dimensions(d.width(), d.height());
            if (d.fireImmune()) b.makeFireImmune();
            EntityType<PalEntity> type = Registry.register(Registries.ENTITY_TYPE,
                    Identifier.of(PalcraftMod.MOD_ID, d.id()), b.build(d.id()));
            PALS.put(d.id(), type);
            FabricDefaultAttributeRegistry.register(type, PalEntity.createPalAttributes(d));
            SpawnRestriction.register(type, SpawnLocationTypes.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    (t, world, reason, pos, random) ->
                            world.getBlockState(pos.down()).isOpaque() && world.getBaseLightLevel(pos, 0) > 8);
        }

        SPHERE = Registry.register(Registries.ENTITY_TYPE, Identifier.of(PalcraftMod.MOD_ID, "pal_sphere"),
                EntityType.Builder.<PalSphereEntity>create(PalSphereEntity::new, SpawnGroup.MISC)
                        .dimensions(0.25f, 0.25f).maxTrackingRange(4).trackingTickInterval(10)
                        .build("pal_sphere"));

        for (SpawnRow s : GeneratedSheets.SPAWNS) {
            RegistryKey<Biome> key = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(s.biomeNamespace(), s.biomePath()));
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(key), SpawnGroup.CREATURE,
                    PALS.get(s.palId()), s.weight(), s.min(), s.max());
        }
    }
}
