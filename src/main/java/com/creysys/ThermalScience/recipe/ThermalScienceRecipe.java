package com.creysys.ThermalScience.recipe;

import com.creysys.ThermalScience.ThermalScienceUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;


/**
 * Created by Creysys on 2/1/2015.
 */
public class ThermalScienceRecipe {

    public Object[] inputs;
    public Object[] outputs;
    public int energy;

    public ThermalScienceRecipe(Object[] input, Object[] outputs, int energy){
        this.inputs = input;
        this.outputs = outputs;
        this.energy = energy;
    }

    public boolean isValid(){
        for(int i = 0; i < inputs.length; i++){
            if(!isObjectValid(inputs[i])){
                return false;
            }
        }

        for(int i = 0; i < outputs.length; i++){
            if(!isObjectValid(outputs[i])){
                return false;
            }
        }

        return true;
    }

    private boolean isObjectValid(Object obj) {
        if (obj instanceof ItemStack || obj instanceof Item || obj instanceof Block) {
            return true;
        } else if (obj instanceof String) {
            String[] splits = ((String) obj).split(",");

            if(splits.length == 1){
                return true;
            }
            else if (splits.length == 2 && StringUtils.isNumeric(splits[1])) {
                return true;
            }
        }

        return false;
    }

    public boolean matches(ItemStack[] inputs){
        if(inputs.length < this.inputs.length){
            return false;
        }

        for(int i = 0; i < this.inputs.length;i++){
            int index = ThermalScienceUtil.getIndexOfItem(this.inputs[i], inputs);

            if(index == -1){
                return false;
            }

            if(this.inputs[i] instanceof Item){
                this.inputs[i] = new ItemStack((Item)this.inputs[i]);
            }
            else if(this.inputs[i] instanceof Block){
                this.inputs[i] = new ItemStack((Block)this.inputs[i]);
            }

            if(this.inputs[i] instanceof ItemStack) {
                if (((ItemStack)this.inputs[i]).stackSize > inputs[index].stackSize) {
                    return false;
                }
            }
            else if(this.inputs[i] instanceof String){

                int stackSize = 1;
                String[] s = ((String)this.inputs[i]).split(",");
                if(s.length > 1){
                    stackSize = Integer.parseInt(s[1]);
                }

                if(stackSize > inputs[index].stackSize){
                    return false;
                }
            }
        }

        return true;
    }
}
