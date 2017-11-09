package exnihilocreatio.tiles;

import exnihilocreatio.ModBlocks;
import exnihilocreatio.blocks.BlockInfestedLeaves;
import exnihilocreatio.config.ModConfig;
import exnihilocreatio.texturing.Color;
import exnihilocreatio.util.Util;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

public class TileInfestedLeaves extends BaseTileEntity implements ITickable {
    private static int tileId = 0;

    @Getter
    private float progress = 0;
    @Getter
    private boolean hasNearbyLeaves = true;
    @Getter
    private IBlockState leafBlock = Blocks.LEAVES.getDefaultState();

    // Stop ALL infested leaves from updating on the same tick always - this way they're evenly spread out and not causing a spike in tick time every time they update
    // Let's hope no one gets 2 billion in their server
    private int updateIndex = tileId++ % ModConfig.infested_leaves.leavesUpdateFrequency;

    @Override
    public void update() {
        if (progress < 1.0F) {
            progress += 1.0 / ModConfig.infested_leaves.ticksToTransform;

            markDirtyClient();

            if (progress > 1.0F) {
                progress = 1.0F;

                getWorld().notifyBlockUpdate(pos, getWorld().getBlockState(pos), getWorld().getBlockState(pos), 3);
            }
        }

        // Don't update unless there's leaves nearby, or we haven't checked for leavesUpdateFrequency ticks. And only update on the server
        if (!getWorld().isRemote && hasNearbyLeaves || getWorld().getTotalWorldTime() % ModConfig.infested_leaves.leavesUpdateFrequency == updateIndex) {
            hasNearbyLeaves = false;

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        BlockPos newPos = new BlockPos(pos.add(x, y, z));
                        IBlockState state = getWorld().getBlockState(newPos);

                        if (state != Blocks.AIR.getDefaultState() && state.getBlock() != Blocks.AIR && state.getBlock() != ModBlocks.infestedLeaves) {
                            ItemStack itemStack = new ItemStack(state.getBlock());
                            if (OreDictionary.getOres("treeLeaves").stream().anyMatch(stack1 -> Util.compareItemStack(stack1, itemStack))) {
                                hasNearbyLeaves = true;

                                if (getWorld().rand.nextFloat() < ModConfig.infested_leaves.leavesSpreadChance) {
                                    BlockInfestedLeaves.infestLeafBlock(getWorld(), newPos);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 128 * 128;
    }

    @SideOnly(Side.CLIENT)
    public int getColor() {
        if (pos == null) {
            return Util.whiteColor.toInt();
        } else {
            Color green = new Color(BiomeColorHelper.getFoliageColorAtPos(getWorld(), pos));
            return Color.average(green, Util.whiteColor, (float) Math.pow(progress, 2)).toInt();
        }
    }

    public void setProgress(float newProgress) {
        progress = newProgress;
        markDirty();
    }

    public void setLeafBlock(IBlockState block) {
        leafBlock = block;
        markDirty();
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setFloat("progress", progress);
        tag.setString("leafBlock", leafBlock.getBlock().getRegistryName() == null ? "" : leafBlock.getBlock().getRegistryName().toString());
        tag.setInteger("leafBlockMeta", leafBlock.getBlock().getMetaFromState(leafBlock));
        return super.writeToNBT(tag);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void readFromNBT(NBTTagCompound tag) {
        progress = tag.getFloat("progress");

        if (tag.hasKey("leafBlock") && tag.hasKey("leafBlockMeta")) {
            try {
                leafBlock = Block.getBlockFromName(tag.getString("leafBlock")).getStateFromMeta(tag.getInteger("leafBlockMeta"));
            } catch (Exception e) {
                leafBlock = Blocks.LEAVES.getDefaultState();
            }
        } else {
            leafBlock = Blocks.LEAVES.getDefaultState();
        }

        super.readFromNBT(tag);
    }
}
