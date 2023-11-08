package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.Heplers.AssetManager;
import com.mygdx.game.Settings.Settings;

public class Spacecraft extends Actor {

    // Distintes posicions de la spacecraft, recta, pujant i baixant
    public static final int SPACECRAFT_STRAIGHT = 0;
    public static final int SPACECRAFT_UP = 1;
    public static final int SPACECRAFT_DOWN = 2;
    public static final int SPACECRAFT_LEFT = 3;
    public static final int SPACECRAFT_RIGHT = 4;

    // Paràmetres de la spacecraft
    private Vector2 position;
    private int width, height;
    private int direction;
    private Rectangle collisionRect;

    public Spacecraft(float x, float y, int width, int height) {

        // Inicialitzem els arguments segons la crida del constructor
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);

        // Inicialitzem la spacecraft a l'estat normal
        direction = SPACECRAFT_STRAIGHT;

        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();

        // Per a la gestio de hit
        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);

    }

    public void act(float delta) {
        super.act(delta);

        // Movem la spacecraft depenent de la direcció controlant que no surti de la pantalla
        switch (direction) {
            case SPACECRAFT_UP:
                if (this.position.y - Settings.SPACECRAFT_VELOCITY * delta >= 0) {
                    this.position.y -= Settings.SPACECRAFT_VELOCITY * delta;
                }
                break;
            case SPACECRAFT_RIGHT:
                if (this.position.x + width+ Settings.SPACECRAFT_VELOCITY * delta <= Settings.GAME_WIDTH) {
                    this.position.x += Settings.SPACECRAFT_VELOCITY * delta;
                }
                break;
            case SPACECRAFT_LEFT:
                if (this.position.x - Settings.SPACECRAFT_VELOCITY * delta >= 0) {
                    this.position.x -= Settings.SPACECRAFT_VELOCITY * delta;
                }
                break;
            case SPACECRAFT_DOWN:
                if (this.position.y + height + Settings.SPACECRAFT_VELOCITY * delta <= Settings.GAME_HEIGHT) {
                    this.position.y += Settings.SPACECRAFT_VELOCITY * delta;
                }
                break;
            case SPACECRAFT_STRAIGHT:
                break;
        }

        collisionRect.set(position.x, position.y + 3, width, height);
        setBounds(position.x, position.y, width, height);
    }

    public TextureRegion getSpacecraftTexture() {
                return AssetManager.naveAliada.first();
    }

    // Getters dels atributs principals
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

    // Canviem la direcció de la spacecraft: Puja
    public void goUp() {
        direction = SPACECRAFT_UP;
    }

    // Canviem la direcció de la spacecraft: Baixa
    public void goDown() {
        direction = SPACECRAFT_DOWN;
    }

    // Canviem la direcció de la spacecraft: Dreta
    public void goRight() {
        direction = SPACECRAFT_RIGHT;
    }

    // Canviem la direcció de la spacecraft: Esquerra
    public void goLeft() {
        direction = SPACECRAFT_LEFT;
    }

    // Posem la spacecraft al seu estat original
    public void goStraight() {
        direction = SPACECRAFT_STRAIGHT;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(getSpacecraftTexture(), position.x, position.y, width, height);
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }
}