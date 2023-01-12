package net.kaupenjoe.gptmod.entity.ai;

import net.kaupenjoe.gptmod.GPTMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class DroneMoveToBlockGoal extends Goal {
    protected final FlyingMob mob;
    public final double speedModifier;
    protected int tryTicks;
    protected BlockPos blockPos = BlockPos.ZERO;
    protected int verticalSearchStart;
    private BlockPos position;
    private boolean reachedTarget;

    public DroneMoveToBlockGoal(FlyingMob pathfinderMob, double d, int i, BlockPos savedPos) {
        this(pathfinderMob, d, i, 1, savedPos);
    }

    public DroneMoveToBlockGoal(FlyingMob pathfinderMob, double d, int i, int j, BlockPos savedPos) {
        this.mob = pathfinderMob;
        this.speedModifier = d;
        this.verticalSearchStart = 0;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.position = savedPos;
        this.reachedTarget = false;
    }

    @Override
    public boolean canUse() {
        return position != null;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if(position == null) {
            GPTMod.LOGGER.info("Not even ticking!!");
            GPTMod.LOGGER.info("reached " + reachedTarget);
            GPTMod.LOGGER.info("position " + position);
            GPTMod.LOGGER.info("closerToCenterThan " + position.closerToCenterThan(this.mob.position(), 16));
            return;
        }

        if (!position.closerToCenterThan(this.mob.position(), 16)) {
            this.reachedTarget = false;
            ++this.tryTicks;
            if (this.shouldRecalculatePath()) {
                this.mob.getNavigation().moveTo(position.getX(), position.getY(), position.getZ(), this.speedModifier);
                GPTMod.LOGGER.info("Moving towards Block!");
            }
        } else {
            this.reachedTarget = true;
            --this.tryTicks;
        }
    }

    public boolean shouldRecalculatePath() {
        return this.tryTicks % 40 == 0;
    }
}