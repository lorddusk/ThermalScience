package com.creysys.ThermalScience;

import com.creysys.ThermalScience.util.DXYZ;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 11 Mar 15.
 */
public class ThermalScienceWorldData extends WorldSavedData {

    public static ThermalScienceWorldData instance;

    public static void load(World world){
        if(world.isRemote){
            return;
        }

        instance = (ThermalScienceWorldData)world.perWorldStorage.loadData(ThermalScienceWorldData.class, ThermalScience.modid);

        if(instance == null){
            instance = new ThermalScienceWorldData(ThermalScience.modid);
            world.perWorldStorage.setData(ThermalScience.modid, instance);
        }
    }

    public List<DXYZ> mapControllerPositions;

    public ThermalScienceWorldData(String mapName) {
        super(mapName);

        mapControllerPositions = new ArrayList<DXYZ>();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        mapControllerPositions = new ArrayList<DXYZ>();
        if(compound.hasKey(ThermalScienceNBTTags.Controller)){
            NBTTagCompound posCompound = compound.getCompoundTag(ThermalScienceNBTTags.Controller);
            int length = posCompound.getInteger(ThermalScienceNBTTags.Count);
            for(int i = 0; i < length; i++){
                DXYZ pos = new DXYZ(compound.getInteger(ThermalScienceNBTTags.Dim + i), compound.getInteger(ThermalScienceNBTTags.XCoord + i), compound.getInteger(ThermalScienceNBTTags.YCoord + i), compound.getInteger(ThermalScienceNBTTags.ZCoord + i));
                mapControllerPositions.add(pos);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        NBTTagCompound posCompound = new NBTTagCompound();
        posCompound.setInteger(ThermalScienceNBTTags.Count, mapControllerPositions.size());
        for(int i = 0; i < mapControllerPositions.size(); i++){
            DXYZ pos = mapControllerPositions.get(i);
            posCompound.setInteger(ThermalScienceNBTTags.Dim + i, pos.d);
            posCompound.setInteger(ThermalScienceNBTTags.XCoord + i, pos.x);
            posCompound.setInteger(ThermalScienceNBTTags.YCoord + i, pos.y);
            posCompound.setInteger(ThermalScienceNBTTags.ZCoord + i, pos.z);
        }
    
    compound.setTag(ThermalScienceNBTTags.Controller, posCompound);
    }
}
