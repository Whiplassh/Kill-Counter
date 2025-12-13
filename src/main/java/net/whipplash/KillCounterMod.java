package net.whipplash;

import net.whipplash.network.NetworkHandler;
import net.minecraftforge.fml.common.Mod;

@Mod(KillCounterMod.MODID)
public class KillCounterMod {
    public static final String MODID = "killcounter";

    public KillCounterMod() {
        NetworkHandler.register();
    }
}