package com.example.killcounter;

import com.example.killcounter.network.NetworkHandler;
import net.minecraftforge.fml.common.Mod;

@Mod(KillCounterMod.MODID)
public class KillCounterMod {
    public static final String MODID = "killcounter";

    public KillCounterMod() {
        NetworkHandler.register(); // регистрируем канал и пакеты
    }
}
