package com.creysys.ThermalScience.block.teleporter;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.client.gui.ThermalScienceGuiID;
import com.creysys.ThermalScience.tileEntity.teleporter.TileEntityTeleporterController;
import com.creysys.ThermalScience.util.DXYZ;
import com.creysys.ThermalScience.util.ThermalScienceUtil;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
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
public class BlockTeleporterController extends BlockContainer {

    public static IIcon iconOff;
    public static IIcon iconOn;

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
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityTeleporterController();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if(world.isRemote){
            return true;
        }

        if(player.isSneaking() && player.getHeldItem() != null && ThermalScienceUtil.isItemWrench(player.getHeldItem().getItem())){
            ThermalScienceUtil.wrenchBlock(world,player,x,y,z);
        }
        else if(world.getTileEntity(x,y,z) instanceof TileEntityTeleporterController){
            FMLNetworkHandler.openGui(player, ThermalScience.instance, ThermalScienceGuiID.TeleporterController.ordinal(), world, x, y, z);
        }

        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        ThermalScienceUtil.dropBlockContents(world, x, y, z);

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        iconOff = iconRegister.registerIcon(ThermalScienceTextures.teleporterControllerOff.icon);
        iconOn = iconRegister.registerIcon(ThermalScienceTextures.teleporterControllerOn.icon);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int var6 = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        TileEntityTeleporterController tileEntity = (TileEntityTeleporterController) world.getTileEntity(x, y, z);

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
            return iconOff;
        }

        return ThermalScience.blockTeleporterWall.getIcon(side, meta);
    }

    @Override
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
        TileEntityTeleporterController controller = (TileEntityTeleporterController) blockAccess.getTileEntity(x, y, z);

        if (side == controller.facing) {
            return controller.active ? iconOn : iconOff;
        }

        return ThermalScience.blockTeleporterWall.getIcon(side, 0);
    }
}
