package net.whipplash.common;

import net.whipplash.network.NetworkHandler;
import net.whipplash.network.S2COpenGuiPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber
public class ServerEvents {
    private static final String NBT_KILLS_KEY = "killcounter_kills";

    public static int getKills(ServerPlayer player) {
        return player.getPersistentData().getInt(NBT_KILLS_KEY);
    }

    public static void setKills(ServerPlayer player, int value) {
        player.getPersistentData().putInt(NBT_KILLS_KEY, value);
    }

    public static void openGuiFor(ServerPlayer player) {
        int kills = getKills(player);
        NetworkHandler.CHANNEL.send(new S2COpenGuiPacket(kills), PacketDistributor.PLAYER.with(player));
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
            if (!player.getPersistentData().contains(NBT_KILLS_KEY)) {
                player.getPersistentData().putInt(NBT_KILLS_KEY, 0);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.getOriginal() instanceof ServerPlayer oldPlayer &&
                event.getEntity() instanceof ServerPlayer newPlayer) {
            int kills = oldPlayer.getPersistentData().getInt(NBT_KILLS_KEY);
            newPlayer.getPersistentData().putInt(NBT_KILLS_KEY, kills);
        }
    }
}