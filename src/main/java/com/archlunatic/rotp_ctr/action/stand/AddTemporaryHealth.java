package com.archlunatic.rotp_ctr.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.*;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class AddTemporaryHealth extends StandAction {
    final Supplier<SoundEvent> blinkSound;

    public AddTemporaryHealth(Builder builder, @Nonnull Supplier<SoundEvent> blinkSound) {
        super(builder);
        this.blinkSound = blinkSound;
    }
    
    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        if (!isInRain(user)) { return conditionMessage("needs_rain");}
        return super.checkSpecificConditions(user, power, target);
    }
    
    private boolean isInRain(Entity entity) { // пришлось скопировать из ваниллы, т.к. там этот метод приватный
        BlockPos blockPos = entity.blockPosition();
        return entity.level.isRainingAt(blockPos) || entity.level.isRainingAt(new BlockPos(blockPos.getX(), entity.getBoundingBox().maxY, blockPos.getZ()));
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        float health = checkHealth(user);
        user.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20 - (20 - health));
        user.setHealth(20 - (20 - health));
        user.removeEffect(Effects.ABSORPTION);
        user.removeEffect(Effects.HUNGER);
        user.addEffect(new EffectInstance(Effects.ABSORPTION, 24000, ((10 - (int)health/2)/2 - 1), false, false, false));
        user.addEffect(new EffectInstance(Effects.HUNGER, 40, 32, false, false, false));
    }

    private float checkHealth(LivingEntity user) { return user.getHealth();}
}