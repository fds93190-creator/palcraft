package com.palcraft;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

/** Hook: pal_model. One simple blocky quadruped shared by every Pal; each Pal has its own texture. */
public class PalModel extends EntityModel<PalEntity> {
    private final ModelPart root;
    private final ModelPart head, legFR, legFL, legBR, legBL;

    public PalModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.legFR = root.getChild("leg_fr");
        this.legFL = root.getChild("leg_fl");
        this.legBR = root.getChild("leg_br");
        this.legBL = root.getChild("leg_bl");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData r = data.getRoot();
        r.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4f, -4f, -5f, 8f, 8f, 10f), ModelTransform.pivot(0f, 16f, 0f));
        r.addChild("head", ModelPartBuilder.create().uv(0, 18).cuboid(-3f, -3f, -5f, 6f, 6f, 5f), ModelTransform.pivot(0f, 14f, -5f));
        ModelPartBuilder leg = ModelPartBuilder.create().uv(28, 18).cuboid(-1f, 0f, -1f, 2f, 4f, 2f);
        r.addChild("leg_fr", leg, ModelTransform.pivot(-2.5f, 20f, -3.5f));
        r.addChild("leg_fl", leg, ModelTransform.pivot(2.5f, 20f, -3.5f));
        r.addChild("leg_br", leg, ModelTransform.pivot(-2.5f, 20f, 3.5f));
        r.addChild("leg_bl", leg, ModelTransform.pivot(2.5f, 20f, 3.5f));
        return TexturedModelData.of(data, 64, 32);
    }

    @Override
    public void setAngles(PalEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        head.yaw = headYaw * ((float) Math.PI / 180f);
        head.pitch = headPitch * ((float) Math.PI / 180f);
        float swing = MathHelper.cos(limbAngle * 0.6662f) * 1.2f * limbDistance;
        legFR.pitch = swing;
        legBL.pitch = swing;
        legFL.pitch = -swing;
        legBR.pitch = -swing;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        root.render(matrices, vertices, light, overlay, color);
    }
}
