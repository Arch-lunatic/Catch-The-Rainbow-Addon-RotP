package com.archlunatic.rotp_ctr.item;

import com.github.standobyte.jojo.item.CustomModelArmorItem;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CatchTheRainbowMaskItem extends CustomModelArmorItem {
    protected String textureStr;

    public CatchTheRainbowMaskItem(Properties builder) {
        super(ModArmorMaterials.CATCH_THE_RAINBOW_MASK, EquipmentSlotType.HEAD, builder);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if (textureStr == null) {
            textureStr = createTexturePath(getRegistryName());
        }
        return textureStr;
    }

    protected String createTexturePath(ResourceLocation regName) {
        return regName.getNamespace() + ":textures/armor/" + regName.getPath() + ".png";
    }
}
