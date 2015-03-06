package com.creysys.ThermalScience.item;

import com.creysys.ThermalScience.ThermalScience;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 16 Feb 15.
 */
public class ItemDust extends ItemThermalScience {

    public static ItemStack getDust(String dust, int stackSize){
        for(int i = 0; i < ThermalScience.itemDust.dusts.size(); i++){
            if(ThermalScience.itemDust.dusts.get(i).equals(dust)){
                return new ItemStack(ThermalScience.itemDust, stackSize, i);
            }
        }

        return null;
    }

    public static ItemStack getDust(String dust){
        return getDust(dust, 1);
    }


    public List<String> dusts;

    IIcon[] icons;

    public ItemDust(){
        super("Dust");

        dusts = new ArrayList<String>();

        setHasSubtypes(true);
        setMaxDamage(0);

        registerDusts();

        icons = new IIcon[dusts.size()];
    }

    public void registerDusts() {
        dusts.add("Netherrack");
        dusts.add("DirtyNetherrack");

        for (int i = 0; i < dusts.size(); i++) {
            OreDictionary.registerOre("dust" + dusts.get(i), new ItemStack(this, 1, i));
        }
    }


    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if(stack.getItemDamage() < 0 || stack.getItemDamage() >= dusts.size()){
            return null;
        }

        return "item.dust" + dusts.get(stack.getItemDamage());
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
            icons[i] = iconRegister.registerIcon(ThermalScience.modid + ":dusts/" + StringUtils.uncapitalize(dusts.get(i)));
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        for(int i = 0; i < dusts.size(); i++){
            list.add(new ItemStack(this, 1, i));
        }
    }
}
