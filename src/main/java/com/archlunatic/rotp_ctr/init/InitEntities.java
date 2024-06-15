package com.archlunatic.rotp_ctr.init;

import com.archlunatic.rotp_ctr.AddonMain;
import com.archlunatic.rotp_ctr.entity.damaging.projectile.CatchTheRainbowRainBladeEntity1;

import com.archlunatic.rotp_ctr.entity.damaging.projectile.CatchTheRainbowRainBladeEntity2;
import com.archlunatic.rotp_ctr.entity.damaging.projectile.CatchTheRainbowRainBladeEntity3;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, AddonMain.MOD_ID);

    public static final RegistryObject<EntityType<CatchTheRainbowRainBladeEntity1>> CTR_RAIN_BLADE1 = ENTITIES.register("catch_the_rainbow_rain_blade1",
            () -> EntityType.Builder.<CatchTheRainbowRainBladeEntity1>of(CatchTheRainbowRainBladeEntity1::new, EntityClassification.MISC)
            .sized(0.5F, 0.5F).noSave().setUpdateInterval(10)
            .build(AddonMain.MOD_ID + ":catch_the_rainbow_rain_blade1"));
    public static final RegistryObject<EntityType<CatchTheRainbowRainBladeEntity2>> CTR_RAIN_BLADE2 = ENTITIES.register("catch_the_rainbow_rain_blade2",
            () -> EntityType.Builder.<CatchTheRainbowRainBladeEntity2>of(CatchTheRainbowRainBladeEntity2::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).noSave().setUpdateInterval(10)
                    .build(AddonMain.MOD_ID + ":catch_the_rainbow_rain_blade2"));
    public static final RegistryObject<EntityType<CatchTheRainbowRainBladeEntity3>> CTR_RAIN_BLADE3 = ENTITIES.register("catch_the_rainbow_rain_blade3",
            () -> EntityType.Builder.<CatchTheRainbowRainBladeEntity3>of(CatchTheRainbowRainBladeEntity3::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).noSave().setUpdateInterval(10)
                    .build(AddonMain.MOD_ID + ":catch_the_rainbow_rain_blade3"));
}
