package com.gamerforea.clientfixer.asm;

import net.minecraft.launchwrapper.IClassTransformer;

import com.gamerforea.clientfixer.loader.CoreMod;

public class ASMTransformer implements IClassTransformer
{
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		if (CoreMod.patchFatRussianFont)
		{
			if (transformedName.equals("net.minecraft.client.resources.Locale")) return FatRussianFont.patchLocale(basicClass);
			if (transformedName.equals("net.minecraft.client.gui.FontRenderer")) return FatRussianFont.patchFontRenderer(basicClass);
			if (transformedName.equals("net.minecraft.client.Minecraft")) return FatRussianFont.patchMinecraft(basicClass);
		}

		return basicClass;
	}
}