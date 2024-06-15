package com.archlunatic.rotp_ctr.client;

import com.archlunatic.rotp_ctr.AddonMain;
import com.archlunatic.rotp_ctr.client.render.entity.model.stand.CatchTheRainbowMaskItemModel;
import com.archlunatic.rotp_ctr.client.render.entity.renderer.damaging.projectile.CatchTheRainbowRainBladeRenderer1;
import com.archlunatic.rotp_ctr.client.render.entity.renderer.damaging.projectile.CatchTheRainbowRainBladeRenderer2;
import com.archlunatic.rotp_ctr.client.render.entity.renderer.damaging.projectile.CatchTheRainbowRainBladeRenderer3;
import com.archlunatic.rotp_ctr.client.render.entity.renderer.stand.CatchTheRainbowRenderer;
//import com.archlunatic.rotp_ctr.client.render.item.generic.ItemISTERModelWrapper;
import com.archlunatic.rotp_ctr.init.InitEntities;

import com.archlunatic.rotp_ctr.init.InitItems;
import com.archlunatic.rotp_ctr.init.InitStands;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.render.armor.ArmorModelRegistry;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = AddonMain.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {

    private static final IItemPropertyGetter STAND_ITEM_INVISIBLE = (CustomModelArmorItem, clientWorld, livingEntity) -> {//itemStack
        return !ClientUtil.canSeeStands() ? 1 : 0;
    };

    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(InitStands.STAND_CATCH_THE_RAINBOW.getEntityType(), CatchTheRainbowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.CTR_RAIN_BLADE1.get(), CatchTheRainbowRainBladeRenderer1::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.CTR_RAIN_BLADE2.get(), CatchTheRainbowRainBladeRenderer2::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.CTR_RAIN_BLADE3.get(), CatchTheRainbowRainBladeRenderer3::new);

        event.enqueueWork(() -> {
            ItemModelsProperties.register(InitItems.CATCH_THE_RAINBOW_MASK.get(),
                    new ResourceLocation(AddonMain.MOD_ID, "stand_invisible"),
                    STAND_ITEM_INVISIBLE);
//            ItemModelsProperties.register(InitItems.UMBRELLA.get(),
//                    new ResourceLocation(AddonMain.MOD_ID, "is_held"),
//                    (itemStack, clientWorld, livingEntity) -> livingEntity != null && (livingEntity.getItemInHand(Hand.MAIN_HAND) == itemStack || livingEntity.getItemInHand(Hand.OFF_HAND) == itemStack) ? 1 : 0);
        });
        ArmorModelRegistry.registerArmorModel(CatchTheRainbowMaskItemModel::new, InitItems.CATCH_THE_RAINBOW_MASK.get());
    }

//    @SubscribeEvent
//    public static void onModelBaked(ModelBakeEvent event){
//        registerCustomBakedModel(InitItems.UMBRELLA.get().getRegistryName(), event.getModelRegistry(),
//                model -> new ItemISTERModelWrapper(model).setCaptureEntity());
//    }

}
