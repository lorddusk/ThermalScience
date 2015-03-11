package com.creysys.ThermalScience.event;

import com.creysys.ThermalScience.event.handler.HandlerTooltip;
import com.creysys.ThermalScience.event.handler.HandlerWorld;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Creysys on 11 Mar 15.
 */
public class ThermalScienceEventHandler {

    public static void register(){
        MinecraftForge.EVENT_BUS.register(new HandlerTooltip());
        MinecraftForge.EVENT_BUS.register(new HandlerWorld());
    }
}
