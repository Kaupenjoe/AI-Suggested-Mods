package net.kaupenjoe.gptmod.entity.custom;

import net.kaupenjoe.gptmod.GPTMod;
import net.kaupenjoe.gptmod.entity.ai.DroneMoveControl;
import net.kaupenjoe.gptmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;

public class DroneEntity extends FlyingMob implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private BlockPos savedPosition;

    public DroneEntity(EntityType<? extends FlyingMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new DroneMoveControl(this);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new DroneMoveToBlockGoal(this, 1d));
        this.goalSelector.addGoal(3, new DroneHarvestGoal(this));
        // this.goalSelector.addGoal(5, new RandomFloatAroundGoal(this));
    }

    @Override
    @SuppressWarnings(value = "all")
    protected InteractionResult mobInteract(Player player, @NotNull InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if(itemstack.getItem() == ModItems.DRONE_REMOTE) {
            if(itemstack.hasTag()) {
                savedPosition = NbtUtils.readBlockPos((CompoundTag) itemstack.getTag().get("remote.position")).above();
                GPTMod.LOGGER.info("Successfully read in data " + savedPosition);
            }
        }

        return super.mobInteract(player, interactionHand);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.drone.fly", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.drone.idle", true));
        return PlayState.CONTINUE;
    }


    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    static class DroneMoveToBlockGoal extends Goal {
        protected final DroneEntity drone;
        public final double speedModifier;

        public DroneMoveToBlockGoal(DroneEntity pathfinderMob, double d) {
            this.drone = pathfinderMob;
            this.speedModifier = d;
        }

        @Override
        public void start() {
            drone.moveControl.setWantedPosition(drone.savedPosition.getX() + 0.5d, drone.savedPosition.getY() + 0.5d,
                    drone.savedPosition.getZ() + 0.5d, this.speedModifier);
        }

        @Override
        public void tick() {
            super.tick();
            drone.moveControl.setWantedPosition(drone.savedPosition.getX() + 0.5d, drone.savedPosition.getY() + 0.5d,
                    drone.savedPosition.getZ() + 0.5d, this.speedModifier);
        }

        @Override
        public boolean canUse() {
            return drone.savedPosition != null;
        }
    }

    static class DroneHarvestGoal extends Goal {
        private DroneEntity drone;
        private int counter = 0;

        public DroneHarvestGoal(DroneEntity drone) {
            this.drone = drone;
        }

        @Override
        public void tick() {
            super.tick();

            // Movement towards the
            if(!drone.blockPosition().equals(drone.savedPosition)) {
                GPTMod.LOGGER.info(drone.blockPosition() + " != " + drone.savedPosition);
            } else {
                if(counter >= 100) { // every 5 seconds
                    if(isCropBelowOfAge()) {
                        harvestCropBlock();
                    }
                    counter = 0;
                } else {
                    counter++;
                }
            }
        }

        private void harvestCropBlock() {
            if(!drone.getLevel().isClientSide()) {
                drone.getLevel().destroyBlock(this.drone.savedPosition.below(), true, this.drone);
            }
        }

        private boolean isCropBelowOfAge() {
            GPTMod.LOGGER.info("Checking of Age!");
            BlockState blockState = drone.getLevel().getBlockState(this.drone.savedPosition.below());
            if(blockState.getBlock() instanceof CropBlock cropBlock) {
                return blockState.getValue(CropBlock.AGE).equals(cropBlock.getMaxAge());
            } else {
                return false;
            }
        }

        @Override
        public boolean canUse() {
            return this.drone.savedPosition != null;
        }
    }
}
