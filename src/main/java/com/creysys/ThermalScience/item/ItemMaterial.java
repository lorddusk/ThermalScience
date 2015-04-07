package com.creysys.ThermalScience.item;

import com.creysys.ThermalScience.ThermalScience;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 08 Feb 15.
 */
public class ItemMaterial extends ItemThermalScience {

    public static ItemStack tube;
    public static ItemStack wireCopper;
    public static ItemStack coil;
    public static ItemStack motor;

    public static ItemStack wireTin;
    public static ItemStack wireIron;
    public static ItemStack wireGold;
    public static ItemStack wireSilver;
    public static ItemStack wireLead;
    public static ItemStack wireNickel;
    public static ItemStack wirePlatinum;
    public static ItemStack wireMithril;
    public static ItemStack wireElectrum;
    public static ItemStack wireInvar;
    public static ItemStack wireBronze;
    public static ItemStack wireSignalum;
    public static ItemStack wireLumium;
    public static ItemStack wireEnderium;

    public static ItemStack insulatedWireCopper;
    public static ItemStack insulatedWireTin;
    public static ItemStack insulatedWireIron;
    public static ItemStack insulatedWireGold;
    public static ItemStack insulatedWireSilver;
    public static ItemStack insulatedWireLead;
    public static ItemStack insulatedWireNickel;
    public static ItemStack insulatedWirePlatinum;
    public static ItemStack insulatedWireMithril;
    public static ItemStack insulatedWireElectrum;
    public static ItemStack insulatedWireInvar;
    public static ItemStack insulatedWireBronze;
    public static ItemStack insulatedWireSignalum;
    public static ItemStack insulatedWireLumium;
    public static ItemStack insulatedWireEnderium;

    public static ItemStack circuitBasic;
    public static ItemStack circuitHardened;
    public static ItemStack circuitReinforced;
    public static ItemStack circuitResonant;

    public static ItemStack magnet;
    public static ItemStack inductionCoil;

    public static ItemStack ingotMagneticIron;
    public static ItemStack ingotMagneticTin;
    public static ItemStack ingotMagneticCopper;
    public static ItemStack ingotMagneticInvar;
    public static ItemStack ingotMagneticEnderium;

    public static ItemStack denseMatterBall;
    public static ItemStack fluidTransmitter;
    public static ItemStack fluidReceiver;

    public List<String> materials;
    public IIcon[] icons;

    public ItemMaterial(){
        super("Material");

        materials = new ArrayList<String>();

        setHasSubtypes(true);
        setMaxDamage(0);

        registerMaterials();

        icons = new IIcon[materials.size()];
    }

    public void registerMaterials(){
        tube = registerMaterial("Tube");
        wireCopper = registerMaterial("WireCopper");
        coil = registerMaterial("Coil");
        motor = registerMaterial("Motor");
        wireTin = registerMaterial("WireTin");
        wireIron = registerMaterial("WireIron");
        wireGold = registerMaterial("WireGold");
        wireSilver = registerMaterial("WireSilver");
        wireLead = registerMaterial("WireLead");
        wireNickel = registerMaterial("WireNickel");
        wirePlatinum = registerMaterial("WirePlatinum");
        wireMithril = registerMaterial("WireMithril");
        wireElectrum = registerMaterial("WireElectrum");
        wireInvar = registerMaterial("WireInvar");
        wireBronze = registerMaterial("WireBronze");
        wireSignalum = registerMaterial("WireSignalum");
        wireLumium = registerMaterial("WireLumium");
        wireEnderium = registerMaterial("WireEnderium");

        insulatedWireCopper = registerMaterial("InsulatedWireCopper");
        insulatedWireTin = registerMaterial("InsulatedWireTin");
        insulatedWireIron = registerMaterial("InsulatedWireIron");
        insulatedWireGold = registerMaterial("InsulatedWireGold");
        insulatedWireSilver = registerMaterial("InsulatedWireSilver");
        insulatedWireLead = registerMaterial("InsulatedWireLead");
        insulatedWireNickel = registerMaterial("InsulatedWireNickel");
        insulatedWirePlatinum = registerMaterial("InsulatedWirePlatinum");
        insulatedWireMithril = registerMaterial("InsulatedWireMithril");
        insulatedWireElectrum = registerMaterial("InsulatedWireElectrum");
        insulatedWireInvar = registerMaterial("InsulatedWireInvar");
        insulatedWireBronze = registerMaterial("InsulatedWireBronze");
        insulatedWireSignalum = registerMaterial("InsulatedWireSignalum");
        insulatedWireLumium = registerMaterial("InsulatedWireLumium");
        insulatedWireEnderium = registerMaterial("InsulatedWireEnderium");

        circuitBasic = registerMaterial("CircuitBasic");
        circuitHardened = registerMaterial("CircuitHardened");
        circuitReinforced = registerMaterial("CircuitReinforced");
        circuitResonant = registerMaterial("CircuitResonant");

        magnet = registerMaterial("Magnet");
        inductionCoil = registerMaterial("InductionCoil");

        ingotMagneticIron = registerMaterial("IngotMagneticIron");
        ingotMagneticTin = registerMaterial("IngotMagneticTin");
        ingotMagneticCopper = registerMaterial("IngotMagneticCopper");
        ingotMagneticInvar = registerMaterial("IngotMagneticInvar");
        ingotMagneticEnderium = registerMaterial("IngotMagneticEnderium");

        denseMatterBall = registerMaterial("DenseMatter");
        fluidTransmitter = registerMaterial("FluidTransmitter");
        fluidReceiver = registerMaterial("FluidReceiver");
    }

    public ItemStack registerMaterial(String name){
        materials.add(name);
        return new ItemStack(this, 1, materials.size() - 1);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if(stack.getItemDamage() < 0 || stack.getItemDamage() >= materials.size()){
            return null;
        }

        return "item.material" + materials.get(stack.getItemDamage());
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        if(damage < 0 || damage >= icons.length){
            return null;
        }

        return icons[damage];
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        for(int i = 0; i < icons.length; i++){
            icons[i] = iconRegister.registerIcon(ThermalScience.modid + ":materials/" + StringUtils.uncapitalize(materials.get(i)));
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        for(int i = 0; i < materials.size(); i++){
            list.add(new ItemStack(this, 1, i));
        }
    }
}
