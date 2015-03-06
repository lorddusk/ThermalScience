package com.creysys.ThermalScience.compat.computercraft;

import com.creysys.ThermalScience.compat.computercraft.peripheral.PeripheralEnergyRelay;
import com.creysys.ThermalScience.tileEntity.TileEntityEnergyRelay;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Hashtable;

/**
 * Created by Creysys on 05 Mar 15.
 */
public class ThermalSciencePeripheralProvider implements IPeripheralProvider {

    public Hashtable<Class<? extends TileEntity>, Class<? extends IPeripheral>> peripherals;

    public ThermalSciencePeripheralProvider(){
        peripherals = new Hashtable<Class<? extends TileEntity>, Class<? extends IPeripheral>>();
        peripherals.put(TileEntityEnergyRelay.class, PeripheralEnergyRelay.class);
    }



    @Override
    public IPeripheral getPeripheral(World world, int x, int y, int z, int side) {

        TileEntity tileEntity = world.getTileEntity(x,y,z);

        if(tileEntity != null){
            if(peripherals.containsKey(tileEntity.getClass())){
                Class c = peripherals.get(tileEntity.getClass());
                try {
                    return (IPeripheral)c.getConstructor(tileEntity.getClass()).newInstance(tileEntity);
                }
                catch(Exception ex){
                    return null;
                }
            }
        }


        return null;
    }
}
