package julianh06.wynnarsch.notg;

import com.wynntils.core.components.Models;
import julianh06.wynnarsch.WynnarschConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

import static julianh06.wynnarsch.render.PlayerRenderFilter.*;

public class BossPlayerHider {
    public static void registerBossPlayerHider() {
        ClientTickEvents.START_CLIENT_TICK.register((tick) -> {
            if(!WynnarschConfig.INSTANCE.partyMemberHide) {
                return;
            }
            int Distance = WynnarschConfig.INSTANCE.maxHideDistance;

            MinecraftClient client = MinecraftClient.getInstance();
            if(client.player == null || client.world == null) { return; }

            /*Scoreboard scoreboard = client.world.getScoreboard();
            if(scoreboard != null) {
                Collection<ScoreHolder> scoreHolders = scoreboard.getKnownScoreHolders();
                if(scoreHolders != null ) {
                    for (ScoreHolder scoreHolder : scoreHolders) {
                        if(scoreHolder != null) {
                            System.out.println(Objects.requireNonNull(scoreHolder.getDisplayName()).getString());
                        }
                    }
                }
            }*/ //TODO: only hide in boss

            ClientPlayerEntity me = client.player;

            for (PlayerEntity player : client.world.getPlayers()) {
                if (player == null) {
                    System.out.println("PLAYER == NULL");
                    return;
                }

                if (player == me) {
                    continue;
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
}
