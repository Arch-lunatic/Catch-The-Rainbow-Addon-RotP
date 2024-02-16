package com.archlunatic.rotp_ctr.power.impl.stand.stats;

import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import net.minecraft.network.PacketBuffer;

public class RainStopperStandStats extends StandStats {
    private final int rainStopMaxTicks;
    private final int rainStopMaxTicksVampire;
    public final float rainStopLearningPerTick;
//    public final float rainStopDecayPerDay;
    public final float rainStopCooldownPerTick;

    protected RainStopperStandStats(com.archlunatic.rotp_ctr.power.impl.stand.stats.RainStopperStandStats.Builder builder) {
        super(builder);
        this.rainStopMaxTicks = builder.rainStopMaxTicks;
        this.rainStopMaxTicksVampire = builder.rainStopMaxTicksVampire;
        this.rainStopLearningPerTick = builder.rainStopLearningPerTick;
//        this.rainStopDecayPerDay = builder.rainStopDecayPerDay;
        this.rainStopCooldownPerTick = builder.rainStopCooldownPerTick;
    }

    protected RainStopperStandStats(PacketBuffer buf) {
        super(buf);
        this.rainStopMaxTicks = buf.readInt();
        this.rainStopMaxTicksVampire = buf.readInt();
        this.rainStopLearningPerTick = buf.readFloat();
//        this.rainStopDecayPerDay = buf.readFloat();
        this.rainStopCooldownPerTick = buf.readFloat();
    }

    public int getMaxRainStopTicks (boolean vampire) {
        return vampire ? this.rainStopMaxTicksVampire : this.rainStopMaxTicks;
    }

    public void write(PacketBuffer buf) {
        super.write(buf);
        buf.writeInt(this.rainStopMaxTicks);
        buf.writeInt(this.rainStopMaxTicksVampire);
        buf.writeFloat(this.rainStopLearningPerTick);
//        buf.writeFloat(this.rainStopDecayPerDay);
        buf.writeFloat(this.rainStopCooldownPerTick);
    }

//    public void onNewDay(LivingEntity user, IStandPower power) {
//        if (!user.level.isClientSide()) {
//            LivingUtilCap cap = (LivingUtilCap)user.getCapability(LivingUtilCapProvider.CAPABILITY).resolve().get();
//            if (!cap.hasUsedTimeStopToday && this.rainStopDecayPerDay > 0.0F) {
//                power.getActions(ActionType.ABILITY).getAll().forEach((ability) -> {
//                    if (ability.isUnlocked(power) && ability instanceof RainStop) {
//                        power.setLearningProgressPoints(ability, power.getLearningProgressPoints(ability) - this.rainStopDecayPerDay, true, false);
//                    }
//
//                });
//            }
//
//            cap.hasUsedRainStopToday = false;
//        }
//
//    }

    static {
        registerFactory(com.archlunatic.rotp_ctr.power.impl.stand.stats.RainStopperStandStats.class, com.archlunatic.rotp_ctr.power.impl.stand.stats.RainStopperStandStats::new);
    }

    public static class Builder extends StandStats.AbstractBuilder<com.archlunatic.rotp_ctr.power.impl.stand.stats.RainStopperStandStats.Builder, com.archlunatic.rotp_ctr.power.impl.stand.stats.RainStopperStandStats> {
        private int rainStopMaxTicks = 24000;
        private int rainStopMaxTicksVampire = 99999;
        private float rainStopLearningPerTick = 1.0F;
//        private float rainStopDecayPerDay = 0.0F;
        private float rainStopCooldownPerTick = 0.0001F;

        public Builder() {
        }

        public com.archlunatic.rotp_ctr.power.impl.stand.stats.RainStopperStandStats.Builder rainStopMaxTicks(int forHuman, int forVampire) {
            forHuman = Math.max(5, forHuman);
            forVampire = Math.max(forHuman, forVampire);
            this.rainStopMaxTicks = forHuman;
            this.rainStopMaxTicksVampire = forVampire;
            return this.getThis();
        }

        public com.archlunatic.rotp_ctr.power.impl.stand.stats.RainStopperStandStats.Builder rainStopLearningPerTick(float points) {
            this.rainStopLearningPerTick = points;
            return this.getThis();
        }

//        public com.archlunatic.rotp_ctr.power.impl.stand.stats.RainStopperStandStats.Builder rainStopDecayPerDay(float points) {
//            this.rainStopDecayPerDay = points;
//            return this.getThis();
//        }

        public com.archlunatic.rotp_ctr.power.impl.stand.stats.RainStopperStandStats.Builder rainStopCooldownPerTick(float ticks) {
            this.rainStopCooldownPerTick = ticks;
            return this.getThis();
        }

        protected com.archlunatic.rotp_ctr.power.impl.stand.stats.RainStopperStandStats.Builder getThis() {
            return this;
        }

        protected com.archlunatic.rotp_ctr.power.impl.stand.stats.RainStopperStandStats createStats() {
            return new com.archlunatic.rotp_ctr.power.impl.stand.stats.RainStopperStandStats(this);
        }
    }
}
