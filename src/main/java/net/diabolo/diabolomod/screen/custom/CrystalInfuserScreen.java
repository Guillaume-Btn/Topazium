package net.diabolo.diabolomod.screen.custom;

import net.diabolo.diabolomod.DiaboloMod;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class CrystalInfuserScreen extends AbstractContainerScreen<CrystalInfuserMenu> {
    private static final Identifier GUI_TEXTURE =
            Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "textures/gui/crystal_infuser/crystal_infuser_gui.png");
    private static final Identifier ARROW_TEXTURE =
            Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "textures/gui/arrow_progress.png");
    private static final Identifier BUBBLE_TEXTURE =
            Identifier.fromNamespaceAndPath(DiaboloMod.MODID, "textures/gui/bubbles.png");

    public CrystalInfuserScreen(CrystalInfuserMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    // NOUVEAU SYSTÈME 1.26.1 : On remplace renderBg par extractContents
    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        // On dessine D'ABORD le fond de notre interface (avant d'appeler le super qui dessine les slots)
        int guiLeft = this.leftPos; // leftPos et topPos remplacent ton calcul (width - imageWidth) / 2
        int guiTop = this.topPos;

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, guiLeft, guiTop, this.imageWidth, this.imageHeight);

        renderProgressArrow(graphics, guiLeft, guiTop);
        renderBubbles(graphics, guiLeft + 55, guiTop + 34);

        // On appelle ensuite le super pour qu'il dessine le reste (les slots, les items, etc.)
        super.extractContents(graphics, mouseX, mouseY, a);
    }

    private void renderProgressArrow(GuiGraphicsExtractor graphics, int x, int y) {
        if (menu.isCrafting()) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARROW_TEXTURE, x + 73, y + 35,
                    0, 0, menu.getScaledArrowProgress(), 16, 24, 16); // Vérifie si blitSprite accepte ces arguments, sinon utilise blit() comme avant
        }
    }

    private void renderBubbles(GuiGraphicsExtractor graphics, int x, int y) {
        if (!menu.isCrafting()) return;

        int fillHeight1 = menu.getScaledBubbleProgress1();

        // Attention : en 26.1, l'utilisation de RenderPipelines requiert souvent blit() si on utilise des coordonnées u/v
        graphics.blit(RenderPipelines.GUI_TEXTURED, BUBBLE_TEXTURE,
                x, y + 16 - fillHeight1,
                0, 16 - fillHeight1,
                12, fillHeight1,
                12, 16);
    }
}