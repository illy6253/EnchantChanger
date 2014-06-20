package ak.EnchantChanger.Client.gui;

import ak.EnchantChanger.inventory.EcContainerPortableEnchantment;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.Project;

import java.util.Random;
@SideOnly(Side.CLIENT)
public class EcGuiPortableEnchantment extends GuiContainer
{
    /** The book model used on the GUI. */
    private static ModelBook bookModel = new ModelBook();
    private Random rand = new Random();
    private static final ResourceLocation enchantingTableGuiTextures = new ResourceLocation("textures/gui/container/enchanting_table.png");
    private static final ResourceLocation enchantingTableBookTextures = new ResourceLocation("textures/entity/enchanting_table_book.png");

    /** ContainerEnchantment object associated with this gui */
    private EcContainerPortableEnchantment containerEnchantment;
    public int field_74214_o;
    public float field_74213_p;
    public float field_74212_q;
    public float field_74211_r;
    public float field_74210_s;
    public float field_74209_t;
    public float field_74208_u;
    ItemStack theItemStack;

    public EcGuiPortableEnchantment(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
    {
        super(new EcContainerPortableEnchantment(par1InventoryPlayer, par2World, par3, par4, par5));
        this.containerEnchantment = (EcContainerPortableEnchantment)this.inventorySlots;
        //System.out.println("02");
    }

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.enchant"), 12, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        this.func_74205_h();
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        int var4 = (this.width- this.xSize) / 2;
        int var5 = (this.height - this.ySize) / 2;

        for (int var6 = 0; var6 < 3; ++var6)
        {
            int var7 = par1 - (var4 + 60);
            int var8 = par2 - (var5 + 14 + 19 * var6);

            if (var7 >= 0 && var8 >= 0 && var7 < 108 && var8 < 19 && this.containerEnchantment.enchantItem(Minecraft.getMinecraft().thePlayer, var6))
            {
                Minecraft.getMinecraft().playerController.sendEnchantPacket(this.containerEnchantment.windowId, var6);
            }
        }
    }


	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(enchantingTableGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        GL11.glPushMatrix();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        GL11.glViewport((scaledresolution.getScaledWidth() - 320) / 2 * scaledresolution.getScaleFactor(), (scaledresolution.getScaledHeight() - 240) / 2 * scaledresolution.getScaleFactor(), 320 * scaledresolution.getScaleFactor(), 240 * scaledresolution.getScaleFactor());
        GL11.glTranslatef(-0.34F, 0.23F, 0.0F);
        Project.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
        float f1 = 1.0F;
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        RenderHelper.enableStandardItemLighting();
        GL11.glTranslatef(0.0F, 3.3F, -16.0F);
        GL11.glScalef(f1, f1, f1);
        float f2 = 5.0F;
        GL11.glScalef(f2, f2, f2);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(enchantingTableBookTextures);
        GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
        float f3 = this.field_74208_u + (this.field_74209_t - this.field_74208_u) * par1;
        GL11.glTranslatef((1.0F - f3) * 0.2F, (1.0F - f3) * 0.1F, (1.0F - f3) * 0.25F);
        GL11.glRotatef(-(1.0F - f3) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        float f4 = this.field_74212_q + (this.field_74213_p - this.field_74212_q) * par1 + 0.25F;
        float f5 = this.field_74212_q + (this.field_74213_p - this.field_74212_q) * par1 + 0.75F;
        f4 = (f4 - (float)MathHelper.truncateDoubleToInt((double)f4)) * 1.6F - 0.3F;
        f5 = (f5 - (float)MathHelper.truncateDoubleToInt((double)f5)) * 1.6F - 0.3F;

        if (f4 < 0.0F)
        {
            f4 = 0.0F;
        }

        if (f5 < 0.0F)
        {
            f5 = 0.0F;
        }

        if (f4 > 1.0F)
        {
            f4 = 1.0F;
        }

        if (f5 > 1.0F)
        {
            f5 = 1.0F;
        }

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        bookModel.render(null, 0.0F, f4, f5, f3, 0.0F, 0.0625F);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glViewport(0, 0, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        EnchantmentNameParts.instance.reseedRandomGenerator(this.containerEnchantment.nameSeed);

        for (int i1 = 0; i1 < 3; ++i1)
        {
            String s = EnchantmentNameParts.instance.generateNewRandomName();
            this.zLevel = 0.0F;
            Minecraft.getMinecraft().getTextureManager().bindTexture(enchantingTableGuiTextures);
            int j1 = this.containerEnchantment.enchantLevels[i1];
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            if (j1 == 0)
            {
                this.drawTexturedModalRect(k + 60, l + 14 + 19 * i1, 0, 185, 108, 19);
            }
            else
            {
                String s1 = "" + j1;
                FontRenderer fontrenderer = Minecraft.getMinecraft().standardGalacticFontRenderer;
                int k1 = 6839882;

                if (Minecraft.getMinecraft().thePlayer.experienceLevel < j1 && !Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode)
                {
                    this.drawTexturedModalRect(k + 60, l + 14 + 19 * i1, 0, 185, 108, 19);
                    fontrenderer.drawSplitString(s, k + 62, l + 16 + 19 * i1, 104, (k1 & 16711422) >> 1);
                    fontrenderer = Minecraft.getMinecraft().fontRenderer;
                    k1 = 4226832;
                    fontrenderer.drawStringWithShadow(s1, k + 62 + 104 - fontrenderer.getStringWidth(s1), l + 16 + 19 * i1 + 7, k1);
                }
                else
                {
                    int l1 = par2 - (k + 60);
                    int i2 = par3 - (l + 14 + 19 * i1);

                    if (l1 >= 0 && i2 >= 0 && l1 < 108 && i2 < 19)
                    {
                        this.drawTexturedModalRect(k + 60, l + 14 + 19 * i1, 0, 204, 108, 19);
                        k1 = 16777088;
                    }
                    else
                    {
                        this.drawTexturedModalRect(k + 60, l + 14 + 19 * i1, 0, 166, 108, 19);
                    }

                    fontrenderer.drawSplitString(s, k + 62, l + 16 + 19 * i1, 104, k1);
                    fontrenderer = Minecraft.getMinecraft().fontRenderer;
                    k1 = 8453920;
                    fontrenderer.drawStringWithShadow(s1, k + 62 + 104 - fontrenderer.getStringWidth(s1), l + 16 + 19 * i1 + 7, k1);
                }
            }
        }
    }

    public void func_74205_h()
    {
        ItemStack itemstack = this.inventorySlots.getSlot(0).getStack();

        if (!ItemStack.areItemStacksEqual(itemstack, this.theItemStack))
        {
            this.theItemStack = itemstack;

            do
            {
                int var1 = this.rand.nextInt(4);
                int var2 = this.rand.nextInt(4);
                this.field_74211_r += (float)(var1 - var2);
            }
            while (this.field_74213_p <= this.field_74211_r + 1.0F && this.field_74213_p >= this.field_74211_r - 1.0F);
        }

        ++this.field_74214_o;
        this.field_74212_q = this.field_74213_p;
        this.field_74208_u = this.field_74209_t;
        boolean flag = false;

        for (int i = 0; i < 3; ++i)
        {
            if (this.containerEnchantment.enchantLevels[i] != 0)
            {
                flag = true;
            }
        }

        if (flag)
        {
            this.field_74209_t += 0.2F;
        }
        else
        {
            this.field_74209_t -= 0.2F;
        }

        if (this.field_74209_t < 0.0F)
        {
            this.field_74209_t = 0.0F;
        }

        if (this.field_74209_t > 1.0F)
        {
            this.field_74209_t = 1.0F;
        }

        float f = (this.field_74211_r - this.field_74213_p) * 0.4F;
        float f1 = 0.2F;

        if (f < -f1)
        {
            f = -f1;
        }

        if (f > f1)
        {
            f = f1;
        }

        this.field_74210_s += (f - this.field_74210_s) * 0.9F;
        this.field_74213_p += this.field_74210_s;
    }
}