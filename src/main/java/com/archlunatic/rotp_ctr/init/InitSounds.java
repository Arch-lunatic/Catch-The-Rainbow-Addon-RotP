package com.archlunatic.rotp_ctr.init;

import java.util.function.Supplier;

import com.archlunatic.rotp_ctr.RotpCtrAddon;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.util.mc.OstSoundList;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RotpCtrAddon.MOD_ID);

    public static final RegistryObject<SoundEvent> CATCHTHERAINBOW_SUMMON = SOUNDS.register("catch_the_rainbow_summon",
            () -> new SoundEvent(new ResourceLocation(RotpCtrAddon.MOD_ID, "catch_the_rainbow_summon")));

    public static final RegistryObject<SoundEvent> CATCHTHERAINBOW_HEAL = SOUNDS.register("rain_heal",
            () -> new SoundEvent(new ResourceLocation(RotpCtrAddon.MOD_ID, "rain_heal")));

    public static final RegistryObject<SoundEvent> CATCHTHERAINBOW_BLADE = SOUNDS.register("throwing_blade",
            () -> new SoundEvent(new ResourceLocation(RotpCtrAddon.MOD_ID, "throwing_blade")));

    public static final RegistryObject<SoundEvent> CATCHTHERAINBOW_UNSUMMON = SOUNDS.register("catch_the_rainbow_unsummon",
            () -> new SoundEvent(new ResourceLocation(RotpCtrAddon.MOD_ID, "catch_the_rainbow_unsummon")));


	
    static final OstSoundList CATCHTHERAINBOW_OST = new OstSoundList(new ResourceLocation(RotpCtrAddon.MOD_ID, "catch_the_rainbow_ost"), SOUNDS);
}
