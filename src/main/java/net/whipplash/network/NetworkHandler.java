package net.whipplash.network;

import net.whipplash.KillCounterMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class NetworkHandler {
    private static final int PROTOCOL_VERSION = 1;

    public static final SimpleChannel CHANNEL = ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath(KillCounterMod.MODID, "main"))
            .clientAcceptedVersions((status, version) ->
                    status == Channel.VersionTest.Status.VANILLA ||
                            (status == Channel.VersionTest.Status.PRESENT && version == PROTOCOL_VERSION))
            .serverAcceptedVersions((status, version) ->
                    status == Channel.VersionTest.Status.VANILLA ||
                            (status == Channel.VersionTest.Status.PRESENT && version == PROTOCOL_VERSION))
            .networkProtocolVersion(PROTOCOL_VERSION)
            .simpleChannel();

    private static int packetId = 0;
    private static int nextId() { return packetId++; }

    public static void register() {
        CHANNEL.messageBuilder(C2SRequestCountPacket.class, nextId(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SRequestCountPacket::encode)
                .decoder(C2SRequestCountPacket::decode)
                .consumer((msg, ctx) -> C2SRequestCountPacket.handle(msg, ctx))
                .add();

        CHANNEL.messageBuilder(C2SResetCountPacket.class, nextId(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SResetCountPacket::encode)
                .decoder(C2SResetCountPacket::decode)
                .consumer((msg, ctx) -> C2SResetCountPacket.handle(msg, ctx))
                .add();

        CHANNEL.messageBuilder(S2COpenGuiPacket.class, nextId(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(S2COpenGuiPacket::encode)
                .decoder(S2COpenGuiPacket::decode)
                .consumer((msg, ctx) -> S2COpenGuiPacket.handle(msg, ctx))
                .add();
    }
}