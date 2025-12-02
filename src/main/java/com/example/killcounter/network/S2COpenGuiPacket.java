package com.example.killcounter.network;

import com.example.killcounter.client.screens.KillCounterScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2COpenGuiPacket {
    private final int kills;

    public S2COpenGuiPacket(int kills) {
        this.kills = kills;
    }

    public static void encode(S2COpenGuiPacket pkt, FriendlyByteBuf buf) {
        buf.writeVarInt(pkt.kills);
    }

    public static S2COpenGuiPacket decode(FriendlyByteBuf buf) {
        return new S2COpenGuiPacket(buf.readVarInt());
    }

    public static void handle(S2COpenGuiPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.setScreen(new KillCounterScreen(pkt.kills));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
