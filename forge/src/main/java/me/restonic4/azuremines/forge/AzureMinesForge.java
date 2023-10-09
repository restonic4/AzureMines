package me.restonic4.azuremines.forge;

import dev.architectury.platform.forge.EventBuses;
import me.restonic4.azuremines.AzureMines;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AzureMines.MOD_ID)
public class AzureMinesForge {
    public AzureMinesForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(AzureMines.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        AzureMines.init();
    }
}