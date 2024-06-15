package com.archlunatic.rotp_ctr.init;

import com.archlunatic.rotp_ctr.AddonMain;
import com.github.standobyte.jojo.util.mc.OstSoundList;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, AddonMain.MOD_ID);

    public static final RegistryObject<SoundEvent> CATCH_THE_RAINBOW_SUMMON = SOUNDS.register("catch_the_rainbow_summon",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "catch_the_rainbow_summon")));

    public static final RegistryObject<SoundEvent> CATCH_THE_RAINBOW_MERGE = SOUNDS.register("catch_the_rainbow_rain_merge",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "catch_the_rainbow_rain_merge")));

    public static final RegistryObject<SoundEvent> CATCH_THE_RAINBOW_HEAL = SOUNDS.register("catch_the_rainbow_rain_heal",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "catch_the_rainbow_rain_heal")));

    public static final RegistryObject<SoundEvent> CATCH_THE_RAINBOW_RAIN_BLINK = SOUNDS.register("catch_the_rainbow_rain_blink",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "catch_the_rainbow_rain_blink")));

    public static final RegistryObject<SoundEvent> CATCH_THE_RAINBOW_RAIN_BLADE = SOUNDS.register("catch_the_rainbow_throwing_blade",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "catch_the_rainbow_throwing_blade")));

    public static final RegistryObject<SoundEvent> CATCH_THE_RAINBOW_RAIN_STEP = SOUNDS.register("catch_the_rainbow_rain_step",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "catch_the_rainbow_rain_step")));

    public static final RegistryObject<SoundEvent> CATCH_THE_RAINBOW_UNSUMMON = SOUNDS.register("catch_the_rainbow_unsummon",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "catch_the_rainbow_unsummon")));

    public static final RegistryObject<SoundEvent> UMBRELLA_FALLING_RAIN_DROPS = SOUNDS.register("umbrella_falling_rain_drops",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "umbrella_falling_rain_drops")));

    public static final RegistryObject<SoundEvent> REVOLVER_SHOT = SOUNDS.register("revolver_shot",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "revolver_shot")));

    public static final RegistryObject<SoundEvent> REVOLVER_NO_AMMO = SOUNDS.register("revolver_no_ammo",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "revolver_no_ammo")));

    public static final RegistryObject<SoundEvent> REVOLVER_RELOAD = SOUNDS.register("revolver_reload",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "revolver_reload")));

    static final OstSoundList CATCH_THE_RAINBOW_OST = new OstSoundList(
            new ResourceLocation(AddonMain.MOD_ID, "catch_the_rainbow_ost"), SOUNDS);
}
