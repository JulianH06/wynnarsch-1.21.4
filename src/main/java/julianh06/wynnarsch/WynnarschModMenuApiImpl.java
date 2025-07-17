package julianh06.wynnarsch;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import julianh06.wynnarsch.notg.cannon.CannonOverlay;
import julianh06.wynnarsch.notg.cannon.ColorEnum;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class WynnarschModMenuApiImpl implements ModMenuApi {
    private static final boolean IS_CLOTH_LOADED = FabricLoader.getInstance().isModLoaded("cloth-config");

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (!IS_CLOTH_LOADED) {
            return parent -> null;
        }

        return parent -> {
            // Cloth Config Builder
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.of("Wynnarsch Mod Config"));

            ConfigCategory Hider = builder.getOrCreateCategory(Text.of("Party Member Hider"));
            ConfigCategory Cannon = builder.getOrCreateCategory(Text.of("NOTG Cannon"));

            Hider.addEntry(
                    builder.entryBuilder()
                            .startBooleanToggle(Text.of("Enable/Disable party member hide feature"), WynnarschConfig.INSTANCE.partyMemberHide)
                            .setDefaultValue(true)
                            .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.partyMemberHide = newValue)
                            .build()
            );

            Hider.addEntry(
                    builder.entryBuilder()
                            .startIntField(Text.of("Party member maximum hide distance"), WynnarschConfig.INSTANCE.maxHideDistance)
                            .setDefaultValue(3)
                            .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.maxHideDistance = newValue)
                            .build()
            );

            SubCategoryBuilder overlaySub = builder.entryBuilder()
                    .startSubCategory(Text.of("Overlay"));

            overlaySub.add(builder.entryBuilder()
                    .startIntField(Text.of("Cannon Overlay Block Height"), WynnarschConfig.INSTANCE.NotgCannonBlockHeight)
                    .setDefaultValue(40)
                    .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.NotgCannonBlockHeight = newValue)
                    .build()
            );

            overlaySub.add(builder.entryBuilder()
                    .startIntField(Text.of("Cannon Overlay Block Width"), WynnarschConfig.INSTANCE.NotgCannonBlockWidth)
                    .setDefaultValue(40)
                    .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.NotgCannonBlockWidth = newValue)
                    .build()
            );

            overlaySub.add(builder.entryBuilder()
                    .startIntField(Text.of("Cannon Overlay X Offset"), WynnarschConfig.INSTANCE.NotgCannonOverlayOffsetX)
                    .setDefaultValue(0)
                    .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.NotgCannonOverlayOffsetX = newValue)
                    .build()
            );

            overlaySub.add(builder.entryBuilder()
                    .startIntField(Text.of("Cannon Overlay Y Offset"), WynnarschConfig.INSTANCE.NotgCannonOverlayOffsetY)
                    .setDefaultValue(0)
                    .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.NotgCannonOverlayOffsetY = newValue)
                    .build()
            );

            overlaySub.add(builder.entryBuilder()
                    .startIntField(Text.of("Cannon Overlay Current Block Highlight Heigh"), WynnarschConfig.INSTANCE.NotgCannonCurrentBlockHeight)
                    .setDefaultValue(20)
                    .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.NotgCannonCurrentBlockHeight = newValue)
                    .build()
            );

            overlaySub.add(builder.entryBuilder()
                    .startBooleanToggle(Text.of("Toggle Preview"), WynnarschConfig.INSTANCE.NotgCannonOverlayPreview)
                    .setDefaultValue(false)
                    .setSaveConsumer(newValue -> {
                        WynnarschConfig.INSTANCE.NotgCannonOverlayPreview = newValue;
                        if(!newValue) {
                            CannonOverlay.isValid = false;
                            for (ColorEnum Block : CannonOverlay.Blocks) {
                                Block = null;
                            }
                        }
                    })
                    .build()
            );

            SubCategoryBuilder hotkeySub = builder.entryBuilder()
                    .startSubCategory(Text.of("Hotkeys"));


            hotkeySub.add(
                    builder.entryBuilder()
                            .startIntField(Text.of("Move Cannon Left Hotkey"), WynnarschConfig.INSTANCE.keyNotgCannonLeftID)
                            .setDefaultValue(GLFW.GLFW_KEY_A)
                            .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.keyNotgCannonLeftID = newValue)
                            .build()
            );

            hotkeySub.add(
                    builder.entryBuilder()
                            .startIntField(Text.of("Move Cannon Shoot Hotkey"), WynnarschConfig.INSTANCE.keyNotgCannonShootID)
                            .setDefaultValue(GLFW.GLFW_KEY_A)
                            .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.keyNotgCannonShootID = newValue)
                            .build()
            );

            hotkeySub.add(
                    builder.entryBuilder()
                            .startIntField(Text.of("Move Cannon Right Hotkey"), WynnarschConfig.INSTANCE.keyNotgCannonRightID)
                            .setDefaultValue(GLFW.GLFW_KEY_A)
                            .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.keyNotgCannonRightID = newValue)
                            .build()
            );

            hotkeySub.add(
                    builder.entryBuilder()
                            .startTextDescription(Text.of("Enter The Keys as GLFW Codes, the codes can be found here: " +
                                    "https://www.glfw.org/docs/3.3/group__keys.html \n(I know this implementation sucks but i had " +
                                    "multiple issues with normal keybinds and modmenu keybinds and i know that the link isn't clickable, " +
                                    "\ni've spent way too much time trying to make this with keybinds but it kept crashing)\nCommonly Used Codes are: \n" +
                                    "A: 65, B: 66, C: 67, D: 68, E: 69, F: 70, G: 71, H: 72, I: 73, J: 74, K: 75, L: 76, M: 77, N: 78, O: 79, " +
                                    "P: 80, Q: 81, R: 82, S: 83, T: 84, U: 85, V: 86, W: 87, X: 88, Y: 89, Z: 90, SPACE: 32, Mouse Buttons: 1-8"))
                            .build()
            );

            Cannon.addEntry(overlaySub.build());
            Cannon.addEntry(hotkeySub.build());

            return builder.build();
        };
    }
}
