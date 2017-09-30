package com.gamerforea.clientfixer.loader;

import java.io.File;
import java.util.Map;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.12.1")
@IFMLLoadingPlugin.Name(CoreMod.NAME)
@IFMLLoadingPlugin.SortingIndex(1001)
public final class CoreMod implements IFMLLoadingPlugin
{
	public static final String MODID = "ClientFixer";
	public static final String NAME = "ClientFixer";
	public static final String VERSION = "@VERSION@";

	public static boolean isObfuscated = false;
	public static boolean patchFatRussianFont = false;

	public CoreMod()
	{
		Configuration config = new Configuration(new File("config", NAME + ".cfg"));
		config.load();
		patchFatRussianFont = config.getBoolean("FatRussianFont", "tweaks", true, "Включить поддержку толстых русских шрифтов. Файл шрифтов - ascii_fat.png");
		config.save();
	}

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[] { "com.gamerforea.clientfixer.asm.ASMTransformer" };
	}

	@Override
	public String getModContainerClass()
	{
		return "com.gamerforea.clientfixer.loader.ModContainer";
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		isObfuscated = (Boolean) data.get("runtimeDeobfuscationEnabled");
	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}
}