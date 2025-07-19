package julianh06.wynnarsch.mixin;

import com.wynntils.models.raid.event.RaidEndedEvent;
import com.wynntils.models.raid.type.RaidInfo;
import julianh06.wynnarsch.notg.BossPlayerHider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RaidEndedEvent.class)
public class RaidEndedEventMixin {
    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    public void ended (RaidInfo raidInfo, CallbackInfo ci) {
        BossPlayerHider.onRaidEnded(raidInfo);
    }
}
