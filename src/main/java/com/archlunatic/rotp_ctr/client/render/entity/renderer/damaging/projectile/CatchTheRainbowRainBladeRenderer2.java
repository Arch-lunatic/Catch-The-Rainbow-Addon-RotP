package com.archlunatic.rotp_ctr.client.render.entity.renderer.damaging.projectile;

import com.archlunatic.rotp_ctr.AddonMain;
import com.archlunatic.rotp_ctr.client.render.entity.model.projectile.CatchTheRainbowRainBladeModel2;
import com.archlunatic.rotp_ctr.entity.damaging.projectile.CatchTheRainbowRainBladeEntity2;
import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class CatchTheRainbowRainBladeRenderer2 extends SimpleEntityRenderer<CatchTheRainbowRainBladeEntity2, CatchTheRainbowRainBladeModel2> {

    public CatchTheRainbowRainBladeRenderer2(EntityRendererManager renderManager) {
        super(renderManager, new CatchTheRainbowRainBladeModel2(), new ResourceLocation(AddonMain.MOD_ID, "textures/entity/projectiles/catch_the_rainbow_rain_blade.png"));
    }
}