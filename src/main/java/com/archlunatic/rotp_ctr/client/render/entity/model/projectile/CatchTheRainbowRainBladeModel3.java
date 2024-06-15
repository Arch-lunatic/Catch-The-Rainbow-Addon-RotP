package com.archlunatic.rotp_ctr.client.render.entity.model.projectile;

import com.archlunatic.rotp_ctr.entity.damaging.projectile.CatchTheRainbowRainBladeEntity3;
import com.github.standobyte.jojo.client.render.CustomVerticesModelBox;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.Direction;

import static com.github.standobyte.jojo.client.ClientUtil.setRotationAngle;


public class CatchTheRainbowRainBladeModel3 extends EntityModel<CatchTheRainbowRainBladeEntity3> {

    private final ModelRenderer waterCutter;//Todo дождаться 3 модельки
    public CatchTheRainbowRainBladeModel3() {
        super(RenderType::entityTranslucent);
        ModelRenderer cutter;
        ModelRenderer water;

        texWidth = 16;
        texHeight = 16;

        waterCutter = new ModelRenderer(this);
        waterCutter.setPos(0.0F, -3.1F, 0.0F);

        cutter = new ModelRenderer(this);
        cutter.setPos(0.6F, -3.0F, -3.0F);
        waterCutter.addChild(cutter);
        setRotationAngle(cutter, -44.75F, 0.0F, 0.0F);
        cutter.texOffs(10, 2).addBox(-1.45F, -3.375F, -1.125F, 1.7F, 1.0F, 1.0F, -0.25F, false);
        cutter.texOffs(10, 2).addBox(-1.45F, -1.125F, 2.875F, 1.7F, 1.0F, 1.0F, -0.25F, false);
        cutter.texOffs(10, 2).addBox(-1.45F, -3.875F, -0.375F, 1.7F, 1.0F, 1.0F, -0.25F, false);
        cutter.texOffs(10, 2).addBox(-1.45F, -0.125F, 1.125F, 1.7F, 1.0F, 1.0F, -0.25F, false);
        cutter.texOffs(11, 2).addBox(-1.2F, 0.375F, -0.625F, 1.2F, 1.0F, 1.0F, 0.0F, false);
        cutter.texOffs(10, 2).addBox(-1.45F, -2.375F, 1.375F, 1.7F, 1.0F, 1.0F, -0.25F, false);
        cutter.texOffs(10, 0).addBox(-1.45F, 1.875F, -3.125F, 1.7F, 1.0F, 1.0F, -0.25F, false);
        cutter.texOffs(11, 2).addBox(-1.2F, -1.375F, 0.425F, 1.2F, 1.0F, 1.0F, 0.0F, false);
        cutter.texOffs(10, 0).addBox(-1.45F, -2.375F, -0.875F, 1.7F, 1.0F, 1.0F, -0.25F, false);
        cutter.texOffs(10, 2).addBox(-1.45F, -0.125F, -1.625F, 1.7F, 1.0F, 1.0F, -0.25F, false);
        cutter.texOffs(11, 0).addBox(-1.2F, -1.125F, 1.875F, 1.2F, 1.0F, 1.0F, 0.0F, false);
        cutter.texOffs(11, 0).addBox(-1.2F, -2.875F, 0.125F, 1.2F, 1.0F, 1.0F, 0.0F, false);
        cutter.texOffs(10, 2).addBox(-1.45F, -0.375F, 0.375F, 1.7F, 1.0F, 1.0F, -0.25F, false);
        cutter.texOffs(11, 0).addBox(-1.2F, -1.125F, -1.375F, 1.2F, 1.0F, 1.0F, 0.0F, false);
        cutter.texOffs(11, 0).addBox(-1.2F, 0.875F, -2.175F, 1.2F, 1.0F, 1.0F, 0.0F, false);

        water = new ModelRenderer(this);
        water.setPos(0.0F, 0.0F, 0.0F);
        waterCutter.addChild(water);
        new CustomVerticesModelBox.Builder(true)
                .withVertex(true,  true,  false,  0.5F,     3,     -7.85F)
                .withVertex(false, true,  false, -0.5F,     3,     -7.85F)
                .withVertex(true,  false, false,  0.5F, 0,     -0.1F)
                .withVertex(false, false, false, -0.5F, 0,     -0.1F)
                .withVertex(true,  true,   true,  0.5F,     6.8F,  0.15F)
                .withVertex(false, true,   true, -0.5F,     6.8F,  0.15F)
                .withVertex(true,  false,  true,  0.5F,     2.3F,  1.1F)
                .withVertex(false, false,  true, -0.5F,     2.3F,  1.1F)
                .withUvFace(Direction.UP,    0, 6,  16,  6)
                .withUvFace(Direction.DOWN,  0, 6,  16,  6)
                .withUvFace(Direction.EAST,  0, 6,  16,  6)
                .withUvFace(Direction.NORTH, 0, 6,  16,  6)
                .withUvFace(Direction.WEST,  0, 6,  16,  6)
                .withUvFace(Direction.SOUTH, 0, 6,  16,  6)
                .addCube(water, texWidth, texHeight, false);
    }

    @Override
    public void setupAnim(CatchTheRainbowRainBladeEntity3 entity, float walkAnimPos, float walkAnimSpeed, float ticks, float yRotationOffset, float xRotation) {
        waterCutter.yRot = yRotationOffset * ((float)Math.PI / 180F);
        waterCutter.xRot = xRotation * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        waterCutter.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}