package com.archlunatic.rotp_ctr.util.addon;

import com.github.standobyte.jojo.JojoModConfig;
import com.github.standobyte.jojo.capability.entity.PlayerUtilCap;
import com.github.standobyte.jojo.capability.entity.PlayerUtilCapProvider;
import com.github.standobyte.jojo.client.InputHandler;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.item.ClothesSet;
import com.github.standobyte.jojo.network.PacketManager;
import com.github.standobyte.jojo.network.packets.fromserver.PlayVoiceLinePacket;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.NonStandPowerType;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import com.github.standobyte.jojo.util.mc.MCUtil;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.INPC;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;

public class JojoAddonUtil {
    public JojoAddonUtil() {
    }

    public static RayTraceResult rayTrace(Entity entity, double reachDistance, @Nullable Predicate<Entity> entityFilter) {
        return rayTrace(entity, reachDistance, entityFilter, 0.0);
    }

    public static RayTraceResult rayTrace(Entity entity, double reachDistance, @Nullable Predicate<Entity> entityFilter, double rayTraceInflate) {
        return rayTrace(entity, reachDistance, entityFilter, rayTraceInflate, 0.0);
    }

    public static RayTraceResult rayTrace(Entity entity, double reachDistance, @Nullable Predicate<Entity> entityFilter, double rayTraceInflate, double standPrecision) {
        return rayTraceMultipleEntities(entity, reachDistance, entityFilter, rayTraceInflate, standPrecision)[0];
    }

    public static RayTraceResult[] rayTraceMultipleEntities(Entity entity, double reachDistance, @Nullable Predicate<Entity> entityFilter, double rayTraceInflate, double standPrecision) {
        return rayTraceMultipleEntities(entity.getEyePosition(1.0F), entity.getViewVector(1.0F), reachDistance, entity.level, entity, entityFilter, rayTraceInflate, standPrecision);
    }

    public static RayTraceResult[] rayTraceMultipleEntities(Vector3d startPos, Vector3d rayVec, double distance, World world, @Nullable Entity entity, @Nullable Predicate<Entity> entityFilter, double rayTraceInflate, double standPrecision) {
        Vector3d rtVec = rayVec.normalize().scale(distance);
        Vector3d endPos = startPos.add(rtVec);
        AxisAlignedBB aabb = entity.getBoundingBox().expandTowards(rtVec).inflate(1.0);
        return rayTraceMultipleEntities(startPos, endPos, aabb, distance, entity.level, entity, entityFilter, rayTraceInflate, standPrecision);
    }

    public static RayTraceResult rayTrace(Vector3d startPos, Vector3d rayVec, double distance, World world, @Nullable Entity entity, @Nullable Predicate<Entity> entityFilter, double rayTraceInflate, double standPrecision) {
        return rayTraceMultipleEntities(startPos, rayVec, distance, world, entity, entityFilter, rayTraceInflate, standPrecision)[0];
    }

