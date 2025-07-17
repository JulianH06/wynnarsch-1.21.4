package julianh06.wynnarsch.mixin;

import com.wynntils.models.raid.event.RaidChallengeEvent;
import com.wynntils.models.raid.type.RaidInfo;
import julianh06.wynnarsch.notg.cannon.CannonOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RaidChallengeEvent.Started.class)
public class RaidChallengeEventMixin {
    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    public void started (RaidInfo raidInfo, CallbackInfo ci) {
        CannonOverlay.onChallengeStarted(raidInfo);
    }
}
