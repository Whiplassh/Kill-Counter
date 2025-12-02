package com.example.killcounter.network;

import com.example.killcounter.KillCounterMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(KillCounterMod.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;
    private static int nextId() {
        return packetId++;
    }

    public static void register() {
        CHANNEL.registerMessage(nextId(),
                C2SRequestCountPacket.class,
                C2SRequestCountPacket::encode,
                C2SRequestCountPacket::decode,
                C2SRequestCountPacket::handle
        );

        CHANNEL.registerMessage(nextId(),
                C2SResetCountPacket.class,
                C2SResetCountPacket::encode,
                C2SResetCountPacket::decode,
                C2SResetCountPacket::handle
        );

        CHANNEL.registerMessage(nextId(),
                S2COpenGuiPacket.class,
                S2COpenGuiPacket::encode,
                S2COpenGuiPacket::decode,
                S2COpenGuiPacket::handle
        );
    }
}
