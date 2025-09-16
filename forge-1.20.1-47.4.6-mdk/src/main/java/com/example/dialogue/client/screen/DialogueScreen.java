package com.example.dialogue.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.npc.Villager;

import java.util.ArrayList;
import java.util.List;

public class DialogueScreen extends Screen {

    private final Component dialogueText;
    private final List<Button> optionButtons = new ArrayList<>();
    private final Villager villager; // can be null

    // Constructor with villager (normal case)
    public DialogueScreen(Component dialogueText, Villager villager) {
        super(Component.literal("Dialogue"));
        this.dialogueText = dialogueText;
        this.villager = villager;
    }

    // Fallback constructor if no villager passed
    public DialogueScreen(Component dialogueText) {
        this(dialogueText, null);
    }

    @Override
    protected void init() {
        super.init();
        optionButtons.clear();

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Example option
        addDialogueOption(centerX - 100, centerY, "Who are you?", () -> {
            this.minecraft.setScreen(new DialogueScreen(Component.literal("I am just a humble villager."), villager));

        });

        // Goodbye option
        addDialogueOption(centerX - 100, centerY + 40, "Goodbye", () -> {
            if (this.minecraft.player != null) {
                this.minecraft.player.sendSystemMessage(Component.literal("Farewell, traveler!"));
            }
            this.onClose();
        });

        addDialogueOption(centerX - 100, centerY + 80, "OK", this::onClose);
    }

    private void addDialogueOption(int x, int y, String text, Runnable action) {
        Button button = Button.builder(Component.literal(text), (btn) -> action.run())
                .bounds(x, y, 200, 20)
                .build();
        this.addRenderableWidget(button);
        optionButtons.add(button);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        int boxWidth = 250;
        int boxHeight = 240;
        int boxX = (this.width - boxWidth) / 2;
        int boxY = (this.height - boxHeight) / 2;

        guiGraphics.fill(boxX, boxY, boxX + boxWidth, boxY + boxHeight, 0xAA000000);
        guiGraphics.drawCenteredString(this.font, dialogueText, this.width / 2, boxY + 10, 0xFFFFFF);

        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        super.onClose();
        if (villager != null) {
            villager.setTradingPlayer(null); // Unfreeze only if we had a villager
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
