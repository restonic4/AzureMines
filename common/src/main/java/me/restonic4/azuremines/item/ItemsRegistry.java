package me.restonic4.azuremines.item;

import me.restonic4.azuremines.Variables;
import me.restonic4.azuremines.creative_tab.CreativeTabsRegistry;
import me.restonic4.restapi.item.ItemRegistry;
import me.restonic4.restapi.util.CustomItemProperties;
import me.restonic4.restapi.util.CustomToolTier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;

public class ItemsRegistry {
    public static final CustomToolTier AMETHYST_TIER = new CustomToolTier(750, 7, 3, 5, 15, Items.AMETHYST_SHARD);
    public static final CustomToolTier AZURE_TIER = new CustomToolTier(1500, 8, 3, 6, 18, ItemsRegistry.AZURE);

    public static final CustomItemProperties DEFAULT_PROPERTIES = new CustomItemProperties().tab(CreativeTabsRegistry.main);

    public static final Object AZURE = ItemRegistry.CreateSimple(Variables.modID, "azure", CreativeTabsRegistry.main);

    public static final Object AMETHYST_PICKAXE = ItemRegistry.CreateCustom(
            Variables.modID,
            "amethyst_pickaxe",
            () -> new PickaxeItem(
                    AMETHYST_TIER,
                    1, -2.8f,
                    DEFAULT_PROPERTIES.build()
            )
    );

    public static final Object AZURE_PICKAXE = ItemRegistry.CreateCustom(
            Variables.modID,
            "azure_pickaxe",
            () -> new PickaxeItem(
                    AZURE_TIER,
                    1, -2.8f,
                    DEFAULT_PROPERTIES.build()
            )
    );

    public static void Register() {
        ItemRegistry.Register(Variables.modID);
    }
}
