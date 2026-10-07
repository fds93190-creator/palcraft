package com.palcraft;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/** Hook: sphere_throw. Right click throws a Pal Sphere. */
public class PalSphereItem extends Item {
    public PalSphereItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW,
                SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!world.isClient) {
            PalSphereEntity sphere = new PalSphereEntity(world, user);
            sphere.setItem(stack);
            sphere.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, GeneratedSheets.SPHERE_THROW_SPEED, 1.0f);
            world.spawnEntity(sphere);
        }
        stack.decrementUnlessCreative(1, user);
        return TypedActionResult.success(stack, world.isClient());
    }
}
