package exnihilocreatio.handlers;

import exnihilocreatio.items.tools.IHammer;
import exnihilocreatio.registries.manager.ExNihiloRegistryManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class HandlerHammer {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void hammer(BlockEvent.HarvestDropsEvent event) {
        if (event.getWorld().isRemote || event.getHarvester() == null || event.isSilkTouching())
            return;

        ItemStack held = event.getHarvester().getHeldItemMainhand();

        if (!isHammer(held))
            return;

        List<ItemStack> rewards = ExNihiloRegistryManager.HAMMER_REGISTRY.getRewardDrops(event.getWorld().rand, event.getState(), ((IHammer) held.getItem()).getMiningLevel(held), event.getFortuneLevel());

        if (rewards != null && rewards.size() > 0) {
            event.getDrops().clear();
            event.setDropChance(1.0F);
            event.getDrops().addAll(rewards);
        }
    }


    public boolean isHammer(ItemStack stack) {
        return stack != null && stack.getItem() != Items.AIR && stack.getItem() instanceof IHammer && ((IHammer) stack.getItem()).isHammer(stack);

    }

}
