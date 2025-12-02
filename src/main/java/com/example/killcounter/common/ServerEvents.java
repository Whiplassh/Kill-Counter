package com.example.killcounter.common;

import com.example.killcounter.network.NetworkHandler;
import com.example.killcounter.network.S2COpenGuiPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class ServerEvents {
    private static final Map<String, Integer> KILL_COUNTS = new HashMap<>();

    public static int getKills(ServerPlayer player) {
        return KILL_COUNTS.getOrDefault(player.getScoreboardName(), 0);
    }

    public static void setKills(ServerPlayer player, int value) {
        KILL_COUNTS.put(player.getScoreboardName(), value);
    }

    public static void openGuiFor(ServerPlayer player) {
        int kills = getKills(player);
        NetworkHandler.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> player),
                new S2COpenGuiPacket(kills)
        );
    }

    @SubscribeEvent
    public static void onPlayerKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer killer &&
                event.getEntity() instanceof ServerPlayer) {
            setKills(killer, getKills(killer) + 1);
        }
    }

    @SubscribeEvent
    public static void onLogin(PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            setKills(player, 0);
        }
    }
}
