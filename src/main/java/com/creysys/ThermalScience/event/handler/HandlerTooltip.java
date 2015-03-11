package com.creysys.ThermalScience.event.handler;

import com.creysys.ThermalScience.ThermalScienceConfig;
import com.creysys.ThermalScience.client.gui.IItemTooltipProvider;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Creysys on 10 Mar 15.
 */
public class HandlerTooltip {
    @SubscribeEvent
    public void onAddTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.itemStack;
        if(stack == null){
            return;
        }

        if(ThermalScienceConfig.showOreDictNames) {
            int[] ids = OreDictionary.getOreIDs(stack);
            for (int i = 0; i < ids.length; i++) {
                if (i == 0) {
                    event.toolTip.add("OreDict names:");
                }

                event.toolTip.add(OreDictionary.getOreName(ids[i]));
            }
        }


        Item item = stack.getItem();
        if(item == null){
            return;
        }


        IItemTooltipProvider tooltipProvider = null;

        if(item instanceof ItemBlock){
            Block block = ((ItemBlock)item).field_150939_a;

            if(block instanceof IItemTooltipProvider){
                tooltipProvider = (IItemTooltipProvider)block;
            }
        }
        else {
            if(item instanceof IItemTooltipProvider){
                tooltipProvider = (IItemTooltipProvider)item;
            }
        }


        if (tooltipProvider != null) {
            tooltipProvider.addTooltip(event.toolTip, stack);
        }
    }
}
