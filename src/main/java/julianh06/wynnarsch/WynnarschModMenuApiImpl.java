package julianh06.wynnarsch;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.wynntils.utils.mc.McUtils;
import julianh06.wynnarsch.notg.cannon.CannonOverlay;
import julianh06.wynnarsch.notg.cannon.ColorEnum;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import me.shedaniel.math.Color;

import java.util.ArrayList;
import java.util.List;

public class WynnarschModMenuApiImpl implements ModMenuApi {
    private static final boolean IS_CLOTH_LOADED = FabricLoader.getInstance().isModLoaded("cloth-config");
    public Screen configScreen = null;

    public void registerConfig() {
        getModConfigScreenFactory().create(MinecraftClient.getInstance().currentScreen);
    }

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
            ConfigCategory Chat = builder.getOrCreateCategory(Text.of("Chat Notifier"));

            Hider.addEntry(
                    builder.entryBuilder()
                            .startBooleanToggle(Text.of("Enable/Disable party member hide feature"), WynnarschConfig.INSTANCE.partyMemberHide)
                            .setDefaultValue(true)
                            .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.partyMemberHide = newValue)
                            .build()
            );

            Hider.addEntry(
                    builder.entryBuilder()
                            .startBooleanToggle(Text.of("Only Hide in NOTG"), WynnarschConfig.INSTANCE.onlyInNotg)
                            .setDefaultValue(false)
                            .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.onlyInNotg = newValue)
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
                    .startBooleanToggle(Text.of("Enable/Disable the Cannon Overlay"), WynnarschConfig.INSTANCE.NotgCannonOverlayToggle)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.NotgCannonOverlayToggle = newValue)
                    .build()
            );

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
                    .startIntField(Text.of("Cannon Overlay Indicator Height"), WynnarschConfig.INSTANCE.NotgCannonCurrentBlockHeight)
                    .setDefaultValue(20)
                    .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.NotgCannonCurrentBlockHeight = newValue)
                    .build()
            );

            overlaySub.add(builder.entryBuilder()
                    .startColorField(Text.of("Cannon Overlay Indicator Color (Hexadecimal)"), WynnarschConfig.INSTANCE.NotgCannonIndicatorColor)
                    .setDefaultValue2(() -> Color.ofRGB(51, 153, 51))
                    .setSaveConsumer2(newValue -> WynnarschConfig.INSTANCE.NotgCannonIndicatorColor = newValue)
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

            SubCategoryBuilder textSub = builder.entryBuilder()
                    .startSubCategory(Text.of("Text"));

            textSub.add(
                    builder.entryBuilder()
                            .startFloatField(Text.of("Text Scale"), WynnarschConfig.INSTANCE.TextScale)
                            .setDefaultValue(5f)
                            .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.TextScale = newValue)
                            .build()
            );

            textSub.add(
                    builder.entryBuilder()
                            .startIntField(Text.of("Text Offset X"), WynnarschConfig.INSTANCE.TextOffsetX)
                            .setDefaultValue(75)
                            .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.TextOffsetX = newValue)
                            .build()
            );

            textSub.add(
                    builder.entryBuilder()
                            .startIntField(Text.of("Text Offset Y"), WynnarschConfig.INSTANCE.TextOffsetY)
                            .setDefaultValue(40)
                            .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.TextOffsetY = newValue)
                            .build()
            );

            textSub.add(
                    builder.entryBuilder()
                            .startIntField(Text.of("Text Duration (in milliseconds)"), WynnarschConfig.INSTANCE.TextDurationInMs)
                            .setDefaultValue(2000)
                            .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.TextDurationInMs = newValue)
                            .build()
            );

            textSub.add(
                    builder.entryBuilder()
                            .startColorField(Text.of("Text Color (Hexadecimal)"), WynnarschConfig.INSTANCE.TextColor)
                            .setDefaultValue2(() -> Color.ofRGB(255, 255, 255))
                            .setSaveConsumer2(newValue -> WynnarschConfig.INSTANCE.TextColor = newValue)
                            .build()
            );

            textSub.add(
                    builder.entryBuilder()
                            .startBooleanToggle(Text.of("Preview"), WynnarschConfig.INSTANCE.NotifierPreview)
                            .setDefaultValue(false)
                            .setSaveConsumer(newValue -> {
                                WynnarschConfig.INSTANCE.NotifierPreview = newValue;
                                System.out.println(newValue);
                                System.out.println(WynnarschConfig.INSTANCE.NotifierPreview);
                            })
                            .build()
            );

            SubCategoryBuilder soundSub = builder.entryBuilder()
                    .startSubCategory(Text.of("Sound"));

            DropdownMenuBuilder<String> soundDropdown =
                builder.entryBuilder()
                    .startStringDropdownMenu(Text.of("Notification Sound (previewed on save)"), WynnarschConfig.INSTANCE.Sound)
                    .setSelections(List.of(
                        "entity.experience_orb.pickup",
                        "block.bell.use",
                        "entity.player.levelup",
                        "block.anvil.place",
                        "block.note_block.pling",
                        "block.note_block.bell",
                        "block.note_block.flute",
                        "block.note_block.harp",
                        "entity.firework_rocket.launch",
                        "entity.item.pickup"
                    ))
                    .setSuggestionMode(false)
                    .setDefaultValue("entity.experience_orb.pickup")
                    .setSaveConsumer(newValue -> {
                        String oldValue = WynnarschConfig.INSTANCE.Sound;
                        WynnarschConfig.INSTANCE.Sound = newValue;
                        if(!oldValue.equals(newValue)) {
                            McUtils.playSoundAmbient(SoundEvent.of(Identifier.of(WynnarschConfig.INSTANCE.Sound)), WynnarschConfig.INSTANCE.SoundVolume, WynnarschConfig.INSTANCE.SoundPitch);
                        }
                    });

            soundSub.add(soundDropdown.build());

            soundSub.add(
                    builder.entryBuilder()
                            .startFloatField(Text.of("Sound volume"), WynnarschConfig.INSTANCE.SoundVolume)
                            .setDefaultValue(0.1f)
                            .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.SoundVolume = newValue)
                            .build()
            );

            soundSub.add(
                    builder.entryBuilder()
                            .startFloatField(Text.of("Sound pitch"), WynnarschConfig.INSTANCE.SoundPitch)
                            .setDefaultValue(1)
                            .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.SoundPitch = newValue)
                            .build()
            );

            Chat.addEntry(textSub.build());
            Chat.addEntry(soundSub.build());

            Chat.addEntry(
                    builder.entryBuilder()
                            .startStrList(Text.of("Words"), WynnarschConfig.INSTANCE.words)
                            .setExpanded(true)
                            .setSaveConsumer(newValue -> WynnarschConfig.INSTANCE.words = new ArrayList<>(newValue))
                            .build()
            );

            Chat.addEntry(
                    builder.entryBuilder()
                            .startTextDescription(Text.of("The Phrase on the Left is what needs to be in the Message to trigger and " +
                                    "the one on the right is the text that will be displayed. Separate them by | for it to work."))
                            .build()
            );

            Cannon.addEntry(overlaySub.build());
            Cannon.addEntry(hotkeySub.build());

            return configScreen = builder.build();
        };
    }
}
