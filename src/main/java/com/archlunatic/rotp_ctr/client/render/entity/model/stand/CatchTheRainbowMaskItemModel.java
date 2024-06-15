package com.archlunatic.rotp_ctr.client.render.entity.model.stand;

import com.github.standobyte.jojo.client.ClientUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

import java.util.Collections;

public class CatchTheRainbowMaskItemModel extends BipedModel<LivingEntity> {
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;

    public CatchTheRainbowMaskItemModel(float size) {
        super(size);

        head.setTexSize(texWidth, texHeight);
        ClientUtil.clearCubes(head);
        texWidth = 32;
        texHeight = 32;

        head = new ModelRenderer(this);
        head.setPos(0.0F, 24.0F, 0.0F);
        head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.1F, false);
        head.texOffs(12, 20).addBox(-1.9F, -2.8F, -4.4F, 1.0F, 1.0F, 1.0F, -0.2F, false);
        head.texOffs(4, 23).addBox(-0.5F, -2.8F, -4.4F, 1.0F, 1.0F, 1.0F, -0.2F, false);
        head.texOffs(4, 20).addBox(-0.5F, -2.0F, -4.4F, 1.0F, 2.0F, 1.0F, -0.2F, false);
        head.texOffs(0, 23).addBox(0.9F, -2.8F, -4.4F, 1.0F, 1.0F, 1.0F, -0.2F, false);
        head.texOffs(11, 17).addBox(-4.3F, -5.85F, -4.3F, 4.0F, 2.0F, 1.0F, 0.1F, true);
        head.texOffs(11, 22).addBox(0.3F, -5.85F, -4.3F, 4.0F, 2.0F, 1.0F, 0.1F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(2.5F, -2.5F, -4.2F);
        head.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0F, 0.3927F);
        cube_r1.texOffs(0, 16).addBox(-0.3F, -0.35F, 0.0F, 2.0F, 3.0F, 1.0F, 0.1F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(-2.5F, -2.5F, -4.2F);
        head.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.0F, -0.3927F);
        cube_r2.texOffs(5, 16).addBox(-1.7F, -0.35F, 0.0F, 2.0F, 3.0F, 1.0F, 0.1F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(1.4F, -1.8F, -3.9F);
        head.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 0.0F, 0.3927F);
        cube_r3.texOffs(0, 20).addBox(-0.5F, -0.2F, -0.5F, 1.0F, 2.0F, 1.0F, -0.2F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(-1.4F, -1.8F, -3.9F);
        head.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 0.0F, -0.3927F);
        cube_r4.texOffs(8, 20).addBox(-0.5F, -0.2F, -0.5F, 1.0F, 2.0F, 1.0F, -0.2F, false);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return Collections.emptyList();
    }

    @Override
    public void renderToBuffer(MatrixStack pMatrixStack, IVertexBuilder pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        if (ClientUtil.canSeeStands()) {
            super.renderToBuffer(pMatrixStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        }
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}

