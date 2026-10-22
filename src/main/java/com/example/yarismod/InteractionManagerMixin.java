package com.example.yarismod.mixin;

import com.example.yarismod.YarisModClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class InteractionManagerMixin {
    @Shadow private int blockBreakingCooldown;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (YarisModClient.fastBreakEnabled) {
            if (this.blockBreakingCooldown > YarisModClient.fastBreakDelay) {
                this.blockBreakingCooldown = YarisModClient.fastBreakDelay; // Заменяем задержку
            }
        }
    }
}