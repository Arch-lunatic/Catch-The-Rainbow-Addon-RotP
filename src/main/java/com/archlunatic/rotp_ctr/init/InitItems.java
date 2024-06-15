package com.archlunatic.rotp_ctr.init;

import com.archlunatic.rotp_ctr.AddonMain;
//import com.archlunatic.rotp_ctr.client.render.item.umbrella.UmbrellaISTER;
import com.archlunatic.rotp_ctr.item.CatchTheRainbowMaskItem;
import com.archlunatic.rotp_ctr.item.CtRRevolverItem;
import com.archlunatic.rotp_ctr.item.UmbrellaItem;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.github.standobyte.jojo.init.ModItems.MAIN_TAB;

@Mod.EventBusSubscriber(modid = AddonMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AddonMain.MOD_ID);

//    public static final RegistryObject<Item> RAIN_BLADE = ITEMS.register("ctr_rain_blade",
//            () -> new Item(new Item.Properties()));

    public static final RegistryObject<CatchTheRainbowMaskItem> CATCH_THE_RAINBOW_MASK = ITEMS.register("catch_the_rainbow_mask_item",
            ()->new CatchTheRainbowMaskItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<UmbrellaItem> UMBRELLA = ITEMS.register("umbrella",
            ()->new UmbrellaItem(new Item.Properties().tab(MAIN_TAB).defaultDurability(444)
                    ));//.stacksTo(1).setISTER(() -> UmbrellaISTER::new)

    public static final RegistryObject<CtRRevolverItem> REVOLVER = ITEMS.register("revolver",
            () -> new CtRRevolverItem(new Item.Properties().tab(MAIN_TAB).stacksTo(1)));
}
