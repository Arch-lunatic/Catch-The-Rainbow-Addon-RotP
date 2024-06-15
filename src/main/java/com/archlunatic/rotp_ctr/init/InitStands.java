package com.archlunatic.rotp_ctr.init;

import com.archlunatic.rotp_ctr.AddonMain;
import com.archlunatic.rotp_ctr.action.stand.*;
import com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity;
import com.archlunatic.rotp_ctr.action.stand.projectile.CatchTheRainbowRainBlade;
import com.github.standobyte.jojo.action.stand.*;
import com.github.standobyte.jojo.power.impl.stand.StandInstance;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import static com.github.standobyte.jojo.init.power.stand.ModStandsInit.PART_7_NAME;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), AddonMain.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), AddonMain.MOD_ID);

  //======================================== Catch the Rainbow ========================================

    public static final RegistryObject<CatchTheRainbowRainBlade> CATCH_THE_RAINBOW_RAIN_BLADE = ACTIONS.register("catch_the_rainbow_rain_blade",
            () -> new CatchTheRainbowRainBlade(new StandAction.Builder().resolveLevelToUnlock(0)
                    .staminaCost(25).cooldown(10).autoSummonStand(), InitSounds.CATCH_THE_RAINBOW_RAIN_BLADE));

    public static final RegistryObject<CatchTheRainbowFallingRainBlades> CATCH_THE_RAINBOW_RAIN_BLADES = ACTIONS.register("catch_the_rainbow_rain_blades",
            () -> new CatchTheRainbowFallingRainBlades(new StandAction.Builder().resolveLevelToUnlock(0)
                    .staminaCost(100).cooldown(10).autoSummonStand().ignoresPerformerStun()));

    public static final RegistryObject<TimeStop> CATCH_THE_RAINBOW_RAIN_STOP = ACTIONS.register("catch_the_rainbow_rain_stop",
            () -> new TheWorldTimeStop(new TimeStop.Builder().resolveLevelToUnlock(1)
                    .staminaCost(250).staminaCostTick(7.5F).autoSummonStand()
                    .holdToFire(30, false)
                    .heldWalkSpeed(0).isTrained().partsRequired(StandInstance.StandPart.MAIN_BODY)
                    .ignoresPerformerStun()
                    .timeStopSound(ModSounds.THE_WORLD_TIME_STOP)//todo сменить
                    .timeResumeSound(ModSounds.THE_WORLD_TIME_RESUME)));//todo сменить

    public static final RegistryObject<TimeResume> RAIN_RESUME = ACTIONS.register("catch_the_rainbow_rain_resume",
            () -> new TimeResume(new StandAction.Builder().shiftVariationOf(CATCH_THE_RAINBOW_RAIN_STOP)));



    public static final RegistryObject<CatchTheRainbowTeleport> CATCH_THE_RAINBOW_RAIN_BLINK = ACTIONS.register("catch_the_rainbow_rain_blink",
            () -> new CatchTheRainbowTeleport(new StandAction.Builder().resolveLevelToUnlock(0)
                    .staminaCost(60).cooldown(10).autoSummonStand()
                    .ignoresPerformerStun(), InitSounds.CATCH_THE_RAINBOW_RAIN_BLINK));

    public static final RegistryObject<CatchTheRainbowRainMerge> CATCH_THE_RAINBOW_RAIN_MERGE = ACTIONS.register("catch_the_rainbow_rain_merge",
            () -> new CatchTheRainbowRainMerge(new StandAction.Builder().resolveLevelToUnlock(0)
                    .holdToFire(5, false)
                    .staminaCost(50).cooldown(50).autoSummonStand()//.shout(InitSounds.CATCH_THE_RAINBOW_MERGE)
                    .ignoresPerformerStun(), InitSounds.CATCH_THE_RAINBOW_MERGE));

    public static final RegistryObject<CatchTheRainbowTempHealth> CATCH_THE_RAINBOW_TEMP_HEALTH = ACTIONS.register("catch_the_rainbow_temp_health",
            () -> new CatchTheRainbowTempHealth(new StandAction.Builder().resolveLevelToUnlock(0)
                    .staminaCost(80).cooldown(20).autoSummonStand()
                    .holdToFire(10, false)
                    .ignoresPerformerStun(), InitSounds.CATCH_THE_RAINBOW_HEAL));

    public static final RegistryObject<CatchTheRainbowRainRedemption> CATCH_THE_RAINBOW_REDEMPTION = ACTIONS.register("catch_the_rainbow_rain_redemption",
            () -> new CatchTheRainbowRainRedemption(new StandAction.Builder().resolveLevelToUnlock(1)
                    .staminaCost(380).cooldown(12000).autoSummonStand()
                    .holdToFire(20, true)
                    .ignoresPerformerStun(), InitSounds.CATCH_THE_RAINBOW_MERGE));


    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<CatchTheRainbowEntity>> STAND_CATCH_THE_RAINBOW =
            new EntityStandRegistryObject<>("catch_the_rainbow",
                    STANDS,
                    () -> new EntityStandType.Builder<StandStats>()
                            .color(0x2967a2)
                            .storyPartName(PART_7_NAME)
                            .leftClickHotbar(
                                    CATCH_THE_RAINBOW_RAIN_BLADE.get(),
                                    CATCH_THE_RAINBOW_RAIN_BLADES.get()
                                    //CATCH_THE_RAINBOW_RAIN_RAIN_STOP.get()
                            )
                            .rightClickHotbar(
                                    CATCH_THE_RAINBOW_RAIN_BLINK.get(),
                                    CATCH_THE_RAINBOW_RAIN_MERGE.get(),
                                    CATCH_THE_RAINBOW_TEMP_HEALTH.get(),
                                    CATCH_THE_RAINBOW_REDEMPTION.get()
                            )
                            .defaultStats(StandStats.class, new StandStats.Builder()
                                    .tier(5)
                                    .power(9.0)
                                    .speed(9.0)
                                    .range(13.0)
                                    .durability(13.0)
                                    .precision(7.0)
                                    .randomWeight(2)
                            )
                            .addOst(InitSounds.CATCH_THE_RAINBOW_OST)
                            .disableManualControl()
                            .disableStandLeap()
                            .addAttackerResolveMultTier(10)
                            .build(),

                    InitEntities.ENTITIES,
                    () -> new StandEntityType<CatchTheRainbowEntity>(CatchTheRainbowEntity::new, 0F, 0F)
                    .summonSound(InitSounds.CATCH_THE_RAINBOW_SUMMON)
                    .unsummonSound(InitSounds.CATCH_THE_RAINBOW_UNSUMMON))
            .withDefaultStandAttributes();
}
