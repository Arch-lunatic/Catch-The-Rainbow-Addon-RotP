package com.archlunatic.rotp_ctr.client.render.entity.renderer.damaging.projectile;

import com.archlunatic.rotp_ctr.client.render.entity.model.projectile.CatchTheRainbowRainBladeModel1;
import com.archlunatic.rotp_ctr.entity.damaging.projectile.CatchTheRainbowRainBladeEntity1;
import com.archlunatic.rotp_ctr.AddonMain;

import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class CatchTheRainbowRainBladeRenderer1 extends SimpleEntityRenderer<CatchTheRainbowRainBladeEntity1, CatchTheRainbowRainBladeModel1> {

    public CatchTheRainbowRainBladeRenderer1(EntityRendererManager renderManager) {
        super(renderManager, new CatchTheRainbowRainBladeModel1(), new ResourceLocation(AddonMain.MOD_ID, "textures/entity/projectiles/catch_the_rainbow_rain_blade.png"));
    }
}