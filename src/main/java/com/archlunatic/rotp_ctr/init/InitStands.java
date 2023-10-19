package com.archlunatic.rotp_ctr.init;

import com.archlunatic.rotp_ctr.RotpCtrAddon;
import com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity;
import com.archlunatic.rotp_ctr.action.stand.projectile.CatchTheRainbowRainCutter;
import com.archlunatic.rotp_ctr.action.stand.TimeStopInstant;
import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityBlock;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.action.stand.StandEntityMeleeBarrage;
import com.github.standobyte.jojo.action.stand.TimeStop;
//import com.github.standobyte.jojo.action.stand.TimeStopInstant;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.power.impl.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.impl.stand.stats.TimeStopperStandStats;
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
    
    public static final RegistryObject<StandEntityAction> CATCHTHERAINBOW_PUNCH = ACTIONS.register("catch_the_rainbow_punch", 
            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()
                    .punchSound(InitSounds.CATCHTHERAINBOW_PUNCH_LIGHT)));
    
    public static final RegistryObject<StandEntityAction> CATCHTHERAINBOW_BARRAGE = ACTIONS.register("catch_the_rainbow_barrage", 
            () -> new StandEntityMeleeBarrage(new StandEntityMeleeBarrage.Builder()
                    .barrageHitSound(InitSounds.CATCHTHERAINBOW_BARRAGE)));
    
    public static final RegistryObject<StandEntityHeavyAttack> CATCHTHERAINBOW_COMBO_PUNCH = ACTIONS.register("catch_the_rainbow_combo_punch", 
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.CATCHTHERAINBOW_PUNCH_HEAVY)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityAction> CATCH_THE_RAINBOW_RAIN_CUTTER = ACTIONS.register("catch_the_rainbow_rain_cutter",
            () -> new CatchTheRainbowRainCutter(new StandEntityAction.Builder().staminaCost(25).cooldown(20)
                    .resolveLevelToUnlock(2)
                    .standSound(ModSounds.CRAZY_DIAMOND_FIX_STARTED)
                    .partsRequired(StandPart.ARMS)));
    public static final RegistryObject<StandEntityHeavyAttack> CATCHTHERAINBOW_HEAVY_PUNCH = ACTIONS.register("catch_the_rainbow_heavy_punch", 
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.CATCHTHERAINBOW_PUNCH_HEAVY)
                    .partsRequired(StandPart.ARMS)
                    .setFinisherVariation(CATCHTHERAINBOW_COMBO_PUNCH)
                    .shiftVariationOf(CATCHTHERAINBOW_PUNCH).shiftVariationOf(CATCHTHERAINBOW_BARRAGE)));
	
	public static final RegistryObject<TimeStopInstant> CATCH_THE_RAINBOW_RAIN_BLINK = ACTIONS.register("catch_the_rainbow_blink",
            () -> new TimeStopInstant(new StandAction.Builder().staminaCost(50)
                    .resolveLevelToUnlock(2)
                    .ignoresPerformerStun()
                    .partsRequired(StandPart.MAIN_BODY), ModSounds.THE_WORLD_TIME_STOP_BLINK));
    
    public static final RegistryObject<StandEntityAction> CATCHTHERAINBOW_BLOCK = ACTIONS.register("catch_the_rainbow_block", 
            () -> new StandEntityBlock());
    
    
    public static final EntityStandRegistryObject<EntityStandType<TimeStopperStandStats>, StandEntityType<CatchTheRainbowEntity>> STAND_CATCHTHERAINBOW = 
            new EntityStandRegistryObject<>("catch_the_rainbow", 
                    STANDS, 
                    () -> new EntityStandType<TimeStopperStandStats>(
                            0x2967a2, ModStandsInit.PART_7_NAME,

                            new StandAction[] {
                                    CATCHTHERAINBOW_PUNCH.get(), 
                                    CATCHTHERAINBOW_BARRAGE.get(),
                                    CATCH_THE_RAINBOW_RAIN_CUTTER.get()},
                            new StandAction[] {
                                    CATCHTHERAINBOW_BLOCK.get(),
                                    CATCH_THE_RAINBOW_RAIN_BLINK.get()},

                            TimeStopperStandStats.class, new TimeStopperStandStats.Builder()
                            .tier(6)
                            .power(9.0)
                            .speed(9.0)
                            .range(2.0, 3.0)
                            .durability(13.0)
                            .precision(7.0)
							.timeStopMaxTicks(100, 180)
                            .timeStopCooldownPerTick(3F)
                            .build("Catch The Rainbow"), 

                            new StandType.StandTypeOptionals()
                            .addOst(InitSounds.CATCHTHERAINBOW_OST)), 

                    InitEntities.ENTITIES, 
                    () -> new StandEntityType<CatchTheRainbowEntity>(CatchTheRainbowEntity::new, 0.65F, 1.8F)
                    .summonSound(InitSounds.CATCHTHERAINBOW_SUMMON)
                    .unsummonSound(InitSounds.CATCHTHERAINBOW_UNSUMMON))
            .withDefaultStandAttributes();
}
