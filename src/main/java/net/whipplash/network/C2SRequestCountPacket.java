package net.whipplash.network;

import net.whipplash.common.ServerEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class C2SRequestCountPacket {
    public C2SRequestCountPacket() {}

    public static void encode(C2SRequestCountPacket pkt, FriendlyByteBuf buf) {}

    public static C2SRequestCountPacket decode(FriendlyByteBuf buf) { return new C2SRequestCountPacket(); }

    public static void handle(C2SRequestCountPacket pkt, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            var sender = ctx.getSender();
            if (sender != null) {
                ServerEvents.openGuiFor(sender);
            }
        });
        ctx.setPacketHandled(true);
    }
}