    public static RayTraceResult[] rayTraceMultipleEntities(Vector3d startPos, Vector3d endPos, AxisAlignedBB aabb, double minDistance, World world, @Nullable Entity entity, @Nullable Predicate<Entity> entityFilter, double rayTraceInflate, double standPrecision) {
        aabb.inflate(rayTraceInflate);
        double minDistanceSqr = minDistance * minDistance;
        Map<EntityRayTraceResult, Double> rayTracedWithDistance = new HashMap();
        Iterator var15 = world.getEntities(entity, aabb, (e) -> {
            return !e.isSpectator() && e.isPickable() && (entityFilter == null || entityFilter.test(e));
        }).iterator();

        while(true) {
            while(var15.hasNext()) {
                Entity potentialTarget = (Entity)var15.next();
                AxisAlignedBB targetCollisionAABB = potentialTarget.getBoundingBox().inflate((double)potentialTarget.getPickRadius() + rayTraceInflate);
                targetCollisionAABB = standPrecisionTargetHitbox(targetCollisionAABB, standPrecision);
                Optional<Vector3d> clipOptional = targetCollisionAABB.clip(startPos, endPos);
                if (targetCollisionAABB.contains(startPos)) {
                    if (minDistanceSqr >= 0.0) {
                        rayTracedWithDistance.put(new EntityRayTraceResult(potentialTarget, (Vector3d)clipOptional.orElse(startPos)), 0.0);
                    }
                } else if (clipOptional.isPresent()) {
                    Vector3d clipVec = (Vector3d)clipOptional.get();
                    double clipDistanceSqr = startPos.distanceToSqr(clipVec);
                    if (clipDistanceSqr < minDistanceSqr || minDistanceSqr == 0.0) {
                        if (entity != null && potentialTarget.getRootVehicle() == entity.getRootVehicle() && !potentialTarget.canRiderInteract()) {
                            if (minDistanceSqr == 0.0) {
                                rayTracedWithDistance.put(new EntityRayTraceResult(potentialTarget, clipVec), 0.0);
                            }
                        } else {
                            rayTracedWithDistance.put(new EntityRayTraceResult(potentialTarget, clipVec), clipDistanceSqr);
                        }
                    }
                }
            }

            if (rayTracedWithDistance.isEmpty()) {
                return new RayTraceResult[]{world.clip(new RayTraceContext(startPos, endPos, BlockMode.OUTLINE, FluidMode.NONE, entity))};
            }

            return (RayTraceResult[])rayTracedWithDistance.entrySet().stream().sorted(Comparator.comparingDouble(Map.Entry::getValue)).map(Map.Entry::getKey).toArray((x$0) -> {
                return new EntityRayTraceResult[x$0];
            });
        }
    }

    private static AxisAlignedBB standPrecisionTargetHitbox(AxisAlignedBB aabb, double precision) {
        if (precision > 0.0) {
            double smallAabbAddFraction = Math.min(precision, 16.0) / 16.0;
            aabb.inflate(Math.max(1.0 - aabb.getXsize(), 0.0) * smallAabbAddFraction, Math.max(2.5 - aabb.getYsize(), 0.0) * smallAabbAddFraction, Math.max(1.0 - aabb.getZsize(), 0.0) * smallAabbAddFraction);
            aabb = aabb.inflate(precision / 20.0);
        }

        return aabb;
    }

    public static RayTraceResult getHitResult(Entity projectile, @Nullable Predicate<Entity> targetPredicate, RayTraceContext.BlockMode blockMode) {
        World world = projectile.level;
        Vector3d pos = projectile.position();
        Vector3d nextPos = pos.add(projectile.getDeltaMovement());
        RayTraceResult rayTraceResult = world.clip(new RayTraceContext(pos, nextPos, blockMode, FluidMode.NONE, projectile));
        if (((RayTraceResult)rayTraceResult).getType() != Type.MISS) {
            nextPos = ((RayTraceResult)rayTraceResult).getLocation();
        }

        RayTraceResult entityRayTraceResult = ProjectileHelper.getEntityHitResult(world, projectile, pos, nextPos, projectile.getBoundingBox().expandTowards(projectile.getDeltaMovement()).inflate(1.0), targetPredicate);
        if (entityRayTraceResult != null) {
            rayTraceResult = entityRayTraceResult;
        }

        return (RayTraceResult)rayTraceResult;
    }

    protected static BlockRayTraceResult getPlayerPOVHitResult(World world, LivingEntity entity, RayTraceContext.FluidMode fluidMode) {
        float xRot = entity.xRot;
        float yRot = entity.yRot;
        Vector3d eyePos = entity.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-yRot * 0.017453292F - 3.1415927F);
        float f3 = MathHelper.sin(-yRot * 0.017453292F - 3.1415927F);
        float f4 = -MathHelper.cos(-xRot * 0.017453292F);
        float f5 = MathHelper.sin(-xRot * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double distance = (Double)Optional.ofNullable(entity.getAttribute((Attribute)ForgeMod.REACH_DISTANCE.get())).map(ModifiableAttributeInstance::getValue).orElse(5.0);
        Vector3d vector3d1 = eyePos.add((double)f6 * distance, (double)f5 * distance, (double)f7 * distance);
        return world.clip(new RayTraceContext(eyePos, vector3d1, BlockMode.OUTLINE, fluidMode, entity));
    }

