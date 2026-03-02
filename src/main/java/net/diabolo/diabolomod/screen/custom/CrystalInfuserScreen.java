package net.diabolo.diabolomod.screen.custom;

import net.diabolo.diabolomod.DiaboloMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jspecify.annotations.NonNull;

public class CrystalInfuserScreen extends AbstractContainerScreen<CrystalInfuserMenu> {
    private static final Identifier GUI_TEXTURE =
            Identifier.fromNamespaceAndPath(DiaboloMod.MODID,"textures/gui/crystal_infuser/crystal_infuser_gui.png");
    private static final Identifier ARROW_TEXTURE =
            Identifier.fromNamespaceAndPath(DiaboloMod.MODID,"textures/gui/arrow_progress.png");
    private static final Identifier BUBBLE_TEXTURE =
            Identifier.fromNamespaceAndPath(DiaboloMod.MODID,"textures/gui/bubbles.png"); // ← Nouvelle texture 16x16


    public CrystalInfuserScreen(CrystalInfuserMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        int guiLeft = (width - imageWidth) / 2;
        int guiTop = (height - imageHeight) / 2;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, guiLeft, guiTop, 0, 0, imageWidth, imageHeight, 256, 256);

        renderProgressArrow(guiGraphics, guiLeft, guiTop);
        // ✅ POSITION RELATIVE au GUI (58,36)
        renderBubbles(guiGraphics, guiLeft + 55, guiTop + 34);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ARROW_TEXTURE, x + 73, y + 35,
                    0, 0, menu.getScaledArrowProgress(), 16, 24, 16);
        }
    }

    // ← NOUVELLE MÉTHODE (exactement comme la flèche)


    private void renderBubbles(GuiGraphics guiGraphics, int x, int y) {
        if(!menu.isCrafting()) return;

        // Bulle 1 - se remplit de BAS en HAUT depuis (x,y)
        int fillHeight1 = menu.getScaledBubbleProgress1(); // 0→16px
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BUBBLE_TEXTURE,
                x, y + 16 - fillHeight1,     // ← COMMENCE EN BAS (y+16) et monte
                0, 16 - fillHeight1,         // ← Sample de BAS en HAUT dans texture
                12, fillHeight1,             // ← Largeur 12px, hauteur progressive
                12, 16);
    }


    @Override
    public void render(@NonNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}

