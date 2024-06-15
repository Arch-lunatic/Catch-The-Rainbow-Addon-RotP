package com.archlunatic.rotp_ctr.entity.stand.stands;

import com.archlunatic.rotp_ctr.entity.damaging.projectile.CatchTheRainbowRainBladeEntity1;
import com.archlunatic.rotp_ctr.entity.damaging.projectile.CatchTheRainbowRainBladeEntity2;
import com.archlunatic.rotp_ctr.entity.damaging.projectile.CatchTheRainbowRainBladeEntity3;
import com.archlunatic.rotp_ctr.init.InitEffects;
import com.archlunatic.rotp_ctr.init.InitSounds;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static com.archlunatic.rotp_ctr.action.stand.CatchTheRainbowTempHealth.HEALTH_CHANGER_1;

public class CatchTheRainbowEntity extends StandEntity {

    public CatchTheRainbowEntity(StandEntityType<CatchTheRainbowEntity> type, World world) {
        super(type, world);
        unsummonOffset = getDefaultOffsetFromUser().copy();
    }
    private final StandRelativeOffset offsetDefault = StandRelativeOffset.withYOffset(0, 0, 0);

    @Override
    public boolean isPickable(){ return false;}

	public StandRelativeOffset getDefaultOffsetFromUser() {return offsetDefault;}

    @Override
    public float getLeapStrength() {
        return 0;
    }


