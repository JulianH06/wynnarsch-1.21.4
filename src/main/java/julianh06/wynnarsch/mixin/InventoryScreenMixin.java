package julianh06.wynnarsch.mixin;

import com.wynntils.utils.mc.McUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.*;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class InventoryScreenMixin {
    @Inject(method = "renderInGameBackground", at = @At("HEAD"), cancellable = true)
    public void renderInGameBackground(DrawContext context, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if(client.player == null || client.world == null) { return; }

        ScreenHandler currScreenHandler = McUtils.containerMenu();
        if(currScreenHandler == null) { return; }

        Screen currScreen = McUtils.mc().currentScreen;
        if(currScreen == null) { return; }

        String InventoryTitle = currScreen.getTitle().getString();
        if(InventoryTitle == null) { return; }

        if(InventoryTitle.equals("Cannon")) { ci.cancel(); }
    }
}


