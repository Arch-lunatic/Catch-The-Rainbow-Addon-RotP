package com.archlunatic.rotp_ctr.action.stand;

import com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity;
import com.archlunatic.rotp_ctr.init.InitEffects;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandManifestation;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.LazySupplier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import static com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity.isInRain;

public class CatchTheRainbowRainMerge extends StandAction {
    //todo    public static final PosedActionAnimation BLOOD_CUTTER_SHOT_POSE = new StandPose("CD_BLOOD_CUTTER");

    @Nullable
    private final Supplier<SoundEvent> mergeSound;

    public CatchTheRainbowRainMerge(Builder builder, RegistryObject<SoundEvent> mergeSound) {
        super(builder);
        this.mergeSound = mergeSound;
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        PlayerEntity player = (PlayerEntity) user;
        if (!isInRain(player)) {
            if(!player.isCreative()){
                return conditionMessage("needs_rain");
            }
        }
        if (player.hasEffect(InitEffects.RAIN_REDEMPTION.get()) || player.hasEffect(Effects.ABSORPTION)){
            if(!player.isCreative()) {
                return conditionMessage("dont_have_redemption_or_temp_health");
            }
        }
        if (power.getStamina() <= 50){
            return conditionMessage("not_enough_stamina");
        }
        return super.checkSpecificConditions(user, power, target);
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if (!world.isClientSide()) {
            IStandManifestation summonedStand = power.getStandManifestation();
            if (summonedStand instanceof CatchTheRainbowEntity) {
                CatchTheRainbowEntity ctrEntity = (CatchTheRainbowEntity) summonedStand;
                if(ctrEntity.isMergeActive()){
                    user.removeEffect(InitEffects.RAIN_MERGE.get());
                }else if(!ctrEntity.isMergeActive()){
                    user.addEffect(new EffectInstance(InitEffects.RAIN_MERGE.get(), 30, 0,false, false, false));
                }
                ctrEntity.setMergeActive(!ctrEntity.isMergeActive());
            }
            if (mergeSound != null) {
                world.playSound(null, user.blockPosition(), mergeSound.get(), SoundCategory.PLAYERS, 1, 1);
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
            IStandManifestation summonedStand = power.getStandManifestation();
            if (summonedStand instanceof CatchTheRainbowEntity) {
                CatchTheRainbowEntity ctrEntity = (CatchTheRainbowEntity) summonedStand;
                if (ctrEntity.isMergeActive()) { return this.disableTex.get();}
            }
        }
        return super.getIconTexturePath(power);
    }
}