package com.example.killcounter.network;

import com.example.killcounter.common.ServerEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SResetCountPacket {
    public C2SResetCountPacket() {}

    public static void encode(C2SResetCountPacket pkt, FriendlyByteBuf buf) {}

    public static C2SResetCountPacket decode(FriendlyByteBuf buf) {
        return new C2SResetCountPacket();
    }

    public static void handle(C2SResetCountPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var sender = ctx.get().getSender();
            if (sender != null) {
                ServerEvents.setKills(sender, 0);
                ServerEvents.openGuiFor(sender);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
