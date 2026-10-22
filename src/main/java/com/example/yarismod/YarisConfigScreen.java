package com.example.yarismod;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class YarisConfigScreen extends Screen {
    public YarisConfigScreen() {
        super(Text.literal("Yaris Warp & Visuals Menu"));
    }

    private String getTargetName() {
        if (YarisModClient.warpTarget == 0) return "Все";
        if (YarisModClient.warpTarget == 1) return "Игроки";
        return "Мобы";
    }

    @Override
    protected void init() {
        int leftCol = this.width / 2 - 160;
        int rightCol = this.width / 2 + 10;
        int startY = 40;

        // --- ЛЕВАЯ КОЛОНКА (Визуалы и Жмых) ---
        
        this.addDrawableChild(new SliderWidget(leftCol, startY, 140, 20, 
                Text.literal("FOV: " + (int) YarisModClient.customFov), 
                (YarisModClient.customFov - 5.0) / (240.0 - 5.0)) {
            @Override
            protected void updateMessage() { this.setMessage(Text.literal("FOV: " + (int) (5.0 + this.value * (240.0 - 5.0)))); }
            @Override
            protected void applyValue() { YarisModClient.customFov = 5.0 + this.value * (240.0 - 5.0); }
        });

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Fullbright: " + (YarisModClient.fulbrightEnabled ? "ON" : "OFF")),
                button -> {
                    YarisModClient.fulbrightEnabled = !YarisModClient.fulbrightEnabled;
                    button.setMessage(Text.literal("Fullbright: " + (YarisModClient.fulbrightEnabled ? "ON" : "OFF")));
                }
        ).dimensions(leftCol, startY + 25, 140, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("МУЗЫКА (ЖМЫХ): " + (YarisModClient.musicPlaying ? "PLAY" : "STOP")),
                button -> {
                    YarisModClient.musicPlaying = !YarisModClient.musicPlaying;
                    button.setMessage(Text.literal("МУЗЫКА (ЖМЫХ): " + (YarisModClient.musicPlaying ? "PLAY" : "STOP")));
                }
        ).dimensions(leftCol, startY + 60, 140, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Кого жмыхать: " + getTargetName()),
                button -> {
                    YarisModClient.warpTarget = (YarisModClient.warpTarget + 1) % 3;
                    button.setMessage(Text.literal("Кого жмыхать: " + getTargetName()));
                }
        ).dimensions(leftCol, startY + 85, 140, 20).build());

        this.addDrawableChild(new SliderWidget(leftCol, startY + 110, 140, 20, 
                Text.literal(String.format("Сила жмыха: %.1f", YarisModClient.bassIntensity)), 
                YarisModClient.bassIntensity / 10.0) {
            @Override
            protected void updateMessage() { this.setMessage(Text.literal(String.format("Сила жмыха: %.1f", this.value * 10.0))); }
            @Override
            protected void applyValue() { YarisModClient.bassIntensity = this.value * 10.0; }
        });

        // --- ПРАВАЯ КОЛОНКА (BlockRemover & BlockAccurated) ---

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("FastBreak: " + (YarisModClient.fastBreakEnabled ? "ON" : "OFF")),
                button -> {
                    YarisModClient.fastBreakEnabled = !YarisModClient.fastBreakEnabled;
                    button.setMessage(Text.literal("FastBreak: " + (YarisModClient.fastBreakEnabled ? "ON" : "OFF")));
                }
        ).dimensions(rightCol, startY, 140, 20).build());

        this.addDrawableChild(new SliderWidget(rightCol, startY + 25, 140, 20, 
                Text.literal("Задержка ломания: " + YarisModClient.fastBreakDelay + "ms"), 
                YarisModClient.fastBreakDelay / 10.0) {
            @Override
            protected void updateMessage() { this.setMessage(Text.literal("Задержка ломания: " + (int)(this.value * 10) + "ms")); }
            @Override
            protected void applyValue() { YarisModClient.fastBreakDelay = (int)(this.value * 10); }
        });

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("FastPlace: " + (YarisModClient.fastPlaceEnabled ? "ON" : "OFF")),
                button -> {
                    YarisModClient.fastPlaceEnabled = !YarisModClient.fastPlaceEnabled;
                    button.setMessage(Text.literal("FastPlace: " + (YarisModClient.fastPlaceEnabled ? "ON" : "OFF")));
                }
        ).dimensions(rightCol, startY + 60, 140, 20).build());

        this.addDrawableChild(new SliderWidget(rightCol, startY + 85, 140, 20, 
                Text.literal("Задержка стр-ва: " + YarisModClient.fastPlaceDelay + "ms"), 
                YarisModClient.fastPlaceDelay / 10.0) {
            @Override
            protected void updateMessage() { this.setMessage(Text.literal("Задержка стр-ва: " + (int)(this.value * 10) + "ms")); }
            @Override
            protected void applyValue() { YarisModClient.fastPlaceDelay = (int)(this.value * 10); }
        });

        // --- НИЗ ---
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Готово"), button -> this.close())
                .dimensions(this.width / 2 - 70, startY + 150, 140, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
        
        context.drawTextWithShadow(this.textRenderer, "Папка для треков: .minecraft/yaris_music", this.width / 2 - 160, this.height - 20, 0xAAAAAA);
    }
}