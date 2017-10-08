package com.gamerforea.clientfixer.asm;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

public final class FatRussianFont implements Opcodes
{
	private static final String ASCII = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000";
	private static final String ASCII_RUS = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0451\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u0410\u0411\u0412\u0413\u0414\u0415\u0416\u0417\u0418\u0419\u041a\u041b\u041c\u041d\u041e\u041f\u0420\u0421\u0422\u0423\u0424\u0425\u0426\u0427\u0428\u0429\u042a\u042b\u042c\u042d\u042e\u042f\u0430\u0431\u0432\u0433\u0434\u0435\u0436\u0437\u0438\u0439\u043a\u043b\u043c\u043d\u043e\u043f\u0440\u0441\u0442\u0443\u0444\u0445\u0446\u0447\u0448\u0449\u044a\u044b\u044c\u044d\u044e\u044f";
	private static final String ASCII_PATH = "textures/font/ascii.png";
	private static final String ASCII_PATH_RUS = "textures/font/ascii_fat.png";

	public static byte[] patchLocale(byte[] basicClass) // net.minecraft.client.resources.Locale
	{
		ClassNode cNode = new ClassNode();
		new ClassReader(basicClass).accept(cNode, 0);

		String isUnicode = ASMHelper.getMethod("net.minecraft.client.resources.Locale.isUnicode");
		for (MethodNode mNode : cNode.methods)
		{
			if (mNode.name.equals(isUnicode) && mNode.desc.equals("()Z"))
			{
				mNode.instructions = getLocaleInsnList();
				break;
			}
		}

		ClassWriter cWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cNode.accept(cWriter);
		return cWriter.toByteArray();
	}

	public static byte[] patchFontRenderer(byte[] basicClass) // net.minecraft.client.gui.FontRenderer
	{
		ClassNode cNode = new ClassNode();
		new ClassReader(basicClass).accept(cNode, 0);

		for (MethodNode mNode : cNode.methods)
		{
			replaceLdc(mNode, ASCII, ASCII_RUS);
		}

		ClassWriter cWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cNode.accept(cWriter);
		return cWriter.toByteArray();
	}

	public static byte[] patchMinecraft(byte[] basicClass) // net.minecraft.client.Minecraft
	{
		ClassNode cNode = new ClassNode();
		new ClassReader(basicClass).accept(cNode, 0);
		String startGame = ASMHelper.getMethod("net.minecraft.client.Minecraft.init");

		for (MethodNode mNode : cNode.methods)
		{
			if (mNode.name.equals(startGame) && mNode.desc.equals("()V"))
			{
				replaceLdc(mNode, ASCII_PATH, ASCII_PATH_RUS);
				break;
			}
		}

		ClassWriter cWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cNode.accept(cWriter);
		return cWriter.toByteArray();
	}

	// ============================================================================================================================

	private static InsnList getLocaleInsnList()
	{
		String unicode = ASMHelper.getField("net.minecraft.client.resources.Locale.unicode");
		InsnList insn = new InsnList();

		insn.add(new VarInsnNode(ALOAD, 0));
		insn.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/resources/Locale", unicode, "Z"));
		insn.add(new MethodInsnNode(INVOKESTATIC, "com/gamerforea/clientfixer/asm/FatRussianFont", "isUnicode", "(Z)Z", false));
		insn.add(new InsnNode(IRETURN));

		return insn;
	}

	private static <T> void replaceLdc(MethodNode mNode, T from, T to)
	{
		ListIterator<AbstractInsnNode> iterator = mNode.instructions.iterator();
		while (iterator.hasNext())
		{
			AbstractInsnNode insn = iterator.next();
			Object cst = insn instanceof LdcInsnNode ? ((LdcInsnNode) insn).cst : null;
			if (cst != null && cst.equals(from))
				iterator.set(new LdcInsnNode(to));
		}
	}

	// ============================================================================================================================

	public static boolean isUnicode(boolean unicode)
	{
		GameSettings settings = Minecraft.getMinecraft().gameSettings;
		if (!settings.forceUnicodeFont && settings.language.equalsIgnoreCase("ru_ru"))
			return false;
		else
			return unicode;
	}
}
