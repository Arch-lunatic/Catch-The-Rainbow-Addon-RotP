package com.archlunatic.rotp_ctr.entity.stand.stands;


import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CatchTheRainbowEntity extends StandEntity {

    public CatchTheRainbowEntity(StandEntityType<CatchTheRainbowEntity> type, World world) {
        super(type, world);
        unsummonOffset = getDefaultOffsetFromUser().copy();
    }
    private final StandRelativeOffset offsetDefault = StandRelativeOffset.withYOffset(0, 0, 0);

    @Override
    public boolean isPickable(){ return false;}

	public StandRelativeOffset getDefaultOffsetFromUser() {return offsetDefault;}

    private BlockPos prevUserPos = null;
    @Override
    public void tick() {
        super.tick();

        LivingEntity user = getUser();
        assert user != null;
        BlockPos blockPos = user.blockPosition();
        if (!user.level.isClientSide()) {
            if (isInRain(user)) {
                if (user.hasEffect(Effects.ABSORPTION)) {
                    IStandPower.getStandPowerOptional(user).ifPresent(power -> {power.consumeStamina(1.5F);});
                }
                if (!blockPos.below().equals(prevUserPos) && level.getBlockState(blockPos.below())==Blocks.BARRIER.defaultBlockState()) {
                    IStandPower.getStandPowerOptional(user).ifPresent(power -> {power.consumeStamina(1);});
                }
                if (isActive()){
                    AxisAlignedBB area = getBoundingBox().inflate(radiusOfRainKillAura());
                    List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                            area, entity -> !(entity instanceof StandEntity) && entity != getUser());
                    for (Entity entity : entities){ if (isInRain(entity)) entity.hurt(DamageSource.CACTUS, RainType() ? (float) (5/getUserPower().getType().getStats().getBasePower()) : (float) (6/getUserPower().getType().getStats().getBasePower()) );}
                    IStandPower.getStandPowerOptional(user).ifPresent(power -> {power.consumeStamina(RainType() ? (float) (getUserPower().getType().getStats().getBasePower()/getUserPower().getType().getStats().getBasePrecision()) : (float) (getUserPower().getType().getStats().getBasePower()/getUserPower().getType().getStats().getBasePrecision()+1));});
                }
            }
//            if (!isInRain(user)) {}

        }
        if (level.isClientSide()) {
            if (isInRain(user)){
                if (prevUserPos != null && !blockPos.below().equals(prevUserPos) && level.getBlockState(blockPos.below())==Blocks.BARRIER.defaultBlockState()) {
                    level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);
                    prevUserPos = null;
                }
                if (level.getBlockState(blockPos.below()).isAir() && (Objects.requireNonNull(getUserPower()).getStamina() >= 30)) {
                    level.setBlock(blockPos.below(), Blocks.BARRIER.defaultBlockState(), 3);//добавить частицы воды
                    prevUserPos = blockPos.below();
                }
                if (user.isShiftKeyDown() && level.getBlockState(blockPos.below())==Blocks.BARRIER.defaultBlockState()){
                    level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);//добавить частицы воды
                    prevUserPos = null;
                }
                if (prevUserPos != null && !(Objects.requireNonNull(getUserPower()).getStamina() >= 30)) {
                    level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);//добавить частицы воды
                    prevUserPos = null;
                }
            }
            if (!isInRain(user)) {
                if (level.getBlockState(blockPos.below())==Blocks.BARRIER.defaultBlockState()) {
                    level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);//добавить частицы воды
                    prevUserPos = null;
                }
                if (prevUserPos != null && level.getBlockState(blockPos.below())!=Blocks.BARRIER.defaultBlockState()){
                    level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);//добавить частицы воды
                    prevUserPos = null;
                }
                getUser().removeEffect(Effects.ABSORPTION);
                if (Objects.requireNonNull(user.getAttribute(Attributes.MAX_HEALTH)).getBaseValue() < 20) Objects.requireNonNull(user.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(20);
            }
            if (Objects.requireNonNull(user.getAttribute(Attributes.MAX_HEALTH)).getBaseValue() >= 20) getUser().removeEffect(Effects.ABSORPTION);
        }
    }

    private float radiusOfRainKillAura() {
        return 25*(Objects.requireNonNull(getUserPower()).getStamina()/getUserPower().getMaxStamina());}

    public boolean RainType(){ boolean RainType = true; if (level.isThundering()){ RainType = false;}return RainType;}

    private boolean isActive;
    public boolean isActive() { return isActive;}
    public void setActive(boolean isActive) { this.isActive = isActive;}

    private boolean isInRain(Entity entity) {
        BlockPos blockPos = entity.blockPosition();
        return entity.level.isRainingAt(blockPos) || entity.level.isRainingAt(new BlockPos(blockPos.getX(), entity.getBoundingBox().maxY, blockPos.getZ()));
    }

    private static final AttributeModifier ATTACK_DISTANCE_BUFF = new AttributeModifier(
            UUID.fromString("0fa53f0f-7ea0-42a3-98da-40f06e523caf"), "Attack distance buff", 3, AttributeModifier.Operation.MULTIPLY_BASE);

    private static final AttributeModifier REACH_DISTANCE_BUFF = new AttributeModifier(
            UUID.fromString("c137f815-2f58-4fc7-ad4d-a573249f90e0"), "Reach distance buff", 3, AttributeModifier.Operation.MULTIPLY_BASE);

    @Override
    public void setUserAndPower(LivingEntity user, IStandPower power) {
        super.setUserAndPower(user, power);

        if (!level.isClientSide() && user != null && isInRain(user)) {
            ModifiableAttributeInstance attributeInstance1 = user.getAttribute(ForgeMod.REACH_DISTANCE.get());
//            ModifiableAttributeInstance attributeInstance2 = user.getAttribute(ForgeMod.?.get());
            if (attributeInstance1 != null) { attributeInstance1.addTransientModifier(REACH_DISTANCE_BUFF);}
//            if (attributeInstance2 != null){ attributeInstance2.addTransientModifier(ATTACK_DISTANCE_BUFF);}
        }
    }

    @Override
    public void onRemovedFromWorld() {
        super.onRemovedFromWorld();

        if (!level.isClientSide()) {
            LivingEntity user = getUser();
            if (user != null) {
                ModifiableAttributeInstance attributeInstance1 = user.getAttribute(ForgeMod.REACH_DISTANCE.get());
//                ModifiableAttributeInstance attributeInstance2 = user.getAttribute(ForgeMod.?.get());
                if (attributeInstance1 != null) { attributeInstance1.removeModifier(REACH_DISTANCE_BUFF);}
//                if (attributeInstance2 != null){ attributeInstance2.addTransientModifier(ATTACK_DISTANCE_BUFF);}
            }
        }
        if (prevUserPos != null) { level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);}
        Objects.requireNonNull(getUser()).removeEffect(Effects.ABSORPTION);
        if (Objects.requireNonNull(getUser().getAttribute(Attributes.MAX_HEALTH)).getBaseValue() < 20) Objects.requireNonNull(getUser().getAttribute(Attributes.MAX_HEALTH)).setBaseValue(20);
    }
}
