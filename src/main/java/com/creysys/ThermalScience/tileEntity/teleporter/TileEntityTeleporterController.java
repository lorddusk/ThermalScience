package com.creysys.ThermalScience.tileEntity.teleporter;

import com.creysys.ThermalScience.ThermalScience;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Creysys on 06 Mar 15.
 */
public class TileEntityTeleporterController extends TileEntity {

    public byte facing;
    public boolean complete;

    public int startX;
    public int startY;
    public int startZ;

    public TileEntityTeleporterController(){
        facing = 0;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void updateEntity() {

        if(worldObj.isRemote){
            return;
        }


        for(int i = 0;i < worldObj.playerEntities.size(); i++){
            EntityPlayer player = (EntityPlayer)worldObj.playerEntities.get(i);


            int posX = (int)player.posX;
            if(posX < 0){
                posX -= 1;
            }

            int posZ = (int)player.posZ;
            if(posZ < 0){
                posZ -= 1;
            }

            if(posX == startX + 1 && (int)player.posY == startY + 1 && posZ == startZ + 1){
                player.setPositionAndUpdate(-12,73,283);
            }
        }
    }

    public boolean checkMultiblock(){

        complete = false;

        //Get first block coords
        boolean found = false;
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
                            } else {
                                door = 10;
                            }
                        } else if (x == 2 && z == 1) {
                            if (door == -1) {
                                door = 1;
                            } else if (door != 1) {
                                return false;
                            } else {
                                door = 10;
                            }
                        } else if (x == 1 && z == 0) {
                            if (door == -1) {
                                door = 2;
                            } else if (door != 2) {
                                return false;
                            } else {
                                door = 10;
                            }
                        } else if (x == 1 && z == 2) {
                            if (door == -1) {
                                door = 3;
                            } else if (door != 3) {
                                return false;
                            } else {
                                door = 10;
                            }
                        } else {
                            return false;
                        }

                        continue;
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

        if(!hasController || !hasPowerTap || door != 10){
            return false;
        }

        System.out.println("X:" + startX + " Y:" + startY + " Z:" + startZ);

        complete = true;

        return true;
    }
}
