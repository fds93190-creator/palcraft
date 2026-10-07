package com.palcraft;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class PalRenderer extends MobEntityRenderer<PalEntity, PalModel> {
    public PalRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PalModel(ctx.getPart(PalcraftClient.PAL_LAYER)), 0.35f);
    }

    @Override
    public Identifier getTexture(PalEntity entity) {
        return Identifier.of(PalcraftMod.MOD_ID, "textures/entity/" + entity.def().id() + ".png");
    }

    @Override
    protected void scale(PalEntity entity, MatrixStack matrices, float amount) {
        float s = entity.def().modelScale();
        matrices.scale(s, s, s);
    }
}
