package exnihilocreatio.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

@AllArgsConstructor
public class ItemInfo {

    @Getter
    private Item item;
    @Getter
    @Setter
    private int meta;

    public ItemInfo(ItemStack stack) {
        item = stack == null ? null : stack.getItem();
        meta = stack == null ? -1 : stack.getMetadata();
    }

    public ItemInfo(Block block, int blockMeta) {
        item = Item.getItemFromBlock(block);
        meta = block == Blocks.AIR ? -1 : blockMeta;
    }

    public ItemInfo(String string) {
        String[] split = string.split(":");

        switch (split.length) {
            case 1:
                item = Item.getByNameOrId("minecraft:" + split[0]);
                break;
            case 2:
                try {
                    meta = split[1].equals("*") ? -1 : Integer.parseInt(split[1]);
                    item = Item.getByNameOrId("minecraft:" + split[0]);
                } catch (NumberFormatException e) {
                    meta = -1;
                    item = Item.getByNameOrId(split[0] + ":" + split[1]);
                }
                break;
            case 3:
                try {
                    meta = split[2].equals("*") ? -1 : Integer.parseInt(split[2]);
                    item = Item.getByNameOrId(split[0] + ":" + split[1]);
                } catch (NumberFormatException e) {
                    meta = -1;
                }
                break;
            default:
                meta = -1;
                break;
        }
    }

    public ItemInfo(IBlockState state) {
        item = state == null ? null : Item.getItemFromBlock(state.getBlock());
        meta = state == null ? -1 : state.getBlock().getMetaFromState(state);
    }

    public static ItemInfo getItemInfoFromStack(ItemStack stack) {
        return new ItemInfo(stack);
    }

    public static ItemInfo readFromNBT(NBTTagCompound tag) {
        Item item_ = Item.REGISTRY.getObject(new ResourceLocation(tag.getString("item")));
        int meta_ = tag.getInteger("meta");

        return new ItemInfo(item_, meta_);
    }

    public String toString() {
        return Item.REGISTRY.getNameForObject(item) + (meta == -1 ? "" : (":" + meta));
    }

    public ItemStack getItemStack() {
        return item == null ? null : new ItemStack(item, 1, meta == -1 ? 0 : meta);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setString("item", Item.REGISTRY.getNameForObject(item) == null ? "" : Item.REGISTRY.getNameForObject(item).toString());
        tag.setInteger("meta", meta);

        return tag;
    }

    public boolean isValid() {
        return meta <= Short.MAX_VALUE && item != null;
    }

    public int hashCode() {
        return item == null ? 41 : item.hashCode();
    }

    public boolean equals(Object other) {
        if (other instanceof ItemInfo) {
            ItemInfo info = (ItemInfo) other;

            if (item == null || info.item == null) {
                return false;
            }

            if (meta == -1 || info.meta == -1) {
                return item.equals(info.item);
            } else {
                return meta == info.meta && item.equals(info.item);
            }
        }

        return false;
    }
}
