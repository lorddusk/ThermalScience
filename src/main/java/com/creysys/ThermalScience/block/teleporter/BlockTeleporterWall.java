package com.creysys.ThermalScience.block.teleporter;

import com.creysys.ThermalScience.ThermalScience;
import com.creysys.ThermalScience.client.ThermalScienceTextures;
import com.creysys.ThermalScience.util.ThermalScienceUtil;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * Created by Creysys on 06 Mar 15.
 */
public class BlockTeleporterWall extends Block {
    public BlockTeleporterWall() {
        super(Material.iron);

        setHardness(1F);
        setResistance(1F);
        setHarvestLevel("pickaxe", 1);

        String blockName = "blockTeleporterWall";
        setBlockName(blockName);
        setCreativeTab(ThermalScience.creativeTab);
        setBlockTextureName(ThermalScienceTextures.teleporterWall.icon);

        GameRegistry.registerBlock(this, blockName);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float f1, float f2, float f3) {
        if (world.isRemote) {
            return true;
        }

        if (player.isSneaking() && player.getHeldItem() != null && ThermalScienceUtil.isItemWrench(player.getHeldItem().getItem())) {
            ThermalScienceUtil.wrenchBlock(world, player, x, y, z);
            return true;
        }

        return false;
    }
}
