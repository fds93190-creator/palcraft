package com.palcraft;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/** Hook: sphere_catch. The thrown Pal Sphere; decides whether a Pal is caught. */
public class PalSphereEntity extends ThrownItemEntity {
    public PalSphereEntity(EntityType<? extends PalSphereEntity> type, World world) {
        super(type, world);
    }

    public PalSphereEntity(World world, LivingEntity owner) {
        super(ModEntities.SPHERE, owner, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.PAL_SPHERE;
    }

    @Override
    protected void onEntityHit(EntityHitResult hit) {
        super.onEntityHit(hit);
        if (!(getWorld() instanceof ServerWorld world)) return;

        if (hit.getEntity() instanceof PalEntity pal && !pal.isTamed() && getOwner() instanceof PlayerEntity player) {
            float ratio = pal.getHealth() / pal.getMaxHealth();
            double chance = GeneratedSheets.SPHERE_BASE_CATCH * (1.0 - pal.def().catchDifficulty()) * (2.0 - ratio);
            chance = MathHelper.clamp(chance, 0.05, 0.95);
            if (world.getRandom().nextDouble() < chance) {
                pal.setTamed(true, true);
                pal.setOwner(player);
                pal.setHealth(pal.getMaxHealth());
                world.spawnParticles(ParticleTypes.HAPPY_VILLAGER, pal.getX(), pal.getBodyY(0.5), pal.getZ(), 12, 0.3, 0.3, 0.3, 0.0);
                player.sendMessage(Text.literal(pal.def().name() + " joined you!"), true);
            } else {
                world.spawnParticles(ParticleTypes.SMOKE, pal.getX(), pal.getBodyY(0.5), pal.getZ(), 8, 0.3, 0.3, 0.3, 0.0);
                player.sendMessage(Text.literal(pal.def().name() + " broke free!"), true);
                dropStack(new ItemStack(ModItems.PAL_SPHERE));
            }
        } else {
            dropStack(new ItemStack(ModItems.PAL_SPHERE));
        }
        discard();
    }

    @Override
    protected void onBlockHit(BlockHitResult hit) {
        super.onBlockHit(hit);
        if (!getWorld().isClient) {
            dropStack(new ItemStack(ModItems.PAL_SPHERE));
            discard();
        }
    }
}
