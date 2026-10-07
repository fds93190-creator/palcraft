# Palcraft (working title) — v1 source, UNTESTED

Fabric mod for Minecraft: Java Edition 1.21.1. Pal-style creatures spawn by biome; you craft a **Pal Sphere**,
throw it (right click) and a caught Pal follows and fights for you like a tamed wolf. Solo only in v1.

## STATUS — read this first
- **Never compiled, never run.** It was written without access to Minecraft, Gradle or the Fabric repositories.
  Method names follow Yarn 1.21.1 from memory; expect a few compile errors to fix on the first build.
- **Palworld is NOT used.** Creatures are original blocky models with placeholder textures. Nothing is read from
  a Palworld install. As it stands this is a Minecraft-only mod, so it would not qualify on Melty as a
  Palworld + Minecraft mashup. It has NOT been uploaded or published anywhere.
- Fabric loader/API/Yarn versions in gradle.properties are unverified: check https://fabricmc.net/develop/.
- Melty installs no Minecraft loader by itself, so a Melty release would have to bundle Fabric (license permitting).

## Design = sheets (source of truth)
`sheets/pals.json`, `items.json`, `spawns.json`, `hooks.json`. Change a sheet first, then regenerate:

    python3 tools/make_textures.py   # placeholder textures from pals.json
    python3 tools/gen.py preflight   # unfilled cells, unresolved references, unverified hooks
    python3 tools/gen.py generate    # writes GeneratedSheets.java, lang, recipe, item models

Preflight status at last run: 0 errors, 24 unverified (every hook, vanilla item id and biome id still has to be
checked in the real game; hooks.json `verified` stays "no" until then).

## Build and test (needs a PC with Java 21 and Gradle)
    gradle build        # jar in build/libs/
Put the jar and Fabric API in `.minecraft/mods` with Fabric Loader 0.16+ for 1.21.1, then in a creative world:
spawn eggs are in the Spawn Eggs tab, the Pal Sphere in Tools & Utilities.
Checklist: Pals spawn in their biomes · sphere throws · catch succeeds/fails · caught Pal follows and attacks ·
Foxparks ignites, Pengullet slows · no crash on world reload (tamed Pals saved).

## Play mode
Solo play only. Nothing in this release hosts, joins or shares a game with other players.
Later ideas (not in this release): Pal storage, Pal jobs, real Palworld art.
