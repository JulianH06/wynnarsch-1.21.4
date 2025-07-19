package julianh06.wynnarsch.notg.cannon;

import julianh06.wynnarsch.Wynnarsch;
import julianh06.wynnarsch.WynnarschConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import com.wynntils.utils.mc.McUtils;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import static com.wynntils.utils.wynn.ContainerUtils.clickOnSlot;


public class CannonHotkeys {
    static boolean wasPressedLeft = false;
    static boolean wasPressedShoot = false;
    static boolean wasPressedRight = false;
    public static boolean isInCannon = false;
    public static boolean wait = false;

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

                int pos = CannonOverlay.cannonPosition;
                if(pos == -1) {
                    pos = 2;
                }
                if(!wait && CannonOverlay.currentPosition < 5) {
                    switch (CannonOverlay.Blocks[CannonOverlay.currentPosition]) {
                        case RED -> {
                            if(pos == 4) {
                                com.wynntils.utils.mc.McUtils.sendMessageToClient(Text.of("[Wynnarsch] SHOOT"));
                            } else {
                                com.wynntils.utils.mc.McUtils.sendMessageToClient(Text.of("[Wynnarsch] RIGHT"));
                            }
                            wait = true;
                        }

                        case WHITE -> {
                            if(pos == 3) {
                                com.wynntils.utils.mc.McUtils.sendMessageToClient(Text.of("[Wynnarsch] SHOOT"));
                            } else if (pos > 3) {
                                com.wynntils.utils.mc.McUtils.sendMessageToClient(Text.of("[Wynnarsch] LEFT"));
                            } else {
                                com.wynntils.utils.mc.McUtils.sendMessageToClient(Text.of("[Wynnarsch] RIGHT"));
                            }
                            wait = true;
                        }

                        case YELLOW -> {
                            if(pos == 2) {
                                com.wynntils.utils.mc.McUtils.sendMessageToClient(Text.of("[Wynnarsch] SHOOT"));
                            } else if (pos > 2) {
                                com.wynntils.utils.mc.McUtils.sendMessageToClient(Text.of("[Wynnarsch] LEFT"));
                            } else {
                                com.wynntils.utils.mc.McUtils.sendMessageToClient(Text.of("[Wynnarsch] RIGHT"));
                            }
                            wait = true;
                        }

                        case BLUE -> {
                            if(pos == 1) {
                                com.wynntils.utils.mc.McUtils.sendMessageToClient(Text.of("[Wynnarsch] SHOOT"));
                            } else {
                                com.wynntils.utils.mc.McUtils.sendMessageToClient(Text.of("[Wynnarsch] LEFT"));
                            }
                            wait = true;
                        }
                    }
                    return;
                } else {
                    CannonOverlay.cannonPosition = 2;
                }



                boolean isPressedLeft = checkKey(MinecraftClient.getInstance().getWindow().getHandle(), WynnarschConfig.INSTANCE.keyNotgCannonLeftID);
                boolean isPressedShoot = checkKey(MinecraftClient.getInstance().getWindow().getHandle(), WynnarschConfig.INSTANCE.keyNotgCannonShootID);
                boolean isPressedRight = checkKey(MinecraftClient.getInstance().getWindow().getHandle(), WynnarschConfig.INSTANCE.keyNotgCannonRightID);

                if (isPressedLeft && !wasPressedLeft && currScreenHandler.getSlot(2).getStack().getItem() == Items.GOLDEN_SHOVEL) {
                    wait = false;
                    CannonOverlay.cannonPosition--;
                    clickOnSlot(2, currScreenHandler.syncId, 0, currScreenHandler.getStacks());
                }
                if (isPressedShoot && !wasPressedShoot && currScreenHandler.getSlot(4).getStack().getItem() == Items.FIRE_CHARGE) {
                    wait = false;
                    clickOnSlot(4, currScreenHandler.syncId, 0, currScreenHandler.getStacks());
                    if(client.currentScreen != null) {
                        client.currentScreen.close();
                    }
                    CannonOverlay.currentPosition++;
                    if(WynnarschConfig.INSTANCE.keyNotgCannonShootID == 32) {
                        MinecraftClient.getInstance().options.jumpKey.setPressed(false);
                    }
                }
                if (isPressedRight && !wasPressedRight &&currScreenHandler.getSlot(6).getStack().getItem() == Items.GOLDEN_SHOVEL) {
                    wait = false;
                    CannonOverlay.cannonPosition++;
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
