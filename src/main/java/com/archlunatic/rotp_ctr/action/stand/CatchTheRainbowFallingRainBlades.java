package com.archlunatic.rotp_ctr.action.stand;

import com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity;
import static com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity.isInRain;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandManifestation;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import com.github.standobyte.jojo.util.general.LazySupplier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CatchTheRainbowFallingRainBlades extends StandAction {
    public CatchTheRainbowFallingRainBlades(StandAction.Builder builder) {
        super(builder);
    }


    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        PlayerEntity player = (PlayerEntity) user;
        if (!isInRain(player)) {
            if(!player.isCreative()){
                return conditionMessage("needs_rain");
            }
        }
        if (power.getStamina() <= 100){
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
                ctrEntity.setActive(!ctrEntity.isActive());
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
                if (ctrEntity.isActive()) { return this.disableTex.get();}
            }
        }
        return super.getIconTexturePath(power);
    }
}

