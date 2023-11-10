package me.restonic4.azuremines;

import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.registry.registries.RegistrySupplier;
import me.restonic4.azuremines.block.BlocksRegistry;
import me.restonic4.azuremines.creative_tab.CreativeTabsRegistry;
import me.restonic4.azuremines.item.ItemsRegistry;
import me.restonic4.azuremines.sound.SoundsRegistry;
import me.restonic4.restapi.RestApi;
import me.restonic4.restapi.block.BlockRegistry;
import me.restonic4.restapi.creative_tab.CreativeTabRegistry;
import me.restonic4.restapi.item.ItemRegistry;
import me.restonic4.restapi.sound.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class AzureMines
{
	public static void init() {
		ItemRegistry.CreateRegistry(Variables.modID);
		BlockRegistry.CreateRegistry(Variables.modID);
		SoundRegistry.GetRegistry(Variables.modID);
		CreativeTabRegistry.CreateRegistry(Variables.modID);

		ItemsRegistry.Register();
		CreativeTabsRegistry.Register();
		BlocksRegistry.Register();
		SoundsRegistry.Register();

		AtomicReference<Music> miningFacilityMusic = new AtomicReference<>();
		AtomicReference<Music> shallowDepthsMusic = new AtomicReference<>();
		AtomicReference<Music> depthsOfTheMinesMusic = new AtomicReference<>();
		AtomicReference<Music> iceCavernMusic = new AtomicReference<>();

		Music MiningFacilityMusic = miningFacilityMusic.updateAndGet(existingMusic -> {
			if (existingMusic == null) {
				return new Music(Holder.direct(((RegistrySupplier<SoundEvent>) SoundsRegistry.MiningFacilityMusic).get()), 3180, 3180, true);
			} else {
				return existingMusic;
			}
		});

		Music ShallowDepthsMusic = shallowDepthsMusic.updateAndGet(existingMusic -> {
			if (existingMusic == null) {
				return Musics.createGameMusic(Holder.direct(((RegistrySupplier<SoundEvent>) SoundsRegistry.ShallowDepthsMusic).get()));
			} else {
				return existingMusic;
			}
		});

		Music DepthsOfTheMinesMusic = depthsOfTheMinesMusic.updateAndGet(existingMusic -> {
			if (existingMusic == null) {
				return Musics.createGameMusic(Holder.direct(((RegistrySupplier<SoundEvent>) SoundsRegistry.DepthsOfTheMinesMusic).get()));
			} else {
				return existingMusic;
			}
		});

		Music IceCavernMusic = iceCavernMusic.updateAndGet(existingMusic -> {
			if (existingMusic == null) {
				return Musics.createGameMusic(Holder.direct(((RegistrySupplier<SoundEvent>) SoundsRegistry.IceCavernMusic).get()));
			} else {
				return existingMusic;
			}
		});

		AtomicReference<Minecraft> foundClient = new AtomicReference<>();
		AtomicReference<Player> foundPlayer = new AtomicReference<>();

		TickEvent.SERVER_LEVEL_POST.register(
				level -> {
					Minecraft client = foundClient.get();
					Player player = foundPlayer.get();

					if (client != null) {
						//Manage depth
						if (client.level != null && player != null && (player.getServer() != null || client.getSingleplayerServer() != null)) {
							String dimName = client.level.dimension().toString();

							boolean inMineDim = (
									dimName.contains("overworld") ||
											dimName.contains("shallow_depths") ||
											dimName.contains("depths_of_the_mines") ||
											dimName.contains("ice_cavern")
							);

							if (inMineDim) {
								ServerLevel upperLevel = null;
								ServerLevel lowerLevel = null;

								MinecraftServer server = null;

								if (player.getServer() != null) {
									server = player.getServer();
								}
								else if(client.getSingleplayerServer() != null) {
									server = client.getSingleplayerServer();
								}

								for (ServerLevel levelFound : server.getAllLevels()) {
									String levelDimName = levelFound.dimension().toString();

									if (dimName.contains("overworld")) {
										if (levelDimName.contains("overworld")) {
											upperLevel = levelFound;
										} else if (levelDimName.contains("shallow_depths")) {
											lowerLevel = levelFound;
										}
									} else if (dimName.contains("shallow_depths")) {
										if (levelDimName.contains("overworld")) {
											upperLevel = levelFound;
										} else if (levelDimName.contains("depths_of_the_mines")) {
											lowerLevel = levelFound;
										}
									}
									else if (dimName.contains("depths_of_the_mines")) {
										if (levelDimName.contains("shallow_depths")) {
											upperLevel = levelFound;
										} else if (levelDimName.contains("ice_cavern")) {
											lowerLevel = levelFound;
										}
									}
									else if (dimName.contains("ice_cavern")) {
										if (levelDimName.contains("depths_of_the_mines")) {
											upperLevel = levelFound;
										} else if (levelDimName.contains("ice_cavern")) {
											lowerLevel = levelFound;
										}
									}
								}

								BlockPos position = player.blockPosition();

								if (player.position().y > 318 && !dimName.contains("overworld")) {
									player.resetFallDistance();
									player.teleportTo(upperLevel, position.getX(), -63, position.getZ(), null, player.getRotationVector().x, player.getRotationVector().y);
									player.resetFallDistance();

									int x = (int) player.position().x;
									int z = (int) player.position().z;

									for (int i = -1; i <= 1; i++) {
										for (int j = -1; j <= 1; j++) {
											upperLevel.setBlock(new BlockPos(x + i, -64, z + j), Blocks.FROSTED_ICE.defaultBlockState(), 1);
											upperLevel.setBlock(new BlockPos(x + i, -63, z + j), Blocks.AIR.defaultBlockState(), 1);
											upperLevel.setBlock(new BlockPos(x + i, -62, z + j), Blocks.AIR.defaultBlockState(), 1);
										}
									}
								} else if (player.position().y < -64 && !dimName.contains("ice_cavern")) {
									player.resetFallDistance();
									player.teleportTo(lowerLevel, position.getX(), 317, position.getZ(), null, player.getRotationVector().x, player.getRotationVector().y);
									player.resetFallDistance();

									int x = (int) player.position().x;
									int z = (int) player.position().z;

									for (int i = -1; i <= 1; i++) {
										for (int j = -1; j <= 1; j++) {
											lowerLevel.setBlock(new BlockPos(x + i, 316, z + j), Blocks.FROSTED_ICE.defaultBlockState(), 1);
											lowerLevel.setBlock(new BlockPos(x + i, 317, z + j), Blocks.AIR.defaultBlockState(), 1);
											lowerLevel.setBlock(new BlockPos(x + i, 318, z + j), Blocks.AIR.defaultBlockState(), 1);
											lowerLevel.setBlock(new BlockPos(x + i, 319, z + j), Blocks.AIR.defaultBlockState(), 1);
										}
									}
								}
							}
						}
					}
				}
		);

		TickEvent.PLAYER_POST.register(
				player -> {
					Minecraft client = foundClient.get();

					if (player != null && client != null) {
						foundPlayer.set(player);

						//Manage music
						if (!client.getMusicManager().isPlayingMusic(ShallowDepthsMusic) && client.level != null && client.level.dimension().toString().contains("shallow_depths")) {
							client.getMusicManager().startPlaying(ShallowDepthsMusic);
						}
						else if (!client.getMusicManager().isPlayingMusic(DepthsOfTheMinesMusic) && client.level != null && client.level.dimension().toString().contains("depths_of_the_mines")) {
							client.getMusicManager().startPlaying(DepthsOfTheMinesMusic);
						}
						else if (!client.getMusicManager().isPlayingMusic(IceCavernMusic) && client.level != null && client.level.dimension().toString().contains("ice_cavern")) {
							client.getMusicManager().startPlaying(IceCavernMusic);
						}
					}
				}
		);

		ClientTickEvent.CLIENT_POST.register(
				client -> {
					if (client != null) {
						foundClient.set(client);

						ClientTickEvent.CLIENT_POST.clearListeners();
					}
				}
		);
	}
}
