package julianh06.wynnarsch.notg;

import com.wynntils.core.WynntilsMod;
import com.wynntils.core.components.Models;
import com.wynntils.functions.SocialFunctions;
import com.wynntils.models.players.event.HadesRelationsUpdateEvent;
import com.wynntils.overlays.PartyMembersOverlay;
import com.wynntils.utils.mc.McUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.scoreboard.*;

import java.util.Collection;
import java.util.Objects;

import static julianh06.wynnarsch.render.PlayerRenderFilter.*;

public class BossPlayerHider {
    //TODO
    public static void registerBossPlayerHider() {
        ClientTickEvents.START_CLIENT_TICK.register((tick) -> {
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
            }*/ //TODO: nur im Boss hiden

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
                if (distance >= 3) {
                    if(isHidden(player)) { show(player); }
                    continue;
                }

                boolean isPartyMember = false;
                //HadesRelationsUpdateEvent.PartyList
                for (String partyMember : Models.Party.getPartyMembers()) {
                    if(player.getName().getString().equals(partyMember)) {
                        isPartyMember = true;
                        break;
                    }
                }
                if(!isPartyMember) { continue; }

                System.out.println("Spieler in der Nähe: " + player.getName().getString() + " Distance: " + distance + " isPartymember: " + isPartyMember);
                hide(player);
                //client.world.removeEntity(player.getId(), Entity.RemovalReason.DISCARDED);
            }
        });
    }
}
