package ak.ChainDestruction;

import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.world.WorldEvent.Save;
import ak.akapi.ConfigSavable;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="ChainDestruction", name="ChainDestruction", version="1.0d")
//@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels = {"CD|RegKey"}, packetHandler=PacketHandler.class)
public class ChainDestruction
{
	@Instance("ChainDestruction")
	public static ChainDestruction instance;
	@SidedProxy(clientSide = "ak.ChainDestruction.ClientProxy", serverSide = "ak.ChainDestruction.CommonProxy")
	public static CommonProxy proxy;
	public static HashSet<String> enableItems = new HashSet();
	public static HashSet<String> enableBlocks = new HashSet();
	public static String[] itemsConfig;
	public static String[] blocksConfig;
	public static boolean digUnder;
	public static String[] vanillaTools;
	public static String[] vanillaBlocks;
	public static int maxDestroyedBlock;
	public static boolean dropOnPlayer = true;
	public ConfigSavable config;
	public InteractBlockHook interactblockhook;
	public static boolean loadMTH = false;
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		config = new ConfigSavable(event.getSuggestedConfigurationFile());
		config.load();
		maxDestroyedBlock = config.get(Configuration.CATEGORY_GENERAL, "Maximum Destroued Block Counts", 100).getInt();
		itemsConfig = config.get(Configuration.CATEGORY_GENERAL, "toolItemsId", vanillaTools).getStringList();
		blocksConfig = config.get(Configuration.CATEGORY_GENERAL, "chainDestroyedBlockIdConfig", vanillaBlocks).getStringList();
		digUnder = config.get(Configuration.CATEGORY_GENERAL, "digUnder", true).getBoolean(true);
		config.save();
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		proxy.registerClientInfo();
		interactblockhook = new InteractBlockHook();
		MinecraftForge.EVENT_BUS.register(interactblockhook);
		MinecraftForge.EVENT_BUS.register(new SaveConfig());

		LanguageRegistry.instance().addStringLocalization("Key.CDRegistItem", "RegChainDestructItem");
		LanguageRegistry.instance().addStringLocalization("Key.CDRegistItem", "ja_JP","一括破壊アイテム登録キー");
		LanguageRegistry.instance().addStringLocalization("Key.CDDIgUnder", "Dig Under Key");
		LanguageRegistry.instance().addStringLocalization("Key.CDDIgUnder", "ja_JP","下方採掘キー");
	}
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent evet)
	{
		addItemsAndBlocks();
		this.loadMTH = Loader.isModLoaded("MultiToolHolders");
	}
	public void addItemsAndBlocks()
	{
		for(int i = 0;i< itemsConfig.length;i++)
		{
			enableItems.add(itemsConfig[i]);
		}
		for(int i = 0;i< blocksConfig.length;i++)
		{
			enableBlocks.add(blocksConfig[i]);
		}
//		enableItems.addAll(new HashSet(Arrays.asList(itemsConfig)));
//		enableBlocks.addAll(new HashSet(Arrays.asList(blocksConfig)));
	}
	public class SaveConfig
	{
		@SubscribeEvent
		public void WorldSave(Save event)
		{
			config.set(Configuration.CATEGORY_GENERAL, "toolItemsId", enableItems);
			config.set(Configuration.CATEGORY_GENERAL, "chainDestroyedBlockIdConfig", enableBlocks);
			config.set(Configuration.CATEGORY_GENERAL, "digUnder", digUnder);
			config.save();
		}
	}
	public static String getUniqueStrings(Object obj)
	{
		UniqueIdentifier uId;
		if(obj instanceof Block) {
			uId = GameRegistry.findUniqueIdentifierFor((Block) obj);
		}else {
			uId = GameRegistry.findUniqueIdentifierFor((Item) obj);
		}
		return uId.modId + ":" + uId.name;

	}
	static{
		vanillaTools = new String[]{
				"minecraft:diamond_axe","minecraft:golden_axe","minecraft:iron_axe","minecraft:stone_axe","minecraft:wooden_axe",
				"minecraft:diamond_shovel","minecraft:golden_shovel","minecraft:iron_shovel","minecraft:stone_shovel","minecraft:wooden_shovel",
				"minecraft:diamond_pickaxe","minecraft:golden_pickaxe","minecraft:iron_pickaxe","minecraft:stone_pickaxe","minecraft:wooden_pickaxe"};
		vanillaBlocks = new String[]{
				getUniqueStrings(Blocks.obsidian),getUniqueStrings(Blocks.coal_ore),getUniqueStrings(Blocks.diamond_ore),getUniqueStrings(Blocks.emerald_ore),
				getUniqueStrings(Blocks.gold_ore),getUniqueStrings(Blocks.iron_ore),getUniqueStrings(Blocks.lapis_ore), getUniqueStrings(Blocks.quartz_ore),getUniqueStrings(Blocks.redstone_ore),getUniqueStrings(Blocks.lit_redstone_ore)};
	}
}