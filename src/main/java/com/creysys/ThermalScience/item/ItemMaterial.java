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
