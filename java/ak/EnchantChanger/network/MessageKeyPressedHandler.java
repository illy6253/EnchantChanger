package ak.EnchantChanger.network;

import ak.EnchantChanger.EnchantChanger;
import ak.EnchantChanger.ExtendedPlayerData;
import ak.EnchantChanger.item.EcItemSword;
import ak.MultiToolHolders.ItemMultiToolHolder;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;

/**
 * Created by A.K. on 14/07/31.
 */
public class MessageKeyPressedHandler implements IMessageHandler<MessageKeyPressed, IMessage> {
    @Override
    public IMessage onMessage(MessageKeyPressed message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        if (player != null && player.getCurrentEquippedItem() != null && message.keyIndex != -1) {
            switch(message.keyIndex) {
                case EnchantChanger.MagicKEY :doMagic(player.getCurrentEquippedItem(), player); break;
                case EnchantChanger.MateriaKEY:openMateriaWindow(player);break;
                case EnchantChanger.CtrlKEY:doCtrlKeyAction(player.getCurrentEquippedItem(), player); break;
            }
        }
        return null;
    }

    private void doMagic(ItemStack itemStack, EntityPlayer player) {
        if (itemStack.getItem() instanceof EcItemSword) {
            EcItemSword.doMagic(itemStack, player.worldObj, player);
        } else if (itemStack.getItem() instanceof ItemMultiToolHolder) {
            //ツールホルダーとの連携処理。
            ItemMultiToolHolder mth = (ItemMultiToolHolder) itemStack.getItem();
            if (mth.getInventoryFromItemStack(itemStack).getStackInSlot(ItemMultiToolHolder.getSlotNumFromItemStack(itemStack)) != null
                    && mth.getInventoryFromItemStack(itemStack).getStackInSlot(ItemMultiToolHolder.getSlotNumFromItemStack(itemStack)).getItem() instanceof EcItemSword)
            {
                EcItemSword.doMagic(mth.getInventoryFromItemStack(itemStack).getStackInSlot(ItemMultiToolHolder.getSlotNumFromItemStack(itemStack)), player.worldObj, player);
            }
        }
    }

    private void openMateriaWindow(EntityPlayer player) {
        if (canOpenMateriaWindow(player)) {
            player.openGui(EnchantChanger.instance, EnchantChanger.guiIdMateriaWindow, player.worldObj, MathHelper.ceiling_double_int(player.posX), MathHelper.ceiling_double_int(player.posY), MathHelper.ceiling_double_int(player.posZ));
        }
    }

    private boolean canOpenMateriaWindow(EntityPlayer player) {
        return ExtendedPlayerData.get(player).getSoldierMode() && (EnchantChanger.difficulty < 2 || checkCost(player));
    }

    private void doCtrlKeyAction(ItemStack itemStack, EntityPlayer player) {
        if (itemStack.getItem() instanceof EcItemSword) {
            ((EcItemSword)itemStack.getItem()).doCtrlKeyAction(itemStack, player.worldObj, player);
        }
    }

    private boolean checkCost(EntityPlayer player) {
        int expLv = player.experienceLevel;
        if (expLv > EnchantChanger.enchantChangerCost) {
            player.addExperienceLevel(-EnchantChanger.enchantChangerCost);
            return true;
        }
        player.addChatComponentMessage(new ChatComponentText(String.format("Need %dLevel to open materia window", EnchantChanger.enchantChangerCost)));
        return false;
    }
}