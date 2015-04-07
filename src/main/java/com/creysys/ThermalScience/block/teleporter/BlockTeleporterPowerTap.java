package com.creysys.ThermalScience.block.teleporter;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.tileEntity.teleporter.TileEntityTeleporterPowerTap;
import com.creysys.ThermalScience.util.ThermalScienceUtil;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by Creysys on 06 Mar 15.
 */
public class BlockTeleporterPowerTap extends BlockContainer {
    public BlockTeleporterPowerTap() {
        super(Material.iron);

        setHardness(1F);
        setResistance(1F);
        setHarvestLevel("pickaxe", 1);

        String blockName = "blockTeleporterPowerTap";
        setBlockName(blockName);
        setCreativeTab(ThermalScience.creativeTab);
        setBlockTextureName(ThermalScienceTextures.teleporterPowerTap.icon);

        GameRegistry.registerBlock(this, blockName);
        GameRegistry.registerTileEntity(TileEntityTeleporterPowerTap.class, "tileEntityTeleporterPowerTap");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i1) {
        return new TileEntityTeleporterPowerTap();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int var6 = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        TileEntityTeleporterPowerTap tileEntity = (TileEntityTeleporterPowerTap) world.getTileEntity(x, y, z);

        byte facing = 3;

        switch (var6) {
            case 0:
                facing = 2;
                break;
            case 1:
                facing = 5;
                break;
            case 3:
                facing = 4;
                break;
        }

        tileEntity.facing = facing;
    }

    @Override
    public IIcon getIcon(int side, int meta) {

        if (side == 3) {
            return blockIcon;
        }

        return ThermalScience.blockTeleporterWall.getIcon(side, meta);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float f1, float f2, float f3) {
        if(world.isRemote){
            return true;
        }

        if(player.isSneaking() && player.getHeldItem() != null && ThermalScienceUtil.isItemWrench(player.getHeldItem().getItem())){
            ThermalScienceUtil.wrenchBlock(world,player,x,y,z);
        }

        return false;
    }

    @Override
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
        TileEntityTeleporterPowerTap powerTap = (TileEntityTeleporterPowerTap) blockAccess.getTileEntity(x, y, z);

        if (side == powerTap.facing) {
            return blockIcon;
        }

        return ThermalScience.blockTeleporterWall.getIcon(side, 0);
    }
}
