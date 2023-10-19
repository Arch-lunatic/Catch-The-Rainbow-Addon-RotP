package com.archlunatic.rotp_ctr.action.stand.projectile;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.archlunatic.rotp_ctr.entity.damaging.projectile.CDBloodCutterEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public class CatchTheRainbowRainCutter extends StandEntityAction {
    public static final StandPose BLOOD_CUTTER_SHOT_POSE = new StandPose("CD_BLOOD_CUTTER");

    public CatchTheRainbowRainCutter(StandEntityAction.Builder builder) {
        super(builder);
    }
    /*
    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        if (user.getHealth() >= user.getMaxHealth()
                && !(user instanceof PlayerEntity && ((PlayerEntity) user).abilities.invulnerable)) {
            return conditionMessage("full_health");
        }
        return super.checkSpecificConditions(user, power, target);
    }*/

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            MinecraftServer source = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
            if (!world.isRaining()) {
                LivingEntity user = userPower.getUser();
                CDBloodCutterEntity cutter = new CDBloodCutterEntity(user, world);
                cutter.setShootingPosOf(user);
                cutter.shootFromRotation(user, 10.0F, standEntity.getProjectileInaccuracy(1.0F));
                standEntity.addProjectile(cutter);
            }
        }
    }

    @Override
    protected int getCooldownAdditional(IStandPower power, int ticksHeld) {
        int cooldown = super.getCooldownAdditional(power, ticksHeld);
        if (!power.isUserCreative() && power.getUser() != null) {
            LivingEntity user = power.getUser();
            cooldown = MathHelper.ceil((float) cooldown * 2);
        }
        return cooldown;
    }
    /*
    @Override
    protected void playSoundAtStand(World world, StandEntity standEntity, SoundEvent sound, IStandPower standPower, Phase phase) {
        if (world.isClientSide() && phase == Phase.WINDUP && sound == ModSounds.CRAZY_DIAMOND_FIX_STARTED.get()) {
            ClientTickingSoundsHelper.playStandEntityCancelableActionSound(standEntity, sound, this, phase, 1.0F, 1.0F, false);
        }
        else {
            super.playSoundAtStand(world, standEntity, sound, standPower, phase);
        }
    }*/
}
