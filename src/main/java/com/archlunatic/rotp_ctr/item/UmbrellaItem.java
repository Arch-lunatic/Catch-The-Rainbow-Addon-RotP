package com.archlunatic.rotp_ctr.item;

import com.archlunatic.rotp_ctr.AddonMain;
import com.archlunatic.rotp_ctr.init.InitSounds;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class UmbrellaItem extends Item {
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;
    public static final UUID MODIFIER_GRAVITY = UUID.fromString("e00c3955-0634-44a4-9bfa-071fb74914e3");


    public UmbrellaItem(Properties properties) {
        super(properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 0.0, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -3.2, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
        PlayerEntity player = (PlayerEntity) entityIn;
        ModifiableAttributeInstance gravity = player.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
//        if (player.getItemInHand(Hand.MAIN_HAND) == stack || player.getItemInHand(Hand.OFF_HAND) == stack) {
        if (isHoldingUmbrellaUpright(player, Hand.MAIN_HAND) || isHoldingUmbrellaUpright(player, Hand.OFF_HAND)) {
            if (gravity != null) {
                if (player.isOnGround()) {
                    gravity.removeModifier(MODIFIER_GRAVITY);
                }else if (!player.isOnGround() && !player.isInWater() && player.getEntity().getDeltaMovement().y < 0 && !player.hasEffect(Effects.SLOW_FALLING)){
                    if (!(player.isUsingItem() && !player.getUseItem().isEmpty())) {// && player.getUseItem().getItem().getUseAnimation(player.getUseItem()) == UseAction.BLOCK
                        gravity.removeModifier(MODIFIER_GRAVITY);
                        gravity.addTransientModifier(new AttributeModifier(
                                MODIFIER_GRAVITY, "Gravity", -0.875, AttributeModifier.Operation.MULTIPLY_TOTAL));
                    }
                }
                player.fallDistance = 0;
            }
            if(isUmbrellaInRain(player)){
                if (ticksCount == 10) {
                    player.playSound(InitSounds.UMBRELLA_FALLING_RAIN_DROPS.get(), 1.0F, 1.0F);
                    tCount(1);
                } else {
                    tCount(ticksCount() + 1);
                }
            }
        }else{
            if (gravity != null) {
                gravity.removeModifier(MODIFIER_GRAVITY);
            }
        }
        boolean isVampire = INonStandPower.getNonStandPowerOptional(player)
                .map(power -> power.getType() == ModPowers.VAMPIRISM.get())
                .orElse(false);
        boolean isInSun = (0 < player.level.getDayTime() && player.level.getDayTime() < 12542)
                && player.level.canSeeSky(player.blockPosition())
                && !player.level.isRaining() && !player.level.isThundering();
        if(isInSun && isVampire) {
            player.addEffect(new EffectInstance(ModStatusEffects.SUN_RESISTANCE.get(), 10, 9, false, false, false));
            if (player.getItemInHand(Hand.MAIN_HAND) == stack) {
                stack.hurtAndBreak(111, player, e -> e.broadcastBreakEvent(Hand.MAIN_HAND));
            }
            if (player.getItemInHand(Hand.OFF_HAND) == stack) {
                stack.hurtAndBreak(111, player, e -> e.broadcastBreakEvent(Hand.OFF_HAND));
            }
        }

    }

    public int ticksCount;
    public int ticksCount(){return ticksCount;}
    public void tCount(int ticksCount) {this.ticksCount = ticksCount;}

//    @Override
//    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
//        ItemStack handStack = player.getItemInHand(hand);
//        if (!world.isClientSide()) {
//            world.playSound(null, player.blockPosition(), InitSounds.CATCH_THE_RAINBOW_SUMMON.get(), SoundCategory.PLAYERS, 1, 1);//сделать звук с каплями на зонт
//            player.getCooldowns().addCooldown(this, 2);
//
////            if (!player.abilities.instabuild) {
////                handStack.shrink(1);
////            }
//        }
//        return ActionResult.success(handStack);
//    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (entity) -> {
            entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

//    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
//        return slot == EquipmentSlotType.MAINHAND && stack.getCount() == 1 ? this.attributeModifiers : super.getAttributeModifiers(slot, stack);
//    }

//    @Override
//    public void onUseTick(World world, LivingEntity entity, ItemStack stack, int remainingTicks) {
//        if(!world.isClientSide()){
//            world.playSound(null, entity.blockPosition(), InitSounds.CATCH_THE_RAINBOW_SUMMON.get(), SoundCategory.PLAYERS, 1, 1);
//            entity.releaseUsingItem();
//        }
//    }

//    @Override
//    public void releaseUsing(ItemStack stack, World world, LivingEntity entity, int remainingTicks) {
//        if(entity instanceof PlayerEntity){
//            PlayerEntity player = (PlayerEntity) entity;
//            if(player.isShiftKeyDown()){
//                player.getCooldowns().addCooldown(this,65);
//            }else {
//                player.getCooldowns().addCooldown(this,10);
//            }
//        }
//    }

//    @Override
//    public UseAction getUseAnimation(ItemStack stack) {
//        return UseAction.CROSSBOW;
//    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public static boolean isUmbrellaInRain(Entity entity) {
        BlockPos blockPos = entity.blockPosition();
        return entity.level.isRainingAt(blockPos) || entity.level.isRainingAt(new BlockPos(blockPos.getX(), entity.getBoundingBox().maxY, blockPos.getZ()));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
//        tooltip.add(new TranslationTextComponent("item.rotp_ctr.revolver.reload_prompt",
//                new KeybindTextComponent("key.sneak"), new KeybindTextComponent("key.use")).withStyle(TextFormatting.GRAY));
        ClientUtil.addItemReferenceQuote(tooltip, this);
    }

    public static boolean isHoldingUmbrellaUpright(LivingEntity entity, Hand hand) {
        return entity.getItemInHand(hand).getItem() instanceof UmbrellaItem && (!entity.isUsingItem() || entity.getUsedItemHand() != hand);
    }

    public static boolean isHoldingUmbrellaUpright(LivingEntity entity) {
        return isHoldingUmbrellaUpright(entity, Hand.MAIN_HAND) || isHoldingUmbrellaUpright(entity, Hand.OFF_HAND);
    }

    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = AddonMain.MOD_ID)
    public static class ClientEvents {

        @SubscribeEvent
        public static void onLivingRender(RenderLivingEvent.Pre<?, ?> event) {
            if (!(event.getRenderer().getModel() instanceof BipedModel)) {
                return;
            }

            LivingEntity entity = event.getEntity();
            BipedModel<?> model = (BipedModel<?>) event.getRenderer().getModel();

            boolean isHoldingOffHand = isHoldingUmbrellaUpright(entity, Hand.OFF_HAND);
            boolean isHoldingMainHand = isHoldingUmbrellaUpright(entity, Hand.MAIN_HAND);
            boolean isRightHanded = entity.getMainArm() == HandSide.RIGHT;

            if ((isHoldingMainHand && isRightHanded) || (isHoldingOffHand && !isRightHanded)) {
                model.rightArmPose = BipedModel.ArmPose.BOW_AND_ARROW;
//                model.rightArm.xRot /= 8;
            }
            if ((isHoldingMainHand && !isRightHanded) || (isHoldingOffHand && isRightHanded)) {
                model.leftArmPose = BipedModel.ArmPose.BOW_AND_ARROW;
//                model.leftArm.xRot /= 8;
//                model.leftArm = BipedModel.ArmPose.BOW_AND_ARROW;//todo своя анимация держания зонта
            }
        }
    }
}
