package com.archlunatic.rotp_ctr.entity.stand.stands;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import com.github.standobyte.jojo.entity.stand.StandEntityType;

import net.minecraft.world.World;

public class CatchTheRainbowEntity extends StandEntity {
    
    public CatchTheRainbowEntity(StandEntityType<CatchTheRainbowEntity> type, World world) {
        super(type, world);
    }
    private StandRelativeOffset offsetDefault = StandRelativeOffset.withYOffset(0, 0, 0);
	public StandRelativeOffset unsummonOffset = offsetDefault.copy();

	public StandRelativeOffset getDefaultOffsetFromUser() {
        return offsetDefault;
    }
    private static final float SUMMON_ANIMATION_LENGTH = 0.0F;
    private static final float SUMMON_ANIMATION_POSE_REVERSE_POINT = 0.0F;
}
