package com.creysys.ThermalScience.gui;

import com.creysys.ThermalScience.container.*;
import com.creysys.ThermalScience.tileEntity.*;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Creysys on 29.01.2015.
 */
public class ThermalScienceGuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x,y,z);

        ThermalScienceGuiID id = ThermalScienceGuiID.values()[ID];

        switch(id)
        {
            case Compressor:
                if(tileEntity instanceof TileEntityCompressor)
                {
                    return new ContainerCompressor(player.inventory, (TileEntityCompressor)tileEntity);
                }
                break;
            case Centrifuge:
                if(tileEntity instanceof TileEntityCentrifuge)
                {
                    return new ContainerCentrifuge(player.inventory, (TileEntityCentrifuge)tileEntity);
                }
                break;
            case CarbothermicFurnace:
                if(tileEntity instanceof TileEntityCarbothermicFurnace)
                {
                    return new ContainerCarbothermicFurnace(player.inventory, (TileEntityCarbothermicFurnace)tileEntity);
                }
                break;
            case Wiremill:
                if(tileEntity instanceof TileEntityWiremill)
                {
                    return new ContainerWiremill(player.inventory, (TileEntityWiremill)tileEntity);
                }
                break;
            case EnergyRelay:
                if(tileEntity instanceof TileEntityEnergyRelay) {
                    return new ContainerFake();
                }
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(x,y,z);

        ThermalScienceGuiID id = ThermalScienceGuiID.values()[ID];

        switch(id)
        {
            case Compressor:
                if(tileEntity instanceof TileEntityCompressor)
                {
                    return new GuiCompressor(player.inventory, (TileEntityCompressor)tileEntity);
                }
                break;
            case Centrifuge:
                if(tileEntity instanceof TileEntityCentrifuge)
                {
                    return new GuiCentrifuge(player.inventory, (TileEntityCentrifuge)tileEntity);
                }
                break;
            case CarbothermicFurnace:
                if(tileEntity instanceof TileEntityCarbothermicFurnace)
                {
                    return new GuiCarbothermicFurnace(player.inventory, (TileEntityCarbothermicFurnace)tileEntity);
                }
                break;
            case Wiremill:
                if(tileEntity instanceof TileEntityWiremill)
                {
                    return new GuiWiremill(player.inventory, (TileEntityWiremill)tileEntity);
                }
                break;
            case EnergyRelay:
                if(tileEntity instanceof TileEntityEnergyRelay) {
                    return new GuiEnergyRelay((TileEntityEnergyRelay)tileEntity);
                }
                break;
        }

        return null;
    }
}
