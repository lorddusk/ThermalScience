package com.creysys.ThermalScience.event.handler;

import com.creysys.ThermalScience.util.DXYZ;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.ArrayList;

/**
 * Created by Creysys on 25 Mar 15.
 */
public class HandlerBlock {

    private static ArrayList<DXYZ> updatedBlocks = new ArrayList<DXYZ>();
    public static boolean ticked = false;

    public static ArrayList<DXYZ> getUpdatedBlocks(){
        ticked = true;
        return updatedBlocks;
    }

    public static void clearUpdatedBlocks(){
        ticked = false;
        updatedBlocks.clear();
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if(!event.world.isRemote) {
            updatedBlocks.add(new DXYZ(event.world.provider.dimensionId, event.x, event.y, event.z));
        }
    }

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.PlaceEvent event){
        if(!event.world.isRemote) {
            updatedBlocks.add(new DXYZ(event.world.provider.dimensionId, event.x, event.y, event.z));
        }
    }
}
