package com.palcraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

/** Hook: pal_ai. A wolf-like tameable creature; one class for every row of pals.json. */
public class PalEntity extends TameableEntity {
    public PalEntity(EntityType<? extends PalEntity> type, World world) {
        super(type, world);
    }

    public PalDef def() {
        return GeneratedSheets.PAL_BY_ID.get(Registries.ENTITY_TYPE.getId(getType()).getPath());
    }

    public static DefaultAttributeContainer.Builder createPalAttributes(PalDef d) {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, d.maxHealth())
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, d.speed())
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, d.attackDamage());
    }

    @Override
    protected void initGoals() {
        goalSelector.add(1, new SwimGoal(this));
        goalSelector.add(2, new MeleeAttackGoal(this, 1.1, true));
        goalSelector.add(3, new FollowOwnerGoal(this, 1.1, 8.0f, 2.0f));
        goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        goalSelector.add(7, new LookAroundGoal(this));
        targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        targetSelector.add(2, new AttackWithOwnerGoal(this));
        targetSelector.add(3, new RevengeGoal(this));
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean hit = super.tryAttack(target);
        if (hit && target instanceof LivingEntity living && !getWorld().isClient) {
            PalDef d = def();
            switch (d.onHitEffect()) {
                case "none" -> {}
                case "ignite" -> living.setOnFireFor(d.onHitSeconds());
                case "minecraft:slowness" -> living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, d.onHitSeconds() * 20));
                case "minecraft:poison" -> living.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, d.onHitSeconds() * 20));
                case "minecraft:weakness" -> living.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, d.onHitSeconds() * 20));
                default -> {}
            }
        }
        return hit;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null; // Pals do not breed in v1.
    }
}
