package com.mygdx.game.Objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.Heplers.AssetManager;
import com.mygdx.game.Settings.Settings;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ScrollHandler extends Group {

    // Fons de pantalla
    Background bg, bg_back;

    private GlyphLayout textLayout;
    private ArrayList<Enemy> naus;
    Random r;
    Music music;
    FileHandle handle;

    public ScrollHandler(Stage g) {

        // Creem els dos fons
        bg = new Background(0, 0, Settings.GAME_WIDTH , Settings.GAME_HEIGHT*2, Settings.BG_SPEED_GAME);
        bg_back = new Background(0, bg.getTailY(), Settings.GAME_WIDTH , Settings.GAME_HEIGHT*2 , Settings.BG_SPEED_GAME);

        addActor(bg);
        addActor(bg_back);
        r = new Random();
        naus = new ArrayList<Enemy>();
        textLayout = new GlyphLayout();
        handle = Gdx.files.local("dificultad.bin");
        ByteArrayInputStream in = new ByteArrayInputStream(handle.readBytes());
        DataInputStream ooi = new DataInputStream(in);

        //Creem la primera nau
        Enemy n = new Enemy(r.nextInt(Settings.GAME_WIDTH- (int) 150), 0 , 150, 150, Settings.SPACECRAFT_SPEED);
        naus.add(n);

        //Agafem el numero de naus del nivell
        int numm = 0;
        try {
            numm = ooi.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Totes les naus que queden
        for (int i = 1; i < numm; i++) {
            // Afegim la nau.
            n = new Enemy(r.nextInt(Settings.GAME_WIDTH- (int) 150), naus.get(naus.size() - 1).getTailY() + Settings.SPACECRAFT_GAP , 150, 150, Settings.SPACECRAFT_SPEED);
            // Afegim la nau a l'array
            naus.add(n);
            // Afegim la nau al grup d'actors
            addActor(n);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Si algun element està fora de la pantalla, fem un reset de l'element.
        if (bg.isLeftOfScreen()) {
            bg.reset(bg_back.getTailY());

        } else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailY());
        }
    }

    public boolean collides(Spacecraft nau) {
        // Comprovem les col·lisions entre cada nauEnemiga i la nau
        for (Enemy nauEnemiga : naus) {
            if (nauEnemiga.collides(nau)) {
                return true;
            }
        }
        return false;
    }

    public boolean out() {
        for (int i = 0; i < naus.size(); i++) {
            Enemy nauEnemiga = naus.get(i);
            if (nauEnemiga.isLeftOfScreen()) {
              return true;
            }
        }
        return false;
    }

    public boolean collides2(Bullet nau,SpriteBatch batch) {
        for (int i = 0; i < naus.size(); i++) {
            Enemy nauEnemiga = naus.get(i);
            if (nauEnemiga.collidesBullet(nau)) {
                int explosionTime=0;
                music = Gdx.audio.newMusic(Gdx.files.internal("sonidos/explosion.mp3"));
                music.setVolume(0.5f);
                music.setLooping(false);
                music.play();
                while(explosionTime<200){
                    batch.draw((TextureRegion) AssetManager.explosionAnim.getKeyFrame(explosionTime, false), (nauEnemiga.position.x + nauEnemiga.width / 2) - 100, Settings.GAME_HEIGHT-(nauEnemiga.position.y+nauEnemiga.getHeight())-20, nauEnemiga.width+50, nauEnemiga.height+50);
                    explosionTime++;
                }
                nauEnemiga.reset(-200);
                return true;
            }
        }
        return false;
    }
}