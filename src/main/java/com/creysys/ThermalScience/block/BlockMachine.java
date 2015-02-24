package com.creysys.ThermalScience.block;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.ThermalScienceUtil;
import com.creysys.ThermalScience.gui.ThermalScienceGuiID;
import com.creysys.ThermalScience.tileEntity.TileEntityMachine;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * Created by Creysys on 29.01.2015.
 */
public class BlockMachine extends BlockContainer {
    public Class tileEntityClass;
    public ThermalScienceGuiID guiID;

    public IIcon iconSide;
    public IIcon iconTop;
    public IIcon iconFrontOff;
    public IIcon iconFrontOn;

    private int[] faceMap;

    public BlockMachine(String name, Class tileEntityClass, ThermalScienceGuiID guiID) {
        super(Material.rock);

        setHardness(1F);
        setResistance(1F);
        setHarvestLevel("pickaxe", 1);

        this.tileEntityClass = tileEntityClass;
        this.guiID = guiID;

        String blockName = "block" + name;

        setBlockName(blockName);
        setCreativeTab(ThermalScience.creativeTab);

        GameRegistry.registerBlock(this, blockName);
        GameRegistry.registerTileEntity(tileEntityClass, "tileEntity" + name);

        faceMap = new int[]{3, 2, 4, 5};
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        try {
            return (TileEntity) tileEntityClass.newInstance();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int var6 = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        TileEntityMachine tileEntity = (TileEntityMachine) world.getTileEntity(x, y, z);

        byte facing = 0;

        switch (var6) {
            case 0:
                facing = 1;
                break;
            case 1:
                facing = 3;
                break;
            case 3:
                facing = 2;
                break;
        }
        ;

        tileEntity.facing = facing;
        world.setBlockMetadataWithNotify(x, y, z, facing, 2);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (player.isSneaking()) {
            ItemStack heldItemStack = player.getHeldItem();
            if (heldItemStack != null && ThermalScienceUtil.isItemWrench(heldItemStack.getItem())) {
                if (!world.isRemote) {
                    ThermalScienceUtil.wrenchBlock(world, x, y, z);
                }
            } else {
                return false;
            }
        } else if (!world.isRemote) {
            FMLNetworkHandler.openGui(player, ThermalScience.instance, guiID.ordinal(), world, x, y, z);
        }

        return true;
    }


    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        iconSide = iconRegister.registerIcon(ThermalScience.MODID.toLowerCase() + ":machineSide");
        iconTop = iconRegister.registerIcon(ThermalScience.MODID.toLowerCase() + ":machineTop");
        iconFrontOff = iconSide;
        iconFrontOn = iconSide;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if(side == 0 || side == 1){
            return iconTop;
        }

        IIcon frontIcon = iconFrontOff;
        if(meta >= 10){
            meta -= 10;
            frontIcon = iconFrontOn;
        }

        if(meta >= 0 && meta < faceMap.length &&  faceMap[meta] == side){
            return frontIcon;
        }

        return iconSide;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
            TileEntity tileEntity = world.getTileEntity(x, y, z);

            if (tileEntity instanceof TileEntityMachine)
            {
                TileEntityMachine tileEntityMachine = (TileEntityMachine)tileEntity;
                ArrayList<ItemStack> drops = tileEntityMachine.getDrops();

                for (int i = 0; i < drops.size(); i++)
                {
                    ItemStack itemStack = drops.get(i);

                    if (itemStack != null)
                    {
                        float f = world.rand.nextFloat() * 0.8F + 0.1F;
                        float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                        float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

                        while (itemStack.stackSize > 0)
                        {
                            int j1 = world.rand.nextInt(21) + 10;

                            if (j1 > itemStack.stackSize)
                            {
                                j1 = itemStack.stackSize;
                            }

                            itemStack.stackSize -= j1;
                            EntityItem entityItem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemStack.getItem(), j1, itemStack.getItemDamage()));

                            if (itemStack.hasTagCompound())
                            {
                                entityItem.getEntityItem().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityItem.motionX = (double)((float)world.rand.nextGaussian() * f3);
                            entityItem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
                            entityItem.motionZ = (double)((float)world.rand.nextGaussian() * f3);
                            world.spawnEntityInWorld(entityItem);
                        }
                    }
                }

                world.func_147453_f(x, y, z, block);
            }

        super.breakBlock(world, x, y, z, block, meta);
    }
}
