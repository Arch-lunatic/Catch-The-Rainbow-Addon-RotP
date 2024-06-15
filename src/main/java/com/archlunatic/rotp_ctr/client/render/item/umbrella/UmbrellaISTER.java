//package com.archlunatic.rotp_ctr.client.render.item.umbrella;
//
//import com.archlunatic.rotp_ctr.AddonMain;
//import com.archlunatic.rotp_ctr.client.render.item.generic.CustomModelItemISTER;
//import com.archlunatic.rotp_ctr.init.InitItems;
//import com.github.standobyte.jojo.client.ClientUtil;
//import com.github.standobyte.jojo.client.render.item.RoadRollerItemModel;
//import com.mojang.blaze3d.matrix.MatrixStack;
//
//import com.mojang.blaze3d.vertex.IVertexBuilder;
//import net.minecraft.client.renderer.IRenderTypeBuffer;
//import net.minecraft.client.renderer.ItemRenderer;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.model.ItemCameraTransforms;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.Hand;
//import net.minecraft.util.ResourceLocation;
//
//public class UmbrellaISTER extends CustomModelItemISTER<UmbrellaItemModel> {
//    private final UmbrellaItemModel umbrellaItemModel = new UmbrellaItemModel();
//
//    public UmbrellaISTER() {
//        super(
//                new ResourceLocation(AddonMain.MOD_ID, "umbrella_opened"),
//                new ResourceLocation(AddonMain.MOD_ID, "textures/item/umbrella.png"),
//                InitItems.UMBRELLA,
//                UmbrellaItemModel::new);
//    }
//
//    @Override
//    public void renderByItem(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, int overlay) {
//        if (!(entity == ClientUtil.getClientPlayer())) {
//            super.renderByItem(
//                    itemStack,
//                    transformType,
//                    matrixStack,
//                    renderTypeBuffer,
//                    light,
//                    overlay);
//        }
//    }
//
//    @Override
//    protected void doRender(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, int overlay) {
////        boolean isHeld = entity != null && (entity.getItemInHand(Hand.MAIN_HAND) == itemStack || entity.getItemInHand(Hand.OFF_HAND) == itemStack);
////        model.setAnim(isHeld);
//        super.doRender(itemStack, transformType, matrixStack, renderTypeBuffer, light, overlay);
//        if (itemStack.getItem() == InitItems.UMBRELLA.get()) {
//            matrixStack.pushPose();
//            matrixStack.scale(1.0F, -1.0F, -1.0F);
//            IVertexBuilder vertexBuilder = ItemRenderer.getFoilBufferDirect(
//                    renderTypeBuffer, RenderType.armorCutoutNoCull(new ResourceLocation(AddonMain.MOD_ID, "textures/item/umbrella.png")), false, itemStack.hasFoil());
//            umbrellaItemModel.renderToBuffer(matrixStack, vertexBuilder, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
//            matrixStack.popPose();
//        }
//    }
//
//}