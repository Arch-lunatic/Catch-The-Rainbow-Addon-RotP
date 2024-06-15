package com.archlunatic.rotp_ctr.action.stand;

import com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity;
import com.archlunatic.rotp_ctr.init.InitEffects;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandManifestation;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.LazySupplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Supplier;

import static com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity.isInRain;

public class CatchTheRainbowTempHealth extends StandAction {
    public static final UUID HEALTH_CHANGER_1 = UUID.fromString("29225666-5603-4b2e-9f60-1ef597b44af2");

    @Nullable
    private final Supplier<SoundEvent> healSound;

    public CatchTheRainbowTempHealth(Builder builder, @Nonnull Supplier<SoundEvent> healSound) {
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

        if (user.hasEffect(InitEffects.RAIN_MERGE.get()) || player.hasEffect(InitEffects.RAIN_REDEMPTION.get())){
            if(!player.isCreative()) {
                return conditionMessage("dont_have_merge_or_redemption");
            }
        }

        float health = user.getHealth();
        double maxHealth = user.getMaxHealth();
        ModifiableAttributeInstance max_health = user.getAttribute(Attributes.MAX_HEALTH);
        AttributeModifier setHealth = new AttributeModifier(HEALTH_CHANGER_1, "health_changer", (int) -(maxHealth - health), AttributeModifier.Operation.ADDITION);
        if (user.getHealth() > user.getMaxHealth() * 0.9F) {
            if (max_health != null){
            if (!max_health.hasModifier(setHealth)) {
                if (!player.isCreative()) {
                    return conditionMessage("little_bit_not_enough_damage");
                }
            }
            }
        }

        if (power.getStamina() <= 100) {
            if (!player.isCreative()) {
                return conditionMessage("not_enough_stamina");
            }
        }
        return super.checkSpecificConditions(user, power, target);
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        float health = user.getHealth();
        double maxHealth = user.getMaxHealth();

        if (!world.isClientSide()) {
            IStandManifestation summonedStand = power.getStandManifestation();
            if (summonedStand instanceof CatchTheRainbowEntity) {
                CatchTheRainbowEntity ctrEntity = (CatchTheRainbowEntity) summonedStand;
                ctrEntity.setMaxHP((float) maxHealth);
            }
            ModifiableAttributeInstance max_health = user.getAttribute(Attributes.MAX_HEALTH);
            if (max_health != null) {
                AttributeModifier setHealth = new AttributeModifier(HEALTH_CHANGER_1, "health_changer", (int) -(maxHealth - health), AttributeModifier.Operation.ADDITION);
                if (max_health.hasModifier(setHealth)){
                    max_health.removeModifier(HEALTH_CHANGER_1);
                    user.setHealth((float) (maxHealth - (maxHealth - health)));
                    user.removeEffect(Effects.ABSORPTION); user.removeEffect(Effects.HUNGER);
                } else {
                    max_health.addTransientModifier(setHealth);
                    user.setHealth((float) (maxHealth - (maxHealth - health)));
                    user.removeEffect(Effects.ABSORPTION); user.removeEffect(Effects.HUNGER);
                    user.addEffect(new EffectInstance(Effects.ABSORPTION, 37000, (int) ((maxHealth/2 - (int) health/2)/2 - 1), false, false, false));
                    user.addEffect(new EffectInstance(Effects.HUNGER, 20, 240, false, false, false));
                }
            }
            if (healSound != null) {
                world.playSound(null, user.blockPosition(), healSound.get(), SoundCategory.PLAYERS, 1, 1);
            }
        }
    }

    @Override
    public IFormattableTextComponent getTranslatedName(IStandPower power, String key) { return new TranslationTextComponent(key, String.format("%.2f", (float) 99999 / 20));}

    private final LazySupplier<ResourceLocation> disableTex =
            new LazySupplier<>(() -> makeIconVariant(this, "_disable"));

    @Override
    public ResourceLocation getIconTexturePath(@Nullable IStandPower power) {
        if (power != null) {
            float health = power.getUser().getHealth();
            double maxHealth = power.getUser().getMaxHealth();
            ModifiableAttributeInstance max_health = power.getUser().getAttribute(Attributes.MAX_HEALTH);
            AttributeModifier setHealth = new AttributeModifier(HEALTH_CHANGER_1, "health_changer", (int) -(maxHealth - health), AttributeModifier.Operation.ADDITION);
            if (max_health != null) {
                if (max_health.hasModifier(setHealth)) {
                    return this.disableTex.get();
                }

            }
        }
        return super.getIconTexturePath(power);
    }
}