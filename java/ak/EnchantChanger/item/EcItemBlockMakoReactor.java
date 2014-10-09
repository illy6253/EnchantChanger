package ak.EnchantChanger.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by A.K. on 14/09/10.
 */
public class EcItemBlockMakoReactor extends ItemBlock {
    private static String[] nameArray = {"tile.makoreactor-machine", "tile.makoreactor-case"};
    public EcItemBlockMakoReactor(Block block) {
        super(block);
    }

    @Override
    public int getMetadata(int itemDamage) {
        return itemDamage % 16;
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        String baseName = super.getItemStackDisplayName(itemStack);
        String[] strings = itemStack.getTagCompound().getString("EnchantChanger|baseBlock").split(":");
        Block base = GameRegistry.findBlock(strings[0], strings[1]);
        int meta = itemStack.getTagCompound().getInteger("EnchantChanger|baseMeta");
        String baseBlockName = new ItemStack(base, 1, meta).getDisplayName();
        return String.format("%s (%s)", baseName, baseBlockName);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        int meta = itemStack.getItemDamage() % 16;
        if (meta < nameArray.length) {
            return nameArray[meta];
        }
        return "";
    }
}