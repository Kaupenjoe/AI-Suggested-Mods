package net.kaupenjoe.gptmod.entity.ai;

import net.kaupenjoe.gptmod.entity.custom.DroneEntity;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class RandomFloatAroundGoal
        extends Goal {
    private final DroneEntity droneEntity;

    public RandomFloatAroundGoal(DroneEntity droneEntity) {
        this.droneEntity = droneEntity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        double f;
        double e;
        MoveControl moveControl = this.droneEntity.getMoveControl();
        if (!moveControl.hasWanted()) {
            return true;
        }
        double d = moveControl.getWantedX() - this.droneEntity.getX();
        double g = d * d + (e = moveControl.getWantedY() - this.droneEntity.getY()) * e + (f = moveControl.getWantedZ() - this.droneEntity.getZ()) * f;
        return g < 1.0 || g > 3600.0;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        RandomSource randomSource = this.droneEntity.getRandom();
        double d = this.droneEntity.getX() + (double)((randomSource.nextFloat() * 2.0f - 1.0f) * 16.0f);
        double e = this.droneEntity.getY() + (double)((randomSource.nextFloat() * 2.0f - 1.0f) * 16.0f);
        double f = this.droneEntity.getZ() + (double)((randomSource.nextFloat() * 2.0f - 1.0f) * 16.0f);
        this.droneEntity.getMoveControl().setWantedPosition(d, e, f, 1.0);
    }
}
