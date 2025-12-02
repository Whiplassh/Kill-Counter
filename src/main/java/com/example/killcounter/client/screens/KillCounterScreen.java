package com.example.killcounter.client.screens;

import com.example.killcounter.network.C2SResetCountPacket;
import com.example.killcounter.network.NetworkHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class KillCounterScreen extends Screen {
    private final int kills;

    public KillCounterScreen(int kills) {
        super(Component.literal("Kill Counter"));
        this.kills = kills;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addRenderableWidget(Button.builder(
                Component.literal("Reset"),
                btn -> NetworkHandler.CHANNEL.sendToServer(new C2SResetCountPacket())
        ).bounds(centerX - 40, centerY + 20, 80, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        graphics.drawCenteredString(this.font, "Kills: " + kills, centerX, centerY - 20, 0xFFFFFF);
        super.render(graphics, mouseX, mouseY, partialTicks);
    }
}
