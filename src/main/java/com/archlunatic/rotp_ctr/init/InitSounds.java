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
    
    public static final Supplier<SoundEvent> CATCHTHERAINBOW_SUMMON = ModSounds.STAND_SUMMON_DEFAULT;
    
    public static final Supplier<SoundEvent> CATCHTHERAINBOW_UNSUMMON = ModSounds.STAND_UNSUMMON_DEFAULT;
    
    public static final Supplier<SoundEvent> CATCHTHERAINBOW_PUNCH_LIGHT = ModSounds.STAND_PUNCH_LIGHT;
    
    public static final Supplier<SoundEvent> CATCHTHERAINBOW_PUNCH_HEAVY = ModSounds.STAND_PUNCH_HEAVY;
    
    public static final Supplier<SoundEvent> CATCHTHERAINBOW_BARRAGE = ModSounds.STAND_PUNCH_LIGHT;
	
    static final OstSoundList CATCHTHERAINBOW_OST = new OstSoundList(new ResourceLocation(RotpCtrAddon.MOD_ID, "catch_the_rainbow_ost"), SOUNDS);

}
