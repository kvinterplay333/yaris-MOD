package com.example.yarismod.mixin;

import com.example.yarismod.YarisModClient;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.mob.HostileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class EntityWarpMixin {
    @Inject(method = "setupTransforms", at = @At("TAIL"))
    protected void injectWarp(LivingEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale, CallbackInfo ci) {
        if (YarisModClient.musicPlaying) {
            boolean shouldWarp = false;
            switch (YarisModClient.warpTarget) {
                case 0: shouldWarp = true; break; // Все
                case 1: shouldWarp = (entity instanceof PlayerEntity); break; // Игроки
                case 2: shouldWarp = (entity instanceof HostileEntity); break; // Враждебные
            }
            if (shouldWarp) {
                // Генерируем "бит" (от 0 до 1) на основе времени
                float beat = (float) Math.pow(Math.abs(Math.sin(System.currentTimeMillis() / 150.0)), 4);
                
                // Сила жмыха до bassIntensity (например, до 10 блоков)
                float stretchY = 1.0f + (beat * (float)YarisModClient.bassIntensity);
                float squeezeXZ = 1.0f - (beat * 0.4f); // Чуть сплющиваем по бокам
                
                matrices.scale(squeezeXZ, stretchY, squeezeXZ);
            }
        }
    }
}