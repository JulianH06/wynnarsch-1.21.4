package julianh06.wynnarsch.notg.cannon;

import com.wynntils.models.raid.type.RaidInfo;
import com.wynntils.utils.colors.CustomColor;
import julianh06.wynnarsch.Wynnarsch;
import julianh06.wynnarsch.WynnarschConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import com.wynntils.utils.render.RenderUtils;

import java.util.Dictionary;
import java.util.Hashtable;
import net.minecraft.util.Identifier;


public class CannonOverlay {
    static boolean inSlimeRoom = false;
    public static boolean isValid = false;

    static Dictionary<String, ColorEnum> BlockDict = new Hashtable<>();
    public static ColorEnum[] Blocks = new ColorEnum[5];
    static String[] colorStrings = new String[4];
    static final String YELLOW = "Yellow Stained Glass";
    static final String BLUE = "Blue Stained Glass";
    static final String RED = "Red Stained Glass";
    static final String WHITE = "White Stained Glass";
    public static Identifier identifier = IdentifiedLayer.MISC_OVERLAYS;
    public static int currentPosition = 0;
    public static int cannonPosition = -1;

    private static final Identifier LAYER_ID = Identifier.of("wynnarsch:hud_cannon");

    public static void registerCanonOverlay() {
        BlockDict.put("Yellow Stained Glass", ColorEnum.YELLOW);
        BlockDict.put("Blue Stained Glass", ColorEnum.BLUE);
        BlockDict.put("Red Stained Glass", ColorEnum.RED);
        BlockDict.put("White Stained Glass", ColorEnum.WHITE);
        colorStrings[0] = YELLOW;
        colorStrings[1] = BLUE;
        colorStrings[2] = RED;
        colorStrings[3] = WHITE;
        Wynnarsch.LOGGER.info("Registering CanonOverlay for " + Wynnarsch.MOD_ID);
        ClientTickEvents.END_CLIENT_TICK.register((tick) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client == null) {
                return;
            }
            ClientWorld world = client.world;
            if (world == null) {
                return;
            }
            if(!WynnarschConfig.INSTANCE.NotgCannonOverlayToggle) {
                isValid = false;
                return;
            }
            if(WynnarschConfig.INSTANCE.NotgCannonOverlayPreview) {
                isValid = true;
                currentPosition = 0;
                Blocks[0] = ColorEnum.RED;
                Blocks[1] = ColorEnum.BLUE;
                Blocks[2] = ColorEnum.YELLOW;
                Blocks[3] = ColorEnum.WHITE;
                Blocks[4] = ColorEnum.RED;
                return;
            }
            //inSlimeRoom = true; // also for debug
            if (!inSlimeRoom || client.player == null) {
                return;
            }
            Vec3d playerPos = client.player.getPos();
            Vec3d LeftBlock = new Vec3d(9568, 35, 3564);
            //Vec3d LeftBlock = new Vec3d(-119, 76, 61); //for my debug world
            if (!playerPos.isInRange(LeftBlock, 25)) {
                isValid = false;
                return;
            }
            BlockState[] blockStates = new BlockState[5];
            boolean isDifferent = false;
            for (int i = 0; i < 5; i++) {
                //left: 9568, 35, 3564 right: 9568, 35, 3568
                blockStates[i] = world.getBlockState(new BlockPos(9568, 35, 3564 + i)); if(blockStates[i] == null) { return; }
                //blockStates[i] = world.getBlockState(new BlockPos(-119 - i, 76, 61)); if(blockStates[i] == null) { return; } //for my debug world
                String blockNameString = blockStates[i].getBlock().getName().getString();
                isValid = false;
                for (String s : colorStrings) {
                    if (blockNameString.equals(s)) {
                        isValid = true;
                        break;
                    }
                }
                if (!isValid) {
                    return;
                }
                if (!isDifferent && Blocks[i] != BlockDict.get(blockNameString)) {
                    isDifferent = true;
                }
                Blocks[i] = BlockDict.get(blockNameString);
            }
            if (isDifferent) {
                currentPosition = 0;
                cannonPosition = 2;
            }
        });

        WynnarschConfig conf = WynnarschConfig.INSTANCE;
        HudLayerRegistrationCallback.EVENT.register(drawer -> {
            drawer.attachLayerAfter(identifier, LAYER_ID, (context, tickCounter) -> {
                MinecraftClient client = MinecraftClient.getInstance();
                MatrixStack poseStack = context.getMatrices();
                if (!isValid) {
                    return;
                }
                if (client.currentScreen != null && CannonHotkeys.isInCannon) {
                    RenderUtils.drawRect(context.getMatrices(), CustomColor.fromInt(-804253680), 0, 0, 0, client.currentScreen.width, client.currentScreen.height);
                }
                //Brown stripe
                RenderUtils.drawRect(context.getMatrices(),
                        CustomColor.fromInt(0x82654c),
                        conf.NotgCannonOverlayOffsetX,
                        conf.NotgCannonOverlayOffsetY + conf.NotgCannonBlockHeight,
                        0,
                        conf.NotgCannonBlockWidth * 5,
                        conf.NotgCannonCurrentBlockHeight);
                for (int i = 0; i < 5; i++) {
                    CustomColor color;
                    switch (Blocks[i]) {
                        case RED -> color = CustomColor.fromInt(0xFFFF0000);
                        case BLUE -> color = CustomColor.fromInt(0xFF0000FF);
                        case WHITE -> color = CustomColor.fromInt(0xFFFFFFFF);
                        case YELLOW -> color = CustomColor.fromInt(0xFFFFFF00);
                        default -> color = CustomColor.fromInt(0x00000000);
                    }
                    RenderUtils.drawRect(poseStack, color, i * conf.NotgCannonBlockWidth + conf.NotgCannonOverlayOffsetX, conf.NotgCannonOverlayOffsetY, 0, conf.NotgCannonBlockWidth, conf.NotgCannonBlockHeight);
                }
                if(currentPosition < 5) {
                    //indicator
                    RenderUtils.drawRect(poseStack, CustomColor.fromInt(conf.NotgCannonIndicatorColor.getColor()), currentPosition * conf.NotgCannonBlockWidth + conf.NotgCannonOverlayOffsetX, conf.NotgCannonOverlayOffsetY + conf.NotgCannonBlockHeight, 0, conf.NotgCannonBlockWidth, conf.NotgCannonCurrentBlockHeight);
                }
            });
        });
    }

    public static void onChallengeStarted(RaidInfo info) {
        if(info.getCurrentRoom().getRoomName().equals("Slime Gathering")){
            cannonPosition = 2;
            inSlimeRoom = true;
        } else {
            inSlimeRoom = false;
        }
    }
}
