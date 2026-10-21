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

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = 40;

        this.addDrawableChild(new SliderWidget(centerX - 100, startY, 200, 20, 
                Text.literal("FOV: " + (int) YarisModClient.customFov), 
                (YarisModClient.customFov - 5.0) / (240.0 - 5.0)) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal("FOV: " + (int) (5.0 + this.value * (240.0 - 5.0))));
            }

            @Override
            protected void applyValue() {
                YarisModClient.customFov = 5.0 + this.value * (240.0 - 5.0);
            }
        });

        this.addDrawableChild(new SliderWidget(centerX - 100, startY + 28, 200, 20, 
                Text.literal(String.format("Bass Warp: %.1fx", YarisModClient.bassIntensity)), 
                YarisModClient.bassIntensity / 5.0) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal(String.format("Bass Warp: %.1fx", this.value * 5.0)));
            }

            @Override
            protected void applyValue() {
                YarisModClient.bassIntensity = this.value * 5.0;
            }
        });

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Fullbright: " + (YarisModClient.fulbrightEnabled ? "ON" : "OFF")),
                button -> {
                    YarisModClient.fulbrightEnabled = !YarisModClient.fulbrightEnabled;
                    button.setMessage(Text.literal("Fullbright: " + (YarisModClient.fulbrightEnabled ? "ON" : "OFF")));
                }
        ).dimensions(centerX - 100, startY + 56, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("No Shake: " + (YarisModClient.noShakeEnabled ? "ON" : "OFF")),
                button -> {
                    YarisModClient.noShakeEnabled = !YarisModClient.noShakeEnabled;
                    button.setMessage(Text.literal("No Shake: " + (YarisModClient.noShakeEnabled ? "ON" : "OFF")));
                }
        ).dimensions(centerX - 100, startY + 84, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("No Effects: " + (YarisModClient.noEffectsEnabled ? "ON" : "OFF")),
                button -> {
                    YarisModClient.noEffectsEnabled = !YarisModClient.noEffectsEnabled;
                    button.setMessage(Text.literal("No Effects: " + (YarisModClient.noEffectsEnabled ? "ON" : "OFF")));
                }
        ).dimensions(centerX - 100, startY + 112, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Готово"), button -> this.close())
                .dimensions(centerX - 100, startY + 148, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 18, 0xFFFFFF);
    }
}
