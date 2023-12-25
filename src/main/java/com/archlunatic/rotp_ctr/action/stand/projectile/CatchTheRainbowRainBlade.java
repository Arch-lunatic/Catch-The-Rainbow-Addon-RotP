package com.archlunatic.rotp_ctr.action.stand.projectile;

import com.archlunatic.rotp_ctr.entity.damaging.projectile.CtRainBladeEntity;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.sound.ClientTickingSoundsHelper;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class CatchTheRainbowRainBlade extends StandEntityAction {
    //public static final StandPose BLOOD_CUTTER_SHOT_POSE = new StandPose("CD_BLOOD_CUTTER");

    public CatchTheRainbowRainBlade(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        if (!isInRain(user)) {
            return conditionMessage("needs_rain");
        }
        return super.checkSpecificConditions(user, power, target);
    }

    private boolean isInRain(Entity entity) {
        BlockPos blockPos = entity.blockPosition();
        return entity.level.isRainingAt(blockPos) || entity.level.isRainingAt(new BlockPos(blockPos.getX(), entity.getBoundingBox().maxY, blockPos.getZ()));
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            LivingEntity user = userPower.getUser();
            CtRainBladeEntity cutter = new CtRainBladeEntity(user, world);
            cutter.setShootingPosOf(user);
            cutter.shootFromRotation(user, 2.0F, standEntity.getProjectileInaccuracy((float) (standEntity.getAttackSpeed())/9.0F));
            standEntity.addProjectile(cutter);
        }
    }

    @Override
    protected int getCooldownAdditional(IStandPower power, int ticksHeld) {
        int cooldown = super.getCooldownAdditional(power, ticksHeld);
        if (!power.isUserCreative() && power.getUser() != null) {
            cooldown = MathHelper.ceil((float) cooldown * 2);
        }
        return cooldown;
    }

//    @Override
//    protected void playSoundAtStand(World world, StandEntity standEntity, SoundEvent sound, IStandPower standPower, Phase phase) {
//        if (world.isClientSide() && phase == Phase.WINDUP && sound == ModSounds.CRAZY_DIAMOND_FIX_STARTED.get()) {
//            ClientTickingSoundsHelper.playStandEntityCancelableActionSound(standEntity, sound, this, phase, 1.0F, 1.0F, false);
//        }
//        else {
//            super.playSoundAtStand(world, standEntity, sound, standPower, phase);
//        }
//    }
}
