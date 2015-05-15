package com.creysys.ThermalScience;

import com.creysys.ThermalScience.block.*;
import com.creysys.ThermalScience.block.machine.*;
import com.creysys.ThermalScience.block.teleporter.BlockTeleporterController;
import com.creysys.ThermalScience.block.teleporter.BlockTeleporterPowerTap;
import com.creysys.ThermalScience.block.teleporter.BlockTeleporterWall;
import com.creysys.ThermalScience.compat.ThermalScienceCompat;
import com.creysys.ThermalScience.item.*;
import com.creysys.ThermalScience.network.ThermalSciencePacketHandler;
import com.creysys.ThermalScience.proxy.ServerProxy;
import com.creysys.ThermalScience.recipe.ThermalScienceRecipes;
import com.creysys.ThermalScience.util.ThermalScienceUtil;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.item.Item;

import java.io.File;
import java.util.Iterator;

@Mod(modid = ThermalScience.MODID, version = ThermalScience.VERSION, name = ThermalScience.MODNAME, dependencies = "required-after:CoFHCore;required-after:ThermalExpansion")
public class ThermalScience
{
    public static final String MODID = "ThermalScience";
    public static final String modid = MODID.toLowerCase();
    public static final String VERSION = "1.7.10";
    public static final String MODNAME = "Thermal Science";

    public static File configDir;

    @Mod.Instance(MODID)
    public static ThermalScience instance;

    @SidedProxy(modId = MODID, clientSide = "com.creysys.ThermalScience.proxy.ClientProxy", serverSide = "com.creysys.ThermalScience.proxy.ServerProxy")
    public static ServerProxy proxy;

    public static ThermalSciencePacketHandler packetHandler;

    public static CreativeTabs creativeTab;

    //Blocks
    public static BlockMachine blockCompressor;
    public static BlockMachine blockCarbothermicFurnace;
    public static BlockMachine blockCentrifuge;
    public static BlockMachine blockWiremill;
    public static BlockMachine blockAssemblingMachine;
    public static BlockMachine blockMagnetizer;
    public static BlockMachine blockExtractor;

    public static Block blockWatermill;
    public static Block blockFluidVoid;

    public static BlockEnergyRelay blockEnergyRelay;
    public static BlockGravitationalTank blockGravitationalTank;

    //Teleporter
    public static BlockTeleporterWall blockTeleporterWall;
    public static Block blockTeleporterPowerTap;
    public static Block blockTeleporterController;


    //Items
    public static ItemMaterial itemMaterial;
    public static ItemDust itemDust;
    public static ItemTestTube itemTestTube;

    public static Item itemPortableCompressor;
    public static Item itemMachineUpgradeKit;

    public static Item itemTeleporterDestinationCard;

    @EventHandler
    public void preInitialize(FMLPreInitializationEvent event){
        configDir = new File(event.getModConfigurationDirectory(), MODID);
        ThermalScienceConfig.load(new File(configDir, "main.cfg"));

        creativeTab = new ThermalScienceCreativeTab();

        itemMaterial = new ItemMaterial();
        itemDust = new ItemDust();
        itemTestTube = new ItemTestTube();

        itemPortableCompressor = new ItemPortableCompressor();
        itemMachineUpgradeKit = new ItemMachineUpgradeKit();

        itemTeleporterDestinationCard = new ItemTeleporterDestinationCard();

        blockCompressor = new BlockCompressor();
        blockCarbothermicFurnace = new BlockCarbothermicFurnace();
        blockCentrifuge = new BlockCentrifuge();
        blockWiremill = new BlockWiremill();
        blockAssemblingMachine = new BlockAssemblingMachine();
        blockMagnetizer = new BlockMagnetizer();
        blockExtractor = new BlockExtractor();

        blockWatermill = new BlockWatermill();
        blockFluidVoid = new BlockFluidVoid();



        blockEnergyRelay = new BlockEnergyRelay();


        blockGravitationalTank = new BlockGravitationalTank();




        blockTeleporterWall = new BlockTeleporterWall();
        blockTeleporterPowerTap = new BlockTeleporterPowerTap();
        blockTeleporterController = new BlockTeleporterController();

        ThermalScienceRecipes.preInitialize();
    }

    @EventHandler
    public void initialize(FMLInitializationEvent event)
    {
        packetHandler = new ThermalSciencePacketHandler();

        ThermalScienceUtil.initialize();
        ThermalScienceRecipes.initialize();

        NetworkRegistry.INSTANCE.registerGuiHandler(ThermalScience.MODID, new ThermalScienceGuiHandler());

        proxy.initialize();

        ThermalScienceCompat.initialize();
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

    @EventHandler
    public void loaded(FMLLoadCompleteEvent event){
        ThermalScienceRecipes.removeRecipes();
    }
}
