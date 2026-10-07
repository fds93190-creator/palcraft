package com.palcraft;

/** One row of sheets/spawns.json. */
public record SpawnRow(String palId, String biomeNamespace, String biomePath, int weight, int min, int max) {}
