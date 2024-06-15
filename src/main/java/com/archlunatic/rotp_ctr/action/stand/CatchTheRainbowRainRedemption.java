package com.archlunatic.rotp_ctr.action.stand;

import com.archlunatic.rotp_ctr.init.InitEffects;

import static com.archlunatic.rotp_ctr.action.stand.CatchTheRainbowTempHealth.HEALTH_CHANGER_1;
import static com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity.isInRain;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.*;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class CatchTheRainbowRainRedemption extends StandAction {
    @Nullable
    private final Supplier<SoundEvent> healSound;

    public CatchTheRainbowRainRedemption(Builder builder, RegistryObject<SoundEvent> healSound) {
        super(builder);
        this.healSound = healSound;
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        PlayerEntity player = (PlayerEntity) user;
        if (!isInRain(player)) {
            if(!player.isCreative()){
                return conditionMessage("needs_rain");
            }
        }
        if (user.getHealth() > user.getMaxHealth() * 0.3F){
            if(!player.isCreative()) {
                return conditionMessage("not_enough_damage");
            }
        }
        if (power.getStamina() < 380){
            return conditionMessage("not_enough_stamina");
        }
        return super.checkSpecificConditions(user, power, target);
    }

    @Override
    public void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        assert user != null;
        if (!world.isClientSide) {
            if(user.hasEffect(InitEffects.RAIN_MERGE.get())){
                user.removeEffect(InitEffects.RAIN_MERGE.get());
            }
            if(user.hasEffect(Effects.ABSORPTION)){
                ModifiableAttributeInstance max_health = user.getAttribute(Attributes.MAX_HEALTH);
                if (max_health != null) {
                    max_health.removeModifier(HEALTH_CHANGER_1);
                }
                user.removeEffect(InitEffects.RAIN_MERGE.get());
            }
            user.addEffect(new EffectInstance(InitEffects.RAIN_REDEMPTION.get(), 12000, 0, false, false, true));
            user.addEffect(new EffectInstance(Effects.SATURATION, 1, 255, false, false, false));
            if (healSound != null) {
                world.playSound(null, user.blockPosition(), healSound.get(), SoundCategory.PLAYERS, 1, 1);
            }
        }
    }
}