package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Heplers.AssetManager;
import com.mygdx.game.Settings.Settings;
import java.util.Random;

public class  Enemy extends ScrollableSpacecraft {

    private Rectangle collisionRect;
    Random r;
    int assetSpacecraft;

    public Enemy(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        // Creem el rectangle
        collisionRect = new Rectangle();

        r = new Random();
        assetSpacecraft = r.nextInt(15);

        setOrigin();

        setSize(width,height);
        setBounds(position.x, position.y, this.width, this.height);

    }

    public void setOrigin() {

        this.setOrigin(width/2 , 0);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Actualitzem el rectangle de colisions
        collisionRect.set(position.x, position.y + 3, width, height);
        setBounds(position.x, position.y, width, height);

    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        width = height = 150;
        // La posició serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
        position.x =  new Random().nextInt(Settings.GAME_WIDTH - (int) width);
        assetSpacecraft = r.nextInt(15);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.naveEnemiga.first(), position.x, position.y, this.getOriginX(), this.getOriginY(), width, height, this.getScaleX(), this.getScaleY(), this.getRotation());
    }

    // Retorna true si hi ha col·lisió
    public boolean collides(Spacecraft nau) {
        if (position.y <= nau.getY()) {
            // Comprovem si han col·lisionat
            return (Intersector.overlaps(collisionRect, nau.getCollisionRect()));
        }
        return false;
    }

    public boolean collidesBullet(Bullet b) {
        if (position.y <=b.getY()+b.getHeight() ){
            return (Intersector.overlaps(collisionRect, b.getCollisionRect()));
        }
        return false;
    }

}