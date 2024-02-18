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
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
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

        LivingEntity user = getUser(); assert user != null;
        BlockPos BelowBlockPosition = user.blockPosition().below();
        if (!user.level.isClientSide()) {
            if (isInRain(user)) {
                if (user.hasEffect(Effects.ABSORPTION)) {
                    IStandPower.getStandPowerOptional(user).ifPresent(power -> {power.consumeStamina(1.5F);});
                }
                if (!BelowBlockPosition.equals(prevUserPos) && level.getBlockState(BelowBlockPosition)==Blocks.BARRIER.defaultBlockState()) {
                    IStandPower.getStandPowerOptional(user).ifPresent(power -> {power.consumeStamina(1);});
                }
//                Vector3d position = null;
                if (isActive()){
                    //if (isCount==30) {
                        double BasePrecision = getUserPower().getType().getStats().getBasePrecision();
                        double BasePower = getUserPower().getType().getStats().getBasePower();
                        AxisAlignedBB area = getBoundingBox().inflate(radiusOfRainKillAura());
                        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                                area, entity -> !(entity instanceof StandEntity) && entity != getUser());
                        for (Entity entity : entities) {
                            if (isInRain(entity)) {
//                                position = entity.position();
                                entity.hurt(DamageSource.CACTUS, RainType() ? (float) (5/BasePower) : (float) (6/BasePower));}}
//                                SpawnBlade(position);}

                        IStandPower.getStandPowerOptional(user).ifPresent(power -> {
                            power.consumeStamina(RainType() ? (float) (BasePower / BasePrecision) : (float) (BasePower / BasePrecision + 1));
                        });
//                        Count(1);
//                    }else{Count(isCount()+1); }
                }
            }
//            if (!isInRain(user)) {}

        }
        if (level.isClientSide()) {
            if (isInRain(user)){
                if (prevUserPos != null && !BelowBlockPosition.equals(prevUserPos) && level.getBlockState(prevUserPos)==Blocks.BARRIER.defaultBlockState()) {
                    level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);
                    prevUserPos = null;
                }
                if (level.getBlockState(BelowBlockPosition).isAir() && (Objects.requireNonNull(getUserPower()).getStamina() >= 150)) {
                    level.setBlock(BelowBlockPosition, Blocks.BARRIER.defaultBlockState(), 3); //TOdo добавить частицы воды
                    prevUserPos = BelowBlockPosition;
                }
                if (user.isShiftKeyDown() && level.getBlockState(BelowBlockPosition)==Blocks.BARRIER.defaultBlockState()) {
                    level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);
                    prevUserPos = null;
                }
                if (prevUserPos != null && level.getBlockState(BelowBlockPosition)==Blocks.BARRIER.defaultBlockState() && !(Objects.requireNonNull(getUserPower()).getStamina() >= 150)) {
                    level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);
                    prevUserPos = null;
                }
            }
            if (!isInRain(user)) {
                if (level.getBlockState(BelowBlockPosition)==Blocks.BARRIER.defaultBlockState()) {
                    level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);
                    prevUserPos = null;
                }
                if (prevUserPos != null && level.getBlockState(BelowBlockPosition)!=Blocks.BARRIER.defaultBlockState()) {
                    level.setBlock(prevUserPos, Blocks.AIR.defaultBlockState(), 3);
                    prevUserPos = null;
                }
                getUser().removeEffect(Effects.ABSORPTION);
                if (Objects.requireNonNull(user.getAttribute(Attributes.MAX_HEALTH)).getBaseValue() < 20) Objects.requireNonNull(user.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(20);
            }
            if (Objects.requireNonNull(user.getAttribute(Attributes.MAX_HEALTH)).getBaseValue() >= 20) getUser().removeEffect(Effects.ABSORPTION);
        }
    }

//    private int isCount;
//    public int isCount(){return isCount;}
//    public void Count(int isCount) {this.isCount = isCount;}
//
//    public void SpawnBlade(Vector3d position) {
//        MinecraftServer source = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
//        source.getCommands().performCommand(source.createCommandSourceStack(), String.format("summon rotp_ctr:ctr_rain_blade %.2f %.2f %.2f {Motion:[0.0, -1.0, 0.0]}", position.x, position.y + 5F, position.z));
//    }/execute at Dev run particle minecraft:dripping_water ~ ~ ~0.1 0 0.1 1 1 1

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

    private static final AttributeModifier REACH_DISTANCE_BUFF = new AttributeModifier(
            UUID.fromString("c137f815-2f58-4fc7-ad4d-a573249f90e0"), "Reach distance buff", 3, AttributeModifier.Operation.MULTIPLY_BASE);

    private static final AttributeModifier ATTACK_DISTANCE_BUFF = new AttributeModifier(
            UUID.fromString("0fa53f0f-7ea0-42a3-98da-40f06e523caf"), "Attack distance buff", 3, AttributeModifier.Operation.MULTIPLY_BASE);

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
