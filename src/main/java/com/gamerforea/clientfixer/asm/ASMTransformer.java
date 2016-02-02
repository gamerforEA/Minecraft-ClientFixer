package com.gamerforea.clientfixer.asm;

import com.gamerforea.clientfixer.loader.CoreMod;

import net.minecraft.launchwrapper.IClassTransformer;

public final class ASMTransformer implements IClassTransformer
{
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		if (CoreMod.patchFatRussianFont)
		{
			if (transformedName.equals("net.minecraft.client.resources.Locale"))
				return FatRussianFont.patchLocale(basicClass);
			if (transformedName.equals("net.minecraft.client.gui.FontRenderer"))
				return FatRussianFont.patchFontRenderer(basicClass);
			if (transformedName.equals("net.minecraft.client.Minecraft"))
				return FatRussianFont.patchMinecraft(basicClass);
		}

		return basicClass;
	}
}