    public static boolean isAnotherEntityTargeted(RayTraceResult rayTraceResult, Entity targettingEntity) {
        return rayTraceResult.getType() == Type.ENTITY && !((EntityRayTraceResult)rayTraceResult).getEntity().is(targettingEntity);
    }

    public static double getDistance(Entity entity, AxisAlignedBB targetAabb) {
        Vector3d startPos = entity.getEyePosition(1.0F);
        if (targetAabb.contains(startPos)) {
            return 0.0;
        } else {
            Vector3d endPos = new Vector3d(MathHelper.lerp(0.5, targetAabb.minX, targetAabb.maxX), MathHelper.lerp(entity.getBbHeight() == 0.0F ? 0.0 : (double)(entity.getEyeHeight() / entity.getBbHeight()), targetAabb.minY, targetAabb.maxY), MathHelper.lerp(0.5, targetAabb.minZ, targetAabb.maxZ));
            Optional<Vector3d> clipOptional = targetAabb.clip(startPos, endPos);
            return (Double)clipOptional.map((clipVec) -> {
                return startPos.distanceTo(clipVec) - (double)(entity.getBbWidth() / 2.0F);
            }).orElse(-1.0);
        }
    }

    public static boolean canEntityDestroy(ServerWorld world, BlockPos blockPos, BlockState blockState, LivingEntity entity) {
        if ((Boolean)JojoModConfig.getCommonConfigInstance(world.isClientSide()).abilitiesBreakBlocks.get() && blockState.canEntityDestroy(world, blockPos, entity) && ForgeEventFactory.onEntityDestroyBlock(entity, blockPos, blockState)) {
            PlayerEntity player = null;
            if (entity instanceof PlayerEntity) {
                player = (PlayerEntity)entity;
            } else if (entity instanceof StandEntity) {
                LivingEntity standUser = ((StandEntity)entity).getUser();
                if (standUser instanceof PlayerEntity) {
                    player = (PlayerEntity)standUser;
                }
            }

            return player == null || world.mayInteract(player, blockPos);
        } else {
            return false;
        }
    }

    public static ResourceLocation makeTextureLocation(String folderName, String namespace, String path) {
        return makeTextureLocation(folderName, namespace, path, true);
    }

    public static ResourceLocation makeTextureLocation(String folderName, String namespace, String path, boolean addPngExtension) {
        return new ResourceLocation(namespace, "textures/" + folderName + "/" + path + (addPngExtension ? ".png" : ""));
    }

    public static boolean playerHasClientInput(PlayerEntity player) {
        return player.level.isClientSide() ? InputHandler.getInstance().hasInput : (Boolean)player.getCapability(PlayerUtilCapProvider.CAPABILITY).map(PlayerUtilCap::hasClientInput).orElse(false);
    }

    public static boolean isUndead(LivingEntity entity) {
        if (entity.getMobType() == CreatureAttribute.UNDEAD) {
            return true;
        } else {
            return entity instanceof PlayerEntity ? isPlayerUndead((PlayerEntity)entity) : false;
        }
    }

    public static boolean isPlayerUndead(PlayerEntity player) {
        return (Boolean)INonStandPower.getNonStandPowerOptional(player).map((power) -> {
            NonStandPowerType<?> powerType = (NonStandPowerType)power.getType();
            return powerType == ModPowers.VAMPIRISM.get();
        }).orElse(false);
    }

    public static boolean canBleed(LivingEntity entity) {
        return entity.getMobType() != CreatureAttribute.UNDEAD && (entity instanceof PlayerEntity || entity instanceof AgeableEntity || entity instanceof INPC || entity instanceof AbstractIllagerEntity || entity instanceof WaterMobEntity);
    }

    public static void extinguishFieryStandEntity(Entity entity, ServerWorld world) {
        MCUtil.playSound(world, (PlayerEntity)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.FIRE_EXTINGUISH, entity.getSoundSource(), 1.0F, 1.0F, StandUtil::playerCanHearStands);
        world.sendParticles(ParticleTypes.LARGE_SMOKE, entity.getX(), entity.getY(), entity.getZ(), 8, (double)(entity.getBbWidth() / 2.0F), (double)(entity.getBbHeight() / 2.0F), (double)(entity.getBbWidth() / 2.0F), 0.0);
        entity.remove();
    }

