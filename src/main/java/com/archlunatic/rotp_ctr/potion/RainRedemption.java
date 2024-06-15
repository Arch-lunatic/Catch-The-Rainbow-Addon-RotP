package com.archlunatic.rotp_ctr.potion;

import com.archlunatic.rotp_ctr.init.AddonStands;
import com.archlunatic.rotp_ctr.init.InitEffects;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.potion.IApplicableEffect;
import com.github.standobyte.jojo.potion.UncurableEffect;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import java.util.UUID;

public class RainRedemption extends UncurableEffect implements IApplicableEffect {
    private static final UUID MODIFIER_UUID = UUID.fromString("9a18fea8-4ab1-4dc8-a9b1-29488ea93462");

    public RainRedemption(EffectType type, int color) {
        super(type, color);
    }//Todo текстуры на теле

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeModifierManager pAttributeMap, int pAmplifier) {
        float missingHp = pLivingEntity.getMaxHealth() - 1F;// - pLivingEntity.getHealth();
        pLivingEntity.setAbsorptionAmount(pLivingEntity.getAbsorptionAmount() + pLivingEntity.getMaxHealth()*4F - 1F);// +dd missingHp
        ModifiableAttributeInstance health = pLivingEntity.getAttribute(Attributes.MAX_HEALTH);
        if (health != null) {
            health.removeModifier(MODIFIER_UUID);
            health.addTransientModifier(new AttributeModifier(MODIFIER_UUID,
                    "Max HP reduction from CtR healing", -missingHp, AttributeModifier.Operation.ADDITION));
        }
        pLivingEntity.addEffect(new EffectInstance(ModStatusEffects.RESOLVE.get(), 12000, 0, false, false, false));
        MCUtil.runCommand(pLivingEntity, "weather rain 12000");
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

//    @Override
//    public void applyEffectTick(LivingEntity user, int p_76394_2_) {
//        if (user instanceof PlayerEntity) {
//            PlayerEntity player = (PlayerEntity) user;
//            if (player.getMaxHealth() > 1F){
//                float missingHp = player.getMaxHealth() - 1F;// - pLivingEntity.getHealth();
//                player.setAbsorptionAmount(player.getAbsorptionAmount() + player.getMaxHealth()*4F - 1F);// +dd missingHp
//                ModifiableAttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);
//                if (health != null) {
//                    health.removeModifier(MODIFIER_UUID);
//                    health.addTransientModifier(new AttributeModifier(MODIFIER_UUID,
//                            "Max HP reduction from CtR healing", -missingHp, AttributeModifier.Operation.ADDITION));
//                }
//            }
//        }
//        super.applyEffectTick(user, p_76394_2_);
//    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeModifierManager pAttributeMap, int pAmplifier) {
        ModifiableAttributeInstance health = pLivingEntity.getAttribute(Attributes.MAX_HEALTH);
        if (health != null) {
            AttributeModifier modifier = health.getModifier(MODIFIER_UUID);
            if (modifier != null) {
                pLivingEntity.setAbsorptionAmount(pLivingEntity.getAbsorptionAmount() - pLivingEntity.getMaxHealth()*4F - 1F);// - modifier.getAmount()
                health.removeModifier(modifier);
            }
        }
        pLivingEntity.kill();
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public boolean isApplicable(LivingEntity entity) {
        return IStandPower.getStandPowerOptional(entity).map(stand ->
                stand.hasPower() && stand.getType() == AddonStands.CATCH_THE_RAINBOW.getStandType()).orElse(false);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}