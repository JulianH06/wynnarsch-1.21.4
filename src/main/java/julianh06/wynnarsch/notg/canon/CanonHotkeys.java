package julianh06.wynnarsch.notg.canon;

import julianh06.wynnarsch.Wynnarsch;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import com.wynntils.utils.mc.McUtils;

import static com.wynntils.utils.wynn.ContainerUtils.clickOnSlot;


public class CanonHotkeys {
    static boolean waspressedA = false;
    static boolean waspressedSPACE = false;
    static boolean waspressedD = false;

    public static void registerCanonHotkeys() {
        Wynnarsch.LOGGER.info("Registering CanonHotkeys for " + Wynnarsch.MOD_ID);
        ClientTickEvents.START_CLIENT_TICK.register((tick) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if(client.player == null || client.world == null) { return; }

            ScreenHandler currScreenHandler = McUtils.containerMenu();
            if(currScreenHandler == null) { return; }

            Screen currScreen = McUtils.mc().currentScreen;
            if(currScreen == null) { return; }

            String InventoryTitle = currScreen.getTitle().getString();
            if(InventoryTitle == null) { return; }

            if(InventoryTitle.equals("Cannon")) {
                boolean ispressedA = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.GLFW_KEY_A);
                boolean ispressedSPACE = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.GLFW_KEY_SPACE);
                boolean ispressedD = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.GLFW_KEY_D);
                if (ispressedA && !waspressedA && currScreenHandler.getSlot(2).getStack().getItem() == Items.GOLDEN_SHOVEL) {
                    clickOnSlot(2, currScreenHandler.syncId, 0, currScreenHandler.getStacks());
                }
                if (ispressedSPACE && !waspressedSPACE && currScreenHandler.getSlot(4).getStack().getItem() == Items.FIRE_CHARGE) {
                    clickOnSlot(4, currScreenHandler.syncId, 0, currScreenHandler.getStacks());
                }
                if (ispressedD && !waspressedD &&currScreenHandler.getSlot(6).getStack().getItem() == Items.GOLDEN_SHOVEL) {
                    clickOnSlot(6, currScreenHandler.syncId, 0, currScreenHandler.getStacks());
                }
                waspressedA = ispressedA;
                waspressedSPACE = ispressedSPACE;
                waspressedD = ispressedD;
            }
        });
    }
}
