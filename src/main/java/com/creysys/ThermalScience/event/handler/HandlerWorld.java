package com.creysys.ThermalScience.event.handler;

import com.creysys.ThermalScience.ThermalScienceWorldData;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;

/**
 * Created by Creysys on 11 Mar 15.
 */
public class HandlerWorld {
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event){
        ThermalScienceWorldData.load(event.world);
    }
}
