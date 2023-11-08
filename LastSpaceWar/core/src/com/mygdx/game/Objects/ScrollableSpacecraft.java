package com.mygdx.game.Objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Settings.Settings;

public class ScrollableSpacecraft extends Actor {

    protected Vector2 position;
    protected float velocity;
    protected float width;
    protected float height;
    protected boolean leftOfScreen;

    public ScrollableSpacecraft(float x, float y, float width, float height, float velocity) {
        position = new Vector2(x, y);
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        leftOfScreen = false;

    }

    public void act(float delta) {

        super.act(delta);
        // Desplacem l'objecte en l'eix de les y
        position.y -= velocity * delta;

        // Si està fora de la pantalla canviem la variable a true
        if (position.y >= Settings.GAME_HEIGHT +100) {
            leftOfScreen = true;
        }
    }

    public void reset(float newX) {
        position.y = newX;
        leftOfScreen = false;
    }

    public boolean isLeftOfScreen() {
        return leftOfScreen;
    }
    public float getTailX() {
        return position.x + width;
    }
    public float getTailY() {
        return position.y - height;
    }
    public float getX() {
        return position.x;
    }
    public float getY() {
        return position.y;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
}