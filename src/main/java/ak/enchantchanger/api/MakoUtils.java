package ak.enchantchanger.api;

import ak.enchantchanger.item.EcItemMateria;
import ak.enchantchanger.utils.Items;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;
import java.util.Map;

/**
 * Created by A.K. on 14/10/12.
 */
public class MakoUtils {
    public static final Map<ItemStackWrapper, Integer> MAKO_AMOUNT_MAP = Maps.newHashMap();

    public static void init() {
        MakoUtils.registerMakoAmount(new ItemStack(Items.itemMateria, 1, 0), 5);
        for (int i = 1; i < EcItemMateria.MagicMateriaNum; i++) {
            MakoUtils.registerMakoAmount(new ItemStack(Items.itemMateria, 1, i), 100);
        }
        MakoUtils.registerMakoAmount(new ItemStack(Items.itemMasterMateria, 1, 0), 5000);
        for (MasterMateriaType type : MasterMateriaType.values()) {
            MakoUtils.registerMakoAmount(new ItemStack(Items.itemMasterMateria, 1, type.getMeta()), 1000);
        }
        MakoUtils.registerMakoAmount(new ItemStack(Items.itemBucketLifeStream), 1000);
        List<ItemStack> chalcedonyList = OreDictionary.getOres("blockChalcedony");
        for (ItemStack chalcedonyStack : chalcedonyList) {
            MakoUtils.registerMakoAmount(chalcedonyStack, 1);
        }
    }

    public static void registerMakoAmount(ItemStack makoStack, int amount) {
        MAKO_AMOUNT_MAP.put(ItemStackWrapper.getItemStackWrapper(makoStack), amount);
    }

    public static boolean isMako(ItemStack itemStack) {
        ItemStack copyItemWild = itemStack.copy();
        copyItemWild.setItemDamage(OreDictionary.WILDCARD_VALUE);
        return MAKO_AMOUNT_MAP.containsKey(ItemStackWrapper.getItemStackWrapper(copyItemWild)) || MAKO_AMOUNT_MAP.containsKey(ItemStackWrapper.getItemStackWrapper(itemStack));
//        return itemStack.getItem() instanceof EcItemBucketLifeStream
//                || itemStack.getItem() instanceof EcItemMateria
//                || itemStack.getItem() instanceof EcItemMasterMateria
//                || isChalcedony(itemStack);
    }

    public static boolean isChalcedony(ItemStack itemStack) {
        List<ItemStack> chalcedonyList = OreDictionary.getOres("blockChalcedony");
        for (ItemStack chalcedonyStack : chalcedonyList) {
            if (chalcedonyStack.getItemDamage() == OreDictionary.WILDCARD_VALUE && itemStack.getItem().equals(chalcedonyStack.getItem()) || itemStack.isItemEqual(chalcedonyStack)) {
                return true;
            }
        }
        return false;
    }

    public static int getMakoFromItem(ItemStack itemStack) {
        int coefficient = 1;
        if (itemStack.isItemEnchanted()) {
            NBTTagList enchantmentList = itemStack.getEnchantmentTagList();
            for (int i = 0; i < enchantmentList.tagCount(); i++) {
                if (enchantmentList.getCompoundTagAt(i).getShort("lvl") > 0) {
                    coefficient *= enchantmentList.getCompoundTagAt(i).getShort("lvl");
                    break;
                }
            }
        }
        ItemStack copyItemWild = itemStack.copy();
        copyItemWild.setItemDamage(OreDictionary.WILDCARD_VALUE);
        ItemStackWrapper itemStackWrapper = ItemStackWrapper.getItemStackWrapper(itemStack);
        ItemStackWrapper copyWrapper = ItemStackWrapper.getItemStackWrapper(copyItemWild);
        if (MAKO_AMOUNT_MAP.containsKey(copyWrapper)) {
            return MAKO_AMOUNT_MAP.get(copyWrapper) * coefficient;
        }
        if (MAKO_AMOUNT_MAP.containsKey(itemStackWrapper)) {
            return MAKO_AMOUNT_MAP.get(itemStackWrapper) * coefficient;
        }
        return 0;
    }
}