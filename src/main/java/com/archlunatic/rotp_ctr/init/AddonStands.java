package com.archlunatic.rotp_ctr.init;

import com.archlunatic.rotp_ctr.entity.stand.stands.CatchTheRainbowEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject.EntityStandSupplier;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.stats.TimeStopperStandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;

public class AddonStands {

    public static final EntityStandSupplier<EntityStandType<TimeStopperStandStats>, StandEntityType<CatchTheRainbowEntity>> 
    CATCHTHERAINBOW = new EntityStandSupplier<>(InitStands.STAND_CATCHTHERAINBOW);
}