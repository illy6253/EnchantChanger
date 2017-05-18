package ak.enchantchanger;

import ak.enchantchanger.utils.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class CreativeTabEC extends CreativeTabs {
    public CreativeTabEC(String var1) {
        super(var1);
    }

    @Override
    @Nonnull
    public String getTranslatedTabLabel() {
        return "E.Changer";
    }

    @Override
    @Nonnull
    public ItemStack getTabIconItem() {
        return new ItemStack(Items.itemZackSword);
    }
}