package ak.enchantchanger.client.renderer;

import ak.enchantchanger.api.Constants;
import ak.enchantchanger.entity.EcEntityExExpBottle;
import ak.enchantchanger.entity.EcEntityMeteor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class EcRenderItemThrowable extends Render<Entity> {
    private static final ResourceLocation RL_TEXTURE_METEOR = new ResourceLocation(Constants.EcAssetsDomain, Constants.EcMeteorPNG);
    private static final ResourceLocation RL_TEXTURE_EX_EXP_BOTTLE = new ResourceLocation(Constants.EcAssetsDomain, Constants.EcExpBottlePNG);
    private float RenderSize;

    public EcRenderItemThrowable(RenderManager renderManager, float size) {
        super(renderManager);
        this.RenderSize = size;
    }

    public void doRender(@Nonnull Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.enableRescaleNormal();
        ;
        GlStateManager.scale(this.RenderSize, this.RenderSize, this.RenderSize);
        this.bindEntityTexture(entity);

        this.func_77026_a(Tessellator.getInstance());
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }

    private void func_77026_a(Tessellator tessellator) {
        float var3 = 0;
        float var4 = 1;
        float var5 = 0;
        float var6 = 1;
        float var7 = 1.0F;
        float var8 = 0.5F;
        float var9 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        VertexBuffer vertexBuffer = tessellator.getBuffer();
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
//        par1Tessellator.getWorldRenderer().setNormal(0.0F, 1.0F, 0.0F);
        vertexBuffer.pos((double) (0.0F - var8), (double) (0.0F - var9), 0.0D).tex((double) var3, (double) var6).endVertex();
        vertexBuffer.pos((double) (var7 - var8), (double) (0.0F - var9), 0.0D).tex((double) var4, (double) var6).endVertex();
        vertexBuffer.pos((double) (var7 - var8), (double) (var7 - var9), 0.0D).tex((double) var4, (double) var5).endVertex();
        vertexBuffer.pos((double) (0.0F - var8), (double) (var7 - var9), 0.0D).tex((double) var3, (double) var5).endVertex();
        tessellator.draw();
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull Entity entity) {
        return entity instanceof EcEntityMeteor ? RL_TEXTURE_METEOR : entity instanceof EcEntityExExpBottle ? RL_TEXTURE_EX_EXP_BOTTLE : null;
    }
}