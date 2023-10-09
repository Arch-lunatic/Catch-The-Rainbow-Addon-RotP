package com.archlunatic.rotp_ctr.init;

import com.archlunatic.rotp_ctr.RotpCtrAddon;
import com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity;
import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction.Phase;
import com.github.standobyte.jojo.action.stand.StandEntityBlock;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.action.stand.StandEntityMeleeBarrage;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
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
    
    public static final RegistryObject<StandEntityHeavyAttack> CATCHTHERAINBOW_HEAVY_PUNCH = ACTIONS.register("catch_the_rainbow_heavy_punch", 
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.CATCHTHERAINBOW_PUNCH_HEAVY)
                    .partsRequired(StandPart.ARMS)
                    .setFinisherVariation(CATCHTHERAINBOW_COMBO_PUNCH)
                    .shiftVariationOf(CATCHTHERAINBOW_PUNCH).shiftVariationOf(CATCHTHERAINBOW_BARRAGE)));
    
    public static final RegistryObject<StandEntityAction> CATCHTHERAINBOW_BLOCK = ACTIONS.register("catch_the_rainbow_block", 
            () -> new StandEntityBlock());
    
    
    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<CatchTheRainbowEntity>> STAND_CATCHTHERAINBOW = 
            new EntityStandRegistryObject<>("catch_the_rainbow", 
                    STANDS, 
                    () -> new EntityStandType<StandStats>(
                            0x2967a2, ModStandsInit.PART_7_NAME,

                            new StandAction[] {
                                    CATCHTHERAINBOW_PUNCH.get(), 
                                    CATCHTHERAINBOW_BARRAGE.get()},
                            new StandAction[] {
                                    CATCHTHERAINBOW_BLOCK.get()},

                            StandStats.class, new StandStats.Builder()
                            .tier(6)
                            .power(16.0)
                            .speed(16.0)
                            .range(2.0, 3.0)
                            .durability(16.0)
                            .precision(16.0)
                            .build("Catch The Rainbow"), 

                            new StandType.StandTypeOptionals()
                            .addOst(InitSounds.CATCHTHERAINBOW_OST)), 

                    InitEntities.ENTITIES, 
                    () -> new StandEntityType<CatchTheRainbowEntity>(CatchTheRainbowEntity::new, 0.65F, 1.95F)
                    .summonSound(InitSounds.CATCHTHERAINBOW_SUMMON)
                    .unsummonSound(InitSounds.CATCHTHERAINBOW_UNSUMMON))
            .withDefaultStandAttributes();
}
