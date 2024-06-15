package com.archlunatic.rotp_ctr.entity.damaging.projectile;

import com.archlunatic.rotp_ctr.init.InitEntities;
import com.github.standobyte.jojo.action.ActionTarget.TargetType;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.util.general.GeneralUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity.isInRain;

public class CatchTheRainbowRainBladeEntity3 extends ModdedProjectileEntity {

    public CatchTheRainbowRainBladeEntity3(EntityType<CatchTheRainbowRainBladeEntity3> type, World world) {
        super(type, world);
    }

    public CatchTheRainbowRainBladeEntity3(LivingEntity shooter, World world) {super(InitEntities.CTR_RAIN_BLADE3.get(), shooter, world);}

    @Override
    protected boolean hurtTarget(Entity target, @Nullable LivingEntity owner) {
        if (isInRain(target)) {
            if (target instanceof LivingEntity) {
                LivingEntity targetLiving = (LivingEntity) target;
                if (GeneralUtil.orElseFalse(INonStandPower.getNonStandPowerOptional(targetLiving), power -> {return false;})) {
                    remove();
                    return false;
                }
            }
            super.hurtTarget(target, owner);
        }
        return false;
    }

    protected void breakProjectile(TargetType targetType, RayTraceResult hitTarget) {
        if (targetType != TargetType.ENTITY || ((EntityRayTraceResult) hitTarget).getEntity() instanceof LivingEntity) {
            super.breakProjectile(targetType, hitTarget);
        }
    }

    @Override
    protected void addShooterMotion(Entity shooter) { }

    @Override
    public int ticksLifespan() {return 100;}

    @Override
    protected float getBaseDamage(){return 8.0F;}

    @Override
    protected float getMaxHardnessBreakable() {return 0;}

    @Override
    public boolean standDamage() {return false;}
}