    private BlockPos prevUserPos;
    private Vector3d prevUserPos1 = new Vector3d(0,0, 0);
    @Override
    public void tick() {//todo перенести в отдельный файл
        super.tick();

        PlayerEntity player = (PlayerEntity) getUser();
        IStandPower userPower = getUserPower();

        if(player != null && userPower != null){
            if (!level.isClientSide()) {
                if (isInRain(player) || player.isCreative()) {
                    if (level.getBlockState(player.blockPosition().below()) == Blocks.AIR.defaultBlockState() && !player.isShiftKeyDown()) {
                        IStandPower.getStandPowerOptional(player).ifPresent(power -> {
                            power.consumeStamina(1F);
                        });
                    }
                    if (isActive()) {
                        AxisAlignedBB area = getBoundingBox().inflate(25);
                        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                                area, entity -> !(entity instanceof StandEntity) && entity != player);
                        for (Entity entity : entities) {
                            if (isInRain(entity)) {
                                SpawnBlade(entity.position(), player);
                            }
                        }
                    }

                    if (isMergeActive()) {
                        player.addEffect(new EffectInstance(InitEffects.RAIN_MERGE.get(), 40, 0, false, false, false));
                        IStandPower.getStandPowerOptional(player).ifPresent(power -> {
                            power.consumeStamina(4F);
                        });
                    }

                    if (player.hasEffect(InitEffects.RAIN_REDEMPTION.get())) {
//                        userPower.getResolveCounter().addResolveValue(userPower.getMaxResolve(), player);
                        if (player.hasEffect(InitEffects.RAIN_MERGE.get())) {
                            setMergeActive(false);
                        }
                    }
                    if (level.getBlockState(player.blockPosition().below()).isAir() && !prevUserPos1.equals(player.position()) && !player.isShiftKeyDown()) {
                        boolean hasMerge = player.hasEffect(InitEffects.RAIN_MERGE.get());
//                        float playerSpeed = player.getSpeed();
                        if (isSound == ((hasMerge) ? 12 : 6)) {
                            player.level.playSound(null, blockPosition(), InitSounds.CATCH_THE_RAINBOW_RAIN_STEP.get(), SoundCategory.PLAYERS, 1, 1);
                            setSound(1);
                        } else {
                            setSound(isSound() + 1);
                        }
                    }
                    prevUserPos1 = player.position();
                }
            }
            if (level.isClientSide()) {
                if (isInRain(player) || player.isCreative()) {
                    if (prevUserPos != null && !player.blockPosition().below().equals(prevUserPos) && level.getBlockState(prevUserPos) == Blocks.BARRIER.defaultBlockState()) {
                        level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);
                        prevUserPos = null;
                    }
                    if (level.getBlockState(player.blockPosition().below()).isAir()) {
                        level.setBlock(player.blockPosition().below(), Blocks.BARRIER.defaultBlockState(), 3);
                        prevUserPos = player.blockPosition().below();
                    }
                    if (player.isShiftKeyDown() && level.getBlockState(player.blockPosition().below()) == Blocks.BARRIER.defaultBlockState()) {
                        level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);
                        prevUserPos = null;
                    }
//                    if (prevUserPos != null && level.getBlockState(player.blockPosition().below()) == Blocks.BARRIER.defaultBlockState()) {
//                        level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);
//                        prevUserPos = null;
//                    }
                    if (level.getBlockState(player.blockPosition().below()) == Blocks.BARRIER.defaultBlockState()) {
                        if (isCount == 3) {
                            rainStep();
                            Count(1);
                        } else {
                            Count(isCount() + 1);
                        }
                    }

                }
                if (!isInRain(player)) {
                    if (!player.isCreative()) {
                        if (level.getBlockState(player.blockPosition().below()) == Blocks.BARRIER.defaultBlockState()) {
                            level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);
                            prevUserPos = null;
                        }
                        if (prevUserPos != null && level.getBlockState(player.blockPosition().below()) != Blocks.BARRIER.defaultBlockState()) {
                            level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);
                            prevUserPos = null;
                        }
                    }
                }
            }
        }
    }

    public int isCountB;
    public int isCountB(){return isCountB;}
    public void CountB(int isCountB) {this.isCountB = isCountB;}

    public void SpawnBlade(Vector3d position, PlayerEntity player) {
        if (!level.isClientSide()) {
            if (isCountB==frequencyOfBlades()) {
                player.isOnGround();
                Random random = new Random(); int randomNumber = random.nextInt(3)+1;
                BlockPos blockPos = new BlockPos(position.x, position.y, position.z);
                if (randomNumber == 1) {
                    CatchTheRainbowRainBladeEntity1 bladeEntity = new CatchTheRainbowRainBladeEntity1(player, level);
                    bladeEntity.setPos(position.x, position.y + 5, position.z);
                    bladeEntity.setDeltaMovement(0.0, -1.0, 0.0);
                    level.addFreshEntity(bladeEntity);
                    if (InitSounds.CATCH_THE_RAINBOW_RAIN_BLADE != null) {
                        level.playSound(null, blockPos, InitSounds.CATCH_THE_RAINBOW_RAIN_BLADE.get(), SoundCategory.PLAYERS, 1, 1);
                    }
                }else if (randomNumber == 2) {
                    CatchTheRainbowRainBladeEntity2 bladeEntity = new CatchTheRainbowRainBladeEntity2(player, level);
                    bladeEntity.setPos(position.x, position.y + 5, position.z);
                    bladeEntity.setDeltaMovement(0.0, -1.0, 0.0);
                    level.addFreshEntity(bladeEntity);
                    if (InitSounds.CATCH_THE_RAINBOW_RAIN_BLADE != null) {
                        level.playSound(null, blockPos, InitSounds.CATCH_THE_RAINBOW_RAIN_BLADE.get(), SoundCategory.PLAYERS, 1, 1);
                    }
                }else {
                    CatchTheRainbowRainBladeEntity3 bladeEntity = new CatchTheRainbowRainBladeEntity3(player, level);
                    bladeEntity.setPos(position.x, position.y + 5, position.z);
                    bladeEntity.setDeltaMovement(0.0, -1.0, 0.0);
                    level.addFreshEntity(bladeEntity);
                    if (InitSounds.CATCH_THE_RAINBOW_RAIN_BLADE != null) {
                        level.playSound(null, blockPos, InitSounds.CATCH_THE_RAINBOW_RAIN_BLADE.get(), SoundCategory.PLAYERS, 1, 1);
                    }
                }
                IStandPower.getStandPowerOptional(player).ifPresent(power -> {power.consumeStamina(20F);});
                CountB(1);
            }else{CountB(isCountB()+1); }
        }
    }

    public int isCount;
    public int isCount(){return isCount;}
    public void Count(int isCount) {this.isCount = isCount;}

    public void rainStep(){
        LivingEntity user = getUser(); assert user != null;
        user.level.addParticle(ParticleTypes.DRIPPING_WATER,
                Math.random() < 0.5 ? user.position().x+Math.random()*0.15 : user.position().x-Math.random()*0.15,
                user.position().y,
                Math.random() < 0.5 ? user.position().z+Math.random()*0.15 : user.position().z-Math.random()*0.15,
                1, 1, 1);

        user.level.addParticle(ParticleTypes.DRIPPING_WATER,
                Math.random() < 0.5 ? user.position().x+Math.random()*0.15 : user.position().x-Math.random()*0.15,
                Math.random() < 0.5 ? user.position().y+Math.random()*0.15 : user.position().y-Math.random()*0.15,
                Math.random() < 0.5 ? user.position().z+Math.random()*0.15 : user.position().z-Math.random()*0.15,
                1, 1, 1);

    }

    public int isSound;
    public int isSound(){return isSound;}
    public void setSound(int isSound) {this.isSound = isSound;}

    private float frequencyOfBlades() {
        if (Objects.requireNonNull(getUserPower()).getStamina()/getUserPower().getMaxStamina() >= 0.75F) return 30;
        if (Objects.requireNonNull(getUserPower()).getStamina()/getUserPower().getMaxStamina() < 0.75F &&
            Objects.requireNonNull(getUserPower()).getStamina()/getUserPower().getMaxStamina() >= 0.5F) return 40;
        else return 50;
    }

