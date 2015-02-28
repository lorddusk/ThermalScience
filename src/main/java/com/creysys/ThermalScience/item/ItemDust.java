package com.creysys.ThermalScience.item;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

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


    public List<IIcon> icons;
    public List<String> dusts;

    public ItemDust(){
        super("Dust");

        icons = new ArrayList<IIcon>();
        dusts = new ArrayList<String>();

        setHasSubtypes(true);

        registerDusts();
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
        if(damage < 0 || damage >= icons.size()){
            return null;
        }

        return icons.get(damage);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        for(int i = 0; i < dusts.size(); i++){
            icons.add(iconRegister.registerIcon(ThermalScience.MODID.toLowerCase() + ":dusts/" + ThermalScienceUtil.firstLetterLowerCase(dusts.get(i))));
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        for(int i = 0; i < dusts.size(); i++){
            list.add(new ItemStack(this, 1, i));
        }
    }
}
