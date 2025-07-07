package julianh06.wynnarsch;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.text.Text;

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

            return builder.build();
        };
    }
}
