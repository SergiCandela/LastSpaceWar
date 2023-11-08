package com.mygdx.game.Settings;

import com.badlogic.gdx.Gdx;

public class Settings {

    public static final int GAME_WIDTH = Gdx.graphics.getWidth();
    public static final int GAME_HEIGHT = Gdx.graphics.getHeight();

    //Cuanto mide la nave
    public static final int SPACECRAFT_WIDTH = 200;
    public static final int SPACECRAFT_HEIGHT = 200;
    public static final float SPACECRAFT_STARTX = GAME_WIDTH/2;
    public static final float SPACECRAFT_STARTY =GAME_HEIGHT-500 ;
    public static final float SPACECRAFT_VELOCITY = 700;

    // Configuració Scrollable
    public static final int SPACECRAFT_SPEED = -400;
    public static final int SPACECRAFT_GAP = 75;
    public static final int BG_SPEED = -50;
    public static final int BG_SPEED_GAME = -200;

    public static final int BULLET_SPEED = 25;
    public static final int BULLET_WIDTH = 60;
    public static final int BULLET_HEIGHT = 60;
}
