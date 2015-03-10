package com.creysys.ThermalScience.block;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.util.ThermalScienceUtil;
import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.client.gui.IItemTooltipProvider;
import com.creysys.ThermalScience.client.gui.ThermalScienceGuiID;
import com.creysys.ThermalScience.item.ItemBlockMeta;
import com.creysys.ThermalScience.tileEntity.TileEntityEnergyRelay;
import com.creysys.ThermalScience.tileEntity.TileEntityMachine;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Creysys on 13 Feb 15.
 */
public class BlockEnergyRelay extends BlockContainer implements IItemTooltipProvider{

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

        GameRegistry.registerBlock(this, ItemBlockMeta.class,blockName);
        GameRegistry.registerTileEntity(TileEntityEnergyRelay.class, "tileEntityEnergyRelay");
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityEnergyRelay();
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        iconOff = iconRegister.registerIcon(ThermalScienceTextures.energyRelayOff.icon);
        iconIn = iconRegister.registerIcon(ThermalScienceTextures.energyRelayIn.icon);
        iconOut = iconRegister.registerIcon(ThermalScienceTextures.energyRelayOut.icon);
    }


    @Override
    public IIcon getIcon(int side, int meta) {
        return iconOff;
    }

    @Override
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
        TileEntityEnergyRelay energyRelay = (TileEntityEnergyRelay)blockAccess.getTileEntity(x,y,z);

        switch(energyRelay.sideConfigs[side])
        {
            case 1:
                return iconIn;
            case 2:
                return iconOut;
        }

        return iconOff;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        world.setBlockMetadataWithNotify(x, y, z, stack.getItemDamage(), 2);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {

        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (!(tileEntity instanceof TileEntityEnergyRelay)) {
            return false;
        }

        TileEntityEnergyRelay energyRelay = (TileEntityEnergyRelay) tileEntity;


        if (player.getHeldItem() == null) {
            if (player.isSneaking()) {
                if (!world.isRemote) {
                    energyRelay.setSideConfig(side);
                }
            }
            else {
                if (!world.isRemote) {
                    FMLNetworkHandler.openGui(player, ThermalScience.instance, ThermalScienceGuiID.EnergyRelay.ordinal(), world, x, y, z);
                }
            }
        } else if (ThermalScienceUtil.isItemWrench(player.getHeldItem().getItem())) {
            if (player.isSneaking()) {
                if (!world.isRemote) {
                    ThermalScienceUtil.wrenchBlock(world, x, y, z);
                }
            } else {
                if (!world.isRemote) {
                    energyRelay.setSideConfig(side);
                }
            }
        } else {
            if (!world.isRemote) {
                FMLNetworkHandler.openGui(player, ThermalScience.instance, ThermalScienceGuiID.EnergyRelay.ordinal(), world, x, y, z);
            }
        }

        return true;
    }

    @Override
    public void addTooltip(List<String> list, ItemStack stack) {
        list.add("Tier: " + TileEntityMachine.mapTiers[stack.getItemDamage()]);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int i =0;i<4;i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public int damageDropped(int meta) {
        return super.damageDropped(meta);
    }
}
