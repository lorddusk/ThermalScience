package com.creysys.ThermalScience.block.teleporter;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.tileEntity.TileEntityMachine;
import com.creysys.ThermalScience.tileEntity.teleporter.TileEntityTeleporterController;
import com.creysys.ThermalScience.tileEntity.teleporter.TileEntityTeleporterPowerTap;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Created by Creysys on 06 Mar 15.
 */
public class BlockTeleporterPowerTap extends BlockContainer {

    public static final int[] faceMap = new int[]{3, 2, 4, 5};

    public IIcon icon;


    public BlockTeleporterPowerTap() {
        super(Material.iron);

        setHardness(1F);
        setResistance(1F);
        setHarvestLevel("pickaxe", 1);

        String blockName = "blockTeleporterPowerTap";
        setBlockName(blockName);
        setCreativeTab(ThermalScience.creativeTab);

        GameRegistry.registerBlock(this, blockName);
        GameRegistry.registerTileEntity(TileEntityTeleporterController.class, "tileEntityTeleporterPowerTap");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i1) {
        return new TileEntityTeleporterPowerTap();
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        icon = iconRegister.registerIcon(ThermalScience.modid + ":teleporter/powerTap");
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int var6 = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        TileEntityTeleporterPowerTap tileEntity = (TileEntityTeleporterPowerTap) world.getTileEntity(x, y, z);

        byte facing = 0;

        switch (var6) {
            case 0:
                facing = 1;
                break;
            case 1:
                facing = 3;
                break;
            case 3:
                facing = 2;
                break;
        }

        tileEntity.facing = facing;
        world.setBlockMetadataWithNotify(x, y, z, facing, 2);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if(meta >= 0 && meta < faceMap.length &&  faceMap[meta] == side){
            return icon;
        }

        return ThermalScience.blockTeleporterWall.icon;
    }
}
