package com.archlunatic.rotp_ctr.client.render.entity.renderer.damaging.projectile;

import com.archlunatic.rotp_ctr.client.render.entity.model.projectile.CtRainBladeModel;
import com.archlunatic.rotp_ctr.entity.damaging.projectile.CtRainBladeEntity;
import com.archlunatic.rotp_ctr.RotpCtrAddon;

import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class CtRainBladeRenderer extends SimpleEntityRenderer<CtRainBladeEntity, CtRainBladeModel> {

    public CtRainBladeRenderer(EntityRendererManager renderManager) {
        super(renderManager, new CtRainBladeModel(), new ResourceLocation(RotpCtrAddon.MOD_ID, "textures/entity/projectiles/ctr_rain_blade.png"));
    }

}