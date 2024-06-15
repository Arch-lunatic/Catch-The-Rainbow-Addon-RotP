package com.archlunatic.rotp_ctr.client.render.entity.renderer.stand;

import com.archlunatic.rotp_ctr.AddonMain;
import com.archlunatic.rotp_ctr.client.render.entity.model.stand.CatchTheRainbowModel;
import com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class CatchTheRainbowRenderer extends StandEntityRenderer<CatchTheRainbowEntity, CatchTheRainbowModel> {
    
    public CatchTheRainbowRenderer(EntityRendererManager renderManager) {
        super(renderManager, new CatchTheRainbowModel(), new ResourceLocation(AddonMain.MOD_ID, "textures/entity/stand/catch_the_rainbow.png"), 0);
    }
    @Override
    protected void idlePoseSwaying(CatchTheRainbowEntity entity, float ticks, MatrixStack matrixStack) {}
}
