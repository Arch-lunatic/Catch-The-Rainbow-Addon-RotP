package com.archlunatic.rotp_ctr.action.stand;

import java.util.function.Supplier;

import com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity;
import com.archlunatic.rotp_ctr.init.InitEffects;
import com.archlunatic.rotp_ctr.util.addon.JojoAddonUtil;
import static com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity.isInRain;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.ActionTarget.TargetType;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.MathUtil;
import com.github.standobyte.jojo.util.mod.JojoModUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CatchTheRainbowTeleport extends StandAction {

    @Nullable
    private final Supplier<SoundEvent> blinkSound;
    public CatchTheRainbowTeleport(StandAction.Builder builder, @Nullable Supplier<SoundEvent> blinkSound) {
        super(builder);
        this.blinkSound = blinkSound;
    }
    
    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        PlayerEntity player = (PlayerEntity) user;
        if (!isInRain(player)) {
            if(!player.isCreative()){
                return conditionMessage("needs_rain");
            }
        }

        if(user.hasEffect(InitEffects.RAIN_MERGE.get())){
            if(!player.isCreative()) {
                return conditionMessage("dont_have_merge");
            }
        }

        if (power.getStamina() <= 25){
            return conditionMessage("not_enough_stamina");
        }
        return super.checkSpecificConditions(user, power, target);
    }
    
    private static final double PERCENTAGE_DISTANCE = 20 - 5;
    @Override
    public void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        double distance = 5 + (PERCENTAGE_DISTANCE) * (power.getStamina() / power.getMaxStamina()); //5 + 15m*%
        if (user.hasEffect(ModStatusEffects.RESOLVE.get())){
            distance = 5 + PERCENTAGE_DISTANCE;
        }

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
        assert blinkPos != null;
        if (!world.isClientSide()) {
            user.teleportTo(blinkPos.x, blinkPos.y, blinkPos.z);
            if (blinkSound != null) {
                world.playSound(null, user.blockPosition(), blinkSound.get(), SoundCategory.PLAYERS, 1, 1);
            }
        }
    }

    protected Vector3d getEntityTargetTeleportPos(Entity user, Entity target) {
        double distance = target.getBbWidth() + user.getBbWidth();
        return user.distanceToSqr(target) > distance * distance ? target.position().subtract(user.getLookAngle().scale(distance)) : user.position();
    }

}
