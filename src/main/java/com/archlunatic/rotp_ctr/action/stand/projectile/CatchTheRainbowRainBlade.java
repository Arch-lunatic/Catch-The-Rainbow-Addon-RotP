package com.archlunatic.rotp_ctr.action.stand.projectile;

import com.archlunatic.rotp_ctr.entity.damaging.projectile.CatchTheRainbowRainBladeEntity1;

import static com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity.isInRain;

import com.archlunatic.rotp_ctr.entity.damaging.projectile.CatchTheRainbowRainBladeEntity2;
import com.archlunatic.rotp_ctr.entity.damaging.projectile.CatchTheRainbowRainBladeEntity3;
import com.archlunatic.rotp_ctr.init.InitEffects;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandManifestation;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;


public class CatchTheRainbowRainBlade extends StandAction {

    @Nullable
    private final Supplier<SoundEvent> bladeSound;
    public CatchTheRainbowRainBlade(Builder builder, @Nullable Supplier<SoundEvent> bladeSound) {
        super(builder);
        this.bladeSound = bladeSound;
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        PlayerEntity player = (PlayerEntity) user;
        if (!isInRain(player)) {
            if(!player.isCreative()){
                return conditionMessage("needs_rain");
            }
        }
        if (player.hasEffect(InitEffects.RAIN_MERGE.get())){
            if(!player.isCreative()) {
                return conditionMessage("dont_have_merge");
            }
        }
        return super.checkSpecificConditions(user, power, target);
    }
    @Override
    public void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if (!world.isClientSide()) {
            IStandManifestation summonedStand = power.getStandManifestation();
            StandEntity standEntity = (StandEntity) summonedStand;
            assert standEntity != null;
            double baseAttackSpeed = Objects.requireNonNull(standEntity.getUserPower()).getType().getStats().getBaseAttackSpeed();
            Random random = new Random(); int randomNumber = random.nextInt(3)+1;

            if (randomNumber == 1) {
                CatchTheRainbowRainBladeEntity1 cutter = new CatchTheRainbowRainBladeEntity1(user, world);
                cutter.setShootingPosOf(user);
                cutter.shootFromRotation(user, 2.0F, standEntity.getProjectileInaccuracy((float) baseAttackSpeed / 9));
                standEntity.addProjectile(cutter);
                if (bladeSound != null) {
                    world.playSound(null, user.blockPosition(), bladeSound.get(), SoundCategory.PLAYERS, 1, 1);
                }
            }else if (randomNumber == 2) {
                CatchTheRainbowRainBladeEntity2 cutter = new CatchTheRainbowRainBladeEntity2(user, world);
                cutter.setShootingPosOf(user);
                cutter.shootFromRotation(user, 2.0F, standEntity.getProjectileInaccuracy((float) baseAttackSpeed / 9));
                standEntity.addProjectile(cutter);
                if (bladeSound != null) {
                    world.playSound(null, user.blockPosition(), bladeSound.get(), SoundCategory.PLAYERS, 1, 1);
                }
            }else {
                CatchTheRainbowRainBladeEntity3 cutter = new CatchTheRainbowRainBladeEntity3(user, world);
                cutter.setShootingPosOf(user);
                cutter.shootFromRotation(user, 2.0F, standEntity.getProjectileInaccuracy((float) baseAttackSpeed / 9));
                standEntity.addProjectile(cutter);
                if (bladeSound != null) {
                    world.playSound(null, user.blockPosition(), bladeSound.get(), SoundCategory.PLAYERS, 1, 1);
                }
            }
        }
    }
}
