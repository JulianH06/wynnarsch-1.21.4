package julianh06.wynnarsch.notg.cannon;

import julianh06.wynnarsch.Wynnarsch;
import julianh06.wynnarsch.WynnarschConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import com.wynntils.utils.mc.McUtils;
import org.lwjgl.glfw.GLFW;

import static com.wynntils.utils.wynn.ContainerUtils.clickOnSlot;


public class CannonHotkeys {
    static boolean wasPressedLeft = false;
    static boolean wasPressedShoot = false;
    static boolean wasPressedRight = false;
    public static boolean isInCannon = false;

    public static void registerCanonHotkeys() {
        Wynnarsch.LOGGER.info("Registering CanonHotkeys for " + Wynnarsch.MOD_ID);
        ClientTickEvents.START_CLIENT_TICK.register((tick) -> {
            isInCannon = false;
            MinecraftClient client = MinecraftClient.getInstance();
            if(client.player == null || client.world == null) { return; }

            ScreenHandler currScreenHandler = McUtils.containerMenu();
            if(currScreenHandler == null) { return; }

            Screen currScreen = McUtils.mc().currentScreen;
            if(currScreen == null) { return; }

            String InventoryTitle = currScreen.getTitle().getString();
            if(InventoryTitle == null) { return; }

            if(InventoryTitle.equals("Cannon")) {
                isInCannon = true;
                /*InputUtil.Key leftKey = checkKey(client.getWindow().getHandle() ,WynnarschConfig.INSTANCE.keyNotgCannonLeftID);
                InputUtil.Key shootKey = Wynnarsch.getBoundKey(Wynnarsch.keyNotgCannonShoot);
                InputUtil.Key rightKey = Wynnarsch.getBoundKey(Wynnarsch.keyNotgCannonRight);

                boolean isPressedLeft = checkKey(client.getWindow().getHandle(), leftKey.getCode());
                boolean isPressedShoot = checkKey(client.getWindow().getHandle(), shootKey.getCode());
                boolean isPressedRight = checkKey(client.getWindow().getHandle(), rightKey.getCode());

                boolean isPressedLeft = Wynnarsch.keyNotgCannonLeft.isPressed();
                boolean isPressedShoot = Wynnarsch.keyNotgCannonShoot.isPressed();
                boolean isPressedRight = Wynnarsch.keyNotgCannonRight.isPressed();*/

                boolean isPressedLeft = checkKey(MinecraftClient.getInstance().getWindow().getHandle(), WynnarschConfig.INSTANCE.keyNotgCannonLeftID);
                boolean isPressedShoot = checkKey(MinecraftClient.getInstance().getWindow().getHandle(), WynnarschConfig.INSTANCE.keyNotgCannonShootID);
                boolean isPressedRight = checkKey(MinecraftClient.getInstance().getWindow().getHandle(), WynnarschConfig.INSTANCE.keyNotgCannonRightID);

                if (isPressedLeft && !wasPressedLeft && currScreenHandler.getSlot(2).getStack().getItem() == Items.GOLDEN_SHOVEL) {
                    clickOnSlot(2, currScreenHandler.syncId, 0, currScreenHandler.getStacks());
                }
                if (isPressedShoot && !wasPressedShoot && currScreenHandler.getSlot(4).getStack().getItem() == Items.FIRE_CHARGE) {
                    clickOnSlot(4, currScreenHandler.syncId, 0, currScreenHandler.getStacks());
                    CannonOverlay.currentPosition++;
                }
                if (isPressedRight && !wasPressedRight &&currScreenHandler.getSlot(6).getStack().getItem() == Items.GOLDEN_SHOVEL) {
                    clickOnSlot(6, currScreenHandler.syncId, 0, currScreenHandler.getStacks());
                }
                wasPressedLeft = isPressedLeft;
                wasPressedShoot = isPressedShoot;
                wasPressedRight = isPressedRight;
            }
        });
    }
    private static boolean checkKey(long handle, int code) {
        if (code == -1) {
            return false;
        } else if (code >= 0 && code <= 7) {
            return GLFW.glfwGetMouseButton(handle, code) == 1;
        } else {
            return GLFW.glfwGetKey(handle, code) == 1;
        }
    }
}
