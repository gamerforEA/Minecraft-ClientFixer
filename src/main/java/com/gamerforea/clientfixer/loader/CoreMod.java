package com.gamerforea.clientfixer.loader;

import java.io.File;
import java.util.Map;

import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.relauncher.FMLRelaunchLog;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.Name(CoreMod.NAME)
@IFMLLoadingPlugin.SortingIndex(1001)
public class CoreMod implements IFMLLoadingPlugin
{
	public static final String MODID = "ClientFixer";
	public static final String NAME = "ClientFixer";
	public static final String VERSION = "1.0";

	public static boolean isObfuscated = false;
	public static boolean patchFatRussianFont = false;

	public CoreMod()
	{
		Configuration config = new Configuration(new File("config", NAME + ".cfg"));
		config.defaultEncoding = "UTF-8";
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

	public static void log(String message)
	{
		FMLRelaunchLog.log(NAME, Level.INFO, message);
	}
}