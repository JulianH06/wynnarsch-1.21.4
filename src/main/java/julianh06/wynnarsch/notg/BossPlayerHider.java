package julianh06.wynnarsch.notg;

import com.wynntils.core.components.Models;
import com.wynntils.models.raid.raids.RaidKind;
import com.wynntils.models.raid.type.RaidInfo;
import julianh06.wynnarsch.WynnarschConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

import static julianh06.wynnarsch.render.PlayerRenderFilter.*;

public class BossPlayerHider {
    static boolean inNotg = false;

    public static void registerBossPlayerHider() {
        ClientTickEvents.START_CLIENT_TICK.register((tick) -> {
            int Distance = WynnarschConfig.INSTANCE.maxHideDistance;

            MinecraftClient client = MinecraftClient.getInstance();
            if(client.player == null || client.world == null) { return; }
            ClientPlayerEntity me = client.player;

            for (PlayerEntity player : client.world.getPlayers()) {
                if (player == null) {
                    System.out.println("PLAYER == NULL");
                    return;
                }

                if (player == me) {
                    continue;
                }

                if(!WynnarschConfig.INSTANCE.partyMemberHide || (WynnarschConfig.INSTANCE.onlyInNotg && !inNotg)) {
                    if(isHidden(player)) { show(player); }
                    return;
                }

                double distance = player.getPos().distanceTo(me.getPos());
                if (distance >= Distance) {
                    if(isHidden(player)) { show(player); }
                    continue;
                }

                boolean isPartyMember = false;
                for (String partyMember : Models.Party.getPartyMembers()) {
                    if(player.getName().getString().equals(partyMember)) {
                        isPartyMember = true;
                        break;
                    }
                }
                if(!isPartyMember) { continue; }

                System.out.println("Spieler in der Nähe: " + player.getName().getString() + " Distance: " + distance + " isPartymember: " + isPartyMember);
                hide(player);
            }
        });
    }

    public static void onRaidStarted(RaidKind raid) {
        if(raid.getAbbreviation().equals("NOG")){
            inNotg = true;
        }
    }

    public static void onRaidEnded(RaidInfo info) {
        if(info.getRaidKind().getAbbreviation().equals("NOG")){
            inNotg = false;
        }
    }
}
