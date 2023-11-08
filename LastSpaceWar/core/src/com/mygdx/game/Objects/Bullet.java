package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Heplers.AssetManager;
import com.mygdx.game.Settings.Settings;

public class Bullet extends Actor {

    private TextureRegion textureRegionBullet;
    private int width, height;
    private int speedX;
    private int positionX, positionY;
    private boolean isActive = true;
    private Rectangle collisionRect;

    public Bullet(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.textureRegionBullet = AssetManager.balaAliada.first();
        this.speedX = Settings.BULLET_SPEED;
        this.width = Settings.BULLET_WIDTH;
        this.height = Settings.BULLET_HEIGHT;
        collisionRect = new Rectangle();
        collisionRect.set( this.positionX,  this.positionY , width, height);
        setBounds( this.positionX,  this.positionY, width, height);
        setSize(textureRegionBullet.getRegionWidth(),textureRegionBullet.getRegionHeight());
    }

    public void act(float delta) {
        super.act(delta);
        positionY -= -speedX;

        //Controlem si esta en pantalla
        if (positionY < 0)
        {
            isActive = false;
        }

        collisionRect.set(positionX, positionY , width, height);
        setBounds(positionX, positionY, width, height);
    }

    public void draw (SpriteBatch batch)
    {
        batch.draw(textureRegionBullet, positionX, positionY, Settings.BULLET_WIDTH, Settings.BULLET_HEIGHT);

    }

    public float getX() {
        return positionX;
    }
    public float getY() {
        return positionY;
    }
    public boolean isActive() {
        return isActive;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
    public Rectangle getCollisionRect() {
        return collisionRect;
    }
}