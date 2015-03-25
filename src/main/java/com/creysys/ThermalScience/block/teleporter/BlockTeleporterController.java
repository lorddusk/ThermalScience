package com.creysys.ThermalScience.block.teleporter;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceNBTTags;
import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.client.gui.IItemTooltipProvider;
import com.creysys.ThermalScience.ThermalScienceGuiID;
import com.creysys.ThermalScience.tileEntity.teleporter.TileEntityTeleporterController;
import com.creysys.ThermalScience.util.DXYZ;
import com.creysys.ThermalScience.util.IWrenchable;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by Creysys on 06 Mar 15.
 */
public class BlockTeleporterController extends BlockContainer implements IWrenchable, IItemTooltipProvider {

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
        if (world.isRemote) {
            return true;
        }

        if (player.isSneaking() && player.getHeldItem() != null && ThermalScienceUtil.isItemWrench(player.getHeldItem().getItem())) {
            ThermalScienceUtil.wrenchBlock(world, player, x, y, z, this);
        } else if (world.getTileEntity(x, y, z) instanceof TileEntityTeleporterController) {
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

        if(stack.hasTagCompound() && stack.getTagCompound().hasKey(ThermalScienceNBTTags.Id)){
            tileEntity.controllerId = stack.getTagCompound().getInteger(ThermalScienceNBTTags.Id);
        }
        else if(!world.isRemote){
            tileEntity.controllerId = TileEntityTeleporterController.addController(new DXYZ(world.provider.dimensionId, x, y, z));
        }
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

    @Override
    public ItemStack onWrenched(ItemStack stack, TileEntity tileEntity) {

        if(tileEntity instanceof TileEntityTeleporterController){
            TileEntityTeleporterController controller = (TileEntityTeleporterController)tileEntity;

            if(controller.controllerId != -1) {
                NBTTagCompound compound;
                if (stack.hasTagCompound()) {
                    compound = stack.getTagCompound();
                } else {
                    compound = new NBTTagCompound();
                }

                compound.setInteger(ThermalScienceNBTTags.Id, controller.controllerId);

                stack.setTagCompound(compound);
            }
        }

        return stack;
    }

    @Override
    public void addTooltip(List<String> list, ItemStack stack) {
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey(ThermalScienceNBTTags.Id)) {
            list.add("Controller Id: " + stack.getTagCompound().getInteger(ThermalScienceNBTTags.Id));
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        TileEntityTeleporterController controller = (TileEntityTeleporterController)world.getTileEntity(x,y,z);

        if(controller != null){
            controller.checkMultiblock();
        }
    }
}
