package julianh06.wynnarsch.mixin;

import net.minecraft.client.gui.hud.InGameHud;

//@Mixin(InGameHud.class)
public class InGameHudMixin {
    /*@Inject(method = "render", at = @At("TAIL"))
    private void renderCustomHud(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (!YourHudClass.isValid()) return;

        for (int i = 0; i < 5; i++) {
            CustomColor color;
            switch (YourHudClass.Blocks[i]) {
                case RED -> color = CustomColor.fromInt(0xFFFF0000);
                case BLUE -> color = CustomColor.fromInt(0xFF0000FF);
                case WHITE -> color = CustomColor.fromInt(0xFFFFFFFF);
                case YELLOW -> color = CustomColor.fromInt(0xFFFFFF00);
                default -> color = CustomColor.fromInt(0x00000000);
            }

            RenderUtils.drawRect(matrices, color, i * 50 + 300, 0, 0, 50, 50);
        }
    }*/
}

