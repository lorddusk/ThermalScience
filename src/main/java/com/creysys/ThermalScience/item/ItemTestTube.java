package com.creysys.ThermalScience.item;

import com.creysys.ThermalScience.ThermalScience;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 10 May 15.
 */
public class ItemTestTube extends ItemThermalScience {

    public static ItemStack empty;

    public static ItemStack water;

    public static ItemStack sulfur;
    public static ItemStack sulfurDioxide;
    public static ItemStack sulfurDioxideAndPlatinum;
    public static ItemStack sulfurTrioxide;
    public static ItemStack sulfuricAcid;
    public static ItemStack sulfuricAcidAndOssein;

    public List<String> testTubes;
    IIcon[] icons;

    public ItemTestTube(){
        super("TestTube");

        testTubes = new ArrayList<String>();

        setHasSubtypes(true);
        setMaxDamage(0);

        registerTestTubes();

        icons = new IIcon[testTubes.size()];
    }

    public void registerTestTubes() {
        empty = registerTestTube("Empty");

        water = registerTestTube("Water");

        sulfur = registerTestTube("Sulfur");
        sulfurDioxide = registerTestTube("SulfurDioxide");
        sulfurDioxideAndPlatinum = registerTestTube("SulfurDioxideAndPlatinum");
        sulfurTrioxide = registerTestTube("SulfurTrioxide");
        sulfuricAcid = registerTestTube("SulfuricAcid");
        sulfuricAcidAndOssein = registerTestTube("SulfuricAcidAndOssein");
    }

    public ItemStack registerTestTube(String name){
        testTubes.add(name);
        return new ItemStack(this, 1, testTubes.size() - 1);
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
            icons[i] = iconRegister.registerIcon(ThermalScience.modid + ":testTubes/" + StringUtils.uncapitalize(testTubes.get(i)));
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        for(int i = 0; i < testTubes.size(); i++){
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {
        list.add(StatCollector.translateToLocal("item.testTube" + testTubes.get(MathHelper.clamp_int(stack.getItemDamage(), 0, testTubes.size() - 1))));
    }
}
