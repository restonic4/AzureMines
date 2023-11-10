package me.restonic4.azuremines.creative_tab;

import me.restonic4.azuremines.Variables;
import me.restonic4.restapi.creative_tab.CreativeTabRegistry;

public class CreativeTabsRegistry {
    public static final Object main = CreativeTabRegistry.CreateCreativeTab(Variables.modID, "main", "azure");

    public static void Register() {
        CreativeTabRegistry.Register(Variables.modID);
    }
}
