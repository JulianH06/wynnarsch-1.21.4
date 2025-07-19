package julianh06.wynnarsch.chat;

import com.wynntils.utils.mc.McUtils;
import julianh06.wynnarsch.WynnarschConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvent;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ChatNotificator {
    static WynnarschConfig config = WynnarschConfig.INSTANCE;

    static boolean showingText = false;
    static long activeUntil = 0;
    public static String shownText;

    public static void registerChatNotificator() {
        //For messages sent by wynnarsch (they are handled different than messages from the server
        ClientReceiveMessageEvents.GAME.register((message, sender) -> {
            for(String notificator : WynnarschConfig.INSTANCE.words) {
                if(!notificator.contains("|")) return;
                String[] parts = notificator.split("\\|");
                if(message.getString().toLowerCase().contains(parts[0].toLowerCase())) {
                    MinecraftClient client = MinecraftClient.getInstance();
                    if(client.player == null || client.world == null) { return; }
                    McUtils.playSoundAmbient(SoundEvent.of(Identifier.of(WynnarschConfig.INSTANCE.Sound)), WynnarschConfig.INSTANCE.SoundVolume, WynnarschConfig.INSTANCE.SoundPitch);
                    showingText = true;
                    activeUntil = System.currentTimeMillis() + WynnarschConfig.INSTANCE.TextDurationInMs; // 3 seconds
                    shownText = parts[1];
                    break;
                }
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (showingText && System.currentTimeMillis() > activeUntil) {
                showingText = false;
            }
        });

        HudLayerRegistrationCallback.EVENT.register(drawer -> {
            drawer.attachLayerBefore(IdentifiedLayer.CHAT, Identifier.of("wynnarsch","chatnotifier"), (context, tickCounter) -> {
                MinecraftClient client = MinecraftClient.getInstance();
                if(!WynnarschConfig.INSTANCE.NotifierPreview) {
                    if(shownText != null) {
                        if (shownText.equals("Preview Text")) {
                            shownText = null;
                        }
                    }
                    if (client.player == null || client.world == null || !showingText) return;
                } else {
                    shownText = "Preview Text";
                }
                MatrixStack matrices = context.getMatrices();
                matrices.push();
                matrices.scale(config.TextScale, config.TextScale, 1.0f);
                context.drawTextWithShadow(client.textRenderer, Text.of(shownText), config.TextOffsetX, config.TextOffsetY, config.TextColor.getColor());
                matrices.pop();
            });
        });

    }

    //For messages sent by the server (basically everything except messages from wynnarsch)
    public static void onPlayerChatReceived(Text message) {
        for(String notificator : WynnarschConfig.INSTANCE.words) {
            if(!notificator.contains("|")) return;
            String[] parts = notificator.split("\\|");
            if(message.getString().toLowerCase().contains(parts[0].toLowerCase())) {
                MinecraftClient client = MinecraftClient.getInstance();
                if(client.player == null || client.world == null) { return; }
                McUtils.playSoundAmbient(SoundEvent.of(Identifier.of(WynnarschConfig.INSTANCE.Sound)), WynnarschConfig.INSTANCE.SoundVolume, WynnarschConfig.INSTANCE.SoundPitch);
                showingText = true;
                activeUntil = System.currentTimeMillis() + WynnarschConfig.INSTANCE.TextDurationInMs;
                shownText = parts[1];
                break;
            }
        }

    }
}