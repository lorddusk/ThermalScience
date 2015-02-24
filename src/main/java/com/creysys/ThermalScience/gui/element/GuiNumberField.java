package com.creysys.ThermalScience.gui.element;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Creysys on 15 Feb 15.
 */
public class GuiNumberField extends GuiTextField {
    public GuiNumberField(FontRenderer fontRenderer, int x, int y, int width, int height) {
        super(fontRenderer, x, y, width, height);
    }

    @Override
    public void writeText(String text) {
        if(StringUtils.isNumeric(text)) {
            super.writeText(text);
        }
    }
}
