package com.creysys.ThermalScience.block.machine;

import cofh.lib.util.helpers.StringHelper;
import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceNBTTags;
import com.creysys.ThermalScience.util.IWrenchable;
import com.creysys.ThermalScience.util.ThermalScienceUtil;
import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.client.gui.IItemTooltipProvider;
import com.creysys.ThermalScience.ThermalScienceGuiID;
import com.creysys.ThermalScience.item.ItemBlockMeta;
import com.creysys.ThermalScience.tileEntity.machine.TileEntityMachine;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

import java.util.List;

/**
 * Created by Creysys on 29.01.2015.
 */
public class BlockMachine extends BlockContainer implements IItemTooltipProvider, IWrenchable{

    public Class tileEntityClass;
    public ThermalScienceGuiID guiID;

    public IIcon iconSide;
    public IIcon iconTop;

    public IIcon iconFrontOff;
    public IIcon iconFrontOn;

    public BlockMachine(String name, Class<? extends TileEntityMachine> tileEntityClass, ThermalScienceGuiID guiID) {
        super(Material.iron);

        setHardness(12F);
        setResistance(16F);
        setHarvestLevel("pickaxe", 1);

        this.tileEntityClass = tileEntityClass;
        this.guiID = guiID;

        String blockName = "block" + name;

        setBlockName(blockName);
        setCreativeTab(ThermalScience.creativeTab);

        GameRegistry.registerBlock(this, ItemBlockMeta.class, blockName);
        GameRegistry.registerTileEntity(tileEntityClass, "tileEntity" + name);
    }

    public int getCraftingSpeed(int meta){
        return TileEntityMachine.mapMaxEnergyReceive[meta] / 2;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        try {
            return (TileEntityMachine)tileEntityClass.newInstance();
        } catch (Exception ex) {
            System.out.println("Could not create TE!");
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int var6 = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        TileEntityMachine tileEntity = (TileEntityMachine) world.getTileEntity(x, y, z);

        byte facing = 3;

        switch (var6) {
            case 0:
                facing = 2;
                break;
            case 1:
                facing = 5;
                break;
            case 3:
                facing = 4;
                break;
        }


        tileEntity.facing = facing;

        if(stack.hasTagCompound() && stack.getTagCompound().hasKey(ThermalScienceNBTTags.EnergyStored)){
            tileEntity.energyStored = stack.getTagCompound().getInteger(ThermalScienceNBTTags.EnergyStored);
        }

        world.setBlockMetadataWithNotify(x, y, z, stack.getItemDamage(), 2);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float f1, float f2, float f3) {
        if (world.isRemote) {
            return true;
        }

        if (player.isSneaking()) {
            ItemStack heldItemStack = player.getHeldItem();
            if (heldItemStack != null && ThermalScienceUtil.isItemWrench(heldItemStack.getItem())) {
                ThermalScienceUtil.wrenchBlock(world, player, x, y, z, this);
            }
        } else if(!(player instanceof FakePlayer)){
            FMLNetworkHandler.openGui(player, ThermalScience.instance, guiID.ordinal(), world, x, y, z);
        }

        return true;
    }


    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        iconSide = iconRegister.registerIcon(ThermalScienceTextures.machineSide.icon);
        iconTop = iconRegister.registerIcon(ThermalScienceTextures.machineTop.icon);
        iconFrontOff = iconSide;
        iconFrontOn = iconSide;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 3) {
            return iconFrontOff;
        }

        return side <= 1 ? iconTop : iconSide;
    }

    @Override
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
        TileEntityMachine machine = (TileEntityMachine)blockAccess.getTileEntity(x,y,z);

        if(side == machine.facing){
            return machine.active ? iconFrontOn : iconFrontOff;
        }

        return side <= 1 ? iconTop : iconSide;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        ThermalScienceUtil.dropBlockContents(world, x, y, z);
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i < 4; i++){
            list.add(new ItemStack(item, 1 , i));
        }
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public void addTooltip(List<String> list, ItemStack stack) {
        list.add("Tier: " + TileEntityMachine.mapTiers[stack.getItemDamage()]);
        list.add(StringHelper.TEAL + StringHelper.UNDERLINE + "Energy");
        int energyStored = 0;
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey(ThermalScienceNBTTags.EnergyStored)){
            energyStored = stack.getTagCompound().getInteger(ThermalScienceNBTTags.EnergyStored);
        }
        list.add(StringHelper.WHITE + "  Stored: " + StringHelper.RED + energyStored + StringHelper.WHITE + "/" + StringHelper.RED + TileEntityMachine.mapMaxEnergyStored[stack.getItemDamage()] + StringHelper.WHITE + " RF");
        list.add(StringHelper.WHITE + "  Usage: " + StringHelper.RED + getCraftingSpeed(stack.getItemDamage()) + StringHelper.WHITE + " RF/t");
    }

    @Override
    public ItemStack onWrenched(ItemStack stack, TileEntity tileEntity) {
        if(tileEntity instanceof TileEntityMachine){
            TileEntityMachine machine = (TileEntityMachine)tileEntity;

            NBTTagCompound compound;
            if(stack.hasTagCompound()){
                compound = stack.getTagCompound();
            }
            else {
                compound = new NBTTagCompound();
            }

            compound.setInteger(ThermalScienceNBTTags.EnergyStored, machine.energyStored);

            stack.setTagCompound(compound);
        }


        return stack;
    }
}
