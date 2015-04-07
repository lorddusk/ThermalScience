package com.creysys.ThermalScience.event;

import com.creysys.ThermalScience.event.handler.*;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Creysys on 11 Mar 15.
 */
public class ThermalScienceEventHandler {

    public static void register(){
        MinecraftForge.EVENT_BUS.register(new HandlerPlayer());
        MinecraftForge.EVENT_BUS.register(new HandlerWorld());
        MinecraftForge.EVENT_BUS.register(new HandlerBlock());

        FMLCommonHandler.instance().bus().register(new HandlerTick());
    }
}
