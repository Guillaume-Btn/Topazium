package net.diabolo.topazium.screen.custom;

import net.diabolo.topazium.Topazium;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class CrystalInfuserScreen extends AbstractContainerScreen<CrystalInfuserMenu> {
    private static final Identifier GUI_TEXTURE =
            Identifier.fromNamespaceAndPath(Topazium.MODID, "textures/gui/crystal_infuser/crystal_infuser_gui.png");
    private static final Identifier ARROW_TEXTURE =
            Identifier.fromNamespaceAndPath(Topazium.MODID, "textures/gui/arrow_progress.png");
    private static final Identifier BUBBLE_TEXTURE =
            Identifier.fromNamespaceAndPath(Topazium.MODID, "textures/gui/bubbles.png");

    public CrystalInfuserScreen(CrystalInfuserMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int guiLeft = this.leftPos;
        int guiTop = this.topPos;

        graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, guiLeft, guiTop, 0f, 0f, this.imageWidth, this.imageHeight, 256, 256);

        renderProgressArrow(graphics, guiLeft, guiTop);
        renderBubbles(graphics, guiLeft + 55, guiTop + 34);

        super.extractContents(graphics, mouseX, mouseY, a);
    }

    private void renderProgressArrow(GuiGraphicsExtractor graphics, int x, int y) {
        if (menu.isCrafting()) {
            int progress = menu.getScaledArrowProgress();
            graphics.blit(RenderPipelines.GUI_TEXTURED, ARROW_TEXTURE, x + 73, y + 35, 0f, 0f, progress, 16, 24, 16);
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