package me.restonic4.azuremines.block;

import me.restonic4.azuremines.Variables;
import me.restonic4.azuremines.creative_tab.CreativeTabsRegistry;
import me.restonic4.restapi.block.BlockRegistry;
import me.restonic4.restapi.util.CustomBlockProperties;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;

public class BlocksRegistry {
    public static final Object AZURE_ORE_BLOCK_PROPERTIES = new CustomBlockProperties().copy(Blocks.DIAMOND_ORE).strength(3, 3).sound(SoundType.STONE).requiresCorrectToolForDrops().build();
    public static final Object AZURE_ORE_BLOCK = BlockRegistry.CreateBlock(Variables.modID, "azure_ore", AZURE_ORE_BLOCK_PROPERTIES, CreativeTabsRegistry.main);

    public static final Object UNDERSTONE_PROPERTIES = new CustomBlockProperties().copy(Blocks.DEEPSLATE).strength(4, 7).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().build();
    public static final Object UNDERSTONE = BlockRegistry.CreateBlock(Variables.modID, "understone", UNDERSTONE_PROPERTIES, CreativeTabsRegistry.main);

    public static final Object FROZEN_UNDERSTONE_PROPERTIES = new CustomBlockProperties().copy(Blocks.DEEPSLATE).strength(5, 8).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().friction(0.989f).build();
    public static final Object FROZEN_UNDERSTONE = BlockRegistry.CreateBlock(Variables.modID, "frozen_understone", FROZEN_UNDERSTONE_PROPERTIES, CreativeTabsRegistry.main);

    public static final Object DECAY_STONE_PROPERTIES = new CustomBlockProperties().copy(Blocks.STONE).strength(2, 2).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().build();
    public static final Object DECAY_STONE = BlockRegistry.CreateBlock(Variables.modID, "decay_stone", DECAY_STONE_PROPERTIES, CreativeTabsRegistry.main);

    public static void Register() {
        BlockRegistry.Register(Variables.modID);
    }
}
