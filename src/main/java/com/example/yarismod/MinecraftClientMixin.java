package com.example.yarismod.mixin;

import com.example.yarismod.YarisModClient;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow private int itemUseCooldown;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (YarisModClient.fastPlaceEnabled) {
            if (this.itemUseCooldown > YarisModClient.fastPlaceDelay) {
                this.itemUseCooldown = YarisModClient.fastPlaceDelay; // Заменяем задержку
            }
        }
    }
}