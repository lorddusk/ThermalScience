package com.creysys.ThermalScience.block;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceUtil;
import com.creysys.ThermalScience.gui.ThermalScienceGuiID;
import com.creysys.ThermalScience.tileEntity.TileEntityEnergyRelay;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by Creysys on 13 Feb 15.
 */
public class BlockEnergyRelay extends BlockContainer {

    public IIcon iconOff;
    public IIcon iconIn;
    public IIcon iconOut;


    public BlockEnergyRelay() {
        super(Material.rock);

        setHardness(1F);
        setResistance(1F);
        setHarvestLevel("pickaxe", 1);

        String blockName = "blockEnergyRelay";
        setBlockName(blockName);
        setCreativeTab(ThermalScience.creativeTab);

        GameRegistry.registerBlock(this, blockName);
        GameRegistry.registerTileEntity(TileEntityEnergyRelay.class, "tileEntityEnergyRelay");
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityEnergyRelay();
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        iconOff = iconRegister.registerIcon(ThermalScience.MODID.toLowerCase() + ":energyRelayOff");
        iconIn = iconRegister.registerIcon(ThermalScience.MODID.toLowerCase() + ":energyRelayIn");
        iconOut = iconRegister.registerIcon(ThermalScience.MODID.toLowerCase() + ":energyRelayOut");
    }


    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return iconOff;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (player.isSneaking()) {
            ItemStack heldItemStack = player.getHeldItem();
            if (heldItemStack == null) {
                TileEntity tileEntity = world.getTileEntity(x, y, z);
                if (tileEntity instanceof TileEntityEnergyRelay) {
                    if(!world.isRemote) {
                        TileEntityEnergyRelay energyRelay = (TileEntityEnergyRelay) tileEntity;
                        energyRelay.setSideConfig(side);
                    }
                    return true;
                }
                else {
                    return false;
                }
            } else if (ThermalScienceUtil.isItemWrench(heldItemStack.getItem())) {
                if (!world.isRemote) {
                    ThermalScienceUtil.wrenchBlock(world, x, y, z);
                }
                return true;
            } else {
                return false;
            }
        }

        if(!world.isRemote) {
            FMLNetworkHandler.openGui(player, ThermalScience.instance, ThermalScienceGuiID.EnergyRelay.ordinal(), world, x, y, z);
        }
        return true;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }
}
