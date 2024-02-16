package com.archlunatic.rotp_ctr.init;

import com.archlunatic.rotp_ctr.RotpCtrAddon;
import com.archlunatic.rotp_ctr.action.stand.AddTemporaryHealth;
import com.archlunatic.rotp_ctr.action.stand.RainBlades;
import com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity;
import com.archlunatic.rotp_ctr.action.stand.projectile.CatchTheRainbowRainBlade;
import com.archlunatic.rotp_ctr.action.stand.CtrTeleport;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), RotpCtrAddon.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), RotpCtrAddon.MOD_ID);
    
 // ======================================== Catch The Rainbow ========================================

    public static final RegistryObject<CatchTheRainbowRainBlade> CATCH_THE_RAINBOW_RAIN_BLADE = ACTIONS.register("catch_the_rainbow_rain_blade",
            () -> new CatchTheRainbowRainBlade(new StandEntityAction.Builder().staminaCost(25).cooldown(10)
                    .resolveLevelToUnlock(0).autoSummonStand()
                    .standSound(InitSounds.CATCHTHERAINBOW_BLADE)));

    public static final RegistryObject<RainBlades> CATCH_THE_RAINBOW_RAIN_BLADES = ACTIONS.register("catch_the_rainbow_rain_blades",
            () -> new RainBlades(new StandAction.Builder().staminaCost(25).cooldown(10)
                    .resolveLevelToUnlock(1).autoSummonStand()
                    .ignoresPerformerStun()));

	public static final RegistryObject<CtrTeleport> CATCH_THE_RAINBOW_RAIN_BLINK = ACTIONS.register("catch_the_rainbow_rain_blink",
            () -> new CtrTeleport(new StandAction.Builder().staminaCost(80)
                    .resolveLevelToUnlock(0).autoSummonStand()
                    .ignoresPerformerStun(), ModSounds.STAR_PLATINUM_TIME_STOP_BLINK));

    public static final RegistryObject<AddTemporaryHealth> CATCH_THE_RAINBOW_RAIN_ADD_TEMPORARY_HEALTH = ACTIONS.register("catch_the_rainbow_add_temporary_health",
            () -> new AddTemporaryHealth(new StandAction.Builder().holdToFire(30, false).staminaCost(580).cooldown(180)
                    .resolveLevelToUnlock(2).autoSummonStand()
                    .ignoresPerformerStun(), InitSounds.CATCHTHERAINBOW_HEAL));

    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<CatchTheRainbowEntity>> STAND_CATCHTHERAINBOW =
            new EntityStandRegistryObject<>("catch_the_rainbow",
                    STANDS,
                    () -> new EntityStandType.Builder<StandStats>()
                    .color(0x2967a2)
                    .storyPartName(ModStandsInit.PART_7_NAME)
                    .leftClickHotbar(
                            CATCH_THE_RAINBOW_RAIN_BLADE.get(),
                            CATCH_THE_RAINBOW_RAIN_BLADES.get()
                            )
                    .rightClickHotbar(
                            CATCH_THE_RAINBOW_RAIN_BLINK.get(),
                            CATCH_THE_RAINBOW_RAIN_ADD_TEMPORARY_HEALTH.get()
                            )
                    .defaultStats(StandStats.class, new StandStats.Builder()
                            .tier(6)
                            .power(9.0)
                            .speed(9.0)
                            .range(10.0)
                            .durability(13.0)
                            .precision(7.0)
                            .randomWeight(1)
                            )
                    .addOst(InitSounds.CATCHTHERAINBOW_OST)
                    .disableManualControl()
                    .disableStandLeap()
                    .build(),

                    InitEntities.ENTITIES, 
                    () -> new StandEntityType<CatchTheRainbowEntity>(CatchTheRainbowEntity::new, 0.65F, 1.8F)
                    .summonSound(InitSounds.CATCHTHERAINBOW_SUMMON)
                    .unsummonSound(InitSounds.CATCHTHERAINBOW_UNSUMMON))
            .withDefaultStandAttributes();
}
