package com.creysys.ThermalScience.block;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceUtil;
import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.tileEntity.TileEntityWatermill;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * Created by Creysys on 04 Feb 15.
 */
public class BlockWatermill extends BlockContainer {

    public BlockWatermill() {
        super(Material.rock);

        setHardness(1F);
        setResistance(1F);
        setHarvestLevel("pickaxe", 1);

        String blockName = "blockWatermill";
        setBlockName(blockName);
        setCreativeTab(ThermalScience.creativeTab);
        setBlockTextureName(ThermalScienceTextures.watermill.icon);

        GameRegistry.registerBlock(this, blockName);
        GameRegistry.registerTileEntity(TileEntityWatermill.class, "tileEntityWatermill");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityWatermill();
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        TileEntity tileEntity = world.getTileEntity(x,y,z);
        if(tileEntity instanceof TileEntityWatermill){
            ((TileEntityWatermill)tileEntity).checkWater();
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if(side > 1){
            return blockIcon;
        }

        return ThermalScience.blockCentrifuge.iconTop;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (player.isSneaking()) {
            ItemStack heldItemStack = player.getHeldItem();
            if (heldItemStack != null && ThermalScienceUtil.isItemWrench(heldItemStack.getItem())) {
                if (!world.isRemote) {
                    ThermalScienceUtil.wrenchBlock(world, x, y, z);
                }
                return true;
            }
        }

        return false;
    }
}
