package com.archlunatic.rotp_ctr.client.render.entity.renderer.damaging.projectile;

import com.archlunatic.rotp_ctr.AddonMain;
import com.archlunatic.rotp_ctr.client.render.entity.model.projectile.CatchTheRainbowRainBladeModel3;
import com.archlunatic.rotp_ctr.entity.damaging.projectile.CatchTheRainbowRainBladeEntity3;
import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class CatchTheRainbowRainBladeRenderer3 extends SimpleEntityRenderer<CatchTheRainbowRainBladeEntity3, CatchTheRainbowRainBladeModel3> {

    public CatchTheRainbowRainBladeRenderer3(EntityRendererManager renderManager) {
        super(renderManager, new CatchTheRainbowRainBladeModel3(), new ResourceLocation(AddonMain.MOD_ID, "textures/entity/projectiles/catch_the_rainbow_rain_blade.png"));
    }
}