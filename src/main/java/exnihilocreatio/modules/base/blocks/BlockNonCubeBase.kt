package exnihilocreatio.modules.base.blocks

import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.world.IBlockReader
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

abstract class BlockNonCubeBase(name: String, mat: Material): BlockBase(name, mat) {
    override fun getCollisionShape(state: IBlockState, worldIn: IBlockReader, pos: BlockPos): VoxelShape {
        return state.getShape(worldIn, pos)
    }

    override fun isBlockNormalCube(state: IBlockState): Boolean {
        return false
    }

    override fun isNormalCube(state: IBlockState): Boolean {
        return false
    }

    override fun isFullCube(state: IBlockState?): Boolean {
        return false
    }

    override fun isTopSolid(state: IBlockState): Boolean {
        return false
    }

    override fun isNormalCube(state: IBlockState, world: IBlockReader?, pos: BlockPos?): Boolean {
        return false
    }

    override fun getOpacity(state: IBlockState, worldIn: IBlockReader, pos: BlockPos): Int {
        return 0
    }

    override fun getRenderLayer(): BlockRenderLayer {
        return BlockRenderLayer.CUTOUT_MIPPED
    }
}