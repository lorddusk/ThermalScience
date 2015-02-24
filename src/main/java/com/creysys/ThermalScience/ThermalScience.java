package com.creysys.ThermalScience;

import cofh.api.modhelpers.ThermalExpansionHelper;
import com.creysys.ThermalScience.block.*;
import com.creysys.ThermalScience.compat.NEIThermalScienceConfig;
import com.creysys.ThermalScience.gui.ThermalScienceGuiHandler;
import com.creysys.ThermalScience.item.ItemDust;
import com.creysys.ThermalScience.item.ItemMaterial;
import com.creysys.ThermalScience.item.ItemPortableCompressor;
import com.creysys.ThermalScience.network.ThermalSciencePacketHandler;
import com.creysys.ThermalScience.proxy.ServerProxy;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Iterator;

@Mod(modid = ThermalScience.MODID, version = ThermalScience.VERSION, name = ThermalScience.MODNAME, dependencies = "required-after:CoFHCore;required-after:ThermalExpansion")
public class ThermalScience
{
    public static final String MODID = "ThermalScience";
    public static final String VERSION = "1.7.10";
    public static final String MODNAME = "Thermal Science";

    @Mod.Instance(ThermalScience.MODID)
    public static ThermalScience instance;

    @SidedProxy(modId = ThermalScience.MODID, clientSide = "com.creysys.ThermalScience.proxy.ClientProxy", serverSide = "com.creysys.ThermalScience.proxy.ServerProxy")
    public static ServerProxy proxy;

    public static ThermalSciencePacketHandler packetHandler;

    public static CreativeTabs creativeTab;

    //Blocks
    public static Block blockCompressor;
    public static Block blockCarbothermicFurnace;
    public static Block blockCentrifuge;
    public static Block blockWiremill;

    public static Block blockWatermill;
    public static Block blockEnergyRelay;

    //Items
    public static ItemMaterial itemMaterial;
    public static ItemDust itemDust;

    public static Item itemPortableCompressor;

    public static NEIThermalScienceConfig NEIconfig;

    @EventHandler
    public void preInitialize(FMLPreInitializationEvent event){
        ThermalScienceConfig.load(event.getSuggestedConfigurationFile());

        creativeTab = new ThermalScienceCreativeTab();

        itemMaterial = new ItemMaterial();
        itemDust = new ItemDust();

        blockCompressor = new BlockCompressor();
        blockCarbothermicFurnace = new BlockCarbothermicFurnace();
        blockCentrifuge = new BlockCentrifuge();
        blockWiremill = new BlockWiremill();

        blockWatermill = new BlockWatermill();
        blockEnergyRelay = new BlockEnergyRelay();

        itemPortableCompressor = new ItemPortableCompressor();

        ThermalScienceRecipes.preInitialize();
    }

    @EventHandler
    public void initialize(FMLInitializationEvent event)
    {
        packetHandler = new ThermalSciencePacketHandler();

        NEIconfig = new NEIThermalScienceConfig();

        ThermalScienceUtil.initialize();
        ThermalScienceRecipes.initialize();

        NetworkRegistry.INSTANCE.registerGuiHandler(ThermalScience.MODID, new ThermalScienceGuiHandler());

        proxy.initialize();

        //Initialize waila compat
        FMLInterModComms.sendMessage("Waila", "register", "com.creysys.ThermalScience.compat.ThermalScienceWailaDataProvider.load");
    }

    @EventHandler
    public void postInitialize(FMLPostInitializationEvent event){
        ThermalScienceRecipes.postInitialize();

        //Stop endermen from taking your cobble if enabled
        if(ThermalScienceConfig.stopEndermenGrief){
            Iterator it = GameData.getBlockRegistry().iterator();

            while(it.hasNext()){
                Block block = (Block)it.next();
                EntityEnderman.setCarriable(block, false);
            }
        }
    }
}
