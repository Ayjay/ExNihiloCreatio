package exnihilocreatio.items.tools;

import com.google.common.collect.Sets;
import exnihilocreatio.registries.manager.ExNihiloRegistryManager;
import exnihilocreatio.util.Data;
import exnihilocreatio.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import javax.annotation.Nonnull;

public class CrookBase extends ItemTool implements ICrook, IHasModel {

    int miningLevel;

    public CrookBase(String name, int maxUses) {
        super(ToolMaterial.WOOD, Sets.newHashSet(new Block[]{}));

        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setMaxDamage(maxUses);

        Data.ITEMS.add(this);
    }

    @Override
    public boolean isCrook(ItemStack stack) {
        return true;
    }


    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, IBlockState state) {
        return ExNihiloRegistryManager.CROOK_REGISTRY.isRegistered(state.getBlock()) ? this.efficiency : 1.0F;
    }
}
