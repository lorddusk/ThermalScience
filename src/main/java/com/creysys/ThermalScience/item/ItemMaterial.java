package com.creysys.ThermalScience.item;

import com.creysys.ThermalScience.ThermalScience;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 08 Feb 15.
 */
public class ItemMaterial extends ItemThermalScience {

    public static ItemStack getMaterial(String material, int stackSize){
        for(int i = 0; i < ThermalScience.itemMaterial.materials.size(); i++){
            if(ThermalScience.itemMaterial.materials.get(i).equals(material)){
                return new ItemStack(ThermalScience.itemMaterial, stackSize, i);
            }
        }

        return null;
    }

    public static ItemStack getMaterial(String material){
        return getMaterial(material, 1);
    }

    public List<IIcon> icons;
    public List<String> materials;

    public ItemMaterial(){
        super("Material");

        icons = new ArrayList<IIcon>();
        materials = new ArrayList<String>();

        registerMaterials();
    }

    public void registerMaterials(){
        materials.add("Tube");
        materials.add("WireCopper");
        materials.add("Coil");
        materials.add("Motor");
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
        if(damage < 0 || damage >= icons.size()){
            return null;
        }

        return icons.get(damage);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        for(int i = 0; i < materials.size(); i++){
            icons.add(iconRegister.registerIcon(ThermalScience.MODID.toLowerCase() + ":materials/" + materials.get(i).toLowerCase()));
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        for(int i = 0; i < materials.size(); i++){
            list.add(new ItemStack(this, 1, i));
        }
    }
}
