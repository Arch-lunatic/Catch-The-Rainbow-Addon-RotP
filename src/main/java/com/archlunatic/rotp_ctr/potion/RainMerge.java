package com.archlunatic.rotp_ctr.potion;

import java.util.UUID;

import com.github.standobyte.jojo.potion.IApplicableEffect;
import com.github.standobyte.jojo.potion.UncurableEffect;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;

public class RainMerge extends UncurableEffect implements IApplicableEffect {
    private static final UUID MODIFIER_ATTACK_SPEED = UUID.fromString("545a64e1-ee9f-4152-ab04-6ff6f05baba3");
    private static final UUID MODIFIER_MOVEMENT_SPEED = UUID.fromString("78aace8e-f365-43dd-9219-111fb75d609c");


    public RainMerge(EffectType type, int color) {
        super(type, color);
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeModifierManager pAttributeMap, int pAmplifier) {
        ModifiableAttributeInstance attack_speed = pLivingEntity.getAttribute(Attributes.ATTACK_SPEED);
        if (attack_speed != null) {
            attack_speed.removeModifier(MODIFIER_ATTACK_SPEED);
            attack_speed.addTransientModifier(new AttributeModifier(MODIFIER_ATTACK_SPEED,
                    "0 attack speed while incredible", -20, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
//        ModifiableAttributeInstance movement_speed = pLivingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
//        if (movement_speed != null) {
//            movement_speed.removeModifier(MODIFIER_MOVEMENT_SPEED);
//            movement_speed.addTransientModifier(new AttributeModifier(MODIFIER_MOVEMENT_SPEED,
//                    "0 attack speed while incredible", -20, AttributeModifier.Operation.MULTIPLY_TOTAL));
//        }
        PlayerEntity player = (PlayerEntity) pLivingEntity;
        player.abilities.flying = false;
        player.abilities.invulnerable = true;
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void applyEffectTick(LivingEntity user, int p_76394_2_) {
        if (user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;
            IStandPower.getStandPowerOptional(player).ifPresent(power -> power.consumeStamina(1));
            player.abilities.flying = false;
            player.abilities.invulnerable = true;
        }
        if (resetsDeltaMovement()) {
            user.setDeltaMovement(0, user.getDeltaMovement().y < 0 ? user.getDeltaMovement().y : 0, 0);
        }
        super.applyEffectTick(user, p_76394_2_);
    }

    public boolean resetsDeltaMovement() { return true; }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) { return true; }
    @Override
    public boolean isApplicable(LivingEntity livingEntity) {
        return !(livingEntity instanceof PlayerEntity && ((PlayerEntity) livingEntity).abilities.instabuild);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeModifierManager pAttributeMap, int pAmplifier) {
        ModifiableAttributeInstance attack_speed = pLivingEntity.getAttribute(Attributes.ATTACK_SPEED);
        if (attack_speed != null) {
            AttributeModifier modifier = attack_speed.getModifier(MODIFIER_ATTACK_SPEED);
            if (modifier != null) {
                attack_speed.removeModifier(modifier);
            }
        }
//        ModifiableAttributeInstance movement_speed = pLivingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
//        if (movement_speed != null) {
//            AttributeModifier modifier = movement_speed.getModifier(MODIFIER_MOVEMENT_SPEED);
//            if (modifier != null) {
//                movement_speed.removeModifier(modifier);
//            }
//        }
        PlayerEntity player = (PlayerEntity) pLivingEntity;
        player.abilities.flying = true;
        player.abilities.invulnerable = false;
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }
}