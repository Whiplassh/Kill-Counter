package net.whipplash.network;

import net.whipplash.common.ServerEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class C2SResetCountPacket {
    public C2SResetCountPacket() {}

    public static void encode(C2SResetCountPacket pkt, FriendlyByteBuf buf) {}

    public static C2SResetCountPacket decode(FriendlyByteBuf buf) { return new C2SResetCountPacket(); }

    public static void handle(C2SResetCountPacket pkt, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            var sender = ctx.getSender();
            if (sender != null) {
                ServerEvents.setKills(sender, 0);
                ServerEvents.openGuiFor(sender);
            }
        });
        ctx.setPacketHandled(true);
    }
}