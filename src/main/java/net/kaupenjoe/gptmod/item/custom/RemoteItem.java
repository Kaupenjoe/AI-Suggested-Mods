package net.kaupenjoe.gptmod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class RemoteItem extends Item {
    public RemoteItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        addNbtToRemote(useOnContext.getPlayer(), useOnContext.getClickedPos(), useOnContext.getHand());
        return super.useOn(useOnContext);
    }

    private void addNbtToRemote(Player player, BlockPos pos, InteractionHand hand) {
        CompoundTag nbtData = new CompoundTag();
        nbtData.put("remote.position", NbtUtils.writeBlockPos(pos));
        nbtData.putString("remote.position.text", "At (" +
                pos.getX() + ", "+ pos.getY() + ", "+ pos.getZ() + ")");

        player.getItemInHand(hand).setTag(nbtData);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return pStack.hasTag();
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents,
                                TooltipFlag pIsAdvanced) {
        if(pStack.hasTag()) {
            String currentPos = pStack.getTag().getString("remote.position.text");
            pTooltipComponents.add(Component.literal(currentPos));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
