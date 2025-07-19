package julianh06.wynnarsch.mixin;

import com.wynntils.mc.event.ChatPacketReceivedEvent;
import julianh06.wynnarsch.chat.ChatNotificator;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatPacketReceivedEvent.class)
public class ChatPacketRecievedEventMixin {
    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    public void started (Text message, CallbackInfo ci) {
        ChatNotificator.onPlayerChatReceived(message);
    }
}
