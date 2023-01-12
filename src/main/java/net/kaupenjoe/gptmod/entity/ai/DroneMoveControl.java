package net.kaupenjoe.gptmod.entity.ai;

import net.kaupenjoe.gptmod.entity.custom.DroneEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DroneMoveControl extends MoveControl {
    private final DroneEntity droneEntity;
    private int floatDuration;

    public DroneMoveControl(DroneEntity droneEntity) {
        super(droneEntity);
        this.droneEntity = droneEntity;
    }

    @Override
    public void tick() {
        if (this.operation != MoveControl.Operation.MOVE_TO) {
            return;
        }
        if (this.floatDuration-- <= 0) {
            this.floatDuration += this.droneEntity.getRandom().nextInt(5) + 2;
            Vec3 vec3 = new Vec3(this.wantedX - this.droneEntity.getX(), this.wantedY - this.droneEntity.getY(), this.wantedZ - this.droneEntity.getZ());
            double d = vec3.length();
            if (this.canReach(vec3 = vec3.normalize(), Mth.ceil(d))) {
                this.droneEntity.setDeltaMovement(this.droneEntity.getDeltaMovement().add(vec3.scale(0.1)));
            } else {
                this.operation = MoveControl.Operation.WAIT;
            }
        }
    }

    private boolean canReach(Vec3 vec3, int i) {
        AABB aABB = this.droneEntity.getBoundingBox();
        for (int j = 1; j < i; ++j) {
            if (this.droneEntity.level.noCollision(this.droneEntity, aABB = aABB.move(vec3))) continue;
            return false;
        }
        return true;
    }
}
