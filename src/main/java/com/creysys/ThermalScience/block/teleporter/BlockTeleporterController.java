package com.creysys.ThermalScience.block.teleporter;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.tileEntity.teleporter.TileEntityTeleporterController;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Created by Creysys on 06 Mar 15.
 */
public class BlockTeleporterController extends BlockContainer {

    public IIcon icon;
    public int[] faceMap;

    public BlockTeleporterController() {
        super(Material.iron);

        setHardness(1F);
        setResistance(1F);
        setHarvestLevel("pickaxe", 1);

        String blockName = "blockTeleporterController";
        setBlockName(blockName);
        setCreativeTab(ThermalScience.creativeTab);

        GameRegistry.registerBlock(this, blockName);
        GameRegistry.registerTileEntity(TileEntityTeleporterController.class, "tileEntityTeleporterController");

        faceMap = new int[]{3, 2, 4, 5};
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityTeleporterController();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if(!world.isRemote && world.getTileEntity(x,y,z) instanceof TileEntityTeleporterController){
            TileEntityTeleporterController teleporterController = (TileEntityTeleporterController)world.getTileEntity(x,y,z);
            teleporterController.checkMultiblock();
        }

        return true;
    }


    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        icon = iconRegister.registerIcon(ThermalScience.modid + ":teleporter/controller");
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {

        if(world.isRemote){
            return;
        }

        int var6 = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        TileEntityTeleporterController tileEntity = (TileEntityTeleporterController) world.getTileEntity(x, y, z);

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
