package com.creysys.ThermalScience.tileEntity.teleporter;

import com.creysys.ThermalScience.ThermalScience;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Creysys on 06 Mar 15.
 */
public class TileEntityTeleporterController extends TileEntity {

    public byte facing;
    public boolean complete;

    public TileEntityTeleporterController(){
        facing = 0;
    }

    public boolean checkMultiblock(){
        //Get first block coords
        boolean found = false;
        int startX = 0;
        for(int i = 0; i < 3; i++){
            Block block = worldObj.getBlock(xCoord - i - 1,yCoord,zCoord);
            if(block != ThermalScience.blockTeleporterWall && block != ThermalScience.blockTeleporterController && block!=ThermalScience.blockTeleporterPowerTap) {
                startX = xCoord - i;
                found = true;
                break;
            }
        }
        if(!found){
            return false;
        }

        found = false;
        int startY = 0;
        for(int i = 0; i < 4; i++) {
            Block block = worldObj.getBlock(startX, yCoord - i - 1, zCoord);
            if (block != ThermalScience.blockTeleporterWall && block != ThermalScience.blockTeleporterController && block != ThermalScience.blockTeleporterPowerTap) {
                startY = yCoord - i;
                found = true;
                break;
            }
        }
        if(!found){
            return false;
        }

        found = false;
        int startZ = -1;
        for(int i = 0; i < 3; i++){
            Block block = worldObj.getBlock(startX,startY,zCoord - i - 1);
            if(block != ThermalScience.blockTeleporterWall && block != ThermalScience.blockTeleporterController && block!=ThermalScience.blockTeleporterPowerTap) {
                startZ = zCoord - i;
                found = true;
                break;
            }
        }
        if(!found){
            return false;
        }



        boolean hasController = false;
        boolean hasPowerTap = false;
        int door = -1;
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 4; y++){
                for(int z = 0; z < 3; z++){
                    if(x == 1 && z == 1 && (y == 1 || y == 2)){
                        if(!worldObj.isAirBlock(startX + x, startY + y, startZ + z)){
                            return false;
                        }
                        continue;
                    }else if(worldObj.isAirBlock(startX + x, startY + y, startZ + z)) {

                        if (y != 1 && y != 2) {
                            return false;
                        }

                        if (x == 0 && z == 1) {
                            if (door == -1) {
                                door = 0;
                            } else if (door != 0) {
                                return false;
                            }
                        } else if (x == 2 && z == 1) {
                            if (door == -1) {
                                door = 1;
                            } else if (door != 1) {
                                return false;
                            }
                        } else if (x == 1 && z == 0) {
                            if (door == -1) {
                                door = 2;
                            } else if (door != 2) {
                                return false;
                            }
                        } else if (x == 1 && z == 2) {
                            if (door == -1) {
                                door = 3;
                            } else if (door != 3) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }



                    Block block = worldObj.getBlock(startX + x, startY + y, startZ + z);
                    if(block == ThermalScience.blockTeleporterController){
                        if(hasController){
                            //Cant have two controllers
                            return false;
                        }

                        hasController = true;
                    }
                    else if(block == ThermalScience.blockTeleporterPowerTap){
                        if(hasPowerTap){
                            //Cant have two power taps
                            return false;
                        }

                        hasPowerTap = true;
                    }
                    else if(block != ThermalScience.blockTeleporterWall){
                        return false;
                    }
                }
            }
        }


        System.out.println("X:" + startX + " Y:" + startY + " Z:" + startZ);

        return true;
    }
}
