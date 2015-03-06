package com.creysys.ThermalScience.compat.computercraft.peripheral;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.network.packet.PacketEnergyRelaySettings;
import com.creysys.ThermalScience.tileEntity.TileEntityEnergyRelay;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;

/**
 * Created by Creysys on 05 Mar 15.
 */
public class PeripheralEnergyRelay implements IPeripheral {

    String[] methods = new String[]{"listMethods","setMaxIn", "setMaxOut", "getEnergyStored", "getMaxEnergyStored"};

    TileEntityEnergyRelay energyRelay;

    public PeripheralEnergyRelay(TileEntityEnergyRelay energyRelay){
        this.energyRelay = energyRelay;
    }


    @Override
    public String getType() {
        return "EnergyRelay";
    }

    @Override
    public String[] getMethodNames() {
        return methods;
    }

    @Override
    public Object[] callMethod(IComputerAccess iComputerAccess, ILuaContext iLuaContext, int method, Object[] objects) throws LuaException, InterruptedException {
        switch (method) {
            case 0:
                //listMethods
                String s = "";
                for (int i = 0; i < methods.length; i++) {
                    if (i != 0) {
                        s += ", ";
                    }
                    s += methods[i];
                }
                return new Object[]{s};
            case 1:
                //setMaxIn
                if (objects.length < 1) {
                    return new Object[]{"Too less arguments!"};
                }

                if (objects[0] instanceof Double) {
                    ThermalScience.packetHandler.sendPacketToServer(new PacketEnergyRelaySettings(energyRelay.xCoord, energyRelay.yCoord, energyRelay.zCoord, ((Double) objects[0]).intValue(), energyRelay.maxOut, energyRelay.sideConfigs));
                } else {
                    return new Object[]{"Argument is not a number!"};
                }
                break;
            case 2:
                //setMaxOut
                if (objects.length < 1) {
                    return new Object[]{"Too less arguments!"};
                }

                if (objects[0] instanceof Double) {
                    energyRelay.setMaxOut(((Double) objects[0]).intValue());
                    //ThermalScience.packetHandler.sendPacketToServer(new PacketEnergyRelaySettings(energyRelay.xCoord, energyRelay.yCoord, energyRelay.zCoord, energyRelay.maxIn, , energyRelay.sideConfigs));
                } else {
                    return new Object[]{"Argument is not a number!"};
                }
                break;
            case 3:
                //getEnergyStored
                return new Object[]{energyRelay.getEnergyStored()};

            case 4:
                //getMaxEnergyStored
                return new Object[]{energyRelay.getMaxEnergyStored()};
        }

        return null;
    }

    @Override
    public void attach(IComputerAccess iComputerAccess) {

    }

    @Override
    public void detach(IComputerAccess iComputerAccess) {

    }

    @Override
    public boolean equals(IPeripheral iPeripheral) {
        return false;
    }
}
