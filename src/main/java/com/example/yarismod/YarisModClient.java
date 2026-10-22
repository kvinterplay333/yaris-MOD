package com.example.yarismod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;

import java.io.File;

public class YarisModClient implements ClientModInitializer {
    public static KeyBinding openGuiKey;

    // Параметры визуалов
    public static double customFov = 70.0;
    public static boolean fulbrightEnabled = false;
    
    // Параметры жмыха
    public static double bassIntensity = 1.0;
    public static boolean musicPlaying = false;
    public static int warpTarget = 0; // 0=Все, 1=Игроки, 2=Мобы

    // BlockRemover & BlockAccurated (FastBreak / FastPlace)
    public static boolean fastPlaceEnabled = false;
    public static int fastPlaceDelay = 0; // в тиках
    
    public static boolean fastBreakEnabled = false;
    public static int fastBreakDelay = 0; // в тиках

    @Override
    public void onInitializeClient() {
        // Регистрация папки для музыки
        File mcDir = MinecraftClient.getInstance().runDirectory;
        File musicDir = new File(mcDir, "yaris_music");
        if (!musicDir.exists()) {
            musicDir.mkdirs();
        }

        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.yarismod.opengui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_U,
                "category.yarismod.main"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.wasPressed()) {
                client.setScreen(new YarisConfigScreen());
            }

            // Фикс Fullbright (Надежный метод через Night Vision)
            if (fulbrightEnabled && client.player != null) {
                if (!client.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
                    client.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 4000, 0, false, false, false));
                }
            } else if (!fulbrightEnabled && client.player != null) {
                if (client.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
                    client.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
                }
            }
        });

        // ESP плашка
        HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHit = (EntityHitResult) client.crosshairTarget;
                if (entityHit.getEntity() instanceof PlayerEntity targetPlayer) {
                    renderPlayerInfo(drawContext, client, targetPlayer);
                }
            }
        });
    }

    private void renderPlayerInfo(DrawContext context, MinecraftClient client, PlayerEntity target) {
        int x = client.getWindow().getScaledWidth() / 2 + 10;
        int y = client.getWindow().getScaledHeight() / 2 - 20;

        String name = "Name: " + target.getName().getString();
        String hp = String.format("HP: %.1f / %.1f", target.getHealth(), target.getMaxHealth());
        int dist = (int) client.player.distanceTo(target);
        
        context.fill(x - 4, y - 4, x + 110, y + 26, 0x90000000);
        context.drawText(client.textRenderer, name, x, y, 0xFFFFFF, true);
        context.drawText(client.textRenderer, hp, x, y + 11, 0xFF5555, true);
    }
}