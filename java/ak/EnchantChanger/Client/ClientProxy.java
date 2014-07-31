package ak.EnchantChanger.Client;

import ak.EnchantChanger.*;
import ak.EnchantChanger.Client.renderer.*;
import ak.EnchantChanger.entity.EcEntityApOrb;
import ak.EnchantChanger.entity.EcEntityExExpBottle;
import ak.EnchantChanger.entity.EcEntityMeteor;
import ak.EnchantChanger.tileentity.EcTileEntityHugeMateria;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.input.Keyboard;

public class ClientProxy extends CommonProxy {
	public static KeyBinding MagicKey = new KeyBinding("Key.EcMagic",
			Keyboard.KEY_V, "EnchantChanger");
    public static KeyBinding MateriaKey = new KeyBinding("Key.EcMateria", Keyboard.KEY_R, "EnchantChanger");
    public static int customRenderPass;
    public static int multiPassRenderType;
    public static EcRenderMultiPassBlock ecRenderMultiPassBlock = new EcRenderMultiPassBlock();
    public static Minecraft mc = Minecraft.getMinecraft();
	@Override
	public void registerRenderInformation() {
		RenderingRegistry.registerEntityRenderingHandler(
				EcEntityExExpBottle.class, new EcRenderItemThrowable(0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EcEntityMeteor.class,
				new EcRenderItemThrowable(EnchantChanger.sizeMeteor));
		RenderingRegistry.registerEntityRenderingHandler(EcEntityApOrb.class,
				new EcRenderApOrb());
        multiPassRenderType = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(ecRenderMultiPassBlock);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(EnchantChanger.blockMakoReactor), ecRenderMultiPassBlock);

		ClientRegistry.registerKeyBinding(MagicKey);
        ClientRegistry.registerKeyBinding(MateriaKey);
		IItemRenderer swordRenderer = new EcRenderSwordModel();
		MinecraftForgeClient.registerItemRenderer(
				EnchantChanger.itemSephirothSword,
				swordRenderer);
		MinecraftForgeClient.registerItemRenderer(
				EnchantChanger.itemZackSword,
				swordRenderer);
		MinecraftForgeClient.registerItemRenderer(
				EnchantChanger.ItemCloudSwordCore,
				swordRenderer);
		MinecraftForgeClient.registerItemRenderer(
				EnchantChanger.itemCloudSword,
				swordRenderer);
		MinecraftForgeClient.registerItemRenderer(
				EnchantChanger.itemUltimateWeapon,
				swordRenderer);
		MinecraftForgeClient.registerItemRenderer(
				EnchantChanger.itemImitateSephirothSword,
				swordRenderer);
		IItemRenderer materiaRenderer = new EcRenderMateria();
		MinecraftForgeClient.registerItemRenderer(EnchantChanger.itemMateria,
				materiaRenderer);
		MinecraftForgeClient.registerItemRenderer(
				EnchantChanger.itemMasterMateria, materiaRenderer);
	}

	@Override
	public void registerTileEntitySpecialRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(
				EcTileEntityHugeMateria.class, new EcRenderHugeMateria());
	}

    @Override
    public EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }
}