package com.gamerforea.clientfixer.asm;

import java.util.HashMap;
import java.util.Map;

import com.gamerforea.clientfixer.loader.CoreMod;

public class ASMHelper
{
	public static Map<String, String> fieldMap = new HashMap<String, String>();
	public static Map<String, String> methodMap = new HashMap<String, String>();

	static
	{
		methodMap.put("net.minecraft.client.resources.Locale.isUnicode", "func_135025_a");
		methodMap.put("net.minecraft.client.Minecraft.getMinecraft", "func_71410_x");
		methodMap.put("net.minecraft.client.gui.FontRenderer.renderCharAtPos", "func_78278_a");
		methodMap.put("net.minecraft.client.gui.FontRenderer.renderStringAtPos", "func_78255_a");
		methodMap.put("net.minecraft.client.gui.FontRenderer.getCharWidth", "func_78263_a");
		methodMap.put("net.minecraft.client.Minecraft.startGame", "func_71384_a");
		fieldMap.put("net.minecraft.client.Minecraft.gameSettings", "field_71474_y");
		fieldMap.put("net.minecraft.client.settings.GameSettings.language", "field_74363_ab");
		fieldMap.put("net.minecraft.client.settings.GameSettings.forceUnicodeFont", "field_151455_aw");
	}

	public static String getField(String field)
	{
		if (!CoreMod.isObfuscated)
		{
			return field.substring(field.lastIndexOf(".") + 1);
		}

		return fieldMap.get(field);
	}

	public static String getMethod(String method)
	{
		if (!CoreMod.isObfuscated)
		{
			return method.substring(method.lastIndexOf(".") + 1);
		}

		return methodMap.get(method);
	}
}