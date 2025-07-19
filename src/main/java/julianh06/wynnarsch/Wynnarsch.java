package julianh06.wynnarsch;

import com.wynntils.utils.mc.McUtils;
import julianh06.wynnarsch.chat.ChatNotificator;
import julianh06.wynnarsch.notg.BossPlayerHider;
import julianh06.wynnarsch.notg.cannon.CannonHotkeys;
import julianh06.wynnarsch.notg.cannon.CannonOverlay;
import net.fabricmc.api.ClientModInitializer;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wynnarsch implements ClientModInitializer {
	public static final String MOD_ID = "wynnarsch";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		WynnarschConfig.load();

		Runtime.getRuntime().addShutdownHook(new Thread(WynnarschConfig::save));

		CannonHotkeys.registerCanonHotkeys();
		CannonOverlay.registerCanonOverlay();
		BossPlayerHider.registerBossPlayerHider();
		ChatNotificator.registerChatNotificator();

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(
					literal("wynnarsch")
					.then(literal("config")
							.executes(context -> {
								WynnarschConfig.openConfigScreen();
								return 1;
							})
					)
			);
		});
	}
}