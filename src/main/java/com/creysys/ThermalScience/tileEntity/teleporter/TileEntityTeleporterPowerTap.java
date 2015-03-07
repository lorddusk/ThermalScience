package com.creysys.ThermalScience.tileEntity.teleporter;

import cofh.api.energy.IEnergyReceiver;
import com.creysys.ThermalScience.block.teleporter.BlockTeleporterPowerTap;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Creysys on 06 Mar 15.
 */
public class TileEntityTeleporterPowerTap extends TileEntity implements IEnergyReceiver {

    public byte facing;

    public TileEntityTeleporterPowerTap(){
        facing = 0;
    }

    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection forgeDirection) {
        return 0;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection forgeDirection) {
        return 0;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection forgeDirection) {
        return BlockTeleporterPowerTap.faceMap[facing] == forgeDirection.ordinal();
    }
}
