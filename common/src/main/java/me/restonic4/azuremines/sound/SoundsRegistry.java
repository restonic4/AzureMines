package me.restonic4.azuremines.sound;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.restonic4.azuremines.Variables;
import me.restonic4.restapi.sound.SoundRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundsRegistry {
    public static final Object MiningFacilityMusic = SoundRegistry.RegisterSound(Variables.modID, "mining_facility_music");
    public static final Object ShallowDepthsMusic = SoundRegistry.RegisterSound(Variables.modID, "shallow_depths_music");
    public static final Object DepthsOfTheMinesMusic = SoundRegistry.RegisterSound(Variables.modID, "depths_of_the_mines_music");
    public static final Object IceCavernMusic = SoundRegistry.RegisterSound(Variables.modID, "ice_cavern_music");

    public static void Register() {
        SoundRegistry.Register(Variables.modID);
    }
}
