package com.archlunatic.rotp_ctr.util;

import com.archlunatic.rotp_ctr.AddonMain;
import com.archlunatic.rotp_ctr.init.InitEffects;
import com.archlunatic.rotp_ctr.init.InitItems;
import com.archlunatic.rotp_ctr.init.InitStands;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import static com.archlunatic.rotp_ctr.action.stand.CatchTheRainbowTempHealth.HEALTH_CHANGER_1;
import static com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity.isInRain;
import static com.archlunatic.rotp_ctr.item.UmbrellaItem.MODIFIER_GRAVITY;
import static com.archlunatic.rotp_ctr.item.UmbrellaItem.isHoldingUmbrellaUpright;


@Mod.EventBusSubscriber(modid = AddonMain.MOD_ID)
public class GameplayHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onEntityJoinWorld(EntityJoinWorldEvent event){
        if(!event.getEntity().level.isClientSide){
            Entity entity = event.getEntity();
            if(entity instanceof ItemEntity){
                ItemEntity entItem = (ItemEntity) entity;
                if(entItem.getItem().getItem() == InitItems.CATCH_THE_RAINBOW_MASK.get()){
                    entity.remove();
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;
        if(!player.level.isClientSide){
            ModifiableAttributeInstance gravity = player.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
            if (!(isHoldingUmbrellaUpright(player, Hand.MAIN_HAND) || isHoldingUmbrellaUpright(player, Hand.OFF_HAND))) {
                if (gravity != null) {
                    gravity.removeModifier(MODIFIER_GRAVITY);
                }
            }
            if (!isInRain(player)) {
                if (!player.isCreative()) {
                    player.removeEffect(InitEffects.RAIN_MERGE.get());
                    player.removeEffect(InitEffects.RAIN_REDEMPTION.get());
                    Objects.requireNonNull(player).removeEffect(Effects.ABSORPTION);
                    player.getAttribute(Attributes.MAX_HEALTH).removeModifier(HEALTH_CHANGER_1);
                }
            }
            if (player.hasEffect(InitEffects.RAIN_REDEMPTION.get())){
                IStandPower.getStandPowerOptional(player).ifPresent(
                        standPower -> {standPower.getResolveCounter().addResolveValue(standPower.getMaxResolve(), player);});
            }

            IStandPower.getStandPowerOptional(player).ifPresent(
                standPower -> {
                    StandType<?> emp = InitStands.STAND_CATCH_THE_RAINBOW.getStandType();
                    if(standPower.getType()!=emp){
                        empInv(player);
                    }else if (standPower.getStandManifestation() instanceof StandEntity){
                        if(player.getItemBySlot(EquipmentSlotType.HEAD).getItem() != InitItems.CATCH_THE_RAINBOW_MASK.get()){
                            ItemStack head = player.getItemBySlot(EquipmentSlotType.HEAD);
                            if(!head.isEmpty()){
                                ItemEntity ent = new ItemEntity(player.level,player.getX(),player.getY(),player.getZ(),head);
                                player.level.addFreshEntity(ent);
                            }

                            ItemStack itemStack = new ItemStack(InitItems.CATCH_THE_RAINBOW_MASK.get(),1);
                            player.setItemSlot(EquipmentSlotType.HEAD, itemStack);
                            oneEmp(player);
                        }
                        dupEmp(player);
                    }else {
                        empInv(player);
                    }
                }
            );
        }
    }


    private static void empInv(PlayerEntity player){
        for (int i = 0; i < player.inventory.getContainerSize(); ++i) {
            ItemStack inventoryStack = player.inventory.getItem(i);
            if (inventoryStack.getItem() == InitItems.CATCH_THE_RAINBOW_MASK.get()) {
                inventoryStack.shrink(inventoryStack.getCount());
            }
        }
    }
    private static void oneEmp(PlayerEntity player) {
        for (int i = 0; i < 39; ++i){
            ItemStack inventoryStack = player.inventory.getItem(i);
            if (inventoryStack.getItem() == InitItems.CATCH_THE_RAINBOW_MASK.get()) {
                inventoryStack.shrink(inventoryStack.getCount());
            }
        }
    }
    private static void dupEmp(PlayerEntity player){
        int count = 0;
        ArrayList<Integer> places = new ArrayList<>();
        for (int i = 0; i < player.inventory.getContainerSize(); ++i) {
            ItemStack inventoryStack = player.inventory.getItem(i);
            if (inventoryStack.getItem() == InitItems.CATCH_THE_RAINBOW_MASK.get()) {
                ++count;
                places.add((Integer) i);
            }
            if(count>1){
                for (Integer in:places) {
                    int pl = (int) in;
                    ItemStack dupliEmp = player.inventory.getItem(pl);
                    dupliEmp.shrink(1);
                }
            }
        }
    }
}
