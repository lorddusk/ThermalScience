package com.creysys.ThermalScience.item;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.tileEntity.machine.TileEntityMachine;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by Creysys on 27 Mar 15.
 */
public class ItemMachineUpgradeKit extends Item {

    IIcon[] icons;

    public ItemMachineUpgradeKit(){
        super();

        String name = "itemMachineUpgradeKit";

        setUnlocalizedName(name);
        setCreativeTab(ThermalScience.creativeTab);
        setHasSubtypes(true);

        GameRegistry.registerItem(this, name);

        icons = new IIcon[TileEntityMachine.mapTiers.length - 1];
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        for(int i = 0; i < icons.length; i++){
            icons[i] = iconRegister.registerIcon(ThermalScience.modid + ":machineUpgradeKit/" + StringUtils.uncapitalize(getTier(i)));
        }
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return icons[stack.getItemDamage()];
    }

    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_) {
        return 1;
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata) {
        return 1;
    }

    public String getTier(int i){
        return TileEntityMachine.mapTiers[i + 1].substring(2);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName() + getTier(stack.getItemDamage());
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float f1, float f2, float f3) {

        if(world.isRemote){
            return true;
        }

        TileEntity tileEntity = world.getTileEntity(x,y,z);
        if(tileEntity instanceof TileEntityMachine) {
            int i = world.getBlockMetadata(x, y, z);
            if (i == stack.getItemDamage()) {
                world.setBlockMetadataWithNotify(x, y, z, i + 1, 2);
                stack.stackSize--;
                return true;
            }
        }

        return false;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i < 3; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
