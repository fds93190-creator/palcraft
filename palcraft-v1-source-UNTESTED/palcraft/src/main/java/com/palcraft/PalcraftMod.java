package com.palcraft;

import net.fabricmc.api.ModInitializer;

public class PalcraftMod implements ModInitializer {
    public static final String MOD_ID = "palcraft";

    @Override
    public void onInitialize() {
        ModEntities.register();
        ModItems.register();
    }
}
