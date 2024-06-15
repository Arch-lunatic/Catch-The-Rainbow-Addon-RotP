package com.archlunatic.rotp_ctr.init;

import java.util.Set;

import com.archlunatic.rotp_ctr.AddonMain;
import com.archlunatic.rotp_ctr.potion.RainMerge;
import com.archlunatic.rotp_ctr.potion.RainRedemption;
import com.google.common.collect.ImmutableSet;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = AddonMain.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class InitEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, AddonMain.MOD_ID);

    public static RegistryObject<RainMerge> RAIN_MERGE = EFFECTS.register("rain_merge_effect",
            () -> new RainMerge(EffectType.BENEFICIAL, 0x80DEF7));

    public static RegistryObject<RainRedemption> RAIN_REDEMPTION = EFFECTS.register("rain_redemption_effect",
            () -> new RainRedemption(EffectType.BENEFICIAL, 0x80DEF7));

    private static Set<Effect> TRACKED_EFFECTS;
    @SubscribeEvent(priority = EventPriority.LOW)
    public static final void afterEffectsRegister(RegistryEvent.Register<Effect> event) {
        TRACKED_EFFECTS = ImmutableSet.of(RAIN_REDEMPTION.get());
    }

    public static boolean isEffectTracked(Effect effect) {
        return TRACKED_EFFECTS.contains(effect);
    }
}
