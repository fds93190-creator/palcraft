package com.palcraft;

/** One row of sheets/pals.json. */
public record PalDef(String id, String name, float maxHealth, float attackDamage, double speed,
                     float width, float height, float modelScale, float catchDifficulty,
                     boolean fireImmune, String onHitEffect, int onHitSeconds,
                     int bodyColor, int accentColor, int eggPrimary, int eggSecondary) {}
