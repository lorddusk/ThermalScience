package com.creysys.ThermalScience.compat;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import com.creysys.ThermalScience.ThermalScience;
import cpw.mods.fml.common.registry.GameRegistry;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * Created by Creysys on 19 Feb 15.
 */
public class ThermalScienceWailaDataProvider implements IWailaDataProvider {

    public static void load(IWailaRegistrar registrar) {

        ThermalScienceWailaDataProvider dataProvider = new ThermalScienceWailaDataProvider();

        registrar.registerBodyProvider(dataProvider, IWailaBodyProvider.class);
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        Block block = accessor.getBlock();
        if(block == null){
            return list;
        }

        if(GameRegistry.findUniqueIdentifierFor(block).modId.equals(ThermalScience.MODID)) {
            TileEntity tileEntity = accessor.getTileEntity();
            if (tileEntity instanceof IWailaBodyProvider) {
                IWailaBodyProvider bodyProvider = (IWailaBodyProvider) tileEntity;
                list = bodyProvider.getWailaBody(itemStack, list, accessor, config);
            }
        }

        return list;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP entityPlayerMP, TileEntity tileEntity, NBTTagCompound nbtTagCompound, World world, int i, int i1, int i2) {
        return null;
    }
}
