package com.archlunatic.rotp_ctr;

import com.archlunatic.rotp_ctr.init.*;
import com.github.standobyte.jojo.init.ModPotions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AddonMain.MOD_ID)
public class AddonMain {
//     The value here should match an entry in the META-INF/mods.toml file
    public static final String MOD_ID = "rotp_ctr";
    public static final Logger LOGGER = LogManager.getLogger();

    public AddonMain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);
        InitEffects.EFFECTS.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
    }

    private void registerVanillaDeferredRegisters(IEventBus modEventBus) {
        ModPotions.POTIONS.register(modEventBus);
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
