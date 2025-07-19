package julianh06.wynnarsch.mixin;

import com.wynntils.models.raid.event.RaidStartedEvent;
import com.wynntils.models.raid.raids.RaidKind;
import julianh06.wynnarsch.notg.BossPlayerHider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RaidStartedEvent.class)
public class RaidStartEventMixin {
    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    public void started (RaidKind raidKind, CallbackInfo ci) {
        BossPlayerHider.onRaidStarted(raidKind);
    }
}
