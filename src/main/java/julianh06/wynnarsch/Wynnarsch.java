package julianh06.wynnarsch;

import julianh06.wynnarsch.notg.BossPlayerHider;
import julianh06.wynnarsch.notg.cannon.CannonHotkeys;
import julianh06.wynnarsch.notg.cannon.CannonOverlay;
import net.fabricmc.api.ClientModInitializer;

/*import net.minecraft.client.option.KeyBinding;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wynnarsch implements ClientModInitializer {
	public static final String MOD_ID = "wynnarsch";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	/*public static KeyBinding keyNotgCannonLeft;
	public static KeyBinding keyNotgCannonRight;
	public static KeyBinding keyNotgCannonShoot;*/

	@Override
	public void onInitializeClient() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		WynnarschConfig.load();

		Runtime.getRuntime().addShutdownHook(new Thread(WynnarschConfig::save));

		CannonHotkeys.registerCanonHotkeys();
		CannonOverlay.registerCanonOverlay();
		BossPlayerHider.registerBossPlayerHider();
	}

	/*public static InputUtil.Key getBoundKey(KeyBinding binding) {
		return ((KeyBindingAccessor) binding).getBoundKey();
	}*/
}