    public static void deflectProjectile(Entity projectile, @Nullable Vector3d deflectVec) {
        projectile.setDeltaMovement(deflectVec != null ? deflectVec.scale(Math.sqrt(projectile.getDeltaMovement().lengthSqr() / deflectVec.lengthSqr())) : projectile.getDeltaMovement().reverse());
        projectile.move(MoverType.SELF, projectile.getDeltaMovement());
        if (projectile instanceof ModdedProjectileEntity) {
            ((ModdedProjectileEntity)projectile).setIsDeflected();
        }

    }

    public static boolean isTargetBlocking(LivingEntity target) {
        ItemStack usedItem = target.getUseItem();
        if (!usedItem.isEmpty() && usedItem.isShield(target)) {
            return true;
        } else {
            if (target instanceof StandEntity) {
                StandEntity stand = (StandEntity)target;
                if (stand.isBlocking()) {
                    return true;
                }
            }

            return false;
        }
    }

    public static void sayVoiceLine(LivingEntity entity, SoundEvent voiceLine) {
        sayVoiceLine(entity, voiceLine, (ClothesSet)null);
    }

    public static void sayVoiceLine(LivingEntity entity, SoundEvent voiceLine, @Nullable ClothesSet character) {
        sayVoiceLine(entity, voiceLine, character, 1.0F, 1.0F, false);
    }

    public static void sayVoiceLine(LivingEntity entity, SoundEvent voiceLine, @Nullable ClothesSet character, boolean interrupt) {
        sayVoiceLine(entity, voiceLine, character, 1.0F, 1.0F, interrupt);
    }

    public static void sayVoiceLine(LivingEntity entity, SoundEvent sound, @Nullable ClothesSet character, float volume, float pitch, boolean interrupt) {
        sayVoiceLine(entity, sound, character, volume, pitch, 0, interrupt);
    }

    public static void sayVoiceLine(LivingEntity entity, SoundEvent sound, @Nullable ClothesSet character, float volume, float pitch, int voiceLineDelay, boolean interrupt) {
        if (!entity.level.isClientSide() && !entity.hasEffect(Effects.INVISIBILITY) && (character == null || character == ClothesSet.getClothesSet(entity))) {
            SoundCategory category = SoundCategory.VOICE;
            if (entity instanceof PlayerEntity) {
                PlayVoiceLinePacket packet;
                if (!canPlayVoiceLine((PlayerEntity)entity, sound, voiceLineDelay)) {
                    packet = PlayVoiceLinePacket.notTriggered(entity.getId());
                } else {
                    PlaySoundAtEntityEvent event = ForgeEventFactory.onPlaySoundAtEntity((Entity)null, sound, category, volume, pitch);
                    if (!event.isCanceled() && event.getSound() != null) {
                        sound = event.getSound();
                        category = event.getCategory();
                        volume = event.getVolume();
                        packet = new PlayVoiceLinePacket(sound, category, entity.getId(), volume, pitch, interrupt);
                    } else {
                        packet = PlayVoiceLinePacket.notTriggered(entity.getId());
                    }
                }
                PacketManager.sendToNearby(packet, (ServerPlayerEntity)null, entity.getX(), entity.getY(), entity.getZ(), volume > 1.0F ? (double)(16.0F * volume) : 16.0, entity.level.dimension());
            } else {
                entity.level.playSound((PlayerEntity)null, entity, sound, category, volume, pitch);
            }

        }
    }

    private static boolean canPlayVoiceLine(PlayerEntity entity, SoundEvent voiceLine, int voiceLineDelay) {
        return (Boolean)entity.getCapability(PlayerUtilCapProvider.CAPABILITY).map((cap) -> {
            return cap.checkNotRepeatingVoiceLine(voiceLine, voiceLineDelay);
        }).orElse(true);
    }

    /** @deprecated */
    @Deprecated
    public static boolean useShiftVar(LivingEntity user) {
        return user.isShiftKeyDown();
    }

    public static enum Direction2D {
        UP,
        RIGHT,
        DOWN,
        LEFT;

        private Direction2D() {
        }
    }
}

