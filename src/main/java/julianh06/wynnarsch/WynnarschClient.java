package julianh06.wynnarsch;

import com.wynntils.models.containers.Container;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;
import com.wynntils.utils.wynn.ContainerUtils.*;
import com.wynntils.utils.mc.McUtils;

import java.io.Console;
import java.util.*;
import java.util.logging.Logger;

import static com.wynntils.utils.wynn.ContainerUtils.clickOnSlot;
import static com.wynntils.utils.wynn.ContainerUtils.closeBackgroundContainer;


public class WynnarschClient implements ClientModInitializer {
    boolean waspressedA = false;
    boolean waspressedSPACE = false;
    boolean waspressedD = false;

    @Override
    public void onInitializeClient() {

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
