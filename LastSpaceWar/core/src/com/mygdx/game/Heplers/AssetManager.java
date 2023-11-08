package com.mygdx.game.Heplers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AssetManager {

    // Sprite Sheet
    public static Texture sheet;
    public static TextureAtlas juegoAtlas;

    // Nau i fons
    public static TextureRegion background;
    public static Array<TextureAtlas.AtlasRegion> naveAliada, naveEnemiga, fondo, naveEnemigaJefe,balaAliada,balaEnemiga,explosion;

    // Explosió
    public static Animation explosionAnim;

    // Sons
    public static Music music;

    // Font
    public static BitmapFont font;
    public static TextureAtlas neoButtons;
    public static Array<TextureAtlas.AtlasRegion> btnPress, btnNormal,back;
    public static Texture sheet2;
    public static TextureRegion[] explosion2;



    public static void load() {

        sheet = new Texture(Gdx.files.internal("sheet.png"));
        juegoAtlas = new TextureAtlas(Gdx.files.internal("sprite.txt"));
        sheet2 = new Texture(Gdx.files.internal("skin/craftacular-ui.png"));
        neoButtons = new TextureAtlas(Gdx.files.internal("skin/craftacular-ui.txt"));

        btnNormal = neoButtons.findRegions("button");
        btnNormal.reverse();
        btnPress=neoButtons.findRegions("button-disabled");
        btnPress.reverse();

        naveAliada = juegoAtlas.findRegions("NaveAliada");
        AssetManager.naveAliada.first().flip(false,true);

        naveEnemiga= juegoAtlas.findRegions("NaveEnemiga");

        explosion = juegoAtlas.findRegions("orange_explosion");
        balaAliada = juegoAtlas.findRegions("DisparoAmigo");

        explosion2 = new TextureRegion[53];

        // Carreguem els 53 estats de l'explosió
        int indexx = 0;
        for (int i = 0; i < 53; i++) {
                explosion2[i] = juegoAtlas.findRegions("orange_explosion").get(indexx);
                indexx++;
        }

        // Finalment creem l'animació
        explosionAnim = new Animation(0.04f, explosion2);

        // Fons de pantalla
        back=juegoAtlas.findRegions("espacio");
        Texture background2 =  new Texture(Gdx.files.internal("espacio3.jpg"));
        background = new TextureRegion(background2);
        background.flip(false, true);


        //Font del joc
        FileHandle fontFile = Gdx.files.internal("skin/font-title-export.fnt");
        font = new BitmapFont(fontFile, true);
        font.getData().setScale(1f);

        // Música del joc
        music = Gdx.audio.newMusic(Gdx.files.internal("sonidos/Musica.ogg"));
        music.setVolume(0.2f);
        music.setLooping(true);
        music.play();
    }

    public static void dispose() {
        // Alliberem els recursos gràfics i de audio
        sheet.dispose();
        music.dispose();
    }
}