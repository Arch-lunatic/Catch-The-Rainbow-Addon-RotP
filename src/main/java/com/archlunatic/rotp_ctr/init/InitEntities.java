package com.archlunatic.rotp_ctr.init;

import com.archlunatic.rotp_ctr.RotpCtrAddon;
import com.archlunatic.rotp_ctr.entity.damaging.projectile.CtRainBladeEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, RotpCtrAddon.MOD_ID);

    public static final RegistryObject<EntityType<CtRainBladeEntity>> CTR_RAIN_BLADE = ENTITIES.register("ctr_rain_blade", () -> EntityType.Builder.<CtRainBladeEntity>of(CtRainBladeEntity::new, EntityClassification.MISC)
            .sized(0.5F, 0.5F).noSave().setUpdateInterval(10)
            .build(RotpCtrAddon.MOD_ID + ":ctr_rain_blade"));
}
