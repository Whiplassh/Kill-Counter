package net.whipplash.network;

import net.whipplash.client.screens.KillCounterScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class S2COpenGuiPacket {
    private final int kills;

    public S2COpenGuiPacket(int kills) { this.kills = kills; }

    public static void encode(S2COpenGuiPacket pkt, FriendlyByteBuf buf) { buf.writeVarInt(pkt.kills); }

    public static S2COpenGuiPacket decode(FriendlyByteBuf buf) { return new S2COpenGuiPacket(buf.readVarInt()); }

    public static void handle(S2COpenGuiPacket pkt, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            var mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.setScreen(new KillCounterScreen(pkt.kills));
            }
        });
        ctx.setPacketHandled(true);
    }
}