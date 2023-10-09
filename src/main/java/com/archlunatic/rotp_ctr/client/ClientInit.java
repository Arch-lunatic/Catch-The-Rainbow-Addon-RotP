package com.archlunatic.rotp_ctr.client;

import com.archlunatic.rotp_ctr.RotpCtrAddon;
import com.archlunatic.rotp_ctr.client.render.entity.renderer.stand.CatchTheRainbowRenderer;
import com.archlunatic.rotp_ctr.init.AddonStands;
import com.archlunatic.rotp_ctr.init.InitEntities;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = RotpCtrAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {
    
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(AddonStands.CATCHTHERAINBOW.getEntityType(), CatchTheRainbowRenderer::new);
    }
}
