package com.palcraft;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

/** Hooks: pal_renderer (and registers pal_model's layer). */
@Environment(EnvType.CLIENT)
public class PalcraftClient implements ClientModInitializer {
    public static final EntityModelLayer PAL_LAYER = new EntityModelLayer(Identifier.of(PalcraftMod.MOD_ID, "pal"), "main");

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(PAL_LAYER, PalModel::getTexturedModelData);
        for (var type : ModEntities.PALS.values()) {
            EntityRendererRegistry.register(type, PalRenderer::new);
        }
        EntityRendererRegistry.register(ModEntities.SPHERE, FlyingItemEntityRenderer::new);
    }
}
