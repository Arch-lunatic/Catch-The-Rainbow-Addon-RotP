package com.archlunatic.rotp_ctr.action.stand;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.ActionTarget.TargetType;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.MathUtil;
import com.github.standobyte.jojo.util.mod.JojoModUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class CtrTeleport extends StandAction {
    final Supplier<SoundEvent> blinkSound;
    
    public CtrTeleport(StandAction.Builder builder, 
            @Nonnull Supplier<SoundEvent> blinkSound) {
        super(builder);
        this.blinkSound = blinkSound;
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
    
    private static final double MAX_DISTANCE = 1360;
    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        SoundEvent sound = blinkSound.get();

        if (sound != null) {
            user.playSound(sound, 1.0F, 1.0F);
        }
        double distance = 3 + (MAX_DISTANCE/getStaminaCost(power))*(power.getStamina()/power.getMaxStamina()); //3 + 17m*%

        Vector3d blinkPos = null;
        if (target.getType() == TargetType.EMPTY) {
            RayTraceResult rayTrace = JojoModUtil.rayTrace(user, distance, entity -> entity instanceof LivingEntity && !(entity instanceof StandEntity && ((StandEntity) entity).getUser() == user));
            if (rayTrace.getType() == RayTraceResult.Type.MISS) {
                blinkPos = rayTrace.getLocation();
            }
            target = ActionTarget.fromRayTraceResult(rayTrace);
        }
        
        switch (target.getType()) {
        case ENTITY:
            blinkPos = getEntityTargetTeleportPos(user, target.getEntity());
            Vector3d toTarget = target.getEntity().position().subtract(blinkPos);
            user.yRot = MathUtil.yRotDegFromVec(toTarget);
            user.yRotO = user.yRot;
            break;
        case BLOCK:
            BlockPos blockPosTargeted = target.getBlockPos();
            blinkPos = Vector3d.atBottomCenterOf(world.isEmptyBlock(blockPosTargeted.above()) ? blockPosTargeted.above() : blockPosTargeted.relative(target.getFace()));
            break;
        default:
            break;
        }
        
        if (!world.isClientSide()) {
            user.teleportTo(blinkPos.x, blinkPos.y, blinkPos.z);
        }
    }

    protected Vector3d getEntityTargetTeleportPos(Entity user, Entity target) {
        double distance = target.getBbWidth() + user.getBbWidth();
        return user.distanceToSqr(target) > distance * distance ? target.position().subtract(user.getLookAngle().scale(distance)) : user.position();
    }
}
