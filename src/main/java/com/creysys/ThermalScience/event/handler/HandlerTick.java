package com.creysys.ThermalScience.event.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.world.WorldServer;

/**
 * Created by Creysys on 02 Apr 15.
 */
public class HandlerTick {

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if(!event.world.isRemote && event.phase == TickEvent.Phase.END && HandlerBlock.ticked) {
            HandlerBlock.clearUpdatedBlocks();
        }
    }
}