//    public boolean isWeatherRainOrThunder() {
//        return !level.isThundering();
//    }

    protected static final DataParameter<Boolean> BLADE_RAIN_ACTIVE = EntityDataManager.defineId(CatchTheRainbowEntity.class, DataSerializers.BOOLEAN);
    public boolean isActive() { return entityData.get(BLADE_RAIN_ACTIVE);}
    public void setActive(boolean isActive) { entityData.set(BLADE_RAIN_ACTIVE, isActive);}
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(BLADE_RAIN_ACTIVE, false);
        entityData.define(PLAYER_MAX_HP, 20F);
        entityData.define(RAIN_MERGE_ACTIVE, false);
    }
    protected static final DataParameter<Float> PLAYER_MAX_HP = EntityDataManager.defineId(CatchTheRainbowEntity.class, DataSerializers.FLOAT);
    public void setMaxHP(float maxHp) { entityData.set(PLAYER_MAX_HP, maxHp);}

    protected static final DataParameter<Boolean> RAIN_MERGE_ACTIVE = EntityDataManager.defineId(CatchTheRainbowEntity.class, DataSerializers.BOOLEAN);
    public boolean isMergeActive() { return entityData.get(RAIN_MERGE_ACTIVE);}
    public void setMergeActive(boolean isMergeActive) { entityData.set(RAIN_MERGE_ACTIVE, isMergeActive);}

    public static boolean isInRain(Entity entity) {
        if (entity != null){
            BlockPos blockPos = entity.blockPosition();
            BlockPos blockPosNorth = entity.blockPosition().north();
            BlockPos blockPosSouth = entity.blockPosition().south();
            BlockPos blockPosEast = entity.blockPosition().east();
            BlockPos blockPosWest = entity.blockPosition().west();
            return entity.level.isRainingAt(blockPos) || entity.level.isRainingAt(new BlockPos(blockPos.getX(), entity.getBoundingBox().maxY, blockPos.getZ()))
                    || entity.level.isRainingAt(blockPosNorth) || entity.level.isRainingAt(blockPosSouth)
                    || entity.level.isRainingAt(blockPosEast) || entity.level.isRainingAt(blockPosWest);
        }else {
            return false;
        }
    }//todo в gamerule

    private static final AttributeModifier REACH_DISTANCE_BUFF = new AttributeModifier(
            UUID.fromString("c137f815-2f58-4fc7-ad4d-a573249f90e0"), "Reach distance buff", 3, AttributeModifier.Operation.MULTIPLY_BASE);

//    private static final AttributeModifier ATTACK_DISTANCE_BUFF = new AttributeModifier(
//            UUID.fromString("0fa53f0f-7ea0-42a3-98da-40f06e523caf"), "Attack distance buff", 3, AttributeModifier.Operation.MULTIPLY_BASE);

    @Override
    public void setUserAndPower(LivingEntity user, IStandPower power) {
        super.setUserAndPower(user, power);

        if (!level.isClientSide() && isInRain(user)) {
            ModifiableAttributeInstance attributeInstance1 = user.getAttribute(ForgeMod.REACH_DISTANCE.get());
//            ModifiableAttributeInstance attributeInstance2 = user.getAttribute(ForgeMod.?.get());
            if (attributeInstance1 != null) { attributeInstance1.addTransientModifier(REACH_DISTANCE_BUFF);}
//            if (attributeInstance2 != null){ attributeInstance2.addTransientModifier(ATTACK_DISTANCE_BUFF);}
        }
    }

    @Override
    public void onRemovedFromWorld() {
        super.onRemovedFromWorld();

        if (!level.isClientSide()) {
            LivingEntity user = getUser();
            if (user != null) {
                ModifiableAttributeInstance attributeInstance1 = user.getAttribute(ForgeMod.REACH_DISTANCE.get());
//                ModifiableAttributeInstance attributeInstance2 = user.getAttribute(ForgeMod.?.get());
                if (attributeInstance1 != null) { attributeInstance1.removeModifier(REACH_DISTANCE_BUFF);}
//                if (attributeInstance2 != null){ attributeInstance2.addTransientModifier(ATTACK_DISTANCE_BUFF);}
            }
        }
        if (prevUserPos != null) { level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);}
        setMergeActive(false);
        Objects.requireNonNull(getUser()).removeEffect(Effects.ABSORPTION);
        getUser().getAttribute(Attributes.MAX_HEALTH).removeModifier(HEALTH_CHANGER_1);
    }
}