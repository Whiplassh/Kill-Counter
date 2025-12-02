package com.example.killcounter.network;

import com.example.killcounter.common.ServerEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SRequestCountPacket {
    public C2SRequestCountPacket() {}

    public static void encode(C2SRequestCountPacket pkt, FriendlyByteBuf buf) {}

    public static C2SRequestCountPacket decode(FriendlyByteBuf buf) {
        return new C2SRequestCountPacket();
    }

    public static void handle(C2SRequestCountPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var sender = ctx.get().getSender();
            if (sender != null) {
                ServerEvents.openGuiFor(sender);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
