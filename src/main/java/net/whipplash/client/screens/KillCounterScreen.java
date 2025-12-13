package net.whipplash.client.screens;

import net.whipplash.network.C2SResetCountPacket;
import net.whipplash.network.NetworkHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.PacketDistributor;

public class KillCounterScreen extends Screen {
    private final int kills;
    private Button resetBtn;
    private int prevMenuBlur = -1;
    private static final int WIDTH = 160;
    private static final int HEIGHT = 80;

    public KillCounterScreen(int kills) {
        super(Component.literal("Kill Counter"));
        this.kills = kills;
    }

    @Override
    protected void init() {
        super.init();

        if (this.minecraft != null && this.minecraft.options != null) {
            prevMenuBlur = this.minecraft.options.menuBackgroundBlurriness().get();
            this.minecraft.options.menuBackgroundBlurriness().set(0);
        }

        int x = (this.width - WIDTH) / 2;
        int y = (this.height - HEIGHT) / 2;

        resetBtn = Button.builder(
                Component.literal("Reset"),
                btn -> NetworkHandler.CHANNEL.send(new C2SResetCountPacket(), PacketDistributor.SERVER.noArg())
        ).bounds(x + 20, y + 40, WIDTH - 40, 20).build();

        this.addRenderableWidget(resetBtn);
    }

    @Override
    public void removed() {
        super.removed();
        if (this.minecraft != null && this.minecraft.options != null && prevMenuBlur >= 0) {
            this.minecraft.options.menuBackgroundBlurriness().set(prevMenuBlur);
            prevMenuBlur = -1;
        }
    }
    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics gg, int mouseX, int mouseY, float partialTicks) {
        int x = (this.width - WIDTH) / 2;
        int y = (this.height - HEIGHT) / 2;

        int bg = 0xAA000000;
        gg.fill(x, y, x + WIDTH, y + HEIGHT, bg);

        int frame = 0xFFFFFFFF;
        gg.fill(x, y, x + WIDTH, y + 1, frame);
        gg.fill(x, y + HEIGHT - 1, x + WIDTH, y + HEIGHT, frame);
        gg.fill(x, y, x + 1, y + HEIGHT, frame);
        gg.fill(x + WIDTH - 1, y, x + WIDTH, y + HEIGHT, frame);

        gg.drawCenteredString(this.font, "Kill Counter", x + WIDTH / 2, y + 6, 0xFFFFFF);
        gg.drawCenteredString(this.font, "Kills: " + kills, x + WIDTH / 2, y + 22, 0xFFFFFF);

        super.render(gg, mouseX, mouseY, partialTicks);
    }
}