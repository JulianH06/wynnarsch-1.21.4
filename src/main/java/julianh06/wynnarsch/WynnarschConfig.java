package julianh06.wynnarsch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class WynnarschConfig {
    public boolean partyMemberHide = true;
    public int maxHideDistance = 3;
    public int keyNotgCannonLeftID = GLFW.GLFW_KEY_A;
    public int keyNotgCannonShootID = GLFW.GLFW_KEY_SPACE;
    public int keyNotgCannonRightID = GLFW.GLFW_KEY_D;
    public int NotgCannonCurrentBlockHeight = 20;
    public int NotgCannonBlockHeight = 40;
    public int NotgCannonBlockWidth = 40;
    public int NotgCannonOverlayOffsetX = 0;
    public int NotgCannonOverlayOffsetY = 0;
    public boolean NotgCannonOverlayPreview = false;

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
}
