package net.kaupenjoe.gptmod.entity.ai;

import net.kaupenjoe.gptmod.GPTMod;
import net.kaupenjoe.gptmod.entity.custom.DroneEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DroneHarvestGoal extends Goal {
    private DroneEntity drone;
    private BlockPos pos;
    private int counter = 0;

    public DroneHarvestGoal(DroneEntity drone, BlockPos position) {
        this.drone = drone;
        this.pos = position;
    }

    @Override
    public void tick() {
        super.tick();

        if(counter <= 100) { // every 5 seconds
            if(isCropBelowOfAge()) {
                harvestCropBlock();
            }
            counter = 0;
        }
    }

    private void harvestCropBlock() {
        GPTMod.LOGGER.info("HARVESTING YES!");
    }

    private boolean isCropBelowOfAge() {
        GPTMod.LOGGER.info("Checking of Age!");
        BlockState blockState = drone.getLevel().getBlockState(pos.below());
        if(blockState.getBlock() instanceof CropBlock cropBlock) {
            return blockState.getValue(CropBlock.AGE).equals(cropBlock.getMaxAge());
        } else {
            return false;
        }
    }

    @Override
    public boolean canUse() {
        return this.pos != null;
    }
}
