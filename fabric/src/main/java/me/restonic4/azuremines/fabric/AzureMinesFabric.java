package me.restonic4.azuremines.fabric;

import me.restonic4.azuremines.AzureMines;
import net.fabricmc.api.ModInitializer;

public class AzureMinesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AzureMines.init();
    }
}