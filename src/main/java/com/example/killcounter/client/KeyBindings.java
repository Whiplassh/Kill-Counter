package com.example.killcounter.client;

import com.example.killcounter.network.C2SRequestCountPacket;
import com.example.killcounter.network.NetworkHandler;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyBindings {
    public static final KeyMapping OPEN_KEY = new KeyMapping(
            "key.killcounter.open", GLFW.GLFW_KEY_K, "key.categories.misc"
    );

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_KEY);
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (OPEN_KEY.consumeClick()) {
                NetworkHandler.CHANNEL.sendToServer(new C2SRequestCountPacket());
            }
        }
    }
}
