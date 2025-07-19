package julianh06.wynnarsch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import me.shedaniel.math.Color;

public class WynnarschConfig {
    static WynnarschModMenuApiImpl modMenuApiImpl;

    //Hider
    public boolean partyMemberHide = true;
    public int maxHideDistance = 3;
    public boolean onlyInNotg = false;

    //Cannon Hotkeys
    public int keyNotgCannonLeftID = GLFW.GLFW_KEY_A;
    public int keyNotgCannonShootID = GLFW.GLFW_KEY_SPACE;
    public int keyNotgCannonRightID = GLFW.GLFW_KEY_D;

    //Cannon Overlay
    public int NotgCannonCurrentBlockHeight = 20;
    public int NotgCannonBlockHeight = 40;
    public int NotgCannonBlockWidth = 40;
    public int NotgCannonOverlayOffsetX = 0;
    public int NotgCannonOverlayOffsetY = 0;
    public boolean NotgCannonOverlayPreview = false;
    public boolean NotgCannonOverlayToggle = true;
    public Color NotgCannonIndicatorColor = Color.ofRGB(51, 153, 51);

    //Chat Notifier Text
    public List<String> words = new ArrayList<>();
    public float TextScale = 5f;
    public int TextOffsetX = 75;
    public int TextOffsetY = 40;
    public int TextDurationInMs = 2000;
    public Color TextColor = Color.ofRGB(255, 255, 255);
    public boolean NotifierPreview = false;

    //Chat Notifier Sound
    public String Sound = "entity.experience_orb.pickup";
    public float SoundVolume = 0.1f;
    public float SoundPitch = 1;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("wynnarsch.json");

    public static WynnarschConfig INSTANCE = new WynnarschConfig();

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                INSTANCE = GSON.fromJson(reader, WynnarschConfig.class);
            } catch (IOException e) {
                System.err.println("[Wynnarsch] Couldn't read the config file:");
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            System.err.println("[Wynnarsch] Couldn't write the config file:");
            e.printStackTrace();
        }
    }

    public static void openConfigScreen() {
        if(modMenuApiImpl == null) {
            modMenuApiImpl = new WynnarschModMenuApiImpl();
        }
        if(modMenuApiImpl.configScreen == null) {
            modMenuApiImpl.registerConfig();
        }
        MinecraftClient.getInstance().send(() -> MinecraftClient.getInstance().setScreen(modMenuApiImpl.configScreen));
    }
}
