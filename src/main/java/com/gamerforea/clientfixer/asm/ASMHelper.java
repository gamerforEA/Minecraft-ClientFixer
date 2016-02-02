package com.gamerforea.clientfixer.asm;

import com.gamerforea.clientfixer.loader.CoreMod;
import com.google.common.collect.ImmutableMap;

public final class ASMHelper
{
	private static final ImmutableMap<String, String> fields;
	private static final ImmutableMap<String, String> methods;

	static
	{
		ImmutableMap.Builder<String, String> builderFields = ImmutableMap.builder();
		builderFields.put("net.minecraft.client.resources.Locale.unicode", "field_135029_d");
		fields = builderFields.build();

		ImmutableMap.Builder<String, String> builderMethods = ImmutableMap.builder();
		builderMethods.put("net.minecraft.client.resources.Locale.isUnicode", "func_135025_a");
		builderMethods.put("net.minecraft.client.Minecraft.startGame", "func_71384_a");
		methods = builderMethods.build();
	}

	public static String getField(String field)
	{
		return CoreMod.isObfuscated ? fields.get(field) : field.substring(field.lastIndexOf('.') + 1);
	}

	public static String getMethod(String method)
	{
		return CoreMod.isObfuscated ? methods.get(method) : method.substring(method.lastIndexOf('.') + 1);
	}
}