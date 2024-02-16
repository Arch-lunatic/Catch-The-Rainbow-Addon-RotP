package com.archlunatic.rotp_ctr.action.stand;

import com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity;

import java.util.function.Supplier;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandManifestation;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class RainBlades extends StandAction {
    public RainBlades(StandAction.Builder builder) {
        super(builder);
    }


    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        if (!isInRain(user)) {
            return conditionMessage("needs_rain");
        }
        return super.checkSpecificConditions(user, power, target);
    }

    private boolean isInRain(Entity entity) { // пришлось скопировать из ваниллы, т.к. там этот метод приватный
        BlockPos blockPos = entity.blockPosition();
        return entity.level.isRainingAt(blockPos) || entity.level.isRainingAt(new BlockPos(blockPos.getX(), entity.getBoundingBox().maxY, blockPos.getZ()));
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if (!world.isClientSide()) {
            IStandManifestation summonedStand = power.getStandManifestation();
            if (summonedStand instanceof CatchTheRainbowEntity) {
                CatchTheRainbowEntity ctrEntity = (CatchTheRainbowEntity) summonedStand;
                ctrEntity.setActive(!ctrEntity.isActive());
            }
        }
    }
    @Override
    public IFormattableTextComponent getTranslatedName(IStandPower power, String key) { return new TranslationTextComponent(key, String.format("%.2f", (float) 99999 / 20));}
}
